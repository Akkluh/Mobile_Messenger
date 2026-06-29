package com.example.messenger.data.network

class SoapClient {
    fun buildLoginRequest(login: String, password: String): String{
        return """
          <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:mes="http://example.com/messenger">
              <soapenv:Header/>
              <soapenv:Body>
                 <mes:LoginRequest>
                    <mes:login>${login}</mes:login>
                    <mes:password>${password}</mes:password>
                 </mes:LoginRequest>
              </soapenv:Body>
           </soapenv:Envelope>
        """.trimIndent()
    }
    fun buildChatsRequest(userId: Int): String {
        return """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:mes="http://example.com/messenger">
              <soapenv:Header/>
              <soapenv:Body>
                 <mes:GetChatsRequest>
                    <mes:Id>${userId}</mes:Id>
                 </mes:GetChatsRequest>
              </soapenv:Body>
           </soapenv:Envelope>
        """.trimIndent()
    }
}