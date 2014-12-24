package conduit_test;

import java.awt.Color;
import java.awt.Graphics2D;

import conduit_camera.Camera;
import conduit_camera.CameraDrawer;
import genesis_event.Drawable;
import genesis_event.DrawableHandler;
import genesis_event.HandlerRelay;
import genesis_event.KeyListenerHandler;
import genesis_util.StateOperator;
import genesis_util.Vector2D;
import genesis_video.GamePanel;
import genesis_video.GameWindow;
import omega_util.SimpleGameObject;

/**
 * This class tests the basic camera features introduced in this module
 * 
 * @author Mikko Hilpinen
 * @since 24.12.2014
 */
public class ConduitTest
{
	// CONSTRUCTOR	-----------------------
	
	private ConduitTest()
	{
		// The constructor is hidden since the interface is static
	}

	
	// MAIN METHOD	-----------------------
	
	/**
	 * Starts the tests
	 * @param args Not used
	 */
	public static void main(String[] args)
	{
		// Creates the window
		GameWindow window = new GameWindow(new Vector2D(800, 600), "Conduit Test", true, 
				120, 20);
		GamePanel panel = window.getMainPanel().addGamePanel();
		
		// Creates the basic handlers
		HandlerRelay baseHandlers = new HandlerRelay();
		baseHandlers.addHandler(new KeyListenerHandler(true, window.getHandlerRelay()));
		baseHandlers.addHandler(new DrawableHandler(true, panel.getDrawer()));
		
		// Creates the camera
		Camera camera = new TestCamera(baseHandlers);
		
		// Creates the camera handlers
		HandlerRelay cameraHandlers = new HandlerRelay();
		cameraHandlers.addHandler(new KeyListenerHandler(true, baseHandlers));
		cameraHandlers.addHandler(new CameraDrawer(camera, true, baseHandlers));
		
		// Creates the test objects
		new TestObject(new Vector2D(10, 10), new Vector2D(780, 580), baseHandlers);
		for (int i = 0; i < 5; i++)
		{
			new TestObject(new Vector2D(i * 100, 0), new Vector2D(75, 75), cameraHandlers);
		}
	}
	
	
	// SUBCLASSES	-----------------------
	
	private static class TestObject extends SimpleGameObject implements Drawable
	{
		// ATTRIBUTES	---------------------
		
		private Vector2D dimensions, position;
		
		
		// CONSTRUCTOR	-----------------------
		
		public TestObject(Vector2D topLeft, Vector2D dimensions, HandlerRelay handlers)
		{
			super(handlers);
			
			this.dimensions = dimensions;
			this.position = topLeft;
		}
		
		
		// IMPLEMENTED METHODS	---------------

		@Override
		public void drawSelf(Graphics2D g2d)
		{
			g2d.setColor(Color.BLACK);
			g2d.drawRect(this.position.getFirstInt(), this.position.getSecondInt(), 
					this.dimensions.getFirstInt(), this.dimensions.getSecondInt());
		}

		@Override
		public int getDepth()
		{
			return 0;
		}

		@Override
		public StateOperator getIsVisibleStateOperator()
		{
			return getIsActiveStateOperator();
		}

		@Override
		public void setDepth(int depth)
		{
			// Not used
		}
	}
}
