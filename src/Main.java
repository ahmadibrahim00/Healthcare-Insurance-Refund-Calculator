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

        ArrayList<Soin> listeSoins = new ArrayList<>();

        //Creation du arraylist avec des objets Soin avec tous leurs attributs
        //TODO la condition doit etre "i < JSON.getNombreReclamations()" au lieu de "i < 3"
        for (int i = 0; i < 3 ; i++) {
            listeSoins.add(new Soin(JSON.getSoin(i), JSON.getDate(i), JSON.getMontant(i),
                JSON.getContrat(), JSON.getNumClient(), JSON.getMois()));
        }

        //Test temporaire pour voir si les loops fonctionnent
        for (int i = 0; i < listeSoins.size(); i++){
            System.out.println(listeSoins.get(i).getNumeroSoin());
        }
        
        //Test temporaire pour voir si le calcul de remboursement fonctionne
        for (int i = 0; i < listeSoins.size(); i++){
            System.out.println(listeSoins.get(i).calculerRemboursement());
        }

    }
}