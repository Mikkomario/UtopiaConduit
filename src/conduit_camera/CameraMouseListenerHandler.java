package conduit_camera;

import omega_util.Transformation;
import genesis_event.ActorHandler;
import genesis_event.HandlerRelay;
import genesis_event.MouseListenerHandler;
import genesis_util.Vector3D;

/**
 * This specialized mouse listener handler informs the objects about the mouse coordinates 
 * on game world, not on screen. This should be used for objects that are drawn under a camera.
 * 
 * @author Mikko Hilpinen
 * @since 23.12.2014
 */
public class CameraMouseListenerHandler extends MouseListenerHandler
{
	// ATTRIBUTES	-----------------------------
	
	private Camera camera;
	
	
	// CONSTRUCTOR	-----------------------------
	
	/**
	 * Creates a new handler
	 * @param camera The camera that affects the mouse coordinates
	 * @param autoDeath Will the handler die once it runs out of handleds
	 */
	public CameraMouseListenerHandler(Camera camera, boolean autoDeath)
	{
		super(autoDeath);
		
		this.camera = camera;
	}

	/**
	 * Creates a new handler
	 * @param camera The camera that affects the mouse coordinates
	 * @param autoDeath Will the handler die once it runs out of handleds
	 * @param superHandlers The handlers that will handle this handler
	 */
	public CameraMouseListenerHandler(Camera camera, boolean autoDeath,
			HandlerRelay superHandlers)
	{
		super(autoDeath, superHandlers);
		
		this.camera = camera;
	}

	/**
	 * Creates a new handler
	 * @param camera The camera that affects the mouse coordinates
	 * @param autoDeath Will the handler die once it runs out of handleds
	 * @param actorhandler The handler that will inform this handler about step events
	 * @param superhandler The handler that will inform this handler about mouse events 
	 */
	public CameraMouseListenerHandler(Camera camera, boolean autoDeath,
			ActorHandler actorhandler, MouseListenerHandler superhandler)
	{
		super(autoDeath, actorhandler, superhandler);
		
		this.camera = camera;
	}

	
	// IMPLEMENTED METHODS	-----------------------
	
	@Override
	public void setMousePosition(Vector3D mousePosition)
	{
		// The camera affects the mouse position
		// Origin as well
		Vector3D transformedPosition = Transformation.transitionTransformation(
				this.camera.getOrigin().reverse()).transform(mousePosition);
		transformedPosition = this.camera.getTransformation().transform(transformedPosition);
		
		super.setMousePosition(transformedPosition);
	}
}
