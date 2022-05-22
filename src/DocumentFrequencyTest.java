import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DocumentFrequencyTest {

    @Test
    public void test() throws IOException {
        DocumentFrequency a = new DocumentFrequency("./src/files/test.txt");
        System.out.println(a.numDocuments());
        System.out.println(a.query("QuIcK"));
    }

}