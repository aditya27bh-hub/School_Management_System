// Student is the child of Person
public class Student extends Person {

    private String className;
    private int[] marks = new int[3];     // marks[0] = english, marks[1] = maths, marks[2] = science

    // constructor 1: used when we add a new student
    public Student(int id, String name, String className) {
        super(id, name);
        this.className = className;
    }

    // constructor 2: used when we read the student from the file
    public Student(int id, String name, String className, int english, int maths, int science) {
        super(id, name);
        this.className = className;
        marks[0] = english;
        marks[1] = maths;
        marks[2] = science;
    }

    public String getClassName() {
        return className;
    }

    public int[] getMarks() {
        return marks;
    }

    // setMarks 1: takes three numbers
    public void setMarks(int english, int maths, int science) throws InvalidMarksException {
        if (english < 0 || english > 100) {
            throw new InvalidMarksException("English marks must be between 0 and 100");
        }
        if (maths < 0 || maths > 100) {
            throw new InvalidMarksException("Maths marks must be between 0 and 100");
        }
        if (science < 0 || science > 100) {
            throw new InvalidMarksException("Science marks must be between 0 and 100");
        }
        marks[0] = english;
        marks[1] = maths;
        marks[2] = science;
    }

    // setMarks 2: takes an array (same name, so this is method overloading)
    public void setMarks(int[] m) throws InvalidMarksException {
        setMarks(m[0], m[1], m[2]);
    }

    public int getTotal() {
        int total = 0;
        for (int i = 0; i < 3; i++) {
            total = total + marks[i];
        }
        return total;
    }

    public double getPercentage() {
        double percentage = getTotal() / 3.0;
        return percentage;
    }

    public String getGrade() {
        double p = getPercentage();
        if (p >= 75) {
            return "A";
        } else if (p >= 50) {
            return "B";
        } else if (p >= 35) {
            return "C";
        } else {
            return "Fail";
        }
    }

    public String getRole() {
        return "Student";
    }

    public String toString() {
        return super.toString() + " | Class " + className + " | Total " + getTotal();
    }

    public ReportCard getReportCard() {
        ReportCard card = new ReportCard();
        return card;
    }

    // This is an inner class (a class inside another class)
    public class ReportCard {

        public void show() {
            System.out.println("----- REPORT CARD -----");
            System.out.println("Name    : " + getName());
            System.out.println("Class   : " + className);
            System.out.println("English : " + marks[0]);
            System.out.println("Maths   : " + marks[1]);
            System.out.println("Science : " + marks[2]);
            System.out.println("Total   : " + getTotal() + " / 300");
            System.out.println("Percent : " + getPercentage() + " %");
            System.out.println("Grade   : " + getGrade());
            System.out.println("-----------------------");
        }
    }
}
