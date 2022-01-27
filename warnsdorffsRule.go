//$GOROOT/bin/go run $0 $@ ; exit

package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Pos struct {
	row int
	col int
}

func (this *Pos) add(p Pos) Pos {
	return Pos{row: this.row + p.row, col: this.col + p.col}
}

// Checks if a given row and column are within the board space and have not been visited yet
func (p *Pos) isValidMove() bool {
	return p.row >= 0 && p.row < boardSize && p.col >= 0 && p.col < boardSize && board[p.row][p.col] == 0
}

// input, 0-based start position
var start = Pos{row: 0, col: 0}

const boardSize = 8

// the board.  squares hold 1-based visit order.  0 means unvisited.
var board = make([][]int, boardSize)

func main() {
	rand.Seed(time.Now().Unix())
	if knightTour() {
		// A knight tour has been found. Printing the board.
		for _, r := range board {
			for _, m := range r {
				fmt.Printf("%3d", m)
			}
			fmt.Println()
		}
	} else {
		println("No tour found.")
	}
}

var moves = []Pos{
	{2, 1},
	{2, -1},
	{1, 2},
	{1, -2},
	{-1, 2},
	{-1, -2},
	{-2, 1},
	{-2, -1},
}

// Attempt knight tour starting at startRow, startCol using Warnsdorff's rule
// and random tie breaking.  If a tour is found, print it and return true.
// Otherwise no backtracking, just return false.
func knightTour() bool {
	for i := range board {
		board[i] = make([]int, boardSize)
	}
	cur := start
	board[cur.row][cur.col] = 1 // first move
	for move := 2; move <= boardSize*boardSize; move++ {
		minMovesCount := len(moves)
		var best Pos
		var nextMoveCandidates int
	candidateMoves:
		for _, move := range moves {
			candidate := cur.add(move)
			if candidate.isValidMove() == false {
				continue
			}

			nextMovesCount := 0 // count possible next moves.
			for _, move2 := range moves {
				candidate2 := candidate.add(move2)
				if candidate2.isValidMove() {
					nextMovesCount++
					if nextMovesCount > minMovesCount { // bail out as soon as it's eliminated
						continue candidateMoves
					}
				}
			}
			if nextMovesCount < minMovesCount { // it's better.  keep it.
				minMovesCount = nextMovesCount // new count of possible 2nd moves
				nextMoveCandidates = 1         // number of candidates with this count
				best = candidate               // making it the best candidate move
				continue
			}
			// Otherwise, it ties for the best move.
			nextMoveCandidates++ // Updating the number of tying moves
			// Using a dice with the 1/(number of tying moves) success probability
			// If it gets lucky, it will be chosen
			if rand.Intn(nextMoveCandidates) == 0 {
				best = candidate
			}
		}
		if nextMoveCandidates == 0 { // no legal move
			return false
		}
		// make selected move
		cur = best
		board[cur.row][cur.col] = move
	}

	return true
}
