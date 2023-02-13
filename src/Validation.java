/**
 * Cette classe sert à verifier la validite du fichier d'entree de l'utilisateur, donc de s'assurer que le fichier est
 * soit un fichier JSON que de respecter les demandes du client concernant les modalités à respecter.
 *
 * @author Gabriel Michaud (MICG93070107)
 * @version (12 fevrier 2023)
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Validation {
    private boolean valide;
    private JSONObject reclamationClient;
    private String messageErreur = "";

    /**
     * Cette methode permet de modifie le message d'erreur lorsque le programme detecte une erreur.
     * @param message le message d'erreur a renvoyer.
     */
    private void setMessageErreur(String message) {
        this.messageErreur = message;
    }

    /**
     * Cette methode permet d'acceder au message d'erreur lorsque le programme detecte une erreur.
     * @return le message d'erreur.
     */
    public String getMessageErreur() {
        return messageErreur;
    }

    /**
     * Cette methode permet de changer la valeur de l'attribut valide, qu'on utilise pour valider les etapes de
     * la validation.
     * @param valide la nouvelle valeur que l'on veut atribuer a valide.
     */
    private void setValide(boolean valide) {
        this.valide = valide;
    }

    /**
     * Cette methode permet de placer dans une variable de classe l'objet JSON se trouvant de le fichier d'entree.
     * @param obj l'objet JSON se trouvant de le fichier d'entree.
     */
    private void setReclamationClient(JSONObject obj) {
        this.reclamationClient = obj;
    }

    /**
     * Cette methode permet de s'assurer que le fichier d'entree est un fichier ".json".
     * @return un boolean validant ou non que le fichier est un fichier ".json".
     */
    private boolean estFichierJson(String nomFichierEntree, String nomFichierSortie) {
        JSONHash test = new JSONHash(nomFichierEntree, nomFichierSortie);
        setValide(test.getFilename().endsWith(".json") && test.getResultat().endsWith(".json"));
        if(!valide)
            setMessageErreur("N'est pas un fichier JSON");
        return valide;
    }

    /**
     * Cette methode permet de recuperer le fichier ".json" donne en entree.
     */
    private void obtenirObjetJSON(String nomFichierEntree, String nomFichierSortie) {
        if(estFichierJson(nomFichierEntree, nomFichierSortie)){
            JSONHash json = new JSONHash(nomFichierEntree, nomFichierSortie);
            try {
                json.load();
                setReclamationClient(json.getJsonobj());
            }catch (IOException | ParseException e) {
                setMessageErreur("Fichier inexistant");
            }
        }
    }

    /**
     * Cette methode permet de verifier que le fichier d'entree contient les cles de "client", "contrat",
     * "mois" et "reclamation" et aucunes autres clés.
     */

    private void validerLaPresenceDesCles() {
        try {
            setValide(reclamationClient.get("client") != null && reclamationClient.get("contrat") != null
                    && reclamationClient.get("mois") != null && reclamationClient.get("reclamations") != null
                    && reclamationClient.size() == 4);
        } catch (NullPointerException e) {
            setMessageErreur("Fichier invalide");
            setValide(false);
        }
    }

    /**
     * Cette methode permet de verifier la longueur du numero du client.
     */
    private void verifierLongueurNumeroClient(){
        String numeroClient;
        if (valide){
            numeroClient = String.valueOf(reclamationClient.get("client"));
            if(numeroClient.length() != 6) {
                setMessageErreur("Numero Client invalide");
                setValide(false);
            }
        }
    }

    /**
     * Cette methode permet de valider le numero du client.
     */
    private void estNumeroCLientValide () {
        if(valide) {
            try {
                Integer.parseInt(String.valueOf(reclamationClient.get("client")));
            } catch (NumberFormatException e){
                setMessageErreur("Numero client invalide");
                setValide(false);
            }
        }
    }

    /**
     * Cette methode permet de valider le type de contrat du client.
     */
    private void estLettreContratValide() {
        if(valide) {
            String contrat = String.valueOf(reclamationClient.get("contrat"));
            if (contrat.length() != 1 || contrat.charAt(0) < 'A' || contrat.charAt(0) > 'D') {
                setMessageErreur("Contrat invalide");
                setValide(false);
            }
        }
    }

    /**
     * Cette methode permet de valider que le champs "mois" dans le fichier d'entree respecte le modele demande.
     */
    private void estFormatMoisValide() {
        if (valide) {
            String mois = String.valueOf(reclamationClient.get("mois"));
            try {
                if (mois.charAt(4) != '-' || mois.length() != 7) {
                    setValide(false);
                }
            } catch (IndexOutOfBoundsException e) {
                setValide(false);
            }
            if (!valide)
                setMessageErreur("Format mois invalide");
        }
    }
    /**
     * Cette methode permet de valider que la cle "mois" dans le fichier d'entree est composede 4 chiffres pour
     * l'annee et de 2 chiffres pour le mois.
     */
    private void validerTypeMois() {
        if(valide) {
            String mois = String.valueOf(reclamationClient.get("mois"));
            try {
                Long.valueOf(mois.substring(0, 4));
                Long.valueOf(mois.substring(5));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                setValide(false);
            }
            if (!valide || Long.parseLong(mois.substring(0, 4)) < 0 || Long.parseLong(mois.substring(0, 4)) < 1900)
                setMessageErreur("Annee invalide");
        }
    }

    /**
     * Cette methode permet de verifier que le nombre associe au mois dans la cle "mois" est un nombre de mois valide.
     */
    private void estMoisEtAnneeValide() {
        if (valide) {
            String mois = String.valueOf(reclamationClient.get("mois"));
            long moisVerif = Long.parseLong(mois.substring(5));
            if (moisVerif <= 0 || moisVerif > 12) {
                setMessageErreur("Mois invalide");
                setValide(false);
            }
        }
    }

    /**
     * Cette method sert a recuperer le JSONArray "reclamations" qui contient toutes les informations concernant les
     * soins, les montants depenses et les dates que les soins ont ete donnes.
     * @return reclamations qui est le JSONArray contenant les informations.
     */
    private JSONArray obtenirReclamations() {
        JSONArray reclamations = new JSONArray();
        if (valide) {
            reclamations = (JSONArray) reclamationClient.get("reclamations");
            if (reclamations.isEmpty()) {
                setMessageErreur("Objet \"reclamations\" vide");
                setValide(false);
            }
        }
        return reclamations;
    }

    /**
     * Cette methode permet de s'sssurer que l'array recalamtion contient les cles : "soin", "date" et "montant" et
     * aucunes autres cles.
     *
     * @param obj objet JSON se trouvant dans le fichier d'entree.
     */
    private void validerLaPresenceDesClesRecalmations(JSONArray obj) {
        if(valide) {
            int position = 0 ;
            while ( valide && position < obj.size() ){
                JSONObject objet = (JSONObject) obj.get(position);
                setValide(objet.get("soin") != null && objet.get("date") != null && objet.get("montant") != null
                        && objet.size() == 3);
                position++;
            }
            if(!valide)
                setMessageErreur("Reclamation(s) invalide(s)");
        }
    }

    /**
     * Cette methode permet de retourner un tableau de type String contenant les valeurs specifiees par le @param cle.
     * @param cle la cle permettant d'accéder aux valeurs dans "reclamations" ("soin", "date" et "montant")
     * @return tabVal, qui est un tableau de type String.
     */
    private String[] obtenirValeur(String cle) {
        String[] tabVal = null;
        if (valide) {
            tabVal = new String[obtenirReclamations().size()];

            for (int i = 0; i < obtenirReclamations().size(); i++) {
                JSONObject temporaire = (JSONObject) obtenirReclamations().get(i);
                tabVal[i] = String.valueOf(temporaire.get(cle));
            }
        }
        return tabVal;
    }
    private void verifieLongueurDate() {
        int position = 0;
        while (valide && position < obtenirValeur("mois").length) {
            setValide(obtenirValeur("date")[position].length() == 10);
            position++;
        }
        if (!valide) {
            setMessageErreur("Format date invalide");
            setValide(false);
        }
    }

    /**
     * Cette methode permet de verifier l'egalite entre la valeur associe a la cle "mois" et celles associees a la
     * cle "date" dans le JSONArray "reclamations".
     */
    private void verifierEgaliteMoisDate() {
        if (valide) {
            int position = 0;
            while (valide && position < obtenirValeur("mois").length) {
                setValide(reclamationClient.get("mois").equals(obtenirValeur("date")[position].substring(0, 7)));
                position++;
            }
            if (!valide)
                setMessageErreur("Mois et date pas equivalent");
        }
    }

    /**
     * Cette methode permet de verifier que les dates se trouvant dans le JSONArray "reclamations" contiennent les 2 '-'
     * et ont la bonne longueur.
     */
    private void validerFormatDate () {
        if (valide){
            int position = 0;
            while(valide && position < obtenirValeur("date").length ) {
                setValide(obtenirValeur("date")[position].length() == 10
                        && obtenirValeur("date")[position].charAt(7) == '-');
                if(!valide)
                    setMessageErreur("Format date invalide");
                position++;
            }
        }
    }

    /**
     * Cette methode permet de valider que le nombre de jours des cles "date" dans le JSONArray "reclamations" sont de
     * type long.
     */
    private void validerTypeJours() {
        if(valide) {
            try {
                for (int i = 0; i < obtenirValeur("soin").length; i++) {
                    Long.parseLong(obtenirValeur("date")[i].substring(8));
                }
            } catch (NumberFormatException e2) {
                setMessageErreur("Donnees invalides");
                setValide(false);
            }
        }
    }

    /**
     * Cette methode permet de valider que le nombre de jour, correspondant aux 2 derniers characteres des valeurs
     * entrees aux cles "date" dans le JSONArray "reclamations", est inferieur ou egal au nombre de jours maximal du
     * mois donne et superieur a 0.
     */
    private void estNombreJoursValide() {
        if(valide) {
            int position = 0;
            //va chercher l'annee et le mois de la cle "mois" et les met en parametre pour obtenir le nombre de jours.
            int nbrJours = obtenirNombreJours(Long.parseLong(((String) reclamationClient.get("mois")).substring(0, 4)),
                    Long.parseLong(((String) reclamationClient.get("mois")).substring(5)) );
            while (valide && position < obtenirValeur("date").length ) {
                //compare avec les deux derniers chiffres des dates.
                setValide(nbrJours >= Long.parseLong(obtenirValeur("date")[position].substring(8)) &&
                        Long.parseLong(obtenirValeur("date")[position].substring(8)) > 0);
                if (!valide)
                    setMessageErreur("Jour invalide");
                position++;
            }
        }
    }

    /**
     * Cette methode permet de determiner le nombre de jours selon le mois et et l'annee donnes en parametre.
     * @param annee pour verifier si l'annee est bissextile.
     * @param mois pour savoir le nombre de jours.
     * @return le nombre de jours du mois et de l'annee donnes.
     */
    private int obtenirNombreJours (long annee, long mois) {
        int nombreJours = 28;
        if( mois == 1 || mois == 3 || mois == 5 || mois == 7 || mois == 8 || mois == 10 || mois == 12 )
            nombreJours = 31;
        else if ( mois == 4 || mois == 6 || mois == 9 || mois == 11 )
            nombreJours = 30;
        else {
            if (estBissextile(annee))
                nombreJours = 29;
        }
        return nombreJours;
    }

    /**
     * Cette methode verifie si une annee donnee est bissextile.
     * @param annee l'annee a verifie
     * @return un booleen qui dit si l'annee est bissextile.
     */
    private boolean estBissextile(long annee) {
        boolean bissextile = annee % 400 == 0 || annee % 4 == 0;
        if (annee % 100 == 0 && annee % 400 != 0)
            bissextile = false;
        return bissextile ;
    }

    /**
     * Cette methode verifie si les donnees liees aux cles "soin" dans le JSONArray "reclamations" sont de type long.
     * @return un tableau de type long.
     */
    private long[] verifierTypeDesSoins() {
        long [] numSoin = new long[0];
        if (valide) {
            numSoin = new long[obtenirValeur("soin").length];
            try {
                for (int i = 0; i < obtenirValeur("soin").length; i++) {
                    numSoin[i] = Long.parseLong(obtenirValeur("soin")[i]);
                }
            } catch (NumberFormatException e2) {
                setMessageErreur("Donnees invalides");
                setValide(false);
            }
        }
        return numSoin;
    }

    /**
     * Cette methode permet de verifier la validite des types de soins de l'objet JSON donne dans le fichier d'entree.
     */
    private void estNumeroSoinValide() {
        if (valide && verifierTypeDesSoins() != null) {
            for (long i : verifierTypeDesSoins()) {
                if (!(i >= 300 && i <= 400) && i != 100 && i != 200 && i != 500 && i != 600 && i != 700) {
                    setValide(false);
                    setMessageErreur("Donnees invalides");
                }
            }
        }
    }

    /**
     * Cette methode permet de valider que les valeurs associees aux cle "montant" dans le JSONArray  "reclamations"
     * se terminent par des signes de dollar ( "$" ).
     */
    public void validerSigneDollar() {
        String [] montant = obtenirValeur("montant");
        int position = 0;
        while(valide && position < montant.length) {
            if (!montant[position].endsWith("$")) {
                setMessageErreur("Montant invalide");
                setValide(false);
            }
            position++;
        }
    }

    /**
     * Cette methode permet de retirer les signes de dollar des valeurs des cles "montant", de verifier que les
     * valeurs ne sont pas negatives et de retourner les valeurs modifiees dans un tableau de type String.
     * @return un tableau de type String contenanat les valeurs des cles montant modifiees.
     */
    private String [] formaterMontant() {
        String[] montant = new String[0];
        if (valide) {
            montant = obtenirValeur("montant");
            for (int i = 0; i < montant.length; i++) {
                montant[i] = montant[i].substring(0, montant[i].lastIndexOf("$"));
                if(montant[i].charAt(0) == '-' ) {
                    setMessageErreur("Montant invalide");
                    setValide(false);
                }
            }
        }
        return montant;
    }

    /**
     * Cette methode permet de verifier que les montants entres sont de type Float.
     */
    private void validerTypeMontant() {
        if (valide && formaterMontant() != null) {
            int position = 0;
            while (valide && position < formaterMontant().length) {
                try {
                    Float.parseFloat(formaterMontant()[position]);
                } catch (NumberFormatException e) {
                    setMessageErreur("Montant invalide");
                    setValide(false);
                }
                position++;
            }
        }
    }

    /**
     * Cette methode permet de valider que les montants entres ont deux chiffres décimaux.
     * */
    private void validerNombreDecimal() {
        if (valide) {
            String [] montant = obtenirValeur("montant");
            for (int i = 0 ; valide && i < montant.length ; i++) {
                try {
                    setValide(montant[i].contains(".") && montant[i].charAt(montant[i].indexOf('.') + 3) == '$');
                } catch(IndexOutOfBoundsException e) {
                    setValide(false);
                }
            }
            if (!valide)
                setMessageErreur("Montant invalide");
        }
    }

    /**
     * Cette methode permet de valider le numero du client.
     */
    public void estClientValide() {
        validerLaPresenceDesCles();
        if (valide) {
            verifierLongueurNumeroClient();
            if (valide)
                estNumeroCLientValide();
        }
    }

    /**
     * Cette methode permet de valider le mois.
     */
    public void estMoisValide() {
        if (valide) {
            estFormatMoisValide();
            if (valide) {
                validerTypeMois();
                if (valide) {
                    estMoisEtAnneeValide();
                }
            }
        }
    }

    /**
     * Cette methode permet de valider les soins.
     */
    public void estSoinValide() {
        if (valide) {
            validerLaPresenceDesClesRecalmations(obtenirReclamations());
            if (valide) {
                verifierTypeDesSoins();
                if (valide)
                    estNumeroSoinValide();
            }

        }
    }

    /**
     * Cette methode permet de valider les dates.
     */
    public void estDateValide() {
        if (valide) {
            verifieLongueurDate();
            if (valide) {
                verifierEgaliteMoisDate();
                if (valide) {
                    validerFormatDate();
                    if (valide) {
                        validerTypeJours();
                        if (valide)
                            estNombreJoursValide();
                    }
                }
            }
        }

    }

    /**
     * Cette methode permet de valider les montants.
     */
    public void estMontantValide() {
        if (valide) {
            validerSigneDollar();
            if (valide) {
                validerTypeMontant();
                if (valide)
                    validerNombreDecimal();
            }
        }
    }


    /**
     * Cette methode permet de valider le fichier d'entree en faisant appel aux methodes de validations. On a pas
     * besoin d'appeler d'autres methode que celle-ci pour valider le fichier. Si la methode ne valide pas le fichier,
     * aucun calcul ne doit etre fait avec le fichier.
     * @return un booleen validant ou non le fichier d'entree.
     */
    public boolean estFichierValide(String nomFichierEntree, String nomFichierSortie) {
        obtenirObjetJSON(nomFichierEntree, nomFichierSortie);
        estClientValide();
        estLettreContratValide();
        estMoisValide();
        estSoinValide();
        estDateValide();
        estMontantValide();
        return valide;
    }
}
