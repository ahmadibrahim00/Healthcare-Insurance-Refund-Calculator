import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ValidationTest {
    private final String MSG_ERR_FILE_NOT_FOUND = "******Le fichier \"Assurance.json\" n'est pas dans le root " +
            "directory ou son nom a ete modifie******";
    private final String MSG_ERR_FILE_VIDE = "******Le fichier \"Assurance.json\" est vide.******";
    private final String MSG_ERR_FILE_INVALIDE = "******Le contenu du fichier \"Assurance.json\" a ete modifie, les " +
            "tests ne peuvent pas etre compiles******";
    private static JSONObject assurance = new JSONObject();
    private final Validation valide = new Validation();
    private static final JSONObject compare = new JSONObject();
    private static final JSONObject temp1 = new JSONObject();
    private static final JSONObject temp2 = new JSONObject();
    private static final JSONObject temp3 = new JSONObject();
    private static final JSONArray temp4 = new JSONArray();
    private JSONArray reclamation;
    private String messageErreur;

    /**
     * Cette méthode permet d'aller chercher le message d'erreur qui est associé au fichier "Test.json".
     */
    public void obtenirMessageDErreur() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String output = gson.toJson(assurance);
        try {
            JSONHash.save(output);
        } catch (FileNotFoundException e) {
            System.err.println("Erreur anormale dans \"ValidationTest\".");
        }
        valide.estFichierValide("Test.json", "Remboursement.json");
        messageErreur = valide.getMessageErreur();
    }

    /**
     * Cree une copie de chacune des reclamations du fichier Assurance.json
     */
    public static void clonerReclamationsFichierAssuranceJson(){
        temp1.put("soin", 100);
        temp1.put("date", "2022-01-11");
        temp1.put("montant", "234.00$");
        temp2.put("soin", 200);
        temp2.put("date", "2022-01-13");
        temp2.put("montant", "90.00$");
        temp3.put("soin", 334);
        temp3.put("date", "2022-01-31");
        temp3.put("montant", "125.00$");
    }

    /**
     * Cree un JSONObject contenant les memes valeurs que le fichier Assurance.json
     */
    public static void creerFichierEquivalentAAssuranceJson() {
        compare.put("dossier", "A100323");
        compare.put("mois", "2022-01");
        temp4.add(temp1);
        temp4.add(temp2);
        temp4.add(temp3);
        compare.put("reclamations", temp4);
    }

    /**
     * Cree une instance de JSONObject contenu dans le fichier Assurance.json avant chaque test.
     */
    @BeforeEach
    public void creerNouvelleInstance() {
        new JSONHash("Assurance.json", "Test.json");
        try {
            assurance = (JSONObject) new JSONParser().parse(new FileReader("Assurance.json"));
        } catch (IOException e1) {
            System.err.println(MSG_ERR_FILE_NOT_FOUND);
            System.exit(-1);
        } catch (ParseException e2) {
            System.err.println(MSG_ERR_FILE_VIDE);
            System.exit(-2);
        }
        reclamation = (JSONArray) assurance.get("reclamations");
    }

    //////////////////////////////////////////////////
    /// Test la validité du fichier Assurance.json ///
    //////////////////////////////////////////////////
    /**
     * Compare ce que le fichier Assurance.json devrait contenir a ce qu'il contient reellement. S'il n'est pas egal,
     * les autres tests ne peuvent pas compiler.
     */
    @Test
    public void comparerAssuranceJsonACopieDuFichier() {
        clonerReclamationsFichierAssuranceJson();
        creerFichierEquivalentAAssuranceJson();
        Gson compareEnGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String formatVoulu = compareEnGson.toJson(compare);
        Gson assuranceEnGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String assuranceString = assuranceEnGson.toJson(assurance);
        if (!formatVoulu.equals(assuranceString)) {
            System.err.println(MSG_ERR_FILE_INVALIDE);
            System.exit(-3);
        }
    }

    /////////////////////////////
    /// Cas de fichier valide ///
    /////////////////////////////
    /**
     * Cette méthode permet de valider un fichier qui est conforme aux exigences et qui respecte le format voulu.
     */
    @Test
    public void verifierFichierValide() {
        obtenirMessageDErreur();
        Assertions.assertEquals("", messageErreur);
    }
    /////////////////////////////
    // Cas de fichier invalide //
    /////////////////////////////
    @Test
    public void verifierLaPresenceDesCles() {
        assurance.put("mois", null);
        obtenirMessageDErreur();
        Assertions.assertEquals("Le fichier d'entrée a une ou plusieurs entrées superflues.", messageErreur);
    }

    @Test
    public void verifierLettreContrat() {
        assurance.put("dossier", "R123456");
        obtenirMessageDErreur();
        Assertions.assertEquals("La lettre de la matricule du dossier n'est pas valide.", messageErreur);
    }

    @Test
    public void verifierNumeroDossier() {
        assurance.put("dossier", "AA12345");
        obtenirMessageDErreur();
        Assertions.assertEquals("Les caractères 2 à 7 doivent être des chiffres, " +
                "aucun autre caractère est accepté.", messageErreur);
    }

    @Test
    public void verifierLongueurMatriculeDossier() {
        assurance.put("dossier", "A1234567");
        obtenirMessageDErreur();
        Assertions.assertEquals("La matricule du dossier n'a pas la bonne longueur. Elle doit être de 7 " +
                "caractères, commençant par une lettre de contrat valide suivi de 6 chiffres", messageErreur);
    }

    @Test
    public void verifierFormatMois() {
        assurance.put("mois", "200-00");
        obtenirMessageDErreur();
        Assertions.assertEquals("La valeur de la clé \"mois\" doit avoir le format suivant \"0000-00\".",
                messageErreur);
    }

    @Test
    public void verifierTypeMois() {
        assurance.put("mois", "1899-01");
        obtenirMessageDErreur();
        Assertions.assertEquals("L'année de la clé \"mois\" doit contenir 4 chiffre et être supérieur à 1900.",
                messageErreur);
    }

    @Test
    public void verifierMoisValide() {
        assurance.put("mois", "2022-00");
        obtenirMessageDErreur();
        Assertions.assertEquals("Le mois de la clé \"mois\" doit être entre 0 et 12.", messageErreur);
    }

    @Test
    public void verifierReclamation() {
        reclamation.clear();
        obtenirMessageDErreur();
        Assertions.assertEquals("L'array \"reclamations\" est vide.", messageErreur);
    }

    @Test
    public void verifierReclamations2() {
        ((JSONObject) reclamation.get(1)).clear();
        obtenirMessageDErreur();
        Assertions.assertEquals("La réclamation 2 contient des champs invalides.", messageErreur);
    }



    @Test
    public void verifierLongueurDate() {
        ((JSONObject) reclamation.get(0)).put("date", "2022-12-100");
        obtenirMessageDErreur();
        Assertions.assertEquals("La longueur de la date dans la réclamation 1 est invalide.", messageErreur);
    }

    @Test
    public void verifierEgalitDateMois() {
        ((JSONObject) reclamation.get(0)).put("date", "2022-12-10");
        obtenirMessageDErreur();
        Assertions.assertEquals("Le champs mois et la date-mois de la réclamation 1 ne sont pas égaux.",
                messageErreur);
    }
    @Test
    public void verifierFormatDate() {
        ((JSONObject) reclamation.get(1)).put("date", "2022-01_10");
        obtenirMessageDErreur();
        Assertions.assertEquals("Le format de la date de la réclamation 2 n'est pas valide." +
        "(0000-00-00)", messageErreur);
    }
    @Test
    public void verifierTypeJour() {
        ((JSONObject) reclamation.get(2)).put("date", "2022-01-1p");
        obtenirMessageDErreur();
        Assertions.assertEquals("Le jour de la réclamation 3 n'est pas un nombre.", messageErreur);
    }
    @Test
    public void verifierNombreJours() {
        ((JSONObject) reclamation.get(0)).put("date", "2022-01-32");
        obtenirMessageDErreur();
        Assertions.assertEquals("Le nombre de jour entré dans la réclamation 1 est inférieur ou supérieur au " +
                "nombre maximal de jour du mois.", messageErreur);
    }
    @Test
    public void verifierTypeSoin() {
        ((JSONObject) reclamation.get(1)).put("soin", "17A");
        obtenirMessageDErreur();
        Assertions.assertEquals("Au moins une réclamation contient un numéro de soin contenant des " +
                "lettres/symboles.", messageErreur);
    }
    @Test
    public void verifierNumeroSoin() {
        ((JSONObject) reclamation.get(2)).put("soin", "174");
        obtenirMessageDErreur();
        Assertions.assertEquals("Le numéro de soin entré dans la réclamation 3 est un numéro de soin invalides."
                , messageErreur);

    }@Test
    public void verifierSigneDollar() {
        ((JSONObject) reclamation.get(2)).remove("montant");
        ((JSONObject) reclamation.get(2)).put("montant", "");
        obtenirMessageDErreur();
        Assertions.assertEquals("Le montant entré dans la réclamation 3 ne se termine pas par un \"$\".",
                messageErreur);
    }
    @Test
    public void verifierTypeMontant() {
        ((JSONObject) reclamation.get(0)).put("montant", "12S.00$");
        obtenirMessageDErreur();
        Assertions.assertEquals("Le montant entré de la réclamation 1 n'est pas un nombre.",
                messageErreur);
    }
    @Test
    public void verifierDeuxChiffresApresVirgule() {
        ((JSONObject) reclamation.get(1)).put("montant", "125.7$");
        obtenirMessageDErreur();
        Assertions.assertEquals("Le montant de la réclamation 2 n'a pas 2 chiffres après la virgule.",
                messageErreur);
    }
}
