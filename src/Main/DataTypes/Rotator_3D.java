package DataTypes;

import static com.sun.tools.doclint.Entity.pi;
import static java.lang.Math.toRadians;

public class Rotator_3D {
    private double xy;
    private double xz;
    private double yz;
    //EFFECT: Construct a Rotator
    public Rotator_3D(){

    }
    //EFFECT: Construct a Rotator with xy,xz,yz
    public Rotator_3D(double xy, double xz, double yz){
        this.xy = xy;
        this.xz = xz;
        this.yz = yz;
    }
    //EFFECT: get xy
    public double getXy() {
        return xy;
    }
    //EFFECT: get xz
    public double getXz() {
        return xz;
    }

    //EFFECT: get yx
    public double getYz() {
        return yz;
    }
    //EFFECT: set xy
    public void setXy(double xy) {
        this.xy = xy;
    }
    //EFFECT: set xz
    public void setXz(double xz) {
        this.xz = xz;
    }
    //EFFECT: set yz
    public void setYz(double yz) {
        this.yz = yz;
    }

    //EFFECTS: return the addition of two Rotators.
    public Rotator_3D add(Rotator_3D r){
        return new Rotator_3D(xy +r.getXy(),xz +r.getXz(),yz +r.getYz());
    }

}
