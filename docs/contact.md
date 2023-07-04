#Contact API SPEC

##Create Contact
Endpoint: POST /api/contacts

Request header:

X-API-TOKEN : Token (wajib)

Request Body:
```json
{
  "fistName": "Bagas",
  "lastName":"Wiji",
  "email": "bagas@example.com",
  "phone": "087123123123"
}
```

Response Body(Success):
```json
{
  "data": {
    "id": "random string",
    "fistName": "Bagas",
    "lastName":"Wiji",
    "email": "bagas@example.com",
    "phone": "087123123123"
  }
}
```

Response Body(Failed):
```json
{
  "errors": "format email/phone salah"
}
```

## Update Contact
Endpoint:PUT /api/contacts/idContact

Request header:

X-API-TOKEN : Token (wajib)

Request Body:
```json
{
  "fistName": "Bagas",
  "lastName":"Wiji",
  "email": "bagas@example.com",
  "phone": "087123123123"
}
```

Response Body(Success):
```json
{
  "data": {
    "id": "random string",
    "fistName": "Bagas",
    "lastName":"Wiji",
    "email": "bagas@example.com",
    "phone": "087123123123"
  }
}
```

Response Body(Failed):
```json
{
  "errors": "format email/phone salah"
}
```

##Get Contact
Endpoint: GET /api/contacts/{idContacts}

Request header:

X-API-TOKEN : Token (wajib)

Response Body(Success):
```json
{
  "data": {
    "id": "random string",
    "fistName": "Bagas",
    "lastName":"Wiji",
    "email": "bagas@example.com",
    "phone": "087123123123"
  }
}
```

Response Body(Failed,404):
```json
{
  "errors": "contact not found"
}
```

##Search Contact
Endpoint: GET /api/contacts

Query Param:
-name : String, contact first name or last name using like query, optional
-phone:String, contact phone using like query, optional
-email:String,contact email using like query, optional
-page: Int, start 0, default 0
size: Int, default 10

Request header:

X-API-TOKEN : Token (wajib)

Response Body(Success):
```json
{
  "data": [
    {
      "fistName": "Bagas",
      "lastName":"Wiji",
      "email": "bagas@example.com",
      "phone": "087123123123"
    }
  ],
  "paging": {
    "currentPage": 0,
    "totalPage": 10,
    "size": 10
  }
}
```

Response Body(Success):

##Remove Contact
Endpoint:DELETE /api/contacts/{idContacts}

Request header:

X-API-TOKEN : Token (wajib)

Response Body(Success):
```json
{
  "data:"OK
}
```

Response Body(Failed,404):
```json
{
  "errors": "contact not found"
}
```