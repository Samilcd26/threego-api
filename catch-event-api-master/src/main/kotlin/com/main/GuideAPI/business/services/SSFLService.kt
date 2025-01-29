package com.main.GuideAPI.business.services

import com.main.GuideAPI.data.models.OrganizerModel
import com.main.GuideAPI.data.models.UserModel
import com.main.GuideAPI.data.models.helperModels.generalHelper.Comment

//SaveShareFollowLike

interface SSFLService {

    fun followOrganizer(userId:Long,organizerId:Long):List<Long>?
    fun unfollowOrganizer(userId:Long,organizerId:Long):List<Long>?

    fun likeEvent(userId:Long,eventId:Long):List<Long>?

    fun removeLikeEvent(userId:Long, eventId:Long):List<Long>?

    fun dislikeEvent(userId:Long,eventId:Long):List<Long>?

    fun removeDislikeEvent(userId:Long,eventId:Long):List<Long>?

    fun saveOrganizerEventFromUserStore(userId:Long,eventId:Long):List<Long>?
    fun removeOrganizerEventFromUserStore(userId:Long, eventId:Long):List<Long>?

    fun addCommentToEvent(userId:Long,eventId:Long,comment: Comment):List<Long>?
    fun removeCommentToEvent(userId:Long,eventId:Long,commentId:Long):List<Long>?
    fun addNewFollowed(organizerId:Long,followedId: Long): UserModel
    fun removeFollowed(organizerId:Long,followedId: Long): UserModel
    fun addNewFollower(organizerId:Long,followerId: Long): OrganizerModel
    fun removeFollower(organizerId:Long,followerId: Long): OrganizerModel
    fun getCommentList(commentIdList:List<Long>):List<Comment>
}
