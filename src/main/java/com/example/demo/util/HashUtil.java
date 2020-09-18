package com.example.demo.util;
import java.util.zip.CRC32;

/**
 * @author ww
 * @version 1.0
 * @date 2020/9/17 10:01
 */

public class HashUtil {
    public static long crc32Code(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        return crc32.getValue();
    }
}