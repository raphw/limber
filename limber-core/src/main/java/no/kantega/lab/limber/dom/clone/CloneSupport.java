package no.kantega.lab.limber.dom.clone;

import com.rits.cloning.Cloner;
import no.kantega.lab.limber.dom.ajax.IAjaxCallback;
import no.kantega.lab.limber.dom.doctype.DoctypeDeclaration;
import no.kantega.lab.limber.dom.element.IDomNodeVisitor;
import no.kantega.lab.limber.dom.target.ITargetable;

public class CloneSupport {

    private static final Cloner INSTANCE = new Cloner();

    static {
        INSTANCE.setNullTransient(true);

        INSTANCE.registerImmutable(DoctypeDeclaration.class);

        INSTANCE.dontClone(IAjaxCallback.class);
        INSTANCE.dontClone(ITargetable.class);
        INSTANCE.dontClone(IDomNodeVisitor.class);
    }

    public static Cloner getInstance() {
        return INSTANCE;
    }

}
