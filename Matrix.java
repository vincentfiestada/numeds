/**
 * A basic square matrix with methods for retrieving and setting elements 
 *
 * @author      Vincent Fiestada <vffiestada@gmail.com>
 * @version     1.0             
 * @since       0.1          
 */
 
public class Matrix
{
	public Matrix(int n)
	{
		_data = new double[n][n];
		_size = n;
	}

	protected double[][] _data;
	protected int _size;

	/**
	* Set the element at index i,j
	* <p>
	* Sets the matrix element at index i,j to the specified Double value
	* </p>
	*
	* @param  i Row index
	* @param  j Column index 
	* @param  val Value to set the element to 
	*/
	public void set(int i, int j, double val) 
	{	
		_data[i][j] = val;
	}
	/**
	* Get the element at index i,j
	* <p>
	* Returns the matrix element at index i,j <br />
	* This method may be unsafe since the matrix elements are untouched by default
	* </p>
	*
	* @param  i Row index
	* @param  j Column index 
	* @return Double value of matrix element specified by the indices
	*/
	public double get(int i, int j) 
	{	
		return _data[i][j];
	}
	/**
	* Get the size, i.e. width and length of the matrix
	* 
	* @return The size of the square matrix
	*/
	public int getSize()
	{
		return _size;
	}
}