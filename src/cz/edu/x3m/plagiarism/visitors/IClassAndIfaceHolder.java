package cz.edu.x3m.plagiarism.visitors;

import cz.edu.x3m.data.mains.ClassHolder;
import cz.edu.x3m.data.mains.IfaceHolder;
import java.util.List;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public interface IClassAndIfaceHolder {

    List<ClassHolder> getClasses ();



    List<IfaceHolder> getIfaces ();
}
