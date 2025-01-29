package com.main.GuideAPI.data.models.helperModels.filterHelper

import com.main.GuideAPI.data.models.EventType
import com.main.GuideAPI.data.models.helperModels.organizerHelper.EventCategory
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.sql.Time
import java.time.LocalDate
import javax.persistence.EnumType
import javax.persistence.Enumerated

@AllArgsConstructor
@NoArgsConstructor
@Data
data class FilterModel(
    @Enumerated(EnumType.STRING)
    var eventType: EventType?=null,

    var country:String?=null,
    var city:String?=null,
    var userId:Long?=null,

    var online:Boolean?=null,
    var ticketNeed:Boolean?=null,

    //Yap
    var verifyAccount:Boolean?=null,

    //
    var endDate:LocalDate?=null,
    var startDate:LocalDate?=null,
    var startHour:Time?=null,
    var endHour:Time?=null,
    //
    var onlyFollow:Boolean?=null,
    var free:Boolean?=null,


    var minPrice:Int?=null,
    var maxPrice:Int?=null,

    @Enumerated(EnumType.STRING)
    var selectCategory:List<EventCategory>?= emptyList(),


)
