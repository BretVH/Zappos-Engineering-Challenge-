/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zappos_software_engineering_challenge;

import java.util.List;

/**
 *
 * @author Bret
 */
public class ProductConstraint implements Constraint
{

    @Override
    public List<Variable> getScope() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean isSatisfiedWith(Assignment assignment) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}