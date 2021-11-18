package com.oacikel.baseapp.db

class RoomConstants {
    companion object {
        const val USER = "user"
        const val ID = "id"
        //User Start
        const val USER_TABLE="USER_TABLE"
        const val USER_ID="id"
        const val USER_GUID="guid"
        const val USER_NAME="userName"
        const val USER_FIRST_NAME="firstName"
        const val USER_LAST_NAME="lastName"
        const val USER_MAIL="mail"
        const val USER_ROLE="userRole"
        const val USER_ROLE_ID="userRoleId"
        const val USER_MARKET_CODE="marketCode"
        const val USER_IMAGE_URL="imageUrl"
        //User End

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

        //URL Start
        const val URL_TYPE ="type"
        const val URL_URL ="url"
        //Url End

        //Image Start
        const val IMAGE_PATH="path"
        const val IMAGE_EXTENSION="extension"
        //Image End

        //Summary Start
        const val SUMMARY_NAME="name"
        const val SUMMARY_URI="resourceURI"
        //Summary End

        //Character Start
        const val CHARACTER_TABLE="CHARACTER_TABLE"
        const val CHARACTER_ID="id"
        const val CHARACTER_NAME="name"
        const val CHARACTER_DESCRIPTION="description"
        const val CHARACTER_MODIFIED="modified"
        const val CHARACTER_URI="resourceURI"
        const val CHARACTER_RESOURCE_URIS="urls"
        const val CHARACTER_THUMBNAIL="thumbnail"
        const val CHARACTER_COMICS="comics"
        const val CHARACTER_STORIES="stories"
        const val CHARACTER_EVENTS="events"
        const val CHARACTER_SERIES="series"
        //Character End

        //Comic Summary Start
        const val RESOURCE_AVAILABLE="available"
        const val RESOURCE_RETURNED="returned"
        const val RESOURCE_COLLECTION_URI="collectionURI"
        const val RESOURCE_ITEMS="items"
        //Comic Summary End

        //Comic Start
        const val COMIC_TABLE="COMIC_TABLE"
        const val COMIC_ID="id"
        const val COMIC_TITLE="title"
        const val COMIC_DESCRIPTION="description"
        const val COMIC_THUMBNAIL="thumbnail"
        //Comic End
    }
}