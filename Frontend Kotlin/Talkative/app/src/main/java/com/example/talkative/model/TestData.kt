package com.example.talkative.model

data class User(
    val id: String,
    val name: String,
    val username: String,
    val avatar: String,
    val bio: String? = null,
    val followers: Int,
    val following: Int,
    val posts: Int,
    val isFollowing: Boolean = false,
    val isVerified: Boolean = false,
    val location: String? = null,
    val website: String? = null,
    val joinDate: String? = null,
    val coverImage: String? = null,
    val category: String? = null
)

data class Post(
    val id: String,
    val user: User,
    val content: String,
    val image: String? = null,
    val likes: Int,
    val comments: Int,
    val shares: Int,
    val timestamp: String,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false
)


object MockData {
    val mockUser = User(
        id = "user-1",
        name = "Your Name",
        username = "you",
        avatar = "https://images.unsplash.com/photo-1539605480396-a61f99da1041?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJzb24lMjBwb3J0cmFpdCUyMHByb2ZpbGV8ZW58MXx8fHwxNzU3NzcxMjg5fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
        bio = "Digital creator sharing my journey through life, travel, and everything in between. Coffee lover ☕ • Adventure seeker 🏔️",
        followers = 1250,
        following = 892,
        posts = 234,
        location = "San Francisco, CA",
        website = "yourwebsite.com",
        joinDate = "Joined March 2020",
        coverImage = "https://images.unsplash.com/photo-1501785888041-af3ef285b470?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0cmF2ZWwlMjBsYW5kc2NhcGV8ZW58MXx8fHwxNzU3NzMyMzY3fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
    )

    val mockUsers = listOf(
        User(
            id = "1",
            name = "Emma Wilson",
            username = "emmawilson",
            avatar = "https://images.unsplash.com/photo-1539605480396-a61f99da1041?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJzb24lMjBwb3J0cmFpdCUyMHByb2ZpbGV8ZW58MXx8fHwxNzU3NzcxMjg5fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            followers = 125000,
            following = 890,
            posts = 456,
            isVerified = true,
            bio = "UI/UX Designer at Google. Passionate about creating beautiful, accessible interfaces.",
            category = "design"
        ),
        User(
            id = "2",
            name = "Alex Chen",
            username = "alexchen",
            avatar = "https://images.unsplash.com/photo-1625837183027-3205998d6079?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsaWZlc3R5bGUlMjBzb2NpYWwlMjBtZWRpYXxlbnwxfHx8fDE3NTc3NzEyODl8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            followers = 89500,
            following = 1200,
            posts = 234,
            isVerified = true,
            bio = "Senior Software Engineer & Tech Lead. Building scalable systems.",
            category = "tech"
        ),
        User(
            id = "3",
            name = "Sarah Johnson",
            username = "sarahjohnson",
            avatar = "https://images.unsplash.com/photo-1623715537851-8bc15aa8c145?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0ZWNobm9sb2d5JTIwd29ya3NwYWNlfGVufDF8fHx8MTc1NzcwNzk4Nnww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            followers = 67800,
            following = 567,
            posts = 189,
            bio = "Content Creator & Digital Marketing Strategist.",
            category = "creators"
        )
    )

    val mockPosts = listOf(
        Post(
            id = "1",
            user = User(
                id = "emma",
                name = "Emma Wilson",
                username = "emmawilson",
                avatar = "https://images.unsplash.com/photo-1539605480396-a61f99da1041?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZXJzb24lMjBwb3J0cmFpdCUyMHByb2ZpbGV8ZW58MXx8fHwxNzU3NzcxMjg5fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                followers = 125000,
                following = 890,
                posts = 456
            ),
            content = "Just had the most amazing brunch with friends! There's nothing better than good food and great company on a weekend. What's everyone else up to today? 🥐☕️",
            image = "https://images.unsplash.com/photo-1532980400857-e8d9d275d858?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxmb29kJTIwcGhvdG9ncmFwaHl8ZW58MXx8fHwxNzU3NjczMzc2fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            likes = 42,
            comments = 8,
            shares = 3,
            timestamp = "2h",
            isLiked = true
        ),
        Post(
            id = "2",
            user = User(
                id = "alex",
                name = "Alex Chen",
                username = "alexchen",
                avatar = "https://images.unsplash.com/photo-1625837183027-3205998d6079?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsaWZlc3R5bGUlMjBzb2NpYWwlMjBtZWRpYXxlbnwxfHx8fDE3NTc3NzEyODl8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                followers = 89500,
                following = 1200,
                posts = 234
            ),
            content = "Working from this beautiful mountain cabin for the week. Sometimes a change of scenery is all you need to boost creativity and productivity. Remote work has its perks! 🏔️💻",
            image = "https://images.unsplash.com/photo-1501785888041-af3ef285b470?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0cmF2ZWwlMjBsYW5kc2NhcGV8ZW58MXx8fHwxNzU3NzMyMzY3fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            likes = 128,
            comments = 23,
            shares = 12,
            timestamp = "4h"
        )
    )
}
