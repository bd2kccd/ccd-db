package edu.pitt.dbmi.ccd.db.util;

/**
 * @author Mark Silvis (marksilvis2pitt.edu)
 */
public abstract class StringUtils {
    
    /**
     * Returns true for any non-null, non-empty string
     * False for any Object that is null, empty, or not a String
     * @param  str string
     */
    public static boolean isNullOrEmpty(Object str) {
        return (str == null || "".equals(str));
    }
}