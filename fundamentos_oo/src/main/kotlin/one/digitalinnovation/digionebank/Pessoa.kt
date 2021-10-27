package one.digitalinnovation.digionebank

import one.digitalinnovation.digionebank.enums.ClientTypes

class Pessoa(private var name: String,
             private var cpf: String,
             private var type: ClientTypes) {

    fun getCpf() : String{
        return this.cpf
    }

    fun setCpf(cpf:String){
        this.cpf = cpf
    }

    fun getName() : String {
        return this.name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setPersonType(type : ClientTypes){
        this.type = type;
    }

    fun getType() : String{
        return type.getDescription()
    }

}