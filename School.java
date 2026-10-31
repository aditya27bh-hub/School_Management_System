import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

// School keeps all students and teachers in arrays.
// FILE HANDLING: everything is saved in one file called school.txt
// When the program starts it reads the old data from school.txt.
// After every change it writes the data to school.txt again.
public class School {

    private Student[] students = new Student[50];
    private Teacher[] teachers = new Teacher[20];
    private int studentCount = 0;
    private int teacherCount = 0;
    private int nextStudentId = 1;
    private int nextTeacherId = 101;

    public School() {
        load();     // read old data from the file

        // find the last id so that new ids continue after it
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId() >= nextStudentId) {
                nextStudentId = students[i].getId() + 1;
            }
        }
        for (int i = 0; i < teacherCount; i++) {
            if (teachers[i].getId() >= nextTeacherId) {
                nextTeacherId = teachers[i].getId() + 1;
            }
        }

        System.out.println("Loaded " + studentCount + " student(s) and "
                + teacherCount + " teacher(s) from school.txt");
    }

    // returns the new student (or null if the array is full)
    public Student addStudent(String name, String className) {
        if (studentCount == 50) {
            System.out.println("Student list is full.");
            return null;
        }
        Student s = new Student(nextStudentId, name, className);
        nextStudentId++;
        students[studentCount] = s;
        studentCount++;
        save();
        return s;
    }

    // type 1 = Teacher, type 2 = Head Teacher
    public Teacher addTeacher(int type, String name, String subject) {
        if (teacherCount == 20) {
            System.out.println("Teacher list is full.");
            return null;
        }
        Teacher t;
        if (type == 2) {
            t = new HeadTeacher(nextTeacherId, name, subject);
        } else {
            t = new Teacher(nextTeacherId, name, subject);
        }
        nextTeacherId++;
        teachers[teacherCount] = t;
        teacherCount++;
        save();
        return t;
    }

    // returns the student with this id (or null if not found)
    public Student findStudent(int id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId() == id) {
                return students[i];
            }
        }
        return null;
    }

    public void updateMarks(Student s, int english, int maths, int science)
            throws InvalidMarksException {
        s.setMarks(english, maths, science);
        save();
    }

    public void showAll() {
        if (studentCount == 0 && teacherCount == 0) {
            System.out.println("Nobody is added yet.");
        }
        for (int i = 0; i < studentCount; i++) {
            System.out.println(students[i]);
        }
        for (int i = 0; i < teacherCount; i++) {
            System.out.println(teachers[i]);
        }
    }

    // takes two students (objects as parameters) and returns the one with more marks
    public Student better(Student a, Student b) {
        if (a.getTotal() >= b.getTotal()) {
            return a;
        } else {
            return b;
        }
    }

    public Student findTopper() {
        if (studentCount == 0) {
            return null;
        }
        Student top = students[0];
        for (int i = 1; i < studentCount; i++) {
            top = better(top, students[i]);
        }
        return top;
    }

    // recursion: this method calls itself
    // it adds the total of student number "index" and all the students after him
    public int totalOfAll(int index) {
        if (index == studentCount) {
            return 0;
        }
        return students[index].getTotal() + totalOfAll(index + 1);
    }

    public double classAverage() {
        if (studentCount == 0) {
            return 0;
        }
        double average = totalOfAll(0) / (studentCount * 3.0);
        return average;
    }

    // ---------- FILE HANDLING ----------
    // Every person is one line in school.txt, for example:
    // S,1,Aman,10A,80,90,70      (S = student)
    // T,101,Mr Sharma,Maths      (T = teacher)
    // H,102,Mrs Gupta,English    (H = head teacher)

    // SAVE: write everything to school.txt
    private void save() {
        try {
            PrintWriter file = new PrintWriter("school.txt");     // 1. open the file

            for (int i = 0; i < studentCount; i++) {               // 2. write all students
                Student s = students[i];
                int[] m = s.getMarks();
                file.println("S," + s.getId() + "," + s.getName() + "," + s.getClassName()
                        + "," + m[0] + "," + m[1] + "," + m[2]);
            }

            for (int i = 0; i < teacherCount; i++) {               // 3. write all teachers
                Teacher t = teachers[i];
                String type = "T";
                if (t.getRole().equals("Head Teacher")) {
                    type = "H";
                }
                file.println(type + "," + t.getId() + "," + t.getName() + "," + t.getSubject());
            }

            file.close();                                          // 4. close the file
        } catch (FileNotFoundException e) {
            System.out.println("Could not save data to school.txt");
        }
    }

    // LOAD: read everything from school.txt
    private void load() {
        try {
            Scanner file = new Scanner(new File("school.txt"));    // 1. open the file

            while (file.hasNextLine()) {                           // 2. read line by line
                String line = file.nextLine();
                String[] p = line.split(",");                      // cut the line at every comma

                int id = Integer.parseInt(p[1]);

                if (p[0].equals("S")) {
                    int english = Integer.parseInt(p[4]);
                    int maths = Integer.parseInt(p[5]);
                    int science = Integer.parseInt(p[6]);
                    students[studentCount] = new Student(id, p[2], p[3], english, maths, science);
                    studentCount++;
                } else if (p[0].equals("H")) {
                    teachers[teacherCount] = new HeadTeacher(id, p[2], p[3]);
                    teacherCount++;
                } else {
                    teachers[teacherCount] = new Teacher(id, p[2], p[3]);
                    teacherCount++;
                }
            }

            file.close();                                          // 3. close the file
        } catch (FileNotFoundException e) {
            // school.txt is not there yet (first time), so there is nothing to load
        }
    }
}
