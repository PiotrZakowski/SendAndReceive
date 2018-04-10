package sample;

import java.io.*;
import java.net.Socket;

import javafx.concurrent.Task;


public class SendFileTask extends Task<Void> {

    File file;

    //port, na którym nasłuchuje serwer
    private static final int PORT = 1337;

    SendFileTask(File file)
    {

        this.file=file;
    }


    @Override
    protected Void call() throws Exception {
        updateMessage("Initiating...");

        //in - strumień wejściowy (np. z gniazda sieciowego)
        //out - strumień wyjściowy (np. do pliku)
        try(Socket socket=new Socket("127.0.0.1",PORT)) {
            try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                try (DataInputStream in = new DataInputStream(new FileInputStream(this.file))) {

                    updateMessage("Sending");
                    out.writeUTF(file.getName());
                    byte[] buffer = new byte[4096]; //bufor 4KB
                    int readSize;
                    long sended = 0;
                    int extraCounter=0;
                    while ((readSize = in.read(buffer)) != -1) {
                        out.write(buffer, 0, readSize);
                        sended = sended + readSize;
                        System.out.println("Sending: " + sended);

                        extraCounter=(extraCounter+1);
                        if(extraCounter==1)
                            updateMessage("Sending.");
                        else if(extraCounter==2)
                            updateMessage("Sending..");
                        else if(extraCounter==3)
                            updateMessage("Sending...");
                        else if(extraCounter==4)
                            updateMessage("Sending....");
                        else
                        {
                            updateMessage("Sending.....");
                            extraCounter=0;
                        }

                        updateProgress(sended, this.file.length());
                    }
                    updateMessage("Finished.");
                    System.out.println("Finished");
                } catch (IOException e) {
                    System.out.println("Wystapil blad. SendFileTask 64");
                }
            } catch (IOException e) {
                System.out.println("Wystapil blad. SendFileTask 66");
            }
        }catch (IOException e) {
            System.out.println("Wystapil blad. SendFileTask 70");
        }

        return null;
    }
}