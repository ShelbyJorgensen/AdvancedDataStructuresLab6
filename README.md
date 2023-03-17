# AdvancedDataStructuresLab6
This is a project created for my data structures class at Central Washington University. Problem: Sort multi-digit integers (with n total digits) in O(n) time. Below is the provided project outline:

You are given an array of non-negative integers, where different integers may have different numbers
of digits, but the total number of digits over all the integers in the array is n. Show how to sort the
array in O(n) time1.
To implement this problem, we represent a single integer as array of bytes. Each byte represents a digit
(base 128). The byte with index 0 in the array represents the least significant byte. That is, if A has
length 3, A[0] = 6, A[1] = 7, and A[2] = 8, then A represents the number 6 · 1280 + 7 · 1281 + 8 · 1282.
