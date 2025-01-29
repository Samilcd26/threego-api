package com.main.GuideAPI.controller

import com.main.GuideAPI.business.services.SSFLService
import com.main.GuideAPI.business.services.UserService
import com.main.GuideAPI.data.models.OrganizerModel
import com.main.GuideAPI.data.models.UserModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController (val userService: UserService,val ssflService: SSFLService){


    @PostMapping("/create")
    fun createNewUser(@RequestBody userModel: UserModel): ResponseEntity<UserModel> {
        return ResponseEntity.ok(userService.createNewUser(userModel))
    }
    @GetMapping("/find")
    fun getUserById(@RequestParam id:Long):UserModel?{
        return userService.getUserById(id)
    }

    @GetMapping("/getFollowers")
    fun getFollowerByUser(@RequestBody followerIdList: List<Long>):List<OrganizerModel>?{
        return userService.getFollowOrganizerListById(followerIdList)
    }

    @GetMapping("/toBeOrganizer")
    fun toBeOrganizer(@RequestParam userId:Long):UserModel?{
        return userService.toBeOrganizer(userId)
    }

}