package com.example.tracker.utls

enum class SortType(val typeName: String) {
    DATA("timestamp"),
    RUNNING_TIME("timemili"),
    DISTANCE("distance"),
    AVERAGE_SPEED("speed"),
    CALORIES_BURNED("calories");

    companion object {
        fun findSortTypeByPosition(position: Int) : String {
            for(enum in values()) {
                if (position == enum.ordinal)
                    return enum.typeName
            }
            return DATA.typeName
        }
    }
}