package voxel3d.global;

import java.util.concurrent.ConcurrentLinkedQueue;

import voxel3d.audio.AudioClip;
import voxel3d.audio.AudioManager;
import voxel3d.graphics.Shader;
import voxel3d.graphics.Texture;
import voxel3d.graphics.Window;
import voxel3d.gui.ChestGUI;
import voxel3d.gui.CraftingGUI;
import voxel3d.gui.HUDInteractable;
import voxel3d.utility.FloatArrayPool;
import voxel3d.utility.MainThreadExecutable;

public class Objects {
	
	public static Window window;
	public static AudioManager audioManager;
	public static Texture chunkAtlas;
	public static Texture itemAtlas;
	public static Texture itemSlot;
	public static Texture itemSlotSelected;
	public static Texture craftButton;
	public static Texture craftButtonSelected;
	public static Texture resumeButton;
	public static Texture resumeButtonSelected;
	public static Texture exitButton;
	public static Texture exitButtonSelected;
	public static Texture leftButton;
	public static Texture leftButtonSelected;
	public static Texture rightButton;
	public static Texture rightButtonSelected;
	
	public static Texture inventoryGUI;
	public static Texture craftingGUI;
	public static Texture maceratorGUI;
	public static Texture chestGUI;
	public static Texture hintGUI;
	
	public static Texture glyphAtlas;
	public static Texture hotbarSlot;
	public static Texture hotbarSlotSelected;
	public static Texture health;
	public static Texture healthGold;
	public static Texture healthEmpty;
	public static Texture hunger;
	public static Texture hungerGold;
	public static Texture hungerEmpty;
	
	public static Texture missingTexture;
	
	public static Texture[] blockBreak;
	
	public static Texture slime;
	public static Texture redSlime;
	public static Texture undead;
	public static Texture runner;
	public static Texture deer;
	
	public static Texture skyBoxSunrise;
	public static Texture skyBoxSunset;
	public static Texture skyBoxDay;
	public static Texture skyBoxStars;
	
	public static Texture skyBoxDay1Sunrise;
	public static Texture skyBoxDay1Sunset;
	public static Texture skyBoxDay1Day;
	public static Texture skyBoxDay1Night;
	
	public static Texture skyboxSun;
	public static Texture skyboxMoon;
	
	public static AudioClip testSound;
	public static AudioClip rocketLaunch;
	public static AudioClip explosion;
	
	public static HUDInteractable craftingHUD;
	public static HUDInteractable chestHUD;
	
	
	public static FloatArrayPool floatArrayPool;
	
	public static Shader shader;
	
	public static ConcurrentLinkedQueue<MainThreadExecutable> mainQueue;

	public static void create()
	{
		window = new Window("Game", 1080, 720);
		audioManager = new AudioManager();
		
		AssetLoader.LoadAllTextures();
		AssetLoader.LoadAllFluids();
		chunkAtlas = AssetLoader.LoadAllBlocks();
		AssetLoader.LoadAllItems();
		AssetLoader.LoadAllRecipes();
		AssetLoader.LoadAllEntities();
		
		
		itemSlot = AssetLoader.loadGUITexture("item slot");
		itemSlotSelected = AssetLoader.loadGUITexture("item slot selected");
		
		inventoryGUI = AssetLoader.loadGUITexture("inventory");
		craftingGUI = AssetLoader.loadGUITexture("crafting");
		chestGUI = AssetLoader.loadGUITexture("chest");
		maceratorGUI = AssetLoader.loadGUITexture("macerator");
		hintGUI = AssetLoader.loadGUITexture("hint");
		
		glyphAtlas = AssetLoader.loadGUITexture("glyphs");
		hotbarSlot = AssetLoader.loadGUITexture("hotbar slot");
		hotbarSlotSelected = AssetLoader.loadGUITexture("hotbar slot selected");
		craftButton = AssetLoader.loadGUITexture("craft button");
		craftButtonSelected = AssetLoader.loadGUITexture("craft button selected");
		resumeButton = AssetLoader.loadGUITexture("resume button");
		resumeButtonSelected = AssetLoader.loadGUITexture("resume button selected");
		exitButton = AssetLoader.loadGUITexture("exit button");
		exitButtonSelected = AssetLoader.loadGUITexture("exit button selected");
		leftButton = AssetLoader.loadGUITexture("left button");
		leftButtonSelected = AssetLoader.loadGUITexture("left button selected");
		rightButton = AssetLoader.loadGUITexture("right button");
		rightButtonSelected = AssetLoader.loadGUITexture("right button selected");
		
		health = AssetLoader.loadGUITexture("health");
		healthGold = AssetLoader.loadGUITexture("health gold");
		healthEmpty = AssetLoader.loadGUITexture("health empty");
		hunger = AssetLoader.loadGUITexture("hunger");
		hungerGold = AssetLoader.loadGUITexture("hunger gold");
		hungerEmpty = AssetLoader.loadGUITexture("hunger empty");
		
		missingTexture = AssetLoader.loadGUITexture("missing");
		
		blockBreak = new Texture[] {
				Texture.get("resources/textures/break0.png"),
				Texture.get("resources/textures/break1.png"),
				Texture.get("resources/textures/break2.png"),
				Texture.get("resources/textures/break3.png"),
				Texture.get("resources/textures/break4.png"),
				Texture.get("resources/textures/break5.png"),
				Texture.get("resources/textures/break6.png"),
				Texture.get("resources/textures/break7.png")
		};
		
		slime = AssetLoader.loadEntityTexture("Slime");
		redSlime = AssetLoader.loadEntityTexture("Red slime");
		undead = AssetLoader.loadEntityTexture("Undead");
		runner = AssetLoader.loadEntityTexture("Runner");
		deer = AssetLoader.loadEntityTexture("Deer ghost");
		
		//skyBoxSunrise = AssetLoader.loadTexture("skybox_sunrise");
		//skyBoxSunset = AssetLoader.loadTexture("skybox_sunset");
		skyBoxDay = AssetLoader.loadTexture("skybox_day");
		skyBoxStars = AssetLoader.loadTexture("skybox_stars2");
		
		//skyBoxDay1Sunrise = AssetLoader.loadTexture("sky1_sunrise");
		//skyBoxDay1Sunset = AssetLoader.loadTexture("sky1_sunset");
		//skyBoxDay1Day = AssetLoader.loadTexture("sky1_day");
		//skyBoxDay1Night = AssetLoader.loadTexture("sky1_night");
		
		skyboxSun = AssetLoader.loadTexture("Skybox sun");
		skyboxMoon = AssetLoader.loadTexture("Skybox moon");
		
		testSound = AssetLoader.loadAudio("test");
		rocketLaunch = AssetLoader.loadAudio("rocket launch");
		explosion = AssetLoader.loadAudio("explosion");
		
		craftingHUD = new CraftingGUI();
		chestHUD = new ChestGUI();
		
		shader = new Shader("resources/shaders/vertex.txt", "resources/shaders/fragment.txt");
		
		floatArrayPool = new FloatArrayPool();
		
		Input.init();
		
		mainQueue = new ConcurrentLinkedQueue<MainThreadExecutable>();
		

	}
	
	public static void destroy()
	{
		window.destroy();
		audioManager.destroy();
	}
	
}
