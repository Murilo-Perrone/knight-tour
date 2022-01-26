#!/usr/bin/env kotlin

for (i in 0..7) {
    val x = (1+i/4) * (1 - 2*(i%4/2))
    val y = (2-i/4) * (1 - 2*(i%2))
    println("$x, $y")
}
