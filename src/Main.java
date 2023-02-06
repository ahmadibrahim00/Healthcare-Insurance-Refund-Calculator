import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ParseException
    {
        JSONHash JSON = new JSONHash("Assurance.json", "resultat.json");

        JSON.load();
        JSON.getNumClient();
        JSON.getContrat();
        JSON.getMois();
        JSON.getSoin(1);
        JSON.getDate(0);
        JSON.getMontant(0);
        JSON.save();

        ArrayList<Soin> reclamation = new ArrayList<>();
        //Creation du arraylist avec des objets Soin avec tous leurs attributs
        //TODO la condition doit etre "i < JSON.getNombreReclamations()" au lieu de "i < 3"
        for (int i = 0; i < 3 ; i++) {
            reclamation.add(new Soin(JSON.getNumClient(), JSON.getContrat(), JSON.getMois(),
                    JSON.getSoin(i), JSON.getDate(i), JSON.getMontant(i)));
        }

        ArrayList<Soin> soinsRembourses = new ArrayList<>();
         for (int i = 0; i < reclamation.size() ; i++) {
             soinsRembourses.add(new Soin(reclamation.get(i).getClient(),
                     reclamation.get(i).getTypeContrat(), reclamation.get(i).getDateReclamation(),
                     reclamation.get(i).getNumeroSoin(), reclamation.get(i).getDateSoin(),
                     reclamation.get(i).calculerRemboursement()));
         }

         Remboursement remboursement = new Remboursement(soinsRembourses.get(0).getClient(),
                     soinsRembourses.get(0).getDateReclamation(), soinsRembourses);
    }
}