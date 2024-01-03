// Name: William Baldwin
// Computing ID: vvp4cx@virginia.edu
// Homework Name: HW13 - Sorting
// Resources used: None
@SuppressWarnings("unchecked")

public class SortingAlgorithms {

	/*
	 * Swaps the elements and indices i and j in list
	 * */
	private static <T> void swap(T[] list, int i, int j) {
		if (i < 0 || i >= list.length)
			return;
		if (j < 0 || j >= list.length)
			return;
		T temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}
	
	// ####################
	// ## INSERTION SORT ## ----------------------------------------------------------------------
	// ####################
	// ## IMPORTANT: the code we've given you below has a small bug!
	// ##   You will need to look at this code and/or test, and fix the bug.
	// ####################
	/**
	 * Usually a slow sorting algorithm. Insertion sort. 
	 * @param list - An array of items
	 */
	public static <T extends Comparable<T>> void insertionSort(T[] list) {
		for (int i = 1; i < list.length; i++) {
			T val = list[i];
			int j = i - 1;
			while (j >= 0 && val.compareTo(list[j]) < 0) {
				list[j+1] = list[j];
				j--;
			}
			list[j+1] = val;
		}
	}

	//Better insertion sort for hybrid methods
	private static <T extends Comparable<T>> void insertionSortForSubArray(T[] list, int from, int to) {
		for (int i = from + 1; i <= to; i++) {
			T key = list[i];
			int j = i - 1;
			while (j >= from && list[j].compareTo(key) > 0) {
				list[j + 1] = list[j];
				j = j - 1;
			}
			list[j + 1] = key;
		}
	}

	//=================================================================================


	// ################
	// ## MERGE SORT ## ----------------------------------------------------------------------
	// ################	
	/**
	 * Fully recursive Merge sort and associated helper method.
	 * The second method below provides the portion of the array
	 * (i.e., index i to j inclusive) that we want to sort.
	 * 
	 * @param list - An array of items
	 */
	public static<T extends Comparable<T>> void mergeSort(T[] list) {
		mergeSort(list, 0, list.length - 1);
	}
	public static<T extends Comparable<T>> void mergeSort(T[] list, int i, int j) {
		//TODO: write the body of this method
		if (j > i) {
			int middle = i + (j - i)/2;
			mergeSort(list, i, middle);
			mergeSort(list, 1 + middle, j);
			merge(list, i, middle, j);
		}
	}
	
	/**
	 * Merge method for Merge Sort algorithm.
	 * Your mergeSort algorithm will call this method as appropriate to do the merging.
	 * @param list - An array of items
	 * @param i - lower bound index
	 * @param mid - middle index
	 * @param j - upper bound index 
	 */
	public static<T extends Comparable<T>> void merge(T[] list, int i, int mid, int j) {
		//TODO: write the body of this method
		int size1 = mid - i +1;
		int size2 = j - mid;

		T [] temp1 = (T[]) new Comparable[size1];
		T [] temp2 = (T[]) new Comparable[size2];

		for (int k = 0; k < size1; k++) {temp1[k] = list[i + k];}
		for (int c = 0; c < size2; c++) {temp2[c] = list[mid + 1 + c];}

		int b = 0;
		int c = 0;
		int d = i;
		while (b < size1 && c < size2) {
			if (temp1[b].compareTo(temp2[c]) <= 0) {
				list[d] = temp1[b];
				b++;
			} else {
				list[d] = temp2[c];
				c++;
			}
			d++;
		}

		while (b < size1) {
			list[d] = temp1[b];
			d++;
			b++;
		}

		while (c < size2) {
			list[d] = temp2[c];
			c++;
			d++;
		}
		
		//Reminder: when using a generic type, to create a new array to hold items of type T,
		//  you do something like the following:

	}

	//=================================================================================

	// #######################
	// ## HYBRID MERGE SORT ## ----------------------------------------------------------------------
	// #######################
	/**
	 * Hybrid recursive Merge sort and associated helper method.
	 * The second method below provides the portion of the array
	 * (i.e., index i to j inclusive) that we want to sort.
	 * For this implementation, when the size of the portion of the array
	 * to be sorted is less than 100 items, call insertionSort method to
	 * sort that chunk of the array.
	 *
	 *
	 * @param list - An array of items
	 */
	public static<T extends Comparable<T>> void mergeSortHybrid(T[] list) {
		mergeSortHybrid(list, 0, list.length - 1);
	}
	public static<T extends Comparable<T>> void mergeSortHybrid(T[] list, int i, int j) {
		//TODO: write the body of this method
		// When the size of array to be sorted is < 100, call insertionSort rather than recurse
		int max = 100;
		if (j - i <= max) {
			insertionSortForSubArray(list, i, j);
		} else {
			if (i < j) {
				int middle = i + (j-i)/2;
				mergeSortHybrid(list, i, middle);
				mergeSortHybrid(list, 1 + middle, j);
				merge(list, i , middle, j);
			}
		}
	}

	//=================================================================================
	
	// ###############
	// ## QUICKSORT ## ----------------------------------------------------------------------
	// ###############	
	/**
	 * Fully recursive Quicksort and associated helper method.
	 * The second method below provides the portion of the array
	 * (i.e., index i to j inclusive) that we want to sort.
	 * >>> Use any partition scheme that you like. 
	 * 
	 * @param list - An array of items
	 */
	public static<T extends Comparable<T>> void quickSort(T[] list) {
		quickSort(list, 0, list.length - 1);
	}
	public static<T extends Comparable<T>> void quickSort(T[] list, int i, int j) {
		//TODO: write the body of this method
		if (i < j) {
			int pivot = partition(list, i, j);
			quickSort(list, i, pivot - 1);
			quickSort(list, pivot + 1, j);
		}
	}
	
	/**
	 * Partition method for Quicksort - Use any valid partition algorithm that you like.
	 * Your quickSort algorithm will call this method as appropriate to do the partitioning.
	 * @param list - An array of items
	 * @param i - lower bound
	 * @param j - upper bound
	 */
	public static<T extends Comparable<T>> int partition(T[] list, int i, int j) {	
		//TODO: write the body of this method
		T pivot = list[j];
		int bottom = i - 1;
		for (int k = i; k < j; k++) {
			if (list[k].compareTo(pivot) <= 0) {
				bottom++;
				swap(list, bottom, k);
			}
		}
		swap(list, bottom + 1, j);
		return bottom + 1; // be sure to return the right value and not 0
	}
	
	//=================================================================================

    // ######################
	// ## HYBRID QUICKSORT ## ----------------------------------------------------------------------
	// ######################
	/**
	 * Hybrid Quicksort and associated helper method.
	 * The second method below provides the portion of the array
	 * (i.e., index i to j inclusive) that we want to sort.
	 * >>> Use any partition scheme that you like.
	 * For this implementation, when the size of the portion of the array
	 * to be sorted is less than 100 items, call insertionSort method to
	 * sort that chunk of the array.
	 *
	 * @param list - An array of items
	 */
	public static<T extends Comparable<T>> void quickSortHybrid(T[] list) {
		quickSortHybrid(list, 0, list.length - 1);
	}
	public static<T extends Comparable<T>> void quickSortHybrid(T[] list, int i, int j) {
		//TODO: write the body of this method
		// When the size of array to be sorted is < 100, call insertionSort rather than recurse
		int max = 100;
		if (j - i <= max) {
			insertionSortForSubArray(list, i, j);
		} else {
			if (i < j) {
				int pivot = partition(list, i, j);
				quickSortHybrid(list, i, pivot - 1);
				quickSortHybrid(list, pivot + 1, j);
			}
		}
	}

	/**
	 * Partition method for Quicksort - Use any valid partition algorithm that you like.
	 * Your quickSort algorithm will call this method as appropriate to do the partitioning.
	 * @param list - An array of items
	 * @param i - lower bound
	 * @param j - upper bound
	 */
	public static<T extends Comparable<T>> int partitionHybrid(T[] list, int i, int j) {
		//TODO: write the body of this method
		int middle = i + (j - i)/2;
		T pivot = list[middle];

		swap(list,middle,j);
		int a = i - 1;
		for(int k = i; k < j; k++) {
			if (list[k].compareTo(pivot) <= 0) {
				a++;
				swap(list,a,k);
			}
		}
		swap(list,a+1, j);
		return a + 1;
		// be sure to return the right value and not 0
	}

}