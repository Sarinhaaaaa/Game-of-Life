import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;

/**
* The JUnit file used to test Project1.java.
*/
public class Project1Test {

    private Project1 runner;


    /**
    * setup() method, creates a game of life with 3x3 grid.
    */
    @Before
    public void setup(){
        runner = new Project1(3,3);
    }

    /**
    * Checks basic setup of 3x3.
    */
    @Test
    public void testOne(){
        String inputData = "3 3\nO..\n...\n...\n";
        runner.loadFromString(inputData);
        assertEquals(3, runner.numRows());
        assertEquals(3, runner.numCols());
        runner.nextGeneration();
        assertFalse("stillLife before generations should be false", runner.isStillLife());
    }

    /**
    * Checks a basic initial grid.
    */
    @Test
    public void testSmallGrid(){
        String inputData =
            "3 3\n" +
            "O..\n" +
            "...\n" +
            "...\n";
        runner.loadFromString(inputData);
        // check position 0,0
        assertTrue("Position 0,0 should be alive", 
            runner.isAlive(0,0));
        // run generation
        runner.nextGeneration();
        // check position 0,0
        assertFalse("Position 0,0 should NOT be alive", 
            runner.isAlive(0,0));
    }

    /**
    * Test default constructor.
    */
    @Test
    public void testDefaultConstructor() {
        Project1 defaultRunner = new Project1();
        assertEquals(0, defaultRunner.numRows());
        assertEquals(0, defaultRunner.numCols());
    }

    /**
    * Tests isStillLife with a still grid.
    */
    @Test
    public void testIsStillLifeStill() throws FileNotFoundException {
        runner.loadFromFile("empty.txt");
        runner.nextGeneration();
        assertTrue("Should be still life", runner.isStillLife());
    }

    /**
    * Tests isStillLife with changing grid.
    */
    @Test
    public void testIsStillLifeChanging() throws FileNotFoundException {
        runner.loadFromFile("example1.txt");
        runner.nextGeneration();
        assertFalse("Should not be still life", runner.isStillLife());
    }

    /**
    * Tests loadFromFile.
    * 
    */
    @Test
    public void testLoadFromFile() throws FileNotFoundException {
        runner.loadFromFile("full.txt");
        assertTrue("Position 0,0 should be alive", runner.isAlive(0,0));
    }

    /**
    * Tests randomInitialize.
    */
    @Test
    public void testRandomInitialize() {
        runner = new Project1(5,5);
        runner.randomInitialize(0.5);
        int liveCount = 0;
        for (int i = 0; i < runner.numRows(); i ++){
            for (int j = 0; j < runner.numCols(); j++){
                if(runner.isAlive(i, j)) liveCount ++;
            }
        }
        assertTrue("Should have some live cells", liveCount > 0 & liveCount < 25);
    }

    /**
    * Tests numRows with null grid.
    */
    @Test
    public void testNumRowsNull(){
        Project1 nullRunner = new Project1();
        assertEquals(0, nullRunner.numRows());
    }

    /**
    * Tests numCols with null grid.
    */

    @Test
    public void testNumColsNull(){
    Project1 nullRunner = new Project1();
        assertEquals(0, nullRunner.numCols());
    }

    /**
     * Tests numCols with a zero-length grid.
     */
    @Test
    public void testNumColsZeroLength(){
        Project1 zeroRunner = new Project1(0,5);
        assertEquals(0, zeroRunner.numCols());
    }

    /**
    * Tests nextGeneration with 3 neighbors.
    */
    @Test
    public void testNextGenerationLive() throws FileNotFoundException{
        runner.loadFromFile("blinker.txt");
        assertTrue("Position 2,1 should be alive initially", runner.isAlive(2,1));
        // blinker oscillates between the same to grid shapes, so initial spot should stay alive
        runner.nextGeneration();
        runner.nextGeneration(); 
        assertTrue("Position 2,2 should stay alive", runner.isAlive(2,1));
    }

    /**
    * Tests nextGeneration with 3 neighbors for dead cell.
    */

    @Test
    public void testNextGenerationDeadToLive() throws FileNotFoundException {
        runner.loadFromFile("glidder.txt");
        assertFalse("Position 4,1 should be dead initially", runner.isAlive(4, 1));
        runner.nextGeneration();
        assertTrue("Position 4,1 should become alive", runner.isAlive(4,1));
    }

}

