package voxel3d.global;

public class ButtonState {
	
	private boolean buttonDown = false;
	private boolean buttonPress = false;
	private boolean buttonRelease = false;
	
	public void updateState(boolean isButtonDown)
	{
		buttonPress = (isButtonDown && !buttonDown);
		buttonRelease = (!isButtonDown && buttonDown);
		buttonDown = isButtonDown;
	}
	
	public boolean isButtonDown()
	{
		return buttonDown;
	}
	
	public boolean isButtonPress()
	{
		return buttonPress;
	}
	
	public boolean isButtonRelease()
	{
		return buttonRelease;
	}

}
