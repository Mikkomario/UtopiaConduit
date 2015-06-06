package conduit_test;

import conduit_camera.Camera;
import genesis_event.EventSelector;
import genesis_event.HandlerRelay;
import genesis_event.KeyEvent;
import genesis_event.KeyListener;
import genesis_util.StateOperator;
import genesis_util.Vector3D;
import omega_util.SimpleGameObject;
import omega_util.Transformable;
import omega_util.Transformation;

/**
 * This camera is used in the testing
 * 
 * @author Mikko Hilpinen
 * @since 24.12.2014
 */
public class TestCamera extends SimpleGameObject implements Transformable, KeyListener, Camera
{
	// ATTRIBUTES	------------------------
	
	private Transformation transformation;
	private EventSelector<KeyEvent> selector;
	
	
	// CONSTRUCTOR	------------------------
	
	/**
	 * Creates a new camera
	 * @param handlers The handlers that will handle this camera
	 */
	public TestCamera(HandlerRelay handlers)
	{
		super(handlers);
		
		this.transformation = new Transformation();
		this.selector = KeyEvent.createEventTypeSelector(KeyEvent.KeyEventType.DOWN);
	}
	
	
	// IMPLEMENTED METHODS	----------------

	@Override
	public EventSelector<KeyEvent> getKeyEventSelector()
	{
		return this.selector;
	}

	@Override
	public StateOperator getListensToKeyEventsOperator()
	{
		return getIsActiveStateOperator();
	}

	@Override
	public void onKeyEvent(KeyEvent event)
	{
		// Moves with WASD, Q and E rotate, R and F scale
		Transformation t = null;
		double v = event.getDuration();
		
		switch (event.getKey())
		{
			case 'w':
				t = Transformation.transitionTransformation(new Vector3D(0, -v));
				break;
			case 'a':
				t = Transformation.transitionTransformation(new Vector3D(-v, 0));
				break;
			case 'd':
				t = Transformation.transitionTransformation(new Vector3D(v, 0));
				break;
			case 's':
				t = Transformation.transitionTransformation(new Vector3D(0, v));
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
	public Vector3D getOrigin()
	{
		//return Vector3D.zeroVector();
		return new Vector3D(400, 300);
	}
}
