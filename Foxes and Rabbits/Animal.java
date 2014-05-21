import java.util.List;
import java.util.Random;

/**
 * The superclass for all objects of type animal (Fox, Rabbit, Sasquatch)
 * Stores methods in which all subclasses share
 * 
 * @author Eric
 * @version December 6, 2010
 */
public abstract class Animal implements Actor {

	protected int age;
	protected boolean alive;
	protected Location location;
	protected Field field;
	protected Random rand;
	// The food value of a single rabbit. In effect, this is the
    // number of steps an animal (fox or sasquatch) can go before it has to eat again.
	protected int RABBIT_FOOD_VALUE = 7;
	// The food value of a single fox. In effect, this is the
    // number of steps an animal (sasquatch) can go before it has to eat again.
	protected int FOX_FOOD_VALUE = 7;
    
	/**
	 * Abstract superclass constructor for Animal objects
	 * @param field
	 * @param location
	 */
	public Animal(Field field, Location location)
    {
        age = 0;
        alive = true;
        this.field = field;
        setLocation(location);
        rand = new Random();
        rand = Randomizer.getRandom();
    }
	
	/**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
	
	/**
	 * Mutator for location field
	 * @param newLocation The new location for this animal
	 */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Kills the animal and clears it from the field
     */
    public void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Increase the age of the animal
     * This could result in the animal's death.
     */
    public void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * Tells whether the animal is still alive or not
     * @return alive Whether or not the animal is alive
     */
    public boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Accessor for location property
     * @return The location of the animal
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Gets whether the animal is old enough to start being intimate with
     * other animals of its species
     * @return Whether or not the animal is old enough to breed
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    /**
     * Gets the food value of a rabbit
     * @return RABBIT_FOOD_VALUE The food value of a rabbit
     */
    public int getRabbitFoodValue() {
    	return RABBIT_FOOD_VALUE;
    }
    
    /**
     * Ensures all subclasses will have a getAge method
     */
    abstract protected int getAge();
    
    /**
     * Ensures all subclasses will have a getAge method
     */
    abstract protected int getMaxAge();
    
    /**
     * Ensures all subclasses will have a getBreedingAge method
     */
    abstract protected int getBreedingAge();
    
    /**
     * Ensures all subclasses will have a getMaxLitterSize method
     */
    abstract protected int getMaxLitterSize();
    
    /**
     * Ensures all subclasses will have a getBreedingProbability method
     */
    abstract protected double getBreedingProbability();
    
    /**
     * Ensures all subclasses will have an act method
     */
    abstract public void act(List<Actor> animals);
}
