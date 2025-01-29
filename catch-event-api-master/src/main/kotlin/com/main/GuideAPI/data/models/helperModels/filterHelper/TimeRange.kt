package com.main.GuideAPI.data.models.helperModels.filterHelper

import java.sql.Time
import java.util.*

data class TimeRange(
    var startTime: Time?=null,
    var endDTime: Time?=null,
)
