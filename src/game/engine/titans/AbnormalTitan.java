package game.engine.titans;

import game.engine.interfaces.Attackee;

public class AbnormalTitan extends Titan
{
	public static final int TITAN_CODE = 2;

	public AbnormalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel)
	{
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}

	@Override
	public int attack(Attackee target)
	{
		int attackRes = super.attack(target);

		return attackRes < 0 ? attackRes : super.attack(target);
	}

}
