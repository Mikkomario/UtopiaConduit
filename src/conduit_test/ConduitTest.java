package conduit_test;

import java.awt.Color;
import java.awt.Graphics2D;

import conduit_camera.Camera;
import conduit_camera.CameraDrawer;
import conduit_camera.CameraMouseListenerHandler;
import genesis_event.MouseEvent;
import genesis_event.MouseListener;
import genesis_event.Drawable;
import genesis_event.DrawableHandler;
import genesis_event.EventSelector;
import genesis_event.HandlerRelay;
import genesis_event.KeyListenerHandler;
import genesis_event.MouseListenerHandler;
import genesis_util.StateOperator;
import genesis_util.Vector3D;
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
		GameWindow window = new GameWindow(new Vector3D(800, 600), "Conduit Test", true, 
				120, 20);
		GamePanel panel = window.getMainPanel().addGamePanel();
		
		// Creates the basic handlers
		HandlerRelay baseHandlers = new HandlerRelay();
		baseHandlers.addHandler(new KeyListenerHandler(true, window.getHandlerRelay()));
		baseHandlers.addHandler(new DrawableHandler(true, panel.getDrawer()));
		baseHandlers.addHandler(new MouseListenerHandler(true, window.getHandlerRelay()));
		
		// Creates the camera
		Camera camera = new TestCamera(baseHandlers);
		
		// Creates the camera handlers
		HandlerRelay cameraHandlers = new HandlerRelay();
		cameraHandlers.addHandler(new KeyListenerHandler(true, baseHandlers));
		cameraHandlers.addHandler(new CameraDrawer(camera, true, baseHandlers));
		cameraHandlers.addHandler(new CameraMouseListenerHandler(camera, true, baseHandlers));
		
		// Creates the test objects
		new TestObject(new Vector3D(10, 10), new Vector3D(780, 580), baseHandlers);
		for (int i = 0; i < 5; i++)
		{
			new TestObject(new Vector3D(i * 100, 0), new Vector3D(75, 75), cameraHandlers);
		}
		new MousePositionDrawer(cameraHandlers);
	}
	
	
	// SUBCLASSES	-----------------------
	
	private static class TestObject extends SimpleGameObject implements Drawable
	{
		// ATTRIBUTES	---------------------
		
		private Vector3D dimensions, position;
		
		
		// CONSTRUCTOR	-----------------------
		
		public TestObject(Vector3D topLeft, Vector3D dimensions, HandlerRelay handlers)
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
	}
	
	private static class MousePositionDrawer extends SimpleGameObject implements 
			MouseListener, Drawable
	{
		// ATTRIBUTES	------------------------
		
		private Vector3D lastMousePosition;
		private EventSelector<MouseEvent> selector;
		
		
		// CONSTRUCTOR	------------------------
		
		public MousePositionDrawer(HandlerRelay handlers)
		{
			super(handlers);
			
			this.lastMousePosition = Vector3D.zeroVector();
			this.selector = MouseEvent.createMouseMoveSelector();
		}
		
		
		// IMPLEMENTED METHODS	----------------

		@Override
		public StateOperator getListensToMouseEventsOperator()
		{
			return getIsActiveStateOperator();
		}

		@Override
		public EventSelector<MouseEvent> getMouseEventSelector()
		{
			return this.selector;
		}

		@Override
		public boolean isInAreaOfInterest(Vector3D position)
		{
			return false;
		}

		@Override
		public void onMouseEvent(MouseEvent event)
		{
			this.lastMousePosition = event.getPosition();
		}

		@Override
		public void drawSelf(Graphics2D g2d)
		{
			g2d.setColor(Color.RED);
			this.lastMousePosition.drawAsPoint(5, g2d);
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
	}
}
