package cz.edu.x3m.plagiarism;

import java.lang.reflect.Modifier;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class ModifiersUtil {

    private static final int FIELDS_MAX = 1/*access*/ + 1/*static*/ + 1/*final*/;
    private static final int ACCESS = Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC;



    public static Difference compareFields (int a, int b) {
        int diff = compareAccess (a, b);
        diff += (a & Modifier.STATIC) == (b & Modifier.STATIC) ? 0 : 1;
        diff += (a & Modifier.FINAL) == (b & Modifier.FINAL) ? 0 : 1;
        return new Difference (diff, FIELDS_MAX);
    }



    public static int compareAccess (int a, int b) {
        a &= ACCESS;
        b &= ACCESS;
        return a == b ? 0 : 1;
    }
}
