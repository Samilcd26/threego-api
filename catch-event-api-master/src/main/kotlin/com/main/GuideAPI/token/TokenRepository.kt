package com.main.GuideAPI.token

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional


interface TokenRepository : JpaRepository<TokenModel?, Int?> {
    @Query("select t from TokenModel t inner join  UserModel u " +
            "on t.user.id=u.id " +
            "where u.id=:id and (t.expired =false or t.revoked =false )")
    fun findAllValidTokenByUser(id: Long?): List<TokenModel?>?
    fun findByToken(token: String?): Optional<TokenModel?>?
}

/*
*
*
* value = """
      select t from Token t inner join User u
      on t.user.id = u.id
      where u.id = :id and (t.expired = false or t.revoked = false)

      """.trimIndent()*/