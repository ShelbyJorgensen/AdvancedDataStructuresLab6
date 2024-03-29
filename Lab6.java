package Lab6;
import java.io.*;
import java.util.*;

public class Lab6
{
    /**
     *  Problem: Sort multi-digit integers (with n total digits) in O(n) time.
     *  (Technically, it is O(n * b) time. However, since our base b = 128 is constant, it is O(n).)
     */
    private static void problem(byte[][] arr) {   
    	// Find the value of n, of the largest array inside arr
        int n = 0;
        for(int i = 0; i < arr.length; i++) {
     	   if(arr[i].length > n) {
     		   n = arr[i].length;
     	   }
        }
        
        // Counting Sort Step 1: Get occurence count of each number
        int[] countSort = new int[n + 1];
        for(int i = 0; i < arr.length; i++) {
     	   int length = arr[i].length;
     	   countSort[length] += 1;
        }
        
        // Counting Sort Step 2: Find sorted index location based off count
        for(int i = 1; i < n + 1; i++) {
     	   countSort[i] += countSort[i - 1];
        }
        
        // Counting Sort step 3: Update array based on the sorted index
        byte[][] arrSorted = new byte[arr.length][];
        for(int j = arr.length - 1; j >= 0; j--) {
     	   arrSorted[countSort[arr[j].length] - 1] = arr[j];
     	   countSort[arr[j].length] = countSort[arr[j].length] - 1;
        }
        
        //Radix Sort
        byte[][] radixSorted = new byte[arr.length][];
        int count = 0;
        for(int i = 0; i < radixSorted.length; i++) {
     	   
     	   
     	   // Get the count of arrays that have the same size as the current array
     	   for(int j = 0; j < arrSorted.length; j++) {
     		   if(arrSorted[i].length == arrSorted[j].length) {
     			   count++;
     		   }
     	   }
     	   
     	   // Go through each element in this column of the array
     	   for(int j = 0; j < arrSorted[i].length; j++) {
     		  int[] radixCounts = new int[128];
     		   
     		   // Get the count of the numbers in the current groups column
     		   for(int k = i; k < count; k++) {
     			   radixCounts[arrSorted[k][j]] += 1;
     		   }
     		   
     		   // Update the counts to the index locations they should be placed at
         	   for(int k = 1; k < radixCounts.length; k++) {
         		   radixCounts[k] += radixCounts[k - 1];
         	   }
         	   
         	   // Place the value into the correct position based on the count array
         	   for(int k = count - 1; k >= i; k--) {
         		   radixSorted[radixCounts[arrSorted[k][j]] - 1 + i] = arrSorted[k];
         		   radixCounts[arrSorted[k][j]]--;
         	   }
         	   
         	   // Update the position of my counting sort array based off the results of radix sorting
         	   for(int k = count - 1; k >= i; k--){
         		   arrSorted[k] = radixSorted[k];
         	   }
     	   }
     	   
     	   // Update i to start at the start of the next group of arrays
     	   i = count - 1;
        }
        
        // Copy the value from radix sorted array into passed array
        for(int i = 0; i < arr.length; i++) {
     	   arr[i] = radixSorted[i];
        }

    }

    // ---------------------------------------------------------------------
    // All code below was provided as part of the class assignment
    // Do not change any of the code below!

    private static final int LabNo = 6;
    private static final Random rng = new Random(654321);

    private static boolean testProblem(byte[][] testCase)
    {
        byte[][] numbersCopy = new byte[testCase.length][];

        // Create copy.
        for (int i = 0; i < testCase.length; i++)
        {
            numbersCopy[i] = testCase[i].clone();
        }

        // Sort
        problem(testCase);
        Arrays.sort(numbersCopy, new numberComparator());

        // Compare if both equal
        if (testCase.length != numbersCopy.length)
        {
            return false;
        }

        for (int i = 0; i < testCase.length; i++)
        {
            if (testCase[i].length != numbersCopy[i].length)
            {
                return false;
            }

            for (int j = 0; j < testCase[i].length; j++)
            {
                if (testCase[i][j] != numbersCopy[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }

    // Very bad way of sorting.
    private static class numberComparator implements Comparator<byte[]>
    {
        @Override
        public int compare(byte[] n1, byte[] n2)
        {
            // Ensure equal length
            if (n1.length < n2.length)
            {
                byte[] tmp = new byte[n2.length];
                for (int i = 0; i < n1.length; i++)
                {
                    tmp[i] = n1[i];
                }
                n1 = tmp;
            }

            if (n1.length > n2.length)
            {
                byte[] tmp = new byte[n1.length];
                for (int i = 0; i < n2.length; i++)
                {
                    tmp[i] = n2[i];
                }
                n2 = tmp;
            }

            // Compare digit by digit.
            for (int i = n1.length - 1; i >=0; i--)
            {
                if (n1[i] < n2[i]) return -1;
                if (n1[i] > n2[i]) return 1;
            }

            return 0;
        }
    }

    public static void main(String args[])
    {
        System.out.println("CS 302 -- Lab " + LabNo);
        testProblems();
    }

    private static void testProblems()
    {
        int noOfLines = 10000;

        System.out.println("-- -- -- -- --");
        System.out.println(noOfLines + " test cases.");

        boolean passedAll = true;

        for (int i = 1; i <= noOfLines; i++)
        {
            byte[][] testCase =  createTestCase(i);

            boolean passed = false;
            boolean exce = false;

            try
            {
                passed = testProblem(testCase);
            }
            catch (Exception ex)
            {
                passed = false;
                exce = true;
            }

            if (!passed)
            {
                System.out.println("Test " + i + " failed!" + (exce ? " (Exception)" : ""));
                passedAll = false;

                break;
            }
        }

        if (passedAll)
        {
            System.out.println("All test passed.");
        }

    }

    private static byte[][] createTestCase(int testNo)
    {
        int maxSize = Math.min(100, testNo) + 5;
        int size = rng.nextInt(maxSize) + 5;

        byte[][] numbers = new byte[size][];

        for (int i = 0; i < size; i++)
        {
            int digits = rng.nextInt(maxSize) + 1;
            numbers[i] = new byte[digits];

            for (int j = 0; j < digits - 1; j++)
            {
                numbers[i][j] = (byte)rng.nextInt(128);
            }

            // Ensures that the most significant digit is not 0.
            numbers[i][digits - 1] = (byte)(rng.nextInt(127) + 1);
        }

        return numbers;
    }

}
