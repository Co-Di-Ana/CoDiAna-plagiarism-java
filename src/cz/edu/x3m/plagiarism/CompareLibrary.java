package cz.edu.x3m.plagiarism;

import cz.edu.x3m.data.mains.AbstractHolder;
import cz.edu.x3m.data.mains.ClassHolder;
import cz.edu.x3m.data.mains.IfaceHolder;
import cz.edu.x3m.plagiarism.comparators.FieldPenalisator;
import cz.edu.x3m.plagiarism.comparators.ParameterPenalisator;
import cz.edu.x3m.plagiarism.comparators.StatementPenalisator;
import cz.edu.x3m.plagiarism.visitors.IClassAndIfaceHolder;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.Parameter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class CompareLibrary {
    
    private static final int FUNCTION_BALANCE_UNIT = 100;
    private static final int FUNCTION_HEAD_UNIT = 30;
    private static final int FUNCTION_BODY_UNIT = 70;
    //
    public static final int PENALTY_MAXIMUM = 100;
    public static final int PENALTY_INSERTION = 100;
    public static final int PENALTY_DELETION = 100;
    public static final int PENALTY_SUBSTITUTION = 80;
    public static final int PENALTY_NONE = 0;
    //
    private static final boolean DEBUG = true;
    
    
    
    public static Difference compareFunction (IStatementVisitor a, IStatementVisitor b) {
        Difference modsDiff, statDiff, consDiff, commDiff, prmsDiff;
        Difference head, body;
        List<Parameter> aParams, bParams;
        
        double value = 0;
        double maxim = 0;

        // modifiers
        value = Integer.bitCount (a.getModifiers () ^ b.getModifiers ());
        maxim = Integer.bitCount (Modifier.methodModifiers () ^ 0);
        modsDiff = new Difference (value, maxim);

        // static get extra point
        value = a.isStatic () == b.isStatic () ? 0 : 1;
        maxim = 1;
        statDiff = new Difference (value, maxim);

        // constructor get extra point
        value = a.isConstructor () == b.isConstructor () ? 0 : 1;
        maxim = 1;
        consDiff = new Difference (value, maxim);

        // params
        aParams = a.isConstructor () ? a.getConstructor ().getParameters () : a.getMethod ().getParameters ();
        bParams = b.isConstructor () ? b.getConstructor ().getParameters () : b.getMethod ().getParameters ();
        prmsDiff = distance (aParams, bParams, ParameterPenalisator.get ());

        // comments
        JavadocComment c1 = a.getFunction ().getJavaDoc ();
        JavadocComment c2 = b.getFunction ().getJavaDoc ();
        commDiff = compareString (Strings.toString (c1), Strings.toString (c2));


        // statements
        Difference statementsResult = distance (a.getStatements (), b.getStatements (), StatementPenalisator.get ());


        // balance
        modsDiff = modsDiff.balance (30);
        statDiff = statDiff.balance (25);
        consDiff = consDiff.balance (10);
        commDiff = commDiff.balance (10);
        prmsDiff = prmsDiff.balance (40);


        // method/constructor head and body
        head = new Difference (modsDiff, statDiff, consDiff, commDiff, prmsDiff);
        body = new Difference (statementsResult);

        // balance
        // every statement gets 100 points
        // function head will recieve from 150 to 1000 points depending on body size
        // 150 will be awarded to 1 body statement and 1000 point for 15 and more statements
        final int stmts = (int) (statementsResult.getMax () / PENALTY_MAXIMUM);
        int balance = stmts * 60 + 90;
        balance = balance < 150 ? 150 : balance > 1000 ? 1000 : balance;
        head = head.balance (balance);
        
        
        return head.add (body);
    }
    
    
    
    public static Difference compareString (String a, String b, int maxim) {
        return compareString (a, b).balance (maxim);
    }
    
    
    
    public static Difference compareString (String s1, String s2) {
        if (s1 == null)
            s1 = "";
        if (s2 == null)
            s2 = "";

        // check preconditions
        int maxim = Math.max (s1.length (), s2.length ());
        int value = StringUtils.getLevenshteinDistance (s1, s2);
        value = value > maxim ? maxim : value;
        
        return new Difference (value, maxim);
    }
    
    
    
    public static Difference compareAll (IClassAndIfaceHolder a, IClassAndIfaceHolder b) {
        ClassHolder classBestMatch = null;
        IfaceHolder ifaceBestMatch = null;
        Difference classDiff = null, ifaceDiff = null;
        Difference classBest = null, ifaceBest = null;
        Difference classResult = Difference.empty ();
        Difference ifaceResult = Difference.empty ();

        // go through all classes
        for (ClassHolder c0 : a.getClasses ()) {
            for (ClassHolder c1 : b.getClasses ()) {
                // compare classes
                classDiff = compareClassOrIface (c0, c1);

                // compare results
                if (classDiff.hasLowerDifference (classBest)) {
                    classBest = classDiff;
                    classBestMatch = c1;
                }
                if (classDiff.isIdentical ()) {
                    break;
                }
            }
            classResult = classResult.add (classBest);
            if (DEBUG) {
                System.out.println ("::::::::::::::::::::::");
                System.out.println (classBest);
                System.out.println ("::::::::::::::::::::::");
            }
            classBest = null;
        }


        // go through all ifaces
        for (IfaceHolder c0 : a.getIfaces ()) {
            for (IfaceHolder c1 : b.getIfaces ()) {
                // compare classes
                ifaceDiff = compareClassOrIface (c0, c1);

                // compare results
                if (ifaceDiff.hasLowerDifference (ifaceBest)) {
                    ifaceBest = ifaceDiff;
                    ifaceBestMatch = c1;
                }
                if (ifaceDiff.isIdentical ()) {
                    break;
                }
            }
            ifaceResult = ifaceResult.add (ifaceBest);
            if (DEBUG) {
                System.out.println ("**********************");
                System.out.println (ifaceBest);
                System.out.println ("**********************");
            }
            classBest = null;
        }
        
        return classResult.balance (100);
    }
    
    
    
    public static Difference compareClassOrIface (AbstractHolder a, AbstractHolder b) {
        IStatementVisitor bestMatch = null;
        Difference methodDiff = null;
        Difference best = null;
        Difference result = Difference.empty ();
        Difference head = Difference.empty ();
        Difference modsDiff, commDiff, fldsDiff;
        int value, maxim;


        // modifiers
        value = Integer.bitCount (a.getClassOrIface ().getModifiers () ^ b.getClassOrIface ().getModifiers ());
        maxim = Integer.bitCount (Modifier.classModifiers () ^ 0);
        modsDiff = new Difference (value, maxim);

        // javadoc
        JavadocComment j1 = a.getClassOrIface ().getJavaDoc ();
        JavadocComment j2 = b.getClassOrIface ().getJavaDoc ();
        commDiff = compareString (Strings.toString (j1), Strings.toString (j2));

        // fields
        fldsDiff = distance (a.getFields (), b.getFields (), FieldPenalisator.get ());
        
        for (IStatementVisitor c0 : a.getMethods ()) {
            for (IStatementVisitor c1 : b.getMethods ()) {
                // compare classes
                methodDiff = compareFunction (c0, c1);

                // compare results
                if (methodDiff.hasLowerDifference (best)) {
                    best = methodDiff;
                    bestMatch = c1;
                }
                if (methodDiff.isIdentical ()) {
                    break;
                }
            }
            result = result.add (best);
            if (DEBUG) {
                System.out.println ("----------------------");
                System.out.println (best);
                System.out.println (bestMatch.getFunction ());
                System.out.println (bestMatch.getStatements ());
                System.out.println (c0.getFunction ());
                System.out.println (c0.getStatements ());
                System.out.println ("----------------------");
            }
            best = null;
        }
        
        modsDiff = modsDiff.balance(FUNCTION_BALANCE_UNIT);
        commDiff = commDiff.balance(FUNCTION_BALANCE_UNIT);
        head = head.add (modsDiff).add (commDiff).add (fldsDiff);
        result = result.add (head);
        
        return result;
    }
    //----------------------------------------------------------------------------------------------

    
    
    private static int minOfThreeNumbers (int num1, int num2, int num3) {
        return Math.min (num1, Math.min (num2, num3));
    }
    
    

    private static <T> Difference distance (List<T> s1, List<T> s2, Penalisator<T> penalisator) {
        if (s1 == null)
            s1 = new ArrayList<T> ();
        if (s2 == null)
            s2 = new ArrayList<T> ();
        // check preconditions
        int m = s1.size ();
        int n = s2.size ();

        //empty comparation
        if (n == m && n == 0)
            return Difference.empty ();
        
        int maxim = Math.max (m, n) * PENALTY_MAXIMUM;
        if (m == 0) {
            // some simple heuristics
            return new Difference (n * PENALTY_MAXIMUM, maxim);
        } else if (n == 0) {
            // some simple heuristics
            return new Difference (m * PENALTY_MAXIMUM, maxim);
        } else if (m > n) {
            // swap m with n to get O(min(m, n)) space
            List<T> tempList = s1;
            s1 = s2;
            s2 = tempList;
            // also swap sizes
            int tempInt = m;
            m = n;
            n = tempInt;
        }

        // Instead of a 2d array of space O(m*n) such as int d[][] = new int[m +
        // 1][n + 1], only the previous row and current row need to be stored at
        // any one time in prevD[] and currD[]. This reduces the space
        // complexity to O(min(m, n)).
        int prevD[] = new int[n + 1];
        int currD[] = new int[n + 1];
        // temporary pointer for swapping
        int temp[];

        // the distance of any second string to an empty first string
        for (int j = 0; j < n + 1; j++) {
            prevD[j] = (j) * PENALTY_INSERTION;
        }

        // for each row in the distance matrix
        T statement1, statement2;
        for (int i = 0; i < m; i++) {

            // the distance of any first string to an empty second string
            currD[0] = (i + 1) * PENALTY_INSERTION;
            statement1 = s1.get (i);

            // for each column in the distance matrix
            for (int j = 1; j <= n; j++) {
                
                statement2 = s2.get (j - 1);
                final int penalty = penalisator.penalise (statement1, statement2);
                if (penalty == 0) {
                    currD[j] = prevD[j - 1];
                } else {
                    currD[j] = minOfThreeNumbers (
                            prevD[j - 0] + PENALTY_DELETION,
                            currD[j - 1] + PENALTY_INSERTION,
                            prevD[j - 1] + penalty);
                }
                
            }
            
            temp = prevD;
            prevD = currD;
            currD = temp;
            
        }

        // after swapping, the final answer is now in the last column of prevD
        int value = prevD[prevD.length - 1];
        value = value > maxim ? maxim : value;
        
        return new Difference (value, maxim);
    }
    /*
     
     public static Difference compareString (String s1, String s2) {
     if (s1 == null)
     s1 = "";
     if (s2 == null)
     s2 = "";
     // check preconditions
     int m = s1.length ();
     int n = s2.length ();
     int maxim = Math.max (m, n) * PENALTY_MAXIMUM;
     if (m == 0) {
     // some simple heuristics
     return new Difference (n * PENALTY_MAXIMUM, maxim);
     } else if (n == 0) {
     // some simple heuristics
     return new Difference (m * PENALTY_MAXIMUM, maxim);
     } else if (m > n) {
     // swap m with n to get O(min(m, n)) space
     String tempString = s1;
     s1 = s2;
     s2 = tempString;
     // also swap lengths
     int tempInt = m;
     m = n;
     n = tempInt;
     }

     // normalize case
     s1 = s1.toUpperCase ();
     s2 = s2.toUpperCase ();

     // Instead of a 2d array of space O(m*n) such as int d[][] = new int[m +
     // 1][n + 1], only the previous row and current row need to be stored at
     // any one time in prevD[] and currD[]. This reduces the space
     // complexity to O(min(m, n)).
     int prevD[] = new int[n + 1];
     int currD[] = new int[n + 1];
     int temp[]; // temporary pointer for swapping

     // the distance of any second string to an empty first string
     for (int j = 0; j < n + 1; j++)
     prevD[j] = (j) * PENALTY_INSERTION;

     // for each row in the distance matrix
     for (int i = 0; i < m; i++) {

     // the distance of any first string to an empty second string
     currD[0] = (i + 1) * PENALTY_INSERTION;
     char ch1 = s1.charAt (i);

     // for each column in the distance matrix
     for (int j = 1; j <= n; j++) {

     char ch2 = s2.charAt (j - 1);
     if (ch1 == ch2) {
     currD[j] = prevD[j - 1];
     } else {
     currD[j] = minOfThreeNumbers (
     prevD[j - 0] + PENALTY_DELETION,
     currD[j - 1] + PENALTY_INSERTION,
     prevD[j - 1] + PENALTY_SUBSTITUTION);
     }

     }

     temp = prevD;
     prevD = currD;
     currD = temp;

     }

     // after swapping, the final answer is now in the last column of prevD
     int value = prevD[prevD.length - 1];
     value = value > maxim ? maxim : value;

     return new Difference (value, maxim);
     }

     */
}
