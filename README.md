# Flight Management System

## Overview

The **Flight Management System** is a Java-based application designed to efficiently manage and track flights. This system leverages several design patterns—Strategy, Composite, and Observer—ensuring flexibility, scalability, and maintainability. The application supports customizable flight searches, hierarchical airline management, and real-time notifications, demonstrating advanced object-oriented programming (OOP) principles.

## Features

- **Flight Search Mechanism**: Implements the Strategy Pattern to support various flight search algorithms (by price, duration, departure time, etc.), allowing dynamic selection at runtime.
- **Airline Hierarchy Management**: Utilizes the Composite Pattern to manage a hierarchical structure of airlines, including subsidiaries and their respective flights.
- **Real-time Notifications**: Uses the Observer Pattern to provide passengers and employees with real-time updates on flight changes, cancellations, and promotions.

## Design Patterns

### Strategy Pattern

The Strategy Pattern allows the system to encapsulate different flight search algorithms, providing the flexibility to switch between them at runtime based on user input. This pattern improves code maintainability and allows for easy integration of new search strategies.

#### Implementations:

- **SearchByPrice**: Filters and sorts flights based on price.
- **SearchByDuration**: Filters flights based on the duration of the flight.
- **SearchByDepartureTime**: Searches flights based on departure time.
- **SearchByDestination**: Finds flights based on the destination.
- **SearchByOriginAndDestination**: Searches flights by origin and destination.
- **SearchByClosestDepartureAndDestination**: Searches flights based on the closest match between departure and destination locations.

### Composite Pattern

The Composite Pattern is used to manage complex airline structures. It allows the system to treat individual airlines and groups of airlines uniformly, simplifying operations such as adding flights, managing subsidiaries, and displaying hierarchical data.

#### Implementations:

- **AirlineComponent Interface**: Defines common operations for both `Airline` and `SubAirline`.
- **Airline**: Represents an airline that may have subsidiaries.
- **SubAirline**: Represents a subsidiary airline.

### Observer Pattern

The Observer Pattern is implemented to allow `Flight` objects to notify registered observers (such as `Passenger` and `Employee`) of any changes in flight status. This ensures that relevant parties receive timely updates without tightly coupling the notification logic to the flight management code.

#### Implementations:

- **Observer Interface**: Defines the `update()` method for observers.
- **Flight Class**: Manages a list of observers and notifies them of changes.
- **Passenger and Employee Classes**: Implement the `Observer` interface to receive notifications.

## Object-Oriented Principles

This project is built on solid OOP principles:

- **Encapsulation**: Each class manages its state and behavior, exposing only the necessary functionality through well-defined interfaces.
- **Polymorphism**: Interfaces and abstract classes allow for flexible interchange of components, such as different search strategies or observers.
- **Separation of Concerns**: The system is divided into clear, manageable components (models, search strategies, management, notifications), each handling its specific responsibilities.

## Project Structure

The project is organized as follows:

```plaintext
flight-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── NadavShabtai/
│   │   │   │   │   ├── models/                # Core entities like Flight, Passenger, Employee, Person, etc.
│   │   │   │   │   ├── search/                # Strategy Pattern implementation
│   │   │   │   │   ├── hierarchy/             # Composite Pattern implementation
│   │   │   │   │   ├── notifications/         # Observer Pattern implementation
│   │   │   │   │   ├── management/            # Management classes
│   │   │   │   │   ├── Main.java              # Main entry point
├── README.md                                  # Detailed README file
