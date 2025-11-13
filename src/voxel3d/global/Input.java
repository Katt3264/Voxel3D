package voxel3d.global;

import static org.lwjgl.glfw.GLFW.*;

import voxel3d.graphics.GraphicsWrapper;

public class Input {
	
	public static boolean mouseVisible = true;
	
	public static ButtonState forward = new ButtonState();
	public static ButtonState back = new ButtonState();
	public static ButtonState left = new ButtonState();
	public static ButtonState right = new ButtonState();
	public static ButtonState jump = new ButtonState();
	public static ButtonState inventory = new ButtonState();
	public static ButtonState sprint = new ButtonState();
	
	public static ButtonState hit = new ButtonState();
	public static ButtonState place = new ButtonState();
	
	public static ButtonState log = new ButtonState();
	public static ButtonState hud = new ButtonState();
	public static ButtonState god = new ButtonState();
	public static ButtonState esc = new ButtonState();
	
	public static ButtonState drop = new ButtonState();
	
	public static ButtonState num1 = new ButtonState();
	public static ButtonState num2 = new ButtonState();
	public static ButtonState num3 = new ButtonState();
	public static ButtonState num4 = new ButtonState();
	public static ButtonState num5 = new ButtonState();
	public static ButtonState num6 = new ButtonState();
	public static ButtonState num7 = new ButtonState();
	public static ButtonState num8 = new ButtonState();
	public static ButtonState num9 = new ButtonState();
	public static ButtonState num0 = new ButtonState();
	
	public static void init()
	{
		addCallback();
	}
	
	public static void update()
	{
		mouseH = mouseX - lastX;
		lastX = mouseX;
		mouseV = mouseY - lastY;
		lastY = mouseY;
		
		forward.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_W) == GLFW_PRESS);
		back.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_S) == GLFW_PRESS);
		right.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_D) == GLFW_PRESS);
		left.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_A) == GLFW_PRESS);
		jump.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_SPACE) == GLFW_PRESS);
		inventory.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_E) == GLFW_PRESS);
		sprint.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_Q) == GLFW_PRESS);
		
		drop.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_C) == GLFW_PRESS);
		
		hit.updateState(glfwGetMouseButton(GraphicsWrapper.window.getID(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS);
		place.updateState(glfwGetMouseButton(GraphicsWrapper.window.getID(), GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS);
		
		hud.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_H) == GLFW_PRESS);
		log.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_L) == GLFW_PRESS);
		god.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_G) == GLFW_PRESS);
		
		esc.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_ESCAPE) == GLFW_PRESS);
		
		num1.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_1) == GLFW_PRESS);
		num2.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_2) == GLFW_PRESS);
		num3.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_3) == GLFW_PRESS);
		num4.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_4) == GLFW_PRESS);
		num5.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_5) == GLFW_PRESS);
		num6.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_6) == GLFW_PRESS);
		num7.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_7) == GLFW_PRESS);
		num8.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_8) == GLFW_PRESS);
		num9.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_9) == GLFW_PRESS);
		num0.updateState(glfwGetKey(GraphicsWrapper.window.getID(), GLFW_KEY_0) == GLFW_PRESS);
	}
	
	
	public static float getMouseX()
	{
		return mouseH;
	}
	public static float getMouseY()
	{
		return mouseV;
	}
	
	public static float getNormalMouseX()
	{
		return (mouseX*2 - GraphicsWrapper.window.width) / GraphicsWrapper.window.height;
	}
	
	public static float getNormalMouseY()
	{
		return -(mouseY*2 - GraphicsWrapper.window.height) / GraphicsWrapper.window.height;
	}
	
	private static float mouseX, mouseY, lastX, lastY, mouseH, mouseV;
	
	private static boolean firstMouseMovement = true;
	private static void addCallback()
	{
		glfwSetCursorPosCallback(GraphicsWrapper.window.getID(), (_window, xpos, ypos) -> {
			
			mouseX = (float) xpos;
        	mouseY = (float) ypos;
        	
        	if(firstMouseMovement) // prevents sudden movement
        	{
        		firstMouseMovement = false;
        		lastX = mouseX;
        		lastY = mouseY;
        	}
        });
	}
	
	
	public static void hideMouse()
	{
		mouseVisible = false;
		
		glfwSetInputMode(GraphicsWrapper.window.getID(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		glfwSetCursorPos(GraphicsWrapper.window.getID(), GraphicsWrapper.window.width / 2, GraphicsWrapper.window.height / 2);
		mouseX = GraphicsWrapper.window.width / 2;
		mouseY = GraphicsWrapper.window.height / 2;
		
		lastX = mouseX;
		lastY = mouseY;
	}
	
	public static void showMouse()
	{
		mouseVisible = true;
		
		glfwSetInputMode(GraphicsWrapper.window.getID(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		glfwSetCursorPos(GraphicsWrapper.window.getID(), GraphicsWrapper.window.width / 2, GraphicsWrapper.window.height / 2);
		mouseX = GraphicsWrapper.window.width / 2;
		mouseY = GraphicsWrapper.window.height / 2;
	}

}
