package bozek.mateusz.crawler.logger;


import bozek.mateusz.crawler.student.Student;

public interface Logger {

    void log( String status, bozek.mateusz.common.domain.Student student );
}
