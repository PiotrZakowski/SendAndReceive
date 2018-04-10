package sample;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;

public class Controller {

    @FXML
    private Label komunikat;

    @FXML
    private ProgressBar pasekPostepu;

    @FXML
    private Button przyciskWyboru;

    @FXML
    private Button przyciskWyslij;

    @FXML
    private Label komunikatPlik;

    //port, na którym nasłuchuje serwer
    private static final int PORT = 1337;

    Task<Void> sendFileTask;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @FXML
    public void WyborPliku()
    {
        FileChooser fileChooser = new FileChooser();
        File wybranyPlik = fileChooser.showOpenDialog(null);
        komunikatPlik.setText(wybranyPlik.getName()+" "+wybranyPlik.getPath());
        sendFileTask = new SendFileTask(wybranyPlik); //klasa zadania
        komunikat.textProperty().bind(sendFileTask.messageProperty());
        pasekPostepu.progressProperty().bind(sendFileTask.progressProperty());
    }

    @FXML
    void WyslijPlik() {
        executor.submit(sendFileTask);
    }
}
