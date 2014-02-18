package cz.edu.x3m.plagiarism.utils;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class Strings {

    public static String toString (Object o) {
        return toString (o, "");
    }



    public static String toString (Object o, String defaultValue) {
        return o == null ? defaultValue : o.toString ();
    }
}
