# Cinema Room REST Service (Spring Boot)

API REST para gestionar la compra y devolución de asientos de un cine. Expone endpoints para consultar asientos disponibles, comprar tickets, devolver tickets y consultar estadísticas protegidas por contraseña.

## Requisitos

- Java 17+
- Gradle (incluye wrapper en el repo)

## Ejecutar la aplicación

```bash
./gradlew bootRun
```

La aplicación queda disponible en `http://localhost:28852`.

## Endpoints

### Obtener asientos disponibles

`GET /seats`

**Respuesta**

```json
{
  "total_rows": 9,
  "total_columns": 9,
  "available_seats": [
    { "row": 1, "column": 1, "price": 10 }
  ]
}
```

### Comprar un asiento

`POST /purchase`

**Body**

```json
{ "row": 3, "column": 5 }
```

**Respuesta**

```json
{
  "token": "uuid-generado",
  "ticket": { "row": 3, "column": 5, "price": 10 }
}
```

### Devolver un ticket

`POST /return`

**Body**

```json
{ "token": "uuid-generado" }
```

**Respuesta**

```json
{
  "returned_ticket": { "row": 3, "column": 5, "price": 10 }
}
```

### Estadísticas (protegidas)

`GET /stats?password=super_secret`

**Respuesta**

```json
{
  "income": 80,
  "available": 80,
  "purchased": 1
}
```

Si la contraseña es incorrecta se devuelve `401`.

## Reglas principales

- El cine tiene 9 filas y 9 columnas.
- El precio es 10 para las filas 1-4 y 8 para las filas 5-9.
- No se puede comprar un asiento fuera de rango o ya comprado.
- La devolución requiere un token válido.

