import java.io.*;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;

public class TestLengthCounter {

    @Test
    public void testCount() {
        LengthCounter lengthCounter = new LengthCounter(",", "+");
        assertEquals("7+3", lengthCounter.count("\"abc,def\",abc"));
        assertEquals("5+4+3", lengthCounter.count("\"a\"bc,def\",abc"));
        assertEquals("5+2+2", lengthCounter.count("ab\"cd,d\",as"));
        lengthCounter.count("abc,abc, ,");
        assertEquals("0+1+1", lengthCounter.count("\"\", ,a"));
        assertEquals("3", lengthCounter.count("abc"));
        assertEquals("12", lengthCounter.count("\"\"a\"bcdef,abc\""));
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testCountFromFile()throws IOException {
        final File file1 = tempFolder.newFile("input.csv");
        final File file2 = tempFolder.newFile("output.csv");

        BufferedWriter input = new BufferedWriter(new FileWriter(file1));
        BufferedReader output = new BufferedReader(new FileReader(file2));

        input.write("abc\n");
        input.write("ab/*cf*/a\n");
        input.write("a");
        input.close();

        LengthCounter lengthCounter = new LengthCounter(",", "+");
        lengthCounter.countFromFile(file1.getAbsolutePath(), file2.getAbsolutePath());
        assertEquals("3", output.readLine());
        assertEquals("3", output.readLine());
        assertEquals("1", output.readLine());
        output.close();

        final File file3 = tempFolder.newFile("input2.csv");
        final File file4 = tempFolder.newFile("output2.csv");

        BufferedWriter input2 = new BufferedWriter(new FileWriter(file3));
        BufferedReader output2 = new BufferedReader(new FileReader(file4));

        input2.write("/*cf*/a\n");
        input2.write("a");
        input2.close();

        lengthCounter.countFromFile(file3.getAbsolutePath(), file4.getAbsolutePath());
        assertEquals("1", output2.readLine());
        assertEquals("1", output2.readLine());
        output2.close();

        final File file5 = tempFolder.newFile("input3.csv");
        final File file6 = tempFolder.newFile("output3.csv");

        BufferedWriter input3 = new BufferedWriter(new FileWriter(file5));
        BufferedReader output3 = new BufferedReader(new FileReader(file6));

        input3.write("abc");
        input3.close();

        lengthCounter.countFromFile(file5.getAbsolutePath(), file6.getAbsolutePath());
        assertEquals("3", output3.readLine());
        output3.close();
    }
}
