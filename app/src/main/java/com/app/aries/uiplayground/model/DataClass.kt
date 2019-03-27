package com.app.aries.uiplayground.model

data class PostContent(
    var id:         Int=-1,
    var title:      String="",
    var abstract:   String="",
    var content:    String="",
    var picture:    String=""
)

data class bannerContent(
    var id:         Int=-1,
    var pic:        String = ""
)