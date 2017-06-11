package example.logger;

import example.model.Student;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializedLogger implements Logger, Closeable {
    @Override
    public void log(String status, Student student) throws IOException {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("serializableLogger.bin"))) {
            outputStream.writeObject(student+" "+status);
        }
    }

    @Override
    public void close() throws IOException {
    }
}
