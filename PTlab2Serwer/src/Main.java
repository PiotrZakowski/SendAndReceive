import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    //private static ExecutorService executor = Executors.newFixedThreadPool(4);


    //port, na którym nasłuchuje serwer
    private static final int PORT = 1337;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            while (true) {
                final Socket socket = serverSocket.accept();
                //...obsługa klienta w osobnym wątku...
                //if(socket!=null) {
                    executor.submit(new SavingFile(socket));
                //}
            }
        }
        catch (IOException e) {
            System.out.println("Wystapil blad. MAIN 27");
        }
    }
}
