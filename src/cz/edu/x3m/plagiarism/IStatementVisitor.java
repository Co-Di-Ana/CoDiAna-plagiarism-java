package cz.edu.x3m.plagiarism;

import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitor;
import java.util.List;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public interface IStatementVisitor extends VoidVisitor<IStatementVisitor> {

    BodyDeclaration getFunction ();



    MethodDeclaration getMethod ();



    ConstructorDeclaration getConstructor ();



    boolean isConstructor ();



    boolean isStatic ();



    int getModifiers ();



    List<StatementType> getStatements ();



    Difference compare (IStatementVisitor visitor);
}
