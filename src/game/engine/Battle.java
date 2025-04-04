package game.engine;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.Weapon;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Battle
{
	private static final int[][] PHASES_APPROACHING_TITANS =
	{
		{ 1, 1, 1, 2, 1, 3, 4 },
		{ 2, 2, 2, 1, 3, 3, 4 },
		{ 4, 4, 4, 4, 4, 4, 4 } 
	}; // order of the types of titans (codes) during each phase
	private static final int WALL_BASE_HEALTH = 10000;

	private int numberOfTurns;
	private int resourcesGathered;
	private BattlePhase battlePhase;
	private int numberOfTitansPerTurn; // initially equals to 1
	private int score; // Number of Enemies Killed
	private int titanSpawnDistance;
	private final WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives;
	private final ArrayList<Titan> approachingTitans; // treated as a Queue
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes;

	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
			int initialResourcesPerLane) throws IOException
	{
		super();
		this.numberOfTurns = numberOfTurns;
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.resourcesGathered = initialResourcesPerLane * initialNumOfLanes;
		this.weaponFactory = new WeaponFactory();
		this.titansArchives = DataLoader.readTitanRegistry();
		this.approachingTitans = new ArrayList<Titan>();
		this.lanes = new PriorityQueue<>();
		this.originalLanes = new ArrayList<>();
		this.initializeLanes(initialNumOfLanes);
	}

	public int getNumberOfTurns()
	{
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns)
	{
		this.numberOfTurns = numberOfTurns;
	}

	public int getResourcesGathered()
	{
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered)
	{
		this.resourcesGathered = resourcesGathered;
	}

	public BattlePhase getBattlePhase()
	{
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase)
	{
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn()
	{
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn)
	{
		this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public int getTitanSpawnDistance()
	{
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance)
	{
		this.titanSpawnDistance = titanSpawnDistance;
	}

	public WeaponFactory getWeaponFactory()
	{
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives()
	{
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans()
	{
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes()
	{
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes()
	{
		return originalLanes;
	}

	private void initializeLanes(int numOfLanes)
	{
		for (int i = 0; i < numOfLanes; i++)
		{
			Wall w = new Wall(WALL_BASE_HEALTH);
			Lane l = new Lane(w);

			this.getOriginalLanes().add(l);
			this.getLanes().add(l);
		}
	}

	public void refillApproachingTitans() // spawns titans of the specified code to lanes based on the current phase
	{
		int[] phaseApproachingTitans;

		switch (this.getBattlePhase())
		{
		case EARLY:
			phaseApproachingTitans = PHASES_APPROACHING_TITANS[0];
			break;
		case INTENSE:
			phaseApproachingTitans = PHASES_APPROACHING_TITANS[1];
			break;
		case GRUMBLING:
			phaseApproachingTitans = PHASES_APPROACHING_TITANS[2];
			break;
		default:
			phaseApproachingTitans = new int[0];
		}

		for (int code : phaseApproachingTitans)
		{
			Titan spawnedTitan = this.getTitansArchives().get(code).spawnTitan(this.getTitanSpawnDistance());
			this.getApproachingTitans().add(spawnedTitan);
		}
	}

	public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException
	{
		if (!this.getLanes().contains(lane))
		{
			throw new InvalidLaneException("Weapon purchase failed");
		}

		FactoryResponse factoryResponse = this.getWeaponFactory().buyWeapon(getResourcesGathered(), weaponCode);
		Weapon purchasedWeapon = factoryResponse.getWeapon();

		if (purchasedWeapon != null)
		{
			lane.addWeapon(purchasedWeapon);
			this.setResourcesGathered(factoryResponse.getRemainingResources());
			performTurn();
		}
	}

	public void purchaseWeapon2(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException
	{
		if (!this.getLanes().contains(lane))
		{
			throw new InvalidLaneException("Weapon purchase failed");
		}

		FactoryResponse factoryResponse = this.getWeaponFactory().buyWeapon(getResourcesGathered(), weaponCode);
		Weapon purchasedWeapon = factoryResponse.getWeapon();

		if (purchasedWeapon != null)
		{
			lane.addWeapon(purchasedWeapon);
			this.setResourcesGathered(factoryResponse.getRemainingResources());
			performTurn2();
		}
	}
	public void passTurn()
	{
		performTurn();
	}

	private void addTurnTitansToLane()
	{
		Lane leastDangerLane = this.getLanes().poll();

		for (int i = 0; i < this.getNumberOfTitansPerTurn(); i++)
		{
			if (this.getApproachingTitans().isEmpty())
			{
				this.refillApproachingTitans();
			}

			leastDangerLane.addTitan(this.getApproachingTitans().remove(0));
		}

		this.getLanes().add(leastDangerLane);
	}

	private void moveTitans()
	{
		for (Lane l : this.getLanes())
		{
			l.moveLaneTitans();
		}
	}

	private int performWeaponsAttacks()
	{
		int resourcesGathered = 0;

		for (Lane l : this.getLanes())
		{
			resourcesGathered += l.performLaneWeaponsAttacks();
		}

		this.setResourcesGathered(this.getResourcesGathered() + resourcesGathered);
		this.setScore(this.getScore() + resourcesGathered);

		return resourcesGathered;
	}

	private int performTitansAttacks()
	{
		int resourcesGathered = 0;
		ArrayList<Lane> lostLanes = new ArrayList<>();

		for (Lane l : this.getLanes())
		{
			resourcesGathered += l.performLaneTitansAttacks();
			if (l.isLaneLost())
			{
				lostLanes.add(l);
			}
		}

		this.getLanes().removeAll(lostLanes);

		return resourcesGathered;
	}

	private void updateLanesDangerLevels()
	{
		ArrayList<Lane> tmp = new ArrayList<>();
		
		while(!this.getLanes().isEmpty())
		{
			Lane l = this.getLanes().poll();
			l.updateLaneDangerLevel();
			tmp.add(l);
		}
		
		this.getLanes().addAll(tmp);
	}

	private void finalizeTurns()
	{
		this.setNumberOfTurns(this.getNumberOfTurns() + 1);

		if (this.getNumberOfTurns() == 15)
		{
			this.setBattlePhase(BattlePhase.INTENSE);
		} else if (this.getNumberOfTurns() == 30)
		{
			this.setBattlePhase(BattlePhase.GRUMBLING);
		} else if (this.getNumberOfTurns() > 30 && this.getNumberOfTurns() % 5 == 0)
		{
			this.setNumberOfTitansPerTurn(this.getNumberOfTitansPerTurn() * 2);
		}
	}

	private void performTurn()
	{
		this.moveTitans();
		this.performWeaponsAttacks();
		this.performTitansAttacks();

		this.addTurnTitansToLane();
		this.updateLanesDangerLevels();

		this.finalizeTurns();
	}
	public void performTurn2()
	{
		this.moveTitans();
		this.performWeaponsAttacks();
		this.performTitansAttacks();

		if(!this.isGameOver()){
			this.addTurnTitansToLane();
		}
		this.updateLanesDangerLevels();

		this.finalizeTurns();
	}

	public boolean isGameOver() // checks if all lanes are destroyed
	{
		return this.getLanes().size() == 0;
	}

	public static void main(String[] args) {

	}

}
