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

        for (int i = 0; i < "<INSERER TAILLE DE LARRAY ICI>"; i++){
            listeSoins.add(new Soin(JSON.getSoin(i), JSON.getDate(i), JSON.getMontant(i),
                JSON.getContrat(), JSON.getNumClient(), JSON.getMois()));
        }

    }
}