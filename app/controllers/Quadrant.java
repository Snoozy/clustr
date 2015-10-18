package controllers;

public class Quadrant {
    public Coordinate coordinate;
    public int users;

    public Quadrant(Coordinate coordinate, int users) {
        this.coordinate = coordinate;
        this.users = users;
    }
}