package voxel3d.item.all;

import voxel3d.entity.all.Lazer;
import voxel3d.entity.all.Player;
import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.item.context.ItemUseContext;
import voxel3d.utility.Vector3d;

public class LazerGunItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Lazer gun");
	private static LazerGunItem sharedInstance = new LazerGunItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static LazerGunItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Lazer gun";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}
	
	public void onUse(ItemUseContext context)
	{
		Player player = context.getPlayer();
		Lazer lazer = new Lazer();
		//rocket.audioSource = Objects.audioManager.playSound(player.camera.position, Objects.rocketLaunch);
		//rocket.audioSource.setGain(0.02f);
		lazer.velocity.set(player.camera.forward);
		lazer.velocity.multiply(20);
		Vector3d p = new Vector3d();
		Vector3d right = new Vector3d();
		right.setCross(player.camera.up, player.camera.forward);
		right.multiply(2);
		right.subtract(player.camera.up);
		right.multiply(0.1);
		p.set(player.camera.forward);
		p.multiply(0.1);
		p.add(right);
		lazer.position.set(player.camera.position);
		lazer.position.add(p);
		context.addEntity(lazer);
	}

}
