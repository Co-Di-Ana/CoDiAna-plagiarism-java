package cz.edu.x3m.plagiarism.statements;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class StatementRule {

    private final StatementGroup left;
    private final StatementGroup right;
    private final double penalty;



    public StatementRule (StatementGroup left, StatementGroup right, double penalty) {
        this.left = left;
        this.right = right;
        this.penalty = penalty;
    }



    public StatementGroup getLeft () {
        return left;
    }



    public StatementGroup getRight () {
        return right;
    }



    public double getPenalty () {
        return penalty;
    }



    public static StatementRule create (StatementGroup left, StatementGroup right, double penalty) {
        return new StatementRule (left, right, penalty);
    }



    public static StatementRule create (StatementType left, StatementType right, double penalty) {
        return new StatementRule (new StatementGroup (left), new StatementGroup (right), penalty);
    }
}
