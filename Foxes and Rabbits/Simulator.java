import java.util.LinkedList;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author Eric Thompson
 * @version December 6, 2010
 */
public class Simulator
{
	// Constants representing configuration information for the simulation.
	// The default width for the grid.
	private static final int DEFAULT_WIDTH = 50;
	// The default depth of the grid.
	private static final int DEFAULT_DEPTH = 50;
	// The probability that a fox will be created in any given grid position.
	private static final double FOX_CREATION_PROBABILITY = 0.02;
	// The probability that a rabbit will be created in any given grid position.
	private static final double RABBIT_CREATION_PROBABILITY = 0.08;

	// Lists of animals in the field.
	private List<Actor> animals;
	// The current state of the field.
	private Field field;
	// The current step of the simulation.
	private int step;
	// A graphical view of the simulation.
	private SimulatorView view;
	// A random number generator
	private Random rand;
	// A sniper in the field
	private Sniper sniper;

	// Variables for creating the sasquatch at the beginning
	private static int xDim = 100;
	private static int yDim = 100;

	/**
	 * @return the xDim
	 */
	public static int getxDim() {
		return xDim;
	}

	/**
	 * @return the yDim
	 */
	public static int getyDim() {
		return yDim;
	}

	/**
	 * Main method to run simulation
	 */
	public static void main(String[] args) {
		Simulator s = new Simulator(yDim, xDim);
		while(true) {
			s.runLongSimulation();
			s.reset();
		}
	}

	/**
	 * Construct a simulation field with default size.
	 */
	public Simulator()
	{
		this(DEFAULT_DEPTH, DEFAULT_WIDTH);
	}

	/**
	 * Create a simulation field with the given size.
	 * @param depth Depth of the field. Must be greater than zero.
	 * @param width Width of the field. Must be greater than zero.
	 */
	public Simulator(int depth, int width)
	{
		if(width <= 0 || depth <= 0) {
			System.out.println("The dimensions must be greater than zero.");
			System.out.println("Using default values.");
			depth = DEFAULT_DEPTH;
			width = DEFAULT_WIDTH;
			xDim = width;
			yDim = depth;
		}
		else {
			xDim = DEFAULT_WIDTH;
			yDim = DEFAULT_DEPTH;
		}

		rand = Randomizer.getRandom();
		animals = new LinkedList<Actor>();
		field = new Field(width, depth);

		// Create a view of the state of each location in the field.
		view = new SimulatorView(depth, width);
		view.setColor(Rabbit.class, Color.orange);
		view.setColor(Fox.class, Color.blue);
		view.setColor(Sasquatch.class, Color.black);
		view.setColor(DeadAnimal.class, Color.red);

		// Create a sniper
		sniper = new Sniper(field, view);

		// Setup a valid starting point.
		reset();
	}

	/**
	 * Run the simulation from its current state for a reasonably long period,
	 * e.g. 500 steps.
	 */
	public void runLongSimulation()
	{
		simulate(5000);
	}

	/**
	 * Run the simulation from its current state for the given number of steps.
	 * Stop before the given number of steps if it ceases to be viable.
	 * @param numSteps The number of steps to run for.
	 */
	public void simulate(int numSteps)
	{
		for(int step = 1; step <= numSteps && view.isViable(field); step++) {
			simulateOneStep();
			try{
				// Comment in or out to slow down the simulation
				//Thread.sleep(1000);
			}
			catch (Exception e){}
		}
	}

	/**
	 * Gets the width of the GUI
	 * @return xDim The width of the GUI
	 */
	public int getWidth() {
		return xDim;
	}

	/**
	 * Gets the depth of the GUI
	 * @return yDim The depth of the GUI
	 */
	public int getDepth() {
		return yDim;
	}

	/**
	 * Run the simulation from its current state for a single step.
	 * Iterate over the whole field updating the state of each
	 * actor.
	 */
	public void simulateOneStep()
	{
		step++;

		List<Actor> newAnimals = new ArrayList<Actor>();        
		// Let all actors act.
		for(Iterator<Actor> it = animals.iterator(); it.hasNext(); ) {
			Actor a = it.next();
			a.act(newAnimals);
			if(! a.isAlive()) {
				it.remove();
			}
		}
		sniper.shootStuff();
		sniper.reload();

		animals.addAll(newAnimals);

		view.showStatus(step, field);
	}

	/**
	 * Reset the simulation to a starting position.
	 */
	public void reset()
	{
		step = 0;
		animals.clear();
		populate();

		// Show the starting state in the view.
		view.showStatus(step, field);
	}

	/**
	 * Randomly populate the field with foxes, rabbits, and a sasquatch.
	 */
	private void populate()
	{
		Random rand = Randomizer.getRandom();
		field.clear();
		
		// Create a sasquatch
		int row = rand.nextInt(yDim);
		int col = rand.nextInt(xDim);
		Location location = new Location(row, col);
		Sasquatch sasquatch = new Sasquatch(field, location);
		animals.add(sasquatch);

		for(row = 0; row < field.getDepth(); row++) {
			for(col = 0; col < field.getWidth(); col++) {
				if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
					location = new Location(row, col);
					Fox fox = new Fox(true, field, location);
					animals.add(fox);
				}
				else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
					location = new Location(row, col);
					Rabbit rabbit = new Rabbit(true, field, location);
					animals.add(rabbit);
				}
				// else leave the location empty.
			}
		}

		
	}
}