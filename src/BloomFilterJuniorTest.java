import org.junit.Test;

import static org.junit.Assert.*;

public class BloomFilterJuniorTest {

    @Test
    public void test(){
        BloomFilterJunior a = new BloomFilterJunior(100);
        a.insert("POOP");
        a.insert("HELLO");
        assertTrue(a.lookup("HELLO"));
        assertFalse(a.lookup("Bonjour"));
        assertFalse(a.lookup("HELLo"));
    }

}