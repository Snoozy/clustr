public class Compute {
    public Quadrant[][] compute(Coordinate a, Coordinate b, int numCol, int numRow, Coordinate[] users) {
        double quadWidth = (b.longitude - a.longitude) / numCol;
        double quadHeight = (a.latitude - b.latitude) / numRow;
        Quadrant[][] map = new Quadrant[numRow][numCol];
        for (int row = 0; row < numRow; row++) {
            double currLat = a.latitude + row * quadHeight;
            for (int col = 0; col < numCol; col++) {
                double currLong = a.longitude + col * quadWidth;
                for (Coordinate user : users) {
                    map[row][col] = new Quadrant(new Coordinate(currLong, currLat), 0);
                    if (user.longitude >= currLong && user.longitude < currLong + quadWidth
                        && user.latitude <= currLat && user.latitude > currLat - quadHeight) {
                        map[row][col].users++;
                    }
                }
            }
        }
        return map;
    }
}