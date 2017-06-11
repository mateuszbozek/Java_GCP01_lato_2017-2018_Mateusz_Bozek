package example.login;


import com.sun.xml.internal.ws.Closeable;
import example.logger.Logger;
import example.model.Student;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressedLogger implements Closeable, Logger, Serializable {


    public void saveToZipFile() throws IOException {
        StringBuilder sb = new StringBuilder();

        File file = new File("textlogger.txt");
        Scanner in = new Scanner(file);

        while (in.hasNextLine()) {

            String line = in.nextLine();
            sb.append(line);
        }



        File f = new File("CompressedLogger.zip");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
        ZipEntry e = new ZipEntry("textlogger.txt");
        out.putNextEntry(e);

        byte[] data = sb.toString().getBytes();
        out.write(data, 0, data.length);
        out.closeEntry();

        out.close();
    }

    @Override
    public void log(String status, Student student) throws IOException {
    }

    @Override
    public void close() {
    }
}
