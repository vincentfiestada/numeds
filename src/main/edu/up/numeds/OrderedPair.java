package edu.up.numeds;

/**
 * Represents an ordered pair, 2-tuple, or coordinates in 2D space
 *
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.2          
 */
 public class OrderedPair<T>
 {
	 public OrderedPair(T x, T y)
	 {
		this.x = x;
		this.y = y;
	 }

	 public final T x;
	 public final T y;

	 public boolean equals(OrderedPair<T> other)
	 {
		boolean result;
		if((other == null) || (getClass() != other.getClass()))
		{
			result = false;
		}
		else
		{
			OrderedPair<T> otherPair = (OrderedPair<T>)other;
			result = x.equals(otherPair.x) && y.equals(otherPair.y);
		}
		return result;
	 }
 }