package de.unisb.cs.st.javalanche.coverage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class CoverageTraceUtil {

	private static final Collection<String> EMPTY_COLLECTION = new ArrayList<String>();
	private static Logger logger = Logger.getLogger(CoverageTraceUtil.class);

	public static Collection<String> getDifferentMethodsForTests(
			Map<String, Map<String, Map<Integer, Integer>>> data1T,
			Map<String, Map<String, Map<Integer, Integer>>> data2T) {
		Set<String> differences = new HashSet<String>();
		Set<String> keySet = data1T.keySet();
		if (data1T == null || data2T == null) {
			return EMPTY_COLLECTION;
		}
		for (String testKey : keySet) {
			if (data2T.containsKey(testKey)) {
				logger.info("Checking Test " + testKey);
				Map<String, Map<Integer, Integer>> data1 = data1T.get(testKey);
				Map<String, Map<Integer, Integer>> data2 = data2T.get(testKey);
				Set<String> allMethods = getAllMethods(data1, data2);
				for (String key : allMethods) {
					boolean difference = false;
					if (data1.containsKey(key) && data2.containsKey(key)) {
						Map<Integer, Integer> classData1 = data1.get(key);
						Map<Integer, Integer> classData2 = data2.get(key);
						difference = compareLines(classData1, classData2);
					} else {
						difference = true;
					}
					if (difference) {
						differences.add(key);
					}
				}
			}
		}
		return differences;
	}

	public static Collection<String> getDifferentMethods(
			Map<String, Map<Integer, Integer>> data1,
			Map<String, Map<Integer, Integer>> data2) {
		Set<String> differences = new HashSet<String>();
		if (data1 == null && data2 == null) {
			return differences;
		}
		if (data1 != null && data2 == null) {
			differences.addAll(data1.keySet());
			return differences;
		}
		if (data1 == null && data2 != null) {
			differences.addAll(data2.keySet());
			return differences;
		}
		Set<String> allMethods = getAllMethods(data1, data2);
		for (String key : allMethods) {
			boolean difference = false;
			if (data1.containsKey(key) && data2.containsKey(key)) {
				Map<Integer, Integer> classData1 = data1.get(key);
				Map<Integer, Integer> classData2 = data2.get(key);
				difference = compareLines(classData1, classData2);
			} else {
				difference = true;
			}
			if (difference) {
				differences.add(key);
			}
		}
		return differences;
	}

	/**
	 * @param lineData1
	 * @param lineData2
	 * @return True, if there is any line difference.
	 */
	private static boolean compareLines(Map<Integer, Integer> lineData1,
			Map<Integer, Integer> lineData2) {
		Set<Integer> allLines = new HashSet<Integer>();
		allLines.addAll(lineData1.keySet());
		allLines.addAll(lineData2.keySet());
		for (Integer line : allLines) {
			if (lineData1.containsKey(line) && lineData2.containsKey(line)
					&& lineData1.get(line).equals(lineData2.get(line))) {
			} else {
				return true;
			}
		}
		return false;
	}

	private static Set<String> getAllMethods(
			Map<String, Map<Integer, Integer>> data1,
			Map<String, Map<Integer, Integer>> data2) {
		Set<String> allTests = new HashSet<String>();
		allTests.addAll(data1.keySet());
		allTests.addAll(data2.keySet());
		return allTests;
	}

	public static Collection<String> getDifferentReturnMethodsForTests(
			Map<String, Map<String, Map<Integer, Integer>>> data1T,
			Map<String, Map<String, Map<Integer, Integer>>> data2T) {
		Set<String> differences = new HashSet<String>();
		Set<String> keySet = data1T.keySet();
		if (data1T == null || data2T == null) {
			return EMPTY_COLLECTION;
		}
		for (String testKey : keySet) {
			if (data2T.containsKey(testKey)) {
				logger.info("Checking Test " + testKey);
				Map<String, Map<Integer, Integer>> data1 = data1T.get(testKey);
				Map<String, Map<Integer, Integer>> data2 = data2T.get(testKey);
				Set<String> allMethods = getAllMethods(data1, data2);
				for (String key : allMethods) {
					boolean difference = false;
					if (data1.containsKey(key) && data2.containsKey(key)) {
						Map<Integer, Integer> classData1 = data1.get(key);
						Map<Integer, Integer> classData2 = data2.get(key);
						difference = compareReturns(classData1, classData2);
					} else {
						difference = true;
					}
					if (difference) {
						differences.add(key);
					}
				}
			}
		}
		return differences;
	}

	private static boolean compareReturns(Map<Integer, Integer> returnData1,
			Map<Integer, Integer> returnData2) {
		Set<Integer> allReturns = new HashSet<Integer>();
		allReturns.addAll(returnData1.keySet());
		allReturns.addAll(returnData2.keySet());
		for (Integer returnHash : allReturns) {
			if (returnData1.containsKey(returnHash)
					&& returnData2.containsKey(returnHash)) {
			} else {
				return true;
			}
		}
		return false;
	}

	private static Map<String, Map<String, Map<String, Map<Integer, Integer>>>> lineCache = new HashMap<String, Map<String, Map<String, Map<Integer, Integer>>>>();
	private static Map<String, Map<String, Map<String, Map<Integer, Integer>>>> dataCache = new HashMap<String, Map<String, Map<String, Map<Integer, Integer>>>>();

	public static Map<String, Map<String, Map<Integer, Integer>>> loadLineCoverageTraceCached(
			String id) {
		if ("0".equals(id)) {
			if (lineCache.containsKey(id)) {
				return lineCache.get(id);
			}
			Map<String, Map<String, Map<Integer, Integer>>> result = CoverageAnalyzer
					.loadLineCoverageTrace(id);
			lineCache.put(id, result);
			return result;
		}
		return CoverageAnalyzer.loadLineCoverageTrace(id);

	}

	public static Map<String, Map<String, Map<Integer, Integer>>> loadDataCoverageTraceCached(
			String id) {
		if ("0".equals(id)) {
			if (dataCache.containsKey(id)) {
				return dataCache.get(id);
			}
			Map<String, Map<String, Map<Integer, Integer>>> result = CoverageAnalyzer
					.loadDataCoverageTrace(id);
			dataCache.put(id, result);
			return result;
		}
		return CoverageAnalyzer.loadDataCoverageTrace(id);
	}

}