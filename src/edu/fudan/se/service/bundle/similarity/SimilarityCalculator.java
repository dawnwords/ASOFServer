package edu.fudan.se.service.bundle.similarity;

public interface SimilarityCalculator<T> {
	double calculateSimilarity(T t1, T t2);
}
