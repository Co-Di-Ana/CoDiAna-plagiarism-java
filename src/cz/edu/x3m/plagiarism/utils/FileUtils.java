package cz.edu.x3m.plagiarism.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *  @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class FileUtils {

    public static List<File> getAllFiles (File directory, FilenameFilter filter) {
        List<File> result = new ArrayList<> ();
        getAllFiles (result, directory, filter);
        return result;
    }



    private static void getAllFiles (List<File> result, File directory, FilenameFilter filter) {
        Collections.addAll (result, directory.listFiles (filter));

        for (File file : directory.listFiles ()) {
            if (file.isDirectory ())
                getAllFiles (result, file, filter);
        }
    }



    public static JavaExtensionFilter getJavaExtensionFilter () {
        return new JavaExtensionFilter ();
    }
}

class JavaExtensionFilter implements FilenameFilter {

    private static final String JAVA = ".java";



    @Override
    public boolean accept (File dir, String name) {
        return name.endsWith (JAVA);
    }
}