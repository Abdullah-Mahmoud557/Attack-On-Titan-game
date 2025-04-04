package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class WallTrap extends Weapon
{
	public static final int WEAPON_CODE = 4;

	public WallTrap(int baseDamage)
	{
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans)
	{
		Titan closestTitan = laneTitans.peek();
		int resourcesGathered = 0;

		if (closestTitan != null && closestTitan.hasReachedTarget())
		{
			resourcesGathered += this.attack(closestTitan);

			if (closestTitan.isDefeated())
			{
				laneTitans.remove(closestTitan);
			}
		}

		return resourcesGathered;
	}

}
