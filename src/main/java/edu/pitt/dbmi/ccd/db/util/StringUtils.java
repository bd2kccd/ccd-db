package edu.pitt.dbmi.ccd.db.util;

/**
 * @author Mark Silvis (marksilvis2pitt.edu)
 */
public abstract class StringUtils {
    
    /**
     * Returns true for Object that is either null or the empty string
     * False for any Object that is neither null nor the empty string
     * @param  str object
     */
    public static boolean isNullOrEmpty(Object str) {
        return (str == null || "".equals(str));
    }
}