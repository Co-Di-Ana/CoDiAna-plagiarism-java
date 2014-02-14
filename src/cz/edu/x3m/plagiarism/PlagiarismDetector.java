package cz.edu.x3m.plagiarism;

import cz.edu.x3m.data.mains.ClassHolder;
import cz.edu.x3m.plagiarism.visitors.CollectVisitor;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import java.io.File;
import java.io.IOException;

/**
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class PlagiarismDetector {

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) throws Exception {
        new PlagiarismDetector ();
    }



    /**
     * Creates new instance of plagiarism class
     */
    public PlagiarismDetector () throws Exception {
        File f0 = new File ("Main.java");
        File f1 = new File ("Main1.java");

        CollectVisitor visitor0 = collect (f0);
        CollectVisitor visitor1 = collect (f1);


        Difference d = CompareLibrary.compareAll (visitor0, visitor1);
        System.out.format ("%1.2f%%%n", 100 * d.getIdenticalLikelihood ());
    }



    public static CollectVisitor collect (String path) throws ParseException, IOException {
        return collect (new File (path));
    }



    public static CollectVisitor collect (File file) throws ParseException, IOException {
        CompilationUnit unit = JavaParser.parse (file);
        CollectVisitor visitor = new CollectVisitor ();
        unit.accept (visitor, unit);
        return visitor;
    }
}
