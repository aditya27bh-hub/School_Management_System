// Person is the parent class of Student and Teacher.
// It is abstract so we cannot make an object of Person directly.
public abstract class Person {

    public static final String SCHOOL_NAME = "Green Valley School";   // final = cannot change

    private String name;
    protected int id;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public final int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // abstract method: no body here, child classes will write it
    public abstract String getRole();

    public String toString() {
        return id + " | " + name + " | " + getRole();
    }
}
