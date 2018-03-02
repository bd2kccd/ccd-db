/*
 * Copyright (C) 2018 University of Pittsburgh.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package edu.pitt.dbmi.ccd.db.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * Feb 15, 2018 2:08:46 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class FileUtils {

    public FileUtils() {
    }

    public static String computeMD5Hash(Path file) throws IOException, NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(file)) {
            DigestInputStream dis = new DigestInputStream(is, messageDigest);
            dis.on(true); // no need to call md.update(buffer) when set ON
            byte[] buffer = new byte[8192];
            while (dis.read(buffer) != -1) {
                // a call to the read method results in an update on the message digest
            }

            byte[] digest = messageDigest.digest();
            for (byte b : digest) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
        }

        return sb.toString();
    }

}
