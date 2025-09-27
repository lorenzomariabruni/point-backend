# API Testing Guide - Point Backend System

## 🧪 Panoramica Testing

Questa guida fornisce una suite completa di test per verificare il corretto funzionamento delle API del sistema Point Backend.

---

## 🔗 Setup Ambiente di Test

### Prerequisiti
- Applicazione in esecuzione su `http://localhost:8080`
- Database popolato con dati di test
- Tool di testing (cURL, Postman, o simili)

### Verifica Stato Sistema
```bash
# Health Check
curl -X GET http://localhost:8080/actuator/health

# Risposta attesa:
# {"status":"UP","components":{...}}
```

---

## 📋 Test Scenarios

### 1. Test Aggiunta Punti

#### 🟢 Test Case 1.1: Aggiunta Valida
```bash
curl -X POST http://localhost:8080/api/points/add \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "points": 100,
    "description": "Acquisto prodotto premium"
  }'
```

**Risultato Atteso:**
```json
{
  "transactionId": 7,
  "customerId": 1,
  "operation": "ADD",
  "points": 100,
  "newBalance": 200,
  "description": "Acquisto prodotto premium",
  "success": true,
  "message": "Punti aggiunti con successo"
}
```
**Status Code:** `201 Created`

#### 🔴 Test Case 1.2: Customer Inesistente
```bash
curl -X POST http://localhost:8080/api/points/add \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 999,
    "points": 50,
    "description": "Test cliente inesistente"
  }'
```

**Risultato Atteso:**
```json
{
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Cliente con ID 999 non trovato",
  "timestamp": "2025-09-27T15:30:00"
}
```
**Status Code:** `404 Not Found`

#### 🔴 Test Case 1.3: Validazione Punti Negativi
```bash
curl -X POST http://localhost:8080/api/points/add \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "points": -50,
    "description": "Test punti negativi"
  }'
```

**Risultato Atteso:**
```json
{
  "code": "VALIDATION_ERROR",
  "message": "Errori di validazione nei dati di input",
  "fieldErrors": {
    "points": "I punti devono essere positivi"
  }
}
```
**Status Code:** `400 Bad Request`

---

### 2. Test Sottrazione Punti

#### 🟢 Test Case 2.1: Sottrazione Valida
```bash
curl -X POST http://localhost:8080/api/points/subtract \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 2,
    "points": 50,
    "description": "Riscatto premio fedeltà"
  }'
```

**Risultato Atteso:**
```json
{
  "transactionId": 8,
  "customerId": 2,
  "operation": "SUBTRACT",
  "points": 50,
  "newBalance": 200,
  "description": "Riscatto premio fedeltà",
  "success": true,
  "message": "Punti sottratti con successo"
}
```
**Status Code:** `200 OK`

#### 🔴 Test Case 2.2: Saldo Insufficiente
```bash
curl -X POST http://localhost:8080/api/points/subtract \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 3,
    "points": 100,
    "description": "Test saldo insufficiente"
  }'
```

**Risultato Atteso:**
```json
{
  "code": "INSUFFICIENT_POINTS",
  "message": "Punti insufficienti. Saldo attuale: 0, richiesti: 100",
  "timestamp": "2025-09-27T15:30:00"
}
```
**Status Code:** `400 Bad Request`

---

### 3. Test Consultazione Saldo

#### 🟢 Test Case 3.1: Saldo Cliente Esistente
```bash
curl -X GET http://localhost:8080/api/points/customer/1/balance
```

**Risultato Atteso:**
```json
{
  "id": 1,
  "name": "Mario Rossi",
  "email": "mario.rossi@email.com",
  "points": 200,
  "createdAt": "2025-09-27T10:00:00",
  "updatedAt": "2025-09-27T15:30:00"
}
```
**Status Code:** `200 OK`

#### 🔴 Test Case 3.2: Cliente Inesistente
```bash
curl -X GET http://localhost:8080/api/points/customer/999/balance
```

**Risultato Atteso:**
```json
{
  "code": "CUSTOMER_NOT_FOUND",
  "message": "Cliente con ID 999 non trovato",
  "timestamp": "2025-09-27T15:30:00"
}
```
**Status Code:** `404 Not Found`

---

### 4. Test Storico Transazioni

#### 🟢 Test Case 4.1: Transazioni Cliente Esistente
```bash
curl -X GET http://localhost:8080/api/points/customer/1/transactions
```

**Risultato Atteso:**
```json
[
  {
    "id": 7,
    "customerId": 1,
    "transactionType": "ADD",
    "points": 100,
    "description": "Acquisto prodotto premium",
    "createdAt": "2025-09-27T15:30:00"
  },
  {
    "id": 1,
    "customerId": 1,
    "transactionType": "ADD",
    "points": 50,
    "description": "Acquisto prodotto",
    "createdAt": "2025-09-27T10:00:00"
  }
]
```
**Status Code:** `200 OK`

---

## 📏 Matrice di Test

| Test Case | Endpoint | Scenario | Input | Expected Status | Expected Response |
|-----------|----------|----------|--------|----------------|------------------|
| TC-001 | POST /add | Aggiunta valida | Valid data | 201 | Success response |
| TC-002 | POST /add | Customer inesistente | Invalid customerId | 404 | Error response |
| TC-003 | POST /add | Punti negativi | Negative points | 400 | Validation error |
| TC-004 | POST /add | CustomerId null | Missing customerId | 400 | Validation error |
| TC-005 | POST /subtract | Sottrazione valida | Valid data | 200 | Success response |
| TC-006 | POST /subtract | Saldo insufficiente | Points > balance | 400 | Business error |
| TC-007 | POST /subtract | Customer inesistente | Invalid customerId | 404 | Error response |
| TC-008 | GET /balance | Cliente esistente | Valid customerId | 200 | Customer data |
| TC-009 | GET /balance | Cliente inesistente | Invalid customerId | 404 | Error response |
| TC-010 | GET /transactions | Cliente con transazioni | Valid customerId | 200 | Transactions array |
| TC-011 | GET /transactions | Cliente senza transazioni | Valid customerId | 200 | Empty array |

---

## 🤖 Test Automatizzati

### Script Bash per Test Suite

```bash
#!/bin/bash

BASE_URL="http://localhost:8080/api/points"
FAILED_TESTS=0
TOTAL_TESTS=0

# Funzione di test
run_test() {
    local test_name="$1"
    local method="$2"
    local endpoint="$3"
    local data="$4"
    local expected_status="$5"
    
    echo "\n📝 Running: $test_name"
    
    if [ "$method" = "POST" ]; then
        response=$(curl -s -w "\n%{http_code}" -X POST "$BASE_URL$endpoint" \
            -H "Content-Type: application/json" -d "$data")
    else
        response=$(curl -s -w "\n%{http_code}" -X GET "$BASE_URL$endpoint")
    fi
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n -1)
    
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    
    if [ "$http_code" = "$expected_status" ]; then
        echo "✅ PASS: Status $http_code (expected $expected_status)"
    else
        echo "❌ FAIL: Status $http_code (expected $expected_status)"
        FAILED_TESTS=$((FAILED_TESTS + 1))
    fi
    
    echo "Response: $body"
}

# Esecuzione test suite
echo "🚀 Starting Point Backend API Test Suite"

# Test aggiunta punti
run_test "Add Points - Valid" "POST" "/add" \
    '{"customerId": 1, "points": 50, "description": "Test add"}' "201"

run_test "Add Points - Invalid Customer" "POST" "/add" \
    '{"customerId": 999, "points": 50, "description": "Test"}' "404"

run_test "Add Points - Negative Points" "POST" "/add" \
    '{"customerId": 1, "points": -10, "description": "Test"}' "400"

# Test sottrazione punti
run_test "Subtract Points - Valid" "POST" "/subtract" \
    '{"customerId": 2, "points": 25, "description": "Test subtract"}' "200"

run_test "Subtract Points - Insufficient" "POST" "/subtract" \
    '{"customerId": 3, "points": 100, "description": "Test"}' "400"

# Test consultazione
run_test "Get Balance - Valid Customer" "GET" "/customer/1/balance" "" "200"
run_test "Get Balance - Invalid Customer" "GET" "/customer/999/balance" "" "404"
run_test "Get Transactions - Valid Customer" "GET" "/customer/1/transactions" "" "200"

# Risultati
echo "\n📈 Test Results:"
echo "Total Tests: $TOTAL_TESTS"
echo "Passed: $((TOTAL_TESTS - FAILED_TESTS))"
echo "Failed: $FAILED_TESTS"

if [ $FAILED_TESTS -eq 0 ]; then
    echo "✅ All tests passed!"
    exit 0
else
    echo "❌ Some tests failed!"
    exit 1
fi
```

### Esecuzione Test Suite

```bash
# Rendi eseguibile lo script
chmod +x test_api.sh

# Esegui i test
./test_api.sh
```

---

## 📊 Load Testing

### Test di Carico con Apache Bench

```bash
# Test 1000 richieste con 10 connessioni concorrenti
ab -n 1000 -c 10 -H "Content-Type: application/json" \
   -p add_points.json http://localhost:8080/api/points/add
```

**File add_points.json:**
```json
{"customerId": 1, "points": 10, "description": "Load test"}
```

### Stress Testing con cURL

```bash
# Test concorrenza con 50 richieste parallele
for i in {1..50}; do
    curl -X POST http://localhost:8080/api/points/add \
        -H "Content-Type: application/json" \
        -d '{"customerId": 1, "points": 1, "description": "Concurrent test '$i'"}' &
done
wait
```

---

## 🔍 Test di Regressione

### Checklist Pre-Release

- [ ] **Funzionalità Base**
  - [ ] Aggiunta punti funziona
  - [ ] Sottrazione punti funziona
  - [ ] Consultazione saldo funziona
  - [ ] Storico transazioni funziona

- [ ] **Validazioni**
  - [ ] Validazione input corretta
  - [ ] Gestione errori appropriata
  - [ ] Messaggi di errore chiari

- [ ] **Performance**
  - [ ] Response time < 100ms
  - [ ] Gestione concorrenza
  - [ ] Memory leaks assenti

- [ ] **Sicurezza**
  - [ ] Input sanitization
  - [ ] Error message security
  - [ ] SQL injection protection

---

## 📝 Report Template

### Test Execution Report

**Data:** 27 Settembre 2025  
**Versione:** 1.0.0  
**Tester:** [Nome]  
**Ambiente:** Sviluppo/Staging/Produzione  

#### Risultati

| Categoria | Totale | Passati | Falliti | % Successo |
|-----------|--------|---------|---------|------------|
| Functional | 8 | 8 | 0 | 100% |
| Validation | 4 | 4 | 0 | 100% |
| Error Handling | 3 | 3 | 0 | 100% |
| Performance | 2 | 2 | 0 | 100% |

#### Issue Trovate

| ID | Severità | Descrizione | Status |
|----|-----------|-------------|--------|
| - | - | Nessun issue | - |

#### Raccomandazioni

1. ✅ API pronte per produzione
2. ✅ Performance nella norma
3. ✅ Gestione errori robusta

---

## 🔗 Strumenti Consigliati

### Postman Collection

Importa la collection Postman per test interattivi:

```json
{
  "info": {
    "name": "Point Backend API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Add Points",
      "request": {
        "method": "POST",
        "header": [{"key": "Content-Type", "value": "application/json"}],
        "body": {
          "mode": "raw",
          "raw": "{\"customerId\": 1, \"points\": 50, \"description\": \"Test\"}"
        },
        "url": {
          "raw": "{{baseUrl}}/api/points/add",
          "host": ["{{baseUrl}}"],
          "path": ["api", "points", "add"]
        }
      }
    }
  ],
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080"
    }
  ]
}
```

### JMeter Test Plan

Crea un test plan JMeter per test automatizzati:

1. **Thread Group**: 10 utenti, 100 iterazioni
2. **HTTP Request**: Configurazione endpoint
3. **Assertions**: Validazione response
4. **Listeners**: Report risultati

---

## 📅 Test Schedule

### Pianificazione Test

- **Daily**: Smoke tests automatici
- **Weekly**: Full regression suite
- **Pre-Release**: Complete test suite + performance
- **Post-Release**: Sanity check in produzione

### Automazione CI/CD

```yaml
# GitHub Actions example
name: API Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Start Application
        run: ./mvnw spring-boot:run &
      - name: Wait for App
        run: sleep 30
      - name: Run API Tests
        run: ./test_api.sh
```