package edu.up.numeds;

import java.lang.Iterable;
import java.util.Collection;
import java.util.Vector;
import java.util.ArrayList;
/**
 * A concrete implementation of Interpolator, which uses Lagrange polynomial interpolation
 * This class uses Doubles but can be easily modified to use other subclasses of Number such 
 * as BigInteger, BigDecimal, etc.
 *
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.3          
 */

 public class LagrangeInterpolator extends Interpolator<Double>
 {
	public LagrangeInterpolator(Collection<OrderedPair<Double>> knownPoints)
	{	
		super(knownPoints);
	}

	/**
	 * Get the interpolated y-value at the specified x-value 
	 *  This will throw an exception if there are no known points
	 * 
	 * @param T x The x-value to interpolate at 
	 * @return The interpolated y-value at the specified x-coordinate
	 */
	@Override
	public Double interpolate(Double x) throws InterpolatorNotReadyException
	{
		if (_known.size() < 1)
		{
			throw new InterpolatorNotReadyException("Lagrange polynomial cannot be fit onto zero points.");
		}
		int n = _known.size();
		Double l[] = new Double[n];
		for (int i = 0; i < n; i++)
		{
			l[i] = 1.0; // Set product to 1 initially
			for (int j = 0; j < n; j++)
			{
				if (j != i) 
				{
					l[i] *= (x - _known.get(j).x)/(_known.get(i).x - _known.get(j).x);
				}
			}
		}
		Double yInt = 0.0;
		for (int i = 0; i < n; i++)
		{
			yInt += _known.get(i).y * l[i];
		}
		return yInt;
	}

	/**
	 * Interpolate at multiple points. 
	 *  This will throw an exception if the Interpolator can't be used yet
	 * 
	 * @param T x Iterable list of x-coordinates to interpolate at 
	 * @return 2D coordinates of interpolated points
	 */
	@Override
	public ArrayList<OrderedPair<Double>> interpolate(Iterable<Double> x) throws InterpolatorNotReadyException
	{
		ArrayList<OrderedPair<Double>> points = new ArrayList();
		for (Double i : x)
		{
			points.add(new OrderedPair<Double>(i, this.interpolate(i)));
		}
		return points;
	}
 }