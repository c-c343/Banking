package accountManager.com.view;
import accountManager.com.controller.Controller;
/**
 * View interface
 */
import accountManager.com.model.Model;
public interface View {
	Controller getController();
	public void setController(Controller aController);
	public Model getModel();
	public void setModel(Model aModel);
}
