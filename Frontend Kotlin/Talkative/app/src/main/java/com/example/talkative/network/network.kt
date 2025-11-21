package com.example.talkative.network

import android.view.Display
import com.example.talkative.model.CreatePostResponse.CreatePostResponse
import com.example.talkative.model.EditProfileResponse.EditProfileResponse
import com.example.talkative.model.FollowUnfollowResponse.FollowUnFollowResponse
import com.example.talkative.model.LoginRequest.LoginRequest
import com.example.talkative.model.LoginResponse.LoginResponse
import com.example.talkative.model.OwnProfileResponse.OwnProfileResponse
import com.example.talkative.model.PostLikeResponse.PostLikeResponse
import com.example.talkative.model.SearchResponse.SearchResponse
import com.example.talkative.model.SignupResponse.SignupResponse
import com.example.talkative.model.signupRequest.SignupRequest
import com.example.talkative.model.viewOthersProfile.OtherUsreProfileResponse
import com.example.talkative.utils.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface network {

    //for Login
    @POST(value= Constants.LOGIN_ENDPOINT)
    suspend fun LoginUser(
        @Body loginRequest: LoginRequest): LoginResponse

    //for signup
    @POST(value = Constants.SIGNUP_ENDPOINT)
    suspend fun SignupUser(
        @Body signupRequest: SignupRequest): SignupResponse

    //to search User
    @GET(value = Constants.SEARCH_USER)
    suspend fun SearchUser(
        @Query(value = "username") username: String): SearchResponse

    //Follow unfollow user
    @GET(value = Constants.Follow_UnFollowUser)
    suspend fun FollowUnfollowUser(
        @Query (value = "followedTo") username: String): FollowUnFollowResponse


    //To post content
    @Multipart
    @POST(value = Constants.Post_Url)
    suspend fun PostContent(
        @Part("caption") caption : RequestBody,
        @Part image : MultipartBody.Part
    ): CreatePostResponse


    //Get values post and every thing of own profile
    @GET(value = Constants.Own_profile)
    suspend fun OwnProfilePost(): OwnProfileResponse

    //Edit profile
    @Multipart
    @POST(value = Constants.Edit_Profile)
    suspend fun EditProfile(
        @Part(value = "displayName") displayName: RequestBody,
        @Part(value = "bio") Bio: RequestBody,
        @Part avatar : MultipartBody.Part?=null,
        @Part coverPhoto: MultipartBody.Part?=null
    ): EditProfileResponse


    //view other's Profile
    @GET(value = Constants.Others_Profile)
    suspend fun OtherUserProfile(
        @Path("username") username: String
    ): OtherUsreProfileResponse

    //like and unlike
    @GET(value = Constants.like_unlike)
    suspend fun LikeUnLike(
        @Query(value="id") id: String,
        @Query(value="likedUsername") likedUsername:String): PostLikeResponse

}