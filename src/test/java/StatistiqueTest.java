import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class StatistiqueTest
{
    Statistique stats = new Statistique();
    JSONHash a = new JSONHash("Assurance.json", "Test.json");
    @AfterEach
    public void remettreAZero() throws FileNotFoundException {
        stats.executerOptionsStatistique("", "-SR");
    }
    @Test
    public void testgetStatistiques() throws IOException, ParseException {
        stats.formaterFichierStatistiques();
        stats.getStatistiques();
        a.load();
        stats.calculerStatistiques(a);
        long reclamationsvalides = (long) stats.reclamationsValides;
        assertEquals(3, reclamationsvalides);
    }
    @Test
    public void testgetnombrepartypedesoins() throws IOException, ParseException{
        stats.formaterFichierStatistiques();
        stats.getStatistiques();
        a.load();
        stats.calculerStatistiques(a);
       int soin0 = (int) stats.soin0;
       int soin200 = (int) stats.soin200;
       int soin3xx = (int) stats.soin300;
       assertEquals(1, soin200);
       assertEquals(0, soin0);
       assertEquals(1, soin3xx);
    }
    @Test
    public void testgetStatistiquesExistantes() throws IOException, ParseException{
        stats.formaterFichierStatistiques();
        stats.getStatistiques();
        a.load();
        stats.calculerStatistiques(a);
        long statistiquesinvalides = stats.reclamationsInvalides;
        assertEquals(0, statistiquesinvalides);
    }

    @Test
    public void testAdditionnersoinspartype1() throws IOException, ParseException{
        stats.formaterFichierStatistiques();
        stats.getStatistiques();
        a.load();
        stats.calculerStatistiques(a);
        int soin100 = (int) stats.soin100;
        assertEquals(1, soin100);
    }

    @Test
    public void testAdditionnerSoinsParType2()throws IOException, ParseException{
        stats.formaterFichierStatistiques();
        stats.getStatistiques();
        a.load();
        stats.calculerStatistiques(a);
        int soin300 = (int) stats.soin300;
        assertEquals(1, soin300);
    }


}
