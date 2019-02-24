package Objects;

import java.util.Timer;
import java.util.TimerTask;

public class TickTimer extends Timer {
    private boolean working;
    public TickTimer(int tickRate, TimerTask t){
        super();
        scheduleAtFixedRate(t,tickRate,tickRate);
        working = true;
    }

    public boolean isWorking() {
        return working;
    }

    @Override
    public void cancel() {
        super.cancel();
        working =false;
    }
}
