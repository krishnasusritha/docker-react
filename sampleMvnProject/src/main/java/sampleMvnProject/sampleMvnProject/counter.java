package sampleMvnProject.sampleMvnProject;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class counter {

	public Map<String, Integer> getCount(String string) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (string.isEmpty()) {
			throw new Exception("Empty string is given");
		} else {
			String[] arr = string.split(" ");
			for (String s : arr) {
				if (map.containsKey(s)) {
					map.put(s, map.get(s) + 1);
				} else {
					map.put(s, 1);
				}
			}
		}
		getSortedCountByKey(map);
		getSortedCountByKey(map);
		return map;

	}

//a -1 
//c-3
//b -2 
	private Map<String, Integer> getSortedCountByKey(Map<String, Integer> map) {
		Map<String, Integer> sorted = new HashMap<String, Integer>();
		String[] strings = (String[]) map.keySet().toArray();
		Arrays.sort(strings);
		for (String s : strings) {
			sorted.put(s, map.get(s));
		}

		return sorted;

	}

	private Map<String, Integer> getSortedCountByValue(final Map<String, Integer> map) {
		Map<String, Integer> sorted = new HashMap<String, Integer>();
		String[] strings = (String[]) map.keySet().toArray();
		Arrays.sort(strings, new Comparator<String>() {
			public int compare(String arg0, String arg1) {
				return map.get(arg0) - map.get(arg1);

			};
		});
		for (String s : strings) {
			sorted.put(s, map.get(s));
		}

		return sorted;

	}

	public int solution(String S) {
		String result = S.substring(0, 2);
		for (int i = 1; i < S.length() - 1; i++) {
			String small = S.substring(i, i + 2);
			if (result.compareTo(small) < 0) {
				result = small;
			}
		}
		return Integer.valueOf(result);

	}

	public static int solution1(String s, int[] c) {
		int result = 0;
		int visited = 0;
		for (int i = 0; i < s.length() - 1; i++) {
			if (s.substring(i, i + 1).equals(s.substring(i + 1, i + 2))) {
				if (c[i] < c[i + 1] && visited != i) {
					result += c[i];
					visited = i;
				} else {
					result += c[i + 1];
					visited = i + 1;
				}
			}
		}
		return result;
	}

//	s1 = "Wipro";
//	s2 = "Limited";
	public static String solution(String s1, String s2) {
		int common = 0;
		int count = 0;
		int count2 = 0;
		boolean matched = false;
		//Use StringBuilder for common characters
		for (int i = 0; i < s1.length(); i++) {
			char c = s1.charAt(i);
			for (int j = 0; j < s2.length(); j++) {
				if (c == s2.charAt(j)) {
					matched = true;
				}
			}
			if (matched) {
				common++;
				matched = false;
			} else {
				if (s1.charAt(i) >= 65 && s1.charAt(i) <= 90)
					count += s1.charAt(i) - 64;
				else if (s1.charAt(i) >= 97 && s1.charAt(i) <= 122)
					count += s1.charAt(i) - 96;
			}
		}
		for (int i = 0; i < s2.length(); i++) {
			char c = s2.charAt(i);
			for (int j = 0; j < s1.length(); j++) {
				if (c == s1.charAt(j)) {
					matched = true;
				}
			}
			if (!matched) {
				if (s2.charAt(i) >= 65 && s2.charAt(i) <= 90) {
					count2 += s2.charAt(i) - 64;
				} else if (s2.charAt(i) >= 97 && s2.charAt(i) <= 122) {
					count2 += s2.charAt(i) - 96;
				}
			}
			matched = false;
		}
		if (common == 0) {
			return String.valueOf(count) + String.valueOf(count2);
		}
		return String.valueOf(common) + String.valueOf(count) + String.valueOf(count2);
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 5, 3, 5, 1, 2, 3 };
		System.out.print(solution("object", "program"));
	}

}
