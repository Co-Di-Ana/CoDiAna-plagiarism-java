package cz.edu.x3m.plagiarism.visitors;

import cz.edu.x3m.plagiarism.IStatementVisitor;
import cz.edu.x3m.data.mains.ClassHolder;
import cz.edu.x3m.data.mains.IClassOrIFaceHolder;
import cz.edu.x3m.data.mains.IfaceHolder;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class CollectVisitor extends VoidVisitorAdapter<Object> implements IClassAndIfaceHolder {

    private List<ClassHolder> classes = new ArrayList<> ();
    private List<IfaceHolder> ifaces = new ArrayList<> ();



    @Override
    public List<ClassHolder> getClasses () {
        return classes;
    }



    @Override
    public List<IfaceHolder> getIfaces () {
        return ifaces;
    }



    @Override
    public void visit (ClassOrInterfaceDeclaration n, Object arg) {
        IClassOrIFaceHolder holder;
        if (n.isInterface ()) {
            holder = new IfaceHolder (n);
            ifaces.add ((IfaceHolder) holder);
        } else {
            holder = new ClassHolder (n);
            classes.add ((ClassHolder) holder);
        }

        super.visit (n, holder);


    }



    @Override
    public void visit (MethodDeclaration n, Object arg) {
        IClassOrIFaceHolder holder = (IClassOrIFaceHolder) arg;
        IStatementVisitor statementVisitor = new FunctionVisitor (n);
        holder.addMethod (statementVisitor);

        // visit method body with statement holder
        statementVisitor.visit (n, statementVisitor);
    }



    @Override
    public void visit (ConstructorDeclaration n, Object arg) {
        IClassOrIFaceHolder holder = (IClassOrIFaceHolder) arg;
        IStatementVisitor statementVisitor = new FunctionVisitor (n);
        holder.setConstructor (statementVisitor);

        // visit method body with statement holder
        statementVisitor.visit (n, statementVisitor);
    }



    @Override
    public void visit (FieldDeclaration n, Object arg) {
        IClassOrIFaceHolder holder = (IClassOrIFaceHolder) arg;
        holder.addField (n);
        super.visit (n, arg);
    }


    


    public void print () {
        for (ClassHolder classHolder : classes) {
            System.out.print (":");
            System.out.println (classHolder.getClassDeclaration ().getName ());
            for (IStatementVisitor m : classHolder.getMethods ()) {
                System.out.println (m.getFunction ());
            }
        }
        System.out.println ("\n\n");

        for (IfaceHolder ifaceHolder : ifaces) {
            System.out.print (":");
            System.out.println (ifaceHolder.getIfaceDeclaration ().getName ());
            for (IStatementVisitor m : ifaceHolder.getMethods ()) {
                System.out.println (m.getFunction ());
            }
        }
    }
}
