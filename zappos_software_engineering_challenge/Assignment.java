package zappos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 * An assignment assigns values to some or all variables of a CSP.
 *
 * @author Ruediger Lunde
 * @author Bret Van Hof
 */
public class Assignment {

    /**
     * Contains all assigned variables. Positions reflect the the order in which
     * the variables were assigned to values.
     */
    List<Variable> variables;
    /**
     * Maps variables to their assigned values.
     */
    Hashtable<Variable, Object> variableToValue;

    public Assignment() {
        variables = new ArrayList<>();
        variableToValue = new Hashtable<>();
    }

    public List<Variable> getVariables() {
        return Collections.unmodifiableList(variables);
    }

    public Object getAssignment(Variable var) {
        return variableToValue.get(var);
    }

    public void setAssignment(Variable var, Object value) {
        if (!variableToValue.containsKey(var)) {
            variables.add(var);
        }
        variableToValue.put(var, value);
    }

    public void removeAssignment(Variable var) {
        if (hasAssignmentFor(var)) {
            variables.remove(var);
            variableToValue.remove(var);
        }
    }

    public boolean hasAssignmentFor(Variable var) {
        return variableToValue.get(var) != null;
    }

    /**
     * Returns true if this assignment does not violate any constraints of
     * <code>constraints</code>.
     *
     * @param constraints
     * @return
     */
    public boolean isConsistent(List<Constraint> constraints) {
        return constraints.stream().noneMatch((cons) -> 
                (!cons.isSatisfiedWith(this)));
    }

    /**
     * Returns true if this assignment assigns values to every variable of
     * <code>vars</code>.
     *
     * @param vars
     * @return
     */
    public boolean isComplete(List<Variable> vars) {
        return vars.stream().noneMatch((var)
                -> (!hasAssignmentFor(var)));
    }

    /**
     * Returns true if this assignment assigns values to every variable of
     * <code>vars</code>.
     *
     * @param vars
     * @return
     */
    public boolean isComplete(Variable[] vars) {
        for (Variable var : vars) {
            if (!hasAssignmentFor(var)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if this assignment is consistent as well as complete with
     * respect to the given CSP.
     *
     * @param csp
     * @return
     */
    public boolean isSolution(CSP csp) {
        return isConsistent(csp.getConstraints())
                && isComplete(csp.getVariables());
    }

    public Assignment copy() {
        Assignment copy = new Assignment();
        variables.stream().forEach((var) -> {
            copy.setAssignment(var, variableToValue.get(var));
        });
        return copy;
    }

    @Override
    public String toString() {
        boolean comma = false;
        StringBuilder result = new StringBuilder("{");
        for (Variable var : variables) {
            if (comma) {
                result.append(", ");
            }
            result.append(var).append("=").append(variableToValue.get(var));
            comma = true;
        }
        result.append("}");
        return result.toString();
    }
}
