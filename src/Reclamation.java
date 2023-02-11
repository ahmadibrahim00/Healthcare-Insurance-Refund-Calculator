import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Reclamation {

    private static ArrayList<Soin> genererReclamation() throws IOException, ParseException {
        JSONHash JSON = new JSONHash("Assurance.json", "resultat.json");
        JSON.load();
        ArrayList<Soin> reclamation = new ArrayList<>();
        for (int i = 0; i < JSON.getNbSoin(); i++) {
                reclamation.add(new Soin(JSON.getNumClient(), JSON.getContrat(), JSON.getMois(),
                        JSON.getSoin(i), JSON.getDate(i), JSON.getMontant(i)));
            }
        return reclamation;
    }

    public static ArrayList<Soin> soinsRembourses() throws IOException, ParseException{
        ArrayList<Soin> soinsRembourses = new ArrayList<>();
            for (int i = 0; i < genererReclamation().size(); i++) {
                soinsRembourses.add(new Soin(genererReclamation().get(i).getClient(),
                        genererReclamation().get(i).getTypeContrat(), genererReclamation().get(i).getDateReclamation(),
                        genererReclamation().get(i).getNumeroSoin(), genererReclamation().get(i).getDateSoin(),
                        genererReclamation().get(i).calculerRemboursement()));
            }
            return soinsRembourses;
    }
}
