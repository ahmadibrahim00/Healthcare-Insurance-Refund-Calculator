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
    long reclamationsValides;
    long reclamationsInvalides;
    long soin0;
    long soin100;
    long soin150;
    long soin175;
    long soin200;
    long soin300;
    long soin400;
    long soin500;
    long soin600;
    long soin700;

    /**
     * Cette methode permet d'aller chercher les statistiques existantes dans le cas ou ce n'est pas la premiere fois
     * que le prograamme est execute.
     * @throws IOException dans le cas ou le fichier n'existe pas.
     * @throws ParseException
     */
    public void getStatistiques() throws IOException, ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader("Statistiques.json"));
        if (obj != null) {
            reclamationsValides = (long) obj.get("reclamations valides");
            reclamationsInvalides = (long) obj.get("reclamations invalides");
            getNombreParTypeSoins((JSONObject) ((JSONArray) obj.get("nombre de soins par type")).get(0));
        }
    }

    /**
     *
     * @param obj
     */
    public void getNombreParTypeSoins(JSONObject obj){
        soin0   = (long) obj.get("soin 0");
        soin100 = (long) obj.get("soin 100");
        soin150 = (long) obj.get("soin 150");
        soin175 = (long) obj.get("soin 175");
        soin200 = (long) obj.get("soin 200");
        soin300 = (long) obj.get("soin 300-399");
        soin400 = (long) obj.get("soin 400");
        soin500 = (long) obj.get("soin 500");
        soin600 = (long) obj.get("soin 600");
        soin700 = (long) obj.get("soin 700");
    }

    /**
     *
     * @param obj
     * @return
     */
    public long getStatistiquesExistantes(JSONArray obj) throws IOException, ParseException {
        long nombresReclamationsInvalides = 0;
        if(obj != null)
        {
            nombresReclamationsInvalides = obj.size();
        }
        return nombresReclamationsInvalides;
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
        soins.put("soin 400", soin400);
        soins.put("soin 500", soin500);
        soins.put("soin 600", soin600);
        soins.put("soin 700", soin700);
    }
    public void additionnerSoinsParType1(long soin) {
        if (soin == 0)
            soin0++;
        if (soin == 100)
            soin100++;
        if (soin == 150)
            soin150++;
        if (soin == 175)
            soin175++;
        if (soin == 200)
            soin200++;
    }
    public void additionnerSoinsParType2(long soin) {
        if (soin >= 300 && soin <= 399)
            soin300++;
        if (soin == 400)
            soin400++;
        if (soin == 500)
            soin500++;
        if (soin == 600)
            soin600++;
        if (soin == 700)
            soin700++;
    }

    /**
     *
     * @param reclamations
     */
    public void cumulerSoinsParType(JSONArray reclamations) {
        for(int i = 0 ; i < reclamations.size() ; i++) {
            long soin = (long) ((JSONObject) reclamations.get(i)).get("soin");
            additionnerSoinsParType1(soin);
            additionnerSoinsParType2(soin);
        }
    }
    public void afficherStatistiques(String statistiques, String option) {
        if (option.equals("-S")) {
            System.out.println(statistiques);
        } else if (option.equals("-SR")) {

        }
    }

    /**
     * Cette methode permet de formatter le fichier de statistiques.
     * @throws FileNotFoundException a cause du PrintWriter.
     */
    public String modifierFichierStatistiques() throws FileNotFoundException {
        formatterJsonStatistiques();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String output = gson.toJson(fichier);
        PrintWriter pw = new PrintWriter("Statistiques.json");
        pw.write(output);
        pw.flush();
        pw.close();
        return output;
    }

    /**
     * Cette methode permet de creer/modifier le fichier "Statistiques.json".
     *
     * @param json pour acceder aux reclamations du fichier d'entrée courant.
     * @throws FileNotFoundException a cause du PrintWriter.
     * @@param option
     */
    public String creerFichierSortieStatistiques(JSONHash json)
            throws IOException, ParseException {
        JSONArray reclamations = (JSONArray) json.getJsonobj().get("reclamations");
        Validation validation = new Validation();
        getStatistiques();
        if (!validation.estFichierValide(json.getFilename(), json.getResultat())) {
            reclamationsInvalides = reclamationsInvalides + getStatistiquesExistantes(reclamations);
        } else {
            reclamationsValides = reclamationsValides + getStatistiquesExistantes(reclamations);
            cumulerSoinsParType((JSONArray) json.getJsonobj().get("reclamations"));
        }
        return modifierFichierStatistiques();
    }
}
