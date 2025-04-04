package game.engine.titans;

public class ArmoredTitan extends Titan
{
	public static final int TITAN_CODE = 3;

	public ArmoredTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel)
	{
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}

	@Override
	public int takeDamage(int damage)
	{
		return super.takeDamage(damage / 4);
	}

}
