package sample;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class FinderScreen extends JFrame{
    private JProgressBar progressBar1;
    private JButton btnOk;
    private JLabel lblProgres;
    private JLabel lblFound;
    private JPanel mainPanel;
    long startTime;

    int progres;
    String file;
    static ArrayList<File> files;
    static ArrayList<Thread>threadsList;

    public FinderScreen(final LandingPage lp, final String password, int threads){
        files = new ArrayList<File>();
        threadsList = new ArrayList<Thread>();

        loadFiles();
        setUI(password);
        startTime = System.nanoTime();
        createThreads(threads, password);
        runThreads();


        final Thread progresUpdate = new Thread(getProgresRun());
        progresUpdate.start();


        setButton(progresUpdate, lp);
    }

    //načítanie súborov do arraylistu
    private void loadFiles(){
        files.add(new File("src/assets/rockyou.txt"));

        for (int i = 1; i<=16; i++){
            String tempFile = "src/assets/file"+i+".txt";
            files.add(new File(tempFile));
        }
    }
    //runnable, ktore updatuje progresbar
    private Runnable getProgresRun(){
        Runnable getProgres = new Runnable() {
            @Override
            public void run() {
                while (progres<=17){
                    progressBar1.setValue(progres);
                    //ak progres = 17, to znamená, že vlákna našli heslo alebo prešli všetky súbory
                    if (progres==17){
                        if (file == null)lblFound.setText("Tvoje heslo som nenašiel");
                        else{
                            lblFound.setText("Tvoje heslo som našiel v súbore: " + file);
                        }
                        //ukončíme vlákno
                        progres = 18;
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return getProgres;
    }
    public void setProgres(int progres){
        this.progres = progres;
    }
    public int getProgres(){
        return progres;
    }
    private void setButton(final Thread dd, final LandingPage lp){
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lp.setVisible(true);
                dispose();
                dd.stop();
            }
        });
    }
    //obsahuje runnable, ktore nastavi UI
    private void setUI(String password){
        lblFound.setText("Hľadám heslo  " + password);
        Runnable setUI = new Runnable() {
            @Override
            public void run() {
                setContentPane(mainPanel);
                lblProgres.setText("Progres: ");

                btnOk.setText("Späť");
                progressBar1.setMinimum(0);
                progressBar1.setMaximum(17);
                setSize(1200, 700);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setVisible(true);
                setTitle("Finder page");
            }
        };
        Thread ne = new Thread(setUI);
        ne.setPriority(Thread.MAX_PRIORITY);
        ne.start();
    }
    public void setFile(String file){
        this.file = file;
    }
    //vytvorime vlákna a zapiseme do arraulistu
    private void createThreads(int threads, String password){
        for (int i = 0; i<threads; i++){
            Finder th = new Finder(password, this);
            th.setName("Thread" + i);
            th.setPriority(Thread.MAX_PRIORITY);
            threadsList.add(th);
        }
    }
    //spustime vlákna z arraylistu
    private void runThreads(){
        for (Thread thread:threadsList){
            System.out.println(thread.getName());
            thread.start();
        }
    }
    public long getStartTime(){
        return startTime;
    }
    public static ArrayList<File> getFiles() {
        return files;
    }

}
