# Autenticação API - Documentação

Este documento descreve os endpoints de autenticação e seus respectivos payloads JSON.

## Endpoints

### 1. Login (Unificado)
Autentica usuários e lojas. O sistema identifica o tipo de conta automaticamente.

**URL:** `/auth/login`
**Método:** `POST`

**Payload:**
```json
{
  "email": "user@example.com",
  "password": "yourpassword"
}
```

**Resposta (Sucesso - 200 OK):**
```json
{
  "name": "Nome do Usuário ou Loja",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 2. Refresh Token (Unificado)
Gera um novo token de acesso usando um refresh token válido.

**URL:** `/auth/refresh-token`
**Método:** `POST`

**Payload:**
```json
{
  "refreshToken": "seu_refresh_token_aqui"
}
```

**Resposta (Sucesso - 200 OK):**
```json
{
  "name": "Nome do Usuário ou Loja",
  "token": "novo_access_token...",
  "refreshToken": "novo_refresh_token..."
}
```

---

### 3. Registro de Usuário
Cria uma nova conta de usuário (Cliente).

**URL:** `/auth/user/register`
**Método:** `POST`

**Payload:**
```json
{
  "firstName": "João",
  "lastName": "Silva",
  "email": "joao@email.com",
  "password": "senha_segura",
  "cell": "11999999999",
  "document": "123.456.789-00",
  "address": [
    {
      "street": "Rua das Flores",
      "number": "123",
      "complement": "Apto 101",
      "neighborhood": "Centro",
      "city": "São Paulo",
      "state": "SP",
      "zipCode": "01001-000",
      "country": "Brasil"
    }
  ]
}
```

---

### 4. Registro de Loja
Cria uma nova conta de loja (Parceiro).

**URL:** `/auth/store/register`
**Método:** `POST`

**Payload:**
```json
{
  "name": "Pizzaria do João",
  "document": "12.345.678/0001-90",
  "email": "contato@pizzariajoao.com.br",
  "password": "senha_da_loja",
  "description": "A melhor pizza da região",
  "category": "RESTAURANT", 
  "address": {
      "street": "Av. Paulista",
      "number": "1000",
      "complement": "Loja 1",
      "neighborhood": "Bela Vista",
      "city": "São Paulo",
      "state": "SP",
      "zipCode": "01310-100",
      "country": "Brasil"
  }
}
```
*Nota: `category` deve ser um valor válido do enum `StoreCategoryEnum`.*
