package conduit_camera;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import omega_util.Transformation;
import genesis_event.DrawableHandler;
import genesis_event.HandlerRelay;

/**
 * CameraDrawer is a specialized drawableHandler that takes camera's transformations into 
 * account when drawing the objects.
 * 
 * @author Mikko Hilpinen
 * @since 23.12.2014
 */
public class CameraDrawer extends DrawableHandler
{
	// ATTRIBUTES	-------------------------
	
	private Camera camera;
	
	
	// CONSTRUCTOR	-------------------------
	
	/**
	 * Creates a new drawer
	 * @param camera That affects the drawing
	 * @param autoDeath Will the handler die once it runs out of handleds
	 */
	public CameraDrawer(Camera camera, boolean autoDeath)
	{
		super(autoDeath);
		
		this.camera = camera;
	}

	/**
	 * Creates a new drawer
	 * @param camera That affects the drawing
	 * @param autoDeath Will the handler die once it runs out of handleds
	 * @param superHandler The handler that will draw this handler
	 */
	public CameraDrawer(Camera camera, boolean autoDeath, DrawableHandler superHandler)
	{
		super(autoDeath, superHandler);
		this.camera = camera;
	}

	/**
	 * Creates a new drawer
	 * @param camera That affects the drawing
	 * @param autoDeath Will the handler die once it runs out of handleds
	 * @param handlers The handlers that will handle this handler
	 */
	public CameraDrawer(Camera camera, boolean autoDeath, HandlerRelay handlers)
	{
		super(autoDeath, handlers);
		this.camera = camera;
	}

	/**
	 * Creates a new drawer
	 * @param camera That affects the drawing
	 * @param autoDeath Will the handler die once it runs out of handleds
	 * @param usesDepth Does the drawer use depth sorting
	 * @param depth The drawing depth of the camera's contents
	 * @param depthSortLayers How many depth sorting layers will be used (smaller if contents 
	 * don't change their depth, larger if they do. No more than 10 is recommended)
	 */
	public CameraDrawer(Camera camera, boolean autoDeath, boolean usesDepth, int depth,
			int depthSortLayers)
	{
		super(autoDeath, usesDepth, depth, depthSortLayers);
		this.camera = camera;
	}

	/**
	 * Creates a new drawer
	 * @param camera That affects the drawing
	 * @param autoDeath Will the handler die once it runs out of handleds
	 * @param usesDepth Does the drawer use depth sorting
	 * @param depth The drawing depth of the camera's contents
	 * @param depthSortLayers How many depth sorting layers will be used (smaller if contents 
	 * don't change their depth, larger if they do. No more than 10 is recommended)
	 * @param superhandler The handler that will handle this handler
	 */
	public CameraDrawer(Camera camera, boolean autoDeath, boolean usesDepth, int depth,
			int depthSortLayers, DrawableHandler superhandler)
	{
		super(autoDeath, usesDepth, depth, depthSortLayers, superhandler);
		this.camera = camera;
	}

	/**
	 * Creates a new drawer
	 * @param camera That affects the drawing
	 * @param autoDeath Will the handler die once it runs out of handleds
	 * @param usesDepth Does the drawer use depth sorting
	 * @param depth The drawing depth of the camera's contents
	 * @param depthSortLayers How many depth sorting layers will be used (smaller if contents 
	 * don't change their depth, larger if they do. No more than 10 is recommended)
	 * @param superHandlers The handlers that will handle this handler
	 */
	public CameraDrawer(Camera camera, boolean autoDeath, boolean usesDepth, int depth,
			int depthSortLayers, HandlerRelay superHandlers)
	{
		super(autoDeath, usesDepth, depth, depthSortLayers, superHandlers);
		this.camera = camera;
	}

	
	// IMPLEMENTED METHODS	---------------------------------
	
	@Override
	public void drawSelf(Graphics2D g2d)
	{
		// The camera's transformations affect the drawing
		AffineTransform lastTransform = g2d.getTransform();
		this.camera.getTransformation().inverse().transform(g2d);
		// Also transforms the origin
		Transformation.transitionTransformation(this.camera.getOrigin()).transform(g2d);
		super.drawSelf(g2d);
		g2d.setTransform(lastTransform);
	}
}
