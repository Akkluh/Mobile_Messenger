package com.example.messenger.data.network

class SoapClient {
    fun buildLoginRequest(login: String, password: String): String{
        return """
           <Envelope>
            <Login>
            <login>$login</login>
            <password>$password</password>
            </Login>
            </Envelope>
        """
    }
}