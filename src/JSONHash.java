/**
 * Cette classe sert a aller chercher les donnees dans le fichier d'entree et de permettre d'en sortir les valeurs.
 *
 * @author Ahmad Ibrahim (IBRA12069802)
 * @version 12 février 2023
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JSONHash {
    private String filename;
    private static String resultat;
    private JSONObject jsonobj;

    public JSONHash(String filename, String resultat) {
        this.filename = filename;
        this.resultat = resultat;
        this.jsonobj = null;
    }

    public String getFilename() {
        return filename;
    }

    public String getResultat() {
        return resultat;
    }

    public JSONObject getJsonobj() {
        return jsonobj;
    }
    /**
     * Cette méthode permet de charger le fichier demandé, sinon il output une erreur
     */
    public void load() throws IOException, ParseException {
        try {
            Object obj = new JSONParser().parse(new FileReader(filename));
            jsonobj = (JSONObject) obj;
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non existant");
        }
    }
    /**
     * Cette méthode ecrit le resultat final dans un fichier resultat JSON
     */
    public static void save(String output) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(resultat);
        pw.write(output);
        pw.flush();
        pw.close();
    }
    /**
     * Cette méthode va chercher la valeur du numéro de client
     */
    public long getNumClient() {
        String client = (String) jsonobj.get("client");
        Long numclient = Long.valueOf(client);
        return numclient;
    }
    /**
     * Cette méthode va chercher la lettre du contrat
     */
    public char getContrat() {
        String contrat = (String) jsonobj.get("contrat");
        char lettrecontrat = contrat.charAt(0);
        return lettrecontrat;
    }
    /**
     * Cette méthode va chercher le mois du contrat
     */
    public String getMois() {
        String mois = (String) jsonobj.get("mois");
        return mois;
    }
    /**
     * Cette méthode va chercher le numéro du soin du contrat
     */
    public Long getSoin(int i) {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        ArrayList<Long> array = new ArrayList<Long>();
        for (Object arA: reclamations) {
            JSONObject reclamation = (JSONObject) arA;
            Long soin = (Long) reclamation.get("soin");
            array.add(soin);
        }
        return array.get(i);
    }
    /**
     * Cette méthode va chercher le nombre de soins appliqué sur le client dans le contrat
     */
    public int getNbSoin() {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        int cpt_soins = 0;
        for (Object arA: reclamations) {
            JSONObject reclamation = (JSONObject) arA;
            Long soin = (Long) reclamation.get("soin");
            cpt_soins += 1;
        }
        return cpt_soins;
    }
    /**
     * Cette méthode va chercher la date complète d'un soin
     */
    public String getDate(int i) {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        ArrayList<String> array = new ArrayList<String>();
        for (Object arA: reclamations) {
            JSONObject reclamation = (JSONObject) arA;
            String date = (String) reclamation.get("date");
            array.add(date);
        }
        return array.get(i);
    }
    /**
     * Cette méthode va chercher le montant payé par le client pour un soin dans le contrat
     */
    public Float getMontant(int i)  {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        ArrayList<Float> array = new ArrayList<>();
        for (Object arA: reclamations) {
            JSONObject reclamation = (JSONObject) arA;
            String montant = (String) reclamation.get("montant");
            String montantparsed = montant.replaceAll("[$,]", "");
            Float montantfinal = Float.valueOf(montantparsed);
            array.add(montantfinal);
        }
        return array.get(i);
    }
}




