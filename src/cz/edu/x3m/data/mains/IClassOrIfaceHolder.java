package cz.edu.x3m.data.mains;

import cz.edu.x3m.plagiarism.statements.IStatementVisitor;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import java.util.List;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public interface IClassOrIfaceHolder {

    void addMethod (IStatementVisitor n);



    void setConstructor (IStatementVisitor n);



    void addField (FieldDeclaration n);



    IStatementVisitor getConstructor ();



    List<IStatementVisitor> getMethods ();



    List<FieldDeclaration> getFields ();



    ClassOrInterfaceDeclaration getClassOrIface ();
}
