package site.lhearen.ajava.pattern;

public class Visitor_0 {
    // https://sourcemaking.com/design_patterns/visitor/java/1
    // Double dispatch: https://en.wikipedia.org/wiki/Double_dispatch
    public static void main( String[] args ) {
        Element[] list = {new FOO(), new BAR(), new BAZ()};
        UpVisitor up = new UpVisitor();
        DownVisitor down = new DownVisitor();
        for (Element element : list) {
            element.accept(up);
        }
        for (Element element : list) {
            element.accept(down);
        }
    }
}

interface Element {
    void accept(Visitor v);
}

class FOO implements Element {
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String getFOO() {
        return "FOO";
    }
}

class BAR implements Element {
    public void   accept( Visitor v ) {
        v.visit( this );
    }

    public String getBAR() {
        return "BAR";
    }
}

class BAZ implements Element {
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String getBAZ() {
        return "BAZ";
    }
}

interface Visitor {
    void visit(FOO foo);
    void visit(BAR bar);
    void visit(BAZ baz);
}

class UpVisitor implements Visitor {
    public void visit(FOO foo) {
        System.out.println("do Up on " + foo.getFOO());
    }

    public void visit(BAR bar) {
        System.out.println("do Up on " + bar.getBAR());
    }

    public void visit(BAZ baz) {
        System.out.println( "do Up on " + baz.getBAZ() );
    }
}

class DownVisitor implements Visitor {
    public void visit(FOO foo) {
        System.out.println("do Down on " + foo.getFOO());
    }

    public void visit(BAR bar) {
        System.out.println("do Down on " + bar.getBAR());
    }

    public void visit(BAZ baz ) {
        System.out.println("do Down on " + baz.getBAZ());
    }
}

