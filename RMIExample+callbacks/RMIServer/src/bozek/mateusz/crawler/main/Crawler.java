package bozek.mateusz.crawler.main;


import bozek.mateusz.common.domain.Student;
import bozek.mateusz.crawler.compare.*;
import bozek.mateusz.crawler.logger.*;
import bozek.mateusz.crawler.student.*;
import bozek.mateusz.crawler.parser.*;
import bozek.mateusz.crawler.type_enum.*;
import bozek.mateusz.crawler.exception.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Crawler{

    private final String url = "http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt";
    private File file = new File("students.txt");
    private List<bozek.mateusz.common.domain.Student> studentFile = StudentsParser.parse(file);
    private ConsoleLogger consoleLogger = new ConsoleLogger();

    public Crawler() throws IOException {
    }
    
    public List<bozek.mateusz.common.domain.Student> getAllStudents(){
    	return studentFile;
    }
    
    void showAllStudent(){
        studentFile.forEach(s -> System.out.println(s));
    }

    void addStudent(bozek.mateusz.common.domain.Student student) throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("students.txt");
        studentFile.add(student);

        for(bozek.mateusz.common.domain.Student s : studentFile){

            printWriter.println(s.getMark()+";"+s.getFirstName()+";"+s.getLastName()+";"+s.getAge());
        }
        printWriter.close();
    }

    void deleteStudent() throws FileNotFoundException {

        Scanner in = new Scanner(System.in);
        int index  = 1;

        for(bozek.mateusz.common.domain.Student s : studentFile){
            System.out.println(index+". "+s);
            index++;
        }
        System.out.println("Podaj index studenta do usuniecia");
        index = in.nextInt();

        studentFile.remove(index -1);

        PrintWriter printWriter = new PrintWriter("students.txt");

        for(bozek.mateusz.common.domain.Student s : studentFile){

            printWriter.println(s.getMark()+";"+s.getFirstName()+";"+s.getLastName()+";"+s.getAge());
        }
        printWriter.close();
    }

    public List<String> run() throws Exception {

    	List<String> response = new ArrayList<>();
        List<bozek.mateusz.common.domain.Student> students;
        List<bozek.mateusz.common.domain.Student> newStudent;
        List<bozek.mateusz.common.domain.Student> removedStudent;
        Date date = new java.util.Date();

        boolean change = false;

            students = StudentsParser.parse(new URL(url));
            studentFile = StudentsParser.parse(file);
            studentFile.sort(new CompareToName());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            newStudent = findNewStudent(students, studentFile);
            removedStudent = findRemovedStudent(students,studentFile );


            if(!removedStudent.isEmpty()){
            	removedStudent.forEach(s -> response.add(sdf.format(date)+"  [REMOVED]  "+s));
                change = true;
            }

            if(!newStudent.isEmpty()){

                newStudent.forEach(s -> consoleLogger.log("ADDED: ",s));
                newStudent.forEach(s -> response.add(sdf.format(date)+"  [ADDED]  "+s));
                change = true;

            }

            if(newStudent.isEmpty() && removedStudent.isEmpty()) {
                System.out.println("We not detected any changes");

                change = false;
            }

            isChange(change);

            Thread.sleep(10000);
            
            return response;
        }

    private List<bozek.mateusz.common.domain.Student> findNewStudent(List<bozek.mateusz.common.domain.Student> students, List<bozek.mateusz.common.domain.Student> studentsFile) {

        boolean find = false;
        List<bozek.mateusz.common.domain.Student> newStudents = new ArrayList<>();

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

    private List<bozek.mateusz.common.domain.Student> findRemovedStudent(List<bozek.mateusz.common.domain.Student> students, List<bozek.mateusz.common.domain.Student> studentsFile){

        boolean find = false;
        List<bozek.mateusz.common.domain.Student> removedStudents = new ArrayList<>();

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

            List<bozek.mateusz.common.domain.Student> studentsOrderByMark = extractStudents(OrderMode.MARK);

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

    List<bozek.mateusz.common.domain.Student> extractStudents(OrderMode mode) {

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

            for (bozek.mateusz.common.domain.Student s : studentFile) {
                resultValue = Math.max(resultValue, s.getMark());
            }

            return resultValue;

        } else {

            resultValue = studentFile.get(0).getMark();

            for (bozek.mateusz.common.domain.Student s : studentFile) {
                resultValue = Math.min(resultValue, s.getMark());
            }

            return resultValue;
        }
    }

    int extractAge(ExtremumMode mode) {

        int resultValue = 0;

        if (mode.equals(ExtremumMode.MAX)) {

            for (bozek.mateusz.common.domain.Student s : studentFile) {
                resultValue = Math.max(resultValue, s.getAge());
            }

            return resultValue;

        } else {

            resultValue = studentFile.get(0).getAge();

            for (bozek.mateusz.common.domain.Student s : studentFile) {
                resultValue = Math.min(resultValue, s.getAge());
            }

            return resultValue;
        }
    }

    public String getUrl() {
        return url;
    }

}
