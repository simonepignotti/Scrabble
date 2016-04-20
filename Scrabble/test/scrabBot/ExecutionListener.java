/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class ExecutionListener extends RunListener
{
	/* Called before any tests have been run. */
	public void testRunStarted(Description description)	throws java.lang.Exception {
		System.out.println("\nStarting Unit Tests: ");
	}

	/* Called when all tests have finished */
	public void testRunFinished(Result result) throws java.lang.Exception {
		System.out.println("\n\nFinished Unit Testing - Failures: " + result.getFailureCount());
	}

	/* Called when an atomic test is about to be started.*/
	public void testStarted(Description description) throws java.lang.Exception {
		System.out.print("\n"+description.getMethodName());
	}

	/* Called when an atomic test fails. */
	public void testFailure(Failure failure) throws java.lang.Exception {
		System.out.print(" --> FAIL: "+failure.getMessage());
	}

}