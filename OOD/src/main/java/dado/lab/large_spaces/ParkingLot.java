package dado.lab.large_spaces;

import dado.lab.aux_fields.Capacity;
import dado.lab.aux_fields.SpotType;
import dado.lab.managed_objects.Car;

import java.util.*;

/*
* principle:
* -> large space calls small to implement the function.
* -> [owner of the field] defines the [behavior (method)].
*
* large space: lot
* unit space: spot
*   capacity
*   state (fill/empty)
*   influenced by: parking leaving -> state needed
*   methods: park, leave, check
* object: car
*   size
*   identity
* add-ons: time interval, towling when exceed time limit, space management & spot recommendation
* */
public class ParkingLot {

    class Spot {
        Capacity capacity;
        Car car;
        SpotType type;//Extendable (e.g. EV spot with charging post...)

        // --- small space implement of all operations ---
        boolean park(Car car) {return false;}//park the car

        void leave() {}//leave the spot

        boolean check(Car car) {return false;}//can park here?
    }

    List<Spot> spots;

    public boolean park(Car car) {return false;}//park the car

    public void leave() {}//leave the spot

    public boolean check(Car car) {return false;}//can park here?
}
