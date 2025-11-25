package com.example.talkative.utils

object Constants {
    const val BASE_URL="https://talkative-1-0-1-2.onrender.com/";
    const val LOGIN_ENDPOINT="api/login"

    const val SIGNUP_ENDPOINT="api/register"

    const val SEARCH_USER="search"

    const val Follow_UnFollowUser="follow"

    const val Post_Url="post"

    const val Own_profile="profile"

    const val Edit_Profile="profile/edit"

    //view other's profile
    const val Others_Profile="profile/{username}"


    //like unlike
    const val like_unlike="post/like"

    //get followers
    const val get_followers="followers/{username}"

    //get Following
    const val get_following ="following/{username}"

    //Delete Post
    const val delete_post = "/post/delete/{postid}"

    //get comments on POst
    const val GET_COMMENT="/comment"

    //Post Comments
    const val POST_COMMENT="/comment"

    //Delete Comment
    const val DELETE_COMMENT="/comment/delete/{commentId}"
}
