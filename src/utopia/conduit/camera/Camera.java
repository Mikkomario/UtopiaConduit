package utopia.conduit.camera;

import utopia.genesis.util.Transformable;
import utopia.genesis.util.Vector3D;

/**
 * Cameras are objects that can be used for modifying what can be seen on the screen at any 
 * given time.
 * @author Mikko Hilpinen
 * @since 23.12.2014
 */
public interface Camera extends Transformable
{
	/**
	 * @return The relative coordinates of the camera's origin (usually the center). This is 
	 * the point the camera is rotated around, for example. The coordinates are calculated 
	 * from the top-left corner of the camera's area.
	 */
	public Vector3D getOrigin();
}
