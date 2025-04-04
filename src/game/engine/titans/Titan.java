package game.engine.titans;

import game.engine.interfaces.Attackee;
import game.engine.interfaces.Attacker;
import game.engine.interfaces.Mobil;

public abstract class Titan implements Attacker, Attackee, Mobil, Comparable<Titan>
{
	private final int baseHealth;
	private int currentHealth;
	private final int baseDamage;
	private final int heightInMeters;
	private int distanceFromBase;
	private int speed; // distance moved per turn
	private final int resourcesValue; // resources gained by defeating it
	private final int dangerLevel;
	public boolean FirstTime = true;

	public Titan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel)
	{
		super();
		this.baseHealth = baseHealth;
		this.currentHealth = baseHealth;
		this.baseDamage = baseDamage;
		this.heightInMeters = heightInMeters;
		this.distanceFromBase = distanceFromBase;
		this.speed = speed;
		this.resourcesValue = resourcesValue;
		this.dangerLevel = dangerLevel;
	}

	public int getBaseHealth()
	{
		return this.baseHealth;
	}

	@Override
	public int getCurrentHealth()
	{
		return this.currentHealth;
	}

	@Override
	public void setCurrentHealth(int health)
	{
		this.currentHealth = health < 0 ? 0 : health;
	}

	@Override
	public int getDamage()
	{
		return this.baseDamage;
	}

	public int getHeightInMeters()
	{
		return this.heightInMeters;
	}

	@Override
	public int getDistance()
	{
		return this.distanceFromBase;
	}

	@Override
	public void setDistance(int distance)
	{
		this.distanceFromBase = distance < 0 ? 0 : distance;
	}

	@Override
	public int getSpeed()
	{
		return this.speed;
	}

	@Override
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	@Override
	public int getResourcesValue()
	{
		return this.resourcesValue;
	}

	public int getDangerLevel()
	{
		return this.dangerLevel;
	}

	@Override
	public int compareTo(Titan o) // prioritizing the nearest titans according to the wall
	{
		return this.distanceFromBase - o.distanceFromBase;
	}

}
