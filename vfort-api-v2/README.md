# VFORT API — Spring Boot Aggregation Layer
## Oracle (Business Data) + Flowable (Process Status) → React Frontend

---

## Architecture

```
React Frontend (port 3000)
        │  JWT token in every request
        ▼
VFORT API (port 8090)           ← THIS PROJECT
        │              │
        ▼              ▼
  Oracle DB        Flowable REST (port 8080)
  (orders,         (process status,
   fallouts)        current task, assignee)
        │              │
        └──────────────┘
               │
        Merged Response → React
```

---

## Prerequisites

- Java 17
- Maven
- Oracle 19c running and accessible
- Flowable 6.8.1 running on port 8080 (Tomcat)

---

## Step 1 — Install Oracle JDBC driver into Maven

Oracle's ojdbc11.jar is not in Maven Central. Install it manually:

```bash
mvn install:install-file \
  -Dfile=ojdbc11.jar \
  -DgroupId=com.oracle \
  -DartifactId=ojdbc11 \
  -Dversion=19.0 \
  -Dpackaging=jar
```

---

## Step 2 — Configure application.yml

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@//your-oracle-host:1521/your-service-name
    username: your_oracle_user
    password: your_oracle_password

flowable:
  rest:
    base-url: http://localhost:8080/flowable-rest/service
    username: admin
    password: test
```

---

## Step 3 — Create Oracle Tables

```sql
CREATE TABLE ORDERS (
    ORDER_ID       VARCHAR2(50) PRIMARY KEY,
    CUSTOMER_NAME  VARCHAR2(100),
    CUSTOMER_EMAIL VARCHAR2(100),
    STATUS         VARCHAR2(20),
    TOTAL_AMOUNT   NUMBER(10,2),
    CREATED_AT     TIMESTAMP DEFAULT SYSTIMESTAMP,
    UPDATED_AT     TIMESTAMP DEFAULT SYSTIMESTAMP
);

CREATE SEQUENCE FALLOUT_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE FALLOUTS (
    ID                  NUMBER DEFAULT FALLOUT_SEQ.NEXTVAL PRIMARY KEY,
    ORDER_ID            VARCHAR2(50),
    ERROR_CODE          VARCHAR2(20),
    ERROR_MESSAGE       VARCHAR2(500),
    FALLOUT_TYPE        VARCHAR2(50),
    STATUS              VARCHAR2(20) DEFAULT 'PENDING',
    PROCESS_INSTANCE_ID VARCHAR2(100),
    CREATED_AT          TIMESTAMP DEFAULT SYSTIMESTAMP,
    RESOLVED_AT         TIMESTAMP
);
```

---

## Step 4 — Run the API

```bash
mvn clean spring-boot:run
```

API runs at: `http://localhost:8090/vfort`

---

## API Endpoints

### Auth
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/auth/login` | Get JWT token |

```bash
curl -X POST http://localhost:8090/vfort/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin"}'
```

Response:
```json
{ "token": "eyJ...", "username": "admin", "expiresIn": "24h" }
```

---

### Orders
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/orders` | All orders |
| GET | `/api/orders?status=PENDING` | Filter by status |
| GET | `/api/orders/{orderId}` | Full order detail + fallouts + Flowable status |

```bash
# All orders
curl -H "Authorization: Bearer <token>" \
  http://localhost:8090/vfort/api/orders

# Order detail (merges Oracle + Flowable)
curl -H "Authorization: Bearer <token>" \
  http://localhost:8090/vfort/api/orders/ORD-123
```

Response for order detail:
```json
{
  "orderId": "ORD-123",
  "customerName": "John Doe",
  "status": "FAILED",
  "totalAmount": 299.99,
  "fallouts": [
    {
      "id": 1,
      "falloutType": "PAYMENT_FAIL",
      "status": "IN_PROGRESS",
      "processInstanceId": "12345",
      "processStatus": "RUNNING",
      "currentTask": "Manual Review",
      "currentAssignee": "admin"
    }
  ]
}
```

---

### Fallouts
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/fallouts` | All fallouts + Flowable status |
| GET | `/api/fallouts?status=PENDING` | Filter by status |
| GET | `/api/fallouts/order/{orderId}` | Fallouts for an order |
| POST | `/api/fallouts/{id}/trigger` | Manually trigger Flowable process |

---

## How React Uses This

```javascript
// 1. Login
const { token } = await fetch('/api/auth/login', {
  method: 'POST',
  body: JSON.stringify({ username: 'admin', password: 'admin' })
}).then(r => r.json());

// 2. Fetch orders dashboard
const orders = await fetch('/api/orders?status=PENDING', {
  headers: { 'Authorization': `Bearer ${token}` }
}).then(r => r.json());

// 3. Fetch order detail
const order = await fetch(`/api/orders/${orderId}`, {
  headers: { 'Authorization': `Bearer ${token}` }
}).then(r => r.json());

// order.fallouts[0].currentTask → "Manual Review"
// order.fallouts[0].processStatus → "RUNNING"
```

---

## The Link Between Oracle and Flowable

```
FALLOUTS.PROCESS_INSTANCE_ID = Flowable process instance ID
```

When a fallout is picked up by Flowable:
1. Flowable starts a process → gets a process instance ID
2. Your API stores that ID in `FALLOUTS.PROCESS_INSTANCE_ID`
3. On every frontend request, API uses that ID to query Flowable for live status
4. Both data sets merged and returned to React
