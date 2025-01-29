package com.main.GuideAPI.data.models.helperModels.userHelper

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
data class TicketModel(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id:String?=null,
    var eventId:Long?=null,
    var name:String?=null,
    var imageUrl:String?=null,
    var description:String?=null,
    var createdDate: LocalDateTime?= LocalDateTime.now(),
    var changedDate: LocalDateTime?=LocalDateTime.now(),
    var status:String?=null,
    var price:Int?=0,

    )
