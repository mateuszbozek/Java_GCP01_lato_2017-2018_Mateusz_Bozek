package example.logger;


import example.model.Student;

public class ConsoleLogger implements Logger {

    @Override
    public void log(String status, Student student) {
        System.out.println(status+" "+student);
    }
}
