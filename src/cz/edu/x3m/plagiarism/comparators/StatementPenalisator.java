package cz.edu.x3m.plagiarism.comparators;

import cz.edu.x3m.plagiarism.JavaComparator;
import cz.edu.x3m.plagiarism.utils.Penalisator;
import cz.edu.x3m.plagiarism.statements.StatementType;
import cz.edu.x3m.plagiarism.statements.StatementTypePenalty;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class StatementPenalisator implements Penalisator<StatementType> {

    private static StatementPenalisator instance;
    private static final StatementTypePenalty PENALTY = new StatementTypePenalty ();



    private StatementPenalisator () {
    }



    @Override
    public int penalise (StatementType o1, StatementType o2) {
        return o1 == o2 ? JavaComparator.PENALTY_NONE : PENALTY.getPenalty (o1, o2);
    }



    public static StatementPenalisator get () {
        return instance == null ? instance = new StatementPenalisator () : instance;
    }
}
