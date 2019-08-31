package com.example.recyclerviewdemo.model

class TagType {

    enum class Tags {
        GOOD,
        BAD,
        NORMAL,
        UNKNOWN
    }

    companion object {
        fun getTagType(rating: Int): Tags {
            return when (rating) {
                in 1..3 -> Tags.BAD
                4 -> Tags.NORMAL
                5 -> Tags.GOOD
                else -> Tags.UNKNOWN
            }
        }
    }
}