package game.engine.titans;

public class PureTitan extends Titan
{
	public static final int TITAN_CODE = 1;

	public PureTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel)
	{
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}

}
