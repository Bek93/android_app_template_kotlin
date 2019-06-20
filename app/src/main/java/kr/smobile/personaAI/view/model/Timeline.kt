package kr.smobile.personaAI.view.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Timeline {

    @SerializedName("commentCount")
    @Expose
    internal var commentCount: Int = 0
    @SerializedName("content_type")
    @Expose
    internal var content_type: String? = null
    @SerializedName("expression")
    @Expose
    internal var expression: String? = null
    @SerializedName("likeCount")
    @Expose
    internal var likeCount: Int = 0
    @SerializedName("status")
    @Expose
    internal var status: Int = 0
    @SerializedName("timestamp")
    @Expose
    internal var timestamp: String? = null
    @SerializedName("user")
    @Expose
    internal var user: String? = null

}
