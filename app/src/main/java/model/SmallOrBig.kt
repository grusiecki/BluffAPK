package model

enum class SmallOrBig(override var rank: Int, override var str: String): Rankable {
    SMALLER(-1,"Smaller"),
    SMALL(0,"Small"),
    BIG(1,"Big")
}