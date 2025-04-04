package game.engine.interfaces;

public interface Mobil
{
	int getDistance();

	void setDistance(int distance);

	int getSpeed();

	void setSpeed(int speed);

	default boolean hasReachedTarget() // returns true if arrived at the intended target
	{
		return getDistance() <= 0;
	}

	default boolean move() // returns true if arrived at the intended target
	{
		setDistance(getDistance() - getSpeed());
		return hasReachedTarget();
	}

}
