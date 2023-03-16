import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.assertEquals;

public class MonnaieTest {

    Monnaie m1 = new Monnaie(10.50);
    Monnaie m2 = new Monnaie(23.75);
    @Test
    public void testConstruction() {
        assertEquals("10.50", m1.toString());
    }

    @Test
    public void testAddition() {
        Monnaie m3 = m1.ajouter(m2);
        assertEquals("34.25", m3.toString());
    }

    @Test
    public void testSoustraction() {
        Monnaie m3 = m2.soustraire(m1);
        assertEquals("13.25", m3.toString());
    }

    @Test
    public void testMultiplication() {
        Monnaie m2 = m1.multiplier(2.5);
        assertEquals("26.25", m2.toString());
    }

    @Test
    public void invalidetestMultiplication()
    {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {Monnaie m3 = m1.soustraire(m2);});
    }

    @Test
    public void testDivision() {
        Monnaie m1 = new Monnaie(10.22);
        Monnaie m2 = m1.diviser(3);
        assertEquals("3.41", m2.toString());
    }


}
