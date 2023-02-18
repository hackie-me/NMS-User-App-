package com.nms.user.repo

import com.nms.user.models.ReviewModel

class ReviewRepository {
    companion object{
        // Function to add new review
        fun addReview(review: ReviewModel) : Boolean {
            return true
        }

        // Function to update review by id
        fun updateReviewById(id: String, review: ReviewModel) : Boolean {
            return true
        }

        // Function to delete review by id
        fun deleteReviewById(id: String) : Boolean {
            return true
        }

        // Function to get Rating by product id
        fun getRatingByProductId(id: String) : Float {
            return 0.0f
        }

        // Function to



    }
}