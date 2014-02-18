package cz.edu.x3m.data.mains;

import cz.edu.x3m.plagiarism.statements.IStatementVisitor;
import japa.parser.ast.body.FieldDeclaration;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
abstract public class AbstractHolder implements IClassOrIfaceHolder {

    protected IStatementVisitor constructor = null;
    protected List<IStatementVisitor> methods = new ArrayList<> ();
    protected List<FieldDeclaration> fields = new ArrayList<> ();



    @Override
    public void setConstructor (IStatementVisitor constructor) {
        this.constructor = constructor;
        addMethod (constructor);
    }



    @Override
    public IStatementVisitor getConstructor () {
        return constructor;
    }



    @Override
    public List<IStatementVisitor> getMethods () {
        return methods;
    }



    @Override
    public List<FieldDeclaration> getFields () {
        return fields;
    }



    @Override
    public void addMethod (IStatementVisitor n) {
        methods.add (n);
    }



    @Override
    public void addField (FieldDeclaration n) {
        fields.add (n);
    }
}
