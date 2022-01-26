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
fun knightTourFrom(x: Int, y: Int) : List<Square> {
    val moves = mutableListOf<Square>()

    // Possible next squares from given square, excluding those already taken
    fun findPossibleMoves(s: Square) =
        (knightMoveDeltas.map { s.add(it) } intersect boardSquares).toList() - moves

    var nextMove: Square? = Square(x, y)
    while (nextMove != null) {
        val current = nextMove
        moves.add(current);

        // Detecting if a solution has been reached
        if (moves.size == boardSize) break;

        val possibleMoves = findPossibleMoves(current)

        // Uses heuristics to choose the best next square (Warnsdorff's rule, 1823)
        nextMove = possibleMoves.minByOrNull { findPossibleMoves(it).size }
    }
    
    return moves;
}

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
