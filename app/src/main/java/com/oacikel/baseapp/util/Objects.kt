package com.oacikel.baseapp.util

object Objects {
    fun equals(o1: Any?, o2: Any?): Boolean {
        if (o1 == null) {
            return o2 == null
        }
        return if (o2 == null) {
            false
        } else o1 == o2
    }
}