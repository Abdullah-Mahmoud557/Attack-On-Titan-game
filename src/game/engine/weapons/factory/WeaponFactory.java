package game.engine.weapons.factory;

import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;

import java.io.IOException;
import java.util.HashMap;

public class WeaponFactory
{
	private final HashMap<Integer, WeaponRegistry> weaponShop;

	public WeaponFactory() throws IOException
	{
		super();
		weaponShop = DataLoader.readWeaponRegistry();
	}

	public HashMap<Integer, WeaponRegistry> getWeaponShop()
	{
		return weaponShop;
	}

	public FactoryResponse buyWeapon(int resources, int weaponCode) throws InsufficientResourcesException
	{
		WeaponRegistry registry = this.getWeaponShop().get(weaponCode);

		if (resources < registry.getPrice())
		{
			throw new InsufficientResourcesException(resources);
		}

		Weapon weapon = registry.buildWeapon();
		int remainingResources = resources - registry.getPrice();

		return new FactoryResponse(weapon, remainingResources);
	}

	public void addWeaponToShop(int code, int price)
	{
		this.getWeaponShop().put(code, new WeaponRegistry(code, price));
	}

	public void addWeaponToShop(int code, int price, int damage, String name)
	{
		this.getWeaponShop().put(code, new WeaponRegistry(code, price, damage, name));
	}

	public void addWeaponToShop(int code, int price, int damage, String name, int minRange, int maxRange)
	{
		this.getWeaponShop().put(code, new WeaponRegistry(code, price, damage, name, minRange, maxRange));
	}

}
