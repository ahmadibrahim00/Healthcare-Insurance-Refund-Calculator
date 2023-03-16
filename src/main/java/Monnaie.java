import java.math.BigDecimal;
import java.math.RoundingMode;

public class Monnaie {
    private BigDecimal valeurEnCents;

    public Monnaie(double montant) {
        this.valeurEnCents = BigDecimal.valueOf(montant * 100).setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * Méthode qui retourne la valeur de l'adition en monnaie par une division de 100 et arrondi les cents au besoin
     */
    public Monnaie ajouter(Monnaie autre) {
        BigDecimal somme = this.valeurEnCents.add(autre.valeurEnCents);
        return new Monnaie(somme.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).doubleValue());
    }

    /**
     * Méthode qui retourne la valeur de la soustraction en monnaie par une division de 100 et arrondi les cents au besoin
     */
    public Monnaie soustraire(Monnaie autre) {
        int invalid = this.valeurEnCents.compareTo(autre.valeurEnCents);
        if (invalid < 0) {
            throw new IllegalArgumentException("Le premier montant est plus petit que le deuxième");
        } else {
            BigDecimal difference = this.valeurEnCents.subtract(autre.valeurEnCents);
            return new Monnaie(difference.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).doubleValue());
        }
    }

    /**
     * Méthode qui retourne la valeur de la multiplication en monnaie par une division de 100 et arrondi les cents au besoin
     */
    public Monnaie multiplier(double facteur) {
        BigDecimal produit = this.valeurEnCents.multiply(BigDecimal.valueOf(facteur));
        return new Monnaie(produit.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).doubleValue());
    }

    /**
     * Méthode qui retourne la valeur de la division en monnaie par une division de 100 et arrondi les cents au besoin
     */
    public Monnaie diviser(double diviseur) {
        BigDecimal quotient = this.valeurEnCents.divide(BigDecimal.valueOf(diviseur), 2, RoundingMode.HALF_UP);
        return new Monnaie(quotient.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).doubleValue());
    }

    /**
     * Méthodes qui retourne la valeur de la comparaison entre les deux valeurs
     */
    public boolean plusGrandQue(Monnaie autre) {
        return this.valeurEnCents.compareTo(autre.valeurEnCents) > 0;
    }

    public boolean plusPetitQue(Monnaie autre) {
        return this.valeurEnCents.compareTo(autre.valeurEnCents) < 0;
    }

    public boolean plusGrandOuEgal(Monnaie autre) {
        return this.valeurEnCents.compareTo(autre.valeurEnCents) > 0
                || this.valeurEnCents.compareTo(autre.valeurEnCents) == 0;
    }

    public boolean plusPetitOuEgal(Monnaie autre) {
        return this.valeurEnCents.compareTo(autre.valeurEnCents) < 0
                || this.valeurEnCents.compareTo(autre.valeurEnCents) == 0;
    }

    public boolean egal(Monnaie autre) {
        return this.valeurEnCents.compareTo(autre.valeurEnCents) == 0;
    }

    @Override
    public String toString() {
        BigDecimal valeurEnDollars = this.valeurEnCents.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return String.valueOf(valeurEnDollars);
    }

}