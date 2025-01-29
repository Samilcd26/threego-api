package com.main.GuideAPI.token

import com.main.GuideAPI.data.models.UserModel
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
class TokenModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserModel,

    var token: String,

    @Enumerated(EnumType.STRING)
    var tokenType: TokenType,

    var expired: Boolean,

    var revoked: Boolean
) {
    // Hibernate için boş constructor
    constructor() : this(
        id = null,
        user = UserModel(),  // UserModel sınıfında da boş constructor olmalı
        token = "",
        tokenType = TokenType.BEARER,
        expired = false,
        revoked = false
    )
}

