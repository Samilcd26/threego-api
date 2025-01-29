package com.main.GuideAPI.data.models

import com.main.GuideAPI.data.models.helperModels.filterHelper.DateRange
import com.main.GuideAPI.data.models.helperModels.filterHelper.TimeRange
import com.main.GuideAPI.data.models.helperModels.organizerHelper.EventCategory
import com.main.GuideAPI.data.models.helperModels.organizerHelper.EventPlatform

data class EventFilterModel(
    var verifyAccount:Boolean?=null,
    var eventPlatform: EventPlatform?=EventPlatform.OnlineAndLocal,
    var ticketNeed:Boolean?=null,
    var rangeLimit:Double?=10.0,
    var free:Boolean?=null,
    var categories:List<EventCategory>?= emptyList(),
    var dateRange: DateRange?=null,
    var timeRange: TimeRange?=null,

)
