/**
 * 
 */
package de.wollistik.dlna.files;

import java.nio.ByteBuffer;

/**
 * @author Wolfgang Wedelich-John<wolfgang.wedelich@wollistik.de>
 *
 */
public class Primitives {

    /**
     * Converts a long to a 8 byte byte array.
     * 
     * @param value
     *            the long value
     * @return the 8 byte array
     */
    public static byte[] toByteArray(long value) {
        return ByteBuffer.allocate(8).putLong(value).array();
    }

}
