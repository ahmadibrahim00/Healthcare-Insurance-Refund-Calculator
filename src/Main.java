import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * La methode Main permet de prendre un fichier d'entree JSON, de verifier la validite de son contenu et de creer un
 * fichier de sortie JSON contenant, soit les donnees modifiees ou un message d'erreur specifiant le premier probleme
 * survenu.
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Validation validation = new Validation();
        JSONHash JSON = new JSONHash("Assurance.json", "lol.json");
        JSON.load();
        if (validation.estFichierValide(JSON.getFilename(), JSON.getResultat())) {
        CalculateurReclamation.setFichierEntree("Assurance.json");
        CalculateurReclamation.setFichierSortie("lol.json");
        new CalculateurReclamation();
            Remboursement.outputJSON();
        } else {
            Remboursement.outputJSONErreur(validation.getMessageErreur());
        }
        Remboursement.formatJSON();
    }
}