import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException
    {
        JSONHash JSON = new JSONHash("Assurance.json", "resultat.json");

        JSON.load();
        JSON.objClient();
        JSON.objContrat();
        JSON.getSoin();
        JSON.getDate();
        JSON.getMontant();
        JSON.save();
    }
}