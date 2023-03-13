import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;


class MonnaieTest {

    Monnaie un ;
    Monnaie deux;
    Monnaie trois;
    Monnaie six;


    @BeforeEach
    void setUp() {
        // Exécutée avant chaque test.
        un = new Monnaie(new BigDecimal("1"), Currency.getInstance("CAD"));
        deux = new Monnaie(new BigDecimal("2"), Currency.getInstance("CAD"));
        trois = new Monnaie(new BigDecimal("3"), Currency.getInstance("CAD"));
        six = new Monnaie(new BigDecimal("6"), Currency.getInstance("CAD"));
    }

    @AfterEach
    void tearDown() {
        // Exécutée après chaque test
        un = null;
        deux = null;
        trois = null;
        six = null;
    }

    @Test
    void testToString() {
        String expected = "1.00$";
        String actual = un.toString();
        assertEquals(expected, actual);
    }

    @Test
    void testToString1() {
        String expected = "2$";
        String actual = un.toString();
        assertNotEquals(expected, actual);
    }

    @Test
    @DisplayName("Additionne deux nombres")
    void additionner() {

        assertEquals("3.00$", un.addition(deux));
    }

    @Test
    void soustraction_valide() throws IllegalArgumentException {
        assertEquals("1.00$", trois.soustraction(deux));
    }

    @Test
    void soustraction_invalide()  {
        assertThrows(IllegalArgumentException.class, () -> {
            deux.soustraction(trois);
        });
    }

    @Test
    void multiplier() {
        assertEquals("6.00$", deux.multiplication(trois.getValeur()));
    }


    @Test
    @DisplayName("Rejette les strings qui ne sont pas nombres")
    void interceptStrings() {
        assertThrows(NumberFormatException.class, () -> {
            new Monnaie(new BigDecimal("asd"), Currency.getInstance("CAD"));
        });
    }

    @Test
    @DisplayName("Accepte les strings qui représentent des nombres")
    void interceptNombres() {
        assertAll("nombres entiers",
                () -> assertNotNull(new Monnaie(new BigDecimal("6"), Currency.getInstance("CAD"))),
                () -> assertNotNull(new Monnaie(new BigDecimal("20"), Currency.getInstance("CAD"))),
                () -> assertNotNull(new Monnaie(new BigDecimal("10"), Currency.getInstance("CAD")))
        );
    }

}
