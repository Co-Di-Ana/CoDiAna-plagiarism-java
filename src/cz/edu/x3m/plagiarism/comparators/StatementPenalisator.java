package cz.edu.x3m.plagiarism.comparators;

import cz.edu.x3m.plagiarism.CompareLibrary;
import cz.edu.x3m.plagiarism.Penalisator;
import cz.edu.x3m.plagiarism.StatementType;
import cz.edu.x3m.plagiarism.StatementTypePenalty;

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
        return o1 == o2 ? CompareLibrary.PENALTY_NONE : PENALTY.getPenalty (o1, o2);
    }



    public static StatementPenalisator get () {
        return instance == null ? instance = new StatementPenalisator () : instance;
    }
}
