package accountManager.com.controller;
import accountManager.com.model.Model;
import accountManager.com.view.View;
/**
 * 
 * @author 
 * Controller Interface
 */
public interface Controller {
	void setModel(Model model);
	Model getModel();
	View getView();
	void setView(View view);
}
