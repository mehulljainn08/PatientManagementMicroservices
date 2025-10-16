# Patient Management System - Microservices


A robust patient management system built using a microservices architecture. This project demonstrates a decoupled, scalable, and resilient backend system for handling patient data, appointments, and analytics, all containerized with Docker.

## 🏛️ Architecture Overview

The system is composed of several independent microservices that communicate with each other through a message broker (Kafka). All external requests are routed through a single, intelligent API Gateway.



The main services are:
* **API Gateway**: The single entry point for all client requests. Handles routing, security, and cross-cutting concerns.
* **Patient Service**: Manages all patient-related data and business logic (CRUD operations).
* **Analytics Service**: Listens to events from other services (e.g., `PatientCreated`) via Kafka to perform data analysis and generate insights.

---

## ✨ Features

* **RESTful API** for managing patient records.
* **Centralized Routing** via a Spring Cloud API Gateway.
* **Asynchronous Communication** using Kafka for event-driven processing.
* **Containerized Services** with Docker for consistent development and deployment environments.
* **Centralized API Documentation** exposed through the gateway via SpringDoc OpenAPI.

---

## 🛠️ Tech Stack

| Category      | Technology                                                                                                |
|---------------|-----------------------------------------------------------------------------------------------------------|
| **Backend** | [Java 21](https://www.oracle.com/java/), [Spring Boot 3](https://spring.io/projects/spring-boot)             |
| **Gateway** | [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)                                   |
| **Messaging** | [Apache Kafka](https://kafka.apache.org/)                                                                 |
| **Database** | *[Add your database here, e.g., PostgreSQL, MongoDB]* |
| **DevOps** | [Docker](https://www.docker.com/), [Docker Compose](https://docs.docker.com/compose/)                      |

---

## 🚀 Getting Started

Follow these instructions to get the project up and running on your local machine.

### Prerequisites

* [Git](https://git-scm.com/)
* [Docker](https://www.docker.com/products/docker-desktop/) & [Docker Compose](https://docs.docker.com/compose/install/)
* [JDK 21](https://www.oracle.com/java/technologies/downloads/) or later
* [Maven](https://maven.apache.org/download.cgi)

### Installation & Startup

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/mehulljainn08/PatientManagementMicroservice.git](https://github.com/mehulljainn08/PatientManagementMicroservice.git)
    cd PatientManagementMicroservice
    ```

2.  **Build the Docker images:**
    This command will build the JAR for each service and then create the Docker images.
    ```sh
    docker-compose build
    ```

3.  **Run the services:**
    This will start all the services, including Kafka, defined in the `docker-compose.yml` file.
    ```sh
    docker-compose up -d
    ```
The `-d` flag runs the containers in detached mode. You can view logs for a specific service using `docker-compose logs -f <service-name>`.

---

## Usage

Once all services are running, the API is available through the API Gateway.

### API Documentation

The API documentation is generated using SpringDoc and is accessible via the gateway. This provides a Swagger UI where you can view and test all the available endpoints for the `patient-service`.

* **Swagger UI URL**: [http://localhost:4004/api-docs/patients](http://localhost:4004/api-docs/patients)

---

## 📄 License

This project is distributed under the MIT License. See `LICENSE` for more information.

---

## 📬 Contact

Mehul Jain

* GitHub: [@mehulljainn08](https://github.com/mehulljainn08)
* LinkedIn: [https://www.linkedin.com/in/mehul-jain-61186b23a/](https://www.linkedin.com/in/mehul-jain-61186b23a/)