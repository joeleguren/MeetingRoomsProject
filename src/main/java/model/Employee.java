package model;

public class Employee {
    private String dni;
    private String name;
    private String email;
    private String department;

    public Employee(String dni, String name, String email, String department) {
        this.dni = dni;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "\n==============================" +
                "\n|   EMPLEADO                 |" +
                "\n==============================" +
                "\n| DNI:        " + dni +
                "\n| Nombre:     " + name +
                "\n| Email:      " + email +
                "\n| Departamento: " + department +
                "\n==============================\n";
    }
}
