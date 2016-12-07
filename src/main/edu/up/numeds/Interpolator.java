package edu.up.numeds;

import java.lang.Iterable;
import java.util.Collection;
import java.util.Vector;
import java.util.ArrayList;
/**
 * An abstract 2D Interpolator class with APIs for getting interpolated values 
 * given a set of known points
 *
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.2          
 */

public abstract class Interpolator<T>
{

	public Interpolator(Collection<OrderedPair<T>> knownPoints)
	{	
		_known = new Vector<OrderedPair<T>>(knownPoints);
	}

	public Vector<OrderedPair<T>> _known; // Growable vector of known points

	/**
	* Get number of known points 
	*
	* @return Number of currently known Points 
	*/
	public int countKnownPoints() 
	{
		return _known.size();
	}

	/**
	* Get list of known points
	*
	* @return A copy of the list of known points
	*/
	public OrderedPair<T>[] getKnownPoints()
	{
		return (OrderedPair<T>[])_known.toArray();
	}

	/**
	* Add a new known point
	* 
	* @param T x - x-coordinate of the known point 
	* @param T y - y-coordinate of the known point
	*/
	public void addPoint(T x, T y)
	{
		_known.add(new OrderedPair<T>(x, y));
	}

	/**
	* Remove an existing known point at index i 
	* 
	* @param int i - index to remove the point at 
	* @return The coordinates that were removed 
	*/
	public OrderedPair<T> removeAt(int i) throws ArrayIndexOutOfBoundsException
	{
		return _known.remove(i);
	}

	/**
	* Remove an existing known point
	* 
	* @param OrderedPair<T> target - The known OrderedPair to remove
	* @return True if the argument was removed; False otherwise
	*/
	public boolean removePoint(OrderedPair<T> target)
	{
		return _known.removeElement(target);
	}

	/**
	* Clears all known points
	*/
	public void clearPoints()
	{
		_known.clear();
	}

	/**
	 * Get the interpolated y-value at the specified x-value 
	 *  This should throw an exception if the Interpolator can't be used yet
	 * 
	 * @param T x The x-value to interpolate at 
	 * @return The interpolated y-value at the specified x-coordinate
	 */
	public abstract T interpolate(T x) throws InterpolatorNotReadyException;

	/**
	 * Interpolate at multiple points. 
	 *  This should throw an exception if the Interpolator can't be used yet
	 * 
	 * @param T x Iterable list of x-coordinates to interpolate at 
	 * @return 2D coordinates of interpolated points
	 */
	public abstract ArrayList<OrderedPair<T>> interpolate(Iterable<T> x) throws InterpolatorNotReadyException;
}