package edu.fudan.se.service.bundle.similarity;

public class SimpleWordSimilarityCalculator implements
		SimilarityCalculator<String> {

	public static SimpleWordSimilarityCalculator instance = new SimpleWordSimilarityCalculator();

	private SimpleWordSimilarityCalculator() {
	}

	public static SimpleWordSimilarityCalculator getInstance() {
		return instance;
	}

	@Override
	public double calculateSimilarity(String word1, String word2) {
		return word1.contains(word2) || word2.contains(word1) ? 1 : 0;
	}

}
