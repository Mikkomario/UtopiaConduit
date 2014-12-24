package conduit_test;

import conduit_camera.Camera;
import genesis_event.AdvancedKeyEvent;
import genesis_event.AdvancedKeyListener;
import genesis_event.EventSelector;
import genesis_event.HandlerRelay;
import genesis_util.StateOperator;
import genesis_util.Vector2D;
import omega_util.SimpleGameObject;
import omega_util.Transformable;
import omega_util.Transformation;

/**
 * This camera is used in the testing
 * 
 * @author Mikko Hilpinen
 * @since 24.12.2014
 */
public class TestCamera extends SimpleGameObject implements Transformable,
		AdvancedKeyListener, Camera
{
	// ATTRIBUTES	------------------------
	
	private Transformation transformation;
	private EventSelector<AdvancedKeyEvent> selector;
	
	
	// CONSTRUCTOR	------------------------
	
	/**
	 * Creates a new camera
	 * @param handlers The handlers that will handle this camera
	 */
	public TestCamera(HandlerRelay handlers)
	{
		super(handlers);
		
		this.transformation = new Transformation();
		this.selector = AdvancedKeyEvent.createEventTypeSelector(
				AdvancedKeyEvent.KeyEventType.DOWN);
	}
	
	
	// IMPLEMENTED METHODS	----------------

	@Override
	public EventSelector<AdvancedKeyEvent> getKeyEventSelector()
	{
		return this.selector;
	}

	@Override
	public StateOperator getListensToKeyEventsOperator()
	{
		return getIsActiveStateOperator();
	}

	@Override
	public void onKeyEvent(AdvancedKeyEvent event)
	{
		// Moves with WASD, Q and E rotate, R and F scale
		Transformation t = null;
		double v = event.getDuration();
		
		switch (event.getKey())
		{
			case 'w':
				t = Transformation.transitionTransformation(new Vector2D(0, -v));
				break;
			case 'a':
				t = Transformation.transitionTransformation(new Vector2D(-v, 0));
				break;
			case 'd':
				t = Transformation.transitionTransformation(new Vector2D(v, 0));
				break;
			case 's':
				t = Transformation.transitionTransformation(new Vector2D(0, v));
				break;
			case 'q':
				t = Transformation.rotationTransformation(v);
				break;
			case 'e':
				t = Transformation.rotationTransformation(-v);
				break;
			case 'r':
				t = Transformation.scalingTransformation(Math.pow(1.05, v));
				break;
			case 'f':
				t = Transformation.scalingTransformation(Math.pow(0.9, v));
				break;
		}
		
		if (t != null)
			setTrasformation(getTransformation().plus(t));
	}

	@Override
	public Transformation getTransformation()
	{
		return this.transformation;
	}

	@Override
	public void setTrasformation(Transformation t)
	{
		this.transformation = t;
	}

	@Override
	public Vector2D getOrigin()
	{
		//return Vector2D.zeroVector();
		return new Vector2D(400, 300);
	}
}
