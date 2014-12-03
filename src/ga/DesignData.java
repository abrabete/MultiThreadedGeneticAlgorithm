package ga;

import java.util.ArrayList;
import java.io.*;
import java.util.Collections;

/**
 * The DesignData Class will hold a record of the elite ranked designs.
 *
 * @version 1.0
 */
public class DesignData implements Serializable {

    private ArrayList<Design> rank; //elite ranked set
    private ArrayList<Design> rankBackup; //one update behind rank list
    private int len; //the given length of the elite set

    /**
     * Method to construct DesignData with ranked lists of specified length.
     *
     * @param len The length of the elite set
     */
    public DesignData(int len) {
        this.len = len;
        this.rank = new ArrayList<Design>(len);
        this.rankBackup = new ArrayList<Design>(len);
    }

    /**
     * Method to save the best ranked designs into a serialized file.
     *
     */
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

    /**
     * Method to write a text file containing the top ranked designs,
     * one on each row, ordered from best to worst.
     *
     */
    public void saveFile() {
        try {
            PrintWriter writer = new PrintWriter("results.txt");
            // write out the value of each design to a new line in the file
            for (int i=0; i<this.rank.size(); i++) {
                writer.println("Design number: " + i + "= " + this.rank.get(i).getValue());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    /**
     * Method to return the current state of the elite ranked solutions set.
     * Synchronized for thread safety.
     *
     * @return the current state of the elite ranked solutions
     */
    public synchronized ArrayList<Design> getRank() {
        return this.rank;
    }

    /**
     * Method to return the ranked set which is one update behind the live version.
     * Synchronized for thread safety.
     *
     * @return ranked set one update behind live version
     */
    public synchronized ArrayList<Design> getRankBackup() {
        return this.rankBackup;
    }

    /**
     * Method that updates the best ranked solutions.
     * Synchronized for thread safety.
     *
     * @param design The design for the set to be updated with
     */
    public synchronized void updateRank(Design design) {

        rankBackup = this.getRank(); // update the backup
        // simply add the design if the ranked set is empty
        if (rank.isEmpty()) {
            rank.add(design);
        }
        // if the ranked set is not empty, but not full
        // check that the design is not already contained within the list
        // and if not, add to the list and then sort it
        else if (rank.size()<len) {
            if (!rank.contains(design)) {
                rank.add(design);
                Collections.sort(rank);
            }
        }
        // if the ranked set is full, check to see if it contains the design
        // if it does not, check the worst design in the set against the new design
        // if the new design is better, replace the current worst design
        // and then sort the list
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
