package accountManager.com.model;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * @author 
 * Base Model for all the models
 *
 */
public abstract class AbstractModel implements Model {
	/**
	 * Listeners listening for events
	 */
	private List<ModelListener> listeners = new ArrayList<ModelListener>(5);
	
	/**
	 * Call modelChanged function for all the listeners
	 */
	public void notifyChanged(ModelEvent event) {
		for(ModelListener ml : listeners){
			ml.modelChanged(event);
		}
	}
	/**
	 * 
	 * @param l Listener to be added
	 * Add the passed listener to the arrayList
	 */
	public void addModelListener(ModelListener l){
		listeners.add(l);
	}
	
	/**
	 * 
	 * @param l listener to be removed
	 * remove this listener from the arraylist
	 */
	public void removeModelListener(ModelListener l){
		listeners.remove(l);
	}

}
