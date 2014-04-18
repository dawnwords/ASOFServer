package edu.fudan.se.service.bundle.similarity;

public class MostSimilarCalculator<T> {

	private SimilarityCalculator<T> similarityCalculator;

	public MostSimilarCalculator(SimilarityCalculator<T> similarityCalculator) {
		this.similarityCalculator = similarityCalculator;
	}

	public int getMostSimilar(T target, Iterable<? extends T> candidates) {
		double bestValue = -1;
		int index = 0, i = 0;
		for (T candidate : candidates) {
			double currentValue = similarityCalculator.calculateSimilarity(
					candidate, target);
			System.out.printf("[%d]%s:similarity = %f\n", i, candidate,
					currentValue);
			if (currentValue > bestValue) {
				bestValue = currentValue;
				index = i;
			}
			i++;
		}
		// TODO Handle No Match Case
		return index;
	}
}
