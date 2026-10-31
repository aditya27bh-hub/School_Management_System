import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static School school = new School();      // this also loads the old data from school.txt

    public static void main(String[] args) {
        System.out.println("===== SCHOOL MANAGEMENT SYSTEM =====");
        System.out.println("       " + Person.SCHOOL_NAME);

        int choice = -1;

        while (choice != 0) {
            System.out.println();
            System.out.println("1. Add student");
            System.out.println("2. Add teacher");
            System.out.println("3. Enter marks");
            System.out.println("4. Report card");
            System.out.println("5. Show everyone");
            System.out.println("6. Class average");
            System.out.println("7. Find topper");
            System.out.println("0. Exit");

            try {
                choice = readInt("Enter your choice: ");

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        addTeacher();
                        break;
                    case 3:
                        enterMarks();
                        break;
                    case 4:
                        reportCard();
                        break;
                    case 5:
                        school.showAll();
                        break;
                    case 6:
                        classAverage();
                        break;
                    case 7:
                        findTopper();
                        break;
                    case 0:
                        System.out.println("Goodbye! Your data is saved in school.txt");
                        break;
                    default:
                        System.out.println("Wrong choice, try again.");
                }
            } catch (InvalidMarksException e) {
                System.out.println("Marks not accepted: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please type a number.");
            }
        }
    }

    // reads a number from the keyboard
    // Integer is a wrapper class, parseInt changes the text into a number
    static int readInt(String message) {
        System.out.print(message);
        String text = sc.nextLine();
        int number = Integer.parseInt(text.trim());
        return number;
    }

    // reads a word or sentence from the keyboard
    static String readText(String message) {
        System.out.print(message);
        String text = sc.nextLine();
        return text.trim();
    }

    static void addStudent() {
        String name = readText("Student name: ");
        String className = readText("Class (example 10A): ");

        Student s = school.addStudent(name, className);
        if (s != null) {
            System.out.println("Student added. His/her ID is " + s.getId());
        }
    }

    static void addTeacher() {
        String name = readText("Teacher name: ");
        String subject = readText("Subject: ");
        int type = readInt("Type 1 for Teacher or 2 for Head Teacher: ");

        Teacher t = school.addTeacher(type, name, subject);
        if (t != null) {
            System.out.println(t.getRole() + " added. ID is " + t.getId());
        }
    }

    static void enterMarks() throws InvalidMarksException {
        int id = readInt("Student ID: ");
        Student s = school.findStudent(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        int english = readInt("English marks: ");
        int maths = readInt("Maths marks: ");
        int science = readInt("Science marks: ");

        school.updateMarks(s, english, maths, science);
        System.out.println("Marks saved for " + s.getName());
    }

    static void reportCard() {
        int id = readInt("Student ID: ");
        Student s = school.findStudent(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        Student.ReportCard card = s.getReportCard();
        card.show();
    }

    static void classAverage() {
        double average = school.classAverage();
        int rounded = (int) average;          // (int) removes the decimal part
        System.out.println("Class average is " + average + " % (about " + rounded + " %)");
    }

    static void findTopper() {
        Student top = school.findTopper();

        if (top == null) {
            System.out.println("No students yet.");
        } else {
            System.out.println("Topper is " + top.getName() + " with " + top.getTotal() + " marks");
        }
    }
}
