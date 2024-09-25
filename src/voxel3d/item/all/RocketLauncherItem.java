package voxel3d.item.all;

import voxel3d.entity.all.Player;
import voxel3d.entity.all.Rocket;
import voxel3d.global.AssetLoader;
import voxel3d.global.Objects;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.item.context.ItemUseContext;
import voxel3d.utility.Vector3d;

public class RocketLauncherItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Rocket launcher");
	private static RocketLauncherItem sharedInstance = new RocketLauncherItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static RocketLauncherItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Rocket launcher";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}
	
	public void onUse(ItemUseContext context)
	{
		Player player = context.getPlayer();
		Rocket rocket = new Rocket();
		rocket.audioSource = Objects.audioManager.playSound(player.camera.position, Objects.rocketLaunch);
		rocket.velocity.set(player.camera.forward);
		rocket.velocity.multiply(10);
		rocket.position.set(player.camera.position);
		Vector3d p = new Vector3d();
		p.set(player.camera.forward);
		p.multiply(0.5);
		rocket.position.set(player.camera.position);
		rocket.position.add(p);
		context.addEntity(rocket);
	}

}
