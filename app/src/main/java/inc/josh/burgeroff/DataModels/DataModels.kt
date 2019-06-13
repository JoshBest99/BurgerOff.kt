package inc.josh.burgeroff.DataModels

data class User(var uid: String?, var username: String?, var profileImageUrl: String?, var burgerImageUrl: String?, var team: Team?){

    constructor() : this("","","","", null)

}

data class Score(var appearance: String?, var burgerTaste: String?, var pattyTaste: String?){

    constructor() : this("","","")

}

data class Team(var name: String?, var uid: String?, var score: Score?, var members: ArrayList<User>?, var voteesUids: String){

    constructor() : this("","",null,null, "")

}