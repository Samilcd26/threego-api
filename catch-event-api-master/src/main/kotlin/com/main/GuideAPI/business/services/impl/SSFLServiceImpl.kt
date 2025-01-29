package com.main.GuideAPI.business.services.impl

import com.main.GuideAPI.business.services.SSFLService
import com.main.GuideAPI.data.models.OrganizerModel
import com.main.GuideAPI.data.models.UserModel
import com.main.GuideAPI.data.models.dto.UserInfoDto
import com.main.GuideAPI.data.models.helperModels.generalHelper.Comment
import com.main.GuideAPI.data.models.helperModels.organizerHelper.Event
import com.main.GuideAPI.data.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class SSFLServiceImpl(
    @Autowired
    var userRepository: UserRepository,
    @Autowired
    var organizerRepository: OrganizerRepository,
    @Autowired
    var eventRepository: EventRepository,
    @Autowired
    var commentRepository: CommentRepository
) : SSFLService {
    override fun followOrganizer(userId: Long, organizerId: Long): List<Long>? {
        var currentUser: UserModel = userRepository.findById(userId).orElseThrow()
        var tempUser: ArrayList<Long>? = ArrayList()


        currentUser.followOrganizer?.map { s -> tempUser?.add(s) }
        tempUser?.add(organizerId)
        currentUser.followOrganizer = tempUser
        userRepository.save(currentUser);

        var currentOrganizer: OrganizerModel = organizerRepository.findById(organizerId).orElseThrow()
        var tempOrganizer: ArrayList<Long>? = ArrayList()
        currentOrganizer.followerList?.map { s->tempOrganizer?.add(s) }
        currentOrganizer.followerList?.add(userId)

        organizerRepository.save(currentOrganizer)

        return tempUser
    }

    override fun unfollowOrganizer(userId: Long, organizerId: Long): List<Long>? {
        var currentUser: UserModel = userRepository.findById(userId).orElseThrow()
        var tempUser: ArrayList<Long>? = ArrayList()


        currentUser.followOrganizer?.map { s -> tempUser?.add(s) }
        tempUser?.remove(organizerId)
        currentUser.followOrganizer = tempUser
        userRepository.save(currentUser);

        var currentOrganizer: OrganizerModel = organizerRepository.findById(organizerId).orElseThrow()
        var tempOrganizer: ArrayList<Long>? = ArrayList()
        currentOrganizer.followerList?.map { s->tempOrganizer?.add(s) }
        currentOrganizer.followerList?.remove(userId)

        organizerRepository.save(currentOrganizer)

        return tempUser
    }

    override fun likeEvent(userId: Long, eventId: Long): List<Long>? {
        var currentUser: UserModel = userRepository.findById(userId).orElseThrow()
        var temp: ArrayList<Long>? = ArrayList()
        currentUser.likeList?.map { s -> temp?.add(s) }
        temp?.add(eventId)
        currentUser.likeList = temp
        userRepository.save(currentUser);
        return temp
    }

    override fun removeLikeEvent(userId: Long, eventId: Long): List<Long>? {
        var currentUser: UserModel = userRepository.findById(userId).orElseThrow()
        var temp: ArrayList<Long>? = ArrayList()
        currentUser.likeList?.map { s -> temp?.add(s) }
        temp?.remove(eventId)
        currentUser.likeList = temp
        userRepository.save(currentUser);
        return temp
    }

    override fun dislikeEvent(userId: Long, eventId: Long): List<Long>? {
        var currentUser: UserModel = userRepository.findById(userId).orElseThrow()
        var temp: ArrayList<Long>? = ArrayList()
        currentUser.disLikeList?.map { s -> temp?.add(s) }
        temp?.remove(eventId)
        currentUser.disLikeList = temp
        userRepository.save(currentUser);
        return temp
    }

    override fun removeDislikeEvent(userId: Long, eventId: Long): List<Long>? {
        var currentUser: UserModel = userRepository.findById(userId).orElseThrow()
        var temp: ArrayList<Long>? = ArrayList()
        currentUser.disLikeList?.map { s -> temp?.add(s) }
        temp?.remove(eventId)
        currentUser.disLikeList = temp
        userRepository.save(currentUser);
        return temp
    }

    override fun saveOrganizerEventFromUserStore(userId: Long, eventId: Long): List<Long>? {
        var currentUser: UserModel = userRepository.findById(userId).orElseThrow()
        var temp: ArrayList<Long>? = ArrayList()
        currentUser.userEventStore?.map { s -> temp?.add(s) }
        temp?.add(eventId)
        currentUser.userEventStore = temp
        userRepository.save(currentUser);
        return temp
    }

    override fun removeOrganizerEventFromUserStore(userId: Long, eventId: Long): List<Long>? {
        var currentUser: UserModel = userRepository.findById(userId).orElseThrow()
        var temp: ArrayList<Long>? = ArrayList()
        currentUser.userEventStore?.map { s -> temp?.add(s) }
        temp?.remove(eventId)
        currentUser.userEventStore = temp
        userRepository.save(currentUser);
        return temp
    }

    override fun addCommentToEvent(userId:Long, eventId:Long, comment: Comment): List<Long>? {



        comment.createdDate= LocalDateTime.now()
        comment.changedDate= LocalDateTime.now()


        var user:UserModel = userRepository.findById(userId).orElseThrow()


        var commentUser:UserInfoDto = UserInfoDto(id = userId, userName = user.userName, imageURL = user.imageURL )
        comment.addedUser=commentUser
        commentRepository.save(comment)
        var currentEvent:Event=eventRepository.findById(eventId).orElseThrow()
        var tempComment:ArrayList<Long> = ArrayList()




        currentEvent.commentList?.map { s->tempComment.add(s) }
        tempComment?.add(comment.id!!)
        currentEvent.commentList=tempComment
        eventRepository.save(currentEvent)



        return tempComment
    }

    override fun removeCommentToEvent(userId: Long, eventId: Long, commentId: Long): List<Long>? {

        var currentEvent:Event=eventRepository.findById(eventId).orElseThrow()
        var tempComment:ArrayList<Long> = ArrayList()


        currentEvent.commentList?.map { s->tempComment.add(s) }
        tempComment?.remove(commentId)
        currentEvent.commentList=tempComment
        eventRepository.save(currentEvent)



        return tempComment
    }

    override fun addNewFollowed(organizerId: Long, followedId: Long): UserModel {
        //Performs muhtemelen düşük olacak

        var currentUser:UserModel=userRepository.findById(followedId).orElseThrow()
        var temp:ArrayList<Long> =ArrayList()

        currentUser.followOrganizer?.map { s->temp.add(s) }
        temp.add(organizerId)
        currentUser.followOrganizer=temp

        userRepository.save(currentUser)
        return currentUser
    }

    override fun removeFollowed(organizerId: Long, followedId: Long): UserModel {
        var currentUser:UserModel=userRepository.findById(followedId).orElseThrow()
        var temp:ArrayList<Long> =ArrayList()

        currentUser.followOrganizer?.map { s->temp.add(s) }
        temp.remove(organizerId)
        currentUser.followOrganizer=temp

        userRepository.save(currentUser)
        return currentUser
    }

    override fun addNewFollower(organizerId: Long, followerId: Long): OrganizerModel {
        var currentOrganizer:OrganizerModel=organizerRepository.findById(followerId).orElseThrow()
        var temp:ArrayList<Long> =ArrayList()

        currentOrganizer.followerList?.map { s->temp.add(s) }
        temp.add(followerId)
        currentOrganizer.followerList=temp

        organizerRepository.save(currentOrganizer)
        return currentOrganizer
    }

    override fun removeFollower(organizerId: Long, followerId: Long): OrganizerModel {
        var currentOrganizer:OrganizerModel=organizerRepository.findById(followerId).orElseThrow()
        var temp:ArrayList<Long> =ArrayList()

        currentOrganizer.followerList?.map { s->temp.add(s) }
        temp.remove(followerId)
        currentOrganizer.followerList=temp

        organizerRepository.save(currentOrganizer)
        return currentOrganizer
    }

    override fun getCommentList(commentIdList: List<Long>): List<Comment> {

        var iteratorList=commentIdList.asIterable()
        return commentRepository.findAllById(iteratorList)
    }
}