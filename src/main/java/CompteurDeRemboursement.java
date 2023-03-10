public class CompteurDeRemboursement {

    private static double compteurMasso = 0;
    private static double compteurOsteo = 0;
    private static double compteurPsycho = 0;
    private static double compteurDentaire = 0;
    private static double compteurNaturoAcu = 0;
    private static double compteurChiro = 0;
    private static double compteurPhysio = 0;
    private static double compteurOrthoErgo = 0;
    private static double compteurKinesitherapie = 0;
    private static double compteurMedecinGeneralistePrive = 0;

    public void accumuler(long typeSoin, double montantAAdditionner) {
        if (typeSoin == 0) {
            compteurMasso = compteurMasso + montantAAdditionner;
        } else if (typeSoin == 100) {
            compteurOsteo = compteurOsteo + montantAAdditionner;
        } else if (typeSoin == 200) {
            compteurPsycho = compteurPsycho + montantAAdditionner;
        } else if (typeSoin >= 300 && typeSoin <= 399) {
            compteurDentaire = compteurDentaire + montantAAdditionner;
        } else if (typeSoin == 400) {
            compteurNaturoAcu = compteurNaturoAcu + montantAAdditionner;
        } else if (typeSoin == 500) {
            compteurChiro = compteurChiro + montantAAdditionner;
        } else if (typeSoin == 600) {
            compteurPhysio = compteurPhysio + montantAAdditionner;
        } else if (typeSoin == 700) {
            compteurOrthoErgo = compteurOrthoErgo + montantAAdditionner;
        } else if (typeSoin == 150) {
            compteurKinesitherapie = compteurKinesitherapie + montantAAdditionner;
        } else if (typeSoin == 175) {
            compteurMedecinGeneralistePrive = compteurMedecinGeneralistePrive + montantAAdditionner;
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
        } else if (typeSoin == 150) {
            valeurCompteur = compteurKinesitherapie;
        } else if (typeSoin == 175) {
            valeurCompteur = compteurMedecinGeneralistePrive;
        }
        return valeurCompteur;
    }

    /**
     * Retourne le montant total rembourse dans tous les soins
     * @return le montant total Rembourse
     */
    public double getTotalRembourse(){
        return compteurMasso + compteurOsteo + compteurPsycho + compteurDentaire
                + compteurNaturoAcu + compteurChiro + compteurPhysio + compteurOrthoErgo
                + compteurKinesitherapie + compteurMedecinGeneralistePrive;
    }
}
