package com.main.GuideAPI.data.repository

import com.main.GuideAPI.data.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository
import java.util.Optional

@EnableJpaRepositories
@Repository
interface UserRepository :JpaRepository<UserModel,Long>{
    fun findByEmail(Email:String):Optional<UserModel>
}