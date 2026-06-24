
# UniNaSwap
This project was created as part of a university assignment to showcase the understanding of the core concepts of Object-Oriented Programming and DAO Pattern.
## Disclaimer

This repository contains only the client-side application developed for the UniNaSwap project. It depends on additional components that are not included in this repository and therefore cannot be executed as a standalone system.


## About

UniNaSwap is a system for exchanging, selling, and gifting items between university students. This project was created as part of a university assignment.

This project was developed to demonstrate:

- Object-Oriented Design principles
- Layered Software Architecture
- DAO Pattern implementation
- GUI development in Java
- Exception handling and DTO usage
## Features

The system allows users to:

- Add an announcement to the platform.
- Make offers for announcements listed on the platform.
- Accept or reject received proposals.
- View a list of proposals made, along with their outcomes, accompanied by a graphical report.
## Tech Stack

**Client (Java)**
- Object-oriented design
- DAO Pattern
- Layered software architecture
- Graphical user interface (GUI)

## Architecture

The project follows a layered architecture:

- Presentation Layer (GUI)
- Controller Layer
- Data Access Layer (DAO)
- Data Transfer Objects (DTO)

The DAO pattern is used to isolate persistence logic from business logic, improving maintainability and testability.
## Repository Structure

- `src/controller` → Controllers to handle incoming requests
- `src/dao` → Data Access Object to access the underlying storage
- `src/dto` → Data Transfer Object used to represent the storage entities
- `src/exception` Custom exception to handle custom software behaviour
- `src/gui` Graphical User Interface related code and classes
- `src/img` Images used by the software (Logo, icons, etc.)

## License

This project is licensed under the [MIT](https://choosealicense.com/licenses/mit/) License.


## Authors

- GitHub: [@RiuHz](https://www.github.com/RiuHz)
- GitHub: [@faustoandreabellucci](https://github.com/faustoandreabellucci)
