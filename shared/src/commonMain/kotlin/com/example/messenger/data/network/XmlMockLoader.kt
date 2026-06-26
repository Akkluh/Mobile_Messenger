package com.example.messenger.data.network

class XmlMockLoader {
    fun loadLoginResponse(request: String): String {
        if(request.contains("<login>admin</login>") && request.contains("<password>admin</password>")) {
            return """
                <User>
                <id>1</id>
                <login>admin</login>
                </User>
            """
        }
        return """
            <Error>
            <message>
            Wrong login or password
            </message>
            </Error>
        """
    }
}