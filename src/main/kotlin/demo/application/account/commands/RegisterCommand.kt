package demo.application.account.commands

import com.trendyol.kediatr.Command

class RegisterCommand(
    val username: String,
    var firstname: String,
    var lastname: String,
    var email: String,
    var password: String,
    var type: String) : Command