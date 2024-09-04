package model

enum class Color(override var rank: Int, override var str: String): Rankable {
    HEART(3, "Heart"), DIAMOND(2, "Diamond"), SPADE(1, "Spade"), CLUB(0, "Club");
}