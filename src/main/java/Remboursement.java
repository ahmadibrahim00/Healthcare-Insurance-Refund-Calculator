/**
 * Cette classe permet de formater les données pour retourner l'objet JSON dans le ficheir de sortie
 *
 * @version 12 février 2023
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Remboursement {
    private long client;
    private String dateReclamation;
    private ArrayList<Soin> soinsRembourses;

    public Remboursement(long client, String dateReclamation, ArrayList<Soin> soinsRembourses) {
        this.client = client;
        this.dateReclamation = dateReclamation;
        this.soinsRembourses = soinsRembourses;
    }

    public long getClient() {
        return client;
    }

    public String getDateReclamation() {
        return dateReclamation;
    }

    public ArrayList<Soin> getSoinsRembourses() {
        return soinsRembourses;
    }
    static JSONArray json = new JSONArray();
    static JSONObject json2 = new JSONObject();
    /**
     * Cette méthode initialise un nouvel objet remboursement suivis de ces variables. On prend les donées traitées du
     * numéro du client, sa date de réclamation ainsi que son numéro de soin, sa date de soin et du prix qui dois lui
     * être remboursé. Les 3 dernières données nommées sont dans une loop for qui loop sur le nombre total des soins
     * appliqués sur le client. Ces informations sont alors inséré dans un objet JSON
     */
    public static void outputJSON() throws IOException, ParseException {
        Remboursement remboursement = new Remboursement(CalculateurReclamation.getSoinsRembourses().get(0).getClient(),
                CalculateurReclamation.getSoinsRembourses().get(0).getDateReclamation(),
                CalculateurReclamation.getSoinsRembourses());
        json2.put("Numéro Client", String.valueOf(remboursement.getClient()));
        json2.put("Année-Mois Réclamation", remboursement.getDateReclamation());
        for (int i = 0; i < remboursement.getSoinsRembourses().size(); i++) {
            JSONObject json1 = new JSONObject();
            json1.put("Numéro du soin", remboursement.getSoinsRembourses().get(i).getNumeroSoin());
            json1.put("Date du soin", remboursement.getSoinsRembourses().get(i).getDateSoin());
            json1.put("Montant remboursé", remboursement.getSoinsRembourses().get(i).toStringPrixSoin());
            json.add(json1);
        }
        json2.put("Réclamations", json);
    }
    public static void outputJSONErreur(String messageErreur) {
        json2.put("message", messageErreur);
        System.out.println(messageErreur);
    }
    /**
     * Cette méthode permet de formatter la sortie de toutes les données traiter dans le format style JSON
     */
    public static void formatJSON() throws FileNotFoundException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String output = gson.toJson(json2);
        JSONHash.save(output);
    }
}