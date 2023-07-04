#User API Specs

##Register User

Emdpoint = POST /api/users

Request Body:
```json
{
  "username": "big",
  "password": "secret",
  "name": "bagas"
}
```

Respomse Body (success):
```json
{
  "data": "OK"
}
```

Respomse Body (failed):
```json
{
  "errors": "tidak boleh kosong"
}
```

##Login User

Emdpoint = POST /api/auth/login

Request Body:
```json
{
  "username": "big",
  "password": "secret"
}
```

Respomse Body (success):
```json
{
  "data": {
    "token": "TOKEN",
    "expired": 29072976 //milisekon
  }
}
```

Respomse Body (failed, 401):
```json
{
  "errors": "username or password salah"
}
```

##Get User

Emdpoint = GET /api/users/current

Request header:
- X-API-TOKEN : Token (wajib)

Respomse Body (success):
```json
{
  "data": {
    "username": "big",
    "name": "bagas"
  }
}
```

Respomse Body (failed, 401):
```json
{
  "errors": "unauthorize"
}
```

##UPdate Usser

Emdpoint = PATCH /api/users/current

Request header:
- X-API-TOKEN : Token (wajib)

Request Body:
```json
{
  "username": "big", // isi jika ingin diupdate
  "password": "secret" // isi jika ingin diupdate
}
```

Respomse Body (success):
```json
{
  "data": {
    "username": "big",
    "name": "bagas"
  }
}
```

Respomse Body (failed, 401):
```json
{
  "errors": "unauthorize"
}
```

## LOGout User
Emdpoint = DELETE /api/auth/logout

Request header:
- X-API-TOKEN : Token (wajib)

Respomse Body (success):
```json
{
  "data": "OK"
}
```

