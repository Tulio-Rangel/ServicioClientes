# Servicio de Cliente

## Descripción

Este proyecto es un microservicio para la gestión de clientes que proporciona una API REST completa para operaciones CRUD. Implementa un sistema de persistencia con JPA y ofrece integración con sistemas de mensajería mediante ActiveMQ.

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Web (REST)
- ActiveMQ
- H2 Database
- Lombok
- JUnit para pruebas unitarias

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/tulio/serviciocliente/
│   │   ├── controller/       # Controladores REST
│   │   ├── exception/        # Manejo de excepciones global
│   │   ├── messaging/        # Componentes de mensajería
│   │   ├── model/            # Entidades JPA
│   │   ├── repository/       # Repositorios de datos
│   │   └── service/          # Lógica de negocio
│   └── resources/
│       └── application.properties  # Configuración de la aplicación
└── test/                     # Pruebas unitarias
```

## Modelo de Datos

El sistema utiliza dos entidades principales:

- **Persona**: Clase base que contiene:
- id (clave primaria)
- nombre
- genero (restringido a "M" o "F")
- edad
- identificacion
- direccion
- telefono

- **Cliente**: Hereda de Persona y añade:
- contrasena
- estado (booleano)

## Funcionalidades Principales

- Creación, actualización y eliminación de clientes
- Consulta de clientes por ID o listado completo
- Validación de datos de clientes
- Comunicación asíncrona mediante colas de mensajes

## Configuración y Ejecución

### Requisitos Previos

- Java 17 o superior
- Maven o Gradle
- ActiveMQ (para funcionalidades de mensajería)

### Pasos para Ejecutar

1. Clonar el repositorio
2. Configurar el archivo `application.properties` según el entorno
3. Ejecutar la aplicación:

```bash
# Usando Gradle
./gradlew bootRun

# Usando Java directamente
./gradlew build
java -jar build/libs/serviciocliente-*.jar
```

La aplicación estará disponible en `http://localhost:8080`.

### Configuración

Principales propiedades configurables en `application.properties`:

```properties
spring.application.name=ServicioCliente
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:clientedb
spring.jpa.hibernate.ddl-auto=update
spring.activemq.broker-url=tcp://localhost:61616
```

## Endpoints disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET    | /clientes | Obtener lista de todos los clientes |
| GET    | /clientes/{id} | Obtener un cliente por su ID |
| POST   | /clientes | Crear un nuevo cliente |
| PUT    | /clientes/{id} | Actualizar un cliente existente |
| DELETE | /clientes/{id} | Eliminar un cliente |

## Sistema de Mensajería

El servicio implementa un consumidor de mensajes que:
- Escucha en la cola `validar-cliente`
- Verifica si existe un cliente con el ID proporcionado
- Envía la respuesta (booleano) a la cola `respuesta-validacion`

Esta funcionalidad permite la integración con otros servicios en un entorno distribuido.

