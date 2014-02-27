package zappos_software_engineering_challenge;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a binary constraint which forbids equal values.
 * 
 * @author Ruediger Lunde
 */
public class GreaterThanConstraint implements Constraint {

	private List<Variable> scope;
        private double constrainingValue;

	public GreaterThanConstraint(List<Variable> myVars, double finalPrice) 
        {
            
	    scope = myVars;
            constrainingValue = finalPrice;
	}

	@Override
	public List<Variable> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
		Double value1 = 0.00;
                for(Variable var1 : scope)
                    value1 += (Double)assignment.getAssignment(var1);
		return value1 == null || 
                        value1 > constrainingValue;
	}
        
}