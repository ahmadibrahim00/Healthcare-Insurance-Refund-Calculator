public class Soin {
    protected long numeroSoin;
    protected String dateSoin;
    protected double prixSoin;
    protected char typeContrat;
    protected long client;
    protected String dateReclamation;

    public Soin() {
    }

    public Soin(long numeroSoin, String dateSoin, double prixSoin,
                char typeContrat, long client, String dateReclamation) {
        this.numeroSoin = numeroSoin;
        this.dateSoin = dateSoin;
        this.prixSoin = prixSoin;
        this.typeContrat = typeContrat;
        this.client = client;
        this.dateReclamation = dateReclamation;
    }

    boolean soinEstMassotherapie() {
        return numeroSoin == 0;
    }

    boolean soinEstOstheotherapie() {
        return numeroSoin == 100;
    }

    boolean soinEstPsychologieIndividuelle() {
        return numeroSoin == 200;
    }

    boolean soinEstSoinsDentaires() {
        return numeroSoin >= 300 && numeroSoin <= 399;
    }

    boolean soinEstNaturopathieAcuponcture() {
        return numeroSoin == 400;
    }

    boolean soinEstChiropratie() {
        return numeroSoin == 500;
    }

    boolean soinEstPhysiotherapie() {
        return numeroSoin == 600;
    }

    boolean soinEstOrthophonieErgotherapie() {
        return numeroSoin == 700;
    }

    double obtenirPourcentagesContratA() {
        double pourcentage = 0;
        if (soinEstMassotherapie() || soinEstOstheotherapie()
                || soinEstPsychologieIndividuelle() || soinEstChiropratie()) pourcentage = 0.25;
        else if (soinEstSoinsDentaires() || soinEstNaturopathieAcuponcture()
                || soinEstOrthophonieErgotherapie()) pourcentage = 0;
        else if (soinEstPhysiotherapie()) pourcentage = 0.40;
        return pourcentage;
    }

    double obtenirPourcentagesContratB() {
        double pourcentage = 0;
        if (soinEstSoinsDentaires() || soinEstMassotherapie()
                || soinEstOstheotherapie() || soinEstChiropratie()) pourcentage = 0.50;
        else if (soinEstPsychologieIndividuelle() || soinEstPhysiotherapie()) pourcentage = 1.00;
        else if (soinEstNaturopathieAcuponcture()) pourcentage = 0;
        else if (soinEstOrthophonieErgotherapie()) pourcentage = 0.70;
        return pourcentage;
    }

    double obtenirPourcentagesContratC() {
        return 0.90;
    }

    double obtenirPourcentagesContratD() {
        return 1.00;
    }

    double obtenirMaxSelonContratA() {
        return -1;
    }

    double obtenirMaxSelonContratB() {
        double max = -1;
        if (soinEstMassotherapie()) max = 40.0;
        else if (soinEstOstheotherapie() || soinEstChiropratie()) max = 50.0;
        else if (soinEstPsychologieIndividuelle()) max = 70.0;
        return max;
    }

    double obtenirMaxSelonContratC() {
        return -1;
    }

    double obtenirMaxSelonContratD() {
        double max = -1;
        if (soinEstMassotherapie()) max = 85.0;
        else if (soinEstOstheotherapie() || soinEstChiropratie()) max = 75.0;
        else if (soinEstPsychologieIndividuelle() || soinEstPhysiotherapie()) max = 100.0;
        else if (soinEstNaturopathieAcuponcture()) max = 65.0;
        else if (soinEstOrthophonieErgotherapie()) max = 90.0;
        return max;
    }

    double obtenirPourcentageSelonContratActif() {
        return switch (typeContrat) {
            case 'A' -> obtenirPourcentagesContratA();
            case 'B' -> obtenirPourcentagesContratB();
            case 'C' -> obtenirPourcentagesContratC();
            case 'D' -> obtenirPourcentagesContratD();
            default -> 0.00;
        };
    }

    double trouverMax() {
        return switch (typeContrat) {
            case 'A' -> obtenirMaxSelonContratA();
            case 'B' -> obtenirMaxSelonContratB();
            case 'C' -> obtenirMaxSelonContratC();
            case 'D' -> obtenirMaxSelonContratD();
            default -> -1;
        };
    }

    boolean maxExiste() {
        return !(trouverMax() == -1);
    }

    double calculerRemboursement() {
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
