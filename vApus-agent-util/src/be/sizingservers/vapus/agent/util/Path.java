/*
 * Copyright 2014 (c) Sizing Servers Lab
 * University College of West-Flanders, Department GKG * 
 * Author(s):
 * 	Dieter Vandroemme
 */
package be.sizingservers.vapus.agent.util;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

/**
 *
 * @author didjeeh
 */
public class Path {

    /**
     * Gets the path of the current executing jar, filename excluded. Ends with
     * a / or a \ (OS specific).
     *
     * @param cls
     * @return An empty String if the jar path could not be determined.
     */
    public static String getJarParrentPath(Class cls) {
        String jarPath = getJarPath(cls, true);
        if (jarPath.length() == 0) {
            return "";
        }

        String delimiter = "" + jarPath.charAt(jarPath.length() - 1);
        
        String path = jarPath;

        if (path.endsWith(".jar" + delimiter)) {
            File jarFile = new File(jarPath);
            path = jarFile.getParentFile().getAbsolutePath();
        }
        
        if (!path.endsWith(delimiter)) {
            path += delimiter;
        }

        return path;
    }

    /**
     * Gets the path of the current executing jar, filename included. Does not
     * end with the delimiter.
     *
     * @param cls
     * @return
     */
    public static String getJarPath(Class cls) {
        return getJarPath(cls, false);
    }

    /**
     * Gets the path of the current executing jar, filename included.
     *
     * @param cls
     * @param endWithDelimiter
     * @return An empty String if the jar path could not be determined.
     */
    public static String getJarPath(Class cls, boolean endWithDelimiter) {
        try {
            CodeSource codeSource = cls.getProtectionDomain().getCodeSource();

            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarPath = jarFile.getAbsolutePath();

            if (endWithDelimiter) {
                String delimiter = extractDelimiter(jarPath);
                if (!jarPath.endsWith(delimiter)) {
                    jarPath += delimiter;
                }
            }
            return jarPath;
        } catch (URISyntaxException ex) {
            //Ignore
        }
        return "";
    }

    /**
     * Gets the OS specific path delimiter.
     *
     * @param cls
     * @return An empty String if the jar path could not be determined.
     */
    public static String getDelimiter(Class cls) {
        String jarPath = getJarPath(cls, true);
        return "" + jarPath.charAt(jarPath.length() - 1);

    }

    /**
     *
     * @param path
     * @return An empty String if the given path is empty.
     */
    public static String extractDelimiter(String path) {
        if (path.length() == 0) {
            return "";
        }

        String delimiter = "/";
        if (path.contains("\\")) {
            delimiter = "\\";
        }

        return delimiter;
    }

}
