package dado.lab;

/*
* principle:
* -> large call small to implement the function.
* -> [owner of the field] defines the [behavior (method)].
* large space: lot
* unit space: spot
*   capacity
*   state (fill/empty)
*   influenced by: parking leaving -> state needed
*   methods: park, leave, check
* object: car
*   size
*   identity
*
* */
class Car {
    private String licensePlate;
    CarSize size;
}
public class ParkingLot {

    class Spot {
        Capacity capacity;
        SpotState state;
        Car car;
    }
}
