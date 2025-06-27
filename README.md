# Proyecto Java con Maven y SQL - Joel Eguren Guijarro

## Descripción general

Este proyecto es una aplicación de consola desarrollada en **Java**, que permite gestionar reservas de salas de reuniones en una empresa.  
Se ha utilizado **Maven** como gestor de dependencias, **JUnit 5** para pruebas unitarias y **MySQL** para la gestión de la base de datos.

## 🛠️ Tecnologías utilizadas

- Java 21
- Maven
- MySQL
- JUnit 5
- DBeaver (para la gestión visual de la base de datos)
- Git & GitHub
- Trello (Scrum Board)

## Estructura del proyecto
```bash
📦 MeetingRoomsProject
├── README.md
├── pom.xml
├── .gitignore
├── diagrams
│ └── ermodel.png
├── documentation
│ ├── Documentacion_Scrum_JoelEgurenGuijarro.docx
│ ├── Documentacion_Scrum_JoelEgurenGuijarro.odt
│ └── Documentacion_Scrum_JoelEgurenGuijarro.pdf
├── sql
│ └── meeting_rooms_db.sql
└── src
  ├── main
  │ └── java
  │   ├── controller
  │   │ └── MeetingRoomManager.java
  │   ├── model
  │   │ ├── Employee.java
  │   │ ├── EmployeeDAO.java
  │   │ ├── Reservation.java
  │   │ ├── ReservationDAO.java
  │   │ ├── Room.java
  │   │ └── RoomDAO.java
  │   ├── utils
  │   │ ├── ConsoleColors.java
  │   │ └── DAOConstants.java
  │   └── view
  │     └── MeetingRoomMain.java
  └── test
      └── java
          └── MeetingRoomsTest.java
```

## 🚀 Cómo ejecutar el proyecto
1. Abre la terminal en tu directorio preferido y clona el repositorio en tu máquina local:

```bash
git clone https://github.com/joeleguren/MeetingRoomsProject.git
```
2. Abre el proyecto en tu IDE favorito.
   - Si usas **IntelliJ IDEA**, puedes abrir el proyecto directamente desde la carpeta clonada.
   - Si usas **Eclipse**, asegúrate de tener instalado el plugin de Maven y luego importa el proyecto como un proyecto Maven existente.
3. Configura la conexión a la base de datos MySQL:
  - Localiza el fichero `/sql/meeting_rooms_db.sql` que es el script de creación de la base de datos y los inserts.
  - Desde la terminal de SQL, pega y ejecuta el script de creación de la base de datos.
  - Asegúrate que el usuario y la contraseña de la base de datos en el archivo `src/main/java/utils/DAOConstants` coincidan con los de tu instalación de MySQL.
4. Compila el proyecto con Maven desde tu IDE favorito y ejecuta la clase `MeetingRoomMain.java`.

## 📌 Enlace al tablero Scrum

Puedes ver el tablero Scrum del proyecto en el siguiente enlace a Trello:

[Tablero Scrum en Trello](https://trello.com/b/oeWgvuMY/minsaitpracticafinalscrum)