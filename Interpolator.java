import java.util.Iterable;
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
	public enum State // States of the interpolator re. processing of known points
	{
		NotYetProcessed, // Interpolator is not ready to be used.
		UpToDate, // Interpolator is ready to be used and has taken into account 
		          //   the latest known points
		OutOfDate // Interpolator can be used but has not taken into account latest points
	}

	public Interpolator(Collection<OrderedPair<T>> knownPoints)
	{	
		_known = new Vector<OrderedPair<T>>(knownPoints);
		_state = State.NotYetProcessed;
	}

	public Vector<OrderedPair<T>> _known; // Growable vector of known points
	// Keeps track of whether the Interpolator is up to date with the 
	//   current vector of known points. Should be updated by the concrete implementation
	//   of the `interpolate`
	protected State _state;	

	/**
	* Returns current state
	* 
	* @return Current readiness state of the interpolator
	*/
	public State getState()
	{
		return _state;
	}

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
		return _known.toArray();
	}

	/**
	* Add a new known point
	* This will downgrade the Interpolator's readiness state
	* 
	* @param x x-coordinate of the known point 
	* @param y y-coordinate of the known point
	*/
	public void addPoint(T x, T y)
	{
		_known.add(new OrderedPair<T>(x, y));
		makeOutOfDate(); // Out of date until the new point has been processed
	}

	/**
	* Remove an existing known point at index i 
	* 
	* @param i index to remove the point at 
	* @return The coordinates that were removed 
	*/
	public OrderedPair<T> removeAt(int i) throws ArrayIndexOutOfBoundsException
	{
		OrderedPair<T> c = _known.remove(i);
		makeOutOfDate(); // Out of date until the new point has been processed
		return c;
	}

	/**
	* Remove an existing known point
	* 
	* @param target The known OrderedPair to remove
	* @return True if the argument was removed; False otherwise
	*/
	public boolean removePoint(OrderedPair<T> target)
	{
		boolean ret = _known.removeElement(target);
		makeOutOfDate();
		return ret;
	}

	/**
	* Clears all known points
	*/
	public void clearPoints();
	{
		OrderedPair<T> c = _known.remove(i);
		makeOutOfDate(); // Out of date until the new point has been processed
		return c;
	}

	/**
	* Do calculations using the known points; process is left to the concrete implementation
	*  Ex. Fit a polynomial to the known points
	* Please update _isUpdated after processing
	*/
	public abstract void process();

	/**
	 * Get the interpolated y-value at the specified x-value 
	 *  This should throw an exception if the Interpolator is in the NotYetProcessed state 
	 * 
	 * @param x The x-value to interpolate at 
	 * @return The interpolated y-value at the specified x-coordinate
	 */
	public abstract T interpolate(T x);

	/**
	 * Interpolate at multiple points. To be implemented
	 * 
	 * @param x Iterable list of x-coordinates to interpolate at 
	 * @return 2D coordinates of interpolated points
	 */
	public abstract ArrayList<OrderedPair<T>> interpolate(Iterable<T> x);

	/**
	 * Private utility function used to downgrade the readiness state of the Interpolator
	 */ 
	private void makeOutOfDate()
	{
		_state = (_state == State.NotYetProcessed) ? State.NotYetProcessed : State.OutOfDate;
	} 
}