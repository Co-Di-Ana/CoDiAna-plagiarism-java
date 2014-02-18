package cz.edu.x3m.plagiarism.visitors;

import cz.edu.x3m.plagiarism.statements.IStatementVisitor;
import cz.edu.x3m.plagiarism.statements.StatementType;
import japa.parser.ast.body.*;
import japa.parser.ast.expr.*;
import japa.parser.ast.stmt.*;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public abstract class StatementVisitor extends VoidVisitorAdapter<IStatementVisitor> implements IStatementVisitor {

    private static final boolean VERBOSE = false;
    protected List<StatementType> statements = new ArrayList<> ();



    @Override
    public List<StatementType> getStatements () {
        return statements;
    }


    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Override
    public void visit (ArrayAccessExpr n, IStatementVisitor arg) {
        add (StatementType.ArrayAccess);
        super.visit (n, arg);
    }



    @Override
    public void visit (ArrayCreationExpr n, IStatementVisitor arg) {
        add (StatementType.ArrayCreation);
        super.visit (n, arg);
    }



    @Override
    public void visit (AssignExpr n, IStatementVisitor arg) {
        add (StatementType.Assign);
        super.visit (n, arg);
    }



    @Override
    public void visit (ConditionalExpr n, IStatementVisitor arg) {
        add (StatementType.Ternal);
        super.visit (n, arg);
    }



    @Override
    public void visit (VariableDeclarationExpr n, IStatementVisitor arg) {
        add (StatementType.VariableDeclaration);
        super.visit (n, arg);
    }



    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------- 
    @Override
    public void visit (AnnotationDeclaration n, IStatementVisitor arg) {
        add (StatementType.Annotation);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (AssertStmt n, IStatementVisitor arg) {
        add (StatementType.Assert);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (BreakStmt n, IStatementVisitor arg) {
        add (StatementType.Break);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (ContinueStmt n, IStatementVisitor arg) {
        add (StatementType.Continue);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (DoStmt n, IStatementVisitor arg) {
        add (StatementType.Do);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (EmptyStmt n, IStatementVisitor arg) {
        add (StatementType.Empty);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (ExplicitConstructorInvocationStmt n, IStatementVisitor arg) {
        add (StatementType.ExplicitConstructorInvocation);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (ForeachStmt n, IStatementVisitor arg) {
        add (StatementType.Foreach);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (ForStmt n, IStatementVisitor arg) {
        add (StatementType.For);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (IfStmt n, IStatementVisitor arg) {
        add (StatementType.If);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (LabeledStmt n, IStatementVisitor arg) {
        add (StatementType.Labeled);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (ReturnStmt n, IStatementVisitor arg) {
        add (StatementType.Return);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (SwitchEntryStmt n, IStatementVisitor arg) {
        add (StatementType.SwitchEntry);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (SwitchStmt n, IStatementVisitor arg) {
        add (StatementType.Switch);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (SynchronizedStmt n, IStatementVisitor arg) {
        add (StatementType.Synchronized);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (ThrowStmt n, IStatementVisitor arg) {
        add (StatementType.Throw);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (TryStmt n, IStatementVisitor arg) {
        add (StatementType.Try);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (TypeDeclarationStmt n, IStatementVisitor arg) {
        add (StatementType.TypeDeclaration);
        super.visit (n, arg);
        add (StatementType.End);
    }



    @Override
    public void visit (WhileStmt n, IStatementVisitor arg) {
        add (StatementType.While);
        super.visit (n, arg);
        add (StatementType.End);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------



    @Override
    public void visit (CatchClause n, IStatementVisitor arg) {
        add (StatementType.Catch);
        super.visit (n, arg);
        add (StatementType.End);
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------


    private void add (StatementType value) {
        if (value == StatementType.End && VERBOSE == false)
            return;
        statements.add (value);
    }
}
