package game.engine.base;

import game.engine.interfaces.Attackee;

public class Wall implements Attackee
{
	private final int baseHealth;
	private int currentHealth;

	public Wall(int baseHealth)
	{
		super();
		this.baseHealth = baseHealth;
		this.currentHealth = baseHealth;
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
	public int getResourcesValue() // implies that a wall was damaged and not the enemy (titan)
	{
		return -1;
	}

}
