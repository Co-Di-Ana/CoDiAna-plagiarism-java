package cz.edu.x3m.data.mains;

import japa.parser.ast.body.ClassOrInterfaceDeclaration;

/**
 *
 * @author Jan Hybs <x3mSpeedy@gmail.com>
 */
public class IfaceHolder extends AbstractHolder {

    private ClassOrInterfaceDeclaration ifaceDeclaration;



    public IfaceHolder (ClassOrInterfaceDeclaration ifaceDeclaration) {
        this.ifaceDeclaration = ifaceDeclaration;
    }



    public ClassOrInterfaceDeclaration getIfaceDeclaration () {
        return ifaceDeclaration;
    }



    @Override
    public ClassOrInterfaceDeclaration getClassOrIface () {
        return ifaceDeclaration;
    }
}
