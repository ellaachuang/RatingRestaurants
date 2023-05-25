package com.example.loginandregistration

// object keyword makes it so all the functions are
// static functions
object RegistrationUtil {
    // use this in the test class for the is username taken test
    // make another similar list for some taken emails

    var existingUsers = listOf("cosmicF", "cosmicY", "bob", "alice")
    var existingEmails = listOf("cosmicF@gmail.com","cosmicY@gmail.com","bob@gmail.com", "alice@gmail.com")
    //List<String> users = new ArrayList<String>()
//    you can use listOf<type>() instead of making the list & adding individually
//    List<String> blah = new ArrayList<String>();
//    blah.add("hi")
//    blah.add("hello")

    // isn't empty
    // already taken
    // minimum number of characters is 3
    fun validateUsername(username: String) : Boolean {
        var x = existingUsers.size-1
        while (x >= 0){
            if (existingUsers[x].equals(username)) {
                return false
            }
            else{
                x--
            }
        }
        if (username == null){
            return false
        }
        if (username.length < 3){
            return false
        }
        return true
    }

    // make sure meets security requirements (deprecated ones that are still used everywhere)
    // min length 8 chars x
    // at least one digit
    // at least one capital letter
    // both passwords match x
    // not empty x
    fun validatePassword(password : String, confirmPassword: String) : Boolean {
        if (password.length < 8){
            return false
        }
        if (password != confirmPassword){
            return false
        }
        if (password == null){
            return false
        }
        if (password.contains("[0-9]".toRegex()) && password.contains("[A-Z]".toRegex())) {
            return true
        }
        return false
    }

    // isn't empty
    fun validateName(name: String) : Boolean {
        if (name.length > 0 && name != null){
            return true
        }
        return false
    }

    // isn't empty
    // make sure the email isn't used
    // make sure it's in the proper email format user@domain.tld
    fun validateEmail(email: String) : Boolean {
        var isEmpty = false
        if (email.length > 0 && email != null){
            isEmpty = true
        }

        var x = existingEmails.size-1
        while (x >= 0){
            if (existingEmails[x].equals(email)) {
                return false
            }
            else{
                x--
            }
        }

        if (email.contains("@".toRegex()) && email.contains(".com".toRegex()) && isEmpty) {
            return true
        }
        return false


    }
}