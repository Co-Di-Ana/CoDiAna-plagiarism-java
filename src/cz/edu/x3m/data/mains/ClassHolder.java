package cz.edu.x3m.data.mains;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class ClassHolder extends AbstractHolder {

    private ClassOrInterfaceDeclaration classDeclaration;



    public ClassHolder (ClassOrInterfaceDeclaration classDeclaration) {
        this.classDeclaration = classDeclaration;
    }



    public ClassOrInterfaceDeclaration getClassDeclaration () {
        return classDeclaration;
    }



    @Override
    public ClassOrInterfaceDeclaration getClassOrIface () {
        return classDeclaration;
    }
}
