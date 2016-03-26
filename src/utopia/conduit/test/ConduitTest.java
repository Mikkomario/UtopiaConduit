package utopia.conduit.test;

import java.awt.Color;
import java.awt.Graphics2D;

import utopia.conduit.camera.CameraDrawer;
import utopia.conduit.camera.CameraMouseListenerHandler;
import utopia.genesis.event.AbstractMouseListenerHandler;
import utopia.genesis.event.ActorHandler;
import utopia.genesis.event.Drawable;
import utopia.genesis.event.DrawableHandler;
import utopia.genesis.event.KeyListenerHandler;
import utopia.genesis.event.MainKeyListenerHandler;
import utopia.genesis.event.MouseEvent;
import utopia.genesis.event.MouseListener;
import utopia.genesis.event.MouseListenerHandler;
import utopia.genesis.event.StepHandler;
import utopia.genesis.util.Vector3D;
import utopia.genesis.video.GamePanel;
import utopia.genesis.video.GameWindow;
import utopia.genesis.video.PanelKeyListenerHandler;
import utopia.genesis.video.PanelMouseListenerHandler;
import utopia.genesis.video.GamePanel.ScalingPolicy;
import utopia.genesis.video.SplitPanel.ScreenSplit;
import utopia.inception.event.EventSelector;
import utopia.inception.handling.HandlerRelay;
import utopia.inception.util.SimpleHandled;

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
		Vector3D resolution = new Vector3D(800, 600);
		GameWindow window = new GameWindow(resolution.toDimension(), "Conduit Test", false, 
				ScreenSplit.HORIZONTAL);
		GamePanel panel = new GamePanel(resolution, ScalingPolicy.PROJECT, 120);
		window.addGamePanel(panel);
		
		// Creates the basic handlers
		StepHandler stepHandler = new StepHandler(120, 10);
		AbstractMouseListenerHandler mouseHandler = new PanelMouseListenerHandler(panel, false);
		MainKeyListenerHandler keyHandler = new PanelKeyListenerHandler(window);
		stepHandler.add(mouseHandler);
		stepHandler.add(keyHandler);
		
		HandlerRelay baseHandlers = new HandlerRelay();
		baseHandlers.addHandler(stepHandler, keyHandler, mouseHandler, panel.getDrawer());
		
		// Creates the camera
		TestCamera camera = new TestCamera(resolution);
		baseHandlers.add(camera);
		
		// Creates the camera handlers
		ActorHandler actorHandler = new ActorHandler();
		KeyListenerHandler cameraKeyHandler = new KeyListenerHandler();
		MouseListenerHandler cameraMouseHandler = new CameraMouseListenerHandler(camera);
		DrawableHandler cameraDrawer = new CameraDrawer(camera);
		
		baseHandlers.add(actorHandler, cameraKeyHandler, cameraMouseHandler, cameraDrawer);
		
		HandlerRelay cameraHandlers = new HandlerRelay();
		cameraHandlers.addHandler(actorHandler, cameraKeyHandler, cameraMouseHandler, cameraDrawer);
		
		// Creates the test objects
		baseHandlers.add(new TestObject(new Vector3D(10, 10), resolution.minus(new Vector3D(20, 20))));
		for (int i = 0; i < 5; i++)
		{
			cameraHandlers.add(new TestObject(new Vector3D(i * 100, 0), new Vector3D(75, 75)));
		}
		cameraHandlers.add(new MousePositionDrawer());
		
		// Starts the game
		stepHandler.start();
	}
	
	
	// SUBCLASSES	-----------------------
	
	private static class TestObject extends SimpleHandled implements Drawable
	{
		// ATTRIBUTES	---------------------
		
		private Vector3D dimensions, position;
		
		
		// CONSTRUCTOR	-----------------------
		
		public TestObject(Vector3D topLeft, Vector3D dimensions)
		{
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
	}
	
	private static class MousePositionDrawer extends SimpleHandled implements 
			MouseListener, Drawable
	{
		// ATTRIBUTES	------------------------
		
		private Vector3D lastMousePosition;
		private EventSelector<MouseEvent> selector;
		
		
		// CONSTRUCTOR	------------------------
		
		public MousePositionDrawer()
		{	
			this.lastMousePosition = Vector3D.zeroVector();
			this.selector = MouseEvent.createMouseMoveSelector();
		}
		
		
		// IMPLEMENTED METHODS	----------------

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
	}
}
