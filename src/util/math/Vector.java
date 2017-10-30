package util.math;

public class Vector {

	public double x, y;
	
	/**
	 * Sets vector X and Y
	 * <p>
	 * 
	 * @param x		x position
	 * @param y		y position
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Adds vector other to this vector. Changes this vector itself.
	 * 
	 * @param other		Any other Vector
	 */
	public void add(Vector other) {
		
		this.x += other.x;
		this.y += other.y;
		
	}
	
	/**
	 * Subtracts vector other from this vector. Changes this vector itself.
	 * 
	 * @param other		Any other Vector
	 */
	public void subtract(Vector other) {
		
		this.x -= other.x;
		this.y -= other.y;
		
	}
	
	/**
	 * Multiplies a scalar by this vector. Changes this vector itself.
	 * 
	 * @param scalar	Any number
	 */
	public void mult(double scalar) {
	
		this.x *= scalar;
		this.y *= scalar;
		
	}
	
	/**
	 * Set length of vector to l while maintaining ratio   d
	 * 
	 * @param s	New length
	 */
	public void normalize(double l) {
		
		double oldL = Math.sqrt(this.x * this.x + this.y * this.y);
		
		this.x /= oldL;
		this.y /= oldL;
		
		this.x *= l;
		this.y *= l;
	}
	
	/**
	 * Finds length of the vector. Uses the square root function so
	 * this is a bit expensive
	 * 
	 * @return	Double length of this vector
	 */
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	/**
	 * Dot product of 2 vectors. Returns a new vector.
	 * 
	 * @param a		first vector in dot product
	 * @param b		second vector in dot product
	 */
	public static Vector mult(Vector a, Vector b) {
		Vector newVec = new Vector(a.x * b.x, a.y * b.y);
		return newVec;
	}

}
