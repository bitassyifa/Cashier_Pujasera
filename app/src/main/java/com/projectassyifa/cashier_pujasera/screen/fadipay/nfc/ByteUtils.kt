package com.projectassyifa.cashier_pujasera.screen.fadipay.nfc

class ByteUtils {
    companion object {
        fun isNullOrEmpty(array: ByteArray?): Boolean {
            if (array == null) {
                return true
            }
            val length = array.size
            for (i in 0 until length) {
                if (array[i].toInt() != 0) {
                    return false
                }
            }
            return true
        }
    }
}
