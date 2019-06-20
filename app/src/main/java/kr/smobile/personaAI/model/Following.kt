package kr.smobile.personaAI.model

import com.google.firebase.firestore.DocumentReference

class Following {

    var user: DocumentReference? = null
    var following_user: DocumentReference? = null
    var timestamp: String? = null



    var userObject: User? = null
}