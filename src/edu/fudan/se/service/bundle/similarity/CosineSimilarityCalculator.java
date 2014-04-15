package edu.fudan.se.service.bundle.similarity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;

import edu.fudan.se.service.bundle.message.ServiceDescription;

public class CosineSimilarityCalculator implements
		SimilarityCalculator<ServiceDescription> {

	private static final HashSet<String> STOP_LIST = new HashSet<String>(
			Arrays.asList("a", "about", "above", "above", "across", "after",
					"afterwards", "again", "against", "all", "almost", "alone",
					"along", "already", "also", "although", "always", "am",
					"among", "amongst", "amoungst", "amount", "an", "and",
					"another", "any", "anyhow", "anyone", "anything", "anyway",
					"anywhere", "are", "around", "as", "at", "back", "be",
					"became", "because", "become", "becomes", "becoming",
					"been", "before", "beforehand", "behind", "being", "below",
					"beside", "besides", "between", "beyond", "bill", "both",
					"bottom", "but", "by", "call", "can", "cannot", "cant",
					"co", "con", "could", "couldnt", "cry", "de", "describe",
					"detail", "do", "done", "down", "due", "during", "each",
					"eg", "eight", "either", "eleven", "else", "elsewhere",
					"empty", "enough", "etc", "even", "ever", "every",
					"everyone", "everything", "everywhere", "except", "few",
					"fifteen", "fify", "fill", "find", "fire", "first", "five",
					"for", "former", "formerly", "forty", "found", "four",
					"from", "front", "full", "further", "get", "give", "go",
					"had", "has", "hasnt", "have", "he", "hence", "her",
					"here", "hereafter", "hereby", "herein", "hereupon",
					"hers", "herself", "him", "himself", "his", "how",
					"however", "hundred", "ie", "if", "in", "inc", "indeed",
					"interest", "into", "is", "it", "its", "itself", "keep",
					"last", "latter", "latterly", "least", "less", "ltd",
					"made", "many", "may", "me", "meanwhile", "might", "mill",
					"mine", "more", "moreover", "most", "mostly", "move",
					"much", "must", "my", "myself", "name", "namely",
					"neither", "never", "nevertheless", "next", "nine", "no",
					"nobody", "none", "noone", "nor", "not", "nothing", "now",
					"nowhere", "of", "off", "often", "on", "once", "one",
					"only", "onto", "or", "other", "others", "otherwise",
					"our", "ours", "ourselves", "out", "over", "own", "part",
					"per", "perhaps", "please", "put", "rather", "re", "same",
					"see", "seem", "seemed", "seeming", "seems", "serious",
					"several", "she", "should", "show", "side", "since",
					"sincere", "six", "sixty", "so", "some", "somehow",
					"someone", "something", "sometime", "sometimes",
					"somewhere", "still", "such", "system", "take", "ten",
					"than", "that", "the", "their", "them", "themselves",
					"then", "thence", "there", "thereafter", "thereby",
					"therefore", "therein", "thereupon", "these", "they",
					"thickv", "thin", "third", "this", "those", "though",
					"three", "through", "throughout", "thru", "thus", "to",
					"together", "too", "top", "toward", "towards", "twelve",
					"twenty", "two", "un", "under", "until", "up", "upon",
					"us", "very", "via", "was", "we", "well", "were", "what",
					"whatever", "when", "whence", "whenever", "where",
					"whereafter", "whereas", "whereby", "wherein", "whereupon",
					"wherever", "whether", "which", "while", "whither", "who",
					"whoever", "whole", "whom", "whose", "why", "will", "with",
					"within", "without", "would", "yet", "you", "your",
					"yours", "yourself", "yourselves", "the"));

	private static CosineSimilarityCalculator instance = new CosineSimilarityCalculator();

	private CosineSimilarityCalculator() {
	}

	public static CosineSimilarityCalculator getInstance() {
		return instance;
	}

	@Override
	public double calculateSimilarity(ServiceDescription service1,
			ServiceDescription service2) {
		if (!stringArrayMatch(service1.getInput(), service2.getInput())
				|| !stringArrayMatch(service1.getOutput(), service2.getOutput())) {
			return 0;
		}

		String[] words1 = splitSentences(service1.getDescription());
		String[] words2 = splitSentences(service2.getDescription());
		String[] join = joinWords(words1, words2);
		Vector<Integer> v1 = getFrequencyVector(words1, join);
		Vector<Integer> v2 = getFrequencyVector(words2, join);
		return getCosine(v1, v2);
	}

	private boolean stringArrayMatch(String[] sa1, String[] sa2) {
		return (sa1 == null && sa2 == null)
				|| (sa1 != null && sa2 != null && sa1.length == sa2.length);
	}

	private double getCosine(Vector<Integer> v1, Vector<Integer> v2) {
		int s1 = 0, s2 = 0, s3 = 0;
		for (int i = 0; i < v1.size(); i++) {
			int a = v1.get(i);
			int b = v2.get(i);
			s1 += a * b;
			s2 += a * a;
			s3 += b * b;
		}
		return s1 / (Math.sqrt(s2) * Math.sqrt(s3));
	}

	private Vector<Integer> getFrequencyVector(String[] words, String[] join) {
		Vector<Integer> result = new Vector<Integer>();
		for (String key : join) {
			int count = 0;
			for (String word : words) {
				if (word.equals(key)) {
					count++;
				}
			}
			result.add(count);
		}
		return result;
	}

	private String[] joinWords(String[] words1, String[] words2) {
		HashSet<String> wordSet = new HashSet<String>();
		addWordNotInStopList(words1, wordSet);
		addWordNotInStopList(words2, wordSet);
		return wordSet.toArray(new String[wordSet.size()]);
	}

	private void addWordNotInStopList(String[] words, HashSet<String> wordSet) {
		for (String word : words) {
			if (!STOP_LIST.contains(word)) {
				wordSet.add(word);
			}
		}
	}

	private String[] splitSentences(String sentence) {
		return sentence.toLowerCase().split(" ");
	}

}
