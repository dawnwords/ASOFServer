package edu.fudan.se.service.bundle.chooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import edu.fudan.se.service.bundle.message.BundleDescription;
import edu.fudan.se.service.bundle.message.Response;
import edu.fudan.se.service.bundle.message.ServiceDescription;
import edu.fudan.se.service.bundle.similarity.MostSimilarCalculator;
import edu.fudan.se.service.bundle.similarity.SimilarityCalculator;
import edu.fudan.se.service.servlet.util.IOUtil;
import edu.fudan.se.service.servlet.util.Parameter;

public class BasicBundleChooser implements BundleChooser {
	private static final Name BUNDLE_DESCRIPTION = new Name(
			"Bundle-Description");
	private static final Name SERVICE_ACTIVITY = new Name("Service-Activity");
	private static final Name SERVICE_INPUT = new Name("Service-Input");
	private static final Name SERVCIE_OUTPUT = new Name("Service-Output");

	private File bundleDir;
	private SimilarityCalculator<ServiceDescription> serviceDescriptionSimilarityCalculator;
	private SimilarityCalculator<String> wordSimilarityCalculator;

	public BasicBundleChooser(
			File bundleDir,
			SimilarityCalculator<ServiceDescription> serviceDescriptionSimilarityCalculator,
			SimilarityCalculator<String> wordSimilarityCalculator) {
		this.bundleDir = bundleDir;
		this.serviceDescriptionSimilarityCalculator = serviceDescriptionSimilarityCalculator;
		this.wordSimilarityCalculator = wordSimilarityCalculator;
	}

	@Override
	public Response getResponseByDescription(ServiceDescription service) {
		List<BundleDescription> bundles = new ArrayList<BundleDescription>();
		for (String bundleName : bundleDir.list(Parameter.JAR_FILTER)) {
			BundleDescription bundle = getBundleDescription(bundleName);
			if (bundle != null) {
				bundles.add(bundle);
			}
		}

		int mostSimilar = new MostSimilarCalculator<ServiceDescription>(
				serviceDescriptionSimilarityCalculator).getMostSimilar(service,
				bundles);

		return constructResponse(service, bundles.get(mostSimilar));
	}

	private Response constructResponse(ServiceDescription service,
			BundleDescription bundle) {
		int[] inputMatch = getParamMatch(service.getInput(), bundle.getInput());
		int[] outputMatch = getParamMatch(service.getOutput(),
				bundle.getOutput());
		byte[] bundleFile = IOUtil.getBundleFile(bundle.getPath());
		
		return new Response(inputMatch, outputMatch, bundleFile,
				bundle.getName(), bundle.getActivityClass());
	}

	private int[] getParamMatch(String[] source, String[] target) {
		int[] result = new int[target.length];
		MostSimilarCalculator<String> calculator = new MostSimilarCalculator<String>(
				wordSimilarityCalculator);
		for (int i = 0; i < source.length; i++) {
			result[i] = calculator.getMostSimilar(source[i],
					Arrays.asList(target));
		}
		return result;
	}

	private BundleDescription getBundleDescription(String bundleName) {
		JarFile bundle = null;
		BundleDescription result = null;
		try {
			String bundlePath = bundleDir.getAbsolutePath() + File.separator
					+ bundleName;
			bundle = new JarFile(bundlePath);
			Manifest manifest = bundle.getManifest();
			Attributes attrs = (Attributes) manifest.getMainAttributes();

			String serviceDescription = attrs.getValue(BUNDLE_DESCRIPTION);
			String serviceInput = attrs.getValue(SERVICE_INPUT);
			String serviceOutput = attrs.getValue(SERVCIE_OUTPUT);
			String serviceActivity = attrs.getValue(SERVICE_ACTIVITY);

			String[] input = checkFormatandSplit("Service-Input", serviceInput);
			String[] output = checkFormatandSplit("Service-Output",
					serviceOutput);

			result = new BundleDescription(bundlePath, serviceActivity,
					serviceDescription, input, output);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(bundle);
		}

		return result;
	}

	private String[] checkFormatandSplit(String position, String tocheck) {
		if (tocheck == null || tocheck.length() == 0) {
			return new String[0];
		}
		tocheck = tocheck.replaceAll(" " , "");
		final String paramRegex = "((\\w+)(,(\\w+))*)?";
		if (tocheck != null && !tocheck.matches(paramRegex)) {
			throw new IllegalArgumentException(String.format(
					"%s format error: %s", position, tocheck));
		}
		return tocheck.split(",");
	}

}
