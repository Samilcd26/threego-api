package com.main.GuideAPI.data.repository

import com.main.GuideAPI.data.models.helperModels.generalHelper.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@EnableJpaRepositories
@Repository
interface CommentRepository:JpaRepository<Comment,Long> {

}