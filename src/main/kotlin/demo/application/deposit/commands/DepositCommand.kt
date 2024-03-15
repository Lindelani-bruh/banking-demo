package demo.application.deposit.commands

import com.trendyol.kediatr.Command

class DepositCommand(val amount: Double, val currency: String, val accountId:String) : Command


