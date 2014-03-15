package cz.edu.x3m.plagiarism.utils;

import java.util.List;

/**
 *
 *  @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class CountCrate <T>{
    
    private final List<T> larger;
    private final List<T> smaller;



    public CountCrate (List<T> a, List<T> b) {
        final int sa = a.size (), sb = b.size ();
        this.larger = sa == sb ? a : sa > sb ? a : b;
        this.smaller = this.larger == a ? b : a;
    }



    public List<T> getLarger () {
        return larger;
    }



    public List<T> getSmaller () {
        return smaller;
    }
    
    
    
    
    
    
}
