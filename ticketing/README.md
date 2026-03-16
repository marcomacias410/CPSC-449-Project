# Event Ticketing System — CPSC 449 Midterm Project

A RESTful backend API for an event ticketing platform, built with Spring Boot 3, JPA, and PostgreSQL.

## Team Members

| Name | CWID |
|------|------|
| [Member 1 Name] | [CWID] |
| [Member 2 Name] | [CWID] |
| [Member 3 Name] | [CWID] |
| [Member 4 Name] | [CWID] |

---

## Project Overview

This system manages events, venues, organizers, ticket types, attendees, and bookings — similar in concept to Ticketmaster or Eventbrite. The backend is built using:

- **Spring Boot 3.2** — application framework
- **Spring Data JPA / Hibernate** — ORM and database interaction
- **PostgreSQL** — relational database
- **Lombok** — boilerplate reduction
- **Maven** — build tool

Authentication and authorization are out of scope per project requirements.

---

## Architecture

The project follows a strict 3-layer architecture:

```
Controller  →  Service  →  Repository
```

- **Controllers** handle HTTP requests and return DTOs only (never JPA entities)
- **Services** contain all business logic, validation, and `@Transactional` methods
- **Repositories** extend `JpaRepository` and contain any custom `@Query` methods

---

## Domain Model

```
Organizer ──< Event >── Venue
               │
               └──< TicketType >──< Booking >── Attendee
```

- One `Organizer` → many `Events`
- One `Venue` → many `Events`
- One `Event` → many `TicketTypes`
- Many `Attendees` ↔ many `TicketTypes` via `Booking` junction table

---

## Setup Instructions

### Prerequisites
- Java 21
- Maven 3.8+
- PostgreSQL running locally

### Database Setup
```sql
CREATE DATABASE ticketing_db;
```

### Configuration
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Run
```bash
mvn spring-boot:run
```
Tables are auto-created by Hibernate on first run (`ddl-auto=update`).

---

## API Endpoints

### Organizers
| Method | URL | Description | Status |
|--------|-----|-------------|--------|
| POST | `/api/organizers` | Create organizer | 201 |

### Venues
| Method | URL | Description | Status |
|--------|-----|-------------|--------|
| POST | `/api/venues` | Create venue | 201 |

### Events
| Method | URL | Description | Status |
|--------|-----|-------------|--------|
| POST | `/api/events` | Create event | 201 |
| GET | `/api/events` | List all UPCOMING events | 200 |
| GET | `/api/events/{id}` | Get event with ticket types | 200 |
| GET | `/api/events/{id}/revenue` | Total confirmed revenue | 200 |

### Attendees
| Method | URL | Description | Status |
|--------|-----|-------------|--------|
| POST | `/api/attendees` | Register attendee | 201 |
| GET | `/api/attendees/{id}/bookings` | Get attendee's bookings | 200 |

### Bookings
| Method | URL | Description | Status |
|--------|-----|-------------|--------|
| POST | `/api/bookings` | Book a ticket | 201 |
| PUT | `/api/bookings/{id}/cancel` | Cancel booking | 200 |

---

## Business Logic Highlights

### Book a Ticket (`POST /api/bookings`)
1. Checks ticket type exists and `quantity_available > 0` — throws `"Sorry, this ticket type is sold out."` if not
2. Checks attendee hasn't already booked this ticket type
3. Decrements `quantity_available` by 1
4. Generates `booking_reference` in format `TKT-{year}-{00042}`
5. Sets `booking_date` to current timestamp, `payment_status` to `CONFIRMED`
6. Entire operation is `@Transactional` — rolls back on any failure

### Cancel a Booking (`PUT /api/bookings/{id}/cancel`)
1. Verifies booking exists and is not already cancelled
2. Sets `payment_status` to `CANCELLED`
3. Increments `quantity_available` by 1
4. Both updates wrapped in a single `@Transactional` method

### Revenue Query (`GET /api/events/{id}/revenue`)
Uses a custom JPQL `@Query` to `SUM(ticketType.price)` for all `CONFIRMED` bookings belonging to an event.

---

## Sample Postman Requests

### Create Organizer
```json
POST /api/organizers
{
  "name": "Live Nation",
  "email": "contact@livenation.com",
  "phone": "555-0100"
}
```

### Create Venue
```json
POST /api/venues
{
  "name": "Honda Center",
  "address": "2695 E Katella Ave",
  "city": "Anaheim",
  "totalCapacity": 18000
}
```

### Create Event
```json
POST /api/events
{
  "title": "Spring Music Festival",
  "description": "A night of live music",
  "eventDate": "2026-05-15T19:00:00",
  "status": "UPCOMING",
  "organizerId": 1,
  "venueId": 1
}
```

### Book a Ticket
```json
POST /api/bookings
{
  "attendeeId": 1,
  "ticketTypeId": 2
}
```

---

## Demo Video

[YouTube Link — replace with your unlisted video URL]
