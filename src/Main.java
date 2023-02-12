import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class Main {
    public Main() {
    }

    public static void main(String[] args) throws IOException, ParseException {
        Validation validation = new Validation();
        JSONHash JSON = new JSONHash("Assurance.json", "resultat.json");
        JSON.load();
        if (validation.estFichierValide(JSON.getFilename(), JSON.getResultat())) {
            Remboursement.outputJSON();
        } else {
            Remboursement.outputJSONErreur(validation.getMessageErreur());
        }
        Remboursement.formatJSON();
    }
}