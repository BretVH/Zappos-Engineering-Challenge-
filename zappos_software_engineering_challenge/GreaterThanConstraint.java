package zappos;

import java.util.List;

/**
 * Represents a binary constraint which forbids equal values.
 *
 * @author Bret Van Hof
 */
public class GreaterThanConstraint implements Constraint {

    private final List<Variable> scope;
    private final double constrainingValue;

    public GreaterThanConstraint(List<Variable> myVars, double finalPrice) {

        scope = myVars;
        constrainingValue = finalPrice;
    }

    @Override
    public List<Variable> getScope() {
        return scope;
    }

    @Override
    public boolean isSatisfiedWith(Assignment assignment) {
        Double value = 0.00;
        value = scope.stream().map((var1)
                -> (Double) assignment.getAssignment(var1)).reduce
                    (value, (accumulator, _item) -> accumulator + _item);
        return value == null || value > constrainingValue;
    }

}
