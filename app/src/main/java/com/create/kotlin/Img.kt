package com.create.kotlin

/**
 * Created by 24540 on 2017/7/21.
 */
class Img {
    var url:String?=null
    var who:String?=null

    constructor(text: String,image: String){
        this.url = image
        this.who = text
    }
}