package edu.up.numeds;

import java.util.ArrayList;

public class NumericalMethods
{
	public static void main(String[] args) throws Exception
	{
		System.out.println("LU Decomposition Test");
		ArrayList<OrderedPair<Double>> knownPoints = new ArrayList(5);
		knownPoints.add(new OrderedPair<Double>(45.67, 32.4));
		knownPoints.add(new OrderedPair<Double>(163.0, 111.23));
		knownPoints.add(new OrderedPair<Double>(367.01, 42.5));
		knownPoints.add(new OrderedPair<Double>(587.12, 82.69));
		knownPoints.add(new OrderedPair<Double>(590.4, 89.12));
		LagrangeInterpolator lagrange = new LagrangeInterpolator(knownPoints);
		Double y = lagrange.interpolate(612.12);
		System.out.println(y);
	}
}