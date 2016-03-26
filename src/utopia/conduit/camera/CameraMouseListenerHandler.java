package utopia.conduit.camera;

import utopia.genesis.event.MouseListenerHandler;
import utopia.genesis.util.Transformation;
import utopia.genesis.util.Vector3D;

/**
 * This specialised mouse listener handler informs the objects about the mouse coordinates 
 * on game world, not on screen. This should be used for objects that are drawn under a camera.
 * @author Mikko Hilpinen
 * @since 23.12.2014
 */
public class CameraMouseListenerHandler extends MouseListenerHandler
{
	// ATTRIBUTES	-----------------------------
	
	private Camera camera;
	
	
	// CONSTRUCTOR	-----------------------------
	
	/**
	 * Creates a new handler. Remember to add the handler to a working actor handler afterwards.
	 * @param camera The camera that affects the mouse coordinates
	 */
	public CameraMouseListenerHandler(Camera camera)
	{
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
