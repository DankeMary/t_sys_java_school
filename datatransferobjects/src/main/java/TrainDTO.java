public class TrainDTO {
    private int id;
    private int number;
    private int capacity;
    private TripDTO trip;

    public TrainDTO(int id, int number, int capacity, TripDTO trip) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.trip = trip;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }

    public TripDTO getTrip() {
        return trip;
    }
}
