package game.engine.lanes;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Lane implements Comparable<Lane>
{
	private final Wall laneWall;
	private int dangerLevel;
	private final PriorityQueue<Titan> titans;
	private final ArrayList<Weapon> weapons;

	public Lane(Wall laneWall)
	{
		super();
		this.laneWall = laneWall;
		this.dangerLevel = 0;
		this.titans = new PriorityQueue<>();
		this.weapons = new ArrayList<>();
	}

	public Wall getLaneWall()
	{
		return this.laneWall;
	}

	public int getDangerLevel()
	{
		return this.dangerLevel;
	}

	public void setDangerLevel(int dangerLevel)
	{
		this.dangerLevel = dangerLevel;
	}

	public PriorityQueue<Titan> getTitans()
	{
		return this.titans;
	}

	public ArrayList<Weapon> getWeapons()
	{
		return this.weapons;
	}

	@Override
	public int compareTo(Lane o)
	{
		return this.dangerLevel - o.dangerLevel;
	}

	public void addTitan(Titan titan)
	{
		this.getTitans().add(titan);
	}

	public void addWeapon(Weapon weapon)
	{
		this.getWeapons().add(weapon);
	}

	public void moveLaneTitans()
	{
		ArrayList<Titan> tmp = new ArrayList<>();

		for (Titan t : this.getTitans())
		{
			if (!t.hasReachedTarget())
			{
				t.move();
				tmp.add(t);
			}

		}
		
		this.getTitans().removeAll(tmp);
		this.getTitans().addAll(tmp);
	}

	public int performLaneTitansAttacks()
	{
		int resourcesGathered = 0;

		for (Titan t : this.getTitans())
		{
			if (t.hasReachedTarget())
			{
				resourcesGathered += t.attack(this.getLaneWall());
			}
		}

		return resourcesGathered;
	}

	public int performLaneWeaponsAttacks()
	{
		int resourcesGathered = 0;

		for (Weapon w : this.getWeapons())
		{
			resourcesGathered += w.turnAttack(this.getTitans());
		}

		return resourcesGathered;
	}

	public boolean isLaneLost()
	{
		return this.getLaneWall().isDefeated();
	}

	public void updateLaneDangerLevel()
	{
		int newDanger = 0;

		for (Titan t : this.getTitans())
		{
			newDanger += t.getDangerLevel();
		}

		this.setDangerLevel(newDanger);
	}
}
