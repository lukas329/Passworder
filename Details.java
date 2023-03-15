package sample;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Details extends JFrame{
    private JLabel lblTime;
    private JLabel lblPassword;
    private JLabel txtTime;
    private JLabel txtPassword;
    private JPanel panel;
    private JLabel lblThreads;
    private JLabel txtThreads;
    long startTime;

    FinderScreen fs;
    String password;
    int threads;
    long nowSECONDS;
    public Details(final FinderScreen fs, String password, int threads) {
        setValues(password, threads);
        this.startTime = fs.getStartTime();
        this.fs = fs;
        this.password = password;
        this.threads = threads;
        nowSECONDS = 0;
        startThreads();
    }

    //spustime vlákna
    private void startThreads(){
        Thread is_visible = new Thread(isVisibleRun());
        is_visible.start();

        Thread tr = new Thread(updateRun());
        tr.start();
    }
    //nastavime hodnoty v labels
    private void setValues(String password, int threads){
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Details");
        setContentPane(panel);
        lblPassword.setText("Heslo: ");
        lblTime.setText("Čas: ");
        lblThreads.setText("Počet vlákien: ");
        txtPassword.setText(password);
        txtThreads.setText("" + threads);
    }
    //runnable pre update sekund v detail
    private Runnable updateRun(){
        Runnable updateValues = new Runnable() {
            @Override
            public void run() {
                //bezi kym neskoncia vlákna, ktoré prehľadávajú súbory
                while (fs.getProgres()<=17) {
                    long now = System.nanoTime() - startTime;
                     nowSECONDS = TimeUnit.SECONDS.convert(now, TimeUnit.NANOSECONDS);
                    txtTime.setText("" + nowSECONDS);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        };
        return updateValues;
    }
    //beží, kým sa nezatvorí vyľadavcie okno, a zapíše hodnoty z vyhľadávania
    private Runnable isVisibleRun(){
        Runnable isVisible = new Runnable() {
            @Override
            public void run() {

                while (true){
                    if (!fs.isShowing()){
                        Controler c = new Controler(password, nowSECONDS, threads);
                        c.start();
                        dispose();
                        break;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return isVisible;
    }

}
