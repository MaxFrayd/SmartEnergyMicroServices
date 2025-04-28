# SmartEnergyMicro

SmartEnergyMicro is a **polyglot micro‑services playground** for a hypothetical Smart‑Energy platform.  
It brings together:

* **Spring Boot / Spring Cloud Java micro‑services** (Eureka, Config, API Gateway, OpenFeign, Resilience4J, Sleuth Zipkin, etc.)
* **MySQL** persistence
* **Docker Compose** for local orchestration

The repository is intentionally modular so you can start, stop, or extend individual services without rebuilding the whole stack.

---

## Service Landscape

| Service directory | Purpose | Port (default) |
|-------------------|---------|---------------|
| `configserver`            | Spring Cloud Config Server holding **all configuration** shared by the fleet | 8071 |
| `eurekaserver`            | Service discovery (Eureka)                                                | 8761 |
| `gatewayserver`           | Spring Cloud Gateway – single entry‑point / API gateway                    | 8072 |
| `SmartEnergy`             | Core domain logic – suppliers, tariffs, meters, energy reports …          | 8081 |
| `SmartEnergyCustomer`     | Customer profiles & authentication                                        | 8082 |
| `paymentservice`          | Payments & billing                                                        | 8083 |
| `ragai`                   | Reporting & analytics gateway                                             | 8086 |


---

## Quick‑Start (🪄 zero‑to‑demo)

```bash
#  spin up the whole stack
cd docker
docker compose up --build -d
# give it ~1 minute – watch the logs with `docker compose logs -f`



That’s it!  All back‑end services, discovery, config.

---

## Manual build & run

### Prerequisites

* **Java 17** (or 11+) & Maven 3.6+
* **Docker 24+** / Docker Desktop (only for the compose approach)

### Build all Java services

```bash
mvn -q -T1C clean package
```

Each module produces a *Docker‑ready* fat JAR under `target/`.  
You can run a single service from your IDE or CLI:

```bash
cd SmartEnergy
mvn spring-boot:run
```

Override ports and database credentials via the usual Spring `application.yml` or `-D` flags.

---

## Configuration

The **Config Server** bootstraps everything from the files in `docker/config/*`.  
Sensitive values are encrypted with the key set in the *compose* file:

```yaml
services:
  configserver:
    environment:
      ENCRYPT_KEY: "change-me-in-prod"
```

Change it locally or use `curl -X POST http://localhost:8071/encrypt -d 'mySecret'`  
to generate fresh cipher blocks.

---

## Database

A **single MySQL instance** (schema `smart_energy`) is provisioned at startup with the DDL & data found in `docker/data.sql`.

Feel free to point individual services at their own DB instances – just update the Spring profiles.

---

## Project structure

```
SmartEnergyMicro/
├─ docker/                 ← local infra (compose, db init scripts)
├─ configserver/           ← Spring Cloud Config
├─ eurekaserver/           ← Eureka discovery
├─ gatewayserver/          ← API Gateway
├─ SmartEnergy*/           ← core micro‑services
└─ pom.xml                 ← multi‑module Maven parent
```

---

## Contributing

1. Fork 🎣, create a branch
2. Commit *atomic* logical chunks with good messages
3. Open a PR – describe **what** & **why**






