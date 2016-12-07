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

	 public T x;
	 public T y;

	 // Getters and Setters so it can be used with JavaFX's PropertyValueFactory
	 public T getX() 
	 {
		 return x;
	 }

	 public void setX(T val)
	 {
		 x = val;
	 }

	 public T getY()
	 {
		 return y;
	 }

	 public void sety(T val)
	 {
		 y = val;
	 }

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