package edu.up.numeds;

/**
 * Exception to be thrown when trying to use an interpolator that has not yet
 *  done its relevant calculations
 * 
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.3          
 */

public class InterpolatorNotReadyException extends Exception 
{
	public InterpolatorNotReadyException(String message)
	{
		super(message + " Interpolator cannot be used.");
	}
}