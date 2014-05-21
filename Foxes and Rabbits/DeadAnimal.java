import java.util.List;

/**
 * @author Eric
 * @version December 6, 2010
 * Empty class. Created to cause animals hit by the sniper to turn red and bleed
 * out after the season is over.
 */
public class DeadAnimal extends Animal implements Actor {

	public DeadAnimal(Field field, Location location) {
		super(field, location);
		age = 0;
	}

	/* (non-Javadoc)
	 * @see Animal#getAge()
	 */
	@Override
	protected int getAge() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see Animal#getMaxAge()
	 */
	@Override
	protected int getMaxAge() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see Animal#getBreedingAge()
	 */
	@Override
	protected int getBreedingAge() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see Animal#getMaxLitterSize()
	 */
	@Override
	protected int getMaxLitterSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see Animal#getBreedingProbability()
	 */
	@Override
	protected double getBreedingProbability() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see Animal#act(java.util.List)
	 */
	@Override
	public void act(List<Actor> animals) {
		if(age >= getMaxAge()) {
			field.clear(location);
		}

	}
	
	

}
