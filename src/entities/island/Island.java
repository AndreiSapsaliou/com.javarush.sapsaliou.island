package entities.island;

public class Island {
    private Location[][] locations;

    public Island(int x,int y) {
        locations = new Location[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                locations[i][j] = new Location(i,j);
            }
        }
    }

    public Location[][] getLocations(){
        return locations;
    }

}
