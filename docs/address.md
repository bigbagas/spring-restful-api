#ADDRESS API Specs

##Create Address 

Endpoint: POST /api/contacts/{idContacts}/addresses

Request header:

X-API-TOKEN : Token (wajib)

Request Body:
```json
{
  "street": "Jalan. no 1",
  "city": "klaten",
  "province": "jateng",
  "country": "indonesia",
  "postalCode": "57482"
}
```

Response Body (success):
```json
{
  "data": {
    "id": "randimString",
    "street": "Jalan. no 1",
    "city": "klaten",
    "province": "jateng",
    "country": "indonesia",
    "postalCode": "57482"
  }
}
```

Response Body (failed):

```json
{
  "errors":"contact not found"
}
```

##Update Address

Endpoint: PUT /api/contact/{idContact}/addresses/{idAddress}
Request header:

X-API-TOKEN : Token (wajib)

Request Body:
```json
{
  "street": "Jalan. no 1",
  "city": "klaten",
  "province": "jateng",
  "country": "indonesia",
  "postalCode": "57482"
}
```

Response Body (success):

```json
{
  "data": {
    "id": "randimString",
    "street": "Jalan. no 1",
    "city": "klaten",
    "province": "jateng",
    "country": "indonesia",
    "postalCode": "57482"
  }
}
```

Response Body (failed):
```json
{
  "errors":"address not found"
}
```

## Get Address

Endpoint: GET /api/contact/{idContact}/addresses/{idAddress}

Request header:

X-API-TOKEN : Token (wajib)

Response Body (success):

```json
{
  "data": {
    "id": "randimString",
    "street": "Jalan. no 1",
    "city": "klaten",
    "province": "jateng",
    "country": "indonesia",
    "postalCode": "57482"
  }
}
```

Response Body (failed):
```json
{
  "errors":"address not found"
}
```

## Remove Address
Endpoint: DELETE /api/contact/{idContact}/addresses/{idAddress}

Request header:

X-API-TOKEN : Token (wajib)

Response Body (success):

```json
{
  "data": "OK"
}
```

Response Body (failed):
```json
{
  "errors":"address not found"
}
```

##List Address
Endpoint: GET /api/contacts/{idContact}/adresses

Request header:

X-API-TOKEN : Token (wajib)

Response Body (success):

```json
{
  "data": [
    {
    "id": "randimString",
    "street": "Jalan. no 1",
    "city": "klaten",
    "province": "jateng",
    "country": "indonesia",
    "postalCode": "57482"
    }
  ]
}
```

Response Body (failed):
```json
{
  "errors":"Contacts not found"
}
```