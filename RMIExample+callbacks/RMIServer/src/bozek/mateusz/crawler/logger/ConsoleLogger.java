package bozek.mateusz.crawler.logger;

import bozek.mateusz.crawler.student.Student;

public class ConsoleLogger implements Logger {

    @Override
    public void log(String status, bozek.mateusz.common.domain.Student student) {
        System.out.println(status+" "+student);
    }
}
