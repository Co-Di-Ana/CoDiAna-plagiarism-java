package cz.edu.x3m.plagiarism.comparators;

import cz.edu.x3m.plagiarism.JavaComparator;
import cz.edu.x3m.plagiarism.Difference;
import cz.edu.x3m.plagiarism.utils.ModifiersUtil;
import cz.edu.x3m.plagiarism.utils.Penalisator;
import japa.parser.ast.body.FieldDeclaration;
import java.lang.reflect.Modifier;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class FieldPenalisator implements Penalisator<FieldDeclaration> {

    private static FieldPenalisator instance;
    private static final int MODS_WEIGHT = (int) (JavaComparator.PENALTY_SUBSTITUTION * 0.30);
    private static final int TYPE_WEIGHT = (int) (JavaComparator.PENALTY_SUBSTITUTION * 0.70);



    private FieldPenalisator () {
    }



    @Override
    public int penalise (FieldDeclaration o1, FieldDeclaration o2) {
        Difference d1 = ModifiersUtil.compareFields (o1.getModifiers (), o2.getModifiers ()).balance (MODS_WEIGHT);
        Difference d2 = new Difference (o1.getType ().toString ().equals (o2.getType ().toString ()) ? 0 : 1, 1).balance (TYPE_WEIGHT);
        double result = d1.add (d2).getValue ();
        return (int) result;
    }



    public static FieldPenalisator get () {
        return instance == null ? instance = new FieldPenalisator () : instance;
    }
}
