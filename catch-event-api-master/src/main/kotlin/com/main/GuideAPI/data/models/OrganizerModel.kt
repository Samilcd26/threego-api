package com.main.GuideAPI.data.models

import com.main.GuideAPI.data.models.helperModels.generalHelper.Address
import com.main.GuideAPI.data.models.helperModels.organizerHelper.Event
import com.main.GuideAPI.data.models.helperModels.organizerHelper.OrganizerType
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Pattern


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
data class OrganizerModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?=null,
    @Pattern(regexp = "\b[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}\b.")
    var email:String? = null,
    var organizerName:String?=null,
    @Enumerated(EnumType.ORDINAL)
    var organizerType:OrganizerType?=null,
    var title:String? = null,
    var verify:Boolean?=false,
    var description: String? = null,
    var image:String? = null,
    var eventLimit:Int?=4,
    var eventLimitRefreshDate:LocalDateTime?= LocalDateTime.now().plusMonths(1),
    @ElementCollection
    var followedList:MutableList<Long>?= arrayListOf(),

    @ElementCollection
    var followerList:MutableList<Long>?= arrayListOf(),


    @OneToMany(cascade = [(CascadeType.ALL)] ,fetch = FetchType.LAZY)
    var address:MutableList<Address>? = arrayListOf(),

    @OneToMany(cascade = [(CascadeType.ALL)] ,fetch = FetchType.LAZY)
    var event:MutableList<Event>?=arrayListOf(),

    )
