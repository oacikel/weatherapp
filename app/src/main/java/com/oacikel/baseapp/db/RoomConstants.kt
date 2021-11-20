package com.oacikel.baseapp.db

class RoomConstants {
    companion object {
        const val USER = "user"
        const val ID = "id"

        //Base Response Start
        const val RESPONSE_CODE="cod"
        const val RESPONSE_DATA="data"
        //Base Respond End

        //Weather Start
        const val WEATHER_TABLE="weather"
        const val WEATHER_ID="id"
        const val WEATHER_COORDINATE="coord"
        const val WEATHER_INFO="weather"
        const val WEATHER_CITY="name"
        const val WEATHER_TEMP="main"
        const val WEATHER_VISIBILITY="visibility"
        const val WEATHER_WIND="wind"
        const val WEATHER_CLOUDS="clouds"
        const val WEATHER_MESSAGE="message"
        const val WEATHER_THROWABLE="throwable"
        const val WEATHER_DATE="date"
        const val WEATHER_LOCATION="sys"
        const val WEATHER_CODE="cod"
        //Weather End

        //Coordinate Start
        const val COORDINATE_LAT="lat"
        const val COORDINATE_LNG="lon"
        //Coordinate End

        //Summary Start
        const val SUMMARY_ID="id"
        const val SUMMARY_MAIN="main"
        const val SUMMARY_DESC="description"
        const val SUMMARY_ICON="icon"
        //Summary End

        //Detail Start
        const val DETAIL_TEMP="temp"
        const val DETAIL_FEELS_LIKE="feels_like"
        const val DETAIL_TEMP_MIN="temp_min"
        const val DETAIL_MAX="temp_max"
        const val DETAIL_PRESSURE="pressure"
        const val DETAIL_HUMIDITY="humidity"
        //Detail End

        //Wind Start
        const val WIND_SPEED="speed"
        const val WIND_DEGREE="deg"
        //Wind End

        //Cloud Start
        const val CLOUD_ALL="all"
        //Cloud End

        //Location Start
        const val LOCATION_COUNTRY="country"
        //Location End

        //MARVEL REGION
        //Base Response Start
        const val MARVEL_RESPONSE_CODE="code"
        const val MARVEL_RESPONSE_STATUS="status"
        const val MARVEL_RESPONSE_COPYRIGHT="copyright"
        const val MARVEL_RESPONSE_ATTR_TEXT="attributionText"
        const val MARVEL_RESPONSE_ATTR_HTML="attributionHTML"
        const val MARVEL_RESPONSE_ETAG="etag"
        const val MARVEL_RESPONSE_DATA="data"
        const val MARVEL_RESPONSE_RESULTS="results"
        //Base Response End


        //Image Start
        const val IMAGE_PATH="path"
        const val IMAGE_EXTENSION="extension"
        //Image End

        //Summary Start
        const val SUMMARY_NAME="name"
        const val SUMMARY_URI="resourceURI"
        //Summary End

    }
}