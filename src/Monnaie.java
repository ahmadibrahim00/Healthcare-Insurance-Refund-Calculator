import java.math.BigDecimal;
import java.math.RoundingMode;

public class Monnaie {
    private BigDecimal valeurEnCents;

    public Monnaie(double montant) {
        this.valeurEnCents = BigDecimal.valueOf(montant * 100).setScale(0, RoundingMode.HALF_UP);
    }

    public Monnaie ajouter(Monnaie autre) {
        BigDecimal somme = this.valeurEnCents.add(autre.valeurEnCents);
        return new Monnaie(somme.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).doubleValue());
    }

    public Monnaie soustraire(Monnaie autre) {
        BigDecimal difference = this.valeurEnCents.subtract(autre.valeurEnCents);
        return new Monnaie(difference.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).doubleValue());
    }

    public Monnaie multiplier(double facteur) {
        BigDecimal produit = this.valeurEnCents.multiply(BigDecimal.valueOf(facteur));
        return new Monnaie(produit.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).doubleValue());
    }

    public Monnaie diviser(double diviseur) {
        BigDecimal quotient = this.valeurEnCents.divide(BigDecimal.valueOf(diviseur), 2, RoundingMode.HALF_UP);
        return new Monnaie(quotient.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).doubleValue());
    }

    public int compareTo(Monnaie autre) {
        return this.valeurEnCents.compareTo(autre.valeurEnCents);
    }

    @Override
    public String toString() {
        BigDecimal valeurEnDollars = this.valeurEnCents.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return String.valueOf(valeurEnDollars);
    }

   // public static void main(String[] args)
    //{
        //Monnaie m1 = new Monnaie(120.21); // $120.21
        //Monnaie m2 = new Monnaie(59.99); // $59.99
        //Monnaie m3 = new Monnaie(1000.00); // $1000.00

// Ajouter deux montants
        //Monnaie somme = m1.ajouter(m2);
        //double q = Double.valueOf(String.valueOf(somme));
        //System.out.println(q); // Affiche : $180.20

// Soustraire deux montants
        //Monnaie difference = m3.soustraire(m1);
        //double w = Double.valueOf(String.valueOf(difference));
        //System.out.println(w); // Affiche : $879.79

// Multiplier un montant par un facteur
        //Monnaie produit = m2.multiplier(2);
        //double e = Double.valueOf(String.valueOf(produit));
        //System.out.println(e); // Affiche : $119.98

// Diviser un montant par un diviseur, avec arrondi
        //Monnaie quotient = m1.diviser(3);
        //double r = Double.valueOf(String.valueOf(quotient));
        //System.out.println(r); // Affiche : $40.07


    //}
}
