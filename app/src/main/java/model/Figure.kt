package model

enum class Figure(var rank: Int, var str: String) {
    EIGHT(-1, "8"),//fake, used only for start
    NINE(0, "9"),
    TEN(1, "10"),
    JACK(2, "jack"),
    QUEEN(3, "queen"),
    KING(4, "king"),
    ACE(5, "ace")
}