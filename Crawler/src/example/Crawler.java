package example;


import example.comparator.CompareToAge;
import example.comparator.CompareToMark;
import example.comparator.CompareToName;
import example.comparator.CompareToSurname;
import example.exception.CrolwerException;
import example.logger.ConsoleLogger;
import example.logger.MailLogger;
import example.model.Student;
import example.parser.StudentsParser;
import example.type_enum.ExtremumMode;
import example.type_enum.OrderMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Crawler{

    private String url;
    private File file = new File("students.txt");
    private List<Student> studentFile = StudentsParser.parse(file);
    private ConsoleLogger consoleLogger = new ConsoleLogger();

    public Crawler() throws IOException {
    }

    void showAllStudent(){
        studentFile.forEach(s -> System.out.println(s));
    }

    void addStudent(Student student) throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("students.txt");

        studentFile.add(student);

        for(Student s : studentFile){

            printWriter.println(s.getMark()+";"+s.getFirstName()+";"+s.getLastName()+";"+s.getAge());
        }
        printWriter.close();
    }

    void deleteStudent() throws FileNotFoundException {

        Scanner in = new Scanner(System.in);
        int index  = 1;


        for(Student s : studentFile){
            System.out.println(index+". "+s);
            index++;
        }
        System.out.println("Podaj index studenta do usuniecia");
        index = in.nextInt();

        studentFile.remove(index -1);

        PrintWriter printWriter = new PrintWriter("students.txt");

        for(Student s : studentFile){

            printWriter.println(s.getMark()+";"+s.getFirstName()+";"+s.getLastName()+";"+s.getAge());
        }
        printWriter.close();
    }

    void run() throws CrolwerException, IOException, InterruptedException {

        List<Student> students;
        List<Student> newStudent;
        List<Student> removedStudent;
        MailLogger mailLogger = new MailLogger();
        boolean change = false;

        if(url == null){
            throw  new CrolwerException(" No path url");
        }

            students = StudentsParser.parse(new URL(url));
            studentFile = StudentsParser.parse(file);
            studentFile.sort(new CompareToName());

            newStudent = findNewStudent(students, studentFile);
            removedStudent = findRemovedStudent(students,studentFile );


            if(!removedStudent.isEmpty()){
                removedStudent.forEach(s -> consoleLogger.log("REMOVED: ",s));
                removedStudent.forEach(s -> mailLogger.log("REMOVED: ",s));

                change = true;
            }

            if(!newStudent.isEmpty()){

                newStudent.forEach(s -> consoleLogger.log("ADDED: ",s));
                newStudent.forEach(s -> mailLogger.log("ADDED: ",s));

                change = true;

            }

            if(newStudent.isEmpty() && removedStudent.isEmpty()) {
                System.out.println("We not detected any changes");

                change = false;
            }

            isChange(change);

            Thread.sleep(10000);
        }

    private List<Student> findNewStudent(List<Student> students, List<Student> studentsFile) {

        boolean find = false;
        List<Student> newStudents = new ArrayList<>();

        for (int i = 0; i < studentsFile.size(); i++) {

            for (int j = 0; j < students.size(); j++) {

                if (studentsFile.get(i).equals(students.get(j))) {
                    find = true;
                }

            }

            if (!find) {
                newStudents.add(studentsFile.get(i));
            } else {
                find = false;
            }

        }

        return newStudents;
    }

    private List<Student> findRemovedStudent(List<Student> students, List<Student> studentsFile){

        boolean find = false;
        List<Student> removedStudents = new ArrayList<>();

        for (int i = 0; i < students.size(); i++) {

            for (int j = 0; j < studentsFile.size(); j++) {

                if (students.get(i).equals(studentsFile.get(j))) {
                    find = true;
                }
            }

            if (!find) {
                removedStudents.add(students.get(i));
            }
            find = false;
        }

        return removedStudents;
    }

    private void isChange(boolean change){

        if(change){

            List<Student> studentsOrderByMark = extractStudents(OrderMode.MARK);

            int minAge = extractAge(ExtremumMode.MIN);
            int maxAge = extractAge(ExtremumMode.MAX);

            double minMark = extractMark(ExtremumMode.MIN);
            double maxMark = extractMark(ExtremumMode.MAX);

            System.out.println("Age: <"+minAge+", "+maxAge+">");
            System.out.println("Mark: <"+minMark+", "+maxMark+">");
            System.out.println("Order by Mark: ");

            studentsOrderByMark.forEach(s -> System.out.println(s));

        }

    }

    List<Student> extractStudents(OrderMode mode) {

        if (mode.equals(OrderMode.FIRST_NAME)) {
            Collections.sort(studentFile, new CompareToSurname());

            return studentFile;

        } else if (mode.equals(OrderMode.LAST_NAME)) {
            Collections.sort(studentFile, new CompareToSurname());

            return studentFile;

        } else if (mode.equals(OrderMode.MARK)) {
            Collections.sort(studentFile, new CompareToMark());

            return studentFile;

        } else if (mode.equals(OrderMode.AGE)) {
            Collections.sort(studentFile, new CompareToAge());

            return studentFile;
        }

        return null;
    }

    double extractMark(ExtremumMode mode) {

        double resultValue = 0;

        if (mode.equals(ExtremumMode.MAX)) {

            for (Student s : studentFile) {
                resultValue = Math.max(resultValue, s.getMark());
            }

            return resultValue;

        } else {

            resultValue = studentFile.get(0).getMark();

            for (Student s : studentFile) {
                resultValue = Math.min(resultValue, s.getMark());
            }

            return resultValue;
        }
    }

    int extractAge(ExtremumMode mode) {

        int resultValue = 0;

        if (mode.equals(ExtremumMode.MAX)) {

            for (Student s : studentFile) {
                resultValue = Math.max(resultValue, s.getAge());
            }

            return resultValue;

        } else {

            resultValue = studentFile.get(0).getAge();

            for (Student s : studentFile) {
                resultValue = Math.min(resultValue, s.getAge());
            }

            return resultValue;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
