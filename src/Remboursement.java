import java.util.ArrayList;

public class Remboursement {
    private long client;
    private String dateReclamation;
    private ArrayList<Soin> soinsRembourses;

    public Remboursement(long client, String dateReclamation, ArrayList<Soin> soinsRembourses) {
        this.client = client;
        this.dateReclamation = dateReclamation;
        this.soinsRembourses = soinsRembourses;
    }

    public long getClient() {
        return client;
    }

    public String getDateReclamation() {
        return dateReclamation;
    }

    public ArrayList<Soin> getSoinsRembourses() {
        return soinsRembourses;
    }
}