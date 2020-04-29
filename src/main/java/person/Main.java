package person;

import com.github.javafaker.Faker;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.ZoneId;

public class Main {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-homework");
    private static void createPerson(int personNumb) {
        EntityManager em = emf.createEntityManager();
        try {
            for (int i=0;i<personNumb;i++){
                em.getTransaction().begin();
                em.persist(randomPerson());
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
    public static Person  randomPerson(){
        Faker faker = new Faker();

       Address address= new Address();
       address.setCountry(faker.address().country());
       address.setState(faker.address().state());
       address.setCity(faker.address().city());
       address.setStreetAddress(faker.address().streetAddress());
       address.setZip(faker.address().zipCode());

        Person person=Person.builder()
                .name(faker.name().fullName())
                .dob(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .gender(faker.options().option(Person.Gender.class))
                .address(address)
                .email(faker.internet().emailAddress())
                .profession(faker.company().name())
                .build();
        return person;
    }

    public static void main(String[] args) {
        createPerson(4);
    }
}
