package com.app.aries.uiplayground.model

class ViewPager1Model{
    // id= -1: banner
    // id= -2: no more
    var postContentList = mutableListOf(PostContent(id=-1))

    fun updateFakePostContentList(amount: Int): MutableList<PostContent> {
        for (i in 1..amount){
            if(postContentList.size -1 >= fakeContentListMax){
                if(postContentList.last().id != -2) postContentList.add(PostContent(id=-2))
                break
            }
            postContentList.add(
                PostContent(id=postContentList.size, title = (postContentList.size).toString())
            )
        }
        return postContentList
    }

    private var fakeContentListMax = 30
    fun getListMax():Int = fakeContentListMax
}