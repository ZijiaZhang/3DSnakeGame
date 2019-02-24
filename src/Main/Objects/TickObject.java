package Objects;

import Exceptions.AlreadyDestroyedException;
import Interfaces.Destroyable;
import World.Level;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TickObject extends Object implements Destroyable, Runnable{
    //protected TickTimer tick;
    protected Thread t;
    protected int delay;
    private long prevTime = 0;
    public TickObject(int tickRate, Level l){
        super();
        this.l = l;
        delay = tickRate;

    }

    @Override
    public void run() {
            tick();
            setChanged();
            notifyObservers("Finished");
    }

    public void start(){
        if(prevTime == 0|| System.currentTimeMillis()-prevTime > delay) {
            prevTime = System.currentTimeMillis();
            if(t== null || !t.isAlive()) {
                t = new Thread(this, "");
                t.start();
            }
        }
        setChanged();
        notifyObservers("Finished");
    }

    public abstract void tick();

    //MODIFIES: this
    //EFFECT: Disable the timer when the object is no longer in use.
    @Override
    public void destroy() throws AlreadyDestroyedException {
//        if(!tick.isWorking()){
//            throw new AlreadyDestroyedException(this.name);
//        }
//        tick.cancel();
        setChanged();
        notifyObservers("Destroyed");
        if(l!=null)
            l.destroy(this);
    }

    public boolean isLive(){
        if(t!=null) {
            return t.isAlive();
        }
        return false;
    }
}
