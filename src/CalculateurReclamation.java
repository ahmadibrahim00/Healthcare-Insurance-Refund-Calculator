import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class CalculateurReclamation {
    public CalculateurReclamation() {
    }

    private static ArrayList<Soin> genererReclamation() throws IOException, ParseException {
        JSONHash JSON = new JSONHash("Assurance.json", "resultat.json");
        JSON.load();
        ArrayList<Soin> reclamation = new ArrayList();

        for(int i = 0; i < JSON.getNbSoin(); ++i) {
            reclamation.add(new Soin(JSON.getNumClient(), JSON.getContrat(), JSON.getMois(), JSON.getSoin(i), JSON.getDate(i), (double)JSON.getMontant(i)));
        }

        return reclamation;
    }

    public static ArrayList<Soin> getSoinsRembourses() throws IOException, ParseException {
        ArrayList<Soin> soinsRembourses = new ArrayList();

        for(int i = 0; i < genererReclamation().size(); ++i) {
            soinsRembourses.add(new Soin(((Soin)genererReclamation().get(i)).getClient(), ((Soin)genererReclamation().get(i)).getTypeContrat(), ((Soin)genererReclamation().get(i)).getDateReclamation(), ((Soin)genererReclamation().get(i)).getNumeroSoin(), ((Soin)genererReclamation().get(i)).getDateSoin(), ((Soin)genererReclamation().get(i)).calculerRemboursement()));
        }

        return soinsRembourses;
    }
}