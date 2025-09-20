package com.example.talkative.network

import com.example.talkative.model.CreatePostResponse.CreatePostResponse
import com.example.talkative.model.FollowUnfollowResponse.FollowUnFollowResponse
import com.example.talkative.model.LoginRequest.LoginRequest
import com.example.talkative.model.LoginResponse.LoginResponse
import com.example.talkative.model.OwnProfileResponse.OwnProfileResponse
import com.example.talkative.model.SearchResponse.SearchResponse
import com.example.talkative.model.SignupResponse.SignupResponse
import com.example.talkative.model.signupRequest.SignupRequest
import com.example.talkative.utils.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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


    @GET(value = Constants.Own_profile)
    suspend fun OwnProfilePost(): OwnProfileResponse

}