package com.mycompany.salsabil_alburani;
import java.util.Arrays;
import java.util.Random;

public class Salsabil_alburani {

   
    private static final int N = 100_000;

    public static void main(String[] args) {
      
        int[] randomData = generateRandomArray(N);
        int[] reverseData = generateReverseSortedArray(N);
        int[] partialData = generatePartiallySortedArray(N);
        int[] sortedData = generateFullySortedArray(N);

        int[][] test = {randomData, reverseData, partialData, sortedData};
        String[] algorithms = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Merge Sort", "Quick Sort"};

        long[][] results = new long[algorithms.length][test.length];

        for (int alg = 0; alg < algorithms.length; alg++) {
            for (int tc = 0; tc < test.length; tc++) {
                int[] testArray = Arrays.copyOf(test[tc], test[tc].length);
                long startTime = System.nanoTime();
                switch (alg) {
                    case 0:
                        selectionSort(testArray);
                        break;
                    case 1:
                        insertionSort(testArray);
                        break;
                    case 2:
                        bubbleSort(testArray);
                        break;
                    case 3:
                        mergeSort(testArray, 0, testArray.length - 1);
                        break;
                    case 4:
                        quickSort(testArray, 0, testArray.length - 1);
                        break;
                }
                long endTime = System.nanoTime();
                results[alg][tc] = (endTime - startTime) / 1000;
            }
        }
        printResultsTable(algorithms, results);
    }

    private static int[] generateRandomArray(int size) {
        Random rand = new Random(42); 
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(size * 10);
        }
        return arr;
    }

    private static int[] generateReverseSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        return arr;
    }

    private static int[] generatePartiallySortedArray(int size) {
        int[] arr = generateFullySortedArray(size);
        Random rand = new Random(42);
        int swaps = (int) (size * 0.10); 
        for (int i = 0; i < swaps; i++) {
            int idx1 = rand.nextInt(size);
            int idx2 = rand.nextInt(size);
            int temp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = temp;
        }
        return arr;
    }

    private static int[] generateFullySortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }


    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) {
            arr[k++] = L[i++];
        }
        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        int tempPivot = arr[mid];
        arr[mid] = arr[high];
        arr[high] = tempPivot;

        int pivot = arr[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    private static void printResultsTable(String[] algorithms, long[][] results) {
        String separator = "+------------------+----------------------+----------------------+----------------------+----------------------+";
        System.out.println(separator);
        System.out.printf("| %-16s | %-20s | %-20s | %-20s | %-20s |%n",
                "Algorithm", "Random (us)", "Reversely Sorted(us)", "Partially Sorted(us)", "Fully Sorted (us)");
        System.out.println(separator);

        for (int i = 0; i < algorithms.length; i++) {
            System.out.printf("| %-16s | %,17d us | %,17d us | %,17d us | %,17d us |%n",
                    algorithms[i],
                    results[i][0],
                    results[i][1],
                    results[i][2],
                    results[i][3]);
        }
        System.out.println(separator);
    }
}

