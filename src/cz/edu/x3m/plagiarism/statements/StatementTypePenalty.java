package cz.edu.x3m.plagiarism.statements;

import cz.edu.x3m.plagiarism.JavaComparator;
import java.util.HashMap;
import java.util.Map;
import static cz.edu.x3m.plagiarism.statements.StatementType.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class StatementTypePenalty {

//    private final List<StatementRule> rules = new ArrayList<> ();
//
//
//
//    public StatementTypePenalty () {
//        final int PEN = CompareLibrary.PENALTY_SUBSTITUTION;
//        addPenalty (
//                group (For),
//                group (Foreach),
//                0.25 * PEN);
//        
//        addPenalty (
//                group (If, Return, Return),
//                group (Return, Ternal),
//                0.10 * PEN);
//    }
//
//
//
//    private void addPenalty (StatementType a, StatementType b, double p) {
//        rules.add (StatementRule.create (a, b, p));
//    }
//
//
//
//    private void addPenalty (StatementGroup a, StatementGroup b, double p) {
//        rules.add (StatementRule.create (a, b, p));
//    }
//
//
//
//    private StatementGroup group (StatementType... stmts) {
//        return StatementGroup.create (stmts);
//    }
    private final Map<Integer, Integer> penalties = new HashMap<> ();



    public StatementTypePenalty () {
        setPenalty (For, Foreach, 0.25 * JavaComparator.PENALTY_SUBSTITUTION);
        setPenalty (If, Ternal, 0.25 * JavaComparator.PENALTY_SUBSTITUTION);
    }



    private void setPenalty (StatementType s1, StatementType s2, double penalty) {
        penalties.put (s1.getValue () * s2.getValue (), (int) penalty);
    }



    public int getPenalty (StatementType s1, StatementType s2) {
        int key = getID (s1, s2);
        return penalties.containsKey (key) ? penalties.get (key) : JavaComparator.PENALTY_SUBSTITUTION;
    }



    private int getID (StatementType... stmts) {
        int result = 1;
        for (StatementType s : stmts)
            result *= s.getValue ();
        return result;
    }
}
