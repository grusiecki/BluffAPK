package model

enum class Set (override var rank: Int, override var str: String) :Rankable{
    ONECARD(1, "One Card"),
    PAIR(2,"Pair"),
    TWOPAIRS(3,"Two Pairs"),
    THREE(4, "Three"),
    STRAIGHT(5,"Straight"),
    FULL(6,"Full"),
    FOUR(7,"Four"),
    FLUSH(8, "Flush"),
    ROYALFLUSH(9,"Royal Flush")
}