package edu.fudan.se.service.bundle.chooser;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
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

public class BasicBundleChooser implements BundleChooser {
	private static final Name BUNDLE_DESCRIPTION = new Name(
			"Bundle-Description");
	private static final Name SERVICE_INPUT = new Name("Service-Input");
	private static final Name SERVCIE_OUTPUT = new Name("Service-Output");

	private static final int BUFFER_SIZE = 4096;

	private File bundleDir;
	private SimilarityCalculator<ServiceDescription> serviceDescriptionSimilarityCalculator;
	private SimilarityCalculator<String> wordSimilarityCalculator;

	private static final FilenameFilter JAR_FILTER = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith(".jar");
		}
	};

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
		for (String bundleName : bundleDir.list(JAR_FILTER)) {
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
		byte[] bundleFile = getBundleFile(bundle.getPath());

		return new Response(inputMatch, outputMatch, bundleFile,
				bundle.getName());
	}

	private byte[] getBundleFile(String path) {
		byte[] buffer = null;
		BufferedInputStream in = null;
		ByteArrayOutputStream bos = null;
		try {
			in = new BufferedInputStream(new FileInputStream(path));
			bos = new ByteArrayOutputStream(BUFFER_SIZE);
			byte[] b = new byte[BUFFER_SIZE];
			int n;
			while ((n = in.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			buffer = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(in);
			close(bos);
		}
		return buffer;
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

			String[] input = checkFormatandSplit("Service-Input", serviceInput);
			String[] output = checkFormatandSplit("Service-Output",
					serviceOutput);

			result = new BundleDescription(bundlePath, serviceDescription,
					input, output);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(bundle);
		}

		return result;
	}

	private String[] checkFormatandSplit(String position, String tocheck) {
		if (tocheck == null || tocheck.length() == 0) {
			return new String[0];
		}
		final String paramRegex = "((\\w+)(,(\\w+))*)?";
		if (tocheck != null && !tocheck.matches(paramRegex)) {
			throw new IllegalArgumentException(String.format(
					"%s format error: %s", position, tocheck));
		}
		return tocheck.split(",");
	}

	private void close(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
