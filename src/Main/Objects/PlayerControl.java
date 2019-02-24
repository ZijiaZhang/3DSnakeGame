package Objects;

public class PlayerControl {
    private DisplayObjectWithTick controlledObject;
    //EFFECT: set controlled Object
    public void setControlledObject(DisplayObjectWithTick controlledObject) {
        this.controlledObject = controlledObject;
    }
    //EFFECT: get controlled Object
    public DisplayObjectWithTick getControlledObject() {
        return controlledObject;
    }
}
