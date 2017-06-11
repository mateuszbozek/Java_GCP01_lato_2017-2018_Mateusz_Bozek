package example.logger;

import example.model.Student;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class TextLogger implements Closeable, Logger {


    public void saveToFile(String status, Student student) throws FileNotFoundException {

        PrintWriter printWriter = new PrintWriter("textlogger.txt");

        printWriter.println(status+" "+ student);
        printWriter.close();
    }


    @Override
    public void log(String status, Student student) throws FileNotFoundException {
        saveToFile(status, student);
    }

    @Override
    public void close() throws IOException {

    }
}
