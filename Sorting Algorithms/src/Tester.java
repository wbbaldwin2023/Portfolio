// Name: William Baldwin
// Computing ID: vvp4cx@virginia.edu
// Homework Name: HW13 - Sorting
// Resources used: None
// Don't forget, to print an array (say for debugging, just convert it to a string first:
//    System.out.println(Arrays.toString(list));


import java.util.Arrays;
import java.util.Scanner;

public class Tester {

	public static void main(String[] args) {

		/* Read in size and which algorithm to run */
		Scanner in = new Scanner(System.in);
		System.out.print("Enter size:  ");
		int size = in.nextInt();
		System.out.print("Enter sort:  ");
		int whichSort = in.nextInt();
		in.close();

		/* Make an array to sort. Fill with random numbers */
		final Integer[] list = new Integer[size];
		for (int i = 0; i < size; i++)
			list[i] = (int) (Math.random() * 3 * size);

		/* Make copies to sort */
		Integer[] mergeSortList = list.clone();
		Integer[] mergeHybridList = list.clone();
		Integer[] quickSortList = list.clone();
		Integer[] quickHybridList = list.clone();
		Integer[] insertionSortList = list.clone();

		if (whichSort == 1) {		// insertion sort
			System.out.print("Sorting using insertion sort...");
//			System.out.println("\ninput: " + Arrays.toString(insertionSortList));
			// start timing
			long startTime = System.currentTimeMillis();
			SortingAlgorithms.insertionSort(insertionSortList);
			long timing = System.currentTimeMillis()-startTime;
//			System.out.println("sorted:" + Arrays.toString(insertionSortList));
			System.out.println("Done...checking if sorted correctly...");
			checkSorted(list, insertionSortList);
			System.out.println("It took " + timing + " to complete the sort");
			System.out.println("DONE");
		}

		else if (whichSort == 2) {   // recursive merge
			System.out.print("Sorting using recursive mergesort...");
			// start timing
			long startTime = System.currentTimeMillis();
			SortingAlgorithms.mergeSort(mergeSortList);
			long timing = System.currentTimeMillis()-startTime;
			System.out.println("Done...checking if sorted correctly...");
			checkSorted(list, mergeSortList);
			System.out.println("It took " + timing + " ms to complete the sort");
			System.out.println("DONE");
		}

		else if (whichSort == 3) {  // hybrid merge
			System.out.print("Sorting using hybrid mergesort...");
			// start timing
			long startTime = System.currentTimeMillis();
			SortingAlgorithms.mergeSortHybrid(mergeHybridList);
			long timing = System.currentTimeMillis()-startTime;
			System.out.println("Done...checking if sorted correctly...");
			checkSorted(list, mergeHybridList);
			System.out.println("It took " + timing + " ms to complete the sort");
			System.out.println("DONE");
		}
		else if (whichSort == 4) {  // recursive quick
			System.out.print("Sorting using recursive quicksort...");
			// start timing
			long startTime = System.currentTimeMillis();
			SortingAlgorithms.quickSort(quickSortList);
			long timing = System.currentTimeMillis()-startTime;
			System.out.println("Done...checking if sorted correctly...");
			checkSorted(list, quickSortList);
			System.out.println("It took " + timing + " ms to complete the sort");
			System.out.println("DONE");
		}
		else if (whichSort == 5) { // hybrid quick
			System.out.print("Sorting using hybrid quicksort...");
			// start timing
			long startTime = System.currentTimeMillis();
			SortingAlgorithms.quickSortHybrid(quickHybridList);
			long timing = System.currentTimeMillis()-startTime;
			System.out.println("Done...checking if sorted correctly...");
			checkSorted(list, quickHybridList);
			System.out.println("It took " + timing + " ms to complete the sort");
			System.out.println("DONE");
		}
		else
			System.out.println("invalid selection");
	}

	public static <T extends Comparable<T>> boolean checkSorted(T[] orig, T[] sorted) {
		/* Make sure size is the same */
		if (orig.length != sorted.length) {
			System.out.println("ERROR...original list and sorted list have different lengths...");
			return false;
		}

		/* Make sure new array is sorted */
		for (int i = 1; i < sorted.length; i++) {
			if (sorted[i].compareTo(sorted[i - 1]) < 0) {
				System.out.println("ERROR...the sorted list does not appear to be correctly sorted...");
				return false;
			}
		}
		
		// Make sure the same elements are in each array using binary search.
		// This works because we already know two arrays are same length and 
		//    the list named sorted is correctly sorted.
		boolean ok = true;
		for (int i = 0; i < orig.length; i++) {
			int inSortedIndex = Arrays.binarySearch(sorted, orig[i]);
			if (inSortedIndex < 0) {
				ok = false;
				break;
			}
		}
		if (!ok ) {
			System.out.println(
					"ERROR...The sorted list does not contain the same elements that the original list does...");
			return false;
		}
		
		return true;
	}
}
