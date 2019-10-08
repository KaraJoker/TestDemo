package com.highto.framework.util;

public class CombinationUtil {

	/**
	 * 计算出从总元素里面取一半元素的所有组合可能
	 * 
	 * @return
	 */
	public static int[] makeHalfTakenCombination(int totalCount) throws Exception {
		int halfCount = totalCount / 2;
		if (totalCount >= 0 && halfCount * 2 == totalCount) {
			long combinationCount = 1;
			for (int i = 0; i < halfCount; i++) {
				combinationCount *= (totalCount - i);
			}
			combinationCount /= makeFactorial(halfCount);
			int[] result = new int[((int) combinationCount) * totalCount];
			int minBinCombValue = (1 << halfCount) - 1;
			int maxBinCombValue = minBinCombValue << halfCount;

			int combIdx = 0;
			for (int i = minBinCombValue; i <= maxBinCombValue; i++) {
				int combACount = 0;
				int combBCount = 0;
				for (int j = 0; j < totalCount; j++) {
					int n = i >>> j;
					n = n & 1;
					if (n == 1) {
						combACount++;
						if (combACount > halfCount) {
							break;
						}
						result[combIdx + combACount - 1] = j;
					} else {
						combBCount++;
						if (combBCount > halfCount) {
							break;
						}
						result[combIdx + halfCount + combBCount - 1] = j;
					}
				}
				if (combACount == halfCount && combBCount == halfCount) {
					combIdx += totalCount;
				}
			}
			return result;
		} else {
			throw new Exception("wrong totalCount!");
		}
	}

	/**
	 * 计算n!
	 * 
	 * @param n
	 * @return
	 */
	private static long makeFactorial(int n) {
		long factorial = 1;
		while (n > 1) {
			factorial *= n--;
		}
		return factorial;
	}

}
