package com.main.GuideAPI.data.models.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
data class UserInfoDto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,
    var userName:String? = null,
    var imageURL:String? = null,
    var accessToken:String?=null,
)
