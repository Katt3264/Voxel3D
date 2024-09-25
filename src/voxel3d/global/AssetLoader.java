package voxel3d.global;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import voxel3d.audio.AudioClip;
import voxel3d.data.Crafting;
import voxel3d.data.KeyValuePair;
import voxel3d.graphics.AtlasBuilder;
import voxel3d.graphics.Texture;
import voxel3d.utility.Vector2f;

public class AssetLoader {
	
	private static AtlasBuilder atlasBuilder = new AtlasBuilder();
	private static TreeMap<String, Texture> textures = new TreeMap<String, Texture>();;
	
	protected static void LoadAllRecipes()
	{
		Iterable<File> files = LoadAllFiles("recipes");
		
		List<Iterable<KeyValuePair>> recipes = new LinkedList<Iterable<KeyValuePair>>();
		
		for(File file : files)
		{
			if(!file.exists())
				continue;
			
			if(!file.getName().contains("recipe"))
				continue;
			
			Debug.assetLoadLog("recipe loaded: " + file);
			recipes.add(loadKeyValueFile(file));
		}
		Crafting.init(recipes);
	}
	
	
	protected static void LoadAllTextures()
	{
		Iterable<File> itemTextureFiles = LoadAllFiles("items");
		for(File file : itemTextureFiles)
		{
			if(file.isDirectory())
				continue;
			
			if(!file.getName().endsWith(".png"))
				continue;
			
			textures.put(file.getName().replaceAll(".png", ""), Texture.get(file.getPath()));
		}
		
		Iterable<File> blockTextureFiles = LoadAllFiles("blocks");
		for(File file : blockTextureFiles)
		{
			if(file.isDirectory())
				continue;
			
			if(!file.getName().endsWith(".png"))
				continue;
			
			textures.put(file.getName().replaceAll(".png", ""), Texture.get(file.getPath()));
			
			try {
				atlasBuilder.add(ImageIO.read(file), file.getName().replaceAll(".png", ""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	protected static void LoadAllItems()
	{
		File operatorFile = new File("bin/voxel3d/item/all");
		File[] itemClassFiles = operatorFile.listFiles(new FilenameFilter() {
	        @Override public boolean accept(File dir, String name) {
	            return name.endsWith(".class");
	        }
	    });
	    
	    for (File file : itemClassFiles) {
	        String className = "voxel3d.item.all." + file.getName().substring(0, file.getName().length() - 6);
	        try {
	        	Debug.assetLoadLog("item class loaded: " + className);
	        	@SuppressWarnings("unused")
				Class<?> addedClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    }
	}
	
	
	public static Texture loadItemTexture(String name)
	{
		if(textures.get(name) == null)
		{
			Debug.err("no texture: " + name);;
		}
		return textures.get(name);
	}
	
	
	protected static Texture LoadAllBlocks()
	{
		File operatorFile = new File("bin/voxel3d/block/all");
	    File[] blockClassFiles = operatorFile.listFiles(new FilenameFilter() {
	        @Override public boolean accept(File dir, String name) {
	            return name.endsWith(".class");
	        }
	    });
	    
	    for (File file : blockClassFiles) {
	        String className = "voxel3d.block.all." + file.getName().substring(0, file.getName().length() - 6);
	        try {
	        	Debug.assetLoadLog("block class loaded: " + className);
	        	@SuppressWarnings("unused")
				Class<?> addedClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    }
		
		return atlasBuilder.getTexture();
	}
	
	
	public static Vector2f[] loadBlockTexture(String name)
	{
		if(atlasBuilder.get(name) == null)
		{
			Debug.err("no texture: " + name);;
		}
		return atlasBuilder.get(name);
	}
	
	protected static void LoadAllEntities()
	{
		File operatorFile = new File("bin/voxel3d/entity/all");

	    File[] files = operatorFile.listFiles(new FilenameFilter() {
	        @Override public boolean accept(File dir, String name) {
	            return name.endsWith(".class");
	        }
	    });
	    
	    for (File file : files) {
	        String className = "voxel3d.entity.all." + file.getName().substring(0, file.getName().length() - 6);
	        try {
	        	Debug.assetLoadLog("entity class loaded: " + className);
	        	@SuppressWarnings("unused")
				Class<?> addedClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    }
	}
	
	protected static Texture loadEntityTexture(String name)
	{
		return Texture.get(LoadFile("entity textures", name + ".png").getPath());
	}
	
	protected static void LoadAllFluids()
	{
		File operatorFile = new File("bin/voxel3d/fluid/all");

	    File[] files = operatorFile.listFiles(new FilenameFilter() {
	        @Override public boolean accept(File dir, String name) {
	            return name.endsWith(".class");
	        }
	    });
	    
	    for (File file : files) {
	        String className = "voxel3d.fluid.all." + file.getName().substring(0, file.getName().length() - 6);
	        try {
	        	Debug.assetLoadLog("fluid class loaded: " + className);
	        	@SuppressWarnings("unused")
				Class<?> addedClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    }
	}
	
	protected static Texture loadGUITexture(String name)
	{
		return Texture.get(LoadFile("gui textures", name + ".png").getPath());
	}
	
	protected static Texture loadTexture(String name)
	{
		return Texture.get(LoadFile("textures", name + ".png").getPath());
	}
	
	protected static AudioClip loadAudio(String name)
	{
		return AudioClip.get(LoadFile("sounds", name + ".ogg").getPath());
	}
	
	/*
	 * Loads a file acording to priority order
	 */
	private static File LoadFile(String path, String name)
	{
		Iterable<File> files = LoadAllFiles(path);
		
		for(File file : files)
		{
			if(file.getName().equals(name))
			{
				return file;
			}
		}
		return null;
	}
	
	/*
	 * Loads all files in a path, prevents duplicates from other resource folders
	 */
	private static Iterable<File> LoadAllFiles(String path)
	{
		List<File> results = new LinkedList<File>();
		File[] modFolders = new File("GameData").listFiles();
		
		for(int i = 0; i < Settings.resourceOrder.length; i++)
		{
			for(File file : modFolders)
			{
				if(!file.getName().equals(Settings.resourceOrder[i]))
					continue;
				
				if(file.isDirectory())
				{
					List<File> res = recursiveFileLoad(file.getPath() + "/" + path);
					
					for(File nf : res)
					{
						boolean exists = false;
						for(File exist : results)
						{
							if(exist.getName().equals(nf.getName()))
								exists = true;
						}
						if(!exists)
							results.add(nf);
					}
					
				}
			}
		}
		return results;
	}
	
	private static List<File> recursiveFileLoad(String path)
	{
		List<File> results = new LinkedList<File>();
		
		File[] directoryContent = new File(path).listFiles();
		
		if(directoryContent == null)
			directoryContent = new File[0];
		
		for(File file : directoryContent)
		{
			if(file.isDirectory())
				results.addAll(recursiveFileLoad(file.getPath()));
			else
				results.add(file);
		}
		return results;
	}
	
	private static Iterable<KeyValuePair> loadKeyValueFile(File file)
	{
		Iterable<String> lines = loadKeyValueLines(file);
		List<KeyValuePair> pairs = new LinkedList<KeyValuePair>();
		
		for(String line : lines)
		{
			pairs.add(new KeyValuePair(line));
		}
		
		return pairs;
	}
	
	private static Iterable<String> loadKeyValueLines(File file)
	{
		List<String> lines = new ArrayList<String>();
		
		if(!file.exists()) {return lines;}
		
		try {
			BufferedInputStream fileReader = new BufferedInputStream(new FileInputStream(file.getPath()));
			Scanner sc = new Scanner(fileReader);
			
			while(sc.hasNextLine())
			{
				lines.add(sc.nextLine());
			}
			
			sc.close();
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}

}
