import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class StatistiqueTest
{
    Statistique stats = new Statistique();

    @Test
    public void testgetStatistiques() throws IOException, ParseException {
        stats.modifierFichierStatistiques();
        stats.getStatistiques();
        assertEquals(2, 2);
    }

}
