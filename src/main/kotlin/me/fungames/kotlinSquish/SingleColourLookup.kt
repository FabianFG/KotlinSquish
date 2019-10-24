package me.fungames.kotlinSquish

val LOOKUP_6_4 = arrayOf<Array<IntArray>>(
    arrayOf<IntArray>(intArrayOf(0, 0, 0), intArrayOf(0, 0, 0), intArrayOf(0, 0, 0), intArrayOf(0, 0, 0)),
    arrayOf<IntArray>(intArrayOf(0, 0, 1), intArrayOf(0, 0, 1), intArrayOf(0, 1, 0), intArrayOf(1, 0, 0)),
    arrayOf<IntArray>(intArrayOf(0, 0, 2), intArrayOf(0, 0, 2), intArrayOf(0, 2, 0), intArrayOf(0, 1, 0)),
    arrayOf<IntArray>(intArrayOf(1, 0, 1), intArrayOf(0, 1, 1), intArrayOf(0, 3, 1), intArrayOf(1, 1, 1)),
    arrayOf<IntArray>(intArrayOf(1, 0, 0), intArrayOf(0, 1, 0), intArrayOf(0, 3, 0), intArrayOf(1, 1, 0)),
    arrayOf<IntArray>(intArrayOf(1, 0, 1), intArrayOf(0, 1, 1), intArrayOf(0, 4, 0), intArrayOf(0, 2, 0)),
    arrayOf<IntArray>(intArrayOf(1, 0, 2), intArrayOf(0, 1, 2), intArrayOf(0, 5, 0), intArrayOf(1, 2, 0)),
    arrayOf<IntArray>(intArrayOf(2, 0, 1), intArrayOf(0, 2, 1), intArrayOf(0, 6, 1), intArrayOf(0, 3, 1)),
    arrayOf<IntArray>(intArrayOf(2, 0, 0), intArrayOf(0, 2, 0), intArrayOf(0, 6, 0), intArrayOf(0, 3, 0)),
    arrayOf<IntArray>(intArrayOf(2, 0, 1), intArrayOf(0, 2, 1), intArrayOf(0, 7, 0), intArrayOf(1, 3, 0)),
    arrayOf<IntArray>(intArrayOf(2, 0, 2), intArrayOf(0, 2, 2), intArrayOf(0, 8, 0), intArrayOf(0, 4, 0)),
    arrayOf<IntArray>(intArrayOf(3, 0, 1), intArrayOf(0, 3, 1), intArrayOf(0, 9, 1), intArrayOf(1, 4, 1)),
    arrayOf<IntArray>(intArrayOf(3, 0, 0), intArrayOf(0, 3, 0), intArrayOf(0, 9, 0), intArrayOf(1, 4, 0)),
    arrayOf<IntArray>(intArrayOf(3, 0, 1), intArrayOf(0, 3, 1), intArrayOf(0, 10, 0), intArrayOf(0, 5, 0)),
    arrayOf<IntArray>(intArrayOf(3, 0, 2), intArrayOf(0, 3, 2), intArrayOf(0, 11, 0), intArrayOf(1, 5, 0)),
    arrayOf<IntArray>(intArrayOf(4, 0, 1), intArrayOf(0, 4, 1), intArrayOf(0, 12, 1), intArrayOf(0, 6, 1)),
    arrayOf<IntArray>(intArrayOf(4, 0, 0), intArrayOf(0, 4, 0), intArrayOf(0, 12, 0), intArrayOf(0, 6, 0)),
    arrayOf<IntArray>(intArrayOf(4, 0, 1), intArrayOf(0, 4, 1), intArrayOf(0, 13, 0), intArrayOf(1, 6, 0)),
    arrayOf<IntArray>(intArrayOf(4, 0, 2), intArrayOf(0, 4, 2), intArrayOf(0, 14, 0), intArrayOf(0, 7, 0)),
    arrayOf<IntArray>(intArrayOf(5, 0, 1), intArrayOf(0, 5, 1), intArrayOf(0, 15, 1), intArrayOf(1, 7, 1)),
    arrayOf<IntArray>(intArrayOf(5, 0, 0), intArrayOf(0, 5, 0), intArrayOf(0, 15, 0), intArrayOf(1, 7, 0)),
    arrayOf<IntArray>(intArrayOf(5, 0, 1), intArrayOf(0, 5, 1), intArrayOf(0, 16, 0), intArrayOf(0, 8, 0)),
    arrayOf<IntArray>(intArrayOf(5, 0, 2), intArrayOf(0, 5, 2), intArrayOf(1, 15, 0), intArrayOf(1, 8, 0)),
    arrayOf<IntArray>(intArrayOf(6, 0, 1), intArrayOf(0, 6, 1), intArrayOf(0, 17, 0), intArrayOf(17, 0, 0)),
    arrayOf<IntArray>(intArrayOf(6, 0, 0), intArrayOf(0, 6, 0), intArrayOf(0, 18, 0), intArrayOf(0, 9, 0)),
    arrayOf<IntArray>(intArrayOf(6, 0, 1), intArrayOf(0, 6, 1), intArrayOf(0, 19, 0), intArrayOf(1, 9, 0)),
    arrayOf<IntArray>(intArrayOf(6, 0, 2), intArrayOf(0, 6, 2), intArrayOf(3, 14, 0), intArrayOf(0, 10, 0)),
    arrayOf<IntArray>(intArrayOf(7, 0, 1), intArrayOf(0, 7, 1), intArrayOf(0, 20, 0), intArrayOf(16, 2, 0)),
    arrayOf<IntArray>(intArrayOf(7, 0, 0), intArrayOf(0, 7, 0), intArrayOf(0, 21, 0), intArrayOf(1, 10, 0)),
    arrayOf<IntArray>(intArrayOf(7, 0, 1), intArrayOf(0, 7, 1), intArrayOf(0, 22, 0), intArrayOf(0, 11, 0)),
    arrayOf<IntArray>(intArrayOf(7, 0, 2), intArrayOf(0, 7, 2), intArrayOf(4, 15, 0), intArrayOf(1, 11, 0)),
    arrayOf<IntArray>(intArrayOf(8, 0, 1), intArrayOf(0, 8, 1), intArrayOf(0, 23, 0), intArrayOf(17, 3, 0)),
    arrayOf<IntArray>(intArrayOf(8, 0, 0), intArrayOf(0, 8, 0), intArrayOf(0, 24, 0), intArrayOf(0, 12, 0)),
    arrayOf<IntArray>(intArrayOf(8, 0, 1), intArrayOf(0, 8, 1), intArrayOf(0, 25, 0), intArrayOf(1, 12, 0)),
    arrayOf<IntArray>(intArrayOf(8, 0, 2), intArrayOf(0, 8, 2), intArrayOf(6, 14, 0), intArrayOf(0, 13, 0)),
    arrayOf<IntArray>(intArrayOf(9, 0, 1), intArrayOf(0, 9, 1), intArrayOf(0, 26, 0), intArrayOf(16, 5, 0)),
    arrayOf<IntArray>(intArrayOf(9, 0, 0), intArrayOf(0, 9, 0), intArrayOf(0, 27, 0), intArrayOf(1, 13, 0)),
    arrayOf<IntArray>(intArrayOf(9, 0, 1), intArrayOf(0, 9, 1), intArrayOf(0, 28, 0), intArrayOf(0, 14, 0)),
    arrayOf<IntArray>(intArrayOf(9, 0, 2), intArrayOf(0, 9, 2), intArrayOf(7, 15, 0), intArrayOf(1, 14, 0)),
    arrayOf<IntArray>(intArrayOf(10, 0, 1), intArrayOf(0, 10, 1), intArrayOf(0, 29, 0), intArrayOf(17, 6, 0)),
    arrayOf<IntArray>(intArrayOf(10, 0, 0), intArrayOf(0, 10, 0), intArrayOf(0, 30, 0), intArrayOf(0, 15, 0)),
    arrayOf<IntArray>(intArrayOf(10, 0, 1), intArrayOf(0, 10, 1), intArrayOf(0, 31, 0), intArrayOf(1, 15, 0)),
    arrayOf<IntArray>(intArrayOf(10, 0, 2), intArrayOf(0, 10, 2), intArrayOf(9, 14, 0), intArrayOf(2, 15, 0)),
    arrayOf<IntArray>(intArrayOf(11, 0, 1), intArrayOf(0, 11, 1), intArrayOf(0, 32, 0), intArrayOf(0, 16, 0)),
    arrayOf<IntArray>(intArrayOf(11, 0, 0), intArrayOf(0, 11, 0), intArrayOf(0, 33, 0), intArrayOf(1, 16, 0)),
    arrayOf<IntArray>(intArrayOf(11, 0, 1), intArrayOf(0, 11, 1), intArrayOf(2, 30, 0), intArrayOf(4, 15, 0)),
    arrayOf<IntArray>(intArrayOf(11, 0, 2), intArrayOf(0, 11, 2), intArrayOf(0, 34, 0), intArrayOf(0, 17, 0)),
    arrayOf<IntArray>(intArrayOf(12, 0, 1), intArrayOf(0, 12, 1), intArrayOf(0, 35, 0), intArrayOf(1, 17, 0)),
    arrayOf<IntArray>(intArrayOf(12, 0, 0), intArrayOf(0, 12, 0), intArrayOf(0, 36, 0), intArrayOf(0, 18, 0)),
    arrayOf<IntArray>(intArrayOf(12, 0, 1), intArrayOf(0, 12, 1), intArrayOf(3, 31, 0), intArrayOf(7, 15, 0)),
    arrayOf<IntArray>(intArrayOf(12, 0, 2), intArrayOf(0, 12, 2), intArrayOf(0, 37, 0), intArrayOf(1, 18, 0)),
    arrayOf<IntArray>(intArrayOf(13, 0, 1), intArrayOf(0, 13, 1), intArrayOf(0, 38, 0), intArrayOf(0, 19, 0)),
    arrayOf<IntArray>(intArrayOf(13, 0, 0), intArrayOf(0, 13, 0), intArrayOf(0, 39, 0), intArrayOf(1, 19, 0)),
    arrayOf<IntArray>(intArrayOf(13, 0, 1), intArrayOf(0, 13, 1), intArrayOf(5, 30, 0), intArrayOf(10, 15, 0)),
    arrayOf<IntArray>(intArrayOf(13, 0, 2), intArrayOf(0, 13, 2), intArrayOf(0, 40, 0), intArrayOf(0, 20, 0)),
    arrayOf<IntArray>(intArrayOf(14, 0, 1), intArrayOf(0, 14, 1), intArrayOf(0, 41, 0), intArrayOf(1, 20, 0)),
    arrayOf<IntArray>(intArrayOf(14, 0, 0), intArrayOf(0, 14, 0), intArrayOf(0, 42, 0), intArrayOf(0, 21, 0)),
    arrayOf<IntArray>(intArrayOf(14, 0, 1), intArrayOf(0, 14, 1), intArrayOf(6, 31, 0), intArrayOf(13, 15, 0)),
    arrayOf<IntArray>(intArrayOf(14, 0, 2), intArrayOf(0, 14, 2), intArrayOf(0, 43, 0), intArrayOf(1, 21, 0)),
    arrayOf<IntArray>(intArrayOf(15, 0, 1), intArrayOf(0, 15, 1), intArrayOf(0, 44, 0), intArrayOf(0, 22, 0)),
    arrayOf<IntArray>(intArrayOf(15, 0, 0), intArrayOf(0, 15, 0), intArrayOf(0, 45, 0), intArrayOf(1, 22, 0)),
    arrayOf<IntArray>(intArrayOf(15, 0, 1), intArrayOf(0, 15, 1), intArrayOf(8, 30, 0), intArrayOf(16, 15, 0)),
    arrayOf<IntArray>(intArrayOf(15, 0, 2), intArrayOf(0, 15, 2), intArrayOf(0, 46, 0), intArrayOf(0, 23, 0)),
    arrayOf<IntArray>(intArrayOf(16, 0, 2), intArrayOf(0, 16, 2), intArrayOf(0, 47, 0), intArrayOf(1, 23, 0)),
    arrayOf<IntArray>(intArrayOf(16, 0, 1), intArrayOf(0, 16, 1), intArrayOf(1, 46, 0), intArrayOf(0, 24, 0)),
    arrayOf<IntArray>(intArrayOf(16, 0, 0), intArrayOf(0, 16, 0), intArrayOf(0, 48, 0), intArrayOf(16, 16, 0)),
    arrayOf<IntArray>(intArrayOf(16, 0, 1), intArrayOf(0, 16, 1), intArrayOf(0, 49, 0), intArrayOf(1, 24, 0)),
    arrayOf<IntArray>(intArrayOf(16, 0, 2), intArrayOf(0, 16, 2), intArrayOf(0, 50, 0), intArrayOf(0, 25, 0)),
    arrayOf<IntArray>(intArrayOf(17, 0, 1), intArrayOf(0, 17, 1), intArrayOf(2, 47, 0), intArrayOf(1, 25, 0)),
    arrayOf<IntArray>(intArrayOf(17, 0, 0), intArrayOf(0, 17, 0), intArrayOf(0, 51, 0), intArrayOf(17, 17, 0)),
    arrayOf<IntArray>(intArrayOf(17, 0, 1), intArrayOf(0, 17, 1), intArrayOf(0, 52, 0), intArrayOf(0, 26, 0)),
    arrayOf<IntArray>(intArrayOf(17, 0, 2), intArrayOf(0, 17, 2), intArrayOf(0, 53, 0), intArrayOf(1, 26, 0)),
    arrayOf<IntArray>(intArrayOf(18, 0, 1), intArrayOf(0, 18, 1), intArrayOf(4, 46, 0), intArrayOf(0, 27, 0)),
    arrayOf<IntArray>(intArrayOf(18, 0, 0), intArrayOf(0, 18, 0), intArrayOf(0, 54, 0), intArrayOf(16, 19, 0)),
    arrayOf<IntArray>(intArrayOf(18, 0, 1), intArrayOf(0, 18, 1), intArrayOf(0, 55, 0), intArrayOf(1, 27, 0)),
    arrayOf<IntArray>(intArrayOf(18, 0, 2), intArrayOf(0, 18, 2), intArrayOf(0, 56, 0), intArrayOf(0, 28, 0)),
    arrayOf<IntArray>(intArrayOf(19, 0, 1), intArrayOf(0, 19, 1), intArrayOf(5, 47, 0), intArrayOf(1, 28, 0)),
    arrayOf<IntArray>(intArrayOf(19, 0, 0), intArrayOf(0, 19, 0), intArrayOf(0, 57, 0), intArrayOf(17, 20, 0)),
    arrayOf<IntArray>(intArrayOf(19, 0, 1), intArrayOf(0, 19, 1), intArrayOf(0, 58, 0), intArrayOf(0, 29, 0)),
    arrayOf<IntArray>(intArrayOf(19, 0, 2), intArrayOf(0, 19, 2), intArrayOf(0, 59, 0), intArrayOf(1, 29, 0)),
    arrayOf<IntArray>(intArrayOf(20, 0, 1), intArrayOf(0, 20, 1), intArrayOf(7, 46, 0), intArrayOf(0, 30, 0)),
    arrayOf<IntArray>(intArrayOf(20, 0, 0), intArrayOf(0, 20, 0), intArrayOf(0, 60, 0), intArrayOf(16, 22, 0)),
    arrayOf<IntArray>(intArrayOf(20, 0, 1), intArrayOf(0, 20, 1), intArrayOf(0, 61, 0), intArrayOf(1, 30, 0)),
    arrayOf<IntArray>(intArrayOf(20, 0, 2), intArrayOf(0, 20, 2), intArrayOf(0, 62, 0), intArrayOf(0, 31, 0)),
    arrayOf<IntArray>(intArrayOf(21, 0, 1), intArrayOf(0, 21, 1), intArrayOf(8, 47, 0), intArrayOf(1, 31, 0)),
    arrayOf<IntArray>(intArrayOf(21, 0, 0), intArrayOf(0, 21, 0), intArrayOf(0, 63, 0), intArrayOf(17, 23, 0)),
    arrayOf<IntArray>(intArrayOf(21, 0, 1), intArrayOf(0, 21, 1), intArrayOf(1, 62, 0), intArrayOf(0, 32, 0)),
    arrayOf<IntArray>(intArrayOf(21, 0, 2), intArrayOf(0, 21, 2), intArrayOf(1, 63, 0), intArrayOf(3, 31, 0)),
    arrayOf<IntArray>(intArrayOf(22, 0, 1), intArrayOf(0, 22, 1), intArrayOf(10, 46, 0), intArrayOf(1, 32, 0)),
    arrayOf<IntArray>(intArrayOf(22, 0, 0), intArrayOf(0, 22, 0), intArrayOf(2, 62, 0), intArrayOf(0, 33, 0)),
    arrayOf<IntArray>(intArrayOf(22, 0, 1), intArrayOf(0, 22, 1), intArrayOf(2, 63, 0), intArrayOf(1, 33, 0)),
    arrayOf<IntArray>(intArrayOf(22, 0, 2), intArrayOf(0, 22, 2), intArrayOf(3, 62, 0), intArrayOf(6, 31, 0)),
    arrayOf<IntArray>(intArrayOf(23, 0, 1), intArrayOf(0, 23, 1), intArrayOf(11, 47, 0), intArrayOf(0, 34, 0)),
    arrayOf<IntArray>(intArrayOf(23, 0, 0), intArrayOf(0, 23, 0), intArrayOf(3, 63, 0), intArrayOf(1, 34, 0)),
    arrayOf<IntArray>(intArrayOf(23, 0, 1), intArrayOf(0, 23, 1), intArrayOf(4, 62, 0), intArrayOf(0, 35, 0)),
    arrayOf<IntArray>(intArrayOf(23, 0, 2), intArrayOf(0, 23, 2), intArrayOf(4, 63, 0), intArrayOf(9, 31, 0)),
    arrayOf<IntArray>(intArrayOf(24, 0, 1), intArrayOf(0, 24, 1), intArrayOf(13, 46, 0), intArrayOf(1, 35, 0)),
    arrayOf<IntArray>(intArrayOf(24, 0, 0), intArrayOf(0, 24, 0), intArrayOf(5, 62, 0), intArrayOf(0, 36, 0)),
    arrayOf<IntArray>(intArrayOf(24, 0, 1), intArrayOf(0, 24, 1), intArrayOf(5, 63, 0), intArrayOf(1, 36, 0)),
    arrayOf<IntArray>(intArrayOf(24, 0, 2), intArrayOf(0, 24, 2), intArrayOf(6, 62, 0), intArrayOf(12, 31, 0)),
    arrayOf<IntArray>(intArrayOf(25, 0, 1), intArrayOf(0, 25, 1), intArrayOf(14, 47, 0), intArrayOf(0, 37, 0)),
    arrayOf<IntArray>(intArrayOf(25, 0, 0), intArrayOf(0, 25, 0), intArrayOf(6, 63, 0), intArrayOf(1, 37, 0)),
    arrayOf<IntArray>(intArrayOf(25, 0, 1), intArrayOf(0, 25, 1), intArrayOf(7, 62, 0), intArrayOf(0, 38, 0)),
    arrayOf<IntArray>(intArrayOf(25, 0, 2), intArrayOf(0, 25, 2), intArrayOf(7, 63, 0), intArrayOf(15, 31, 0)),
    arrayOf<IntArray>(intArrayOf(26, 0, 1), intArrayOf(0, 26, 1), intArrayOf(16, 45, 0), intArrayOf(1, 38, 0)),
    arrayOf<IntArray>(intArrayOf(26, 0, 0), intArrayOf(0, 26, 0), intArrayOf(8, 62, 0), intArrayOf(0, 39, 0)),
    arrayOf<IntArray>(intArrayOf(26, 0, 1), intArrayOf(0, 26, 1), intArrayOf(8, 63, 0), intArrayOf(1, 39, 0)),
    arrayOf<IntArray>(intArrayOf(26, 0, 2), intArrayOf(0, 26, 2), intArrayOf(9, 62, 0), intArrayOf(18, 31, 0)),
    arrayOf<IntArray>(intArrayOf(27, 0, 1), intArrayOf(0, 27, 1), intArrayOf(16, 48, 0), intArrayOf(0, 40, 0)),
    arrayOf<IntArray>(intArrayOf(27, 0, 0), intArrayOf(0, 27, 0), intArrayOf(9, 63, 0), intArrayOf(1, 40, 0)),
    arrayOf<IntArray>(intArrayOf(27, 0, 1), intArrayOf(0, 27, 1), intArrayOf(10, 62, 0), intArrayOf(0, 41, 0)),
    arrayOf<IntArray>(intArrayOf(27, 0, 2), intArrayOf(0, 27, 2), intArrayOf(10, 63, 0), intArrayOf(16, 33, 0)),
    arrayOf<IntArray>(intArrayOf(28, 0, 1), intArrayOf(0, 28, 1), intArrayOf(16, 51, 0), intArrayOf(1, 41, 0)),
    arrayOf<IntArray>(intArrayOf(28, 0, 0), intArrayOf(0, 28, 0), intArrayOf(11, 62, 0), intArrayOf(0, 42, 0)),
    arrayOf<IntArray>(intArrayOf(28, 0, 1), intArrayOf(0, 28, 1), intArrayOf(11, 63, 0), intArrayOf(1, 42, 0)),
    arrayOf<IntArray>(intArrayOf(28, 0, 2), intArrayOf(0, 28, 2), intArrayOf(12, 62, 0), intArrayOf(17, 34, 0)),
    arrayOf<IntArray>(intArrayOf(29, 0, 1), intArrayOf(0, 29, 1), intArrayOf(16, 54, 0), intArrayOf(0, 43, 0)),
    arrayOf<IntArray>(intArrayOf(29, 0, 0), intArrayOf(0, 29, 0), intArrayOf(12, 63, 0), intArrayOf(1, 43, 0)),
    arrayOf<IntArray>(intArrayOf(29, 0, 1), intArrayOf(0, 29, 1), intArrayOf(13, 62, 0), intArrayOf(0, 44, 0)),
    arrayOf<IntArray>(intArrayOf(29, 0, 2), intArrayOf(0, 29, 2), intArrayOf(13, 63, 0), intArrayOf(16, 36, 0)),
    arrayOf<IntArray>(intArrayOf(30, 0, 1), intArrayOf(0, 30, 1), intArrayOf(16, 57, 0), intArrayOf(1, 44, 0)),
    arrayOf<IntArray>(intArrayOf(30, 0, 0), intArrayOf(0, 30, 0), intArrayOf(14, 62, 0), intArrayOf(0, 45, 0)),
    arrayOf<IntArray>(intArrayOf(30, 0, 1), intArrayOf(0, 30, 1), intArrayOf(14, 63, 0), intArrayOf(1, 45, 0)),
    arrayOf<IntArray>(intArrayOf(30, 0, 2), intArrayOf(0, 30, 2), intArrayOf(15, 62, 0), intArrayOf(17, 37, 0)),
    arrayOf<IntArray>(intArrayOf(31, 0, 1), intArrayOf(0, 31, 1), intArrayOf(16, 60, 0), intArrayOf(0, 46, 0)),
    arrayOf<IntArray>(intArrayOf(31, 0, 0), intArrayOf(0, 31, 0), intArrayOf(15, 63, 0), intArrayOf(1, 46, 0)),
    arrayOf<IntArray>(intArrayOf(31, 0, 1), intArrayOf(0, 31, 1), intArrayOf(24, 46, 0), intArrayOf(0, 47, 0)),
    arrayOf<IntArray>(intArrayOf(31, 0, 2), intArrayOf(0, 31, 2), intArrayOf(16, 62, 0), intArrayOf(16, 39, 0)),
    arrayOf<IntArray>(intArrayOf(32, 0, 2), intArrayOf(0, 32, 2), intArrayOf(16, 63, 0), intArrayOf(1, 47, 0)),
    arrayOf<IntArray>(intArrayOf(32, 0, 1), intArrayOf(0, 32, 1), intArrayOf(17, 62, 0), intArrayOf(2, 47, 0)),
    arrayOf<IntArray>(intArrayOf(32, 0, 0), intArrayOf(0, 32, 0), intArrayOf(25, 47, 0), intArrayOf(0, 48, 0)),
    arrayOf<IntArray>(intArrayOf(32, 0, 1), intArrayOf(0, 32, 1), intArrayOf(17, 63, 0), intArrayOf(1, 48, 0)),
    arrayOf<IntArray>(intArrayOf(32, 0, 2), intArrayOf(0, 32, 2), intArrayOf(18, 62, 0), intArrayOf(0, 49, 0)),
    arrayOf<IntArray>(intArrayOf(33, 0, 1), intArrayOf(0, 33, 1), intArrayOf(18, 63, 0), intArrayOf(5, 47, 0)),
    arrayOf<IntArray>(intArrayOf(33, 0, 0), intArrayOf(0, 33, 0), intArrayOf(27, 46, 0), intArrayOf(1, 49, 0)),
    arrayOf<IntArray>(intArrayOf(33, 0, 1), intArrayOf(0, 33, 1), intArrayOf(19, 62, 0), intArrayOf(0, 50, 0)),
    arrayOf<IntArray>(intArrayOf(33, 0, 2), intArrayOf(0, 33, 2), intArrayOf(19, 63, 0), intArrayOf(1, 50, 0)),
    arrayOf<IntArray>(intArrayOf(34, 0, 1), intArrayOf(0, 34, 1), intArrayOf(20, 62, 0), intArrayOf(8, 47, 0)),
    arrayOf<IntArray>(intArrayOf(34, 0, 0), intArrayOf(0, 34, 0), intArrayOf(28, 47, 0), intArrayOf(0, 51, 0)),
    arrayOf<IntArray>(intArrayOf(34, 0, 1), intArrayOf(0, 34, 1), intArrayOf(20, 63, 0), intArrayOf(1, 51, 0)),
    arrayOf<IntArray>(intArrayOf(34, 0, 2), intArrayOf(0, 34, 2), intArrayOf(21, 62, 0), intArrayOf(0, 52, 0)),
    arrayOf<IntArray>(intArrayOf(35, 0, 1), intArrayOf(0, 35, 1), intArrayOf(21, 63, 0), intArrayOf(11, 47, 0)),
    arrayOf<IntArray>(intArrayOf(35, 0, 0), intArrayOf(0, 35, 0), intArrayOf(30, 46, 0), intArrayOf(1, 52, 0)),
    arrayOf<IntArray>(intArrayOf(35, 0, 1), intArrayOf(0, 35, 1), intArrayOf(22, 62, 0), intArrayOf(0, 53, 0)),
    arrayOf<IntArray>(intArrayOf(35, 0, 2), intArrayOf(0, 35, 2), intArrayOf(22, 63, 0), intArrayOf(1, 53, 0)),
    arrayOf<IntArray>(intArrayOf(36, 0, 1), intArrayOf(0, 36, 1), intArrayOf(23, 62, 0), intArrayOf(14, 47, 0)),
    arrayOf<IntArray>(intArrayOf(36, 0, 0), intArrayOf(0, 36, 0), intArrayOf(31, 47, 0), intArrayOf(0, 54, 0)),
    arrayOf<IntArray>(intArrayOf(36, 0, 1), intArrayOf(0, 36, 1), intArrayOf(23, 63, 0), intArrayOf(1, 54, 0)),
    arrayOf<IntArray>(intArrayOf(36, 0, 2), intArrayOf(0, 36, 2), intArrayOf(24, 62, 0), intArrayOf(0, 55, 0)),
    arrayOf<IntArray>(intArrayOf(37, 0, 1), intArrayOf(0, 37, 1), intArrayOf(24, 63, 0), intArrayOf(17, 47, 0)),
    arrayOf<IntArray>(intArrayOf(37, 0, 0), intArrayOf(0, 37, 0), intArrayOf(32, 47, 0), intArrayOf(1, 55, 0)),
    arrayOf<IntArray>(intArrayOf(37, 0, 1), intArrayOf(0, 37, 1), intArrayOf(25, 62, 0), intArrayOf(0, 56, 0)),
    arrayOf<IntArray>(intArrayOf(37, 0, 2), intArrayOf(0, 37, 2), intArrayOf(25, 63, 0), intArrayOf(1, 56, 0)),
    arrayOf<IntArray>(intArrayOf(38, 0, 1), intArrayOf(0, 38, 1), intArrayOf(26, 62, 0), intArrayOf(17, 48, 0)),
    arrayOf<IntArray>(intArrayOf(38, 0, 0), intArrayOf(0, 38, 0), intArrayOf(32, 50, 0), intArrayOf(0, 57, 0)),
    arrayOf<IntArray>(intArrayOf(38, 0, 1), intArrayOf(0, 38, 1), intArrayOf(26, 63, 0), intArrayOf(1, 57, 0)),
    arrayOf<IntArray>(intArrayOf(38, 0, 2), intArrayOf(0, 38, 2), intArrayOf(27, 62, 0), intArrayOf(0, 58, 0)),
    arrayOf<IntArray>(intArrayOf(39, 0, 1), intArrayOf(0, 39, 1), intArrayOf(27, 63, 0), intArrayOf(16, 50, 0)),
    arrayOf<IntArray>(intArrayOf(39, 0, 0), intArrayOf(0, 39, 0), intArrayOf(32, 53, 0), intArrayOf(1, 58, 0)),
    arrayOf<IntArray>(intArrayOf(39, 0, 1), intArrayOf(0, 39, 1), intArrayOf(28, 62, 0), intArrayOf(0, 59, 0)),
    arrayOf<IntArray>(intArrayOf(39, 0, 2), intArrayOf(0, 39, 2), intArrayOf(28, 63, 0), intArrayOf(1, 59, 0)),
    arrayOf<IntArray>(intArrayOf(40, 0, 1), intArrayOf(0, 40, 1), intArrayOf(29, 62, 0), intArrayOf(17, 51, 0)),
    arrayOf<IntArray>(intArrayOf(40, 0, 0), intArrayOf(0, 40, 0), intArrayOf(32, 56, 0), intArrayOf(0, 60, 0)),
    arrayOf<IntArray>(intArrayOf(40, 0, 1), intArrayOf(0, 40, 1), intArrayOf(29, 63, 0), intArrayOf(1, 60, 0)),
    arrayOf<IntArray>(intArrayOf(40, 0, 2), intArrayOf(0, 40, 2), intArrayOf(30, 62, 0), intArrayOf(0, 61, 0)),
    arrayOf<IntArray>(intArrayOf(41, 0, 1), intArrayOf(0, 41, 1), intArrayOf(30, 63, 0), intArrayOf(16, 53, 0)),
    arrayOf<IntArray>(intArrayOf(41, 0, 0), intArrayOf(0, 41, 0), intArrayOf(32, 59, 0), intArrayOf(1, 61, 0)),
    arrayOf<IntArray>(intArrayOf(41, 0, 1), intArrayOf(0, 41, 1), intArrayOf(31, 62, 0), intArrayOf(0, 62, 0)),
    arrayOf<IntArray>(intArrayOf(41, 0, 2), intArrayOf(0, 41, 2), intArrayOf(31, 63, 0), intArrayOf(1, 62, 0)),
    arrayOf<IntArray>(intArrayOf(42, 0, 1), intArrayOf(0, 42, 1), intArrayOf(32, 61, 0), intArrayOf(17, 54, 0)),
    arrayOf<IntArray>(intArrayOf(42, 0, 0), intArrayOf(0, 42, 0), intArrayOf(32, 62, 0), intArrayOf(0, 63, 0)),
    arrayOf<IntArray>(intArrayOf(42, 0, 1), intArrayOf(0, 42, 1), intArrayOf(32, 63, 0), intArrayOf(1, 63, 0)),
    arrayOf<IntArray>(intArrayOf(42, 0, 2), intArrayOf(0, 42, 2), intArrayOf(41, 46, 0), intArrayOf(2, 63, 0)),
    arrayOf<IntArray>(intArrayOf(43, 0, 1), intArrayOf(0, 43, 1), intArrayOf(33, 62, 0), intArrayOf(16, 56, 0)),
    arrayOf<IntArray>(intArrayOf(43, 0, 0), intArrayOf(0, 43, 0), intArrayOf(33, 63, 0), intArrayOf(3, 63, 0)),
    arrayOf<IntArray>(intArrayOf(43, 0, 1), intArrayOf(0, 43, 1), intArrayOf(34, 62, 0), intArrayOf(4, 63, 0)),
    arrayOf<IntArray>(intArrayOf(43, 0, 2), intArrayOf(0, 43, 2), intArrayOf(42, 47, 0), intArrayOf(5, 63, 0)),
    arrayOf<IntArray>(intArrayOf(44, 0, 1), intArrayOf(0, 44, 1), intArrayOf(34, 63, 0), intArrayOf(17, 57, 0)),
    arrayOf<IntArray>(intArrayOf(44, 0, 0), intArrayOf(0, 44, 0), intArrayOf(35, 62, 0), intArrayOf(6, 63, 0)),
    arrayOf<IntArray>(intArrayOf(44, 0, 1), intArrayOf(0, 44, 1), intArrayOf(35, 63, 0), intArrayOf(7, 63, 0)),
    arrayOf<IntArray>(intArrayOf(44, 0, 2), intArrayOf(0, 44, 2), intArrayOf(44, 46, 0), intArrayOf(8, 63, 0)),
    arrayOf<IntArray>(intArrayOf(45, 0, 1), intArrayOf(0, 45, 1), intArrayOf(36, 62, 0), intArrayOf(16, 59, 0)),
    arrayOf<IntArray>(intArrayOf(45, 0, 0), intArrayOf(0, 45, 0), intArrayOf(36, 63, 0), intArrayOf(9, 63, 0)),
    arrayOf<IntArray>(intArrayOf(45, 0, 1), intArrayOf(0, 45, 1), intArrayOf(37, 62, 0), intArrayOf(10, 63, 0)),
    arrayOf<IntArray>(intArrayOf(45, 0, 2), intArrayOf(0, 45, 2), intArrayOf(45, 47, 0), intArrayOf(11, 63, 0)),
    arrayOf<IntArray>(intArrayOf(46, 0, 1), intArrayOf(0, 46, 1), intArrayOf(37, 63, 0), intArrayOf(17, 60, 0)),
    arrayOf<IntArray>(intArrayOf(46, 0, 0), intArrayOf(0, 46, 0), intArrayOf(38, 62, 0), intArrayOf(12, 63, 0)),
    arrayOf<IntArray>(intArrayOf(46, 0, 1), intArrayOf(0, 46, 1), intArrayOf(38, 63, 0), intArrayOf(13, 63, 0)),
    arrayOf<IntArray>(intArrayOf(46, 0, 2), intArrayOf(0, 46, 2), intArrayOf(47, 46, 0), intArrayOf(14, 63, 0)),
    arrayOf<IntArray>(intArrayOf(47, 0, 1), intArrayOf(0, 47, 1), intArrayOf(39, 62, 0), intArrayOf(16, 62, 0)),
    arrayOf<IntArray>(intArrayOf(47, 0, 0), intArrayOf(0, 47, 0), intArrayOf(39, 63, 0), intArrayOf(15, 63, 0)),
    arrayOf<IntArray>(intArrayOf(47, 0, 1), intArrayOf(0, 47, 1), intArrayOf(40, 62, 0), intArrayOf(16, 63, 0)),
    arrayOf<IntArray>(intArrayOf(47, 0, 2), intArrayOf(0, 47, 2), intArrayOf(48, 46, 0), intArrayOf(32, 55, 0)),
    arrayOf<IntArray>(intArrayOf(48, 0, 2), intArrayOf(0, 48, 2), intArrayOf(40, 63, 0), intArrayOf(17, 63, 0)),
    arrayOf<IntArray>(intArrayOf(48, 0, 1), intArrayOf(0, 48, 1), intArrayOf(41, 62, 0), intArrayOf(18, 63, 0)),
    arrayOf<IntArray>(intArrayOf(48, 0, 0), intArrayOf(0, 48, 0), intArrayOf(41, 63, 0), intArrayOf(19, 63, 0)),
    arrayOf<IntArray>(intArrayOf(48, 0, 1), intArrayOf(0, 48, 1), intArrayOf(48, 49, 0), intArrayOf(33, 56, 0)),
    arrayOf<IntArray>(intArrayOf(48, 0, 2), intArrayOf(0, 48, 2), intArrayOf(42, 62, 0), intArrayOf(20, 63, 0)),
    arrayOf<IntArray>(intArrayOf(49, 0, 1), intArrayOf(0, 49, 1), intArrayOf(42, 63, 0), intArrayOf(21, 63, 0)),
    arrayOf<IntArray>(intArrayOf(49, 0, 0), intArrayOf(0, 49, 0), intArrayOf(43, 62, 0), intArrayOf(22, 63, 0)),
    arrayOf<IntArray>(intArrayOf(49, 0, 1), intArrayOf(0, 49, 1), intArrayOf(48, 52, 0), intArrayOf(32, 58, 0)),
    arrayOf<IntArray>(intArrayOf(49, 0, 2), intArrayOf(0, 49, 2), intArrayOf(43, 63, 0), intArrayOf(23, 63, 0)),
    arrayOf<IntArray>(intArrayOf(50, 0, 1), intArrayOf(0, 50, 1), intArrayOf(44, 62, 0), intArrayOf(24, 63, 0)),
    arrayOf<IntArray>(intArrayOf(50, 0, 0), intArrayOf(0, 50, 0), intArrayOf(44, 63, 0), intArrayOf(25, 63, 0)),
    arrayOf<IntArray>(intArrayOf(50, 0, 1), intArrayOf(0, 50, 1), intArrayOf(48, 55, 0), intArrayOf(33, 59, 0)),
    arrayOf<IntArray>(intArrayOf(50, 0, 2), intArrayOf(0, 50, 2), intArrayOf(45, 62, 0), intArrayOf(26, 63, 0)),
    arrayOf<IntArray>(intArrayOf(51, 0, 1), intArrayOf(0, 51, 1), intArrayOf(45, 63, 0), intArrayOf(27, 63, 0)),
    arrayOf<IntArray>(intArrayOf(51, 0, 0), intArrayOf(0, 51, 0), intArrayOf(46, 62, 0), intArrayOf(28, 63, 0)),
    arrayOf<IntArray>(intArrayOf(51, 0, 1), intArrayOf(0, 51, 1), intArrayOf(48, 58, 0), intArrayOf(32, 61, 0)),
    arrayOf<IntArray>(intArrayOf(51, 0, 2), intArrayOf(0, 51, 2), intArrayOf(46, 63, 0), intArrayOf(29, 63, 0)),
    arrayOf<IntArray>(intArrayOf(52, 0, 1), intArrayOf(0, 52, 1), intArrayOf(47, 62, 0), intArrayOf(30, 63, 0)),
    arrayOf<IntArray>(intArrayOf(52, 0, 0), intArrayOf(0, 52, 0), intArrayOf(47, 63, 0), intArrayOf(31, 63, 0)),
    arrayOf<IntArray>(intArrayOf(52, 0, 1), intArrayOf(0, 52, 1), intArrayOf(48, 61, 0), intArrayOf(33, 62, 0)),
    arrayOf<IntArray>(intArrayOf(52, 0, 2), intArrayOf(0, 52, 2), intArrayOf(48, 62, 0), intArrayOf(32, 63, 0)),
    arrayOf<IntArray>(intArrayOf(53, 0, 1), intArrayOf(0, 53, 1), intArrayOf(56, 47, 0), intArrayOf(33, 63, 0)),
    arrayOf<IntArray>(intArrayOf(53, 0, 0), intArrayOf(0, 53, 0), intArrayOf(48, 63, 0), intArrayOf(49, 55, 0)),
    arrayOf<IntArray>(intArrayOf(53, 0, 1), intArrayOf(0, 53, 1), intArrayOf(49, 62, 0), intArrayOf(34, 63, 0)),
    arrayOf<IntArray>(intArrayOf(53, 0, 2), intArrayOf(0, 53, 2), intArrayOf(49, 63, 0), intArrayOf(35, 63, 0)),
    arrayOf<IntArray>(intArrayOf(54, 0, 1), intArrayOf(0, 54, 1), intArrayOf(58, 46, 0), intArrayOf(36, 63, 0)),
    arrayOf<IntArray>(intArrayOf(54, 0, 0), intArrayOf(0, 54, 0), intArrayOf(50, 62, 0), intArrayOf(48, 57, 0)),
    arrayOf<IntArray>(intArrayOf(54, 0, 1), intArrayOf(0, 54, 1), intArrayOf(50, 63, 0), intArrayOf(37, 63, 0)),
    arrayOf<IntArray>(intArrayOf(54, 0, 2), intArrayOf(0, 54, 2), intArrayOf(51, 62, 0), intArrayOf(38, 63, 0)),
    arrayOf<IntArray>(intArrayOf(55, 0, 1), intArrayOf(0, 55, 1), intArrayOf(59, 47, 0), intArrayOf(39, 63, 0)),
    arrayOf<IntArray>(intArrayOf(55, 0, 0), intArrayOf(0, 55, 0), intArrayOf(51, 63, 0), intArrayOf(49, 58, 0)),
    arrayOf<IntArray>(intArrayOf(55, 0, 1), intArrayOf(0, 55, 1), intArrayOf(52, 62, 0), intArrayOf(40, 63, 0)),
    arrayOf<IntArray>(intArrayOf(55, 0, 2), intArrayOf(0, 55, 2), intArrayOf(52, 63, 0), intArrayOf(41, 63, 0)),
    arrayOf<IntArray>(intArrayOf(56, 0, 1), intArrayOf(0, 56, 1), intArrayOf(61, 46, 0), intArrayOf(42, 63, 0)),
    arrayOf<IntArray>(intArrayOf(56, 0, 0), intArrayOf(0, 56, 0), intArrayOf(53, 62, 0), intArrayOf(48, 60, 0)),
    arrayOf<IntArray>(intArrayOf(56, 0, 1), intArrayOf(0, 56, 1), intArrayOf(53, 63, 0), intArrayOf(43, 63, 0)),
    arrayOf<IntArray>(intArrayOf(56, 0, 2), intArrayOf(0, 56, 2), intArrayOf(54, 62, 0), intArrayOf(44, 63, 0)),
    arrayOf<IntArray>(intArrayOf(57, 0, 1), intArrayOf(0, 57, 1), intArrayOf(62, 47, 0), intArrayOf(45, 63, 0)),
    arrayOf<IntArray>(intArrayOf(57, 0, 0), intArrayOf(0, 57, 0), intArrayOf(54, 63, 0), intArrayOf(49, 61, 0)),
    arrayOf<IntArray>(intArrayOf(57, 0, 1), intArrayOf(0, 57, 1), intArrayOf(55, 62, 0), intArrayOf(46, 63, 0)),
    arrayOf<IntArray>(intArrayOf(57, 0, 2), intArrayOf(0, 57, 2), intArrayOf(55, 63, 0), intArrayOf(47, 63, 0)),
    arrayOf<IntArray>(intArrayOf(58, 0, 1), intArrayOf(0, 58, 1), intArrayOf(56, 62, 1), intArrayOf(48, 63, 1)),
    arrayOf<IntArray>(intArrayOf(58, 0, 0), intArrayOf(0, 58, 0), intArrayOf(56, 62, 0), intArrayOf(48, 63, 0)),
    arrayOf<IntArray>(intArrayOf(58, 0, 1), intArrayOf(0, 58, 1), intArrayOf(56, 63, 0), intArrayOf(49, 63, 0)),
    arrayOf<IntArray>(intArrayOf(58, 0, 2), intArrayOf(0, 58, 2), intArrayOf(57, 62, 0), intArrayOf(50, 63, 0)),
    arrayOf<IntArray>(intArrayOf(59, 0, 1), intArrayOf(0, 59, 1), intArrayOf(57, 63, 1), intArrayOf(51, 63, 1)),
    arrayOf<IntArray>(intArrayOf(59, 0, 0), intArrayOf(0, 59, 0), intArrayOf(57, 63, 0), intArrayOf(51, 63, 0)),
    arrayOf<IntArray>(intArrayOf(59, 0, 1), intArrayOf(0, 59, 1), intArrayOf(58, 62, 0), intArrayOf(52, 63, 0)),
    arrayOf<IntArray>(intArrayOf(59, 0, 2), intArrayOf(0, 59, 2), intArrayOf(58, 63, 0), intArrayOf(53, 63, 0)),
    arrayOf<IntArray>(intArrayOf(60, 0, 1), intArrayOf(0, 60, 1), intArrayOf(59, 62, 1), intArrayOf(54, 63, 1)),
    arrayOf<IntArray>(intArrayOf(60, 0, 0), intArrayOf(0, 60, 0), intArrayOf(59, 62, 0), intArrayOf(54, 63, 0)),
    arrayOf<IntArray>(intArrayOf(60, 0, 1), intArrayOf(0, 60, 1), intArrayOf(59, 63, 0), intArrayOf(55, 63, 0)),
    arrayOf<IntArray>(intArrayOf(60, 0, 2), intArrayOf(0, 60, 2), intArrayOf(60, 62, 0), intArrayOf(56, 63, 0)),
    arrayOf<IntArray>(intArrayOf(61, 0, 1), intArrayOf(0, 61, 1), intArrayOf(60, 63, 1), intArrayOf(57, 63, 1)),
    arrayOf<IntArray>(intArrayOf(61, 0, 0), intArrayOf(0, 61, 0), intArrayOf(60, 63, 0), intArrayOf(57, 63, 0)),
    arrayOf<IntArray>(intArrayOf(61, 0, 1), intArrayOf(0, 61, 1), intArrayOf(61, 62, 0), intArrayOf(58, 63, 0)),
    arrayOf<IntArray>(intArrayOf(61, 0, 2), intArrayOf(0, 61, 2), intArrayOf(61, 63, 0), intArrayOf(59, 63, 0)),
    arrayOf<IntArray>(intArrayOf(62, 0, 1), intArrayOf(0, 62, 1), intArrayOf(62, 62, 1), intArrayOf(60, 63, 1)),
    arrayOf<IntArray>(intArrayOf(62, 0, 0), intArrayOf(0, 62, 0), intArrayOf(62, 62, 0), intArrayOf(60, 63, 0)),
    arrayOf<IntArray>(intArrayOf(62, 0, 1), intArrayOf(0, 62, 1), intArrayOf(62, 63, 0), intArrayOf(61, 63, 0)),
    arrayOf<IntArray>(intArrayOf(62, 0, 2), intArrayOf(0, 62, 2), intArrayOf(63, 62, 0), intArrayOf(62, 63, 0)),
    arrayOf<IntArray>(intArrayOf(63, 0, 1), intArrayOf(0, 63, 1), intArrayOf(63, 63, 1), intArrayOf(63, 63, 1)),
    arrayOf<IntArray>(intArrayOf(63, 0, 0), intArrayOf(0, 63, 0), intArrayOf(63, 63, 0), intArrayOf(63, 63, 0))
)