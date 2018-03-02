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

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * Apr 18, 2017 5:25:30 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class InetUtils {

    private InetUtils() {
    }

    public static String getInetATON(Long value) {
        if (value == null) {
            return null;
        }

        try {
            return toInetATON(value);
        } catch (UnknownHostException exception) {
            return null;
        }
    }

    public static Long getInetNTOA(String host) {
        if (host == null) {
            return null;
        }

        try {
            return toInetNTOA(host);
        } catch (UnknownHostException exception) {
            return null;
        }
    }

    /**
     * Implementation of MySQL INET_NTOA function. Given a numeric IPv4 network
     * address in network byte order, returns the dotted-quad string
     * representation of the address as a nonbinary string in the connection
     * character set.
     * http://dev.mysql.com/doc/refman/5.6/en/miscellaneous-functions.html#function_inet-ntoa
     *
     * @param host
     * @return
     * @throws UnknownHostException when the IP address of a host could not be
     * determined
     */
    public static long toInetNTOA(String host) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(host);
        byte[] address = inetAddress.getAddress();
        double base = 256.0;
        double power = address.length;
        long result = 0;
        for (byte b : address) {
            result += ((b & 0xFF) % base * Math.pow(base, --power));
        }

        return result;
    }

    /**
     * Implementation of MySQL INET_ATON function. Given the dotted-quad
     * representation of an IPv4 network address as a string, returns an integer
     * that represents the numeric value of the address in network byte order
     * (big endian).
     * http://dev.mysql.com/doc/refman/5.6/en/miscellaneous-functions.html#function_inet-aton
     *
     * @param value
     * @return
     * @throws UnknownHostException when he IP address of a host could not be
     * determined
     */
    public static String toInetATON(long value) throws UnknownHostException {
        byte[] addr = {
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value
        };

        return InetAddress.getByAddress(addr).getHostAddress();
    }

}
