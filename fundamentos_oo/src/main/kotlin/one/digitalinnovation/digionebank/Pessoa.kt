package one.digitalinnovation.digionebank

class Pessoa(private var name: String, private var cpf: String) {

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

}