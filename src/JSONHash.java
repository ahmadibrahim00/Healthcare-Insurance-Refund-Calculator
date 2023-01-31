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

    public long objClient() throws ParseException {
        String client = (String) jsonobj.get("client");
        Long numclient = Long.valueOf(client);
        return numclient;
    }
    public Object objContrat() throws ParseException
    {
        String contrat = (String) jsonobj.get("contrat");
        char lettrecontrat = contrat.charAt(0);
        return lettrecontrat;
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
            long soin = (Long) reclamation.get("soin");
            array.add(soin);
        }
        }
    public void getDate()
    {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        for (Object arA: reclamations)
        {
            List<Object> array = new ArrayList<Object>();
            JSONObject reclamation = (JSONObject) arA;
            String date = (String) reclamation.get("date");
            array.add(date);
        }

    }
    public void getMontant()
    {
        JSONArray reclamations = (JSONArray) jsonobj.get("reclamations");
        for (Object arA: reclamations)
        {
            ArrayList<Object> array = new ArrayList<Object>();
            JSONObject reclamation = (JSONObject) arA;
            String montant = (String) reclamation.get("montant");
            String montantparsed = montant.replaceAll("[$,]", "");
            Float montantfinal = Float.valueOf(montantparsed);
            array.add(montantfinal);
        }
    }

    }


