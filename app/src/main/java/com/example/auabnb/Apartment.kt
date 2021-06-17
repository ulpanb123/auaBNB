package com.example.auabnb

import kotlin.properties.Delegates

class Apartment {
    private lateinit var objectId : String
    private lateinit var image : String
    private lateinit var title : String
    private var price : Int = 0


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

    fun getPrice() : Int {
        return price
    }

    fun setPrice(price : Int) {
        this.price = price
    }



}