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
    JSONObject assurance = new JSONObject();
    Validation valide = new Validation();
    JSONArray reclamation;
    String messageErreur;

    @BeforeEach
    public void creerNouvelleInstance() throws IOException, ParseException {
        new JSONHash("Assurance.json", "Test.json");
        assurance = (JSONObject) new JSONParser().parse(new FileReader("Assurance.json"));
        reclamation = (JSONArray) assurance.get("reclamations");
    }

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

    @Test
    public void verifierFichierValide() {
        obtenirMessageDErreur();
        Assertions.assertEquals("", messageErreur);
    }

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
