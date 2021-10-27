package one.digitalinnovation.digionebank.test

import one.digitalinnovation.digionebank.Bank
import one.digitalinnovation.digionebank.Pessoa
import one.digitalinnovation.digionebank.enums.ClientTypes

class BankTest {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val myBank = Bank(name = "BB", number = 10)
            println(myBank.name)
            val cliente : Pessoa = Pessoa("Pedro", "013.957.834-03", ClientTypes.PF)
            println(cliente.getType())
        }
    }
}