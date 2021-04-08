package sample;
import java.io.*; 
import java.util.*; 
  
class GFG { 
  
      // function to find the length of longest 
      // subarray having sum k 
      static int lenOfLongSubarr(int[] arr, int n, int k) 
      { 
             // HashMap to store (sum, index) tuples 
             HashMap<Integer, Integer> map = new HashMap<>(); 
             int sum = 0, maxLen = 0; 
  
             // traverse the given array 
             for (int i = 0; i < n; i++) { 
                  
                  // accumulate sum 
                  sum += arr[i]; 
                  
                  // when subarray starts from index '0' 
                  if (sum == k) 
                      maxLen = i + 1; 
  
                  // make an entry for 'sum' if it is 
                  // not present in 'map' 
                  if (!map.containsKey(sum)) { 
                      map.put(sum, i); 
                  } 
             
                  // check if 'sum-k' is present in 'map' 
                  // or not 
                  if (map.containsKey(sum - k)) { 
                        
                      // update maxLength 
                      if (maxLen < (i - map.get(sum - k))) {
                    	  System.out.println(i);
                    	  System.out.println(map.get(sum-k));
                          maxLen = i - map.get(sum - k); 
                          System.out.println(maxLen);
                      }
                  } 
             } 
               
             return maxLen;              
      } 
  
      // Driver code 
      public static void main(String args[]) 
      { 
             int[] arr = {5,5, 5, 2, 6,1, 1, 9}; 
             int n = arr.length; 
             int k = 15; 
             System.out.println("Length = " +  
                                lenOfLongSubarr(arr, n, k)); 
      } 
} 
  