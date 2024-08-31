package model

enum class Color(var rank: Int, var str: String) {
    HEART(3, "heart"), DIAMOND(2, "diamond"), SPADE(1, "spade"), CLUB(0, "club");
}