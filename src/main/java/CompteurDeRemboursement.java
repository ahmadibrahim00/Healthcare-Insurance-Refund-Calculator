public class CompteurDeRemboursement {

    private static double compteurMasso = 0;
    private static double compteurOsteo = 0;
    private static double compteurPsycho = 0;
    private static double compteurDentaire = 0;
    private static double compteurNaturoAcu = 0;
    private static double compteurChiro = 0;
    private static double compteurPhysio = 0;
    private static double compteurOrthoErgo = 0;

    public void accumuler(long typeSoin, double montantACumuler) {
        if (typeSoin == 0) {
            compteurMasso = compteurMasso + montantACumuler;
        } else if (typeSoin == 100) {
            compteurOsteo = compteurOsteo + montantACumuler;
        } else if (typeSoin == 200) {
            compteurPsycho = compteurPsycho + montantACumuler;
        } else if (typeSoin >= 300 && typeSoin <= 399) {
            compteurDentaire = compteurDentaire + montantACumuler;
        } else if (typeSoin == 400) {
            compteurNaturoAcu = compteurNaturoAcu + montantACumuler;
        } else if (typeSoin == 500) {
            compteurChiro = compteurChiro + montantACumuler;
        } else if (typeSoin == 600) {
            compteurPhysio = compteurPhysio + montantACumuler;
        } else if (typeSoin == 700) {
            compteurOrthoErgo = compteurOrthoErgo + montantACumuler;
        }
    }

    public double getCompteur(long typeSoin) {
        double valeurCompteur = 0;
        if (typeSoin == 0) {
            valeurCompteur = compteurMasso;
        } else if (typeSoin == 100) {
            valeurCompteur = compteurOsteo;
        } else if (typeSoin == 200) {
            valeurCompteur = compteurPsycho;
        } else if (typeSoin >= 300 && typeSoin <= 399) {
            valeurCompteur = compteurDentaire;
        } else if (typeSoin == 400) {
            valeurCompteur = compteurNaturoAcu;
        } else if (typeSoin == 500) {
            valeurCompteur = compteurChiro;
        } else if (typeSoin == 600) {
            valeurCompteur = compteurPhysio;
        } else if (typeSoin == 700) {
            valeurCompteur = compteurOrthoErgo;
        }
        return valeurCompteur;
    }
}
