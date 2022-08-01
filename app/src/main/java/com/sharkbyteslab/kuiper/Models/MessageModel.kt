package com.sharkbyteslab.kuiper.Models

import java.sql.Timestamp

data class MessageModel(
    var message : String? = "",
    var senderId : String? = "",
    var timestamp: Long? = 0

)
