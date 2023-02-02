import org.json.simple.parser.ParseException;

import java.io.IOException;

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
    }
}