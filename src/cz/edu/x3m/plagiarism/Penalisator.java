package cz.edu.x3m.plagiarism;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public interface Penalisator<T> {

    int penalise (T o1, T o2);
}
