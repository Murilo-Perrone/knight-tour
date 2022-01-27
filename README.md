# Knight Tour
This is a collection of knight tour solutions written in different languages. Notes:
  * All files can be ran as executables (because they have a shebang header). Eg: `./knightTour.scala`
  * Knight tour is a problem/puzzle in which a knight piece (from chess) needs to step in all squares of a board without repeating anyone of them.

## Kotlin
 * [warnsdorffsRule.main.kts](warnsdorffsRule.main.kts) - Finds a solution using warnsdorffs rule. Output:
    ```
     1  4 57 20 47  6 49 22 
    34 19  2  5 58 21 46  7 
    3 56 35 60 37 48 23 50 
    18 33 38 55 52 59  8 45 
    39 14 53 36 61 44 51 24 
    32 17 40 43 54 27 62  9 
    13 42 15 30 11 64 25 28 
    16 31 12 41 26 29 10 63
    ```
  * [warnsdorffsRule-recursive.main.kts](warnsdorffsRule-recursive.main.kts) - Finds a solution using warnsdorffs rule and recursion. Output: 
    ```
     1  4 57 20 47  6 49 22 
    34 19  2  5 58 21 46  7 
     3 56 35 60 37 48 23 50 
    18 33 38 55 52 59  8 45 
    39 14 53 36 61 44 51 24 
    32 17 40 43 54 27 62  9 
    13 42 15 30 11 64 25 28 
    16 31 12 41 26 29 10 63 
    ```
 * [knightDeltaMoves.main.kts](knightDeltaMoves.main.kts) - Generates and lists all knight delta moves. Output:
    ```
    1, 2
    1, -2
    -1, 2
    -1, -2
    2, 1
    2, -1
    -2, 1
    -2, -1
    ```
## Scala
  * [knightTour.scala](knightTour.scala) - Enumerates and counts all solutions for a 5x5 board, using brute-force. Also, uses warnsdorffs rule to find a full board solution (8x8). Output:
    ```
    (...)
     1 12 17 22  3 
    18 25  2 11 16 
    13  8 23  4 21 
    24 19  6 15 10 
     7 14  9 20  5 
    ------
    Found 56 knight tours for a 5 x 5 board.

    Searching for a knight tour in a 8 x 8 board...

    10 51 24 63 18 41 26 61 
    23 64 11 40 25 62 17 42 
    50  9 52 19 12 15 60 27 
    53 22  1 14 39 20 43 16 
     8 49 32 21  6 13 28 59 
    33 54  7  2 29 38  5 44 
    48 31 56 35 46  3 58 37 
    55 34 47 30 57 36 45  4
    ```

## Go
  * [warnsdorffsRule.go](warnsdorffsRule.go) - Finds a solution using warnsdorffs rule and randomized choices. Output: 
    ```
     1  4 35 20 47  6 43 22
    34 19  2  5 42 21 46  7
     3 36 41 48 39 64 23 44
    18 33 38 61 50 45  8 59
    37 14 49 40 63 60 55 24
    32 17 62 51 54 27 58  9
    13 52 15 30 11 56 25 28
    16 31 12 53 26 29 10 57
    ```
  