package com.example.adiplar.models

import java.io.Serializable

class User : Serializable {
    var idDate:String? = null
    var nameAndLastName:String? = null
    var bornAge:Int? = null
    var deadAge:Int? = null
    var imageUrl:String? = null
    var mType:String? = null
    var infoUser:String? = null
    var saveAndDelete:Boolean? = null

    constructor()
    constructor(
        idDate: String,
        nameAndLastName: String?,
        bornAge: Int?,
        deadAge: Int?,
        imageUrl: String?,
        mType: String?,
        infoUser: String?,
        saveAndDelete: Boolean?
    ) {
        this.idDate = idDate
        this.nameAndLastName = nameAndLastName
        this.bornAge = bornAge
        this.deadAge = deadAge
        this.imageUrl = imageUrl
        this.mType = mType
        this.infoUser = infoUser
        this.saveAndDelete = saveAndDelete
    }
}