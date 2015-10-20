package controllers;

import java.util.*;

public class Compute {

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