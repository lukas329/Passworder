package sample;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Trieda na zapisanie historie
public class Controler extends Thread{

    String password;
    long time;
    int threads;

    public Controler(String password, long time, int threads) {
        this.password = password;
        this.time = time;
        this.threads = threads;
    }

    @Override
    public void run() {
        File fileToWrite = new File("src/sample/history.txt");
        String line ="Heslo: " + password + "-- čas: " + time + "-- vlákna: " + threads + "\n";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileToWrite, true));
            bw.write(line);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
