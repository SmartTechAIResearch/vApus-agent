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
     * @return An empty String if the jar path could not be determined.
     */
    public static String getJarParrentPath() {
        String jarPath = getJarPath(true);
        if (jarPath.length() == 0) {
            return "";
        }

        String delimiter = "" + jarPath.charAt(jarPath.length() - 1);

        File jarFile = new File(jarPath);

        String path = jarFile.getParentFile().getPath();
        if (!path.endsWith(delimiter)) {
            path += delimiter;
        }

        return path;
    }

    /**
     * Gets the path of the current executing jar, filename included. Does not end with the delimiter.
     * @return 
     */
    public static String getJarPath(){
        return getJarPath(false);
    }
    /**
     * Gets the path of the current executing jar, filename included.
     *
     * @param endWithDelimiter
     * @return An empty String if the jar path could not be determined.
     */
    public static String getJarPath(boolean endWithDelimiter) {
        try {
            CodeSource codeSource = Path.class.getProtectionDomain().getCodeSource();
            String jarPath = codeSource.getLocation().toURI().getPath();
            
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
     * @return An empty String if the jar path could not be determined.
     */
    public static String getDelimiter() {
        String jarPath = getJarPath(true);
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
