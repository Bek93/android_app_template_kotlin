package kr.smobile.personaAI.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.model.value.ReferenceValue

@IgnoreExtraProperties
class User {

    var name: String? = null
    var count_book: Int = 0
    var count_follower: Int = 0
    var count_following: Int = 0
    var email: String? = null
    var fcm_token: String = ""
    var profile: String? = null
    var representativePersona: DocumentReference? = null




    constructor(name: String?) {
        this.name = name
    }

    constructor()
}