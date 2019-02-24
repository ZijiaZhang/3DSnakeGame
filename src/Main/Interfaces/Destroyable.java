package Interfaces;

import Exceptions.AlreadyDestroyedException;

public interface Destroyable {
    public void destroy() throws AlreadyDestroyedException;
}
