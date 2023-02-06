public class Soin {
    protected long client;
    protected char typeContrat;
    protected String dateReclamation;
    protected long numeroSoin;
    protected String dateSoin;
    protected double prixSoin;

    public Soin() {
    }

    public Soin(long client, char typeContrat, String dateReclamation,
                long numeroSoin, String dateSoin, double prixSoin) {
        this.client = client;
        this.typeContrat = typeContrat;
        this.dateReclamation = dateReclamation;
        this.numeroSoin = numeroSoin;
        this.dateSoin = dateSoin;
        this.prixSoin = prixSoin;
    }

    /**
     * Determine si l'instance de soin a le numero de soin de massotherapie.
     * @return vrai si le soin a le numero de soin de massotherapie, faux dans le cas contraire.
     */
    private boolean estMassotherapie() {
        return numeroSoin == 0;
    }
    /**
     * Determine si l'instance de soin a le numero de soin d'osteopatie.
     * @return vrai si le soin a le numero de soin d'osteopatie, faux dans le cas contraire.
     */
    private boolean estOsteopatie() {
        return numeroSoin == 100;
    }
    /**
     * Determine si l'instance de soin a le numero de soin de psychologie individuelle.
     * @return vrai si le soin a le numero de soin de psychologie individuelle, 
     * faux dans le cas contraire.
     */
    private boolean estPsychologieIndividuelle() {
        return numeroSoin == 200;
    }
    /**
     * Determine si l'instance de soin a le numero de soin dentaire.
     * @return vrai si le soin a le numero de soin dentaire, faux dans le cas contraire.
     */
    private boolean estSoinsDentaires() {
        return numeroSoin >= 300 && numeroSoin <= 399;
    }
    /**
     * Determine si l'instance de soin a le numero de soin de naturopatie ou d'acuponcture.
     * @return vrai si le soin a le numero de soin de naturopatie ou d'acuponcture,
     * faux dans le cas contraire
     */
    private boolean estNaturopatieAcuponcture() {
        return numeroSoin == 400;
    }
    /**
     * Determine si l'instance de soin a le numero de soin de chiropratie.
     * @return vrai si le soin a le numero de soin de chiropratie, faux dans le cas contraire.
     */
    private boolean estChiropratie() {
        return numeroSoin == 500;
    }
    /**
     * Determine si l'instance de soin a le numero de soin de physiotherapie.
     * @return vrai si le soin a le numero de soin de physiotherapie, faux dans le cas contraire.
     */
    private boolean estPhysiotherapie() {
        return numeroSoin == 600;
    }
    /**
     * Determine si l'instance de soin a le numero de soin d'orthophonie ou d'ergotherapie.
     * @return vrai si le soin a le numero de soin d'orthophonie ou d'ergotherapie,
     * faux dans le cas contraire.
     */
    private boolean estOrthophonieErgotherapie() {
        return numeroSoin == 700;
    }

    /**
     * Permet d'obtenir le pourcentage parmi les pourcentages
     * correspondant au contrat A en fonction du type de soin.
     * @return le pourcentage en fonction du contrat A.
     */
    private double obtenirPourcentagesContratA() {
        double pourcentage = 0;
        if (estMassotherapie() || estOsteopatie()
                || estPsychologieIndividuelle() || estChiropratie()) pourcentage = 0.25;
        else if (estSoinsDentaires() || estNaturopatieAcuponcture()
                || estOrthophonieErgotherapie()) pourcentage = 0;
        else if (estPhysiotherapie()) pourcentage = 0.40;
        return pourcentage;
    }
    /**
     * Permet d'obtenir le pourcentage parmi les pourcentages
     * correspondant au contrat B en fonction du type de soin.
     * @return le pourcentage en fonction du contrat B.
     */
    private double obtenirPourcentagesContratB() {
        double pourcentage = 0;
        if (estSoinsDentaires() || estMassotherapie()
                || estOsteopatie() || estChiropratie()) pourcentage = 0.50;
        else if (estPsychologieIndividuelle() || estPhysiotherapie()) pourcentage = 1.00;
        else if (estNaturopatieAcuponcture()) pourcentage = 0;
        else if (estOrthophonieErgotherapie()) pourcentage = 0.70;
        return pourcentage;
    }
    /**
     * Permet d'obtenir le pourcentage parmi les pourcentages
     * correspondant au contrat C en fonction du type de soin.
     * @return le pourcentage en fonction du contrat C.
     */
    private double obtenirPourcentagesContratC() {
        return 0.90;
    }
    /**
     * Permet d'obtenir le pourcentage parmi les pourcentages
     * correspondant au contrat D en fonction du type de soin.
     * @return le pourcentage en fonction du contrat D.
     */
    private double obtenirPourcentagesContratD() {
        return 1.00;
    }
    /**
     * Permet d'obtenir le maximum parmi les maximums
     * correspondant au contrat A en fonction du type de soin.
     * @return le maximum en fonction du contrat A.
     */
    private double obtenirMaxSelonContratA() {
        return -1;
    }
    /**
     * Permet d'obtenir le maximum parmi les maximums
     * correspondant au contrat B en fonction du type de soin.
     * @return le maximum en fonction du contrat B.
     */
    private double obtenirMaxSelonContratB() {
        double max = -1;
        if (estMassotherapie()) max = 40.0;
        else if (estOsteopatie() || estChiropratie()) max = 50.0;
        else if (estPsychologieIndividuelle()) max = 70.0;
        return max;
    }
    /**
     * Permet d'obtenir le maximum parmi les maximums
     * correspondant au contrat C en fonction du type de soin.
     * @return le maximum en fonction du contrat C.
     */
    private double obtenirMaxSelonContratC() {
        return -1;
    }
    /**
     * Permet d'obtenir le maximum parmi les maximums
     * correspondant au contrat D en fonction du type de soin.
     * @return le maximum en fonction du contrat D.
     */
    private double obtenirMaxSelonContratD() {
        double max = -1;
        if (estMassotherapie()) max = 85.0;
        else if (estOsteopatie() || estChiropratie()) max = 75.0;
        else if (estPsychologieIndividuelle() || estPhysiotherapie()) max = 100.0;
        else if (estNaturopatieAcuponcture()) max = 65.0;
        else if (estOrthophonieErgotherapie()) max = 90.0;
        return max;
    }

    /**
     * Permet d'obtenir le pourcentage selon le type de contrat associe a la reclamation.
     * @return le pourcentage remboursable.
     */
    private double obtenirPourcentageSelonContratActif() {
        return switch (typeContrat) {
            case 'A' -> obtenirPourcentagesContratA();
            case 'B' -> obtenirPourcentagesContratB();
            case 'C' -> obtenirPourcentagesContratC();
            case 'D' -> obtenirPourcentagesContratD();
            default -> 0.00;
        };
    }
    /**
     * Permet d'obtenir le maximum selon le type de contrat associe a la reclamation.
     * @return le maximum remboursable.
     */
    private double trouverMax() {
        return switch (typeContrat) {
            case 'A' -> obtenirMaxSelonContratA();
            case 'B' -> obtenirMaxSelonContratB();
            case 'C' -> obtenirMaxSelonContratC();
            case 'D' -> obtenirMaxSelonContratD();
            default -> -1;
        };
    }

    /**
     * Determine si un maximum est associe a ce soin de la reclamation.
     * @return Vrai si le contrat admet un maximum, faux sinon.
     */
    private boolean maxExiste() {
        return !(trouverMax() == -1);
    }

    /**
     * Permet d'ontenir le montant qui doit etre rembourse selon
     * le pourcentage approprie et du maximum determie, s'il y en a un.
     * @return le montant remboursable pour le soin.
     */
    public double calculerRemboursement() {
        double montantRembourse;
        if (maxExiste() && (obtenirPourcentageSelonContratActif() * prixSoin >= trouverMax())) {
            montantRembourse = trouverMax();
        } else {
            montantRembourse = obtenirPourcentageSelonContratActif() * prixSoin;
        }
        return montantRembourse;
    }

    public long getNumeroSoin() {
        return numeroSoin;
    }

    public String getDateSoin() {
        return dateSoin;
    }

    public double getPrixSoin() {
        return prixSoin;
    }

    public char getTypeContrat() {
        return typeContrat;
    }

    public long getClient() {
        return client;
    }

    public String getDateReclamation() {
        return dateReclamation;
    }
}
