import it.unive.lisa.analysis.SemanticException;
import it.unive.lisa.analysis.nonrelational.value.BaseNonRelationalValueDomain;
import it.unive.lisa.analysis.representation.DomainRepresentation;
import it.unive.lisa.analysis.representation.StringRepresentation;
import it.unive.lisa.program.cfg.ProgramPoint;
import it.unive.lisa.symbolic.value.Constant;
import it.unive.lisa.symbolic.value.operator.AdditionOperator;
import it.unive.lisa.symbolic.value.operator.binary.BinaryOperator;

public class ACADIASignDomain extends BaseNonRelationalValueDomain<ACADIASignDomain> {


    private final int value;

    public static ACADIASignDomain plus = new ACADIASignDomain(0);
    public static ACADIASignDomain minus = new ACADIASignDomain(1);
    public static ACADIASignDomain zero = new ACADIASignDomain(2);
    public static ACADIASignDomain top = new ACADIASignDomain(3);
    public static ACADIASignDomain bottom = new ACADIASignDomain(4);

    private ACADIASignDomain(int value) {
        this.value = value;
    }


    @Override
    public ACADIASignDomain lubAux(ACADIASignDomain other) throws SemanticException {
        if(this==other) return this;
        else return top();
    }

    @Override
    public ACADIASignDomain evalNonNullConstant(Constant constant, ProgramPoint pp) throws SemanticException {
        if(constant.getValue() instanceof Integer) {
            Integer i = (Integer) constant.getValue();
            if(i==0)
                return zero;
            else if(i>0)
                return plus;
            else return minus;
        }
        else return super.evalNonNullConstant(constant, pp);
    }

    @Override
    public ACADIASignDomain evalBinaryExpression(BinaryOperator operator, ACADIASignDomain left, ACADIASignDomain right, ProgramPoint pp) throws SemanticException {
        if(operator instanceof AdditionOperator) {
            if(left==bottom || right==bottom)
                return bottom;
            if(left==top || right==top)
                return top;
            if(left==zero)
                return right;
            if(right==zero)
                return left;
            if(left==right)
                return left;
            else return top;
        }
        return super.evalBinaryExpression(operator, left, right, pp);
    }

    @Override
    public boolean lessOrEqualAux(ACADIASignDomain other) throws SemanticException {
        return this==other;
    }

    @Override
    public boolean equals(Object obj) {
        return this==obj;
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    @Override
    public DomainRepresentation representation() {
        switch(this.value) {
            case 0: return new StringRepresentation("+");
            case 1:return new StringRepresentation("-");
            case 2:return new StringRepresentation("0");
            case 3:return new StringRepresentation("T");
            case 4:return new StringRepresentation("_|_");
        }
        return null;
    }

    @Override
    public ACADIASignDomain top() {
        return top;
    }

    @Override
    public ACADIASignDomain bottom() {
        return bottom;
    }
}
