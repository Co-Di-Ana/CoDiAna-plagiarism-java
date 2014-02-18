package cz.edu.x3m.plagiarism.statements;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public enum StatementType {

    Annotation, Assert, Block, Break, Continue, Do, Empty,
    Foreach, For, If, Labeled, Return, SwitchEntry, Switch,
    Synchronized, Throw, Try, TypeDeclaration, While, Catch,
    ExplicitConstructorInvocation, End,
    ArrayAccess, ArrayCreation, Assign, Ternal, VariableDeclaration;
    private int value;



    public int getValue () {
        return value;
    }



    private StatementType () {
        value = StatementTypeHelper.getNext ();
    }

    static class StatementTypeHelper {

        private static int counter = 0;
        private static int[] primes = new int[]{
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,
            41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83,
            89, 97, 101, 103, 107, 109, 113, 127, 131,
            137, 139, 149, 151, 157, 163, 167, 173
        };



        private static int getNext () {
            return primes[counter++];
        }
    }
}

/*
 
    //107 109 113 127 131 137 139 149 151 157 163 167 173
 */