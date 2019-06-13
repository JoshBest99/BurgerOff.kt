package inc.josh.burgeroff.DataModels

import java.io.Serializable

data class User(var uid: String?, var username: String?, var profileImageUrl: String?, var burgerImageUrl: String?, var team: Team?) : Serializable{

    constructor() : this("","","","", null)

}

data class Score(var appearance: String?, var burgerTaste: String?, var pattyTaste: String?) : Serializable{

    constructor() : this("","","")

}

data class Team(var name: String?, var uid: String?, var score: Score?, var members: ArrayList<User>?, var voteesUids: String) : Serializable{

    constructor() : this("","",null,null, "")

}