package utopia.conduit.test;

import utopia.conduit.camera.Camera;
import utopia.genesis.event.KeyEvent;
import utopia.genesis.event.KeyListener;
import utopia.genesis.util.Transformable;
import utopia.genesis.util.Transformation;
import utopia.genesis.util.Vector3D;
import utopia.inception.event.EventSelector;
import utopia.inception.util.SimpleHandled;

/**
 * This camera is used in the testing
 * @author Mikko Hilpinen
 * @since 24.12.2014
 */
public class TestCamera extends SimpleHandled implements Transformable, KeyListener, Camera
{
	// ATTRIBUTES	------------------------
	
	private Transformation transformation;
	private EventSelector<KeyEvent> selector;
	private Vector3D origin;
	
	
	// CONSTRUCTOR	------------------------
	
	/**
	 * Creates a new camera
	 * @param size The size of the camera's area
	 */
	public TestCamera(Vector3D size)
	{
		this.transformation = new Transformation();
		this.selector = KeyEvent.createEventTypeSelector(KeyEvent.KeyEventType.DOWN);
		this.origin = size.dividedBy(2);
	}
	
	
	// IMPLEMENTED METHODS	----------------

	@Override
	public EventSelector<KeyEvent> getKeyEventSelector()
	{
		return this.selector;
	}

	@Override
	public void onKeyEvent(KeyEvent event)
	{
		// Moves with WASD, Q and E rotate, R and F scale
		Transformation t = null;
		double v = event.getDurationMillis();
		
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
		return this.origin;
	}
}
