import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException
    {
        JSONHash JSON = new JSONHash("Assurance.json", "resultat.json");

        JSON.load();
        JSON.objClient();
        JSON.objContrat();
        JSON.getSoin(0);
        JSON.getDate(0);
        JSON.getMontant(0);
        JSON.save();
    }
}