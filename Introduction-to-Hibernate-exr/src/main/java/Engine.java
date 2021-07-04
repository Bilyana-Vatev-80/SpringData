import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable{
    private final EntityManager entityManager;
    public final BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Chose one exercise: ");
        int problemNumber = 0;
        try {
            problemNumber = Integer.parseInt(this.reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (problemNumber){
                case 2:
                    changeCasingEx2();
                    break;
                case 3:
                    try {
                              containsEmployeesExr3();
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                    break;
                case 4:
                    employeesWithSalaryOverFiftyThousandEx4();
                    break;
                case 5:
                    employeesFromDepartmentEx5();
                    break;
                case 6:
                    try {
                        addingANewAddressAndUpdatingEmployeeEx6();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    addressesWithEmployeeCountEx7();
                    break;
                case 8:
                    try {
                        getEmployeeWithProjectEx8();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 9:
                    findLatestTenProjectsEx9();
                    break;
                case 10:
                    increaseSalariesEx10();
                    break;
                case 11:
                    try {
                        findEmployeesByFirstNameEx11();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 12:
                    employeesMaximumSalariesEx12(); // is not correct
                    break;
                case 13:
                    try {
                        removesTownByNameEx13();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    private void removesTownByNameEx13() throws IOException {
        System.out.println("Enter valid town name to delete: ");
        String townName = reader.readLine();
        Town town = entityManager.createQuery("SELECT t FROM Town t " +
                "WHERE t.name = :name", Town.class)
                .setParameter("name", townName)
                .getSingleResult();

        List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a " +
                "WHERE a.town.name = :name", Address.class).setParameter("name", townName)
                .getResultList();
        String output = String.format("%d addresses %s in %s deleted%n", addresses.size(),
                (addresses.size() != 1) ? "es" : "", town.getName());

        entityManager.getTransaction().begin();
        addresses.forEach(a-> {
            for (Employee employee : a.getEmployees()) {
                employee.setAddress(null);
            }
            a.setTown(null);
            entityManager.remove(a);
        });
        entityManager.remove(town);
        entityManager.getTransaction().commit();
        System.out.println(output);
    }

    private void employeesMaximumSalariesEx12() {
        List<Employee> employees = entityManager

                .createQuery("SELECT e FROM Employee e " +
                        "GROUP BY e.department.id " +
                        "HAVING MAX(e.salary)  NOT BETWEEN (30000) AND (70000) ", Employee.class)
                .getResultList();


        employees
                .forEach(e -> {
                    System.out.printf("%s - %.2f%n",
                            e.getDepartment().getName(),
                            e.getSalary());
                });
    }

    private void findEmployeesByFirstNameEx11() throws IOException {
        System.out.println("Entry valid Employee first name: ");
        String employeeName = reader.readLine();
         entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.firstName like :name", Employee.class).setParameter("name", employeeName + "%")
                .getResultStream().forEach(e -> {
                    System.out.printf("%s %s - %s - ($%.2f)%n",e.getFirstName(),
                            e.getLastName(), e.getJobTitle(),e.getSalary());
                });
    }

    private void increaseSalariesEx10() {
        entityManager.getTransaction().begin();
        int effectedRows = entityManager.createQuery("UPDATE Employee e " +
                "SET e.salary = e.salary * 1.12 " +
                "WHERE e.department.id IN (1,2,4,11)").executeUpdate();
        entityManager.getTransaction().commit();

        System.out.println("Effected rows " + effectedRows);
        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.id IN (1,2,4,11)",Employee.class)
                .getResultList()
                .stream()
                .forEach(e ->
                        System.out.printf("%s %s ($%.2f)%n",e.getFirstName(), e.getLastName(),
                                e.getSalary()));
    }

    private void findLatestTenProjectsEx9() {
        List<Project> projects = entityManager.createQuery("SELECT p FROM Project p " +
                "ORDER BY p.startDate DESC ", Project.class)
                .setMaxResults(10)
                .getResultList();
        projects.stream().sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    System.out.printf("Project name: %s%n" +
                            "Project Description: %s%n" +
                            "Project Start Date: %s%n" +
                            "Project End Date: %s%n", p.getName(), p.getDescription(),
                            p.getStartDate(), p.getEndDate());
                });
    }

    private void getEmployeeWithProjectEx8() throws IOException {
        System.out.println("Enter valid employee id:");
        int id = Integer.parseInt(reader.readLine());

        Employee employee = entityManager.find(Employee.class, id);
        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(),
                employee.getJobTitle());

        employee.getProjects().stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.printf("\t%s%n", project.getName());
                });
    }

    private void addressesWithEmployeeCountEx7() {
        // data is not correct size is 2 not 3

       List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a " +
               "ORDER BY a.employees.size DESC", Address.class)
               .setMaxResults(10)
               .getResultList();

       addresses.forEach(a ->
               System.out.printf("%s, %s - %d employee%n",a.getText(), a.getTown().getName(),a.getEmployees().size()));
    }

    private void addingANewAddressAndUpdatingEmployeeEx6() throws IOException {
       Address address = createAddress("Vitoshka 15");

        System.out.println("Entry employee last name:");
        String lastName = reader.readLine();

        Employee employee = entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.lastName = :name", Employee.class)
                .setParameter("name", lastName)
                .getSingleResult();
        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();

    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
        return address;
    }

    private void employeesFromDepartmentEx5() {
       entityManager.createQuery("SELECT e FROM Employee e " +
               "WHERE e.department.name = :d_name " +
               "ORDER BY e.salary ,e.id", Employee.class).setParameter("d_name", "Research and Development")
               .getResultList()
               .forEach(e -> {
                   System.out.printf("%s %s from Research and Development - $%.2f%n",e.getFirstName(),
                           e.getLastName(),e.getSalary());
               });

    }

    private void employeesWithSalaryOverFiftyThousandEx4() {
        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.salary > 50000", Employee.class)
                .getResultList();
        // second type
        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.salary > :min_salary", Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000L))
                .getResultList()
                .stream().map(Employee::getFirstName)
                .forEach(System.out::println);
    }

    private void containsEmployeesExr3() throws IOException {
        System.out.println("Enter employee fullName:");
        String fullName = reader.readLine();
        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e WHERE " +
                "concat(e.firstName, ' ', e.lastName) = :name", Employee.class)
                .setParameter("name", fullName)
                .getResultList();

        System.out.println(employees.isEmpty() ? "NO" : "YES");
    }

    private void changeCasingEx2() {
        entityManager.getTransaction().begin();
        int update = entityManager.
                createQuery("UPDATE Town t " +
                        "SET t.name = upper(t.name) " +
                        "WHERE length(t.name) <= 5 ",Town.class).executeUpdate();

        System.out.println(update);
        entityManager.getTransaction().commit();
    }
}
