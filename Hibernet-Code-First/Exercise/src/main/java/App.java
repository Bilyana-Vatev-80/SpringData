import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    private static final String GRINGOTTS_PU = "gringotts";
    private static final String SALES_PU = "sales";
    private static final String HOSPITAL_PU = "hospital";
    private static final String UNIVERSITY_SYSTEM = "university_system";
    private static final String BILLS_PAYMENT_SYSTEM = "bills_payment_system";
    public static void main(String[] args) {
        EntityManager emf = Persistence.createEntityManagerFactory(HOSPITAL_PU)
                .createEntityManager();

        emf.getTransaction().begin();
        emf.getTransaction().commit();

        Engine engine = new Engine(emf);
        engine.run();

    }
}
