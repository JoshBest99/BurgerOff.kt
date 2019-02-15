package inc.josh.burgeroff.DataModels

class Person(var UUID: String, var name: String, var ratings: Ratings)

class Ratings(var taste: Int, var appearance: Int)