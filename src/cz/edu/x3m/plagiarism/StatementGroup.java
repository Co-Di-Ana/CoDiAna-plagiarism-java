package cz.edu.x3m.plagiarism;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class StatementGroup {

    private final List<StatementType> items = new ArrayList<> ();
    private final int hashCode;



    public StatementGroup (StatementType... stmts) {
        int result = 1;
        for (StatementType s : stmts)
            result *= s.getValue ();
        Collections.addAll (items, stmts);
        hashCode = result;
    }



    public List<StatementType> getItems () {
        return items;
    }



    @Override
    public int hashCode () {
        return hashCode;
    }



    @Override
    public boolean equals (Object obj) {
        if (obj == null)
            return false;
        if (getClass () != obj.getClass ())
            return false;
        final StatementGroup other = (StatementGroup) obj;
        if (this.hashCode != other.hashCode)
            return false;
        return true;
    }



    public static StatementGroup create (StatementType... stmts) {
        return new StatementGroup (stmts);
    }
}
