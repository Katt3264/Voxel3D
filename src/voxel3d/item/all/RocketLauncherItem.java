package voxel3d.item.all;

import voxel3d.entity.all.Player;
import voxel3d.entity.all.Rocket;
import voxel3d.global.AssetLoader;
import voxel3d.global.Objects;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.ItemRenderContext;
import voxel3d.item.ItemUseContext;
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
		rocket.audioSource.setGain(0.02f);
		rocket.velocity.set(player.camera.forward);
		rocket.velocity.multiply(10);
		Vector3d p = new Vector3d();
		Vector3d right = new Vector3d();
		right.setCross(player.camera.up, player.camera.forward);
		right.multiply(2);
		right.subtract(player.camera.up);
		right.multiply(0.1);
		p.set(player.camera.forward);
		p.multiply(0.1);
		p.add(right);
		rocket.position.set(player.camera.position);
		rocket.position.add(p);
		context.addEntity(rocket);
	}

}
