import itsc2214.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

/**
 * Implements Conway's Game of Life using a 2D array.
 */
public class Project1 implements GameOfLife {
    private boolean[][] current;
    private boolean[][]next;
    
     /**
     * Default constructor. Initializes current and next arrays as null.
     */
    public Project1() {      
        this.current = null;
        this.next = null; 
    }

    /**
     * Constructs a Project1 object with specified rows and columns.
     * @param row number of rows.
     * @param col number of columns.
     */
    public Project1(int row, int col) {
        this.current = new boolean[row][col];
        this.next = new boolean[row][col]; 
    }

    /**
     * Returns number of rows in the current grid.
     * @return number of rows, or 0 if grid is null.
     * 
     */
    public int numRows(){
        if(current == null){
            return 0;
        }
        return current.length;
    }

    /**
     * Returns number of columns in the current grid.
     * @return number of columns, or 0 if grid is null or empty.
     */
    public int numCols(){
        if(current == null || current.length == 0){
            return 0;
        }
        return current[0].length;
    }

    /**
     * Checks if a cell at position (r, c) is alive.
     * @param r row index.
     * @param c column index.
     * @return true if cell is alive, false otherwise.
     */
    public boolean isAlive(int r, int c){
        if(r > numRows()-1 || r < 0 || c > numCols()-1 || c < 0){
            return false;
        }
        return current[r][c];
    }

    /**
     * Checks to see if a a grid is unchanging through next generations.
     * @return true if grid is a still life, false otherwise.
     */
    public boolean isStillLife(){
        // Check if the next generation is still in it's initial state.
        for(int r = 0; r < numRows(); r++){
            for(int c = 0; c < numCols(); c++){
                if(current[r][c] != next[r][c]){
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Advances the game to the next generation.
     */
    public void nextGeneration(){
   
        for(int r = 0; r < numRows(); r++){
            for(int c = 0; c < numCols(); c++){
                int neighbors = countLiveNeighbors(r, c);

                if(isAlive(r,c)){
                    if(neighbors == 2 || neighbors == 3){
                        next[r][c] = true;
                    } else {
                        next[r][c] = false;
                    }
                } else {
                    if(neighbors == 3){
                        next[r][c] = true;
                    } else {
                        next[r][c] = false;
                    }
                }
            }
        }
        boolean[][] temp = current;
        current = next;
        next = temp;
    }
    

    /**
     * Counts live neighbors for cell at position (r, c).
     * @param r row index.
     * @param c column index.
     * @return number of live neighbors.
     */
    public int countLiveNeighbors(int r, int c){
        int liveCount = 0;
        for(int i = r-1; i <= r+1; i++){
            for(int j = c-1; j <= c+1; j++){
                if(!(i == r && j == c) && isAlive(i, j)){
                    liveCount++;
                }

            }

        }
        return liveCount;
    }

    /**
     * Loads the grid from a file.
     * @param filename name of the file to load.
     * @throws FileNotFoundException if file is not found.
     */
    public void loadFromFile(String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        StringBuilder sb = new StringBuilder();

        while(scanner.hasNextLine()){
            sb.append(scanner.nextLine());
            sb.append("\n");
            
        }
        scanner.close();
        loadFromString(sb.toString());
    }
    /**
     * Loads the grid from a string.
     * @param data string containing grid data.
     */
    public void loadFromString(String data){
        Scanner sc = new Scanner(data);
        int r = sc.nextInt(); 
        int c = sc.nextInt(); 
        sc.nextLine(); 
        current = new boolean[r][c];
        next = new boolean[r][c];

        for (int i = 0; i < r; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < c; j++) {
                char ch = line.charAt(j);
                if (ch == 'O') {
                    current[i][j] = true;
                } else {
                    current[i][j] = false;
                }
            }
        }
        sc.close();
    }
    
    /**
     * Randomly initializes live or dead cells based on probability.
     * @param aliveProbability probability of a cell being alive.
     */
    public void randomInitialize(double aliveProbability){
        Random rand = new Random();
        for(int i = 0; i < numRows(); i++){
            for(int j = 0; j < numCols(); j++){
                current[i][j] = rand.nextDouble() < aliveProbability;
                next[i][j] = false; 

            }
        }
    }
}