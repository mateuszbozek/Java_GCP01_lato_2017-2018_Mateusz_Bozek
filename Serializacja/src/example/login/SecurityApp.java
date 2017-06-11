package example.login;


import javax.crypto.Cipher;
import java.io.*;
import java.util.Scanner;

public class SecurityApp {

    private Cipher cipher;

    public boolean registerUser() throws IOException {

        final Scanner in = new Scanner(System.in);

        System.out.println("Podaj nazwe użytkownika: ");
        String username = in.nextLine();

        while (shouldUserExists(username)){
            System.out.println("Nazwa użytkownika zajęta: ");
            System.out.println("Spróbuj jeszcze raz: ");
            username = in.nextLine();
        }

        System.out.println("Podaj hasło: ");
        String password = in.nextLine();

        PrintWriter printWriter = new PrintWriter("Username.txt");

        printWriter.println(username);
        printWriter.println(password);
        printWriter.close();

        User user = new User(username, password);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("user.bin"))) {
            outputStream.writeObject(user);

        }
        return true;
    }

    private boolean shouldUserExists(final String userame) throws FileNotFoundException {
        File file = new File("Username.txt");

        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.equals(userame)) {
                return true;
            }
        }
        return false;
    }
}
