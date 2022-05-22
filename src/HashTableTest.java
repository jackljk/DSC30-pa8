import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HashTableTest {
    private HashTable a;
    private HashTable b;

    @Before
    public void setUp() throws Exception {
        a = new HashTable();
        b = new HashTable(5);
    }

    @Test
    public void aTest(){
        assertEquals(15, a.capacity());
        assertTrue(a.insert("Hello"));
        assertEquals(1, a.size());
        assertTrue(a.lookup("Hello"));
        assertFalse(a.insert("Hello"));
        assertTrue(a.insert("Bonjour"));
        assertTrue(a.insert("Ni Hao"));
        assertTrue(a.insert("Konichiwa"));
        assertTrue(a.insert("Ohaiyo"));
        assertTrue(a.insert("Hola"));
        assertTrue(a.insert("Guten Tag"));
        assertTrue(a.insert("Salve"));
        assertTrue(a.insert("ola"));
        assertTrue(a.insert("asalaam alaikum"));
        assertEquals("Before rehash # 1: load factor 0.60, 1 collisions(s).\n", a.getStatsLog());
        assertEquals(10, a.size());
        assertTrue(a.delete("Hello"));
        assertTrue(a.delete("Bonjour"));
        assertTrue(a.insert("Hello"));
        assertTrue(a.lookup("ola"));
        assertEquals(30, a.capacity());
    }

    @Test
    public void bTest(){
        String[] words = new String[]{"Hello", "Bonjour", "Hola", "Ciao", "Olá", "Kia Ora", "G" +
                "'day", "Geia (γεια)", "Zdravo", "Privet", "Nǐ hǎo", "Namaste", "Kon’nichiwa",
                "Merhaba", "Kon’nichiwa", "Ahoj", "Guten Tag", "Hallo", "Cześć", "S̄wạs̄dī",
                "Szia", "Hyālō", "Hello", "Hola", "Assalam u Alaikum", "Salām", "Dobryj Den",
                "Hallå", "Bunâ", "Shalom", "Barev", "Sata Srī Akāla", "Halo", "Selamat Pagi",
                "Xin Chào", "Kaixo", "Kamusta"};
        for (int i =0;i<words.length;i++){
            b.insert(words[i]);
        }
        assertEquals("Before rehash # 1: load factor 0.60, 0 collisions(s).\n" +
                "Before rehash # 2: load factor 0.60, 1 collisions(s).\n" +
                "Before rehash # 3: load factor 0.60, 3 collisions(s).\n" +
                "Before rehash # 4: load factor 0.58, 8 collisions(s).\n", b.getStatsLog());
        assertEquals(34, b.size());
        assertEquals(80, b.capacity());
        assertTrue(b.delete("Hola"));
        assertTrue(b.lookup("Barev"));
        assertTrue(b.delete("Barev"));
        assertFalse(b.lookup("Barev"));
        assertEquals(32, b.size());
        assertEquals(80, b.capacity());
        for (int i =0;i<words.length;i++){
            b.insert(words[i] + i);
        }
        assertEquals("Before rehash # 1: load factor 0.60, 0 collisions(s).\n" +
                "Before rehash # 2: load factor 0.60, 1 collisions(s).\n" +
                "Before rehash # 3: load factor 0.60, 3 collisions(s).\n" +
                "Before rehash # 4: load factor 0.58, 8 collisions(s).\n" +
                "Before rehash # 5: load factor 0.56, 14 collisions(s).\n", b.getStatsLog());
        assertEquals(69, b.size());
        assertEquals(160, b.capacity());
    }

    @Test
    public void test(){
        for (int i = 0;i<20;i++){
            a.insert("Hello");
            a.insert("Bonjour");
        }
        System.out.println(a.toString());
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructorIAE(){
        HashTable test = new HashTable(1);
    }

    @Test (expected = NullPointerException.class)
    public void insertNPE(){
        a.insert(null);
    }

    @Test (expected = NullPointerException.class)
    public void deleteNPE(){
        a.insert("Hello");
        a.delete(null);
    }

    @Test (expected = NullPointerException.class)
    public void lookupNPE(){
        a.insert("Hello");
        a.lookup(null);
    }
}