package cz.edu.x3m.plagiarism;

import cz.edu.x3m.plagiarism.visitors.IClassAndIfaceHolder;
import java.io.File;
import java.util.List;

/**
 *
 *  @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public interface ICollector {

    public IClassAndIfaceHolder collect (String path) throws Exception;



    public IClassAndIfaceHolder collect (File file) throws Exception;



    public IClassAndIfaceHolder collect (List<File> files) throws Exception;
}
