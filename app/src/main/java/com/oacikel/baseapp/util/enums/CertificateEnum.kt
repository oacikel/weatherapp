package com.oacikel.baseapp.util.enums

enum class CertificateEnum  constructor(description: String) {

    TEST("base_test.cer") , PROD("base.crt");

    var description : String? = null

    init {
        this.description = description
    }
}