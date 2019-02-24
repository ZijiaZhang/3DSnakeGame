package DataTypes;

public class Vector_3D {
    private double x=0;
    private double y=0;
    private double z=0;
    //EFFECT: Construct a 3D Vector
    public Vector_3D(){
    }
    //EFFECT: copy a 3D Vector
    public Vector_3D(Vector_3D v){
        x = v.getX();
        y = v.getY();
        z= v.getZ();
    }
    //EFFECT: Construct a 3D Vector with x,y,z
    public Vector_3D(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    //EFFECT: get x
    public double getX() {
        return x;
    }
    //EFFECT: get y
    public double getY() {
        return y;
    }
    //EFFECT: get z
    public double getZ() {
        return z;
    }
    //EFFECT: set x
    public void setX(double x) {
        this.x = x;
    }
    //EFFECT: set y
    public void setY(double y) {
        this.y = y;
    }
    //EFFECT: set z
    public void setZ(double z) {
        this.z = z;
    }


    //EFFECTS: return the addition of two Vectors.
    public Vector_3D add(Vector_3D l){
        return new Vector_3D(this.x+l.getX(),this.y+l.getY(),this.z+l.getZ());
    }

    //EFFECTS: return the difference of two Vectors.
    public Vector_3D minus(Vector_3D l){
        return new Vector_3D(this.x-l.getX(),this.y-l.getY(),this.z-l.getZ());
    }

    //EFFECTS: return the length of two Vectors.
    public double length(){
        return Math.sqrt(x*x+y*y+z*z);
    }

    //EFFECTS: return the multiplication of the Vector and a scalar.
    public Vector_3D multiply(double n){
        return new Vector_3D(x*n,y*n,z*n);
    }

    //Require: Vector cannot divide by zero
    //EFFECTS: return the multiplication of the Vector and a scalar.
    public Vector_3D divide(double n){
        return new Vector_3D(x/n,y/n,z/n);
    }

}
