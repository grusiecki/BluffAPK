package model

enum class Figure(override var rank: Int, override var str: String): Rankable {
    EIGHT(-1, "8"),//fake, used only for start
    NINE(0, "9"),
    TEN(1, "10"),
    JACK(2, "Jack"),
    QUEEN(3, "Queen"),
    KING(4, "King"),
    ACE(5, "Ace")
}