import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Validation {
    private boolean valide;
    private JSONObject reclamationClient;
    private String messageErreur;

    public Validation() {
    }

    private void setMessageErreur(String message) {
        this.messageErreur = message;
    }

    public String getMessageErreur() {
        return this.messageErreur;
    }

    private void setValide(boolean valide) {
        this.valide = valide;
    }

    private void setReclamationClient(JSONObject obj) {
        this.reclamationClient = obj;
    }

    private boolean estFichierJson(String nomFichierEntree, String nomFichierSortie) {
        JSONHash test = new JSONHash(nomFichierEntree, nomFichierSortie);
        this.setValide(test.getFilename().endsWith(".json") && test.getResultat().endsWith(".json"));
        if (!this.valide) {
            this.setMessageErreur("N'est pas un fichier JSON");
        }

        return this.valide;
    }

    private void obtenirObjetJSON(String nomFichierEntree, String nomFichierSortie) {
        if (this.estFichierJson(nomFichierEntree, nomFichierSortie)) {
            JSONHash json = new JSONHash(nomFichierEntree, nomFichierSortie);

            try {
                json.load();
                this.setReclamationClient(json.getJsonobj());
            } catch (ParseException | IOException var5) {
                this.setMessageErreur("Fichier inexistant");
            }
        }

    }

    private boolean validerLaPresenceDesCles() {
        this.setValide(this.reclamationClient.get("client") != null && this.reclamationClient.get("contrat") != null && this.reclamationClient.get("mois") != null && this.reclamationClient.get("reclamations") != null && this.reclamationClient.size() == 4);
        return this.valide;
    }

    private boolean verifierLongueurNumeroClient() {
        if (this.validerLaPresenceDesCles()) {
            String numeroClient = String.valueOf(this.reclamationClient.get("client"));
            if (numeroClient.length() != 6) {
                this.setMessageErreur("Numero Client invalide");
                this.setValide(false);
            }
        }

        return this.valide;
    }

    private void estNumeroCLientValide() {
        if (this.verifierLongueurNumeroClient()) {
            try {
                Integer.parseInt(String.valueOf(this.reclamationClient.get("client")));
            } catch (NumberFormatException var2) {
                this.setMessageErreur("Numero client invalide");
                this.setValide(false);
            }
        }

    }

    private void estContratValide() {
        if (this.validerLaPresenceDesCles()) {
            String contrat = String.valueOf(this.reclamationClient.get("contrat"));
            if (contrat.length() != 1 || contrat.charAt(0) < 'A' || contrat.charAt(0) > 'D') {
                this.setMessageErreur("Contrat invalide");
                this.setValide(false);
            }
        }

    }

    private boolean estFormatMoisValide() {
        if (this.valide) {
            String mois = String.valueOf(this.reclamationClient.get("mois"));

            try {
                if (mois.charAt(4) != '-' || mois.length() != 7) {
                    this.setValide(false);
                }
            } catch (IndexOutOfBoundsException var3) {
                this.setValide(false);
            }

            if (!this.valide) {
                this.setMessageErreur("Format mois invalide");
            }
        }

        return this.valide;
    }

    private boolean validerTypeMois() {
        if (this.estFormatMoisValide()) {
            String mois = String.valueOf(this.reclamationClient.get("mois"));

            try {
                Long.valueOf(mois.substring(0, 4));
                Long.valueOf(mois.substring(5));
            } catch (IndexOutOfBoundsException | NumberFormatException var3) {
                this.setValide(false);
            }

            if (!this.valide || Long.parseLong(mois.substring(0, 4)) < 0L || Long.parseLong(mois.substring(0, 4)) < 1970L) {
                this.setMessageErreur("Annee invalide");
            }
        }

        return this.valide;
    }

    private boolean estMoisValide() {
        if (this.validerTypeMois()) {
            String mois = String.valueOf(this.reclamationClient.get("mois"));
            long moisVerif = Long.parseLong(mois.substring(5));
            if (moisVerif <= 0L || moisVerif > 12L) {
                this.setMessageErreur("Mois invalide");
                this.setValide(false);
            }
        }

        return this.valide;
    }

    private JSONArray obtenirReclamations() {
        JSONArray reclamations = null;
        if (this.valide) {
            reclamations = (JSONArray)this.reclamationClient.get("reclamations");
            if (reclamations.isEmpty()) {
                this.setMessageErreur("Objet \"reclamations\" vide");
                this.setValide(false);
            }
        }

        return reclamations;
    }

    private boolean validerLaPresenceDesClesRecalmations(JSONArray obj) {
        if (this.valide) {
            for(int position = 0; this.valide && position < obj.size(); ++position) {
                JSONObject objet = (JSONObject)obj.get(position);
                this.setValide(objet.get("soin") != null && objet.get("date") != null && objet.get("montant") != null && objet.size() == 3);
            }

            if (!this.valide) {
                this.setMessageErreur("Reclamation(s) invalide(s)");
            }
        }

        return this.valide;
    }

    private String[] obtenirValeur(String cle) {
        String[] tabVal = null;
        if (this.validerLaPresenceDesClesRecalmations(this.obtenirReclamations())) {
            tabVal = new String[this.obtenirReclamations().size()];

            for(int i = 0; i < this.obtenirReclamations().size(); ++i) {
                JSONObject temporaire = (JSONObject)this.obtenirReclamations().get(i);
                tabVal[i] = String.valueOf(temporaire.get(cle));
            }
        }

        return tabVal;
    }

    private boolean verifieLongueurDate() {
        for(int position = 0; this.valide && position < this.obtenirValeur("mois").length; ++position) {
            this.setValide(this.obtenirValeur("date")[position].length() == 10);
        }

        if (!this.valide) {
            this.setMessageErreur("Format date invalide");
            this.setValide(false);
        }

        return this.valide;
    }

    private boolean verifierEgaliteMoisDate() {
        if (this.estMoisValide() && this.verifieLongueurDate()) {
            for(int position = 0; this.valide && position < this.obtenirValeur("mois").length; ++position) {
                this.setValide(this.reclamationClient.get("mois").equals(this.obtenirValeur("date")[position].substring(0, 7)));
            }

            if (!this.valide) {
                this.setMessageErreur("Mois et date pas equivalent");
            }
        }

        return this.valide;
    }

    private boolean validerFormatDate() {
        if (this.verifierEgaliteMoisDate()) {
            for(int position = 0; this.valide && position < this.obtenirValeur("date").length; ++position) {
                this.setValide(this.obtenirValeur("date")[position].length() == 10 && this.obtenirValeur("date")[position].charAt(7) == '-');
                if (!this.valide) {
                    this.setMessageErreur("Format date invalide");
                }
            }
        }

        return this.valide;
    }

    private boolean validerTypeJours() {
        if (this.validerFormatDate()) {
            try {
                for(int i = 0; i < this.obtenirValeur("soin").length; ++i) {
                    Long.parseLong(this.obtenirValeur("date")[i].substring(8));
                }
            } catch (NumberFormatException var2) {
                this.setMessageErreur("Donnees invalides");
                this.setValide(false);
            }
        }

        return this.valide;
    }

    private void estDateValide() {
        if (this.validerTypeJours()) {
            int position = 0;

            for(int nbrJours = this.obtenirNombreJours(Long.parseLong(((String)this.reclamationClient.get("mois")).substring(0, 4)), Long.parseLong(((String)this.reclamationClient.get("mois")).substring(5))); this.valide && position < this.obtenirValeur("date").length; ++position) {
                this.setValide((long)nbrJours >= Long.parseLong(this.obtenirValeur("date")[position].substring(8)) && Long.parseLong(this.obtenirValeur("date")[position].substring(8)) > 0L);
                if (!this.valide) {
                    this.setMessageErreur("Jour invalide");
                }
            }
        }

    }

    private int obtenirNombreJours(long annee, long mois) {
        int nombreJours = 28;
        if (mois != 1L && mois != 3L && mois != 5L && mois != 7L && mois != 8L && mois != 10L && mois != 12L) {
            if (mois != 4L && mois != 6L && mois != 9L && mois != 11L) {
                if (this.estBissextile(annee)) {
                    nombreJours = 29;
                }
            } else {
                nombreJours = 30;
            }
        } else {
            nombreJours = 31;
        }

        return nombreJours;
    }

    private boolean estBissextile(long annee) {
        boolean bissextile = annee % 400L == 0L || annee % 4L == 0L;
        if (annee % 100L == 0L && annee % 400L != 0L) {
            bissextile = false;
        }

        return bissextile;
    }

    private long[] verifierTypeDesSoins() {
        long[] numSoin = new long[0];
        if (this.obtenirValeur("soin") != null) {
            numSoin = new long[this.obtenirValeur("soin").length];

            try {
                for(int i = 0; i < this.obtenirValeur("soin").length; ++i) {
                    numSoin[i] = Long.parseLong(this.obtenirValeur("soin")[i]);
                }
            } catch (NumberFormatException var3) {
                this.setMessageErreur("Donnees invalides");
                this.setValide(false);
            }
        }

        return numSoin;
    }

    private void estSoinValide() {
        if (this.valide && this.verifierTypeDesSoins() != null) {
            long[] var1 = this.verifierTypeDesSoins();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                long i = var1[var3];
                if ((i < 300L || i > 400L) && i != 100L && i != 200L && i != 500L && i != 600L && i != 700L) {
                    this.setValide(false);
                }
            }
        }

    }

    public boolean validerSigneDollar() {
        String[] montant = this.obtenirValeur("montant");

        for(int position = 0; this.valide && position < montant.length; ++position) {
            if (!montant[position].endsWith("$")) {
                this.setMessageErreur("Montant invalide");
                this.setValide(false);
            }
        }

        return this.valide;
    }

    private String[] formaterMontant() {
        String[] montant = new String[0];
        if (this.validerSigneDollar()) {
            montant = this.obtenirValeur("montant");

            for(int i = 0; i < montant.length; ++i) {
                montant[i] = montant[i].substring(0, montant[i].lastIndexOf("$"));
                if (montant[i].charAt(0) == '-') {
                    this.setMessageErreur("Montant invalide");
                    this.setValide(false);
                }
            }
        }

        return montant;
    }

    private boolean validerTypeMontant() {
        if (this.valide && this.formaterMontant() != null) {
            for(int position = 0; this.valide && position < this.formaterMontant().length; ++position) {
                try {
                    Float.parseFloat(this.formaterMontant()[position]);
                } catch (NumberFormatException var3) {
                    this.setMessageErreur("Montant invalide");
                    this.setValide(false);
                }
            }
        }

        return this.valide;
    }

    private void estMontantValide() {
        if (this.validerTypeMontant()) {
            String[] montant = this.obtenirValeur("montant");

            for(int i = 0; this.valide && i < montant.length; ++i) {
                try {
                    this.setValide(montant[i].contains(".") && montant[i].charAt(montant[i].indexOf(46) + 3) == '$');
                } catch (IndexOutOfBoundsException var4) {
                    this.setValide(false);
                }
            }

            if (!this.valide) {
                this.setMessageErreur("Montant invalide");
            }
        }

    }

    public boolean estFichierValide(String nomFichierEntree, String nomFichierSortie) {
        this.obtenirObjetJSON(nomFichierEntree, nomFichierSortie);
        this.estNumeroCLientValide();
        this.estContratValide();
        this.estSoinValide();
        this.estDateValide();
        this.estMontantValide();
        return this.valide;
    }
}
