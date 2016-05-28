package zappos;

/**
 * Interface which allows interested clients to register at a solution strategy
 * and follow their progress step by step.
 *
 * @author Ruediger Lunde
 * @author Bret Van Hof
 */
public interface CSPStateListener {

    /**
     * Informs about changed assignments.
     *
     * @param assignment
     * @param csp
     */
    void stateChanged(Assignment assignment, CSP csp);

    /**
     * Informs about changed domains (inferences).
     *
     * @param csp
     */
    void stateChanged(CSP csp);
}
