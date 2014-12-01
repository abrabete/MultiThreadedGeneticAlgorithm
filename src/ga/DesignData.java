package ga;

import java.util.ArrayList;
import java.io.*;

/**
 * Created by Andrei on 12/1/2014.
 * Version v1.12
 */
public class DesignData implements Serializable {

    private ArrayList<Design> rank;
    private ArrayList<Design> rankBackup;

    public DesignData(int len) {
        this.rank = new ArrayList<Design>(len);
        this.rankBackup = new ArrayList<Design>(len);
    }

    public void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream("designs.ser");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this.rank);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public void saveFile() {
        try {
            PrintWriter writer = new PrintWriter("results.txt");
            for (int i=0; i<this.rank.size(); i++) {
                writer.println("Design number: " + i + "= " + this.rank.get(i).getValue());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public synchronized ArrayList<Design> getRank() {
        return this.rank;
    }

    public synchronized ArrayList<Design> getRankBackup() {
        return this.rankBackup;
    }

    public synchronized void updateRank(Design design) {

    }
}
