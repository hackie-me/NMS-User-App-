package com.nms.user.models

class TopCategoriesHomeModel (private var course_name: String,
                              private var course_image: Int) {

    // Getter and Setter
    fun getCourse_name(): String {
        return course_name
    }

    fun setCourse_name(course_name: String) {
        this.course_name = course_name
    }
    fun getCourse_image(): Int {
        return course_image
    }

    fun setCourse_image(course_image: Int) {
        this.course_image = course_image
    }

}
