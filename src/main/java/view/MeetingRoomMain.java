package view;

import controller.MeetingRoomManager;
import model.Employee;
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
                // Aquí puedes implementar la lógica para añadir una sala
                System.out.println("Añadiendo una nueva sala...");
                // mrmanager.addRoom(...);
                break;
            case 4:
                // Aquí puedes implementar la lógica para eliminar una sala
                System.out.println("Eliminando una sala...");
                // mrmanager.deleteRoom(...);
                break;
            default:
                System.out.println(ConsoleColors.RED_BOLD + "Opción no válida. Por favor, intenta de nuevo." + ConsoleColors.RESET + "\n");
        }
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
                boolean addedRoom = false; // Variable para verificar si se ha añadido la sala correctamente

                System.out.print("Introduce ID de la sala: ");
                roomId = Integer.parseInt(keyboard.nextLine());
                System.out.print("Introduce nombre de la sala: ");
                roomName = keyboard.nextLine();
                System.out.print("Introduce capacidad de la sala: ");
                capacity = Integer.parseInt(keyboard.nextLine());
                System.out.print("Introduce recursos de la sala (separados por comas): ");
                resources = keyboard.nextLine();

                try {
                    addedRoom = mrmanager.addRoom(new Room(roomId, roomName, capacity, resources));
                } catch (SQLException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al añadir la sala: " + e.getMessage() + ConsoleColors.RESET);
                }

                if (!addedRoom) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al añadir la sala, ya existe una sala con ese ID o los datos introducidos son inválidos." + ConsoleColors.RESET);
                } else {
                    System.out.println(ConsoleColors.GREEN_BOLD + "Sala añadida correctamente." + ConsoleColors.RESET);
                }
                break;
            case 2:
                boolean deletedRoom = false; // Variable para verificar si se ha eliminado la sala correctamente
                try {
                    System.out.print("Introduce el ID de la sala que deseas eliminar: ");
                    roomId = Integer.parseInt(keyboard.nextLine());
                    deletedRoom = mrmanager.deleteRoom(roomId); // Llamamos al método para eliminar la sala
                } catch (SQLException e) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al eliminar la sala: " + e.getMessage() + ConsoleColors.RESET);
                }

                if (!deletedRoom) {
                    System.out.println(ConsoleColors.RED_BOLD + "Error al eliminar la sala, puede que tenga reservas asociadas o no exista." + ConsoleColors.RESET);
                } else {
                    System.out.println(ConsoleColors.GREEN_BOLD + "Sala eliminada correctamente." + ConsoleColors.RESET);
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
                "╔══════════════════════════════════════════════╗\n" +
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
