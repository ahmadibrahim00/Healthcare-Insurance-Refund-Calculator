import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JSONHashTest
{
    JSONHash a = new JSONHash("Assurance.json", "Remboursement.json");

    @Test
    public void testgetnumDossier() throws IOException, ParseException {
        a.load();
        assertEquals(100323, a.getNumDossier());
    }
    @Test
    public void testgetlettreContrat() throws IOException, ParseException {
        a.load();
        assertEquals('A', a.getContrat());
    }
    @Test
    public void testgetMois() throws IOException, ParseException
    {
        a.load();
        assertEquals("2022-01", a.getMois());
    }
    @Test
    public void testgetSoins() throws IOException, ParseException
    {
        a.load();
        long soin0 = a.getSoin(0);
        assertEquals(100, soin0);
    }
    @Test
    public void testgetNbSoins() throws IOException, ParseException
    {
        a.load();
        assertEquals(3, a.getNbSoin());
    }
    @Test
    public void testgetDate() throws IOException, ParseException
    {
        a.load();
        String date1 = a.getDate(1);
        assertEquals("2022-01-13", date1);
    }
    @Test
    public void testgetMontant() throws IOException, ParseException
    {
        a.load();
        float montant2 = a.getMontant(2);
        assertEquals(125.00, montant2, 0);
    }
}
