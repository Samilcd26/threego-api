package com.main.GuideAPI.data.models.helperModels.organizerHelper

import com.main.GuideAPI.data.models.EventType
import com.main.GuideAPI.data.models.UserModel
import com.main.GuideAPI.data.models.helperModels.generalHelper.Address
import com.main.GuideAPI.data.models.helperModels.generalHelper.Comment
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.reflect.jvm.internal.impl.protobuf.Internal

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
data class Event(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable=false,name = "event_id")
    var id:Long?=null,
    @Enumerated(EnumType.ORDINAL)
    var type:EventType?= EventType.EVENT,

    var organizerId:Long?=null,
    var title:String?=null,

    var status:String?="active",
    @Column(length = 800)
    var description:String?=null,
    var price:Int?=0,
    var currency:String?=null,
    var isOnline:Boolean?=false,
    var onlineEventUrl:String?="",
    var link1:String?="",
    var link2:String?="",
    var isTicketNeed:Boolean?=false,

    @Enumerated(EnumType.STRING)
    var eventPlatform:EventPlatform?=null,
    var capacity:Int?=null,
    var createdDate:LocalDateTime?=LocalDateTime.now(),
    var changedDate:LocalDateTime?=LocalDateTime.now(),
    var published:LocalDateTime?= LocalDateTime.now(),
    var imageUrl:String?="https://images.pexels.com/photos/2747449/pexels-photo-2747449.jpeg?cs=srgb&dl=pexels-wolfgang-2747449.jpg&fm=jpg",

    @ElementCollection
    var eventDateTime:MutableList<LocalDateTime>?= arrayListOf(),
    @ElementCollection
    var eventTag:MutableList<String>?=null,

    @ElementCollection
    var likeScore:MutableList<HashMap<Long,Int>>?= arrayListOf(),

    @OneToMany(cascade = [(CascadeType.ALL)] ,fetch = FetchType.LAZY)
    var eventPartners:MutableList<PartnerModel>?= arrayListOf(),
    @ElementCollection
    var commentList:MutableList<Long>?= arrayListOf(),



    @Enumerated(EnumType.ORDINAL)
    var category: EventCategory? = null,

    @ManyToMany(cascade = [(CascadeType.ALL)] ,fetch = FetchType.LAZY)
    var address: MutableList<Address>?= arrayListOf(),

    )

/*
    //Organizatör addreslerinden
    @ElementCollection
    var currentAddress: MutableList<Long> = arrayListOf(),

    //Yeni Adres
    @OneToMany(cascade = [(CascadeType.ALL)] ,fetch = FetchType.LAZY)
    var newAddress: MutableList<Address> = arrayListOf(),
    */

//Like columan name olarak kullanılınca hata veriyor.
//@JsonFormat(pattern="dd/MM/yyyy HH:mm")
//Like point ==>likescore/likePerson
