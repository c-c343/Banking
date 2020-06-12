package accountManager.com.view;
import javax.swing.JFrame;

import accountManager.com.controller.Controller;
import accountManager.com.model.AbstractModel;
import accountManager.com.model.Model;
import accountManager.com.model.ModelListener;
/**
 * 
 * @author 
 *
 */
public abstract class JFrameView extends JFrame implements View, ModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Model model;
	private Controller controller;

	public JFrameView (Model model, Controller controller){
		setModel(model);
		setController(controller);
	}
	/**
	 * registers the view with the model.
	 */
	public void registerWithModel(){
		((AbstractModel)model).addModelListener(this);
	}
	public void unregisterWithModel(){
		((AbstractModel)model).removeModelListener(this);
	}
	
	public Controller getController(){return controller;}
	
	
	public void setController(Controller controller){
		this.controller = controller;
	}
	
	public Model getModel(){return model;}
	
	
	public void setModel(Model model) {
		this.model = model;
		registerWithModel();
	}

}
