/**
 * 
 */
package de.wollistik.dlna.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Fast checksum creation for large Files.
 * 
 * @author Wolfgang Wedelich-John<wolfgang.wedelich@wollistik.de>
 *
 */
public class FastCheckSum {

    /**
     * The default chunk size of 5 MB.
     */
    public static final int DEFAULT_CHUNK_SIZE = 1024 * 1024 * 5;

    /**
     * The default size for skipping after a chunk. 100 MB.
     */
    public static final long DEFAULT_SKIP_SIZE = 1024 * 1024 * 100;

    /**
     * The file to create a checksum for.
     */
    private File file;

    /**
     * @param file
     */
    public FastCheckSum(File file) {
        this.file = file;
    }

    public String createChecksum() {

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            InputStream in = new FileInputStream(file);

            byte[] buffer = new byte[DEFAULT_CHUNK_SIZE];

            int length = fillBuffer(in,
                    buffer);

            digest.update(buffer,
                    0,
                    length);

            long skipped = in.skip(DEFAULT_SKIP_SIZE);

            String result = new String(digest.digest());
            return result;
        } catch (NoSuchAlgorithmException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    private int fillBuffer(InputStream in,
            byte[] buffer) {
        return 0;
    }
}
