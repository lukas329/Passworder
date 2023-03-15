package sample;

import java.io.*;
import java.util.ArrayList;

public class Finder extends Thread{
    static Integer precitane;
    private static int currentFile;
    static boolean goThread;
    String password;
    String line;
    private static ArrayList<File> files;
    final FinderScreen fs;

    public Finder(String password, FinderScreen fs){
        this.password = password;
        currentFile = precitane = 0;
        goThread=true;
        files = FinderScreen.getFiles();
        this.fs = fs;
    }

    @Override
    public void run() {
        //vlákno beží kým neprejde všetky súbory
        while (currentFile<=16){
            File file;
            //použitím synchronized na arraylist zabezpečíme, že iba jedno vlákno si berie súbor z arraylistu
            synchronized (files){
                file = files.get(currentFile);
                currentFile++;
            }
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                System.out.println("Thread " + getName() + " si berie file " + file.getName());
                //kým nie sú všetky prečítané
                while (precitane <=16) {
                    line = br.readLine();
                    if (line == null) {
                        //ak je na konci suboru zvysi precitane o 1
                        precitane++;
                        System.out.println("Thread " + getName() + " prešiel celý file " + file.getName());
                        setIterator(fs);
                        //ak prejde aj 16. nastavi progres na konecnu hodnotu
                        if (precitane > 16){
                            setProgres();
                            fs.setProgres(17);
                            setIterator(fs);
                        }
                        break;
                    }
                    else if (line.equals(password)) {
                        //ak sme nasli heslo nastavime progres, zastavime ostatne vlákna a ukoncime toto vlákno
                        fs.setFile(file.getName());
                        fs.setProgres(17);
                        interrupt();
                        setProgres();
                        currentFile = 17;
                        break;
                    }
                }
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void setIterator(FinderScreen fs){
        fs.setProgres(precitane);
    }
    @Override
    public void interrupt() {
        goThread = false;
    }
    @Override
    public boolean isInterrupted() {
        return !goThread;
    }
    private void setProgres(){
        precitane = 17;
    }
}
