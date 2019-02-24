package Objects;

import DataTypes.Pair_4;
import DataTypes.Rotator_3D;
import DataTypes.Vector_3D;
import Exceptions.InvalidVectorException;
import Interfaces.Saveable;
import World.Level;
import javafx.util.Pair;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class DisplayObject extends Object implements Saveable{
    protected List<Vector_3D> relatedAllPoints = new CopyOnWriteArrayList<>();
    protected List<Vector_3D> allPoints = new CopyOnWriteArrayList<>();
    protected List<int[]> lines = new CopyOnWriteArrayList<>();
    protected List<Pair<Vector_3D,Vector_3D>> allLines =new CopyOnWriteArrayList<>();
    protected  List<Pair_4<Integer,Integer,Integer, Color>> surfaces = new CopyOnWriteArrayList<>();
    protected List<Pair_4<Vector_3D,Vector_3D,Vector_3D,Color>> allSurfaces = new CopyOnWriteArrayList<>();

    //EFFECTS: create a new copy of a DisplayObject
    public DisplayObject(DisplayObject d){
        this.name =d.name;
        this.l = d.l;
        this.location = d.location;
        this.rotation = d.rotation;
        for(Vector_3D v : d.relatedAllPoints)
            this.relatedAllPoints.add(new Vector_3D(v));
        for(Vector_3D v : d.allPoints)
            this.allPoints.add(new Vector_3D(v));
        this.lines = new ArrayList<>(d.lines);
        this.allLines = generateLines(this.lines);
        for(Pair_4<Integer,Integer,Integer, Color> v : d.surfaces)
            this.surfaces.add(new Pair_4<Integer,Integer,Integer, Color>(v.getFirst(),v.getSecond(),v.getThird(),v.getForth()));
        this.allSurfaces=generateSurfaces(surfaces);
    }

    //EFFECTS: create a new basic DisplayObject
    public DisplayObject(String name, Level l){
        this.l = l;
        this.name = name;
    }

    //EFFECT: Construct a display object
    public DisplayObject(Vector_3D location, Vector_3D[] points, int[][] lines, String name, Rotator_3D r, Level l){
        this.l = l;
        if(location==null){
            throw new InvalidVectorException();
        }
        this.location = location;
        for (Vector_3D i : points) {
            this.relatedAllPoints.add(i);
        }
        for (Vector_3D i : points) {
            this.allPoints.add(i.add(location));
        }
        this.lines = new CopyOnWriteArrayList<>(Arrays.asList(lines));
        this.name = name;
        this.rotation=r;
        updatePoints();
        this.allLines = generateLines(this.lines);
    }
    //EFFECTS: return name;
    public String getName() {
        return name;
    }
    //EFFECTS: set name;
    public void setName(String name) {
        this.name = name;
    }

    //EFFECTS: get AllPoints
    public List<Vector_3D> getAllPoints() {
        return allPoints;
    }


    //EFFECTS: generate All lines
    protected List<Pair<Vector_3D,Vector_3D>> generateLines(List<int[]> lines){
        List<Pair<Vector_3D,Vector_3D>> allLines = new CopyOnWriteArrayList<>();
        for(int[] i : lines){
            int lineP1 = i[0];
            int lineP2 = i[1];
//            if(lineP1>=allPoints.size()||lineP2>=allPoints.size()){
//                throw new IndexException();
//            }
            allLines.add(new Pair<Vector_3D,Vector_3D>(allPoints.get(lineP1),allPoints.get(lineP2)));
        }
        return allLines;
    }

    //EFFECTS: generate All surfaces
    protected List<Pair_4<Vector_3D,Vector_3D,Vector_3D,Color>> generateSurfaces(List<Pair_4<Integer,Integer,Integer, Color>> surfaces){
        List<Pair_4<Vector_3D,Vector_3D,Vector_3D,Color>> allSurfaces = new CopyOnWriteArrayList<>();
        for(Pair_4<Integer,Integer,Integer, Color> i : surfaces){
            int surfaceP1 = i.getFirst();
            int surfaceP2 = i.getSecond();
            int surfaceP3 = i.getThird();
            Color c = i.getForth();
//            if(surfaceP1>=allPoints.size()||surfaceP2>=allPoints.size()||surfaceP3>=allPoints.size()){
//                throw new IndexException();
//            }
            allSurfaces.add(new Pair_4<>(allPoints.get(surfaceP1),allPoints.get(surfaceP2),allPoints.get(surfaceP3),c));
        }
        return allSurfaces;
    }

    //EFFECTS: return all lines
    public List<Pair<Vector_3D,Vector_3D>> getAllLines(){
        return allLines;
    }

    //MODIFIES: this
    //EFFECTS: initialize the points when construct
    public void updatePoints(){
        //allPoints.clear();
        for(int i = 0 ;i<relatedAllPoints.size();i++){
            Vector_3D v = relatedAllPoints.get(i);
            Vector_3D temp_v = new Vector_3D(v);
            //Vector_3D rotated = v;

            rotateXY(temp_v, rotation.getXy());
            rotateXZ(temp_v, rotation.getXz());
            rotateYZ(temp_v, rotation.getYz());

            temp_v = temp_v.add(location);
            allPoints.get(i).setX(temp_v.getX());
            allPoints.get(i).setY(temp_v.getY());
            allPoints.get(i).setZ(temp_v.getZ());
        }
    }

    //MODIFIES: this
    //EFFECTS: rotate object
    public void addRotationOffset(Rotator_3D r){
        double xy = r.getXy();
        double xz = r.getXz();
        double yz = r.getYz();
        for(int i=0;i<allPoints.size();i++) {
            Vector_3D point = allPoints.get(i);
            Vector_3D relativePoint = point.minus(location);
            rotateXY(relativePoint,xy);
            rotateXZ(relativePoint,xz);
            rotateYZ(relativePoint,yz);
            point.setX(relativePoint.getX()+location.getX());
            point.setY(relativePoint.getY()+location.getY());
            point.setZ(relativePoint.getZ()+location.getZ());

        }
    }
    //MODIFIES: this
    //EFFECTS: rotate object in XY plane
    protected void rotateXY(Vector_3D point, double deg) {
        double x = point.getX();
        double y = point.getY();
        double z =point.getZ();
        double temp_X = x*cos((deg)) - y*sin((deg));
        double temp_Y = x*sin((deg)) + y*cos((deg));
        point.setX(temp_X);
        point.setY(temp_Y);
    }
    //MODIFIES: this
    //EFFECTS: rotate object in XZ plane
    protected void rotateXZ(Vector_3D point, double deg) {
        double x = point.getX();
        double y = point.getY();
        double z =point.getZ();
        double temp_X = x*cos((deg)) - z*sin((deg));
        double temp_Z = x*sin((deg)) + z*cos((deg));
        point.setX(temp_X);
        point.setZ(temp_Z);
    }
    //MODIFIES: this
    //EFFECTS: rotate object in YZ plane
    protected void rotateYZ(Vector_3D point, double deg) {
        double x = point.getX();
        double y = point.getY();
        double z =point.getZ();
        double temp_Y = y*cos((deg)) - z*sin((deg));
        double temp_Z = y*sin((deg)) + z*cos((deg));
        point.setY(temp_Y);
        point.setZ(temp_Z);
    }

    //EFFECT: Save the object to the file
    @Override
    public void save(String filename) {
        try {
            PrintWriter writer = new PrintWriter(name+".txt", "UTF-8");
            writer.println("class:"+this.getClass().getName());
            writer.println("name:" + name);
            for(Vector_3D v:relatedAllPoints){
                writer.println("related:" + v.getX()+","+v.getY()+","+v.getZ());
            }
            writer.println("location:"+location.getX()+","+location.getY()+","+location.getZ());
            for(Vector_3D v:allPoints){
                writer.println("points:" + v.getX()+","+v.getY()+","+v.getZ());
            }
            for (int[] p :lines){
                int v1 = p[0];
                int v2 = p[1];
                writer.println("lines:" + v1+","+v2);
            }
            writer.close();
        }catch (IOException e){
            System.out.println("Can't Save " + name);
        }
    }

    //EFFECT: load object from data
    @Override
    public void load(List<String> data) {
        for(String l: data){
            if(l.startsWith("name")){
                l=l.substring(5);
                this.name=l;
            }else if(l.startsWith("related")){
                l = l.substring(8);
                String[] a = l.split(",");
                this.relatedAllPoints.add(new Vector_3D(Double.parseDouble(a[0]), Double.parseDouble(a[1]),Double.parseDouble(a[2])));

            }else if(l.startsWith("location:")){
                l= l.substring(9);
                String[] a =l.split(",");
                this.location=new Vector_3D(Double.parseDouble(a[0]), Double.parseDouble(a[1]),Double.parseDouble(a[2]));
            }else if(l.startsWith("points:")){
                l = l.substring(7);
                String[] a = l.split(",");
                this.allPoints.add(new Vector_3D(Double.parseDouble(a[0]), Double.parseDouble(a[1]),Double.parseDouble(a[2])));
            }else if(l.startsWith("lines")){
                l = l.substring(6);
                String[] a = l.split(",");
                int[] result = new int[a.length];
                for (int i = 0; i < a.length; i++) {
                    result[i] = Integer.parseInt(a[i]);
                }
                this.lines.add(result);
            }
        }
        this.allLines=generateLines(this.lines);
    }
    //EFFECTS: return All related points
    public List<Vector_3D> getRelatedAllPoints() {
        return relatedAllPoints;
    }

    //EFFECTS: return surfaces
    public List<Pair_4<Integer, Integer, Integer, Color>> getSurfaces() {
        return surfaces;
    }
    //EFFECTS: return all surfaces
    public List<Pair_4<Vector_3D, Vector_3D, Vector_3D, Color>> getAllSurfaces() {
        return allSurfaces;
    }
    //EFFECTS: add Locational Offset to the object
    public void addLocationOffset(Vector_3D dl){
        location = location.add(dl);
        for (Vector_3D v:allPoints){
            v.setX(v.getX()+dl.getX());
            v.setY(v.getY()+dl.getY());
            v.setZ(v.getZ()+dl.getZ());
        }
    }

    public void setAllSurfaces(List<Pair_4<Vector_3D, Vector_3D, Vector_3D, Color>> allSurfaces) {
        this.allSurfaces = allSurfaces;
    }

    public void setSurfaces(List<Pair_4<Integer, Integer, Integer, Color>> surfaces) {
        this.surfaces = surfaces;
    }
}
