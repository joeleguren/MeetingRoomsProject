package view;

import controller.MeetingRoomManager;
import model.Employee;
import model.Reservation;
import model.ReservationDAO;
import model.Room;
import utils.ConsoleColors;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class MeetingRoomMain {
    private static final int EXIT_OPTION = 0;

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int option;

        showWelcomeBanner(); // Mostramos el menú de bienvenida
        // Creamos una instancia del gestor de salas de reuniones
        MeetingRoomManager mrmanager = new MeetingRoomManager();

        do {
            try {
                showMainMenu(); // Mostramos el menú de opciones
                System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT +  "Selecciona una opción: " + ConsoleColors.RESET);
                option = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException e) {
                option = -1;
            }
            manageOptions(option, keyboard,mrmanager);

        } while (option!=MeetingRoomMain.EXIT_OPTION); // Bucle para gestionar las opciones del menú principal
    }

    private static void manageOptions(int option, Scanner keyboard, MeetingRoomManager mrmanager) {
        int subOption = 0;

        switch (option) {
            case 0:
                System.out.println(ConsoleColors.RED_BOLD + "Saliendo del sistema..." + ConsoleColors.RESET);
                break; // Salir del programa
            case 1:
                do {
                    try {
                        showRoomManagementMenu(); // Mostramos el menú de gestión de salas
                        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT +  "Selecciona una opción: " + ConsoleColors.RESET);
                        subOption = Integer.parseInt(keyboard.nextLine());
                        // Llamamos al método para gestionar las opciones de salas
                    } catch (NumberFormatException e) {
                        option = -1;
                    }
                    manageRoomManagementOptions(subOption, mrmanager);
                } while (subOption != MeetingRoomMain.EXIT_OPTION); // Bucle para gestionar las opciones de salas
                break;
            case 2:
                do {
                    try {
                        showEmployeeManagementMenu(); // Mostramos el menú de gestión de empleados
                        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT +  "Selecciona una opción: " + ConsoleColors.RESET);
                        subOption = Integer.parseInt(keyboard.nextLine());
                        // Llamamos al método para gestionar las opciones de empleados
                    } catch (NumberFormatException e) {
                        option = -1;
                    }
                    manageEmployeeManagementOptions(subOption, mrmanager);
                } while (subOption != MeetingRoomMain.EXIT_OPTION); // Bucle para gestionar las opciones de salas
                break;
            case 3:
                manageAddReservation(mrmanager); // Llamamos al método para gestionar las reservas
                break;
            case 4:
                manageCancelReservation(mrmanager); // Llamamos al método para gestionar las reservas
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "Opción no válida. Por favor, intenta de nuevo." + ConsoleColors.RESET + "\n");
        }
    }

    private static void manageCancelReservation(MeetingRoomManager mrmanager) {
        Scanner keyboard = new Scanner(System.in); // Scanner para leer los input del usuario

        System.out.println("--- Cancelar una reserva ---");
        System.out.print("Introduce el código de la reserva que deseas cancelar: ");
        String reservationId = keyboard.nextLine();
        try {
            if (mrmanager.cancelReservation(reservationId)) { // Llamamos al método para eliminar la reserva
                System.out.println(ConsoleColors.GREEN_BOLD + "Reserva cancelada correctamente." + ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.RED_BOLD + "Error al cancelar la reserva, puede que no exista o que ya haya pasado el tiempo para cancelar." + ConsoleColors.RESET);
            }
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RED_BOLD + "Error al cancelar la reserva: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    private static void manageAddReservation(MeetingRoomManager mrmanager) {
        Scanner keyboard = new Scanner(System.in); // Scanner para leer los input del usuario

        System.out.println("--- Reservar una sala de reuniones ---");
        System.out.print("Introduce el DNI del empleado que reserva la sala: ");
        String dni = keyboard.nextLine();
        System.out.print("Introduce el ID de la sala que desea reservar: ");
        int roomId = Integer.parseInt(keyboard.nextLine());
        System.out.print("Introduce la fecha de la reserva (YYYY-MM-DD): ");
        String reservationDate = keyboard.nextLine();
        System.out.print("Introduce la hora de inicio de la reserva (HH:MM): ");
        String startTime = keyboard.nextLine();
        System.out.print("Introduce la hora de fin de la reserva (HH:MM): ");
        String endTime = keyboard.nextLine();
        try {
            if (validReservationDate(reservationDate, startTime, endTime)) { // Validamos la fecha y hora de la reserva
                Reservation newReservation = new Reservation(
                        roomId,
                        dni,
                        java.time.LocalDate.parse(reservationDate),
                        java.time.LocalTime.parse(startTime),
                        java.time.LocalTime.parse(endTime)
                );
                // Creamos una nueva reserva
                Optional<String> reservationId = mrmanager.addReservation(newReservation); // Generamos un ID único para la reserva
                if (reservationId.isPresent()) { // Si nos ha devuelto un ID de reserva, es que se ha podido añadir correctamente.
                    System.out.println(ConsoleColors.GREEN_BOLD + "Reserva añadida correctamente. Su código es " + reservationId.get() + ConsoleColors.RESET + ".");
                } else { // No se ha podido añadir la reserva
                    System.out.println(ConsoleColors.RED_BOLD + "Error al añadir la reserva, puede que la sala no exista, el empleado no exista o los datos introducidos sean inválidos." + ConsoleColors.RESET);
                }
            } else {
                System.out.println(ConsoleColors.RED_BOLD + "Fecha o hora de reserva inválida. Por favor, inténtalo de nuevo." + ConsoleColors.RESET);
                return; // Salimos del método si la fecha o hora no son válidas
            }
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RED_BOLD + "Error al añadir la reserva: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    private static boolean validReservationDate(String reservationDate, String startTime, String endTime) {
        return reservationDate.matches(ReservationDAO.DATE_REGEX) && // Comprobamos que la fecha sigue el formato YYYY-MM-DD
               startTime.matches(ReservationDAO.TIME_REGEX) && // Comprobamos que la hora de inicio sigue el formato HH:MM:SS
               endTime.matches(ReservationDAO.TIME_REGEX); // Comprobamos que la hora de fin sigue el formato HH:MM:SS
    }

    private static void manageEmployeeManagementOptions(int subOption, MeetingRoomManager mrmanager) {
        Scanner keyboard = new Scanner(System.in); // Scanner para leer los input del usuario
        String dni;
        String name;
        String email;
        String department;

        switch (subOption) {
            case 0:
                System.out.println(ConsoleColors.RED_BOLD + "Volviendo al menú principal..." + ConsoleColors.RESET);
                break; // Volver al menú principal
            case 1:
                System.out.print("Introduce DNI del empleado: ");
                dni = keyboard.nextLine();
                System.out.print("Introduce nombre del empleado: ");
                name = keyboard.nextLine();
                System.out.print("Introduce email del empleado: ");
                email = keyboard.nextLine();
                System.out.print("Introduce departamento del empleado: ");
                department = keyboard.nextLine();

                try {
                    Employee newEmployee = new Employee(dni, name, email, department);
                    if (mrmanager.addEmployee(newEmployee)) {// Llamamos al método para añadir el empleado
                        System.out.println(ConsoleColors.GREEN_BOLD + "Empleado añadido correctamente." + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.RED_BOLD + "Error al añadir el empleado, ya existe un empleado con ese DNI o los datos introducidos son inválidos." + ConsoleColors.RESET);
                    }
                } catch (SQLException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al añadir el empleado: " + e.getMessage() + ConsoleColors.RESET);
                }
                break;
            case 2:
                System.out.print("Introduce el DNI del empleado que deseas eliminar: ");
                dni = keyboard.nextLine();
                try {
                    if (mrmanager.deleteEmployee(dni)) { // Llamamos al método para eliminar el empleado
                        System.out.println(ConsoleColors.GREEN_BOLD + "Empleado eliminado correctamente." + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.RED_BOLD + "Error al eliminar el empleado, puede que tenga reservas asociadas o no exista." + ConsoleColors.RESET);
                    }
                } catch (SQLException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al eliminar el empleado: " + e.getMessage() + ConsoleColors.RESET);
                }
                break;
            case 3:
                System.out.print("Introduce el DNI del empleado que deseas modificar: ");
                dni = keyboard.nextLine();
                try {
                    Optional<Employee> employee = mrmanager.getEmployeeByDni(dni); // Llamamos al método para obtener el empleado por DNI
                    if (employee.isEmpty()) {
                        System.out.println(ConsoleColors.RED_BOLD + "Empleado no encontrado." + ConsoleColors.RESET);
                    } else {
                        System.out.println(employee.get()); // Mostramos los datos del empleado
                        System.out.print("Introduce el nuevo nombre del empleado: ");
                        name = keyboard.nextLine();
                        System.out.print("Introduce el nuevo email del empleado: ");
                        email = keyboard.nextLine();
                        System.out.print("Introduce el nuevo departamento del empleado: ");
                        department = keyboard.nextLine();
                        Employee modifiedEmployee = new Employee(dni, name, email, department);
                        if (mrmanager.modifyEmployee(modifiedEmployee)) {
                            System.out.println(ConsoleColors.GREEN_BOLD + "Empleado modificado correctamente." + ConsoleColors.RESET);
                        } else {
                            System.out.println(ConsoleColors.RED_BOLD + "Error al modificar el empleado, puede que los datos introducidos sean inválidos." + ConsoleColors.RESET);
                        }
                    }
                } catch (SQLException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al obtener el empleado: " + e.getMessage() + ConsoleColors.RESET);
                }
                break;
            case 4:
                try {
                    mrmanager.getAllEmployees().forEach(System.out::println); // Llamamos al método para listar todos los empleados
                } catch (SQLException e) {
                    System.out.println("Error al listar los empleados: " + e.getMessage());
                }
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "Opción no válida. Por favor, intenta de nuevo." + ConsoleColors.RESET + "\n");
        }
    }


    private static void manageRoomManagementOptions(int subOption, MeetingRoomManager mrmanager) {
        Scanner keyboard = new Scanner(System.in); // Scanner para leer los input del usuario
        int roomId;
        String roomName;
        int capacity;
        String resources;

        switch (subOption) {
            case 0:
                System.out.println(ConsoleColors.RED_BOLD + "Volviendo al menú principal..." + ConsoleColors.RESET);
                break; // Volver al menú principal
            case 1:
                try {
                    System.out.print("Introduce ID de la sala: ");
                    roomId = Integer.parseInt(keyboard.nextLine());
                    System.out.print("Introduce nombre de la sala: ");
                    roomName = keyboard.nextLine();
                    System.out.print("Introduce capacidad de la sala: ");
                    capacity = Integer.parseInt(keyboard.nextLine());
                    System.out.print("Introduce recursos de la sala (separados por comas): ");
                    resources = keyboard.nextLine();
                    Room newRoom = new Room(roomId, roomName, capacity, resources);

                    if (mrmanager.addRoom(newRoom)) { // Llamamos al método para añadir la sala, si se ha añadido correctamente, nos devuelve true
                        System.out.println(ConsoleColors.GREEN_BOLD + "Sala añadida correctamente." + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.RED_BOLD + "Error al añadir la sala, ya existe una sala con ese ID o los datos introducidos son inválidos." + ConsoleColors.RESET);
                    }
                } catch (SQLException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al añadir la sala: " + e.getMessage() + ConsoleColors.RESET);
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "ID de sala inválido. Debe ser un número entero." + ConsoleColors.RESET);
                }
                break;
            case 2:
                try {
                    System.out.print("Introduce el ID de la sala que deseas eliminar: ");
                    roomId = Integer.parseInt(keyboard.nextLine());
                    if (!mrmanager.deleteRoom(roomId)) {
                        System.out.println(ConsoleColors.RED_BOLD + "Error al eliminar la sala, puede que tenga reservas asociadas o no exista." + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.GREEN_BOLD + "Sala eliminada correctamente." + ConsoleColors.RESET);
                    }
                } catch (SQLException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al eliminar la sala: " + e.getMessage() + ConsoleColors.RESET);
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "ID de sala inválido. Debe ser un número entero." + ConsoleColors.RESET);
                }
                break;
            case 3:
                try {
                    System.out.print("Introduce el ID de la sala que deseas modificar: ");
                    roomId = Integer.parseInt(keyboard.nextLine());
                    Optional<Room> room = mrmanager.getRoomById(roomId); // Llamamos al método para obtener la sala por ID

                    if (room.isEmpty()) {
                        System.out.println(ConsoleColors.RED_BOLD + "Sala no encontrada." + ConsoleColors.RESET);
                    }
                    else {
                        System.out.println(room.get());// Llamamos al método para obtener la sala por ID
                        System.out.print("Introduce el nuevo nombre de la sala: ");
                        roomName = keyboard.nextLine();
                        System.out.print("Introduce la nueva capacidad de la sala: ");
                        capacity = Integer.parseInt(keyboard.nextLine());
                        System.out.print("Introduce los nuevos recursos de la sala (separados por comas): ");
                        resources = keyboard.nextLine();
                        Room modifiedRoom = new Room(roomId, roomName, capacity, resources);
                        if (mrmanager.modifyRoom(modifiedRoom)) {
                            System.out.println(ConsoleColors.GREEN_BOLD + "Sala modificada correctamente." + ConsoleColors.RESET);
                        } else {
                            System.out.println(ConsoleColors.RED_BOLD + "Error al modificar la sala, puede que los datos introducidos sean inválidos." + ConsoleColors.RESET);
                        }
                    }
                } catch (SQLException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al obtener la sala: " + e.getMessage() + ConsoleColors.RESET);
                }  catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "ID de sala inválido. Debe ser un número entero." + ConsoleColors.RESET);
                }
                break;
            case 4:
                try {
                    mrmanager.getAllRooms().forEach(System.out::println); // Llamamos al método para listar todas las salas
                } catch (SQLException e) {
                    System.out.println("Error al listar las salas: " + e.getMessage());
                }
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "Opción no válida. Por favor, intenta de nuevo." + ConsoleColors.RESET + "\n");
        }
    }

    private static void showEmployeeManagementMenu() {
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT +
                "\n--- Submenú de Gestión de Empleados ---" + ConsoleColors.RESET +
                "\n  [0] Volver al menú principal" +
                "\n  [1] Añadir empleado" +
                "\n  [2] Eliminar empleado" +
                "\n  [3] Modificar empleado" +
                "\n  [4] Listar todas los empleados\n");
    }

    private static void showRoomManagementMenu() {
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT +
                "\n--- Submenú de Gestión de Salas ---" + ConsoleColors.RESET +
                "\n  [0] Volver al menú principal" +
                "\n  [1] Añadir sala de reuniones" +
                "\n  [2] Eliminar sala de reuniones" +
                "\n  [3] Modificar sala de reuniones" +
                "\n  [4] Listar todas las salas de reuniones\n");
    }

    private static void showMainMenu() {
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT +
                "\n╔══════════════════════════════════════════════╗\n" +
                "║                  MENÚ                        ║\n" +
                "╠══════════════════════════════════════════════╣\n" +
                "║  0. Salir del programa                       ║\n" +
                "║  1. Gestionar una sala de reuniones          ║\n" +
                "║  2. Gestionar un empleado                    ║\n" +
                "║  3. Reservar una sala de reuniones           ║\n" +
                "║  4. Cancelar una reserva                     ║\n" +
                "╚══════════════════════════════════════════════╝" +
                ConsoleColors.RESET);
    }

    private static void showWelcomeBanner() {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "╔════════════════════════════════════════════════════════════════╗" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "║    BIENVENIDO AL SISTEMA DE RESERVA DE SALAS DE REUNIONES      ║" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "╚════════════════════════════════════════════════════════════════╝" + ConsoleColors.RESET);
    }
}
