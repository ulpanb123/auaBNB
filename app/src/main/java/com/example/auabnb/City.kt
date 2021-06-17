package com.example.auabnb

class City {

    private  lateinit var objectId : String
    private lateinit var title : String
    private lateinit var image : String


    fun getObjectId() : String {
        return objectId
    }

    fun setObjectTitle(objectId : String) {
        this.objectId = objectId
    }

    fun getTitle() : String {
        return title
    }

    fun setTitle(title : String) {
        this.title = title
    }

    fun getImage() : String {
        return image
    }

    fun setImage(image : String) {
        this.image = image
    }



}
