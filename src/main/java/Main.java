import org.json.simple.parser.ParseException;
import java.io.IOException;

/**
 * La methode Main permet de prendre un fichier d'entree JSON, de verifier la validite de son contenu et de creer un
 * fichier de sortie JSON contenant, soit les donnees modifiees ou un message d'erreur specifiant le premier probleme
 * survenu.
 */
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        JSONHash JSON = new JSONHash(args[0], args[1]);
        Statistique statistique = new Statistique();
        JSON.load();
        CalculateurReclamation.creerFichierSortie(JSON);
        String fichierStatisque = statistique.creerFichierSortieStatistiques(JSON);
        if (args.length > 2)
            statistique.afficherStatistiques(fichierStatisque, args[2]);

    }
}