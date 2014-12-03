package ga;

import java.util.ArrayList;
import java.io.*;
import java.util.Collections;

/**
 * Created by Andrei on 12/1/2014.
 * Version v1.12
 */
public class DesignData implements Serializable {

    private ArrayList<Design> rank;
    private ArrayList<Design> rankBackup;
    private int len;

    public DesignData(int len) {
        this.len = len;
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

        rankBackup = this.getRank();
        if (rank.isEmpty()) {
            rank.add(design);
        }
        else if (rank.size()<len) {
            if (!rank.contains(design)) {
                rank.add(design);
                Collections.sort(rank);
            }
        }
        else {
            if (!rank.contains(design)) {
                if (rank.get(len-1).getValue().doubleValue()<design.getValue().doubleValue()) {
                    rank.set(len-1, design);
                    Collections.sort(rank);
                }
            }
        }
    }
}
