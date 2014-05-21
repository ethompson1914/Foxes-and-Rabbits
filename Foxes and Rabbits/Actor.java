import java.util.List;


public interface Actor {

	/**
	 * What all actors do ... they act
	 * @param actors The list into which any new born Actors be placed
	 */
	void act(List<Actor> actors);
	
	boolean isAlive();
}
