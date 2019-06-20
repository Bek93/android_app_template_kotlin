package kr.smobile.personaAI.view.model

import com.google.firebase.firestore.DocumentReference
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kr.smobile.personaAI.model.User
import java.util.*

class Timeline {

    @SerializedName("commentCount")
    @Expose
    var commentCount: Int? = null
    @SerializedName("content_type")
    @Expose
    var content_type: String? = null
    @SerializedName("expression")
    @Expose
    var expression: String? = null
    @SerializedName("likeCount")
    @Expose
    var likeCount: Int? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("timestamp")
    @Expose
    var timestamp: Date? = null

    var user: DocumentReference? = null

    var userObject = User()

}
