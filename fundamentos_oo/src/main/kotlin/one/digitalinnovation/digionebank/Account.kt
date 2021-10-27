package one.digitalinnovation.digionebank

class Account(private var agency:String,
              private var accountNumber:String,
              private var accountBalance: Double) {

    fun getBalance():Double{
        return this.accountBalance
    }

    fun deposit(amount:Double){
        this.accountBalance += amount
    }

    fun extract(amount:Double):Boolean{
        if (verifyAccountBalance(amount)){
            this.accountBalance -= amount
            return true;
        }else{
            return false;
        }

    }

    fun getAccountNumber():String{
        return this.accountNumber
    }

    fun getAccountAgency():String{
        return this.agency
    }

    fun getInfoAccount():String{
        return "Agency: " + this.getAccountAgency() + "\n" + "Account Number: " +
                this.getAccountNumber()
    }

    private fun verifyAccountBalance(amount:Double):Boolean{
        if (amount > this.accountBalance){
            return false
        }
        return true
    }

}