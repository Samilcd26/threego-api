package com.main.GuideAPI.business.services

import com.main.GuideAPI.data.models.OrganizerModel
import com.main.GuideAPI.data.models.UserModel
import org.springframework.stereotype.Service


interface UserService {

    fun createNewUser(user: UserModel): UserModel?
    fun updateUserById(id:Long,userModel: UserModel)
    fun deleteUserById(id:Long)
    fun getUserById(id:Long):UserModel?
    fun getFollowOrganizerListById(id:List<Long?>):List<OrganizerModel>?
    fun toBeOrganizer(userId:Long):UserModel?

}