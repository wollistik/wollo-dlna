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

import org.apache.commons.codec.binary.Hex;

/**
 * Fast checksum creation for large Files. The basic idea is not to take the
 * complete file for the checksum, but instead take every 100MB a only a small
 * chunk for checksum creation.
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
     * The default size for skipping after a chunk (100 MB).
     */
    public static final long DEFAULT_SKIP_SIZE = 1024 * 1024 * 100;

    /**
     * Static method to obtain a new {@link FastCheckSum} object and call
     * getChecksum Method.
     * 
     * @param file
     *            the {@link File} to be used for checksum creation.
     * @return the HEX String checksum.
     */
    public static String getChecksum(File file) {
        return new FastCheckSum(file).createChecksum();
    }

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

    /**
     * Creates a checksum as Hex String.
     * 
     * @return
     */
    public String createChecksum() {

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            // add the file size in order to prevent duplicate sums, where the
            // picked chunks are same
            digest.update(Primitives.toByteArray(file.length()));

            InputStream in = new FileInputStream(file);

            byte[] buffer = new byte[DEFAULT_CHUNK_SIZE];

            boolean finished = false;

            while (!finished) {

                int length = fillBuffer(in,
                        buffer);
                // if not completely filled, EOF reached
                if (length != DEFAULT_CHUNK_SIZE) {
                    finished = true;
                }

                // add read data to digest
                digest.update(buffer,
                        0,
                        length);

                // skip to the next read position
                if (!finished) {
                    in.skip(DEFAULT_SKIP_SIZE);
                }

            }
            in.close();

            return Hex.encodeHexString(digest.digest());
        } catch (NoSuchAlgorithmException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Reads from the {@link InputStream} until the buffer is completely filled
     * or the end of the stream is reached.
     * 
     * @param in
     *            the {@link InputStream} to read from
     * @param buffer
     *            the byte array to be filled
     * @return the number of read bytes. If smaller than buffer.length, the end
     *         of the stream is reached.
     * @throws IOException
     */
    private int fillBuffer(InputStream in,
            byte[] buffer) throws IOException {
        int length = buffer.length;
        int readTotal = 0;
        int read = 0;

        while (readTotal != buffer.length && read != -1) {
            read = in.read(buffer,
                    readTotal,
                    length);

            if (read != -1) {
                readTotal += read;
                length -= read;
            }
        }
        return readTotal;
    }
}
