package com.example.qiita.data.apiResponse.getArticleList

data class GetArticleListResponse(
    val rendered_body: String,
    val body: String,
    val coediting: Boolean,
    val comments_count: Int,
    val created_at: String,
    val group: Group,
    val id: String,
    val likes_count: Int,
    val private: Boolean,
    val reactions_count: Int,
    val tags: List<Tag>,
    val title: String,
    val updated_at: String,
    val url: String,
    val user: User,
    val page_views_count: Int,
    val team_membership: TeamMembership,
)
