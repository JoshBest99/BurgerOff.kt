package inc.josh.burgeroff.DataModels


data class Ratings(var pattyTaste: Int?, var burgerTaste: Int?, var appearance: Int?, var ratedUids: String?, var scores: Array<Score>?){

    constructor() : this(0,0,0,"", null)

}

data class User(var uid: String?, var username: String?, var profileImageUrl: String?, var burgerImageUrl: String?, var ratings: Ratings?){

    constructor() : this("","","","", null)

}

data class Score(var appearance: String?, var burgerTaste: String?, var pattyTaste: String?){

    constructor() : this("","","")

}