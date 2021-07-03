package Brick;

import java.awt.*;

public class MapGenerator {
    public int[][] map;
    public int BrickWidth;
    public int BrickHeight;
    public MapGenerator(int row, int col){
        map = new int[row][col];
        for(int i = 0; i < map.length; i++) {
            for (int j=0; j < map[0].length; j++){
                if(!(i==row-1 && j==0 || i==row-1 && j==col-1))
                    map[i][j] = 1;
            }
        }

        BrickHeight = 200/row;
        BrickWidth = 400/col;

    }
    public void draw(Graphics2D g){
        for(int i = 0; i < map.length; i++) {
            for (int j=0; j < map[0].length; j++){
                if(map[i][j] > 0){
                    g.setColor(Color.MAGENTA);
                    g.fillRect(j * BrickWidth + 100, i * BrickHeight + 75, BrickWidth, BrickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * BrickWidth + 100, i * BrickHeight + 75, BrickWidth, BrickHeight);
                }
            }
        }
    }
    public void SetBrickValue(int row, int col) {
        map[row][col] = 0;
    }
}
