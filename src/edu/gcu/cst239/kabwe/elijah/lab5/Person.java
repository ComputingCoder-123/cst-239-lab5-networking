import edu.gcu.cst239.kabwe.elijah.lab5.model.Person;


public class Person {
    private int id;
    private String name;
    private int age;

    public Person() {
        // Jackson uses the no-argument constructor when reading JSON.
    }

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{id=" + id + ", name=" + name + ", age=" + age + "}";
    }
}