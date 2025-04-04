package game.engine.exceptions;

@SuppressWarnings("serial")
public class InvalidLaneException extends GameActionException
{
	private static final String MSG = "Action done on an invalid lane";

	public InvalidLaneException()
	{
		super(MSG);
	}

	public InvalidLaneException(String message)
	{
		super(message);
	}

}
