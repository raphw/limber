package no.kantega.lab.limber.kernel.clone;

import com.rits.cloning.Cloner;
import no.kantega.lab.limber.dom.ajax.IAjaxCallback;
import no.kantega.lab.limber.dom.doctype.DoctypeDeclaration;
import no.kantega.lab.limber.dom.element.IDomNodeVisitor;
import no.kantega.lab.limber.dom.target.ITargetable;

public class ClonerCloningStrategy implements ICloningStrategy {

    private final Cloner cloner;

    public ClonerCloningStrategy() {
        this.cloner = new Cloner();

        cloner.setNullTransient(true);

        cloner.registerImmutable(DoctypeDeclaration.class);

        cloner.dontClone(IAjaxCallback.class);
        cloner.dontClone(ITargetable.class);
        cloner.dontClone(IDomNodeVisitor.class);
    }

    @Override
    public <T> T deepClone(T object) {
        return cloner.deepClone(object);
    }
}
