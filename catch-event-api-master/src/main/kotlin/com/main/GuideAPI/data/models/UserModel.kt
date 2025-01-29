package com.main.GuideAPI.data.models

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.main.GuideAPI.data.models.helperModels.generalHelper.Comment
import com.main.GuideAPI.data.models.helperModels.userHelper.Role
import com.main.GuideAPI.data.models.helperModels.userHelper.TicketModel
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.UUID
import javax.persistence.*
import javax.validation.constraints.Pattern

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
data class UserModel(
    var accessToken:String?=null,
    var refreshToken:String?=null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null,
    var userName:String?=null,
    var firstName:String?=null,
    var lastName:String?=null,
    var telNumber:Long?=null,
    @JsonFormat(pattern="dd/MM/yyyy")
    var birtDate:LocalDate?=null,
    var userPassword:String?=null,
    @Pattern(regexp = "\b[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}\b.")
    var email:String?=null,
    var isOrganizer:Boolean?=false,

    @OneToOne(cascade = [(CascadeType.ALL)] ,fetch = FetchType.LAZY)
    var byOrganized:OrganizerModel?= null,
    var imageURL:String?="https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg",
    @ElementCollection
    var likeList:MutableList<Long>?=arrayListOf(),
    @ElementCollection
    var disLikeList:MutableList<Long>?=arrayListOf(),
    @ElementCollection
    var followOrganizer:MutableList<Long>?= arrayListOf(),


    @ElementCollection
    var userEventStore:MutableList<Long>?= arrayListOf(),

    @Enumerated(EnumType.STRING)
    var role:Role?=Role.USER,

    @OneToMany(cascade = [(CascadeType.ALL)] ,fetch = FetchType.LAZY)
    var ticketList:MutableList<TicketModel>?=arrayListOf(),

    ):UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role!!.name))
    }

    override fun getPassword(): String {
       return userPassword!!
    }

    override fun getUsername(): String? {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
       return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

// @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")