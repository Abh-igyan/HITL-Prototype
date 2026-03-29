# HITL-Prototype
**Human-in-the-Loop Triple Validation System for DBpedia Hindi Chapter**

A GSoC 2026 warm-up prototype demonstrating the feedback pipeline for
Hindi Relational Triple Extraction. Extracted triples from a Python SLM
script are POST-ed to a Spring Boot REST API, stored in MySQL, and
presented to annotators via a validation dashboard.

---

## Architecture
```
Python Extraction Script (Sarvam/Gemma)
        │  POST /api/triples
        ▼
Spring Boot REST API (localhost:8080)
        │  Spring Data JPA
        ▼
MySQL Database (hitl_db)
        │  Thymeleaf
        ▼
Validation Dashboard (browser)
        │  PATCH /api/triples/{id}/validate
        ▼
Gold Data → next fine-tuning cycle
```

---

## Data Schema

| Field | Type | Description |
|---|---|---|
| `id` | BIGINT | Auto-generated primary key |
| `originalText` | TEXT | Raw Hindi Wikipedia sentence |
| `subject` | VARCHAR | Extracted Subject (S) |
| `predicate` | VARCHAR | Extracted Predicate (P) |
| `object` | VARCHAR | Extracted Object (O) |
| `status` | ENUM | PENDING / APPROVED / EDITED / REJECTED |
| `correctedTriple` | TEXT | Human-edited triple (JSON) |
| `sourceUrl` | VARCHAR | Source Wikipedia article URL |
| `createdAt` | DATETIME | Ingestion timestamp |

---

## Project Structure
```
HITL-Prototype/
├── src/main/java/com/dbpedia/hitl/
│   ├── entity/Triple.java
│   ├── repository/TripleRepository.java
│   ├── service/TripleService.java
│   └── controller/
│       ├── TripleController.java      # REST API
│       └── DashboardController.java  # Thymeleaf dashboard
├── src/main/resources/
│   ├── application.properties
│   └── templates/dashboard.html
├── extraction/
│   ├── extract.py                    # SLM extraction + auto-POST
│   ├── post_triples.py               # reusable POST helper
│   └── requirements.txt
└── pom.xml
```

---

## Setup & Run

### Prerequisites
- Java 17+
- Maven
- MySQL 8+

### 1. MySQL
```sql
CREATE DATABASE hitl_db;
CREATE USER 'hitl_user'@'localhost' IDENTIFIED BY 'hitl123';
GRANT ALL PRIVILEGES ON hitl_db.* TO 'hitl_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Configure
Edit `src/main/resources/application.properties` with your DB credentials.

### 3. Run backend
```bash
mvn spring-boot:run
```
The `triples` table is auto-created by Hibernate on first run.

### 4. Run extraction script
```bash
cd extraction
pip install -r requirements.txt
python extract.py
```

### 5. Open dashboard
```
http://localhost:8080/dashboard
```

---

## API Reference

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/triples` | Ingest a new triple (called by Python) |
| GET | `/api/triples/pending` | Fetch all PENDING triples |
| PATCH | `/api/triples/{id}/validate` | Approve / Reject / Edit a triple |
| GET | `/api/triples/stats` | Count by status |

**POST body example:**
```json
{
  "originalText": "वाराणसी एक शहर है।",
  "subject": "वाराणसी",
  "predicate": "rdf:type",
  "object": "dbo:City",
  "sourceUrl": "https://hi.wikipedia.org/wiki/वाराणसी"
}
```

---

## GSoC Context

This prototype is a warm-up deliverable for the
**DBpedia Hindi Chapter — GSoC 2026** project:
*Fine-Tuning Indic Models for Hindi Relational Triple Extraction
+ Human-in-the-Loop Feedback.*

The full project will extend this with:
- LoRA fine-tuning of Gemma-3-4b on Hindi BenchIE + IndIE datasets
- Batch validation (approve 50 triples at once)
- Spring Task background jobs for Gold Data export
- OAuth2/JWT for authorized DBpedia contributor access
- RDF export for direct DBpedia ingestion
