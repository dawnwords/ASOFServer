package edu.fudan.se.service.bundle.chooser;

import java.io.File;

import edu.fudan.se.service.bundle.message.ServiceDescription;
import edu.fudan.se.service.bundle.similarity.CosineSimilarityCalculator;
import edu.fudan.se.service.bundle.similarity.SimilarityCalculator;
import edu.fudan.se.service.bundle.similarity.SimpleWordSimilarityCalculator;

public class BundleChooserHolder {
	private static BundleChooser defaultBundleChooser;
	private static final String BUNDLE_DIR = "D:" + File.separator
			+ "Development" + File.separator + "Workspace" + File.separator
			+ "bundleDir";

	static {
		File bundleDir = new File(BUNDLE_DIR);
		SimilarityCalculator<ServiceDescription> serviceDescriptionSimilarityCalculator = CosineSimilarityCalculator
				.getInstance();
		SimilarityCalculator<String> wordSimilarityCalculator = SimpleWordSimilarityCalculator
				.getInstance();
		defaultBundleChooser = new BasicBundleChooser(bundleDir,
				serviceDescriptionSimilarityCalculator,
				wordSimilarityCalculator);
	}

	public static BundleChooser getDefaultBundleChooser() {
		return defaultBundleChooser;
	}
}
