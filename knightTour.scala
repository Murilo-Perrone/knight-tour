#!/usr/bin/env scala

type Pos = (Int, Int) // A board position: coordinates of a board square, which can represent a move
type Path = List[Pos] // A list of positions

// Tests rather the move is legal (inside the board and not steping over previous knight path)
def isLegalMove(dim: Int, path: Path)(x: Pos) : Boolean = {
  x._1 >= 0 && x._1 < dim && x._2 >= 0 && x._2 < dim && !path.contains(x)
}

// A list of knight moves as tuples of delta X and delta Y coordinates
val deltaMoveList = List((1,2), (2,1), (2,-1), (1,-2), (-1, -2), (-2,-1), (-2,1), (-1,2))

// Lists the possible moves, for a given path and board dimension
def listLegalMoves(dim: Int, path: Path) : List[Pos] = {
  val x = path.head
  for (
      n <- deltaMoveList;
      newPos = ((x._1 + n._1), (x._2 + n._2))
      if isLegalMove(dim, path)(newPos)
    )
      yield (newPos).asInstanceOf[Pos]
}

// Test cases

assert(listLegalMoves(8, List((2,2)))
  == List((3,4), (4,3), (4,1), (3,0), (1,0), (0,1), (0,3), (1,4)))
assert(listLegalMoves(8, List((7,7)))
  == List((6,5), (5,6)))
assert(listLegalMoves(8, List((2,2), (4,1), (1,0)))
  == List((3,4), (4,3), (3,0), (0,1), (0,3), (1,4)))
assert(listLegalMoves(8, List((7,7)))
  == List((6,5), (5,6)))

// Counts the number of successfull knight tours starting from a given path
def countKnightTours(dim: Int, path: Path) : Int = {
  val moves = listLegalMoves(dim, path)
  val counts = for (n <- moves) yield countKnightTours(dim, n :: path)
  if (path.size == dim*dim) 1
  else if (moves.size == 0) 0
  else counts.foldLeft(0)(_ + _)
}

// Lists the number of successfull knight tours starting from a given path
def listKnightTours(dim: Int, path: Path) : List[Path] = {
  val moves = listLegalMoves(dim, path)
  val tours = for (n <- moves) yield listKnightTours(dim, n :: path)
  if (path.size == dim*dim) List(path)
  else if (moves.size == 0) Nil
  else tours.flatten
}

// Finds a single knight tour recursively using brute force
def knightTour(dim: Int, path: Path): Option[Path] = {
  if (path.size == dim * dim) {
    Some(path)
  } else {
    val moves = listLegalMoves(dim, path)
    if (moves.size == 0) None
    else moves map { move => knightTour(dim, move :: path) } collectFirst
      { case newPath if newPath != None => newPath.get }
  }
}

// Finds a single knight tour recursively using Warnsdorff's rule
def knightTourOptimized(dim: Int, path: Path): Option[Path] = {
  if (path.size == dim * dim) {
    Some(path)
  } else {
    val moves = listLegalMoves(dim, path)
    if (moves.size == 0) None
    else if (moves.size == 1 || path.size + 1 == dim * dim)
      knightTourOptimized(dim, moves.head :: path)
    else {
      val secondMoves = for (
          m <- moves;
          count = listLegalMoves(dim, m :: path).size
          if count > 0
        ) yield (m, count)
      if (secondMoves.size == 0) None
      else {
        val bestMove = secondMoves.minBy(_._2)._1
        knightTourOptimized(dim, bestMove :: path)
      }
    }
  }
}

def printTour(size: Int, tour: List[Pos]) = {
  for (y <- 0 until size) {
    for (x <- 0 until size) {
      val i = tour.indexWhere ( p => p._1 == x && p._2 == y ) + 1
      print("%2d ".format(i))
    }
    println("");
  }
  println("------");
}

var size = 5
val tours = listKnightTours(size, List[Pos]((1,1)))
for (t <- tours) printTour(size, t)

val count = countKnightTours(size, List[Pos]((1,1)))
println(s"Found $count knight tours for a $size x $size board.\n")

size = 8
println(s"Searching for a knight tour in a $size x $size board...\n")
val tour = knightTourOptimized(size, List[Pos]((1,1)))
if (tour != None)
  printTour(size, tour.get)
else println("No tour found")
