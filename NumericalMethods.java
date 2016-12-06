public class NumericalMethods
{
	public static void main(String[] args) throws Exception
	{
		System.out.println("LU Decomposition Test");
		DecomposableMatrix m = new DecomposableMatrix(3);
		m.set(0,0, 512);
		m.set(0,1, 75);
		m.set(1,0, 345);
		m.set(1,1, 1223);
		m.decomposeLU();
		System.out.println(m.getL().get(0,0));
		System.out.println(m.getL().get(0,1));
		System.out.println(m.getL().get(1,0));
		System.out.println(m.getL().get(1,1));
		System.out.println("-------------------");
		System.out.println(m.getU().get(0,0));
		System.out.println(m.getU().get(0,1));
		System.out.println(m.getU().get(1,0));
		System.out.println(m.getU().get(1,1));
	}
}