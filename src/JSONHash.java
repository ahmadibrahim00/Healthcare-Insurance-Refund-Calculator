import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class JSONHash {
    private String filename;
    private String resultat;
    private JSONObject jsonobj;

    public JSONHash(String filename, String resultat) {
        this.filename = filename;
        this.resultat = resultat;
        this.jsonobj = null;
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
    }

    public Object objClient() throws ParseException {
        String client = (String) jsonobj.get("client");
        return client;
    }
    public Object objContrat() throws ParseException
    {
        String contrat = (String) jsonobj.get("contrat");
        return contrat;
    }
    public Object objMois() throws ParseException{
        String mois = (String) jsonobj.get("mois");
        return mois;
    }
    public void getSoin()
    {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        for (Object arA: reclamations)
        {
            List<Object> array = new ArrayList<Object>();
            JSONObject reclamation = (JSONObject) arA;
            array.add(reclamation.get("soin"));
            System.out.println(array);
        }

        }
    public void getDate()
    {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        for (Object arA: reclamations)
        {
            List<Object> array = new ArrayList<Object>();
            JSONObject reclamation = (JSONObject) arA;
            array.add(reclamation.get("date"));
            System.out.println(array);
        }

    }
    public void getMontant()
    {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        for (Object arA: reclamations)
        {
            List<Object> array = new ArrayList<Object>();
            JSONObject reclamation = (JSONObject) arA;
            array.add(reclamation.get("montant"));
            System.out.println(array);
        }

    }
    }

