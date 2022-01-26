#!/usr/bin/env kotlin

/** Stores the coordinates of a board square */
data class Square(val x: Int, val y: Int)
fun Square.add(p: Pair<Int, Int>) = Square(this.x + p.first, this.y + p.second)

/** Board dimension and size */
val boardDimensionX = 8;
val boardDimensionY = 8;
val boardSize = boardDimensionX * boardDimensionY;

/** An array with all board coordinates */
val boardSquares = (0..(boardSize - 1)).map {
    Square(1 + it / boardDimensionY, 1 + it % boardDimensionY)
}

/** Delta coordinates of all 8 possible kinght move directions */
val knightMoveDeltas = (0..7).map {
    Pair((1+it/4) * (1 - 2*(it%4/2)), (2-it/4) * (1 - 2*(it%2)))
}

/** Finds the next sequence of moves for the knight, recursivelly */
fun knightTour(moves : List<Square>) : List<Square> {
    // Trivial case (detecting is solution has been reached)
    if (moves.size == boardSize) return moves;

    // Possible next squares from given square, excluding those already taken
    fun findPossibleMoves(s: Square) =
        (knightMoveDeltas.map { s.add(it) } intersect boardSquares).toList() - moves

    val possibleMoves = findPossibleMoves(moves.last())

    // Uses heuristics to choose the best next square (Warnsdorff's rule, 1823)
    val newSquare = possibleMoves.minByOrNull { findPossibleMoves(it).size }

    return if (newSquare == null) moves else knightTour(moves + newSquare)
}

fun knightTourFrom(x: Int, y: Int) : List<Square> = knightTour(listOf(Square(x, y)))

/** Calculation of the knight tour from square 1,1 */
val tour = knightTourFrom(1, 1)

// Displaying of the knight tour on te console
for (x in 1..boardDimensionX) {
    for (y in 1..boardDimensionY) {
        val i = tour.indexOfFirst { it.x == x && it.y == y } + 1
        print("%2d ".format(i))
    }
    println()
}
