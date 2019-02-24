package DataTypes;

public class Vector_2D {
    private double x=0;
    private double y=0;
    //EFFECT:Construct a 2D vector
    public Vector_2D(){
    }
    //EFFECT:Copy a 2D vector
    public Vector_2D(Vector_2D v){
        x = v.getX();
        y = v.getY();

    }
    //EFFECT:Construct a 2D vector with x,y
    public Vector_2D(double x, double y){
        this.x=x;
        this.y=y;

    }
    //EFFECT: get x
    public double getX() {
        return x;
    }
    //EFFECT: get y
    public double getY() {
        return y;
    }

    //EFFECT: set x
    public void setX(double x) {
        this.x = x;
    }
    //EFFECT: set y
    public void setY(double y) {
        this.y = y;
    }



    //EFFECTS: return the addition of two Vectors.
    public Vector_2D add(Vector_2D l){
        return new Vector_2D(this.x+l.getX(),this.y+l.getY());
    }

    //EFFECTS: return the difference of two Vectors.
    public Vector_2D minus(Vector_2D l){
        return new Vector_2D(this.x-l.getX(),this.y-l.getY());
    }

    //EFFECTS: return the length of two Vectors.
    public double length(){
        return Math.sqrt(x*x+y*y);
    }

    //EFFECTS: return the multiplication of the Vector and a scalar.
    public Vector_2D multiply(double n){
        return new Vector_2D(x*n,y*n);
    }

    //REQUIRES: Input scalar is not zero
    //EFFECTS: return the multiplication of the Vector and a scalar.
    public Vector_2D divide(double n){
        return new Vector_2D(x/n,y/n);
    }
}
