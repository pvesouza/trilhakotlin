package one.digitalinnovation.digionebank.enums

enum class ClientTypes(description: String) {
    PF("Phisical"),
    PJ("Juridic");

    private val description: String = description

    fun getDescription() : String{
        return this.description
    }
}