package no.kantega.lab.limber.doctype;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.IResponseContainer;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class DoctypeDeclaration implements IRenderable {

    public static final DoctypeDeclaration HTML401_STRICT = new DoctypeDeclaration("html", DoctypeVisibility.PUBLIC, "-//W3C//DTD HTML 4.01//EN", "http://www.w3.org/TR/html4/strict.dtd");

    public static final DoctypeDeclaration HTML401_TRANSITIONAL = new DoctypeDeclaration("html", DoctypeVisibility.PUBLIC, "-//W3C//DTD HTML 4.01 Transitional//EN", "http://www.w3.org/TR/html4/loose.dtd");

    public static final DoctypeDeclaration HTML401_FRAMESET = new DoctypeDeclaration("html", DoctypeVisibility.PUBLIC, "-//W3C//DTD HTML 4.01 Frameset//EN", "http://www.w3.org/TR/html4/frameset.dtd");

    public static final DoctypeDeclaration XHTML1_STRICT = new DoctypeDeclaration("html", DoctypeVisibility.PUBLIC, "-//W3C//DTD XHTML 1.0 Strict//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd");

    public static final DoctypeDeclaration XHTML1_TRANSITIONAL = new DoctypeDeclaration("html", DoctypeVisibility.PUBLIC, "-//W3C//DTD XHTML 1.0 Transitional//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");

    public static final DoctypeDeclaration XHTML1_FRAMESET = new DoctypeDeclaration("html", DoctypeVisibility.PUBLIC, "-//W3C//DTD XHTML 1.0 Frameset//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd");

    public static final DoctypeDeclaration XHTML11 = new DoctypeDeclaration("html", DoctypeVisibility.PUBLIC, "-//W3C//DTD XHTML 1.1//EN", "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd");

    public static final DoctypeDeclaration XHTML11_BASIC = new DoctypeDeclaration("html", DoctypeVisibility.PUBLIC, "-//W3C//DTD XHTML Basic 1.1//EN", "http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd");

    public static final DoctypeDeclaration HTML5 = new DoctypeDeclaration("html", null, null, null);

    private final String name;

    private final DoctypeVisibility doctypeVisibility;

    private final String publicId;

    private final String systemId;

    public DoctypeDeclaration(@Nonnull CharSequence name, DoctypeVisibility doctypeVisibility, CharSequence publicId, CharSequence systemId) {
        this.name = name.toString().toLowerCase(Locale.US);
        this.doctypeVisibility = doctypeVisibility;
        this.publicId = publicId == null ? null : publicId.toString();
        this.systemId = systemId == null ? null : systemId.toString();
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getSystemId() {
        return systemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctypeDeclaration that = (DoctypeDeclaration) o;

        if (doctypeVisibility != that.doctypeVisibility) return false;
        if (!name.equals(that.name)) return false;
        if (publicId != null ? !publicId.equals(that.publicId) : that.publicId != null) return false;
        if (systemId != null ? !systemId.equals(that.systemId) : that.systemId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (doctypeVisibility != null ? doctypeVisibility.hashCode() : 0);
        result = 31 * result + (publicId != null ? publicId.hashCode() : 0);
        result = 31 * result + (systemId != null ? systemId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean render(@Nonnull OutputStream outputStream, @Nonnull IResponseContainer response) throws IOException {
        IOUtils.write("<!DOCTYPE ", outputStream);
        IOUtils.write(name, outputStream);
        if (doctypeVisibility != null) {
            IOUtils.write(" ", outputStream);
            IOUtils.write(doctypeVisibility.getIdentifier(), outputStream);
        }
        if (publicId != null) {
            IOUtils.write(" ", outputStream);
            IOUtils.write(publicId, outputStream);
        }
        if (systemId != null) {
            IOUtils.write(" ", outputStream);
            IOUtils.write(systemId, outputStream);
        }
        IOUtils.write(">", outputStream);
        IOUtils.write("\n", outputStream);
        return true;
    }
}
