package controllers;

import java.util.*;

public class Compute {
    public static void main(String[] args) {
        Coordinate[] users = new Coordinate[10];
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            users[i] = new Coordinate(rand.nextInt(100), rand.nextInt(100));
            System.out.println("User " + i + ": " + users[i].latitude + ", " + users[i].longitude);
        }
        Quadrant[][] map = compute(new Coordinate(100, 0), new Coordinate(0, 100), 10, 10, users);
        for (Quadrant[] row : map) {
            for (Quadrant q : row) {
                System.out.print(q.users + ", ");
            }
            System.out.println();
        }
    }

    public static Quadrant[][] compute(Coordinate a, Coordinate b, int numCol, int numRow, Coordinate[] users) {
        double quadWidth = (b.longitude - a.longitude) / numCol;
        double quadHeight = (a.latitude - b.latitude) / numRow;
        Quadrant[][] map = new Quadrant[numRow][numCol];
        for (int row = 0; row < numRow; row++) {
            double currLat = a.latitude - row * quadHeight;
            System.out.print("current Lat: " + currLat + " ");
            for (int col = 0; col < numCol; col++) {
                double currLong = a.longitude + col * quadWidth;
                System.out.print(currLong + ", ");
                map[row][col] = new Quadrant(new Coordinate(currLat, currLong), 0);
                for (Coordinate user : users) {
                    if (user.longitude >= currLong && user.longitude < currLong + quadWidth
                        && user.latitude <= currLat && user.latitude > currLat - quadHeight) {
                        map[row][col].users++;
                    }
                }
            }
            System.out.println();
        }
        return map;
    }
}