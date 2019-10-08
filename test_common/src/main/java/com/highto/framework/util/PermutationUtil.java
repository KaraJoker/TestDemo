package com.highto.framework.util;

import java.util.ArrayList;
import java.util.List;

public class PermutationUtil {

	private static void permutate(int array[], int end, int length, int b[], List<Integer> result) {
		for (int i = 0; i < end; i++) {
			if (array[i] != -1) {
				b[length] = array[i];
				array[i] = -1;
				permutate(array, end, length + 1, b, result);
				array[i] = i;
			}
		}

		if (length == end) {
			for (int i = 0; i < length; i++) {
				result.add(b[i]);
			}
		}
	}

	public static int[] makeFullPermutation(int totalCount) {
		List<Integer> result = new ArrayList<>();
		int array[] = new int[5];
		for (int i = 0; i < totalCount; i++) {
			array[i] = i;
		}
		int b[] = new int[totalCount * 2];
		permutate(array, totalCount, 0, b, result);
		int[] arrayResult = new int[result.size()];
		for (int i = 0; i < result.size(); i++) {
			arrayResult[i] = result.get(i);
		}
		return arrayResult;
	}

}
