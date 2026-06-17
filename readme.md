AI-powered order management assistant built with Spring AI, OpenAI and Qdrant.

## Features:
- Retrieval-Augmented Generation (RAG)
- Vector search using Qdrant
- Tool calling with Spring AI
- gRPC communication between services
- Event-driven architecture with Apache Kafka
- Docker Compose deployment

## Requirements: 

Before running the application, make sure the following software is installed:


- Java 21+
- Maven 3.9+
- Docker Desktop
- Docker Compose

## External Services

The application uses:

- OpenAI API (for LLM and embeddings)
- Qdrant Vector Database
- Apache Kafka

Qdrant and Kafka are started automatically using Docker Compose.

## Environment Variables

Create a .env file in the project root:

OPENAI_API_KEY=your-api-key


## Build

#### Build all modules:

> mvn clean install -DskipTests

(at the moment tests are not working... TBD)

#### Run

Start the complete environment:

> docker compose up --build

The following services will be started:

- payment-service
- Apache Kafka
- Qdrant
- order-service

## Ports:

| Service                | Port |
| ---------------------- | ---- |
| Order Service          | 8080 |
| Payment Service (gRPC) | 9090 |
| Kafka                  | 9092 |
| Qdrant HTTP API        | 6333 |
| Qdrant gRPC API        | 6334 |


## Examples

#### Retrieve business rules using RAG:

> GET /rag/ask?q=when can order be cancelled

Example response:

> An order can be cancelled only when all of these are true:
>
> - The order status is **not PAID**
> - The order status is **not CANCELLED**
> - The order status is **not COMPLETED**
> - The order amount is **below 500**
>
> So, in short: it can be cancelled only if it is neither paid, cancelled, nor completed, and the amount is less than 500.

#### Execute business logic with RAG:

> /rag-agent/ask?q=can order 123 be cancelled? cancell if eliglible

Example response:
> Order 123 was eligible and has been cancelled successfully.
>
> Details:
> - Previous status: PROCESSING
> - Current status: CANCELLED
