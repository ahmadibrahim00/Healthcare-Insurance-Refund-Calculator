import java.math.BigDecimal;
import java.util.Currency;

public class Monnaie
{
    private final BigDecimal valeur;
    private final Currency devise;

    public Monnaie(BigDecimal valeur, Currency devise) {
        this.valeur = valeur.setScale(devise.getDefaultFractionDigits(), BigDecimal.ROUND_HALF_UP);
        this.devise = devise;
    }

    @Override
    public String toString(){

        return this.valeur + "$";
    }


    public BigDecimal getValeur() {
        return valeur;
    }

    public Currency getDevise() {
        return devise;
    }

    public String addition(Monnaie autre) {
        verifierOperandes(autre);
        return String.valueOf(new Monnaie(valeur.add(autre.valeur), devise));
    }

    public String soustraction(Monnaie autre) {
        verifierOperandes(autre);
        return String.valueOf(new Monnaie(valeur.subtract(autre.valeur), devise));
    }

    public String multiplication(BigDecimal factor) {
        return String.valueOf(new Monnaie(valeur.multiply(factor), devise));
    }

    public String division(BigDecimal divisor) {
        return String.valueOf(new Monnaie(valeur.divide(divisor, devise.getDefaultFractionDigits(), BigDecimal.ROUND_HALF_UP), devise));
    }


    private void verifierOperandes(Monnaie autre) throws IllegalArgumentException {

        if (this.valeur.compareTo(autre.valeur) < 0) {

            throw new IllegalArgumentException("Opération impossible : "
                    + this.valeur + " - " + autre.valeur);
        }
    }

        //public static void main (String[] args)
    //{
        //Monnaie money = new Monnaie(new BigDecimal("1"), Currency.getInstance("CAD"));
        //Monnaie result = money.addition(new Monnaie(new BigDecimal("3"), Currency.getInstance("CAD")));


        //DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        //String formattedValue = decimalFormat.format(result.getValeur());

        //System.out.println(formattedValue + " " + result.getDevise().getSymbol());    }

}
