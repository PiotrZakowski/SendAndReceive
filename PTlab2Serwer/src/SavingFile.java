import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

public class SavingFile implements  Runnable{

    File file;
    Socket socket;

    SavingFile(Socket socket)
    {
        this.socket=socket;
    }

    @Override
    public void run()  {

        //in - strumień wejściowy (np. z gniazda sieciowego)
        //out - strumień wyjściowy (np. do pliku)
        try(DataInputStream in = new DataInputStream(socket.getInputStream()))
        {
            String name = in.readUTF();

            this.file = new File(".\\Pobrane\\"+name);
            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(this.file)))
            {
                byte[] buffer = new byte[4096]; //bufor 4KB
                int readSize;
                long received=0;
                while ((readSize = in.read(buffer)) != -1) {
                    out.write(buffer, 0, readSize);
                    received=received+readSize;
                    System.out.println("Received: "+received);
                }
                System.out.println("Finished");
            }
            catch (IOException e) {
                System.out.println("Wystapil blad. SavingFile 39");
            }
        }
        catch (IOException e) {
            System.out.println("Wystapil blad. SavingFile 43");
        }
    }
}