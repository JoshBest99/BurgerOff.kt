package inc.josh.burgeroff.DataModels


class Ratings(var pattyTaste: Int, var burgerTaste: Int, var appearance: Int, var ratedUids: String){
    constructor() : this(0,0,0, "")
}

class User(var uid: String, var username: String, var profileImageUrl: String, var burgerImageUrl: String, var ratings: Ratings?){
    constructor() : this("", "","","", null)
}