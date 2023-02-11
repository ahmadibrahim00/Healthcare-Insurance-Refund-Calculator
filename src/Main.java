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

            Remboursement remboursement = new Remboursement(Reclamation.soinsRembourses().get(0).getClient(),
                    Reclamation.soinsRembourses().get(0).getDateReclamation(), Reclamation.soinsRembourses());
        } else {
            JSONObject FichierErreur = new JSONObject();
            FichierErreur.put("message", validation.getMessageErreur());
        }
    }
}