public class CompteurDeRemboursement {

    private static Monnaie compteurMasso = new Monnaie(0);
    private static Monnaie compteurOsteo = new Monnaie(0);
    private static Monnaie compteurPsycho = new Monnaie(0);
    private static Monnaie compteurDentaire = new Monnaie(0);
    private static Monnaie compteurNaturoAcu = new Monnaie(0);
    private static Monnaie compteurChiro = new Monnaie(0);
    private static Monnaie compteurPhysio = new Monnaie(0);
    private static Monnaie compteurOrthoErgo = new Monnaie(0);
    private static Monnaie compteurKinesitherapie = new Monnaie(0);
    private static Monnaie compteurMedecinGeneralistePrive = new Monnaie(0);

    public void accumuler(long typeSoin, Monnaie montantAAdditionner) {
        if (typeSoin == 0) {
            compteurMasso = compteurMasso.ajouter(montantAAdditionner);
        } else if (typeSoin == 100) {
            compteurOsteo = compteurOsteo.ajouter(montantAAdditionner);
        } else if (typeSoin == 200) {
            compteurPsycho = compteurPsycho.ajouter(montantAAdditionner);
        } else if (typeSoin >= 300 && typeSoin <= 399) {
            compteurDentaire = compteurDentaire.ajouter(montantAAdditionner);
        } else if (typeSoin == 400) {
            compteurNaturoAcu = compteurNaturoAcu.ajouter(montantAAdditionner);
        } else if (typeSoin == 500) {
            compteurChiro = compteurChiro.ajouter(montantAAdditionner);
        } else if (typeSoin == 600) {
            compteurPhysio = compteurPhysio.ajouter(montantAAdditionner);
        } else if (typeSoin == 700) {
            compteurOrthoErgo = compteurOrthoErgo.ajouter(montantAAdditionner);
        } else if (typeSoin == 150) {
            compteurKinesitherapie = compteurKinesitherapie.ajouter(montantAAdditionner);
        } else if (typeSoin == 175) {
            compteurMedecinGeneralistePrive = compteurMedecinGeneralistePrive.ajouter(montantAAdditionner);
        }
    }

    public Monnaie getCompteur(long typeSoin) {
        Monnaie valeurCompteur = new Monnaie(0);
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
    public Monnaie getTotalRembourse(){
        return compteurMasso.ajouter(compteurOsteo).ajouter(compteurPsycho).ajouter(compteurDentaire)
                .ajouter(compteurNaturoAcu).ajouter(compteurChiro).ajouter(compteurPhysio).ajouter(compteurOrthoErgo)
                .ajouter(compteurKinesitherapie).ajouter(compteurMedecinGeneralistePrive);
    }
}
