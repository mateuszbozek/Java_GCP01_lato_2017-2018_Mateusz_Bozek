package example.logger;


import example.model.Student;

import java.io.IOException;

public interface Logger {

    void log( String status, Student student ) throws IOException;
}
