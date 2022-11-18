package sampleMvnProject.sampleMvnProject;

import java.io.IOException;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {

	static int size = 7;
	int[] a = new int[size];

	public static final String SYNONYMS = "synonyms";
	public static final String DIFFERENT = "different";

	public static void main(String[] args) throws IOException {

		int[] arr = { 4, 2, 6, 3, 7, 1, 5 };

		bubbleSort(arr);

	}

	// BINARY SEARCH
	public int binarySearch() {

		int low = 0;
		int high = size - 1;
		int mid = 0;
		int number = 4;
		while (low <= high) {
			mid = (low + high) / 2;
			if (number == a[mid]) {
				return mid;
			} else if (number < a[mid]) {
				high = mid - 1;
			} else if (number > a[mid]) {
				low = mid + 1;
			}
		}
		return -1;
	}

	public int searchOrInsertSorted(int n) {
		for (int i = 0; i < size; i++) {
			if (a[i] == n) {
				return i;
			} else if (a[i] > n) {
				return i - 1;
			}
		}
		return size;
	}

	// Bubble sort
	public static void bubbleSort(int[] a) {
		int temp;
		boolean swap;
		for (int i = 0; i < size - 1; i++) {
			swap = false;
			for (int j = 0; j < size - i - 1; j++) {
				if (a[j] > a[j + 1]) {
					temp = a[j];
					a[j] = a[i];
					a[i] = temp;
					swap = true;
				}
			}
			if (swap = false) {
				break;
			}
		}
	}

	// Selection sort
	public void selectionSort() {
		for (int i = 0; i < a.length - 1; i++) {
			int minIndex = i;
			for (int j = i + 1; j < a.length - i - 1; j++) {
				if (a[j] < a[minIndex]) {
					minIndex = j;// searching for lowest index
				}
			}
			int smallerNumber = a[minIndex];
			a[minIndex] = a[i];
			a[i] = smallerNumber;
		}
	}

	// Insertion sort
	public static void insertionSort(int[] arr) {
		int j = 0;
		for (int i = 1; i < arr.length; i++) {
			int temp = arr[i];
			j = i - 1;
			while (j >= 0 && temp < arr[j]) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = temp;
		}
		printArr(arr);
	}

	// merge Sorted Arrays
	public static void mergeSortedArrays(int[] x, int[] y) {
		int length = x.length + y.length;
		int i = 0, j = 0, k = 0;
		int[] result = new int[length];
		while (i < x.length && j < y.length) {
			if (x[i] < y[j]) {
				result[k] = x[i];
				i++;
			} else {
				result[k] = y[j];
				j++;
			}
			k++;
		}
		while (i < x.length) {
			result[k] = x[i];
			k++;
		}
		while (j < y.length) {
			result[k] = y[j];
			k++;
		}

		printArr(result);
	}

	// Merge sort
	public static void sort(int[] x, int[] temp, int low, int high) {

		if (low < high) {
			int mid = (low + high) / 2;
			sort(x, temp, low, mid);
			sort(x, temp, mid + 1, high);
			merge(x, temp, low, mid, high);
		}

	}

	// merge Sorted Arrays
	public static void merge(int[] x, int[] temp, int low, int mid, int high) {
		temp = Arrays.copyOf(x, x.length);
		int i = low, j = mid + 1, k = low;
		while (i <= mid && j <= high) {
			if (temp[i] <= temp[j]) {
				x[k] = temp[i];
				i++;
			} else {
				x[k] = temp[j];
				j++;
			}
			k++;
		}
		while (i <= mid) {
			x[k] = temp[i];
			k++;
		}
		while (j <= high) {
			x[k] = temp[j];
			k++;
		}

		printArr(x);
	}

	// Quick sort
	public static void quickSort(int[] a, int low, int high) {
		if (low <= high) {
			int temp = partition(a, low, high);
			quickSort(a, low, temp - 1);
			quickSort(a, temp + 1, high);
		}
	}

	private static int partition(int[] a, int low, int high) {
		int i = 0;
		int j = 0;
		int pivot = a[high];
		while (i <= high) {
			if (a[i] <= pivot) {
				int temp = a[i];
				a[i] = a[j];
				a[j] = temp;
				j++;
			}
			i++;
		}
		return j - 1;
	}

	public static void sort012(int[] x) {
		int i = 0;
		int j = 0;
		int k = x.length - 1;
		while (i <= k) {
			if (x[i] == 0) {
				swap(x, i, j);
				i++;
				j++;
			} else if (x[i] == 1) {
				i++;
			} else if (x[i] == 2) {
				swap(x, i, k);
				k--;
			}
		}
	}

	public void sortSquares(int[] a) {
		int n = a.length;
		int i = 0;
		int j = n - 1;
		int[] result = new int[n];
		for (int k = n - 1; k > 0; k--) {
			if (Math.abs(a[i]) > Math.abs(a[j])) {
				result[k] = a[i] * a[i];
				i++;
			} else {
				result[k] = a[j] * a[j];
				j--;
			}
		}
	}

	private static void swap(int[] x, int i, int k) {
		int temp = x[i];
		x[i] = x[k];
		x[k] = temp;

	}

	private static void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
}
