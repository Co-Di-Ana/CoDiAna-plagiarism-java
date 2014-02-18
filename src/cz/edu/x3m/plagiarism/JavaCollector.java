package cz.edu.x3m.plagiarism;

import cz.edu.x3m.plagiarism.visitors.CollectVisitor;
import cz.edu.x3m.plagiarism.visitors.IClassAndIfaceHolder;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import java.io.File;
import java.util.List;

/**
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class JavaCollector implements ICollector {

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) throws Exception {
        new JavaCollector ();
    }



    /**
     * Creates new instance of JavaCollector class
     */
    public JavaCollector () {
    }



    @Override
    public IClassAndIfaceHolder collect (String path) throws Exception {
        return collect (new File (path));
    }



    @Override
    public IClassAndIfaceHolder collect (File file) throws Exception {
        return collect (new CollectVisitor (), file);
    }



    @Override
    public IClassAndIfaceHolder collect (List<File> files) throws Exception {
        CollectVisitor visitor = new CollectVisitor ();
        for (File file : files)
            collect (visitor, file);
        return visitor;
    }



    private IClassAndIfaceHolder collect (CollectVisitor visitor, File file) throws Exception {
        CompilationUnit unit = JavaParser.parse (file);
        unit.accept (visitor, unit);
        return visitor;
    }
}
