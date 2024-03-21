# Object-Oriented Programming Project 2022-2023

## Overview

This project is a cornerstone of the Object-Oriented Programming course. It focuses on developing an application to manage a communication terminal network efficiently. 
The primary goal is to handle the registration, management, and querying of clients, terminals, and communications. 
The application also introduces advanced features like tariff plans and a sophisticated notification delivery system.

## Features

### Clients, Terminals, and Communications

- **Clients**: Identified by unique keys, associated with multiple terminals, and categorized into Normal, Gold, and Platinum based on their interaction with the system.
- **Terminals**: Uniquely identified numeric strings capable of text, voice, and video communications. Their functionality varies based on the client's tariff plan and the terminal's capabilities.
- **Communications**: Unique identifiers for each communication instance, detailing sender, receiver, type, and cost calculated based on the active tariff plan.

### Tariff Plans

Tariff plans are pivotal in determining the cost of communications. They vary based on the client's category and the type of communication. Special discounts are applied for communications between "friend" terminals.

### Notification System

A flexible notification system informs clients about failed communication attempts, allowing for opt-in/out functionality. Various delivery methods are supported, enhancing the user experience.

## Application Functionality

### User Interaction

- A comprehensive user interface with multiple menus for client and terminal management and data querying.
- Features include viewing and registering clients and terminals, managing notification settings, and detailed payment and debt information.

### Data Serialization

- State preservation through serialization allows for saving and loading the application's current state, ensuring data continuity across sessions.

### Extensibility

- Designed with future growth in mind, allowing for the easy addition of new client types, communication types, tariff plans, and querying capabilities.
