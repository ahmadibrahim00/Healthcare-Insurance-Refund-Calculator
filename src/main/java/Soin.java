/**
 * Cette classe permet de calculer les montants a rembourser selon le type de contrat et le prix des soins.
 *
 * @author Vincent Michaud (MICV19039302)
 * @version 12 février 2023
 */

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Currency;

public class Soin {
    private long client;
    private char typeContrat;
    private String dateReclamation;
    private long numeroSoin;
    private String dateSoin;
    private Monnaie prixSoin;
    CompteurDeRemboursement dejaRembourse = new CompteurDeRemboursement();

    public Soin(long client, char typeContrat, String dateReclamation,
                long numeroSoin, String dateSoin, Monnaie prixSoin) {
        this.client = client;
        this.typeContrat = typeContrat;
        this.dateReclamation = dateReclamation;
        this.numeroSoin = numeroSoin;
        this.dateSoin = dateSoin;
        this.prixSoin = prixSoin;
    }

    /**
     * Calcule le montant a rembourser pour chaque soins en fonction du maximum par type de soin
     * @return le montant a rembourser
     */
    public Monnaie calculerRemboursement(){
        Monnaie montantRembourse;
       if (maxMensuelExiste() && dejaRembourse.getCompteur(numeroSoin).plusGrandOuEgal(trouverMaxMensuel())
               || maxExiste() && dejaRembourse.getCompteur(numeroSoin).plusGrandOuEgal(trouverMax())){
           montantRembourse = new Monnaie(0);
       } else if (maxExiste() && dejaRembourse.getCompteur(numeroSoin).ajouter(calculerMontantAvantMax())
               .plusGrandOuEgal(trouverMax())){
           montantRembourse = trouverMax().soustraire(dejaRembourse.getCompteur(numeroSoin));
       } else if (maxMensuelExiste() && dejaRembourse.getCompteur(numeroSoin).ajouter(calculerMontantAvantMax())
               .plusGrandOuEgal(trouverMaxMensuel())) {
           montantRembourse = trouverMaxMensuel().soustraire(dejaRembourse.getCompteur(numeroSoin));
       } else {
           montantRembourse = calculerMontantAvantMax();
       }
        if(montantRembourse.plusPetitQue(new Monnaie(0))) montantRembourse = new Monnaie(0);
        dejaRembourse.accumuler(numeroSoin, montantRembourse);
        return montantRembourse;
    }
    /**
     * Permet d'obtenir le maximum selon le type de contrat associe a la reclamation.
     * @return le maximum remboursable.
     */
    private Monnaie trouverMax() {
        return switch (typeContrat) {
            case 'A' -> obtenirMaxSelonContratA();
            case 'B' -> obtenirMaxSelonContratB();
            case 'C' -> obtenirMaxSelonContratC();
            case 'D' -> obtenirMaxSelonContratD();
            case 'E' -> obtenirMaxSelonContratE();

            default -> new Monnaie(-1);
        };
    }
    //TODO
    private Monnaie trouverMaxMensuel(){
        Monnaie maxMensuel;
        if (numeroSoin == 100 || numeroSoin == 200){
            maxMensuel = new Monnaie(250);
        } else if (numeroSoin == 175) {
            maxMensuel = new Monnaie(200);
        } else if (numeroSoin == 500) {
            maxMensuel = new Monnaie(150);
        } else if (numeroSoin == 600) {
            maxMensuel = new Monnaie(300);
        } else {
            maxMensuel = new Monnaie(-1);
        }
        return maxMensuel;
    }
    /**
     * Determine si un maximum est associe a ce soin de la reclamation.
     * @return Vrai si le contrat admet un maximum, faux sinon.
     */
    private boolean maxExiste() {
        return !(trouverMax().egal(new Monnaie(-1)));
    }
    //TODO
    private boolean maxMensuelExiste() {
        return !(trouverMaxMensuel().egal(new Monnaie(-1)));
    }
    /**
     * Permet d'obtenir le maximum parmi les maximums
     * correspondant au contrat A en fonction du type de soin.
     * @return le maximum en fonction du contrat A.
     */
    private Monnaie obtenirMaxSelonContratA() {
        return new Monnaie(-1);
    }

    /**
     * Permet d'obtenir le maximum parmi les maximums
     * correspondant au contrat B en fonction du type de soin.
     * @return le maximum en fonction du contrat B.
     */
    private Monnaie obtenirMaxSelonContratB() {
        Monnaie max = new Monnaie(-1);
        if (estMassotherapie()) max = new Monnaie(40);
        else if (estOsteopatie() || estChiropratie()) max = new Monnaie(50);
        return max;
    }
    /**
     * Permet d'obtenir le maximum parmi les maximums
     * correspondant au contrat C en fonction du type de soin.
     * @return le maximum en fonction du contrat C.
     */
    private Monnaie obtenirMaxSelonContratC() {
        return new Monnaie(-1);
    }
    /**
     * Permet d'obtenir le maximum parmi les maximums
     * correspondant au contrat D en fonction du type de soin.
     * @return le maximum en fonction du contrat D.
     */
    private Monnaie obtenirMaxSelonContratD() {
        Monnaie max = new Monnaie(-1);
        if (estMassotherapie()) max = new Monnaie(85.0);
        else if (estOsteopatie()) max = new Monnaie(75.0);
        else if (estPsychologieIndividuelle() || estPhysiotherapie()) max = new Monnaie(100.0);
        else if (estNaturopatieAcuponcture()) max = new Monnaie(65.0);
        else if (estOrthophonieErgotherapie()) max = new Monnaie(90.0);
        else if (estKinesitherapie()) max = new Monnaie(150);
        return max;
    }
    private Monnaie obtenirMaxSelonContratE() {
        Monnaie max = new Monnaie(-1);
        if (estNaturopatieAcuponcture()){
            max = new Monnaie(15);
        } else if (estChiropratie() || estMedecinGeneralistePrive()) {
            max = new Monnaie(20);
        }
        return max;
    }
    /**
     * Calcule le montant a rembourser sans prendre en compte le maximum
     * @return Le montant a rembourser avant prise en compte du maximum
     */
    private Monnaie calculerMontantAvantMax() {
        Monnaie montantAvantMax;
        montantAvantMax =  prixSoin.multiplier(obtenirPourcentageSelonContratActif());
        return montantAvantMax;
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
            case 'E' -> obtenirPourcentagesContratE();
            default -> 0.00;
        };
    }
    /**
     * Permet d'obtenir le pourcentage parmi les pourcentages
     * correspondant au contrat A en fonction du type de soin.
     * @return le pourcentage en fonction du contrat A.
     */
    private double obtenirPourcentagesContratA() {
        double pourcentage = 0;
        if (estMassotherapie() || estPsychologieIndividuelle() || estChiropratie()) pourcentage = 0.25;
        else if (estOsteopatie()) pourcentage = 0.35;
        else if (estPhysiotherapie()) pourcentage = 0.40;
        else if (estMedecinGeneralistePrive()) {
            pourcentage = 0.50;
        }
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
        else if (estOrthophonieErgotherapie()) pourcentage = 0.70;
        else if (estMedecinGeneralistePrive()) {
            pourcentage = 0.75;
        }
        return pourcentage;
    }
    /**
     * Permet d'obtenir le pourcentage parmi les pourcentages
     * correspondant au contrat C en fonction du type de soin.
     * @return le pourcentage en fonction du contrat C.
     */
    private double obtenirPourcentagesContratC() {
        double pourcentage = 0.90;
        if (estKinesitherapie()){
            pourcentage = 0.85;
        } else if (estPhysiotherapie()) {
            pourcentage = 0.75;
        } else if (estOsteopatie()) {
            pourcentage = 0.95;
        }
        return pourcentage;
    }
    /**
     * Permet d'obtenir le pourcentage parmi les pourcentages
     * correspondant au contrat D en fonction du type de soin.
     * @return le pourcentage en fonction du contrat D.
     */
    private double obtenirPourcentagesContratD() {
        double pourcentage =  1.00;
        if (estMedecinGeneralistePrive()){
            pourcentage = 0.95;
        }
        return pourcentage;
    }


    private double obtenirPourcentagesContratE() {
        double pourcentage = 0;

        if (estMassotherapie() || estPhysiotherapie()) {
            pourcentage = 0.15;
        } else if (estOsteopatie() || estNaturopatieAcuponcture() || estMedecinGeneralistePrive()) {
            pourcentage = 0.25;
        } else if (estPsychologieIndividuelle()) {
            pourcentage = 0.12;
        } else if (estSoinsDentaires()){
            pourcentage = 0.60;
        } else if (estChiropratie()){
            pourcentage = 0.30;
        } else if (estOrthophonieErgotherapie()){
            pourcentage = 0.22;
        } else if (estKinesitherapie()) {
            pourcentage = 0.15;
        }
        return pourcentage;
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

    private boolean estKinesitherapie(){
        return numeroSoin == 150;
    }

    private boolean estMedecinGeneralistePrive(){
        return numeroSoin == 175;
    }



    public long getNumeroSoin() {
        return numeroSoin;
    }

    public String getDateSoin() {
        return dateSoin;
    }


    /**
     * Formate le prix avec deux chiffres apres la virgule et ajoue "$"
     * @return String prixSoin formate
     */
    public String toStringPrixSoin() {
    return (prixSoin.toString()) + "$";
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
