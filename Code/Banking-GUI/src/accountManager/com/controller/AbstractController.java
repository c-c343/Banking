package accountManager.com.controller;
import accountManager.com.model.Model;
import accountManager.com.view.View;

/**
 * 
 * @author 
 * Abstract Controller class 
 * All the controllers will have the following attributes
 *
 */
public abstract class AbstractController implements Controller {

	/**
	 * view 
	 */
	private View view;
	/**
	 * model
	 */
	private Model model;
	
	
	public void setModel(Model model)
	{
		this.model = model;
	}
	
	public Model getModel()
	{
		return model;
	}
	
	public View getView()
	{
		return view;
	}
	
	public void setView(View view)
	{
		this.view = view;
	}
}
