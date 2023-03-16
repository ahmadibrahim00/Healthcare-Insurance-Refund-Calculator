import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JSONHashTest
{
    JSONHash a = new JSONHash("Assurance.json", "Test.json");
    /**
     * Méthode de test Junit qui valide le numéro de dossier
     */
    @Test
    public void testgetnumDossier() throws IOException, ParseException {
        a.load();
        assertEquals(100323l, a.getNumDossier());
    }
    /**
     * Méthode de test Junit qui valide la lettre du contrat
     */
    @Test
    public void testgetlettreContrat() throws IOException, ParseException {
        a.load();
        assertEquals('A', a.getContrat());
    }
    /**
     * Méthode de test Junit qui valide le mois du dossier
     */
    @Test
    public void testgetMois() throws IOException, ParseException
    {
        a.load();
        assertEquals("2022-01", a.getMois());
    }
    /**
     * Méthode de test Junit qui valide le soin choisi
     */
    @Test
    public void testgetSoins() throws IOException, ParseException
    {
        a.load();
        long soin0 = a.getSoin(0);
        assertEquals(100l, soin0);
    }
    /**
     * Méthode de test Junit qui valide le nombre de soins
     */
    @Test
    public void testgetNbSoins() throws IOException, ParseException
    {
        a.load();
        assertEquals(3, a.getNbSoin());
    }
    /**
     * Méthode de test Junit qui valide la date d'un soin choisi
     */
    @Test
    public void testgetDate() throws IOException, ParseException
    {
        a.load();
        String date1 = a.getDate(1);
        assertEquals("2022-01-13", date1);
    }
    /**
     * Méthode de test Junit qui valide le montant d'un soin choisi
     */
    @Test
    public void testgetMontant() throws IOException, ParseException
    {
        a.load();
        float montant2 = a.getMontant(2);
        assertEquals(125.00, montant2, 0);
    }
}
