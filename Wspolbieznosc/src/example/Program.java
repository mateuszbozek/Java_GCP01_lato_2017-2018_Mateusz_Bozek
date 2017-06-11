package example;


import example.exception.CrolwerException;
import example.model.Student;
import example.thread.Monitor;
import example.type_enum.OrderMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException, CrolwerException, InterruptedException {

        Crawler crawler = new Crawler();
        crawler.setUrl("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt");
        Scanner in = new Scanner(System.in);

        boolean work = true;
        boolean workCrawler = false;
        int choice;


        while (work) {

            System.out.println("Menu Crawler");
            System.out.println("1.Wyswietl studentow (plik)");
            System.out.println("2.Dodaj studenta");
            System.out.println("3.Usun studenta");
            System.out.println("4.Sortowanie");
            System.out.println("5.Crawler run");
            System.out.println("6.Zmien sciezke");
            System.out.println("7.Zakoncz");
            System.out.println("8.Special thread ");
            choice = in.nextInt();


            switch (choice) {

                case 1:
                    System.out.println("_______________________________________________________________");
                    crawler.showAllStudent();
                    System.out.println("_______________________________________________________________");
                    break;

                case 2:
                    Scanner in2 = new Scanner(System.in);

                    System.out.println("Imie: ");
                    String name = in2.nextLine();
                    System.out.println("Nazwisko: ");
                    String lastname = in2.nextLine();
                    System.out.println("Wiek: ");
                    int age = in2.nextInt();
                    System.out.println("Ocena");
                    double mark = in2.nextDouble();

                    Student student = new Student(mark, name, lastname, age);
                    crawler.addStudent(student);
                    break;

                case 3:
                    crawler.deleteStudent();
                    break;

                case 4:
                    List<Student> sortedStudent = new ArrayList<>();

                    System.out.println("Sortuj po:");
                    System.out.println("1.Ocena");
                    System.out.println("2.Imie");
                    System.out.println("3.Nazwisko");
                    System.out.println("4.Wiek");
                    int choice2 = in.nextInt();

                    switch (choice2) {

                        case 1:
                            sortedStudent = crawler.extractStudents(OrderMode.MARK);
                            break;
                        case 2:
                            sortedStudent = crawler.extractStudents(OrderMode.FIRST_NAME);
                            break;
                        case 3:
                            sortedStudent = crawler.extractStudents(OrderMode.LAST_NAME);
                            break;
                        case 4:
                            sortedStudent = crawler.extractStudents(OrderMode.AGE);
                            break;

                        default:
                            System.out.println("Niepoprawny wybór sortowania");
                    }
                    System.out.println("_______________________________________________________________");
                    System.out.println("Posortowani studenci");
                    System.out.println("_______________________________________________________________");

                    sortedStudent.forEach(s -> System.out.println(s));

                    System.out.println("_______________________________________________________________");
                    break;

                case 5:

                    System.out.println("_______________________________________________________________");

                    crawler.run();
                    System.out.println("_______________________________________________________________");
                    break;

                case 6:
                    System.out.println("Podaj nowa sciezke:");
                    String path = in.nextLine();
                    crawler.setUrl(path);
                    break;

                case 7:
                    work = false;
                    break;
                case 8:
                    Monitor monitor = new Monitor(Arrays.asList("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt",
                            "http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt",
                            "http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt",
                            "http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt",
                            "http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt",
                            "http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt",
                            "http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt",
                            "http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt",
                            "http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt",
                            "http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt"), crawler);
                    monitor.run();
                    break;

                default:
                    System.out.println("Dokonaleś niepoprawnego wyboru.");

            }
        }
    }
}
