import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Statistique {
    JSONObject fichier = new JSONObject();

    int reclamationsValides;
    int reclamationsInvalides;
    int soin0;
    int soin100;
    int soin150;
    int soin175;
    int soin200;
    int soin300;
    int soin400;
    int soin500;
    int soin600;
    int soin700;

    /**
     * Cette methode permet d'aller chercher les statistiques existantes dans le cas ou ce n'est pas la premiere fois
     * que le prograamme est execute.
     * @throws IOException dans le cas ou le fichier n'existe pas.
     * @throws ParseException
     */
    public void getStatistiques() throws IOException, ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader("Statistiques.json"));
        if (obj != null) {
            reclamationsValides = (int) obj.get("reclamations valides");
            reclamationsInvalides = (int) obj.get("reclamations invalides");
            getNombreParTypeSoins((JSONObject) obj.get("nombre de soins par type"));
        }
    }

    /**
     *
     * @param obj
     */
    public void getNombreParTypeSoins(JSONObject obj){
        soin0 = (int) obj.get("soin 0");
        soin100 = (int) obj.get("soin 100");
        soin150 = (int) obj.get("soin 150");
        soin175 = (int) obj.get("soin 175");
        soin200 = (int) obj.get("soin 200");
        soin300 = (int) obj.get("soin 300-399");
        soin400 = (int) obj.get("soin 400");
        soin500 = (int) obj.get("soin 500");
        soin600 = (int) obj.get("soin 600");
        soin700 = (int) obj.get("soin 700");
    }

    /**
     *
     * @param obj
     * @return
     */
    public static int getNombreReclamationsInvalides(JSONArray obj) {
        int nombresReclamationsInvalides = 0;
        if(obj != null)
        {
            nombresReclamationsInvalides = obj.size();
        }
        return nombresReclamationsInvalides;
    }

    public static int getNombreReclamationsValides(JSONArray obj)
    {
        Validation validation = new Validation();
        String entree = " ", sortie = " ";
        boolean estvalide = validation.estFichierValide(entree, sortie);
        int nombresReclamationsValides = 0;
        if (estvalide)
        {
            nombresReclamationsValides++;
        }
        return nombresReclamationsValides;
    }
    /**
     * Cette methode permet de creer un JSONObject contenant toutes les statistiques demandees.
     */
    public void formatterJsonStatistiques() {
        JSONObject soins = new JSONObject();
        JSONArray soinsParType = new JSONArray();
        fichier.put("reclamations valides", reclamationsValides);
        fichier.put("reclamations invalides", reclamationsInvalides);
        creerArraySoins(soins);
        soinsParType.add(soins);
        fichier.put("nombre de soins par type", soinsParType);
    }

    /**
     * Cette methode permet de creer un JSONObject contenant tous les soins déclarés par type.
     * @param soins JSONObject contenant le nombre de soins déclarés par type.
     */
    public void creerArraySoins(JSONObject soins) {
        soins.put("soin 0", soin0);
        soins.put("soin 100", soin100);
        soins.put("soin 150", soin150);
        soins.put("soin 175", soin175);
        soins.put("soin 200", soin200);
        soins.put("soin 300-399", soin300);
        soins.put("soin 4000", soin400);
        soins.put("soin 500", soin500);
        soins.put("soin 600", soin600);
        soins.put("soin 700", soin700);
    }

    /**
     * Cette methode permet de formatter le fichier de statistiques.
     * @throws FileNotFoundException a cause du PrintWriter.
     */
    public void modifierFichierStatistiques() throws FileNotFoundException {
        formatterJsonStatistiques();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String output = gson.toJson(fichier);
        PrintWriter pw = new PrintWriter("Statistiques.json");
        pw.write(output);
        pw.flush();
        pw.close();
    }

    /**
     * Cette methode permet de creer/modifier le fichier "Statistiques.json".
     * @param reclamations Les reclamations du fichier d'entrée courant.
     * @throws FileNotFoundException a cause du PrintWriter.
     */
    public static void creerFichierSortieStatistiques(JSONArray reclamations, JSONHash json)
            throws FileNotFoundException
    {
        Validation validation = new Validation();
        Statistique statistique = new Statistique();
        if (!validation.estFichierValide(json.getFilename(), json.getResultat()))
        {
            statistique.reclamationsInvalides = statistique.reclamationsInvalides +
                    getNombreReclamationsInvalides(reclamations);
        }
        else
        {
            statistique.reclamationsValides = statistique.reclamationsValides + getNombreReclamationsValides(reclamations);
        }

        statistique.modifierFichierStatistiques();
    }
}
