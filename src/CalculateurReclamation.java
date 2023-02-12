/**
 * Cette classe contien des methodes qui appliquent le remboursement selon les donnes fournis dans Soin
 * @author Vincent Michaud
 * @version 12 fevrier 2023
 */

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class CalculateurReclamation {
    /**
     * Prend les informations d'un fichier JSON et cree une liste des soins dans la reclamation
     * @return Une liste qui contient la liste des soins a rembourser qui contiennent les informations du client
     * @throws IOException
     * @throws ParseException
     */
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

    /**
     * Premd les Soins, leur applique le calcul de remboursement et les retourne dans une liste de soins avec les valeurs du remboursement
     * @return Une liste qui contient les soins avec la valeur de leur remboursement
     * @throws IOException
     * @throws ParseException
     */
    public static ArrayList<Soin> getSoinsRembourses() throws IOException, ParseException{
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
