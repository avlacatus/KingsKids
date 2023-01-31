package ro.adventist.copiiiregelui.models

data class UserProfile(
    val name: String,
    val parents: String,
    val church: String,
    val coach: String,
    val pastor: String,
    val school: String,
    val grade: String
) {
    constructor() : this("", "", "", "", "", "", "")
}