import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException
    {
        Validation validation = new Validation();
        JSONHash JSON = new JSONHash(args[0], args[1]);
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

            Remboursement remboursement = new Remboursement(CalculateurReclamation.getSoinsRembourses().get(0).getClient(),
                    CalculateurReclamation.getSoinsRembourses().get(0).getDateReclamation(), CalculateurReclamation.getSoinsRembourses());

            //Tests des valeurs de sortie (temporaire)
            System.out.println(remboursement.getClient());
            System.out.println(remboursement.getDateReclamation());
            for (int i = 0; i < remboursement.getSoinsRembourses().size(); i++){
                System.out.println(remboursement.getSoinsRembourses().get(i).getNumeroSoin());
                System.out.println(remboursement.getSoinsRembourses().get(i).getDateSoin());
                System.out.println(remboursement.getSoinsRembourses().get(i).getPrixSoin());
            }

        } else {
            JSONObject FichierErreur = new JSONObject();
            FichierErreur.put("message", validation.getMessageErreur());
        }
    }
}