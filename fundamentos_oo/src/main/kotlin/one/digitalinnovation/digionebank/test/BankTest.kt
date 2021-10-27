package one.digitalinnovation.digionebank.test

import one.digitalinnovation.digionebank.Bank

class BankTest {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val myBank = Bank(name = "BB", number = 10)
            println(myBank.name)
        }
    }
}