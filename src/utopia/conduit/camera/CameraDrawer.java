package utopia.conduit.camera;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import utopia.genesis.event.DrawableHandler;
import utopia.genesis.util.Transformation;

/**
 * CameraDrawer is a specialised drawableHandler that takes camera's transformations into 
 * account when drawing the objects.
 * @author Mikko Hilpinen
 * @since 23.12.2014
 */
public class CameraDrawer extends DrawableHandler
{
	// ATTRIBUTES	-------------------------
	
	private Camera camera;
	
	
	// CONSTRUCTOR	-------------------------
	
	/**
	 * Creates a new drawer that doesn't use depth sorting
	 * @param camera That affects the drawing
	 */
	public CameraDrawer(Camera camera)
	{
		this.camera = camera;
	}

	/**
	 * Creates a new drawer
	 * @param camera That affects the drawing
	 * @param usesDepth Does the drawer use depth sorting
	 * @param depth The drawing depth of the camera's contents
	 * @param depthSortLayers How many depth sorting layers will be used (smaller if contents 
	 * don't change their depth, larger if they do. No more than 10 is recommended)
	 */
	public CameraDrawer(Camera camera, boolean usesDepth, int depth, int depthSortLayers)
	{
		super(usesDepth, depth, depthSortLayers);
		this.camera = camera;
	}

	
	// IMPLEMENTED METHODS	---------------------------------
	
	@Override
	public void drawSelf(Graphics2D g2d)
	{
		// The origin and position are swapped, that's why it looks like that (but it works)
		// The camera's transformations affect the drawing
		AffineTransform lastTransform = g2d.getTransform();
		this.camera.getTransformation().inverse().withPosition(this.camera.getOrigin()).transform(g2d);
		// Also transforms the position
		Transformation.transitionTransformation(
				this.camera.getTransformation().getPosition().reverse()).transform(g2d);
		super.drawSelf(g2d);
		g2d.setTransform(lastTransform);
	}
}
