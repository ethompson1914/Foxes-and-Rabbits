import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a sasquatch.
 * Sasquatches age, move, breed, die, and eat other animals.
 * 
 * @author Eric Thompson
 * @version December 6, 2010
 */
public class Sasquatch extends Animal {

	// Characteristics shared by all sasquatches (static fields).

    // The age at which a sasquatch can start to breed.
    private static final int BREEDING_AGE = 1;
    // The age to which a sasquatch can live.
    private static final int MAX_AGE = 1000;
    // The likelihood of a sasquatch breeding.
    private static final double BREEDING_PROBABILITY = .01;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).

    // The sasquatch's food level, which is increased by eating rabbits.
    private int foodLevel;
    
    /**
     * Constructor for objects of type Sasquatch
     * @param field The field the sasquatch is located in
     * @param location The location of the sasquatch in the field
     */
    public Sasquatch(Field field, Location location) {
    	super(field, location);
    	foodLevel = 7;
    }
    
    /**
     * Make this sasquatch more hungry. This could result in the sasquatches' death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Gets the sasquatch's age
     */
    protected int getAge() {
    	return age;
    }
    
    /**
     * Gets the sasquatch's max age
     */
    protected int getMaxAge() {
    	return MAX_AGE;
    }
    
    /**
     * Gets the sasquatch's breeding age
     */
	protected int getBreedingAge() {
		return BREEDING_AGE;
	}
	
	/**
     * Gets the sasquatch's breeding probability
     * @return BREEDING_PROBABILITY The sasquatch's breeding probability
     */
    protected double getBreedingProbability() {
    	return BREEDING_PROBABILITY;
    }

	/**
     * Gets the sasquatch's max litter size
     */
	protected int getMaxLitterSize() {
		return MAX_LITTER_SIZE;
	}

	/**
	 * Increments the sasquatche's age
	 * Increments the sasquatche's hunger
	 * Gives birth to new sasquatches
	 * Causes the sasquatch to hunt
	 */
	public void act(List<Actor> newSasquatches) {
		incrementAge();
		incrementHunger();
		if(alive) {
            giveBirth(newSasquatches);            
            // Move towards a source of food if found.
            Location newLocation = findFood(location);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = field.freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
        }
	}
	
	/**
	 * Causes the sasquatch to potentially give birth to new sasquatches
	 * @param newSasquatches
	 */
	private void giveBirth(List<Actor> newSasquatches)
    {
        // New sasquatches are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = field.getFreeAdjacentLocations(location);
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Sasquatch young = new Sasquatch(field, loc);
            newSasquatches.add(young);
        }
    }
	
	/**
     * Tell the sasquatch to look for animals adjacent to its current location.
     * Eats all animals adjacent to it.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Location location)
    {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel += RABBIT_FOOD_VALUE;
                }
            }
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) { 
                    fox.setDead();
                    foodLevel += FOX_FOOD_VALUE;
                }
            }
        }
        return null;
    }
}
