# Point Backend System

## Descrizione

Sistema backend Spring Boot per la gestione di punti cliente con operazioni di aggiunta e sottrazione punti.

## Caratteristiche

- **Framework**: Spring Boot 3.2.0 con Java 17
- **Database**: H2 in-memory per sviluppo
- **Architettura**: Clean Architecture con layer separati
- **Validazione**: Bean Validation (JSR-303)
- **Documentazione API**: Swagger/OpenAPI
- **Gestione errori**: Global exception handler
- **Transazioni**: Gestione transazionale con Spring @Transactional

## API Endpoints

### Operazioni sui Punti

- `POST /api/points/add` - Aggiunge punti al saldo cliente
- `POST /api/points/subtract` - Sottrae punti dal saldo cliente
- `GET /api/points/customer/{customerId}/balance` - Ottiene il saldo attuale
- `GET /api/points/customer/{customerId}/transactions` - Ottiene lo storico transazioni

### Esempio Richieste

#### Aggiungere Punti
```json
{
  "customerId": 1,
  "points": 50,
  "description": "Acquisto prodotto"
}
```

#### Sottrarre Punti
```json
{
  "customerId": 1,
  "points": 25,
  "description": "Riscatto premio"
}
```

## Avvio Applicazione

```bash
# Clona il repository
git clone https://github.com/lorenzomariabruni/point-backend.git

# Naviga nella directory
cd point-backend

# Avvia l'applicazione
./mvnw spring-boot:run
```

L'applicazione sarà disponibile su `http://localhost:8080`

## Console Database

Accedi alla console H2: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (vuota)

## Documentazione API

Swagger UI disponibile su: `http://localhost:8080/swagger-ui/`

## Struttura Progetto

```
src/main/java/com/pointsystem/
├── controller/          # REST Controllers
├── service/            # Business Logic
├── repository/         # Data Access Layer
├── entity/            # JPA Entities
├── dto/               # Data Transfer Objects
├── exception/         # Exception Handling
└── enums/             # Enumerations
```

## Dati di Test

L'applicazione include dati di test pre-caricati:
- 4 clienti di esempio
- Transazioni di test per dimostrare il funzionamento

## Tecnologie Utilizzate

- Spring Boot 3.2.0
- Spring Data JPA
- Spring Web
- H2 Database
- Bean Validation
- Swagger/OpenAPI
- Maven
