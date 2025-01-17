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
    private final String MSG_ERR_NBR_JOURS_MOIS1 = "Le nombre de jour entré dans la réclamation ";
    private final String MSG_ERR_NBR_JOURS_MOIS2 = " est inférieur ou supérieur au nombre maximal de jour du mois.";
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
     * Cette methode permet de placer dans une variable de classe l'objet JSON se trouvant dans le fichier d'entree.
     * @param obj l'objet JSON se trouvant dans le fichier d'entree.
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
            setMessageErreur("Les deux, ou un des fichier n'est pas un fichier ayant l'extension \".json\".");
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
                setMessageErreur("Le fichier d'entrée est inexistant, ou introuvable.");
            }
        }
    }

    /**
     * Cette methode permet de verifier que le fichier d'entree contient les cles de "dossier","mois" et "reclamation"
     * et aucunes autres clés.
     */

    private void validerLaPresenceDesCles() {
        try {
            setValide(reclamationClient.get("dossier") != null && reclamationClient.get("mois") != null
                    && reclamationClient.get("reclamations") != null && reclamationClient.size() == 3);
        } catch (NullPointerException e) {
            setValide(false);
        }
        if (!valide)
            setMessageErreur("Le fichier d'entrée a une ou plusieurs entrées superflues.");
    }
    /**
     * Cette methode permet de valider le type de contrat du client.
     */
    private void estLettreContratValide() {
        if(valide) {
            String dossier = String.valueOf(reclamationClient.get("dossier"));
            if ( dossier.charAt(0) < 'A' || dossier.charAt(0) > 'E') {
                setMessageErreur("La lettre de la matricule du dossier n'est pas valide.");
                setValide(false);
            }
        }
    }

    /**
     * Cette methode permet de verifier la longueur de la matricule du dossier du client.
     */
    private void verifierLongueurDossier(){
        String matriculeDossier;
        if (valide){
            matriculeDossier = String.valueOf(reclamationClient.get("dossier"));
            if(matriculeDossier.length() != 7) {
                setMessageErreur("La matricule du dossier n'a pas la bonne longueur. Elle doit être de 7 caractères, " +
                        "commençant par une lettre de contrat valide suivi de 6 chiffres");
                setValide(false);
            }
        }
    }

    /**
     * Cette methode permet de valider le numero de la matricule du client.
     */
    private void estMatriculeDossierValide() {
        if(valide) {
            try {
                Integer.parseInt(String.valueOf(reclamationClient.get("dossier")).substring(1));
            } catch (NumberFormatException e){
                setMessageErreur("Les caractères 2 à 7 doivent être des chiffres, aucun autre caractère est accepté.");
                setValide(false);
            }
        }
    }

    /**
     * Cette methode permet de valider que le champ "mois" dans le fichier d'entree respecte le modele demande.
     */
    private void estFormatMoisValide() {
        if (valide) {
            String mois = String.valueOf(reclamationClient.get("mois"));
            try {
                if (mois.charAt(4) != '-' || mois.length() != 7)
                    setValide(false);
            } catch (IndexOutOfBoundsException e) {
                setValide(false);
            }
            if (!valide)
                setMessageErreur("La valeur de la clé \"mois\" doit avoir le format suivant \"0000-00\".");
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
            if (!valide || Long.parseLong(mois.substring(0, 4)) < 0 || Long.parseLong(mois.substring(0, 4)) < 1900) {
                setMessageErreur("L'année de la clé \"mois\" doit contenir 4 chiffre et être supérieur à 1900.");
                setValide(false);
            }
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
                setMessageErreur("Le mois de la clé \"mois\" doit être entre 0 et 12.");
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
                setMessageErreur("L'array \"reclamations\" est vide.");
                setValide(false);
            }
        }
        return reclamations;
    }

    /**
     * Cette methode permet de s'assurer que l'array recalamtion contient les cles : "soin", "date" et "montant" et
     * aucunes autres cles.
     *
     * @param obj objet JSON se trouvant dans le fichier d'entree.
     */
    private void validerLaPresenceDesClesReclamations(JSONArray obj) {
        if(valide) {
            int position = 0 ;
            while ( valide && position < obj.size() ){
                JSONObject objet = (JSONObject) obj.get(position);
                setValide(objet.get("soin") != null && objet.get("date") != null && objet.get("montant") != null
                        && objet.size() == 3);
                position++;
                if(!valide)
                    setMessageErreur("La réclamation " + position + " contient des champs invalides.");
            }
        }
    }

    /**
     * Cette méthode permet à envoyer le bon message d'erreur lorsqu'il y a des champs invalides ou manquants dans
     * l'array réclamations.
     * @param reclamations l'array contenant les réclamations du client.
     */

    private void estReclamationInvalide(JSONArray reclamations){
        if (!valide) {
            for (Object o : reclamations) {
                JSONObject objet = (JSONObject) o;
                int index = reclamations.indexOf(o);
                if (objet.get("soin") == null)
                    setMessageErreur("Dans la réclamation " + index + ", le champs soin est invalide.");
                else if (objet.get("date") == null)
                    setMessageErreur("Dans la réclamation " + index + ", le champs date est invalide.");
                else if(objet.get("montant") == null)
                    setMessageErreur("Dans la réclamation " + index + ", le champs montant est invalide.");
            }
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

    /**
     * Cette méthode permet de valider la longueur des dates des réclamations.
     */
    private void verifieLongueurDate() {
        int position = 0;
        while (valide && position < obtenirValeur("mois").length) {
            setValide(obtenirValeur("date")[position].length() == 10);
            position++;
        }
        if (!valide) {
            setMessageErreur("La longueur de la date dans la réclamation " + position + " est invalide.");
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
                setMessageErreur("Le champs mois et la date-mois de la réclamation " + position + " ne sont pas égaux.");
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
                position++;
                if(!valide)
                    setMessageErreur("Le format de la date de la réclamation " + position + " n'est pas valide." +
                            "(0000-00-00)");
            }
        }
    }

    /**
     * Cette methode permet de valider que le nombre de jours des cles "date" dans le JSONArray "reclamations" sont de
     * type long.
     */
    private void validerTypeJours() {
        if(valide) {
            int position = 0;
            try {
                while (position < obtenirValeur("soin").length) {
                    Long.parseLong(obtenirValeur("date")[position].substring(8));
                    position++;
                }
            } catch (NumberFormatException e2) {
                setMessageErreur("Le jour de la réclamation " + ++position + " n'est pas un nombre.");
                setValide(false);
            }
        }
    }

    /**
     * Cette methode permet de valider que le nombre de jours, correspondant aux 2 derniers characteres des valeurs
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
                position++;
                if (!valide)
                    setMessageErreur(MSG_ERR_NBR_JOURS_MOIS1 + position + MSG_ERR_NBR_JOURS_MOIS2);
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
                setMessageErreur("Au moins une réclamation contient un numéro de soin contenant des lettres/symboles.");
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
            int position = 0;
            for (long i : verifierTypeDesSoins()) {
                if (!(i >= 300 && i <= 400) && i != 100 && i != 150 && i != 175 && i != 200 && i != 500 && i != 600
                        && i != 700) {
                    setValide(false);
                    setMessageErreur("Le numéro de soin entré dans la réclamation " + ++position +
                            " est un numéro de soin invalides.");
                }
                position++;
            }
        }
    }

    /**
     * Cette methode permet de valider que les valeurs associees aux cle "montant" dans le JSONArray "reclamations"
     * se terminent par des signes de dollar ( "$" ).
     */
    private void validerSigneDollar() {
        String [] montant = obtenirValeur("montant");
        int position = 0;
        while(valide && position < montant.length) {
            if (!montant[position].endsWith("$")) {
                setMessageErreur("Le montant entré dans la réclamation " + ++position +
                        " ne se termine pas par un \"$\".");
                setValide(false);
            }
            position++;
        }
    }

    /**
     * Cette methode permet de retirer les signes de dollar des valeurs des cles "montant", de verifier que les
     * valeurs ne sont pas negatives et de retourner les valeurs modifiees dans un tableau de type String.
     * @return un tableau de type String contenant les valeurs des cles montant modifiees.
     */
    private String [] formaterMontant() {
        String[] montant = new String[0];
        if (valide) {
            montant = obtenirValeur("montant");
            for (int i = 0; i < montant.length; i++) {
                montant[i] = montant[i].substring(0, montant[i].lastIndexOf("$"));
                if(montant[i].charAt(0) == '-' ) {
                    setMessageErreur("Le montant entré dans la réclamation " + ++i +" est négatif.");
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
                    setMessageErreur("Le montant entré de la réclamation " + ++position + " n'est pas un nombre.");
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
                    setMessageErreur("Le montant de la réclamation " + ++i + " n'a pas 2 chiffres après la virgule.");
                }
            }
        }
    }

    /**
     * Cette methode permet de valider la matricule du dossier du client.
     */
    private void estDossierValide() {
        validerLaPresenceDesCles();
        if (valide) {
            verifierLongueurDossier();
            if (valide) {
                estLettreContratValide();
                if (valide)
                    estMatriculeDossierValide();
            }

        }
    }

    /**
     * Cette methode permet de valider le mois.
     */
    private void estMoisValide() {
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
    private void estSoinValide() {
        if (valide) {
            validerLaPresenceDesClesReclamations(obtenirReclamations());
                if(!valide) {
                    estReclamationInvalide(obtenirReclamations());
                } else {
                verifierTypeDesSoins();
                    if (valide)
                        estNumeroSoinValide();
            }

        }
    }

    /**
     * Cette methode permet de valider les dates.
     */
    private void estDateValide() {
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
    private void estMontantValide() {
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
        estDossierValide();
        estMoisValide();
        estSoinValide();
        estDateValide();
        estMontantValide();
        return valide;
    }
}
