package cz.edu.x3m.plagiarism.visitors;

import cz.edu.x3m.plagiarism.JavaComparator;
import cz.edu.x3m.plagiarism.Difference;
import cz.edu.x3m.plagiarism.statements.IStatementVisitor;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class FunctionVisitor extends StatementVisitor {

    protected BodyDeclaration function;
    protected MethodDeclaration method;
    protected ConstructorDeclaration constructor;
    protected boolean isContructor;



    protected FunctionVisitor (BodyDeclaration function, boolean isContructor) {
        this.function = function;
        this.isContructor = isContructor;
        if (isContructor) {
            constructor = (ConstructorDeclaration) function;
            method = null;
        } else {
            constructor = null;
            method = (MethodDeclaration) function;
        }
    }



    public FunctionVisitor (MethodDeclaration function) {
        this (function, false);
    }



    public FunctionVisitor (ConstructorDeclaration function) {
        this (function, true);
    }



    @Override
    public BodyDeclaration getFunction () {
        return function;
    }



    @Override
    public ConstructorDeclaration getConstructor () {
        return constructor;
    }



    @Override
    public MethodDeclaration getMethod () {
        return method;
    }



    @Override
    public boolean isConstructor () {
        return isContructor;
    }



    @Override
    public boolean isStatic () {
        return ModifierSet.isStatic (isContructor ? constructor.getModifiers () : method.getModifiers ());
    }



    @Override
    public int getModifiers () {
        return isContructor ? constructor.getModifiers () : method.getModifiers ();
    }



    @Override
    public Difference compare (IStatementVisitor that) {
        return JavaComparator.compareFunction (this, that);
    }
}
