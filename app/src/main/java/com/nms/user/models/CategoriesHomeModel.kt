package com.nms.user.models

class CategoriesHomeModel (private var course_name: String,
                           private var course_price: String,
                           private var course_rating: String,
                           private var course_image: Int) {

    // Getter and Setter
    fun getCourse_name(): String {
        return course_name
    }

    fun setCourse_name(course_name: String) {
        this.course_name = course_name
    }
    fun getCourse_price(): String {
        return course_price
    }

    fun setCourse_price(course_price: String) {
        this.course_price = course_price
    }

    fun getCourse_rating(): String {
        return course_rating
    }

    fun setCourse_rating(course_rating: String) {
        this.course_rating = course_rating
    }

    fun getCourse_image(): Int {
        return course_image
    }

    fun setCourse_image(course_image: Int) {
        this.course_image = course_image
    }

}
