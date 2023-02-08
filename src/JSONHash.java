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
    private String resultat;
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

    public void load() throws IOException, ParseException {
        try {
            Object obj = new JSONParser().parse(new FileReader(filename));
            jsonobj = (JSONObject) obj;
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non existant");
        }
    }

    public void save() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(resultat);
        pw.write(jsonobj.toJSONString());

        pw.flush();
        pw.close();
    }

    public long getNumClient()
    {
        String client = (String) jsonobj.get("client");
        Long numclient = Long.valueOf(client);
        return numclient;
    }
    public char getContrat()
    {
        String contrat = (String) jsonobj.get("contrat");
        char lettrecontrat = contrat.charAt(0);
        return lettrecontrat;
    }
    public String getMois()
    {
        String mois = (String) jsonobj.get("mois");
        return mois;
    }
    public Long getSoin(int i)
    {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        ArrayList<Long> array = new ArrayList<Long>();
        for (Object arA: reclamations)
        {
            JSONObject reclamation = (JSONObject) arA;
            Long soin = (Long) reclamation.get("soin");
            array.add(soin);
        }
        return array.get(i);
    }
    public int getNbSoin()
    {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        int cpt_album = 0;
        for (Object arA: reclamations)
        {
            JSONObject reclamation = (JSONObject) arA;
            Long soin = (Long) reclamation.get("soin");
            cpt_album += 1;
        }
        return cpt_album;
    }
    public String getDate(int i)
    {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        ArrayList<String> array = new ArrayList<String>();
        for (Object arA: reclamations)
        {
            JSONObject reclamation = (JSONObject) arA;
            String date = (String) reclamation.get("date");
            array.add(date);
        }
        return array.get(i);
    }
    public Float getMontant(int i)  {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        ArrayList<Float> array = new ArrayList<>();
        for (Object arA: reclamations)
        {
            JSONObject reclamation = (JSONObject) arA;
            String montant = (String) reclamation.get("montant");
            String montantparsed = montant.replaceAll("[$,]", "");
            Float montantfinal = Float.valueOf(montantparsed);
            array.add(montantfinal);
        }
        return array.get(i);
        }

    }




