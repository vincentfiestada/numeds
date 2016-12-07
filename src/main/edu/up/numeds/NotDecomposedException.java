package edu.up.numeds;

/**
 * Exception to be thrown when trying to access the decomposed components
 * of a matrix before it has been decomposed or if it has been changed since 
 * the last decomposition
 * 
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.1          
 */

public class NotDecomposedException extends Exception 
{
	public NotDecomposedException(String message)
	{
		super(message + " Matrix decomposition is out of date.");
	}
}