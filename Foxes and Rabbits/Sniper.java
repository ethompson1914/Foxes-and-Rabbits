import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A "blind" sniper in a helicopter. Blind in the sense that
 * he fires randomly into the field: if he happens to hit an animal, it dies.
 * He is in a helicopter so that he can fly above the field. It does not
 * matter if he is in the same location as another actor.
 * 
 * @author Eric
 * @version December 6, 2010
 */
public class Sniper implements Actor {

	// How many shots he has to fire each season
	private int ammo;
	// Random number generator for firing blind shots
	private Random rand;
	// The field the sniper is in
	private Field field;
	// The GUI for the simulation
	private SimulatorView view;
	
	/**
	 * Constructor for objects of type Sniper
	 */
	public Sniper(Field field, SimulatorView view) {
		ammo = 10;
		this.field = field;
		rand = Randomizer.getRandom();
		this.view = view;
	}
    
	/**
	 * Act method for the sniper. He fires blindly into the field, sometimes
	 * killing any animal he happens to hit
	 */
	public void act(List<Actor> actors) {
		// Blank method
	}
	
	/**
	 * Act method for the sniper. He fires blindly into the field, sometimes
	 * killing any animal he happens to hit
	 */
	public void shootStuff() {
		// If the sniper still has ammo left...
		while (ammo > 0) {
			int row = rand.nextInt(Simulator.getxDim());
			int col = rand.nextInt(Simulator.getyDim());
			Location shot = new Location(row, col);
			if (field.getObjectAt(shot) != null) {
				field.clear(shot);
				DeadAnimal temp = new DeadAnimal(field, shot);
				field.place(temp, shot);
				try{
					// Slows down the thread so that you can see the animals
					// that the hunter managed to hit
					wait(1000);
				}
				catch (Exception e) {}
			}
			ammo--;
		}
	}
	
	/**
	 * After each season, the sniper reloads for the next season
	 */
	public void reload() {
		ammo = 10;
	}

	/**
	 * Whether or not the sniper is still alive. This is an absolutely pointless
	 * method, and just took extra time to write. However, the program doesn't 
	 * compile if it's not here, because the sniper implements the Actor interface.
	 * Because he does not die, this is a method to always return true.
	 */
	public boolean isAlive() {
		return true;
	}

}
