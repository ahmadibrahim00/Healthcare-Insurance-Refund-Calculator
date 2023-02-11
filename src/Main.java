import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ParseException
    {
        Validation validation = new Validation();
        JSONHash JSON = new JSONHash("Assurance.json", "resultat.json");
        JSON.load();
        if (validation.estFichierValide(JSON.getFilename(), JSON.getResultat())) {
            JSON.getNumClient();
            JSON.getContrat();
            JSON.getMois();
            JSON.getSoin(1);
            JSON.getDate(0);
            JSON.getMontant(0);
            JSON.getNbSoin();
            JSON.save();

            ArrayList<Soin> reclamation = new ArrayList<>();
            for (int i = 0; i < JSON.getNbSoin(); i++) {
                reclamation.add(new Soin(JSON.getNumClient(), JSON.getContrat(), JSON.getMois(),
                        JSON.getSoin(i), JSON.getDate(i), JSON.getMontant(i)));
            }

            ArrayList<Soin> soinsRembourses = new ArrayList<>();
            for (int i = 0; i < reclamation.size(); i++) {
                soinsRembourses.add(new Soin(reclamation.get(i).getClient(),
                        reclamation.get(i).getTypeContrat(), reclamation.get(i).getDateReclamation(),
                        reclamation.get(i).getNumeroSoin(), reclamation.get(i).getDateSoin(),
                        reclamation.get(i).calculerRemboursement()));
            }

            Remboursement remboursement = new Remboursement(soinsRembourses.get(0).getClient(),
                    soinsRembourses.get(0).getDateReclamation(), soinsRembourses);

        } else {
            JSONObject FichierErreur = new JSONObject();
            FichierErreur.put("message", validation.getMessageErreur());
        }
    }
}