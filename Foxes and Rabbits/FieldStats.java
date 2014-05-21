
import java.util.HashMap;

/**
 * This class collects and provides some statistical data on the state 
 * of a field. It is flexible: it will create and maintain a counter 
 * for any class of object that is found within the field.
 * 
 * @author Eric Thompson
 * @version December 6, 2010
 */
public class FieldStats
{
	// Counters for each type of entity (fox, rabbit, etc.) in the simulation.
	private HashMap<Class, Counter> counters;
	// Whether the counters are currently up to date.
	private boolean countsValid;
	//
	private int foxWins = 0;
	private int rabbitWins = 0;

	/**
	 * Construct a FieldStats object.
	 */
	public FieldStats()
	{
		// Set up a collection for counters for each type of animal that
		// we might find
		counters = new HashMap<Class, Counter>();
		countsValid = true;
	}

	/**
	 * Get details of what is in the field.
	 * @return A string describing what is in the field.
	 */
	public String getPopulationDetails(Field field)
	{
		StringBuffer buffer = new StringBuffer();
		if(!countsValid) {
			generateCounts(field);
		}
		for(Class key : counters.keySet()) {
			Counter info = counters.get(key);
			buffer.append(info.getName());
			buffer.append(": ");
			buffer.append(info.getCount());
			buffer.append(' ');
		}
		return buffer.toString();
	}

	/**
	 * Invalidate the current set of statistics; reset all 
	 * counts to zero.
	 */
	public void reset()
	{
		countsValid = false;
		for(Class key : counters.keySet()) {
			Counter count = counters.get(key);
			count.reset();
		}
	}

	/**
	 * Increment the count for one class of animal.
	 * @param animalClass The class of animal to increment.
	 */
	public void incrementCount(Class animalClass)
	{
		Counter count = counters.get(animalClass);
		if(count == null) {
			// We do not have a counter for this species yet.
			// Create one.
			count = new Counter(animalClass.getName());
			counters.put(animalClass, count);
		}
		count.increment();
	}

	/**
	 * Indicate that an animal count has been completed.
	 */
	public void countFinished()
	{
		countsValid = true;
	}

	/**
	 * Determine whether the simulation is still viable.
	 * I.e., should it continue to run.
	 * @return true If there is more than one species alive.
	 */
	public String isViable(Field field)
	{
		// How many counts are non-zero.
		int nonZero = 0;
		if(!countsValid) {
			generateCounts(field);
		}
		for(Class key : counters.keySet()) {
			Counter info = counters.get(key);
			if(!key.equals(Sasquatch.class)) {
				if(!key.equals(DeadAnimal.class)) {
					if(info.getCount() > 0) {
						nonZero++;
					}
				}
			}
		}
		if(nonZero < 2) {
			if (counters.get(Fox.class).getCount() == 0) {
				rabbitWins++;
				return "rabbit";
			} 
			else {
				foxWins++;
				return "fox";
			}
		}
		return "";
	}

	/**
	 * Generate counts of the number of foxes and rabbits.
	 * These are not kept up to date as foxes and rabbits
	 * are placed in the field, but only when a request
	 * is made for the information.
	 * @param field The field to generate the stats for.
	 */
	private void generateCounts(Field field)
	{
		reset();
		for(int row = 0; row < field.getDepth(); row++) {
			for(int col = 0; col < field.getWidth(); col++) {
				Object animal = field.getObjectAt(row, col);
				if(animal != null) {
					incrementCount(animal.getClass());
				}
			}
		}
		countsValid = true;
	}
	
	/**
	 * Returns the number of fox wins
	 */
	public int getFoxWins() {
		return foxWins;
	}
	
	/**
	 * Returns the number of rabbit wins
	 */
	public int getRabbitWins() {
		return rabbitWins;
	}
}
