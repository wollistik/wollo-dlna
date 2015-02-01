/**
 * 
 */
package de.wollistik.dlna.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * @author Wolfgang Wedelich-John<wolfgang.wedelich@wollistik.de>
 *
 */
public class FastCheckSumTest {

    /**
     * Creates a several hundred MB big test file.
     * 
     * @throws IOException
     */
    public File createTestFile(int size) {

        try {
            File tmpFile = File.createTempFile("movie",
                    ".mkv");
            tmpFile.deleteOnExit();

            FileOutputStream out = new FileOutputStream(tmpFile);

            // one mb big buffer
            byte[] buffer = new byte[1024 * 1024];
            // fill with some data
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = (byte) 0xFF;
            }

            for (int i = 0; i < size; i++) {
                out.write(buffer);
            }
            out.close();

            return tmpFile;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Test
    public void test150MB() {
        FastCheckSum chekSum = new FastCheckSum(createTestFile(150));

        String sum = chekSum.createChecksum();

        AssertJUnit.assertEquals("a02417ce412a11d56dfe5e37d0914c17",
                sum);
    }

    @Test
    public void test3MB() {
        FastCheckSum chekSum = new FastCheckSum(createTestFile(3));

        String sum = chekSum.createChecksum();

        AssertJUnit.assertEquals("0def62b109b03c082dcbe001f34e7bbb",
                sum);

    }

    @Test
    public void checksumOfTwoFilesLargerThanChunkSizeShouldDiffer() {
        String chekSum1 = new FastCheckSum(createTestFile(6)).createChecksum();
        String chekSum2 = new FastCheckSum(createTestFile(7)).createChecksum();

        AssertJUnit.assertNotSame(chekSum1,
                chekSum2);

    }
}
