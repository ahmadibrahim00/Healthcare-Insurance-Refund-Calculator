import java.util.ArrayList;

public class Remboursement {
    private long client;
    private String dateReclamation;
    public ArrayList<Soin> soinsRembourses;

    public Remboursement(long client, String dateReclamation, ArrayList<Soin> soinsRembourses) {
        this.client = client;
        this.dateReclamation = dateReclamation;
        this.soinsRembourses = soinsRembourses;
    }
}