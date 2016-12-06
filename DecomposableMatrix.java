/**
 * A Matrix that can be decomposed into LU using Crout's Method
 * 
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.1          
 */

public class DecomposableMatrix extends Matrix
{
	public DecomposableMatrix(int n)
	{
		super(n);
		_decomposed = false;
		_L = new Matrix(n);
		_U = new Matrix(n);
	}

	protected Matrix _L;
	protected Matrix _U;
	protected boolean _decomposed;

	/**
	* Set the element at index i,j
	* <p>
	* Sets the matrix element at index i,j to the specified Double value
	* Also updates the internal state of the matrix so that it knows the 
	* decomposition is now out of date
	* </p>
	*
	* @param  i Row index
	* @param  j Column index 
	* @param  val Value to set the element to 
	*/
	public void set(int i, int j, double val) 
	{	
		_data[i][j] = val;
		_decomposed = false;
	}
	/**
	* Get the L component of the matrix
	* <p>
	* Retrieves the L component of the decomposed matrix <br />
	* Throws an exception if the matrix decomposition is out of date
	* </p>
	*
	* @return L component, derived using Crout's method
	*/
	public Matrix getL() throws NotDecomposedException
	{
		if (_decomposed) 
		{
			return _L;
		}
		else {
			throw new NotDecomposedException("Cannot get L.");
		}
	}
	/**
	* Get the U component of the matrix
	* <p>
	* Retrieves the U component of the decomposed matrix <br />
	* Throws an exception if the matrix decomposition is out of date
	* </p>
	*
	* @return U component, derived using Crout's method
	*/
	public Matrix getU() throws NotDecomposedException
	{
		if (_decomposed) 
		{
			return _U;
		}
		else {
			throw new NotDecomposedException("Cannot get U.");
		}
	}
	/**
	* Update the internal LU decomposition of the matrix
	* <p>
	* Updates the internal matrix LU decomposition using Crout's method
	* After this method finishes, the decomposition becomes up-to-date and the LU matrices 
	* can be accessed without throwing NotDecomposedExceptions 
	* </p>
	*/
	public void decomposeLU() 
	{
		int R = _size;
		int C = _size;
		for (int i = 0; i < R; i++) 
		{
			_L.set(i, 0, _data[i][0]); // First column of L is all 0's
			_U.set(i, i, 1); // Diagonal of U is all 1's
		}
		for (int j = 1; j < R; j++)
		{
			// 1st row of U is the 1st row of A divided by the first element of L
			_U.set(0, j, _data[0][j] / _L.get(0, 0));
		}
		for (int i = 1; i < R; i++)
		{
			for (int j = 1; j <= i; j++)
			{
				// Calculate term to subtract from Aij to get Lij
				double sub = 0.0;
				for (int k = 0; k <= j - 1; k++) 
				{
					sub += _L.get(i, k) * _U.get(k, j);
				}
				_L.set(i, j, _data[i][j] - sub);
			}
			for (int j = i + 1; j < R; j++) 
			{
				// Calculate term to subtract from Aij to get Uij 
				double sub = 0.0;
				for (int k = 0; k <= i - 1; k++)
				{
					sub += _L.get(i, k) * _U.get(k, j);
				}
				_U.set(i, j, (_data[i][j] - sub) / _L.get(i, i));
			}
		}
		_decomposed = true; // Decomposition is now up-to-date and safe to access
	}
}