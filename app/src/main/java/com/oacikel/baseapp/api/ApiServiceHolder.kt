package com.oacikel.baseapp.api

class ApiServiceHolder(
    var baseService: BaseService? = null,
    var marvelService: MarvelService? = null
) {
    fun setAPIService(service: BaseService) {
        this.baseService = service
    }

    fun setAPIMarvelService(service: MarvelService) {
        this.marvelService = service
    }
}