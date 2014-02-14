package cz.edu.x3m.plagiarism.comparators;

import cz.edu.x3m.plagiarism.CompareLibrary;
import cz.edu.x3m.plagiarism.Penalisator;
import japa.parser.ast.body.Parameter;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class ParameterPenalisator implements Penalisator<Parameter> {

    private static ParameterPenalisator instance;



    private ParameterPenalisator () {
    }



    @Override
    public int penalise (Parameter o1, Parameter o2) {
        boolean areSame = o1.getType ().toString ().equals (o2.getType ().toString ());
        return areSame ? CompareLibrary.PENALTY_NONE : CompareLibrary.PENALTY_SUBSTITUTION;
    }



    public static ParameterPenalisator get () {
        return instance == null ? instance = new ParameterPenalisator () : instance;
    }
}
