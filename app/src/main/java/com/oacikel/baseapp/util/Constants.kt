package com.oacikel.baseapp.util

import java.text.SimpleDateFormat

object Constants {
    const val FRAGMENT_DATA = "fragment_data"
    const val IS_SESSION_END = "IS_SESSION_END"
    const val FINGERPRINAUTHTOKEN: String = "fingerprintAuthToken"
    const val FINGERPRINTDONTREMIND: String = "fingerprintDontRemind"
    const val FINGERPRINTFIRSTLOGIN: String = "fingerprintFirstLogin"
    const val PIN: String = "pin"
    val longDateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
}