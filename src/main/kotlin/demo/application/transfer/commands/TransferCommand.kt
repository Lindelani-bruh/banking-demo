package demo.application.transfer.commands

import com.trendyol.kediatr.Command

class TransferCommand(
    val amount: Double,
    val destinationAccount:String,
    val currency: String,
    val fromAccount: String) : Command