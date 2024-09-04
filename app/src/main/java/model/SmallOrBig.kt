package model

enum class SmallOrBig(override var rank: Int, override var str: String): Rankable {
    SMALL(0,"Small"),
    BIG(1,"Big")
}