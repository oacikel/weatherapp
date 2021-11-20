package com.oacikel.baseapp.api

class ApiServiceHolder(
    var baseService: BaseService? = null,
) {
    fun setAPIService(service: BaseService) {
        this.baseService = service
    }

}