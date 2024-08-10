package com.axtech.appmanager.data.dto

import com.axtech.appmanager.util.BaseResponse
import com.google.gson.annotations.SerializedName

class AppResponse(
    @SerializedName("apps")
    val apps: List<AppDto>
) : BaseResponse()