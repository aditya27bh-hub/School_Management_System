// HeadTeacher is the child of Teacher.
// Person -> Teacher -> HeadTeacher  (this is multilevel inheritance)
public class HeadTeacher extends Teacher {

    public HeadTeacher(int id, String name, String subject) {
        super(id, name, subject);
    }

    public String getRole() {
        return "Head Teacher";
    }
}
