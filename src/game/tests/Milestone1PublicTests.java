package game.tests;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

import static org.junit.Assert.*;


public class Milestone1PublicTests {

	private String weaponPath = "game.engine.weapons.Weapon";
	private String weaponPiercingCannonPath = "game.engine.weapons.PiercingCannon";
	private String factoryResponsePath = "game.engine.weapons.factory.FactoryResponse";
	private String weaponFactoryPath = "game.engine.weapons.factory.WeaponFactory";
	private String battlePath = "game.engine.Battle";
	private String titanRegistry = "game.engine.titans.TitanRegistry";

	private String titanRegistryPath="game.engine.titans.TitanRegistry";
	private String weaponRegistryPath="game.engine.weapons.WeaponRegistry";
	private String dataLoaderPath="game.engine.dataloader.DataLoader";


	private String wallPath = "game.engine.base.Wall";
	private String lanePath = "game.engine.lanes.Lane";

	private String attackeePath = "game.engine.interfaces.Attackee";
	private String attackerPath = "game.engine.interfaces.Attacker";
	private String mobilPath = "game.engine.interfaces.Mobil";

	private String battlePhasePath = "game.engine.BattlePhase";

	private String gameActionExceptionPath = "game.engine.exceptions.GameActionException";
	private String invalidLaneExceptionPath = "game.engine.exceptions.InvalidLaneException";
	private String insufficientResourcesExceptionPath = "game.engine.exceptions.InsufficientResourcesException";
	private String invalidCSVFormatExceptionPath = "game.engine.exceptions.InvalidCSVFormat";



	private String piercingCannonPath = "game.engine.weapons.PiercingCannon";
	private String sniperCannonPath = "game.engine.weapons.SniperCannon";
	private String volleySpreadCannonPath = "game.engine.weapons.VolleySpreadCannon";
	private String wallTrapPath = "game.engine.weapons.WallTrap";

	private String titanClassPath = "game.engine.titans.Titan";
	private String PureTitanClassPath = "game.engine.titans.PureTitan";
	private String AbnormalTitanClassPath = "game.engine.titans.AbnormalTitan";
	private String ArmoredTitanClassPath = "game.engine.titans.ArmoredTitan";
	private String ColossalTitanClassPath = "game.engine.titans.ColossalTitan";




	//BaseHealth variable
	
	@Test(timeout = 1000)
	public void testWallInstanceVariableBaseHealthIsPrivate() throws ClassNotFoundException, NoSuchFieldException, SecurityException {
		testInstanceVariableIsPrivate(Class.forName(wallPath), "baseHealth");
	}

	@Test(timeout = 1000)
	public void testWallInstanceVariableBaseHealthOfTypeInt() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(wallPath), "baseHealth", int.class);
	}

	@Test(timeout = 1000)
	public void testWallInstanceVariableBaseHealthIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(wallPath), "baseHealth");
	}


	@Test(timeout = 1000)
	public void testWallInstanceVariableBaseHealthGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int baseHealth = (int) (Math.random() * 10) + 1;
		int currentHealth = (int) (Math.random() * 10) + 1;
		Constructor<?> constructor = Class.forName(wallPath).getConstructor(int.class);
		Object createdObject = constructor.newInstance(baseHealth);
		testGetterMethodLogic(createdObject, "baseHealth", baseHealth);
	}

	@Test(timeout = 1000)
	public void testWallInstanceVariableBaseHealthSetterIsAbsent() throws ClassNotFoundException {
		testSetterMethodIsAbsentInClass(Class.forName(wallPath), "setBaseHealth");
	}

	//CurrentHealth variable
	@Test(timeout = 1000)
	public void testWallInstanceVariableCurrentHealthIsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(wallPath), "currentHealth");
	}

	@Test(timeout = 1000)
	public void testWallInstanceVariableCurrentHealthIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(wallPath), "currentHealth");
	}

	@Test(timeout = 1000)
	public void testWasllInstanceVariableCurrentHealthOfTypeInt() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(wallPath), "currentHealth", int.class);
	}

	@Test(timeout = 1000)
	public void testWallInstanceVariableCurrentHealthGetterExistance() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(wallPath), "getCurrentHealth", int.class);
	}

	@Test(timeout = 1000)
	public void testWallInstanceVariableCurrentHealthSetterExists() throws ClassNotFoundException {
		testSetterMethodExistInClass(Class.forName(wallPath), "setCurrentHealth", int.class);
	}



	@Test(timeout = 1000)
	public void testWallInstanceVariableCurrentHealthSetterLogic() throws Exception{
		int baseHealth = (int) (Math.random() * 10) + 1;
		int random = (int) (Math.random() * 10) + 1;
		Constructor<?> constructor = Class.forName(wallPath).getConstructor(int.class);
		Object createdObject = constructor.newInstance(baseHealth);
		testSetterMethodLogic(createdObject, "currentHealth", random, int.class);
		testSetterMethodLogic(createdObject, "currentHealth", -1, int.class);
	}

	@Test(timeout = 1000)
	public void testWallConstructorExists() throws ClassNotFoundException {
		Class[] parameters = {int.class};
		testConstructorExists(Class.forName(wallPath), parameters);
	}

	@Test(timeout = 1000)
	public void testWallConstructorInitialization() throws Exception{
		int baseHealth = (int) (Math.random() * 10) + 1;
		//int currentHealth = (int) (Math.random() * 10) + 1;
		Constructor<?> constructor = Class.forName(wallPath).getConstructor(int.class);
		Object createdObject = constructor.newInstance(baseHealth);

		String[] names = {"currentHealth", "baseHealth"};
		Object[] values = {baseHealth,baseHealth};
		testWallConstructorInitialization(createdObject, names, values);
	}

	@Test(timeout = 1000)
	public void testWallClassImplementsAttackeeInterface() {
		try {
			testClassImplementsInterface(Class.forName(wallPath), Class.forName(attackeePath));
		}
		catch(ClassNotFoundException e) {
			assertTrue(e.getClass().getName() + " occurred: " + e.getMessage(), false);
		}
	}

	///////////////////////////////// Lane Class ////////////////////////////////////////////
	//LaneWall instanceVariable
	@Test(timeout = 1000)
	public void testInstanceVariableLaneWallPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(lanePath), "laneWall");
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableLaneWallIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(lanePath), "laneWall");
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableLaneWallIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(lanePath), "laneWall");
	}


	@Test(timeout = 1000)
	public void testLaneInstanceVariableLaneWallGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(lanePath), "getLaneWall", Class.forName(wallPath));
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableLaneWallGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int baseHealth = (int) (Math.random() * 10) + 1;
		int dangerLavel = (int) (Math.random() * 10) + 1;
		Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
		Object wall = wallConstructor.newInstance(baseHealth);

		Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
		Object lane = laneConstructor.newInstance(wall);
		testGetterMethodLogic(lane, "laneWall", wall);
	}


	//dangerLevel InstanceVariable
	@Test(timeout = 1000)
	public void testLaneInstanceVariableDangerLevelPersent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(lanePath), "dangerLevel");
	}

	

	@Test(timeout = 1000)
	public void testLaneInstanceVariableDangerLevelOfType() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(lanePath), "dangerLevel", int.class);
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableDangerLevelGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(lanePath), "getDangerLevel", int.class);
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableDangerLevelGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int baseHealth = (int) (Math.random() * 10) + 1;
		int dangerLavel = (int) (Math.random() * 10) + 1;
		Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
		Object wall = wallConstructor.newInstance(baseHealth);

		Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
		Object lane = laneConstructor.newInstance(wall);
		testGetterMethodLogic(lane, "dangerLevel", dangerLavel);
	}

	@Test(timeout = 1000)
	public void testWallInstanceVariableDangerLevelSetterExists() throws ClassNotFoundException {
		testSetterMethodExistInClass(Class.forName(lanePath), "setDangerLevel", int.class);
	}

	@Test(timeout = 1000)
	public void testWallInstanceVariableDangerLevelSetterLogic() throws Exception{
		int baseHealth = (int) (Math.random() * 10) + 1;
		int dangerLavel = (int) (Math.random() * 10) + 1;
		Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
		Object wall = wallConstructor.newInstance(baseHealth);

		Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
		Object lane = laneConstructor.newInstance(wall);

		testSetterMethodLogic(lane, "dangerLevel", dangerLavel, int.class);
	}

	//titans instanceVariable
	@Test(timeout = 1000)
	public void testLaneInstanceVariableTitansPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(lanePath), "titans");
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableTitansIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(lanePath), "titans");
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableTitansIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(lanePath), "titans");
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableTitansOfType() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(lanePath), "titans", PriorityQueue.class);
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableTitansGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(lanePath), "getTitans", PriorityQueue.class);
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableTitansGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int baseHealth = (int) (Math.random() * 10) + 1;
		int dangerLavel = (int) (Math.random() * 10) + 1;
		Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
		Object wall = wallConstructor.newInstance(baseHealth);

		Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
		Object lane = laneConstructor.newInstance(wall);
		PriorityQueue<Object> q = new PriorityQueue<>();
		testGetterMethodLogic(lane, "titans", q);
	}

	//weapons instanceVariable
	@Test(timeout = 1000)
	public void testLaneInstanceVariableWeaponsPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(lanePath), "weapons");
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableWeaponsIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(lanePath), "weapons");
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableWeaponsIsFinal() throws ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(lanePath), "weapons");
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableWeaponsOfType() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(lanePath), "weapons", ArrayList.class);
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableWeaponsGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(lanePath), "getWeapons", ArrayList.class);
	}

	@Test(timeout = 1000)
	public void testLaneInstanceVariableWeaponsGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int baseHealth = (int) (Math.random() * 10) + 1;
		int dangerLavel = (int) (Math.random() * 10) + 1;
		Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
		Object wall = wallConstructor.newInstance(baseHealth);

		Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
		Object lane = laneConstructor.newInstance(wall);
		ArrayList<Object> w = new ArrayList<>();;
		testGetterMethodLogic(lane, "weapons", w);
	}

	

	@Test(timeout = 1000)
	public void testLaneConstructorInitialization() throws Exception{
		int baseHealth = (int) (Math.random() * 10) + 1;
		//int currentHealth = (int) (Math.random() * 10) + 1;
		Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
		Object wall = wallConstructor.newInstance(baseHealth);

		Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
		Object lane = laneConstructor.newInstance(wall);

		String[] names = {"laneWall","dangerLevel","titans","weapons"};
		Object[] values = {wall,0,0,0};
		testLaneConstructorInitialization(lane, names, values);
	}

	@Test(timeout = 1000)
	public void testLaneClassImplementsComparableInterface() {
		try {
			testClassImplementsInterface(Class.forName(lanePath), Comparable.class);
		}
		catch(ClassNotFoundException e) {
			assertTrue(e.getClass().getName() + " occurred: " + e.getMessage(), false);
		}
	}

	@Test(timeout = 1000)
	public void testLaneCompareToMethod() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class aClass = Class.forName(lanePath);
		Method m = null;
		try {
			m = aClass.getDeclaredMethod("compareTo", Class.forName(lanePath)); 
		}
		catch(NoSuchMethodException e) {
			fail("You have to override the compare method");
		}
		Class wall = Class.forName(wallPath);

		Constructor<?> wallConstructor = wall.getConstructor(int.class);

		Object createdWall1 = wallConstructor.newInstance((int) (Math.random() * 10) + 1);
		Object createdWall2 = wallConstructor.newInstance((int) (Math.random() * 10) + 1);

		Constructor<?> laneConstructor = aClass.getConstructor(Class.forName(wallPath));
		Object createdLane1 = laneConstructor.newInstance(createdWall1);
		Object createdLane2 = laneConstructor.newInstance(createdWall2);

		Field f1 = aClass.getDeclaredField("dangerLevel");
		f1.setAccessible(true);
		Field f2 = aClass.getDeclaredField("dangerLevel");
		f2.setAccessible(true);

		//dangerLevel2 > dangerLevel1
		int dangerLevel1 = (int) (Math.random() * 10) + 1;
		int dangerLevel2 = (int) (Math.random() * 20) + 10;

		//createdLane2 > createdLane1
		f1.set(createdLane1, dangerLevel1);
		f2.set(createdLane2, dangerLevel2);

		int actual = (int) m.invoke(createdLane2, createdLane1);
		actual = actual > 0 ? 1 : (actual < 0 ? -1 : 0);
		int expected = 1;
		assertEquals("The method should return a positive value. make sure that you handle the case where the current object danger level is greater than the passed object danger level", 
				expected, actual);

		//createdLane2 < createdLane1
		f1.set(createdLane1, dangerLevel2);
		f2.set(createdLane2, dangerLevel1);
		actual = (int) m.invoke(createdLane2, createdLane1);
		actual = actual > 0 ? 1 : (actual < 0 ? -1 : 0);
		expected = -1;

		assertEquals("The method should return a negative value. make sure that you handle the case where the current object danger level is less than the passed object danger level", 
				expected, actual);

		//createdLane2 == createdLane1
		f1.set(createdLane1, dangerLevel1);
		f2.set(createdLane2, dangerLevel1);
		actual = (int) m.invoke(createdLane2, createdLane1);
		actual = actual > 0 ? 1 : (actual < 0 ? -1 : 0);
		expected = 0;

		assertEquals("The method should return zero. make sure that you handle the case where the current object danger level is equal to the passed object danger level", 
				expected, actual);

	}



	//////////////////////////////// Attackee Interface ////////////////////////////////////////

	@Test(timeout = 1000)
	public void testAttackeeIsAnInterface() throws ClassNotFoundException {
		testIsInterface(Class.forName(attackeePath));
	}

	@Test(timeout = 1000)
	public void testgetCurrentHealthInAttackeeInterface() throws ClassNotFoundException {
		testInterfaceMethod(Class.forName(attackeePath), "getCurrentHealth", int.class, null);
	}

	@Test(timeout = 1000)
	public void testSetCurrentHealthInAttackeeInterface() throws ClassNotFoundException {
		testInterfaceMethod(Class.forName(attackeePath), "setCurrentHealth", Void.TYPE, new Class[] {int.class});
	}

	@Test(timeout = 1000)
	public void testGetResourcesValueInAttackeeInterface() throws ClassNotFoundException {
		testInterfaceMethod(Class.forName(attackeePath), "getResourcesValue", int.class, null);
	}

	//////////////////////////////////// Attacker Interface ///////////////////////////////////
	@Test(timeout = 1000)
	public void testAttackerIsAnInterface() throws ClassNotFoundException {
		testIsInterface(Class.forName(attackerPath));
	}

	@Test(timeout = 1000)
	public void testGetDamageInAttackeeInterface() throws ClassNotFoundException {
		testInterfaceMethod(Class.forName(attackerPath), "getDamage", int.class, null);
	}


	///////////////////////////////////// Mobil Interface //////////////////////////////////// 
	

	@Test(timeout = 1000)
	public void testGetDistanceInAttackeeInterface() throws ClassNotFoundException {
		testInterfaceMethod(Class.forName(mobilPath), "getDistance", int.class, null);
	}

	@Test(timeout = 1000)
	public void testSetDistanceInAttackeeInterface() throws ClassNotFoundException {
		testInterfaceMethod(Class.forName(mobilPath), "setDistance", Void.TYPE, new Class[] {int.class});
	}

	@Test(timeout = 1000)
	public void testGetSpeedInAttackeeInterface() throws ClassNotFoundException {
		testInterfaceMethod(Class.forName(mobilPath), "getSpeed", int.class, null);
	}

	@Test(timeout = 1000)
	public void testSetSpeedInAttackeeInterface() throws ClassNotFoundException {
		testInterfaceMethod(Class.forName(mobilPath), "setSpeed", Void.TYPE, new Class[] {int.class});
	}

	////////////////////////////////////////// Enums /////////////////////////////////////////
	@Test(timeout = 1000)
	public void testBattlePhaseIsEnum() throws ClassNotFoundException {
		testIsEnum(Class.forName(battlePhasePath));
	}

	@Test(timeout = 1000)
	public void testBattlePhaseEnumValues() {
		testEnumValues(battlePhasePath, "battlePhase", new String[] {"EARLY", "INTENSE", "GRUMBLING"});
	}



	/////////////////////////////////////// Exceptions //////////////////////////////////////////

	/////////////////// GameActionException //////////////////////

	@Test(timeout = 1000)
	public void testGameActionExceptionIsAbstract() throws ClassNotFoundException {
		testClassIsAbstract(Class.forName(gameActionExceptionPath));
	}

	@Test(timeout = 1000)
	public void testGameActionExceptionIsSubClassOfException() throws ClassNotFoundException {
		testClassIsSubClass(Exception.class, Class.forName(gameActionExceptionPath));
	}

	@Test(timeout = 1000)
	public void testGameActionExceptionEmptyConstructorExists() throws ClassNotFoundException {
		testConstructorExists(Class.forName(gameActionExceptionPath), new Class[] {});
	}

	@Test(timeout = 1000)
	public void testGameActionExceptionConstructorExists() throws ClassNotFoundException {
		testConstructorExists(Class.forName(gameActionExceptionPath), new Class[] {String.class});
	}


	/////////////////// InvalidLaneException ///////////////


	@Test(timeout = 1000)
	public void testInvalidLaneExceptionIsSubClassOfException() throws ClassNotFoundException {
		testClassIsSubClass(Class.forName(gameActionExceptionPath), Class.forName(invalidLaneExceptionPath));
	}


	@Test(timeout = 1000)
	public void testInvalidLanExceptionMSGIsPrivate() throws SecurityException, ClassNotFoundException, NoSuchFieldException {
		testInstanceVariableIsPrivate(Class.forName(invalidLaneExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInvalidLanExceptionMSGIsFinal() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(invalidLaneExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInvalidLanExceptionMSGIsStatic() throws ClassNotFoundException {
		testInstanceVariableIsStatic(Class.forName(invalidLaneExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInvalidLanExceptionMSGValue() throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class aClass = Class.forName(invalidLaneExceptionPath);
		Field f = aClass.getDeclaredField("MSG");
		f.setAccessible(true);
		String expected = "Action done on an invalid lane";
		String actual = (String) f.get(null);
		assertEquals("wrong value of MSG", expected, actual);
	}



	@Test(timeout = 1000)
	public void testInvalidLanExceptionEmptyConstructorExists() throws ClassNotFoundException {
		testConstructorExists(Class.forName(invalidLaneExceptionPath), new Class[] {});
	}

	@Test(timeout = 1000)
	public void testInvalidLanExceptionConstructorExists() throws ClassNotFoundException {
		testConstructorExists(Class.forName(invalidLaneExceptionPath), new Class[] {String.class});
	}

	@Test(timeout = 1000)
	public void testnvalidLanExceptionEmptyConstructorInitialization() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class aClass = Class.forName(invalidLaneExceptionPath);
		Constructor<?> constructor = aClass.getConstructor(null);
		String msg = "Action done on an invalid lane";
		Object createdObject = constructor.newInstance();
		Method m =  null;
		while(m == null) {
			try {
				m = aClass.getDeclaredMethod("getMessage");
			}
			catch(NoSuchMethodException e) {
				aClass = aClass.getSuperclass();
			}
		}


		assertEquals("wrong formatted message", msg, m.invoke(createdObject));
	}

	@Test(timeout = 1000)
	public void testnvalidLanExceptionConstructorInitialization() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class aClass = Class.forName(invalidLaneExceptionPath);
		Constructor<?> constructor = aClass.getConstructor(String.class);
		String msg = "Dummy text";
		Object createdObject = constructor.newInstance(msg);
		Method m =  null;
		while(m == null) {
			try {
				m = aClass.getDeclaredMethod("getMessage");
			}
			catch(NoSuchMethodException e) {
				aClass = aClass.getSuperclass();
			}
		}


		assertEquals("wrong formatted message", msg, m.invoke(createdObject));
	}

	/////////////////// InsufficientResourcesException ///////////////

	@Test(timeout = 1000)
	public void testInsufficientResourcesExceptionIsSubClassOfException() throws ClassNotFoundException {
		testClassIsSubClass(Class.forName(gameActionExceptionPath), Class.forName(insufficientResourcesExceptionPath));
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesMSGPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(insufficientResourcesExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesMSGIsPrivate() throws SecurityException, ClassNotFoundException, NoSuchFieldException {
		testInstanceVariableIsPrivate(Class.forName(insufficientResourcesExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesMSGIsFinal() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(insufficientResourcesExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesMSGIsStatic() throws ClassNotFoundException {
		testInstanceVariableIsStatic(Class.forName(insufficientResourcesExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesMSGOfType() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(insufficientResourcesExceptionPath), "MSG", String.class);
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesExceptionMSGValue() throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class aClass = Class.forName(insufficientResourcesExceptionPath);
		Field f = aClass.getDeclaredField("MSG");
		f.setAccessible(true);
		String expected = "Not enough resources, resources provided = ";
		String actual = (String) f.get(null);
		assertEquals("wrong value of MSG", expected, actual);
	}


	@Test(timeout = 1000)
	public void testInsufficientResourcesResourcesProvidedPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(insufficientResourcesExceptionPath), "resourcesProvided");
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesResourcesProvidedIsPrivate() throws SecurityException, ClassNotFoundException, NoSuchFieldException {
		testInstanceVariableIsPrivate(Class.forName(insufficientResourcesExceptionPath), "resourcesProvided");
	}


	@Test(timeout = 1000)
	public void testInsufficientResourcesResourcesProvidedOfType() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(insufficientResourcesExceptionPath), "resourcesProvided", int.class);
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesResourcesProvidedGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(insufficientResourcesExceptionPath), "getResourcesProvided", int.class);
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesResourcesProvidedGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int resourcesProvided = (int) (Math.random() * 10) + 1;
		Constructor<?> Constructor = Class.forName(insufficientResourcesExceptionPath).getConstructor(int.class);
		Object createdObject = Constructor.newInstance(resourcesProvided);

		testGetterMethodLogic(createdObject, "resourcesProvided", resourcesProvided);
	}



	@Test(timeout = 1000)
	public void testInsufficientResourcesResourcesProvidedSetterExists() throws ClassNotFoundException {
		testSetterMethodExistInClass(Class.forName(insufficientResourcesExceptionPath), "setResourcesProvided", int.class);
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesResourcesProvidedSetterLogic() throws Exception{
		int resourcesProvided1 = (int) (Math.random() * 10) + 1;
		int resourcesProvided2 = (int) (Math.random() * 10) + 1;
		Constructor<?> Constructor = Class.forName(insufficientResourcesExceptionPath).getConstructor(int.class);
		Object createdObject = Constructor.newInstance(resourcesProvided1);


		testSetterMethodLogic(createdObject, "resourcesProvided", resourcesProvided2, int.class);
	}




	@Test(timeout = 1000)
	public void testInsufficientResourcesExceptionEmptyConstructorExists() throws ClassNotFoundException {
		testConstructorExists(Class.forName(insufficientResourcesExceptionPath), new Class[] {int.class});
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesExceptionConstructorExists() throws ClassNotFoundException {
		testConstructorExists(Class.forName(insufficientResourcesExceptionPath), new Class[] {String.class,int.class});
	}


	@Test(timeout = 1000)
	public void testInsufficientResourcesExceptionFirstConstructorInitialization() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class aClass = Class.forName(insufficientResourcesExceptionPath);
		Constructor<?> constructor = aClass.getConstructor(int.class);
		String msg = "Not enough resources, resources provided = ";
		int resourcesProvided  = (int) (Math.random() * 10) + 1;
		Object createdObject = constructor.newInstance(resourcesProvided);
		Method m =  null;
		while(m == null) {
			try {
				m = aClass.getDeclaredMethod("getMessage");
			}
			catch(NoSuchMethodException e) {
				aClass = aClass.getSuperclass();
			}
		}
		Field f = Class.forName(insufficientResourcesExceptionPath).getDeclaredField("resourcesProvided");
		f.setAccessible(true);

		assertEquals("wrong formatted message", msg+resourcesProvided, m.invoke(createdObject));
		assertEquals("wrong initialization of resourcesProvided", resourcesProvided, f.get(createdObject));
	}

	@Test(timeout = 1000)
	public void testInsufficientResourcesExceptionSecondConstructorInitialization() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class aClass = Class.forName(insufficientResourcesExceptionPath);
		Constructor<?> constructor = aClass.getConstructor(String.class, int.class);
		String msg = "Dummy message";
		int resourcesProvided  = (int) (Math.random() * 10) + 1;
		Object createdObject = constructor.newInstance(msg,resourcesProvided);
		Method m =  null;
		while(m == null) {
			try {
				m = aClass.getDeclaredMethod("getMessage");
			}
			catch(NoSuchMethodException e) {
				aClass = aClass.getSuperclass();
			}
		}
		Field f = Class.forName(insufficientResourcesExceptionPath).getDeclaredField("resourcesProvided");
		f.setAccessible(true);

		assertEquals("wrong formatted message", msg, m.invoke(createdObject));
		assertEquals("wrong initialization of resourcesProvided", resourcesProvided, f.get(createdObject));
	}




	/////////////////// InvalidCSVFormatException ///////////////

	@Test(timeout = 1000)
	public void testInvalidCSVFormatExceptionIsSubClassOfException() throws ClassNotFoundException {
		testClassIsSubClass(IOException.class, Class.forName(invalidCSVFormatExceptionPath));
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatMSGPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(invalidCSVFormatExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatMSGIsPrivate() throws SecurityException, ClassNotFoundException, NoSuchFieldException {
		testInstanceVariableIsPrivate(Class.forName(invalidCSVFormatExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatMSGIsFinal() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(invalidCSVFormatExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatMSGIsStatic() throws ClassNotFoundException {
		testInstanceVariableIsStatic(Class.forName(invalidCSVFormatExceptionPath), "MSG");
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatMSGOfType() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(invalidCSVFormatExceptionPath), "MSG", String.class);
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatExceptionMSGValue() throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class aClass = Class.forName(invalidCSVFormatExceptionPath);
		Field f = aClass.getDeclaredField("MSG");
		f.setAccessible(true);
		String expected = "Invalid input detected while reading csv file, input = \n";
		String actual = (String) f.get(null);
		assertEquals("wrong value of MSG", expected, actual);
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatInputLinePresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(invalidCSVFormatExceptionPath), "inputLine");
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatInputLineIsPrivate() throws SecurityException, ClassNotFoundException, NoSuchFieldException {
		testInstanceVariableIsPrivate(Class.forName(invalidCSVFormatExceptionPath), "inputLine");
	}


	@Test(timeout = 1000)
	public void testInvalidCSVFormatInputLineOfType() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(invalidCSVFormatExceptionPath), "inputLine", String.class);
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatInputLineGetterExists() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(invalidCSVFormatExceptionPath), "getInputLine", String.class);
	}

	





	@Test(timeout = 1000)
	public void testInvalidCSVFormatInputLineSetterExists() throws ClassNotFoundException {
		testSetterMethodExistInClass(Class.forName(invalidCSVFormatExceptionPath), "setInputLine", String.class);
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatInputLineSetterLogic() throws Exception{
		String inputLine1 = "dummy text for creating an object";
		String inputLine2 = "dummy text for test";
		Constructor<?> Constructor = Class.forName(invalidCSVFormatExceptionPath).getConstructor(String.class);
		Object createdObject = Constructor.newInstance(inputLine1);


		testSetterMethodLogic(createdObject, "inputLine", inputLine2, String.class);
	}


	@Test(timeout = 1000)
	public void testInvalidCSVFormatExceptionFirstConstructorExists() throws ClassNotFoundException {
		testConstructorExists(Class.forName(invalidCSVFormatExceptionPath), new Class[] {String.class});
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatExceptionSecondConstructorExists() throws ClassNotFoundException {
		testConstructorExists(Class.forName(invalidCSVFormatExceptionPath), new Class[] {String.class,String.class});
	}


	@Test(timeout = 1000)
	public void testInvalidCSVFormatExceptionFirstConstructorInitialization() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class aClass = Class.forName(invalidCSVFormatExceptionPath);
		Constructor<?> constructor = aClass.getConstructor(String.class);
		String msg = "Invalid input detected while reading csv file, input = \n";
		String inputLine  = "Dummy text";
		Object createdObject = constructor.newInstance(inputLine);
		Method m =  null;
		while(m == null) {
			try {
				m = aClass.getDeclaredMethod("getMessage");
			}
			catch(NoSuchMethodException e) {
				aClass = aClass.getSuperclass();
			}
		}
		Field f = Class.forName(invalidCSVFormatExceptionPath).getDeclaredField("inputLine");
		f.setAccessible(true);

		assertEquals("wrong formatted message", msg+inputLine, m.invoke(createdObject));
		assertEquals("wrong initialization of resourcesProvided", inputLine, f.get(createdObject));
	}

	@Test(timeout = 1000)
	public void testInvalidCSVFormatExceptionSecondConstructorInitialization() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class aClass = Class.forName(invalidCSVFormatExceptionPath);
		Constructor<?> constructor = aClass.getConstructor(String.class,String.class);
		String msg = "Dummy message : /n";
		String inputLine  = "Dummy text";
		Object createdObject = constructor.newInstance(msg,inputLine);
		Method m =  null;
		while(m == null) {
			try {
				m = aClass.getDeclaredMethod("getMessage");
			}
			catch(NoSuchMethodException e) {
				aClass = aClass.getSuperclass();
			}
		}
		Field f = Class.forName(invalidCSVFormatExceptionPath).getDeclaredField("inputLine");
		f.setAccessible(true);

		assertEquals("wrong formatted message", msg, m.invoke(createdObject));
		assertEquals("wrong initialization of resourcesProvided", inputLine, f.get(createdObject));
	}
















	@Test(timeout = 1000)
	public void testClassTitanExists() {
		try {
			Class.forName(titanClassPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Titan");
		}
	}
	
	@Test(timeout = 1000)
	public void testClassAbnormalTitanExists() {
		try {
			Class.forName(AbnormalTitanClassPath);
		} catch (ClassNotFoundException e) {
			fail("missing class AbnormalTitan");
		}
	}
	@Test(timeout = 1000)
	public void testClassArmoredTitanExists() {
		try {
			Class.forName(ArmoredTitanClassPath);
		} catch (ClassNotFoundException e) {
			fail("missing class ArmoredTitan");
		}
	}
	@Test(timeout = 1000)
	public void testClassColossalTitanExists() {
		try {
			Class.forName(ColossalTitanClassPath);
		} catch (ClassNotFoundException e) {
			fail("missing class ColossalTitan");
		}
	}



	@Test(timeout = 1000)
	public void testIsTitanAnAbstractClass() throws Exception {
		testClassIsAbstract(Class.forName(titanClassPath));
	}
	@Test(timeout = 1000)
	public void testConstructorTitan() throws ClassNotFoundException {
		Class[] inputs = { int.class, int.class, int.class,int.class,int.class,int.class , int.class};
		testConstructorExists(Class.forName(titanClassPath), inputs);
	}
	
	
	@Test(timeout = 1000)
	public void testbaseDamageFinalAttribute() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field baseDamageField = Class.forName(titanClassPath).getDeclaredField("baseDamage");
		assertTrue("The baseDamage attribute should be final", Modifier.isFinal(baseDamageField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testbaseDamagePrivateAttribute() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field baseDamageField = Class.forName(titanClassPath).getDeclaredField("baseDamage");
		assertTrue("The baseDamage attribute should be private", Modifier.isPrivate(baseDamageField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testheightInMetersFinalAttribute() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field heightInMetersField = Class.forName(titanClassPath).getDeclaredField("heightInMeters");
		assertTrue("The heightInMeters attribute should be final", Modifier.isFinal(heightInMetersField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testheightInMetersPrivateAttribute() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field heightInMetersField = Class.forName(titanClassPath).getDeclaredField("heightInMeters");
		assertTrue("The heightInMeters attribute should be private", Modifier.isPrivate(heightInMetersField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testresourcesValueFinalAttribute() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field resourcesValueField = Class.forName(titanClassPath).getDeclaredField("resourcesValue");
		assertTrue("The heightInMeters attribute should be final", Modifier.isFinal(resourcesValueField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testresourcesValuePrivateAttribute() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field resourcesValueField = Class.forName(titanClassPath).getDeclaredField("resourcesValue");
		assertTrue("The heightInMeters attribute should be private", Modifier.isPrivate(resourcesValueField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testdangerLevelFinalAttribute() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field dangerLevelField = Class.forName(titanClassPath).getDeclaredField("dangerLevel");
		assertTrue("The dangerLevel attribute should be final", Modifier.isFinal(dangerLevelField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testdangerLevelPrivateAttribute() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field dangerLevelField = Class.forName(titanClassPath).getDeclaredField("dangerLevel");
		assertTrue("The dangerLevel attribute should be private", Modifier.isPrivate(dangerLevelField.getModifiers()));
	}

	@Test(timeout = 1000)
	public void testcurrentHealthExistance() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("currentHealth",titanClassPath);
	}
	@Test(timeout = 1000)
	public void testbaseDamageExistance() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("baseDamage",titanClassPath);
	}
	@Test(timeout = 1000)
	public void testheightInMetersExistance() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("heightInMeters",titanClassPath);
	}
	@Test(timeout = 1000)
	public void testdistanceFromBaseExistance() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("distanceFromBase",titanClassPath);
	}
	@Test(timeout = 1000)
	public void testspeedExistance() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("speed",titanClassPath);
	}
	@Test(timeout = 1000)
	public void testresourcesValueExistance() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("resourcesValue",titanClassPath);
	}
	@Test(timeout = 1000)
	public void testdangerLevelExistance() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("dangerLevel",titanClassPath);
	}

	
	
	@Test(timeout = 1000)
	public void testHeightInMetersReadOnly() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanClassPath),"getHeightInMeters",int.class,true);
		testSetterMethodExistsInClass(Class.forName(titanClassPath),"setHeightInMeters",int.class,false);
	}
	@Test(timeout = 1000)
	public void testDistanceFromBaseReadAndWrite() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanClassPath),"getDistance",int.class,true);
		testSetterMethodExistsInClass(Class.forName(titanClassPath),"setDistance",int.class,true);
	}
	@Test(timeout = 1000)
	public void testSpeedReadAndWrite() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanClassPath),"getSpeed",int.class,true);
		testSetterMethodExistsInClass(Class.forName(titanClassPath),"setSpeed",int.class,true);
	}
	@Test(timeout = 1000)
	public void testResourcesValueReadOnly() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanClassPath),"getResourcesValue",int.class,true);
		testSetterMethodExistsInClass(Class.forName(titanClassPath),"setResourcesValue",int.class,false);
	}
	
	@Test(timeout = 1000)
	public void testDangerLevelReadOnly() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanClassPath),"getResourcesValue",int.class,true);
		testSetterMethodExistsInClass(Class.forName(titanClassPath),"setResourcesValue",int.class,false);
	}

	@Test(timeout = 1000)
	public void testTestingPureTitanIsSubClassOfTitan() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(PureTitanClassPath), 
				Class.forName(titanClassPath));
	}
	@Test(timeout = 1000)
	public void testTestingAbnormalTitanIsSubClassOfTitan() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(AbnormalTitanClassPath), 
				Class.forName(titanClassPath));
	}
	@Test(timeout = 1000)
	public void testTestingArmoredTitanIsSubClassOfTitan() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(ArmoredTitanClassPath), 
				Class.forName(titanClassPath));
	}
	@Test(timeout = 1000)
	public void testTestingColossalTitanIsSubClassOfTitan() throws ClassNotFoundException {
		testClassIsSubclass(Class.forName(ColossalTitanClassPath), 
				Class.forName(titanClassPath));
	}

	@Test(timeout = 1000)
	public void testTitanCodeExistanceInPureClass() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("TITAN_CODE",PureTitanClassPath);
	}
	@Test(timeout = 1000)
	public void testTitanCodeExistanceInAbnormalClass() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("TITAN_CODE",AbnormalTitanClassPath);
	}
	@Test(timeout = 1000)
	public void testTitanCodeExistanceInArmoredClass() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("TITAN_CODE",ArmoredTitanClassPath);
	}
	
	
	
	@Test(timeout = 1000)
	public void testTitanCodeFinalAttributeInPureTitan() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(PureTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be final", Modifier.isFinal(titanCodeField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testTitanCodeFinalAttributeInAbnormalClass() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(AbnormalTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be final", Modifier.isFinal(titanCodeField.getModifiers()));
	}
	
	@Test(timeout = 1000)
	public void testTitanCodeFinalAttributeInColossalClass() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(ColossalTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be final", Modifier.isFinal(titanCodeField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testTitanCodeStaticAttributeInPureTitan() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(PureTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be static", Modifier.isStatic(titanCodeField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testTitanCodeStaticAttributeInAbnormalClass() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(AbnormalTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be static", Modifier.isStatic(titanCodeField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testTitanCodeStaticAttributeInArmoredClass() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(ArmoredTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be static", Modifier.isStatic(titanCodeField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testTitanCodeStaticAttributeInColossalClass() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(ColossalTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be static", Modifier.isStatic(titanCodeField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testTitanCodePublicAttributeInPureTitan() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(PureTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be public", Modifier.isPublic(titanCodeField.getModifiers()));
	}
	@Test(timeout = 1000)
	public void testTitanCodePublicAttributeInAbnormalClass() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(AbnormalTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be public", Modifier.isPublic(titanCodeField.getModifiers()));
	}
	
	
	@Test(timeout = 1000)
	public void testTitanCodeValues() throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {

		Field titanCodeField = Class.forName(PureTitanClassPath).getDeclaredField("TITAN_CODE");
		assertEquals(1,titanCodeField.getInt(null));

		Field abnormaltitanCodeField = Class.forName(AbnormalTitanClassPath).getDeclaredField("TITAN_CODE");
		assertEquals(2,abnormaltitanCodeField.getInt(null));

		Field armoredCodeField = Class.forName(ArmoredTitanClassPath).getDeclaredField("TITAN_CODE");
		assertEquals(3,armoredCodeField.getInt(null));

		Field colossalCodeField = Class.forName(ColossalTitanClassPath).getDeclaredField("TITAN_CODE");
		assertEquals(4,colossalCodeField.getInt(null));
	}

	@Test(timeout = 1000)
	public void testConstructorPureTitan() throws ClassNotFoundException {
		Class[] inputs = { int.class, int.class, int.class,int.class,int.class,int.class,int.class };
		testConstructorExists(Class.forName(PureTitanClassPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorAbnormalTitan() throws ClassNotFoundException {
		Class[] inputs = { int.class, int.class, int.class,int.class,int.class,int.class,int.class };
		testConstructorExists(Class.forName(AbnormalTitanClassPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorArmoredTitan() throws ClassNotFoundException {
		Class[] inputs = { int.class, int.class, int.class,int.class,int.class,int.class,int.class };
		testConstructorExists(Class.forName(ArmoredTitanClassPath), inputs);
	}



	@Test(timeout = 1000)
	public void testConstructorInitializationAbnormalTitan() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);


		Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object abnormalTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
		String[] names = { "baseHealth", "baseDamage", "heightInMeters", "distanceFromBase", "speed", "resourcesValue",
		"dangerLevel" };
		Object[] values = { baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel };

		Class superClass = Class.forName(AbnormalTitanClassPath).getSuperclass();
		Method m = superClass.getDeclaredMethod("getCurrentHealth", null);
		assertEquals(baseHealth, m.invoke(abnormalTitan, null));

		testConstructorInitialization(abnormalTitan, names, values);


	}

	@Test(timeout = 1000)
	public void testConstructorInitializationArmoredTitan() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);


		Constructor<?> constructor = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object armoredTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
		String[] names = { "baseHealth", "baseDamage", "heightInMeters", "distanceFromBase", "speed", "resourcesValue",
		"dangerLevel" };
		Object[] values = { baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel };

		Class superClass = Class.forName(ArmoredTitanClassPath).getSuperclass();
		Method m = superClass.getDeclaredMethod("getCurrentHealth", null);
		assertEquals(baseHealth, m.invoke(armoredTitan, null));

		testConstructorInitialization(armoredTitan, names, values);
	}

	@Test(timeout = 1000)
	public void testConstructorInitializationColossalTitan() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);


		Constructor<?> constructor = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object colossalTitan =   constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
		String[] names = { "baseHealth", "baseDamage", "heightInMeters", "distanceFromBase", "speed", "resourcesValue",
		"dangerLevel" };
		Object[] values = { baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel };


		Class superClass = Class.forName(ColossalTitanClassPath).getSuperclass();
		Method m = superClass.getDeclaredMethod("getCurrentHealth", null);
		assertEquals(baseHealth, m.invoke(colossalTitan, null));

		testConstructorInitialization(colossalTitan, names, values);
	}


	@Test(timeout = 1000)
	public void testGetterLogicForInstanceVariableBaseDamageInClassPureTitan() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,dangerLevel,resourcesValue);
		testGetterLogic(pureTitan, "baseDamage", baseDamage,"getDamage");
	}

	@Test(timeout = 1000)
	public void testGetterLogicForInstanceVariableHeightInMetersInClassPureTitan() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,dangerLevel,resourcesValue);
		testGetterLogic(pureTitan, "heightInMeters", heightInMeters,"getHeightInMeters");
	}

	

	@Test(timeout = 1000)  
	public void testGetterLogicForInstanceVariableResourcesValueInClassPureTitan() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,dangerLevel,resourcesValue);
		testGetterLogic(pureTitan, "resourcesValue", resourcesValue,"getResourcesValue");
	}

	@Test(timeout = 1000)
	public void testGetterLogicForInstanceVariableDangerLevelValueInClassPureTitan() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,dangerLevel,resourcesValue);
		testGetterLogic(pureTitan, "dangerLevel", dangerLevel,"getDangerLevel");
	}

	@Test(timeout = 1000)
	public void testSetterForInstanceVariableCurrentHealthDoesNotExistInTitanSubClasses() throws Exception {
		String[] subClasses = { PureTitanClassPath, AbnormalTitanClassPath , ArmoredTitanClassPath ,ColossalTitanClassPath};
		testSetterAbsentInSubclasses("setCurrentHealth", subClasses);
	}
	@Test(timeout = 1000)
	public void testSetterForInstanceVariableDistanceFromBaseDoesNotExistInTitanSubClasses() throws Exception {
		String[] subClasses = { PureTitanClassPath, AbnormalTitanClassPath , ArmoredTitanClassPath ,ColossalTitanClassPath};
		testSetterAbsentInSubclasses("setDistance", subClasses);
	}
	


	@Test(timeout = 1000)
	public void testSpeedSetterLogic() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,dangerLevel,resourcesValue);
		Class superClass = Class.forName(PureTitanClassPath).getSuperclass();

		Method setterMethod = superClass.getDeclaredMethod("setSpeed",  int.class);
		Method getterMethod = superClass.getDeclaredMethod("getSpeed",  null);

		int new_speed = (int) (Math.random() * 5);
		setterMethod.invoke(pureTitan,new_speed);
		assertEquals(new_speed, getterMethod.invoke(pureTitan,null));
	}

	

	@Test(timeout = 1000)
	public void testCurrentHealthSetterLogic() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);;
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,dangerLevel,resourcesValue);
		Class superClass = Class.forName(PureTitanClassPath).getSuperclass();

		Method setterMethod = superClass.getDeclaredMethod("setCurrentHealth",  int.class);
		Method getterMethod = superClass.getDeclaredMethod("getCurrentHealth",  null);

		int new_currentHealth = (int) (Math.random()* 100);
		setterMethod.invoke(pureTitan,new_currentHealth);
		assertEquals(new_currentHealth, getterMethod.invoke(pureTitan,null));

		int negative_currentHealth = -1;
		setterMethod.invoke(pureTitan,negative_currentHealth);
		assertEquals(0, getterMethod.invoke(pureTitan,null));
	}



	@Test(timeout = 1000)
	public void testForCompExistence() throws ClassNotFoundException {
		Class givenClass = Class.forName(titanClassPath);
		try {
			Method method = givenClass.getDeclaredMethod("compareTo", Class.forName(titanClassPath));
			assertTrue("Method compareTo takes the expected parameter type.", method != null);
		} catch (NoSuchMethodException e) {
			assertTrue("Method compareTo does not exist or does not take the expected parameter type.", false);
		}
	}


	@Test(timeout = 1000)
	public void testCompareToLessThanLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase1 = 5;
		int distanceFromBase2 = 10;

		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);


		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan1 = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase1,speed,resourcesValue,dangerLevel);
		Object pureTitan2 = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase2,speed,resourcesValue,dangerLevel);

		Class superClass = Class.forName(PureTitanClassPath).getSuperclass();

		Method m = superClass.getDeclaredMethod("compareTo", superClass);
		assertTrue(((int)m.invoke(pureTitan1, pureTitan2)) < 0);
	}

	

	@Test(timeout = 1000)
	public void testCompareToEqualLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase1 = 15;
		int distanceFromBase2 = 15;

		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);


		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan1 = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase1,speed,resourcesValue,dangerLevel);
		Object pureTitan2 = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase2,speed,resourcesValue,dangerLevel);

		Class superClass = Class.forName(PureTitanClassPath).getSuperclass();

		Method m = superClass.getDeclaredMethod("compareTo", superClass);
		assertTrue(((int)m.invoke(pureTitan1, pureTitan2)) == 0);
	}

	@Test(timeout = 1000)
	public void testWeaponClassExists() {
		try {
			Class.forName(weaponPath);
		} catch (ClassNotFoundException e) {
			fail("missing class Weapon");
		}
	}
	

	@Test(timeout = 1000)
	public void testAttributeBaseDamageExists() throws NoSuchFieldException, ClassNotFoundException {
		String attributeName = "baseDamage";
		Class weaponClass = Class.forName(weaponPath);
		Field attributeField = weaponClass.getDeclaredField(attributeName);
		assertTrue("Attribute " + attributeName + " should exist in class " + weaponClass.getSimpleName(),
				attributeField != null);
	}

	@Test(timeout = 1000)
	public void testAttributeBaseDamageIsFinal() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field baseDamageField = Class.forName(weaponPath).getDeclaredField("baseDamage");
		assertTrue("The baseDamage attribute should be final", Modifier.isFinal(baseDamageField.getModifiers()));
	}


	
	@Test(timeout = 1000)
	public void testPiercingCannonIsSubClassOfWeapon() throws Exception {
		testClassIsSubclass(Class.forName(piercingCannonPath), Class.forName(weaponPath));
	}

	@Test(timeout = 1000)
	public void testSniperCannonClassExists() {
		try {
			Class.forName(piercingCannonPath);
		} catch (ClassNotFoundException e) {
			fail("missing class SniperCannon");
		}
	}

	


	@Test(timeout = 1000)
	public void testVolleySpreadCannonIsSubClassOfWeapon() throws Exception {
		testClassIsSubclass(Class.forName(volleySpreadCannonPath), Class.forName(weaponPath));
	}

	@Test(timeout = 1000)
	public void testWallTrapClassExists() {
		try {
			Class.forName(wallTrapPath);
		} catch (ClassNotFoundException e) {
			fail("missing class WallTrapPath");
		}
	}


	@Test(timeout = 1000)
	public void testInstanceVariableWeaponCodeIsPresentInClassPiercingCannon() throws Exception {
		testInstanceVariableIsPresent(Class.forName(piercingCannonPath), "WEAPON_CODE", true);
	}
	@Test(timeout = 1000)
	public void testAttributeWeaponCodeIsStaticInClassPiercingCannon() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field WeaponCodeField = Class.forName(piercingCannonPath).getDeclaredField("WEAPON_CODE");
		assertTrue("The WEAPON_CODE attribute should be static", (Modifier.isStatic(WeaponCodeField.getModifiers())));
	}
	

	@Test(timeout = 1000)
	public void testInstanceVariableWeaponCodeIsPresentInClassSniperCannon() throws Exception {
		testInstanceVariableIsPresent(Class.forName(sniperCannonPath), "WEAPON_CODE", true);
	}
	@Test(timeout = 1000)
	public void testAttributeWeaponCodeIsStaticInClassSniperCannon() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field WeaponCodeField = Class.forName(sniperCannonPath).getDeclaredField("WEAPON_CODE");
		assertTrue("The WEAPON_CODE attribute should be static", (Modifier.isStatic(WeaponCodeField.getModifiers())));
	}
	@Test(timeout = 1000) 
	public void testAttributeWeaponCodeIsFinalInClassSniperCannon() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field WeaponCodeField = Class.forName(sniperCannonPath).getDeclaredField("WEAPON_CODE");
		assertTrue("The WEAPON_CODE attribute should be final", Modifier.isFinal(WeaponCodeField.getModifiers()));
	}


	@Test(timeout = 1000)
	public void testInstanceVariableWeaponCodeIsPresentInClassVolleySpreadCannon() throws Exception {
		testInstanceVariableIsPresent(Class.forName(volleySpreadCannonPath), "WEAPON_CODE", true);
	}
	@Test(timeout = 1000)
	public void testAttributeWeaponCodeIsStaticInClassVolleySpreadCannon() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field WeaponCodeField = Class.forName(volleySpreadCannonPath).getDeclaredField("WEAPON_CODE");
		assertTrue("The WEAPON_CODE attribute should be static", (Modifier.isStatic(WeaponCodeField.getModifiers())));
	}

	@Test(timeout = 1000)
	public void testAttributeWeaponCodeIsStaticInClassWallTrap() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field WeaponCodeField = Class.forName(wallTrapPath).getDeclaredField("WEAPON_CODE");
		assertTrue("The WEAPON_CODE attribute should be static", (Modifier.isStatic(WeaponCodeField.getModifiers())));
	}
	@Test(timeout = 1000) 
	public void testAttributeWeaponCodeIsFinalInClassWallTrap() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field WeaponCodeField = Class.forName(wallTrapPath).getDeclaredField("WEAPON_CODE");
		assertTrue("The WEAPON_CODE attribute should be final", Modifier.isFinal(WeaponCodeField.getModifiers()));
	}

	@Test(timeout = 1000) 
	public void testWeaponCodeValues() throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
		Field piercingCode = Class.forName(piercingCannonPath).getDeclaredField("WEAPON_CODE");
		assertEquals(1, piercingCode.getInt(null));

		Field sniperCode = Class.forName(sniperCannonPath).getDeclaredField("WEAPON_CODE");
		assertEquals(2, sniperCode.getInt(null));

		Field volleyCode = Class.forName(volleySpreadCannonPath).getDeclaredField("WEAPON_CODE");
		assertEquals(3, volleyCode.getInt(null));

		Field wallTrapCode = Class.forName(wallTrapPath).getDeclaredField("WEAPON_CODE");
		assertEquals(4, wallTrapCode.getInt(null));
	}

	@Test(timeout = 1000)
	public void testConstructorExistsInClassPiercingCannon() throws ClassNotFoundException {
		Class[] inputs = { int.class };
		testConstructorExists(Class.forName(piercingCannonPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorExistsInClassSniperCannon() throws ClassNotFoundException {
		Class[] inputs = { int.class };
		testConstructorExists(Class.forName(sniperCannonPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorExistsInClassVolleySpreadCannon() throws ClassNotFoundException {
		Class[] inputs = { int.class , int.class, int.class};
		testConstructorExists(Class.forName(volleySpreadCannonPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorExistsInClassWallTrapCannon() throws ClassNotFoundException {
		Class[] inputs = { int.class };
		testConstructorExists(Class.forName(wallTrapPath), inputs);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableMinRangeIsPresentInClassVolleySpreadCannon() throws Exception {
		testInstanceVariableIsPresent(Class.forName(volleySpreadCannonPath), "minRange", true);
	}

	


	@Test(timeout = 1000)
	public void testAttributeMaxRangeIsFinalInClassVolleySpreadCannon() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field maxRangeField = Class.forName(volleySpreadCannonPath).getDeclaredField("maxRange");
		assertTrue("The maxRange attribute should be final", Modifier.isFinal(maxRangeField.getModifiers()));
	}

	@Test(timeout = 1000)
	public void testReadOnlyForInstanceVariableMinRangeInClassVolleySpreadCannon() throws Exception {
		testGetterMethodExistsInClass(Class.forName(volleySpreadCannonPath), "getMinRange", int.class, true);
		testSetterMethodExistsInClass(Class.forName(volleySpreadCannonPath), "setMinRange", int.class , false);
	}

	
	@Test(timeout = 1000)
	public void testInstanceVariableMaxRangeIsPrivate() throws Exception {
		Field maxRangeField = Class.forName(volleySpreadCannonPath).getDeclaredField("maxRange");
		assertTrue("The maxRange attribute should be private", Modifier.isPrivate(maxRangeField.getModifiers()));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableMinRangeIsPrivate() throws Exception {
		Field minRangeField = Class.forName(volleySpreadCannonPath).getDeclaredField("minRange");
		assertTrue("The minRange attribute should be private", Modifier.isPrivate(minRangeField.getModifiers()));
	}

	@Test(timeout = 1000)
	public void testConstructorInitializationPiercingCannon() throws Exception {
		int baseDamage = (int) (Math.random() * 1000);

		Constructor<?> constructor = Class.forName(piercingCannonPath).getConstructor(int.class);
		Object piercingCannon =  constructor.newInstance(baseDamage);

		String[] attributes = { "baseDamage" };
		Object[] values = { baseDamage  };

		testConstructorInitialization(piercingCannon, attributes, values);
	}

	

	@Test(timeout = 1000)
	public void testConstructorInitializationVolleySpreadCannon() throws Exception {
		int baseDamage = (int) (Math.random() * 1000);
		int minRange = (int) (Math.random() * 1000);
		int maxRange = (int) (Math.random() * 1000);

		Constructor<?> constructor = Class.forName(volleySpreadCannonPath).getConstructor(int.class,int.class,int.class);
		Object volley =  constructor.newInstance(baseDamage,minRange, maxRange);

		String[] attributes = { "baseDamage" ,"minRange", "maxRange" };
		Object[] values = { baseDamage, minRange, maxRange };

		testConstructorInitialization(volley, attributes, values);
	}

	@Test(timeout = 1000)
	public void testConstructorInitializationWallTrap() throws Exception {
		int baseDamage = (int) (Math.random() * 1000);

		Constructor<?> constructor = Class.forName(wallTrapPath).getConstructor(int.class);
		Object wallTrap = constructor.newInstance(baseDamage);

		String[] attributes = { "baseDamage" };
		Object[] values = { baseDamage  };

		testConstructorInitialization(wallTrap, attributes, values);
	}

	@Test(timeout = 1000)
	public void testGetterLogicForInstanceVariableBaseDamageInClassPiercingCannon() throws Exception {
		int baseDamage = (int) (Math.random() * 1000);

		Constructor<?> constructor = Class.forName(piercingCannonPath).getConstructor(int.class);
		Object piercingCannon =  constructor.newInstance(baseDamage);

		testGetterLogic(piercingCannon, "baseDamage", baseDamage,"getDamage" );
	}

	@Test(timeout = 1000)
	public void testGetterLogicForInstanceVariableBaseDamageInClassSniperCannon() throws Exception {
		int baseDamage = (int) (Math.random() * 1000);

		Constructor<?> constructor = Class.forName(sniperCannonPath).getConstructor(int.class);
		Object sniperCannon = constructor.newInstance(baseDamage);

		testGetterLogic(sniperCannon, "baseDamage", baseDamage,"getDamage" );
	}

	

	@Test(timeout = 1000)
	public void testGetterLogicForInstanceVariableMaxRangeInClassVolleySpreadCannon() throws Exception {
		int baseDamage = (int) (Math.random() * 1000);
		int minRange = (int) (Math.random() * 1000);
		int maxRange = (int) (Math.random() * 1000);

		Constructor<?> constructor = Class.forName(volleySpreadCannonPath).getConstructor(int.class, int.class, int.class);
		Object volley = constructor.newInstance(baseDamage, minRange, maxRange);

		testGetterLogic(volley, "maxRange", minRange,"getMaxRange" );
	}

	







	@Test(timeout = 1000)
	public void testPhasesValueBattle() throws Exception {
		Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1;
		int random6 = (int) (Math.random() * 10) + 1;
		Object b = constructor.newInstance(random1, random2, random3,random4,random5);
		String name = "PHASES_APPROACHING_TITANS";
		int[][]h = {
				{ 1, 1, 1, 2, 1, 3, 4 },
				{ 2, 2, 2, 1, 3, 3, 4 },
				{ 4, 4, 4, 4, 4, 4, 4 } 
		};

		testPhasesValues(b, name, h);
	}

	

	

	@Test(timeout = 1000)
	public void testInstanceVariableWallBaseBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setWallBaseHealth",int.class , false);
	}



	@Test(timeout = 1000)
	public void testFactoryResponseInstanceVariableRemainingResourcesIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(factoryResponsePath), "remainingResources");
	}

	@Test(timeout = 1000)
	public void testFactoryResponseInstanceVariableRemainingResourcesIsFinal() throws Exception {
		testInstanceVariableIsFinal(Class.forName(factoryResponsePath), "remainingResources");

	}


	@Test(timeout = 1000)
	public void testFactoryResponseInstanceVariableWeaponIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(factoryResponsePath), "weapon", true);

	}

	@Test(timeout = 1000)
	public void testFactoryResponseInstanceVariableWeaponIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(factoryResponsePath), "weapon");
	}



	

	@Test(timeout = 1000)
	public void testConstructorInitializationFactoryResponse() throws Exception {
		Constructor<?> factoryResponseConstructor = Class.forName(factoryResponsePath).getConstructor(Class.forName(weaponPath),int.class);
		Constructor<?> weaponConstructor = Class.forName(weaponPiercingCannonPath).getConstructor(int.class);
		int randomRemainingResources = (int) (Math.random() * 10 )+1; 
		int baseDamage = (int) (Math.random() * 10 )+1; 
		Object weapon = weaponConstructor.newInstance(baseDamage);
		Object factoryResponse = factoryResponseConstructor.newInstance(weapon,randomRemainingResources);
		String[] names = { "weapon", "remainingResources" };
		Object[] values = { weapon, randomRemainingResources };
		testConstructorInitialization(factoryResponse, names, values);
	}


	@Test(timeout = 1000)
	public void testInstanceVariableFactoryResponseRemainingResourcesSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(factoryResponsePath), "setRemainingResources", int.class, false);

	}


	@Test(timeout = 1000)
	public void testInstanceVariableFactoryResponseWeaponSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(factoryResponsePath), "setWeapon", Class.forName(weaponPath),
				false);

	}


	@Test(timeout = 1000)
	public void testInstanceVariableFactoryResponseRemainingResourcesGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(factoryResponsePath), "getRemainingResources", int.class, true);
	}


	@Test(timeout = 1000)
	public void testInstanceVariableFactoryResponseWeaponGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(factoryResponsePath), "getWeapon", Class.forName(weaponPath), true);
	}


	@Test(timeout = 1000) public void testInstanceVariableFactoryResponseWeaponGetterLogic() throws Exception { 
		Constructor<?> factoryResponseConstructor = Class.forName(factoryResponsePath).getConstructor(Class.forName(weaponPath),int.class);
		Constructor<?> weaponConstructor = Class.forName(weaponPiercingCannonPath).getConstructor(int.class);
		int randomRemainingResources = (int) (Math.random() * 10 )+1; 
		int baseDamage = (int) (Math.random() * 10 )+1; 
		Object weapon = weaponConstructor.newInstance(baseDamage);
		Object c = factoryResponseConstructor.newInstance(weapon,randomRemainingResources);
		testGetterLogic(c, "weapon", weapon); }



	@Test(timeout = 1000)
	public void testWeaponFactoryInstanceVariableWeaponShopIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(weaponFactoryPath), "weaponShop");

	}

	@Test(timeout = 1000)
	public void testWeaponFactoryInstanceVariableWeaponShopIsFinal() throws Exception {
		testInstanceVariableIsFinal(Class.forName(weaponFactoryPath), "weaponShop");
	}

	@Test(timeout = 1000)
	public void testWeaponFactoryInstanceVariableWeaponShopType() throws Exception {
		testInstanceVariableOfType(Class.forName(weaponFactoryPath), "weaponShop", HashMap.class);
	}

	@Test(timeout = 1000)
	public void testWeaponFactoryInstanceVariableWeaponShopGetter() throws Exception {
		testGetterMethodExistsInClass(Class.forName(weaponFactoryPath), "getWeaponShop", HashMap.class, true);
	}


	@Test(timeout = 1000)
	public void testWeaponFactoryInstanceVariableWeaponShopSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(weaponFactoryPath), "setWeaponShop", HashMap.class,
				false);

	}


	
	@Test(timeout = 1000)
	public void testWeaponFactoryConstructorInitialization() throws Exception{
		Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
		Object weaponFactory = weaponFactoryConstructor.newInstance();
		Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class , int.class , int.class , String.class);
		Object weaponRegistry1 = weaponRegistryConstructor.newInstance(1,25,10,"Anti Titan Shell");
		Object weaponRegistry2 = weaponRegistryConstructor.newInstance(2,25,35,"Long Range Spear");
		Object weaponRegistry3 = weaponRegistryConstructor.newInstance(3,100,5,"Wall Spread Cannon");
		Object weaponRegistry4 = weaponRegistryConstructor.newInstance(4,75,100,"Proximity Trap");
		HashMap<Integer, Object> h = new HashMap();
		h.put(1, (Object) weaponRegistry1);
		h.put(2, (Object) weaponRegistry2);
		h.put(3, (Object) weaponRegistry3);
		h.put(4, (Object) weaponRegistry4);
		String[] names = {"weaponShop"};
		Object[] values = {h};
		testConstructorInitializationWeaponFactory(weaponFactory, names, values);
	}


	@Test(timeout = 1000)
	public void testConstructorInitializationBattleTitansArchives2()  {
		try {
			
		
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1; 
		Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);
		Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
		Object weaponFactory = weaponFactoryConstructor.newInstance();

		String[] names = { "titansArchives"};




		Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);

		ArrayList<Object> array=new ArrayList<>();
		array.add(Constructor.newInstance(1,100,15,15,10,10,1));
		array.add(Constructor.newInstance(2,100,20,10,15,15,2));
		array.add(Constructor.newInstance(3,200,85,15,10,30,3));
		array.add(Constructor.newInstance(4,1000,100,60,5,60,4));

		Class curr1 = Class.forName(battlePath);

		Field f = null;

		try {
			f = curr1.getDeclaredField("titansArchives");
			f.setAccessible(true);

			HashMap<Integer, Object> hashMap = (HashMap<Integer, Object>) f.get(battle);



			int key=1;
			for (Object object : array) {
				Object o = hashMap.get(key);
				assertTrue("incorrect speed in readTitanRegistry", checkTREqual(object, o, "speed"));
				assertTrue("incorrect resourcesValue in readTitanRegistry", checkTREqual(object, o, "resourcesValue"));
				assertTrue("incorrect heightInMeters in readTitanRegistry", checkTREqual(object, o, "heightInMeters"));
				assertTrue("incorrect baseDamage in readTitanRegistry", checkTREqual(object, o, "baseDamage"));
				assertTrue("incorrect code in readTitanRegistry", checkTREqual(object, o, "code"));
				key++;
			}

		} catch (NoSuchFieldException e) {
			curr1 = curr1.getSuperclass();

			fail("Attributes name error");

		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			fail("Exception occurred. Please check your console.");
		}
	}

	
	@Test(timeout = 1000)
	public void testPhasesInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "PHASES_APPROACHING_TITANS");

	}

	@Test(timeout = 1000)
	public void testPhasesInBattleIsFinal() throws Exception {
		testInstanceVariableIsFinal(Class.forName(battlePath), "PHASES_APPROACHING_TITANS");

	}

	@Test(timeout = 1000)
	public void testPhasesInBattleType() throws Exception {
		testInstanceVariableOfTypeDoubleArray(Class.forName(battlePath), " PHASES APPROACHING TITANS", int.class);

	}

	@Test(timeout = 1000)
	public void testPhasesInBattleisStatic() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field f = Class.forName(battlePath).getDeclaredField("PHASES_APPROACHING_TITANS");
		int modifiers = f.getModifiers();
		assertTrue(f.getName() + " variable in calss Game should be static", (Modifier.isStatic(modifiers)));
	}


	@Test(timeout = 1000)
	public void testHealthInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "WALL_BASE_HEALTH", true);

	}
	@Test(timeout = 1000)
	public void testHealthInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "WALL_BASE_HEALTH");

	}
	@Test(timeout = 1000)
	public void testWallResourcesValueGetterExistance() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(wallPath), "getResourcesValue", int.class);
	}

	@Test(timeout = 1000)
	public void testWallResourcesValueGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int baseHealth = (int) (Math.random() * 10) + 1;
		Constructor<?> constructor = Class.forName(wallPath).getConstructor(int.class);
		Object createdObject = constructor.newInstance(baseHealth);
		Method m = Class.forName(wallPath).getDeclaredMethod("getResourcesValue");
		int actualValue = (int) m.invoke(createdObject);
		assertEquals("The resources value of the wall must be -1" ,-1 ,actualValue);
	}


	@Test(timeout = 1000)
	public void testHealthInBattleIsFinal() throws Exception {
		testInstanceVariableIsFinal(Class.forName(battlePath), "WALL_BASE_HEALTH");

	}

	@Test(timeout = 1000)
	public void testHealthInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "WALL_BASE_HEALTH", int.class);

	}


	@Test(timeout = 1000)
	public void testTurnsInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "numberOfTurns", true);

	}
	@Test(timeout = 1000)
	public void testTurnsInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "numberOfTurns");

	}



	@Test(timeout = 1000)
	public void testResourcesInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "resourcesGathered", true);

	}
	@Test(timeout = 1000)
	public void testResourcesInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "resourcesGathered");

	}


	

	@Test(timeout = 1000)
	public void testBattlePhaseInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "battlePhase", Class.forName(battlePhasePath));

	}

	@Test(timeout = 1000)
	public void testTitansInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "numberOfTitansPerTurn", true);

	}
	@Test(timeout = 1000)
	public void testTitansInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "numberOfTitansPerTurn");

	}

	@Test(timeout = 1000)
	public void testTitansInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "numberOfTitansPerTurn", int.class);

	}

	@Test(timeout = 1000)
	public void testScoreInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "score", true);

	}
	@Test(timeout = 1000)
	public void testScoreInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "score");

	}

	@Test(timeout = 1000)
	public void testScoreInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "score", int.class);

	}

	@Test(timeout = 1000)
	public void testTitanSpawnDistanceInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "titanSpawnDistance", true);

	}
	@Test(timeout = 1000)
	public void testTitanSpawnDistanceInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "titanSpawnDistance");

	}



	@Test(timeout = 1000)
	public void testWeaponFactoryInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "weaponFactory", true);

	}
	@Test(timeout = 1000)
	public void testWeaponFactoryInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "weaponFactory");

	}
	@Test(timeout = 1000)
	public void testWeaponFactoryInBattleIsFinal() throws Exception {
		testInstanceVariableIsFinal(Class.forName(battlePath), "weaponFactory");

	}


	@Test(timeout = 1000)
	public void testTitansArchivesInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "titansArchives", true);

	}

	@Test(timeout = 1000)
	public void testTitansArchivesInBattleIsFinal() throws Exception {
		testInstanceVariableIsFinal(Class.forName(battlePath), "titansArchives");

	}


	@Test(timeout = 1000)
	public void testApproachingTitansInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "approachingTitans", true);

	}
	@Test(timeout = 1000)
	public void testApproachingTitansInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "approachingTitans");

	}
	@Test(timeout = 1000)
	public void testApproachingTitansInBattleIsFinal() throws Exception {
		testInstanceVariableIsFinal(Class.forName(battlePath), "approachingTitans");

	}



	@Test(timeout = 1000)
	public void testLanesInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "lanes", true);

	}
	
	@Test(timeout = 1000)
	public void testLanesInBattleIsFinal() throws Exception {
		testInstanceVariableIsFinal(Class.forName(battlePath), "lanes");

	}

	@Test(timeout = 1000)
	public void testLanesInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "lanes", PriorityQueue.class);

	}
	@Test(timeout = 1000)
	public void testOriginalLanesInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "originalLanes", true);

	}
	@Test(timeout = 1000)
	public void testOriginalLanesInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "originalLanes");

	}
	
	



	@Test(timeout = 1000)
	public void testInstanceVariableResourcesGatheredBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getResourcesGathered", int.class, true);
	}


	@Test(timeout = 1000)
	public void testInstanceVariableNumberOfTitansBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getNumberOfTitansPerTurn",int.class , true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableScoreBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getScore",int.class , true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableTitanSpawnDistanceBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getTitanSpawnDistance",int.class , true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableWeaponFactoryBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getWeaponFactory",Class.forName(weaponFactoryPath), true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableApproachingTitansBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getApproachingTitans",ArrayList.class, true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableLanesBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getLanes",PriorityQueue.class, true);
	}


	@Test(timeout = 1000)
	public void testInstanceVariableResourcesGatheredBattleGetterLogic() throws Exception {

		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);
		testGetterLogic(battle, "resourcesGathered", resources);

	}



	@Test(timeout = 1000)
	public void testInstanceVariableBattlePhaseBattleGetterLogic() throws Exception {

		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;

		Object bp = Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "EARLY");
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);
		testGetterLogic(battle, "battlePhase", bp);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableNumberOfTitansBattleGetterLogic() throws Exception {
		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);
		testGetterLogic(battle, "numberOfTitansPerTurn", 1);
	}

	
	

	@Test(timeout = 1000)
	public void testInstanceVariableWeaponFactoryBattleGetterLogic() throws Exception  {
		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);

		Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
		Object weaponFactory = weaponFactoryConstructor.newInstance();

		testGetterLogic(battle, "weaponFactory", weaponFactory);
	}


	@Test(timeout = 1000)
	public void testInstanceVariableTitansArchivesBattleGetterLogic() throws Exception { 
		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;
		int code = (int) (Math.random() * 10) + 1;
		int baseHealth = (int) (Math.random() * 10) + 1;
		int baseDamage = (int) (Math.random() * 10) + 1;
		int heightInMeters = (int) (Math.random() * 10) + 1;
		int speed = (int) (Math.random() * 10) + 1;
		int resourcesValue = (int) (Math.random() * 10) + 1;
		int dangerLevel = (int) (Math.random() * 10) + 1;
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);

		Constructor<?> titanRegistryConstructor = Class.forName(titanRegistry).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object titanRegistry = titanRegistryConstructor.newInstance(code,baseHealth,baseDamage,heightInMeters,speed,resourcesValue,dangerLevel);
		Object titanRegistry1 = titanRegistryConstructor.newInstance(1,100,15,15,10,10,1);
		Object titanRegistry2 = titanRegistryConstructor.newInstance(2,100,20,10,15,15,2);
		Object titanRegistry3 = titanRegistryConstructor.newInstance(3,200,85,15,10,30,3);
		Object titanRegistry4 = titanRegistryConstructor.newInstance(4,1000,100,60,5,60,4);

		HashMap<Integer, Object> h = new HashMap();
		h.put(1, (Object) titanRegistry1);
		h.put(2, (Object) titanRegistry2);
		h.put(3, (Object) titanRegistry3);
		h.put(4, (Object) titanRegistry4);
		testGetterLogic(battle, "titansArchives", h);
	}

	
	@Test(timeout = 1000)
	public void testInstanceVariableLanesBattleGetterLogic() throws Exception {
		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);
		PriorityQueue<Object> l = new PriorityQueue<>();
		testGetterLogic(battle, "lanes", l);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableOriginalLanesBattleGetterLogic() throws Exception {
		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);
		ArrayList<Object> l = new ArrayList();
		testGetterLogic(battle, "originalLanes", l);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableNumberofTurnsBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setNumberOfTurns", int.class, true);
	}

	

	@Test(timeout = 1000)
	public void testInstanceVariableBattlePhaseBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setBattlePhase",Class.forName(battlePhasePath) , true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableNumberOfTitansBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setNumberOfTitansPerTurn",int.class , true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableScoreBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setScore",int.class , true);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableTitanSpawnDistanceBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setTitanSpawnDistance",int.class , true);
	}


	@Test(timeout = 1000)
	public void testInstanceVariableTitansArchivesBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setTitansArchives",HashMap.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableApproachingTitansBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setApproachingTitans",ArrayList.class, false);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableLanesBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setLanes",PriorityQueue.class, false);
	}



	

	@Test(timeout = 1000)
	public void testBattleNumberOfTitansSetterLogic() throws Exception {
		Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1;
		int random = (int) (Math.random() * 10) + 1;
		Object b = constructor.newInstance(random1, random2, random3,random4,random5);
		testSetterLogic(b, "numberOfTitansPerTurn", random, random, int.class);
	}


	@Test(timeout = 1000)
	public void testTitanSpawnDistanceSetterLogic() throws Exception {
		Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1;
		int random6 = (int) (Math.random() * 10) + 1;
		Object b = constructor.newInstance(random1, random2, random3,random4,random5);
		testSetterLogic(b, "titanSpawnDistance", random6, random6, int.class);
	}

	@Test(timeout = 1000)
	public void testBattlePhaseSetterLogic() throws Exception {
		Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1;
		int random6 = (int) (Math.random() * 10) + 1;
		Object bp = Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "INTENSE");
		//				BattlePhase bp = BattlePhase.INTENSE;
		Object b = constructor.newInstance(random1, random2, random3,random4,random5);
		testSetterLogic(b, "battlePhase", bp, bp, Class.forName(battlePhasePath));
	}

	


	@Test(timeout = 1000)
	public void testConstructorInitializationBattleWeaponFactory() throws Exception {
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1; 
		Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);
		Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
		Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class , int.class , int.class , String.class);
		Object weaponFactory = weaponFactoryConstructor.newInstance();
		Object weaponRegistry1 = weaponRegistryConstructor.newInstance(1,25,10,"Anti Titan Shell");
		Object weaponRegistry2 = weaponRegistryConstructor.newInstance(2,25,35,"Long Range Spear");
		Object weaponRegistry3 = weaponRegistryConstructor.newInstance(3,100,5,"Wall Spread Cannon");
		Object weaponRegistry4 = weaponRegistryConstructor.newInstance(4,75,100,"Proximity Trap");
		HashMap<Integer, Object> h = new HashMap();
		h.put(1, (Object) weaponRegistry1);
		h.put(2, (Object) weaponRegistry2);
		h.put(3, (Object) weaponRegistry3);
		h.put(4, (Object) weaponRegistry4);
		String[] names = {"weaponShop"};
		Object[] values = {h};
		ArrayList<Object> l = new ArrayList<>();
		ArrayList<Object> Olanes = new ArrayList<>();
		PriorityQueue<Object> lanes = new PriorityQueue();

		testConstructorInitializationWeaponFactory(weaponFactory, names, values);
	}


	@Test(timeout = 1000)
	public void testConstructorInitializationBattleTitansArchives() throws Exception {
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1; 
		Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);
		Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
		Object weaponFactory = weaponFactoryConstructor.newInstance();
		HashMap<Integer, Object> h = new HashMap();
		Constructor<?> titanRegistryConstructor = Class.forName(titanRegistry).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object titanRegistry1 = titanRegistryConstructor.newInstance(1,100,15,15,10,10,1);
		Object titanRegistry2 = titanRegistryConstructor.newInstance(2,100,20,10,15,15,2);
		Object titanRegistry3 = titanRegistryConstructor.newInstance(3,200,85,15,10,30,3);
		Object titanRegistry4 = titanRegistryConstructor.newInstance(4,1000,100,60,5,60,4);
		HashMap<Integer, Object> h2 = new HashMap();
		h2.put(1, (Object) titanRegistry1);
		h2.put(2, (Object) titanRegistry2);
		h2.put(3, (Object) titanRegistry3);
		h2.put(4, (Object) titanRegistry4);
		ArrayList<Object> l = new ArrayList<>();
		ArrayList<Object> Olanes = new ArrayList<>();
		PriorityQueue<Object> lanes = new PriorityQueue();
		String[] names = { "titansArchives"};
		Object[] values = {h2};
		testConstructorInitializationTitans(battle, names, values);
	}


	@Test(timeout = 1000)
	public void testConstructorInitializationBattleApprochingTitans() throws Exception {
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1; 
		Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);
		Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
		Object weaponFactory = weaponFactoryConstructor.newInstance();
		HashMap<Integer, Object> h = new HashMap();
		Constructor<?> titanRegistryConstructor = Class.forName(titanRegistry).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object titanRegistry1 = titanRegistryConstructor.newInstance(1,100,15,15,10,10,1);
		Object titanRegistry2 = titanRegistryConstructor.newInstance(2,100,20,10,15,15,2);
		Object titanRegistry3 = titanRegistryConstructor.newInstance(3,200,85,15,10,30,3);
		Object titanRegistry4 = titanRegistryConstructor.newInstance(4,1000,100,60,5,60,4);
		HashMap<Integer, Object> h2 = new HashMap();
		h2.put(1, (Object) titanRegistry1);
		h2.put(2, (Object) titanRegistry2);
		h2.put(3, (Object) titanRegistry3);
		h2.put(4, (Object) titanRegistry4);
		ArrayList<Object> l = new ArrayList<>();
		ArrayList<Object> Olanes = new ArrayList<>();
		PriorityQueue<Object> lanes = new PriorityQueue();
		String[] names = { "approachingTitans"};
		Object[] values = {l};
		testConstructorInitializationApprochingTitans(battle, names, values);
	}


	
	@Test(timeout = 1000)
	public void testInitializeLanesBattleClass() throws ClassNotFoundException, NoSuchMethodException,
	IllegalAccessException, InstantiationException, InvocationTargetException {

		int random1 = (int) (Math.random() * 100) + 11;
		int random2 = (int) (Math.random() * 30) + 1;
		int random3 = (int) (Math.random() * 100) + 11;
		int random4 = (int) (Math.random() * 30) + 1;
		int random5 = (int) (Math.random() * 100) + 11;

		Class<?> battleClass = Class.forName(battlePath);
		Constructor<?> battleConstructor = battleClass.getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

		Method initializeLanes = battleClass.getDeclaredMethod("initializeLanes",int.class);
		initializeLanes.setAccessible(true);
		initializeLanes.invoke(battle, random4);

		Method getOriginalLanes = battleClass.getMethod("getOriginalLanes");
		Method getLanes = battleClass.getMethod("getLanes");
		PriorityQueue<Object> lanes = (PriorityQueue) getLanes.invoke(battle);
		ArrayList<Object> originalLanes = (ArrayList) getOriginalLanes.invoke(battle);
		int expectedSize = random4*2;
		int actualLanesSize = lanes.size();
		int actualOriginalLanesSize = originalLanes.size();
		assertEquals(expectedSize, actualLanesSize);
		assertEquals(expectedSize, actualOriginalLanesSize);
	}








	@Test(timeout = 1000)
	public void testConstructorTitanRegistry() throws ClassNotFoundException {
		Class[] inputs = { int.class, int.class,int.class, int.class,int.class, int.class, int.class};
		testConstructorExists(Class.forName(titanRegistryPath), inputs);
	}

	



	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableCodePresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(titanRegistryPath), "code", true);
	}

	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableCodePrivate() throws Exception {
		Field f = Class.forName(titanRegistryPath).getDeclaredField("code");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(titanRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}


	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableCodeType() throws Exception {
		testInstanceVariableOfType(Class.forName(titanRegistryPath), "code", int.class);
	}



	@Test(timeout = 1000)
	public void testInstanceVariableCodeTRGetterLogic() throws Exception {
		Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomBaseHealth = (int) (Math.random() * 20) + 1;
		int randomBaseDmg = (int) (Math.random() * 20) + 1;
		int randomHIM = (int) (Math.random() * 5) + 1;
		int randomSpeed = (int) (Math.random() * 20) + 1;
		int randomRV = (int) (Math.random() * 20) + 1;
		int randomDL = (int) (Math.random() * 20) + 1;

		Object c = Constructor.newInstance(randomCode,randomBaseHealth,randomBaseDmg,randomHIM,randomSpeed,randomRV,randomDL);
		testGetterLogic(c, "code", randomCode);
	}



	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableBaseHealthPrivate() throws Exception {
		//		testInstanceVariableIsPrivate(Class.forName(titanRegistryPath), "code");
		Field f = Class.forName(titanRegistryPath).getDeclaredField("baseHealth");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(titanRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}


	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableBaseHealthType() throws Exception {
		testInstanceVariableOfType(Class.forName(titanRegistryPath), "baseHealth", int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableBaseHealthTitanRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanRegistryPath), "getBaseHealth", int.class, true);
	}


	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableBaseDmgPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(titanRegistryPath), "baseDamage", true);
	}

	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableBaseDmgPrivate() throws Exception {

		Field f = Class.forName(titanRegistryPath).getDeclaredField("baseDamage");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(titanRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}


	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableBaseDmgType() throws Exception {
		testInstanceVariableOfType(Class.forName(titanRegistryPath), "baseDamage", int.class);
	}

	

	@Test(timeout = 1000)
	public void testTitanRegistrySetterBaseDmgDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { titanRegistryPath };
		testSetterAbsentInSubclasses("baseDamage", subClasses);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableBaseDMGTRGetterLogic() throws Exception {
		Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomBaseHealth = (int) (Math.random() * 20) + 1;
		int randomBaseDmg = (int) (Math.random() * 20) + 1;
		int randomHIM = (int) (Math.random() * 5) + 1;
		int randomSpeed = (int) (Math.random() * 20) + 1;
		int randomRV = (int) (Math.random() * 20) + 1;
		int randomDL = (int) (Math.random() * 20) + 1;

		Object c = Constructor.newInstance(randomCode,randomBaseHealth,randomBaseDmg,randomHIM,randomSpeed,randomRV,randomDL);
		testGetterLogic(c, "baseDamage", randomBaseDmg);
	}

	
	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableHIMPrivate() throws Exception {

		Field f = Class.forName(titanRegistryPath).getDeclaredField("heightInMeters");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(titanRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}

	@Test(timeout = 1000)
	public void testInstanceVariableHIMTitanRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanRegistryPath), "getHeightInMeters", int.class, true);
	}

	@Test(timeout = 1000)
	public void testTitanRegistrySetterHIMDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { titanRegistryPath };
		testSetterAbsentInSubclasses("heightInMeters", subClasses);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableHIMGetterLogic() throws Exception {
		Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomBaseHealth = (int) (Math.random() * 20) + 1;
		int randomBaseDmg = (int) (Math.random() * 20) + 1;
		int randomHIM = (int) (Math.random() * 5) + 1;
		int randomSpeed = (int) (Math.random() * 20) + 1;
		int randomRV = (int) (Math.random() * 20) + 1;
		int randomDL = (int) (Math.random() * 20) + 1;

		Object c = Constructor.newInstance(randomCode,randomBaseHealth,randomBaseDmg,randomHIM,randomSpeed,randomRV,randomDL);
		testGetterLogic(c, "heightInMeters", randomHIM);
	}

	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableSpeedType() throws Exception {
		testInstanceVariableOfType(Class.forName(titanRegistryPath), "speed", int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSpeedTitanRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanRegistryPath), "getSpeed", int.class, true);
	}

	@Test(timeout = 1000)
	public void testTitanRegistrySetterSpeedDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { titanRegistryPath };
		testSetterAbsentInSubclasses("speed", subClasses);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableSpeedGetterLogic() throws Exception {
		Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomBaseHealth = (int) (Math.random() * 20) + 1;
		int randomBaseDmg = (int) (Math.random() * 20) + 1;
		int randomHIM = (int) (Math.random() * 5) + 1;
		int randomSpeed = (int) (Math.random() * 20) + 1;
		int randomRV = (int) (Math.random() * 20) + 1;
		int randomDL = (int) (Math.random() * 20) + 1;

		Object c = Constructor.newInstance(randomCode,randomBaseHealth,randomBaseDmg,randomHIM,randomSpeed,randomRV,randomDL);
		testGetterLogic(c, "speed", randomSpeed);
	}	

	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableResourcesValuePresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(titanRegistryPath), "resourcesValue", true);
	}

	

	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableResourcesValueType() throws Exception {
		testInstanceVariableOfType(Class.forName(titanRegistryPath), "resourcesValue", int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableResourcesValueTitanRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanRegistryPath), "getResourcesValue", int.class, true);
	}

	

	@Test(timeout = 1000)
	public void testInstanceVariableResourcesValueGetterLogic() throws Exception {
		Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomBaseHealth = (int) (Math.random() * 20) + 1;
		int randomBaseDmg = (int) (Math.random() * 20) + 1;
		int randomHIM = (int) (Math.random() * 5) + 1;
		int randomSpeed = (int) (Math.random() * 20) + 1;
		int randomRV = (int) (Math.random() * 20) + 1;
		int randomDL = (int) (Math.random() * 20) + 1;

		Object c = Constructor.newInstance(randomCode,randomBaseHealth,randomBaseDmg,randomHIM,randomSpeed,randomRV,randomDL);
		testGetterLogic(c, "resourcesValue", randomRV);
	}	

	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableDLPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(titanRegistryPath), "dangerLevel", true);
	}

	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableDLPrivate() throws Exception {

		Field f = Class.forName(titanRegistryPath).getDeclaredField("dangerLevel");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(titanRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));

	}


	@Test(timeout = 1000)
	public void testTitanRegistryInstanceVariableDLType() throws Exception {
		testInstanceVariableOfType(Class.forName(titanRegistryPath), "dangerLevel", int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDLTitanRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanRegistryPath), "getDangerLevel", int.class, true);
	}




	@Test(timeout = 1000)
	public void testConstructorWeaponRegistry() throws ClassNotFoundException {
		Class[] inputs = { int.class, int.class};
		testConstructorExists(Class.forName(weaponRegistryPath), inputs);
	}


	@Test(timeout = 1000)
	public void testConstructorWeaponRegistry2() throws ClassNotFoundException {
		Class[] inputs = { int.class, int.class,int.class, String.class};
		testConstructorExists(Class.forName(weaponRegistryPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorWeaponRegistry3() throws ClassNotFoundException {
		Class[] inputs = { int.class, int.class,int.class, String.class,int.class, int.class};
		testConstructorExists(Class.forName(weaponRegistryPath), inputs);
	}

	@Test(timeout = 1000)
	public void testConstructorInitializationClassWeaponRegistry() throws Exception {

		Constructor<?> constructor = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomPrice = (int) (Math.random() * 20) + 1;

		Object wR = constructor.newInstance(randomCode,randomPrice);
		String[] names = {"code", "price"};
		Object[] values = { randomCode,randomPrice};

		testConstructorInitialization(wR, names, values);
	}
	@Test(timeout = 1000)
	public void testConstructorInitializationClassWeaponRegistry2() throws Exception {

		Constructor<?> constructor = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomPrice = (int) (Math.random() * 20) + 1;
		int randomDmg = (int) (Math.random() * 20) + 1;
		int randomNameId = (int) (Math.random() * 5) + 1;



		Object wR = constructor.newInstance(randomCode,randomPrice,randomDmg,"Name_"+randomNameId);
		String[] names = {"code", "price","damage","name"};
		Object[] values = { randomCode,randomPrice,randomDmg,"Name_"+randomNameId};

		testConstructorInitialization(wR, names, values);
	}


	




	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableCodePresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(weaponRegistryPath), "code", true);
	}

	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableCodePrivate() throws Exception {
		Field f = Class.forName(weaponRegistryPath).getDeclaredField("code");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(weaponRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}


	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableCodeType() throws Exception {
		testInstanceVariableOfType(Class.forName(weaponRegistryPath), "code", int.class);
	}

	

	@Test(timeout = 1000)
	public void testWeaponRegistrySetterCodeDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { weaponRegistryPath };
		testSetterAbsentInSubclasses("code", subClasses);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableCodeWRGetterLogic() throws Exception {
		Constructor<?> constructor = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomPrice = (int) (Math.random() * 20) + 1;
		int randomDmg = (int) (Math.random() * 20) + 1;
		int randomNameId = (int) (Math.random() * 5) + 1;
		int randomMax = (int) (Math.random() * 30) + 1;
		int randomMin = (int) (Math.random() * 5) + 1;


		Object wR = constructor.newInstance(randomCode,randomPrice,randomDmg,"Name_"+randomNameId,randomMax,randomMin);
		testGetterLogic(wR, "code", randomCode);
	}
	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariablePricePresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(weaponRegistryPath), "price", true);
	}




	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariablePriceType() throws Exception {
		testInstanceVariableOfType(Class.forName(weaponRegistryPath), "price", int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariablePriceWeaponRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(weaponRegistryPath), "getPrice", int.class, true);
	}

	


	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableDamagePresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(weaponRegistryPath), "damage", true);
	}

	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableDamagePrivate() throws Exception {
		Field f = Class.forName(weaponRegistryPath).getDeclaredField("damage");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(weaponRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}


	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableDamageType() throws Exception {
		testInstanceVariableOfType(Class.forName(weaponRegistryPath), "damage", int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableDamageWeaponRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(weaponRegistryPath), "getDamage", int.class, true);
	}

	@Test(timeout = 1000)
	public void testWeaponRegistrySetterDamageDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { weaponRegistryPath };
		testSetterAbsentInSubclasses("damage", subClasses);
	}

	
	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableNamePrivate() throws Exception {
		Field f = Class.forName(weaponRegistryPath).getDeclaredField("name");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(weaponRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}


	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableNameType() throws Exception {
		testInstanceVariableOfType(Class.forName(weaponRegistryPath), "name", String.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableNameWeaponRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(weaponRegistryPath), "getName", String.class, true);
	}

	@Test(timeout = 1000)
	public void testWeaponRegistrySetterNameDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { weaponRegistryPath };
		testSetterAbsentInSubclasses("name", subClasses);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableNameWRGetterLogic() throws Exception {
		//		
		Constructor<?> constructor = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomPrice = (int) (Math.random() * 20) + 1;
		int randomDmg = (int) (Math.random() * 20) + 1;
		int randomNameId = (int) (Math.random() * 5) + 1;
		int randomMax = (int) (Math.random() * 30) + 1;
		int randomMin = (int) (Math.random() * 5) + 1;


		Object wR = constructor.newInstance(randomCode,randomPrice,randomDmg,"Name_"+randomNameId,randomMax,randomMin);
		testGetterLogic(wR, "name", "Name_"+randomNameId);
	}

	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableminRangePresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(weaponRegistryPath), "minRange", true);
	}

	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableminRangePrivate() throws Exception {
		Field f = Class.forName(weaponRegistryPath).getDeclaredField("minRange");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(weaponRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}


	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableminRangeType() throws Exception {
		testInstanceVariableOfType(Class.forName(weaponRegistryPath), "minRange", int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableminRangeWeaponRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(weaponRegistryPath), "getMinRange", int.class, true);
	}

	@Test(timeout = 1000)
	public void testWeaponRegistrySetterminRangeDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { weaponRegistryPath };
		testSetterAbsentInSubclasses("minRange", subClasses);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableminRangeWRGetterLogic() throws Exception {
		//		
		Constructor<?> constructor = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomPrice = (int) (Math.random() * 20) + 1;
		int randomDmg = (int) (Math.random() * 20) + 1;
		int randomNameId = (int) (Math.random() * 5) + 1;
		int randomMax = (int) (Math.random() * 30) + 1;
		int randomMin = (int) (Math.random() * 5) + 1;


		Object wR = constructor.newInstance(randomCode,randomPrice,randomDmg,"Name_"+randomNameId,randomMin,randomMax);
		testGetterLogic(wR, "minRange", randomMin);
	}
	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableMaxRangePresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(weaponRegistryPath), "maxRange", true);
	}

	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableMaxRangePrivate() throws Exception {
		Field f = Class.forName(weaponRegistryPath).getDeclaredField("maxRange");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(weaponRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}


	@Test(timeout = 1000)
	public void testWeaponRegistryInstanceVariableMaxRangeType() throws Exception {
		testInstanceVariableOfType(Class.forName(weaponRegistryPath), "maxRange", int.class);
	}

	@Test(timeout = 1000)
	public void testInstanceVariableMaxRangeWeaponRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(weaponRegistryPath), "getMaxRange", int.class, true);
	}

	

	@Test(timeout = 1000)
	public void testInstanceVariableMaxRangeWRGetterLogic() throws Exception {
		//		
		Constructor<?> constructor = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomPrice = (int) (Math.random() * 20) + 1;
		int randomDmg = (int) (Math.random() * 20) + 1;
		int randomNameId = (int) (Math.random() * 5) + 1;
		int randomMax = (int) (Math.random() * 30) + 1;
		int randomMin = (int) (Math.random() * 5) + 1;


		Object wR = constructor.newInstance(randomCode,randomPrice,randomDmg,"Name_"+randomNameId,randomMin,randomMax);
		testGetterLogic(wR, "maxRange", randomMax);
	}


	@Test(timeout = 1000)
	public void testDataLoaderTITANS_FILE_NAMEPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(dataLoaderPath), "TITANS_FILE_NAME", true);
	}

	@Test(timeout = 1000)
	public void testDataLoaderTITANS_FILE_NAMEPrivate() throws Exception {
		Field f = Class.forName(dataLoaderPath).getDeclaredField("TITANS_FILE_NAME");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(dataLoaderPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}
	
	@Test(timeout = 1000)
	public void testDataLoaderTITANS_FILE_NAMEStatic() throws Exception {
		Field f = Class.forName(dataLoaderPath).getDeclaredField("TITANS_FILE_NAME");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(dataLoaderPath).getName()+" should be static",(Modifier.isStatic(modifiers)));	
	}

	@Test(timeout = 1000)
	public void testDataLoaderTITANS_FILE_NAMEType() throws Exception {
		testInstanceVariableOfType(Class.forName(dataLoaderPath), "TITANS_FILE_NAME", String.class);
	}


	@Test(timeout = 1000)
	public void testDataLoaderTITANS_FILE_NAMESetterDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { dataLoaderPath };
		testSetterAbsentInSubclasses("TITANS_FILE_NAME", subClasses);
	}


	@Test(timeout = 1000)
	public void testDataLoaderWEAPONS_FILE_NAMEPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(dataLoaderPath), "WEAPONS_FILE_NAME", true);
	}

	@Test(timeout = 1000)
	public void testDataLoaderWEAPONS_FILE_NAMEPrivate() throws Exception {
		Field f = Class.forName(dataLoaderPath).getDeclaredField("WEAPONS_FILE_NAME");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(dataLoaderPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}
	@Test(timeout = 1000)
	public void testDataLoaderWEAPONS_FILE_NAMEFinal() throws Exception {
		Field f = Class.forName(dataLoaderPath).getDeclaredField("WEAPONS_FILE_NAME");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(dataLoaderPath).getName()+" should be final",(Modifier.isFinal(modifiers)));	
	}

	@Test(timeout = 1000)
	public void testDataLoaderWEAPONS_FILE_NAMEStatic() throws Exception {
		Field f = Class.forName(dataLoaderPath).getDeclaredField("WEAPONS_FILE_NAME");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(dataLoaderPath).getName()+" should be static",(Modifier.isStatic(modifiers)));	
	}

	@Test(timeout = 1000)
	public void testDataLoaderWEAPONS_FILE_NAMEType() throws Exception {
		testInstanceVariableOfType(Class.forName(dataLoaderPath), "WEAPONS_FILE_NAME", String.class);
	}


	@Test(timeout = 1000)
	public void testDataLoaderWEAPONS_FILE_NAMESetterDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { dataLoaderPath };
		testSetterAbsentInSubclasses("WEAPONS_FILE_NAME", subClasses);
	}

	

	@Test(timeout = 1000)
	public void testCorrectWeaponFileName() {
		try {

			Field fd = Class.forName(dataLoaderPath).getDeclaredField("WEAPONS_FILE_NAME");
			fd.setAccessible(true);
			assertEquals("weapons.csv", ((String)fd.get(null)));
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();

		}
	}





	

	@Test(timeout = 1000)
	public void testReadWeaponRegistryisStatic() {

		Method m;
		try {
			m = Class.forName(dataLoaderPath).getMethod("readWeaponRegistry",null);
			assertTrue("readWeaponRegistry expected to be static method",Modifier.isStatic(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}

	@Test(timeout = 1000)
	public void testReadWeaponRegistryisPublic() {

		Method m;
		try {
			m = Class.forName(dataLoaderPath).getMethod("readWeaponRegistry",null);
			assertTrue("readWeaponRegistry expected to be public",Modifier.isPublic(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			fail();
		}

	}





	@Test(timeout = 1000)
	public void testLoadCodeTitansCorrectly() {
		try {
			writeTitanCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readTitanRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
			ArrayList<Object> array=new ArrayList<>();
			array.add(Constructor.newInstance(5,300,25,25,30,20,8));
			array.add(Constructor.newInstance(6,200,30,20,45,25,7));
			array.add(Constructor.newInstance(7,400,55,25,40,20,6));
			array.add(Constructor.newInstance(8,5000,90,70,10,50,5));
			array.add(Constructor.newInstance(9,2000,90,70,10,50,5));

			int key=5;
			for (Object object : array) {
				Object o = hashMap.get(key);
				assertTrue("incorrect code in readTitanRegistry", checkTREqual(object, o, "code"));
				key++;
			}


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalTitanCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please make sure to close the csv file for the test file to write on it");
		}
	}

	

	@Test(timeout = 1000)
	public void testLoadbaseDamageTitansCorrectly() {
		try {
			writeTitanCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readTitanRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
			ArrayList<Object> array=new ArrayList<>();
			array.add(Constructor.newInstance(5,300,25,25,30,20,8));
			array.add(Constructor.newInstance(6,200,30,20,45,25,7));
			array.add(Constructor.newInstance(7,400,55,25,40,20,6));
			array.add(Constructor.newInstance(8,5000,90,70,10,50,5));
			array.add(Constructor.newInstance(9,2000,90,70,10,50,5));

			int key=5;
			for (Object object : array) {
				Object o = hashMap.get(key);
				assertTrue("incorrect baseDamage in readTitanRegistry", checkTREqual(object, o, "baseDamage"));
				key++;
			}


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalTitanCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please make sure to close the csv file for the test file to write on it");
		}
	}
	@Test(timeout = 1000)
	public void testLoadheightInMetersTitansCorrectly() {
		try {
			writeTitanCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readTitanRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
			ArrayList<Object> array=new ArrayList<>();
			array.add(Constructor.newInstance(5,300,25,25,30,20,8));
			array.add(Constructor.newInstance(6,200,30,20,45,25,7));
			array.add(Constructor.newInstance(7,400,55,25,40,20,6));
			array.add(Constructor.newInstance(8,5000,90,70,10,50,5));
			array.add(Constructor.newInstance(9,2000,90,70,10,50,5));

			int key=5;
			for (Object object : array) {
				Object o = hashMap.get(key);
				assertTrue("incorrect heightInMeters in readTitanRegistry", checkTREqual(object, o, "heightInMeters"));
				key++;
			}


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalTitanCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please make sure to close the csv file for the test file to write on it");
		}
	}


	@Test(timeout = 1000)
	public void testLoadspeedTitansCorrectly() {
		try {
			writeTitanCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readTitanRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
			ArrayList<Object> array=new ArrayList<>();
			array.add(Constructor.newInstance(5,300,25,25,30,20,8));
			array.add(Constructor.newInstance(6,200,30,20,45,25,7));
			array.add(Constructor.newInstance(7,400,55,25,40,20,6));
			array.add(Constructor.newInstance(8,5000,90,70,10,50,5));
			array.add(Constructor.newInstance(9,2000,90,70,10,50,5));

			int key=5;
			for (Object object : array) {
				Object o = hashMap.get(key);
				assertTrue("incorrect speed in readTitanRegistry", checkTREqual(object, o, "speed"));
				key++;
			}


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalTitanCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please make sure to close the csv file for the test file to write on it");
		}

	}



	@Test(timeout = 1000)
	public void testLoadresourcesValueTitansCorrectly() {
		try {
			writeTitanCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readTitanRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
			ArrayList<Object> array=new ArrayList<>();
			array.add(Constructor.newInstance(5,300,25,25,30,20,8));
			array.add(Constructor.newInstance(6,200,30,20,45,25,7));
			array.add(Constructor.newInstance(7,400,55,25,40,20,6));
			array.add(Constructor.newInstance(8,5000,90,70,10,50,5));
			array.add(Constructor.newInstance(9,2000,90,70,10,50,5));

			int key=5;
			for (Object object : array) {
				Object o = hashMap.get(key);
				assertTrue("incorrect resourcesValue in readTitanRegistry", checkTREqual(object, o, "resourcesValue"));
				key++;
			}


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalTitanCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please make sure to close the csv file for the test file to write on it");
		}
	}

	

	@Test(timeout = 1000)
	public void testLoadCodeWeaponRegistryCorrectly() {
		try {
			writeWeaponCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readWeaponRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> constructor1 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class);
			Constructor<?> constructor2 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);

			ArrayList<Object> array=new ArrayList<>();
			array.add(constructor1.newInstance(5,55,20,"Anti Titan Shell"));
			array.add(constructor1.newInstance(6,45,35,"Strombreaker"));
			array.add(constructor1.newInstance(7,35,90,"The Destroyer"));
			array.add(constructor2.newInstance(8,45,35,"Mjolnir",10,60));
			array.add(constructor2.newInstance(9,55,35,"verybigweapon",20,70));

			int key=5;
			for (Object object : array) {
				Object o = hashMap.get(key);
				assertTrue("incorrect code in readWeaponRegistry", checkWREqual(object, o, "code"));
				key++;
			}


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalWeaponCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please make sure to close the csv file for the test file to write on it");
		}
	}

	@Test(timeout = 1000)
	public void testLoadPriceWeaponRegistryCorrectly() {
		try {
			writeWeaponCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readWeaponRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> constructor1 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class);
			Constructor<?> constructor2 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);

			ArrayList<Object> array=new ArrayList<>();
			array.add(constructor1.newInstance(5,55,20,"Anti Titan Shell"));
			array.add(constructor1.newInstance(6,45,35,"Strombreaker"));
			array.add(constructor1.newInstance(7,35,90,"The Destroyer"));
			array.add(constructor2.newInstance(8,45,35,"Mjolnir",10,60));
			array.add(constructor2.newInstance(9,55,35,"verybigweapon",20,70));

			int key=5;
			for (Object object : array) {
				Object o = hashMap.get(key);
				assertTrue("incorrect price in readWeaponRegistry", checkWREqual(object, o, "price"));
				key++;
			}


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalWeaponCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please make sure to close the csv file for the test file to write on it");
		}
	}

	@Test(timeout = 1000)
	public void testLoadDamageWeaponRegistryCorrectly() {
		try {
			writeWeaponCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readWeaponRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> constructor1 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class);
			Constructor<?> constructor2 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);

			ArrayList<Object> array=new ArrayList<>();
			array.add(constructor1.newInstance(5,55,20,"Anti Titan Shell"));
			array.add(constructor1.newInstance(6,45,35,"Strombreaker"));
			array.add(constructor1.newInstance(7,35,90,"The Destroyer"));
			array.add(constructor2.newInstance(8,45,35,"Mjolnir",10,60));
			array.add(constructor2.newInstance(9,55,35,"verybigweapon",20,70));

			int key=5;
			for (Object object : array) {
				Object o = hashMap.get(key);
				assertTrue("incorrect damage in readWeaponRegistry", checkWREqual(object, o, "damage"));
				key++;
			}


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalWeaponCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please make sure to close the csv file for the test file to write on it");
		}
	}

	@Test(timeout = 1000)
	public void testLoadNameWeaponRegistryCorrectly() {
		try {
			writeWeaponCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readWeaponRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> constructor1 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class);
			Constructor<?> constructor2 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);

			ArrayList<Object> array=new ArrayList<>();
			array.add(constructor1.newInstance(5,55,20,"Anti Titan Shell"));
			array.add(constructor1.newInstance(6,45,35,"Strombreaker"));
			array.add(constructor1.newInstance(7,35,90,"The Destroyer"));
			array.add(constructor2.newInstance(8,45,35,"Mjolnir",10,60));
			array.add(constructor2.newInstance(9,55,35,"verybigweapon",20,70));

			int key=5;
			for (Object object : array) {
				Object o = hashMap.get(key);
				Field f = Class.forName(weaponRegistryPath).getDeclaredField("name");
				f.setAccessible(true);

				String n1 = (String) f.get(object);
				String n2 = (String) f.get(o);
				assertTrue("incorrect name in readWeaponRegistry", n1.equals(n2));
				key++;
			}


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException | NoSuchFieldException e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalWeaponCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please make sure to close the csv file for the test file to write on it");
		}
	}


	@Test(timeout = 1000)
	public void testLoadMinWeaponRegistryCorrectly() {
		try {
			writeWeaponCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readWeaponRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> constructor1 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class);
			Constructor<?> constructor2 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);


			Object o = hashMap.get(8);
			assertTrue("incorrect damage in readWeaponRegistry", checkWREqual(constructor2.newInstance(8,45,35,"Mjolnir",10,60), o, "minRange"));

			o = hashMap.get(9);
			assertTrue("incorrect damage in readWeaponRegistry", checkWREqual(constructor2.newInstance(9,55,35,"verybigweapon",20,70), o, "minRange"));


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException  e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalWeaponCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please make sure to close the csv file for the test file to write on it");
		}
	}

	


	////////////////////////////////////////////	Helper Methods//////////////////////////////////////////////////////////////
	
	private void testIsEnum(Class eClass) {
		assertTrue(eClass.getSimpleName() + " should be an Enum", eClass.isEnum());
	}

	private void testEnumValues(String path, String name, String[] values) {
		try {
			Class eClass = Class.forName(path);
			for(int i=0;i<values.length;i++) {
				try {
					Enum.valueOf((Class<Enum>)eClass, values[i]);
				}
				catch(IllegalArgumentException e) {
					fail(eClass.getSimpleName() + " enum can be " + values[i]);
				}
			}
		}
		catch(ClassNotFoundException e) {
			fail("There should be an enum called " + name + "in package " + path);
		}

	}




	private void testGetterLogicWallBase(Object createdObject, String name,String name2, Object value) throws Exception {

		Field f = null;
		Class curr = createdObject.getClass();

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}

		}

		f.setAccessible(true);


		Character c = name.charAt(0);

		String methodName = "get" + name2;

		if (value.getClass().equals(Boolean.class))
			methodName = "is" + Character.toUpperCase(c) + name.substring(1, name.length());

		Method m = createdObject.getClass().getMethod(methodName);
		assertEquals(
				"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
				+ " should return the correct value of variable \"" + name + "\".",
				value, m.invoke(createdObject));

	}

	private boolean checkDoubleArray(int[][]expected,int[][]original) {
		if(expected.length!=original.length) {
			return false;
		}
		if(expected[0].length!=original[0].length) {
			return false;
		}
		for(int i=0;i<expected.length;i++) {
			int[]row = expected[i];
			for(int j=0;j<row.length;j++) {
				if(expected[i][j]!=original[i][j])
					return false;
			}
		}
		return true;

	}

	private void testPhasesValues(Object createdObject, String name, int[][] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {


		Field f = null;
		Class curr = createdObject.getClass();
		Object currValue = values;

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}
		}
		f.setAccessible(true);
		Object o= f.get(createdObject);
		boolean equal = checkDoubleArray(values,(int[][])o );
		assertEquals(
				"The class " + createdObject.getClass().getSimpleName()
				+ "should initialize the instance variable \"" + name + "\" with {{ 1, 1, 1, 2, 1, 3, 5 },{ 2, 2, 2, 1, 3, 3, 4 },{ 4, 4, 4, 4, 4, 4, 4 } }.",
				true, equal);

	}

	private void testWallBaseValues(Object createdObject, String name, int value)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {


		Field f = null;
		Class curr = createdObject.getClass();
		Object currValue = value;

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}
		}
		f.setAccessible(true);
		Object o= f.get(createdObject);
		boolean equal=true;
		if(value!=(int)o)
			equal =false;
		assertEquals(
				"The class " + createdObject.getClass().getSimpleName()
				+ "should initialize the instance variable \"" + name + "\" with 10000.",
				true, equal);

	}


	
	
	private void writeTitanCSVForDataLoader() throws FileNotFoundException {
		PrintWriter csvWriter = new PrintWriter("titans.csv");
		csvWriter.println("5,300,25,25,30,20,8");
		csvWriter.println("6,200,30,20,45,25,7");
		csvWriter.println("7,400,55,25,40,20,6");
		csvWriter.println("8,5000,90,70,10,50,5");
		csvWriter.println("9,2000,90,70,10,50,5");

		csvWriter.flush();
		csvWriter.close();

	}


	private void rewriteOriginalTitanCSVForDataLoader() throws FileNotFoundException {
		PrintWriter csvWriter = new PrintWriter("titans.csv");
		csvWriter.println("1,100,15,15,10,10,1");
		csvWriter.println("2,100,20,10,15,15,2");
		csvWriter.println("3,200,85,15,10,30,3");
		csvWriter.println("4,1000,100,60,5,60,4");

		csvWriter.flush();
		csvWriter.close();

	}





	
	
	private void writeWeaponCSVForDataLoader() throws FileNotFoundException {
		PrintWriter csvWriter = new PrintWriter("weapons.csv");
		csvWriter.println("5,55,20,Anti Titan Shell");
		csvWriter.println("6,45,35,Strombreaker");
		csvWriter.println("7,35,90,The Destroyer");
		csvWriter.println("8,45,35,Mjolnir,10,60");
		csvWriter.println("9,55,35,verybigweapon,20,70");


		csvWriter.flush();
		csvWriter.close();

	}






	private void rewriteOriginalWeaponCSVForDataLoader() throws FileNotFoundException {
		PrintWriter csvWriter = new PrintWriter("weapons.csv");
		csvWriter.println("1,25,10,Anti Titan Shell");
		csvWriter.println("2,25,35,Long Range Spear");
		csvWriter.println("3,100,5,Wall Spread Cannon,20,50");
		csvWriter.println("4,75,100,Proximity Trap");

		csvWriter.flush();
		csvWriter.close();

	}

	
	private boolean checkTREqual(Object titan1, Object titan2, String field)
			throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		Class curr1 = Class.forName(titanRegistryPath);
		
		Field f = null;
		
		try {
			f = curr1.getDeclaredField(field);
			f.setAccessible(true);
			
			int mh1 = (int) f.get(titan1);
			int mh2 = (int) f.get(titan2);
			return mh1 == mh2;
			
		} catch (NoSuchFieldException e) {
			curr1 = curr1.getSuperclass();
			fail("Attributes name error");
			return false;
		}
		
	}
	private boolean checkWREqual(Object w1, Object w2, String field)
			throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		Class curr1 = Class.forName(weaponRegistryPath);
		
		Field f = null;
		
		try {
			f = curr1.getDeclaredField(field);
			f.setAccessible(true);
			
			int mh1 = (int) f.get(w1);
			int mh2 = (int) f.get(w2);
			return mh1 == mh2;
			
		} catch (NoSuchFieldException e) {
			curr1 = curr1.getSuperclass();
			fail("Attributes name error");
			return false;
		}
		
	}


	private void testLoadMethodExistsInClass(Class aClass, String methodName, Class inputType) {
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName,inputType);
		} catch (NoSuchMethodException e) {
			found = false;
		}

		String varName = "";

		assertTrue(aClass.getSimpleName() + " class should have " + methodName + " method that takes one "
				+ inputType.getSimpleName() + " parameter.", found);

		assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + ".",
				m.getReturnType().equals(Void.TYPE));

	}


	private void testSetterAbsentInSubclasses(String varName,
			String[] subclasses) throws SecurityException,
	ClassNotFoundException {
		String methodName = "set" + varName.substring(0, 1).toUpperCase()
				+ varName.substring(1);
		boolean methodIsInSubclasses = false;
		for (String subclass : subclasses) {
			Method[] methods = Class.forName(subclass).getDeclaredMethods();
			methodIsInSubclasses = methodIsInSubclasses
					|| containsMethodName(methods, methodName);

		}
		assertFalse("The " + methodName
				+ " method should not be implemented in a subclasses.",
				methodIsInSubclasses);
	}
	private void testClassIsSubclass(Class subClass, Class superClass) {
		assertEquals(subClass.getSimpleName() + " class should be a subclass from " + superClass.getSimpleName() + ".",
				superClass, subClass.getSuperclass());
	}



	private void testClassIsSubClass(Class superClass, Class subClass) {
		assertEquals(subClass.getSimpleName() + " should be a subClass of Class : "+ superClass.getSimpleName(), 
				superClass, subClass.getSuperclass());
	}





	private void testInterfaceMethod(Class iClass, String methodName, Class returnType, Class[] parameters) {
		Method[] methods = iClass.getDeclaredMethods();
		assertTrue(iClass.getSimpleName()+" interface should have " + methodName + "method", 
				containsMethodName(methods, methodName));

		Method m = null;
		boolean thrown = false;
		try {
			m = iClass.getDeclaredMethod(methodName,parameters);
		}
		catch(NoSuchMethodException e) {
			thrown = true;
		}

		assertTrue("Method " + methodName + " should have the following set of parameters : " + Arrays.toString(parameters),
				!thrown);
		assertTrue("wrong return type",m.getReturnType().equals(returnType));

	}

	private void testIsInterface(Class iClass) {
		assertTrue(iClass.getSimpleName() + " should be interface",iClass.isInterface());
	}

	private void testClassImplementsInterface(Class aClass, Class iClass) {
		assertTrue(aClass.getSimpleName() +" should implement " + iClass.getSimpleName(), 
				iClass.isAssignableFrom(aClass));
	}

	private void testLaneConstructorInitialization(Object createdObject, String[] names, Object[] values) throws IllegalArgumentException, IllegalAccessException {
		for(int i=0;i<names.length;i++) {
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];

			Field f = null;
			while(f == null) {
				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}
			}
			f.setAccessible(true);
			if(currName.equals("titans")) {
				PriorityQueue<Object> q = (PriorityQueue<Object>) f.get(createdObject);
				assertEquals("The constructor of the " + createdObject.getClass().getSimpleName()
						+ " class should initialize the instance variable \"" + currName
						+ "\" correctly. the size of titans should be equals to the 0 initially.", 
						currValue, q.size());
			}
			else if(currName.equals("weapons")) {
				ArrayList<Object> w = (ArrayList<Object>) f.get(createdObject);
				assertEquals("The constructor of the " + createdObject.getClass().getSimpleName()
						+ " class should initialize the instance variable \"" + currName
						+ "\" correctly. the size of weapons should be equals to the 0 initially.", 
						currValue, w.size());
			}	
			else	
				assertEquals("The constructor of the " + createdObject.getClass().getSimpleName()
						+ " class should initialize the instance variable \"" + currName + "\" correctly.", 
						currValue, f.get(createdObject));

		}
	}


	private void testWallConstructorInitialization(Object createdObject, String[] names, Object[] values) throws IllegalArgumentException, IllegalAccessException {
		for(int i=0;i<names.length;i++) {
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];

			Field f = null;
			while(f == null) {
				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}
			}
			f.setAccessible(true);
			if(currName.equals("currentHealth"))
				assertEquals("The constructor of the " + createdObject.getClass().getSimpleName()
						+ " class should initialize the instance variable \"" + currName
						+ "\" correctly. It should be equals to the baseHealth initially.", 
						currValue, f.get(createdObject));
			else	
				assertEquals("The constructor of the " + createdObject.getClass().getSimpleName()
						+ " class should initialize the instance variable \"" + currName + "\" correctly.", 
						currValue, f.get(createdObject));

		}
	}


	private void testClassIsAbstract(Class aClass) {
		assertTrue(aClass.getSimpleName() + " should be an abtract class.", 
				Modifier.isAbstract(aClass.getModifiers()));
	}

	private void testSetterMethodLogic(Object createdObject, String varName, Object setValue, Class setType) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		Field f = null;
		Class curr = createdObject.getClass();

		while(f == null) {
			if(curr == Object.class)
				fail("you should have " + varName + " as an instance variable in class " + curr.getSimpleName()
				+" or one of its superclasses");
			try {
				f = curr.getDeclaredField(varName);
			}
			catch(NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}
		}

		f.setAccessible(true);
		String MethodName = "set" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);
		Method m = null;
		try {
			m = curr.getDeclaredMethod(MethodName, setType);
		}
		catch(NoSuchMethodException e) {
			assertTrue("No such method",false);
		}
		m.invoke(createdObject, setValue);
		if(f.getType().equals(int.class) && (int)setValue < 0 && varName.equals("currentHealth")) 
			assertEquals("current health should not be less than 0", 0, f.get(createdObject));
		else

			assertEquals("The method \"" + MethodName + "\" in class " + createdObject.getClass().getSimpleName()
					+ " should set the correct value of variable \"", setValue, f.get(createdObject));
	}

	private void testGetterMethodLogic(Object createdObject, String varName, Object expectedValue) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, ClassNotFoundException, InvocationTargetException {
		Field f = null;
		Class curr = createdObject.getClass();

		while(f == null) {
			if(curr == Object.class)
				fail("you should have " + varName + " as an instance variable in class " + curr.getSimpleName()
				+" or one of its superclasses");
			try {
				f = curr.getDeclaredField(varName);
			}
			catch(NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}
		}

		f.setAccessible(true);
		f.set(createdObject, expectedValue);

		String methodName = "";
		if(expectedValue.getClass().equals(boolean.class))
			methodName = "is" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);
		else
			methodName = "get" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);

		Method m = createdObject.getClass().getDeclaredMethod(methodName);
		m.invoke(createdObject);
		assertTrue("The method \"" + methodName
				+ "\" in class Character should return the correct value of variable \"" + varName + "\"."
				, m.invoke(createdObject).equals(expectedValue));
	}

	private void testSetterMethodIsAbsentInClass(Class aClass, String methodName) {
		Method[] methods = aClass.getDeclaredMethods();
		String varName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
		assertTrue(varName + "should not have a setter", !containsMethodName(methods, methodName));
	}

	private void testSetterMethodExistInClass(Class aClass, String methodName, Class setType) {
		//first check if the method exists
		Method[] methods = aClass.getDeclaredMethods();
		String varName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
		assertTrue(varName + "should have a setter", containsMethodName(methods, methodName));

		//second check if takes a parameter or not
		Method m = null;
		boolean thrown = false;
		try {
			m = aClass.getDeclaredMethod(methodName,setType);
		}
		catch(NoSuchMethodException e) {
			thrown = true;
		}
		assertTrue(methodName + " method should take a parameter of type : " + setType, !thrown);

		//finally check if it is void
		assertTrue("this method should be void",m.getReturnType().equals(void.class));

	}


	private void testGetterMethodExistInClass(Class aClass, String methodName, Class returnType) {
		Method m = null;
		boolean thrown = false;
		try {
			m = aClass.getDeclaredMethod(methodName);
		}catch(NoSuchMethodException e) {
			thrown = true;
		}
		String varName = "";
		if(m.getReturnType().equals(boolean.class))
			varName = methodName.substring(2,3).toLowerCase() + methodName.substring(3);
		else
			varName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
		if(!thrown) {
			assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + " class.",
					m.getReturnType().equals(returnType));
		}
		else
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
			+ " is not a READ variable.", false);
	}



	private void testInstanceVariableIsStatic(Class aClass, String varName) {
		boolean thrown = false;
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		}
		catch(NoSuchFieldException e){
			thrown = true;
		}
		if(!thrown) {
			boolean isStatic = Modifier.isStatic(f.getModifiers());
			assertTrue(varName + " should be static", isStatic);
		}
		else
			assertTrue("you should have" + varName + " as a static variable", false);
	}



	private void testInstanceVariableIsPresent(Class aClass, String varName) throws SecurityException {
		boolean thrown = false;
		try {
			aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse("There should be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".",thrown);
	}

	private void testInstanceVariableIsNotPresent(Class aClass, String varName) throws SecurityException {
		boolean thrown = false;
		try {
			aClass.getDeclaredField(varName);
		}catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertTrue("There should not be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".", thrown);
	}

	private void testInstanceVariableOfTypeDoubleArray(Class aClass, String varName, Class expectedType) {
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			return;
		}
		Class varType = f.getType();
		assertEquals(
				"the attribute: " + varType.getSimpleName() + " should be of the type: " + expectedType.getSimpleName(),
				expectedType.getTypeName() + "[][]", varType.getTypeName());
	}

	private void testInstanceVariableIsPresent(Class aClass, String varName, boolean implementedVar)
			throws SecurityException {
		boolean thrown = false;
		try {
			aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		if (implementedVar) {
			assertFalse(
					"There should be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".",
					thrown);
		} else {
			assertTrue("The instance variable \"" + varName + "\" should not be declared in class "
					+ aClass.getSimpleName() + ".", thrown);
		}
	}

	private void testInstanceVariableOfType(Class aClass, String varName, Class expectedType) {
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			return;
		}
		Class varType = f.getType();
		assertEquals(
				"The attribute " + varType.getSimpleName() + " should be of the type " + expectedType.getSimpleName(),
				expectedType, varType);
	}

	private void testInstanceVariableIsPrivate(Class aClass, String varName) throws NoSuchFieldException, SecurityException {
		boolean thrown = false;
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		}catch(NoSuchFieldException e){
			thrown = true;
		}
		if(!thrown) {
			boolean isPrivate = (Modifier.isPrivate(f.getModifiers()));
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
			+ " should not be accessed outside that class.", isPrivate);

		}
		else {
			assertFalse("There should be \"" + varName + "\" instance variable in class " + aClass.getSimpleName() + ".",thrown);
		}

	}


	private void testInstanceVariableIsFinal(Class aClass, String varName) {
		boolean thrown = false;
		Field f = null;
		try {
			f = aClass.getDeclaredField(varName);
		}
		catch(NoSuchFieldException e){
			thrown = true;
		}
		if(!thrown) {
			boolean isFinal = Modifier.isFinal(f.getModifiers());
			assertTrue(varName + " should be final", isFinal);
		}
		else
			assertTrue("you should have" + varName + " as a final variable", false);
	}


	private void testGetterMethodExistsInClass(Class aClass, String methodName, Class returnedType,
			boolean readvariable) {
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			found = false;
		}

		String varName = "";
		if (returnedType == boolean.class)
			varName = methodName.substring(2, 3).toLowerCase() + methodName.substring(3);
		else
			varName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
		if (readvariable) {
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
			+ " is a READ variable.", found);
			assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + " class.",
					m.getReturnType().isAssignableFrom(returnedType));
		} else {
			assertFalse("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
			+ " is not a READ variable.", found);
		}

	}

	private void testGetterLogic(Object createdObject, String name, Object value) throws Exception {

		Field f = null;
		Class curr = createdObject.getClass();

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}

		}

		f.setAccessible(true);
		f.set(createdObject, value);

		Character c = name.charAt(0);

		String methodName = "get" + Character.toUpperCase(c) + name.substring(1, name.length());

		if (value.getClass().equals(Boolean.class))
			methodName = "is" + Character.toUpperCase(c) + name.substring(1, name.length());

		Method m = createdObject.getClass().getMethod(methodName);
		assertEquals(
				"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
				+ " should return the correct value of variable \"" + name + "\".",
				value, m.invoke(createdObject));

	}

	private static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}


	private void testSetterMethodExistsInClass(Class aClass, String methodName, Class inputType,
			boolean writeVariable) {

		Method[] methods = aClass.getDeclaredMethods();
		String varName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
		if (writeVariable) {
			assertTrue("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
			+ " is a WRITE variable.", containsMethodName(methods, methodName));
		} else {
			assertFalse("The \"" + varName + "\" instance variable in class " + aClass.getSimpleName()
			+ " is not a WRITE variable.", containsMethodName(methods, methodName));
			return;
		}
		Method m = null;
		boolean found = true;
		try {
			m = aClass.getDeclaredMethod(methodName, inputType);
		} catch (NoSuchMethodException e) {
			found = false;
		}

		assertTrue(aClass.getSimpleName() + " class should have " + methodName + " method that takes one "
				+ inputType.getSimpleName() + " parameter.", found);

		assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + ".",
				m.getReturnType().equals(Void.TYPE));

	}

	private void testSetterLogic(Object createdObject, String name, Object setValue, Object expectedValue, Class type)
			throws Exception {

		Field f = null;
		Class curr = createdObject.getClass();

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
						+ name + "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}

		}

		f.setAccessible(true);

		Character c = name.charAt(0);
		String methodName = "set" + Character.toUpperCase(c) + name.substring(1, name.length());
		Method m = createdObject.getClass().getMethod(methodName, type);
		m.invoke(createdObject, setValue);
		if (name == "currentActionPoints" || name == "currentHP") {
			if ((int) setValue > (int) expectedValue) {
				assertEquals("The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
						+ " should set the correct value of variable \"" + name
						+ "\". It should not exceed the maximum value.", expectedValue, f.get(createdObject));
			} else if ((int) setValue == -1 && (int) expectedValue == 0) {
				assertEquals("The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
						+ " should set the correct value of variable \"" + name
						+ "\". It should not be less than zero.", expectedValue, f.get(createdObject));
			} else {
				assertEquals(
						"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
						+ " should set the correct value of variable \"" + name + "\".",
						expectedValue, f.get(createdObject));
			}
		} else {
			assertEquals(
					"The method \"" + methodName + "\" in class " + createdObject.getClass().getSimpleName()
					+ " should set the correct value of variable \"" + name + "\".",
					expectedValue, f.get(createdObject));
		}
	}


	private void testConstructorExists(Class aClass, Class[] inputs) {
		boolean thrown = false;
		try {
			aClass.getConstructor(inputs);
		} catch (NoSuchMethodException e) {
			thrown = true;
		}

		if (inputs.length > 0) {
			String msg = "";
			int i = 0;
			do {
				msg += inputs[i].getSimpleName() + " and ";
				i++;
			} while (i < inputs.length);

			msg = msg.substring(0, msg.length() - 4);

			assertFalse(
					"Missing constructor with " + msg + " parameter" + (inputs.length > 1 ? "s" : "") + " in "
							+ aClass.getSimpleName() + " class.",

							thrown);
		} else
			assertFalse("Missing constructor with zero parameters in " + aClass.getSimpleName() + " class.",

					thrown);

	}

	private void testConstructorInitialization(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int i = 0; i < names.length; i++) {

			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];

			while (f == null) {

				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}
			}
			f.setAccessible(true);

			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
					+ " class should initialize the instance variable \"" + currName + "\" correctly.",
					currValue, f.get(createdObject));

		}

	}

	private void testConstructorInitializationTitans(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int i = 0; i < names.length; i++) {

			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];
			HashMap<Integer, Object> h = (HashMap<Integer, Object>) currValue;
			int len = h.size() ;


			while (f == null) {

				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}

			}

			f.setAccessible(true);
			HashMap<Integer, Object> h2 = (HashMap<Integer, Object>) f.get(createdObject);
			int len2 = h2.size() ;


			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
					+ " class should initialize the instance variable \"" + currName + "\" correctly by the data read from the csv file.",
					len, len2);
		}
	}

	private void testConstructorInitializationWeaponFactory(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int i = 0; i < names.length; i++) {

			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];
			HashMap<Integer, Object> h = (HashMap<Integer, Object>) currValue;
			int len = h.size() ;


			while (f == null) {

				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}

			}

			f.setAccessible(true);
			HashMap<Integer, Object> h2 = (HashMap<Integer, Object>) f.get(createdObject);
			int len2 = h2.size() ;


			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
					+ " class should initialize the instance variable \"" + currName + "\" correctly by the data read from the csv file.",
					len, len2);
		}

	}

	private void testConstructorInitializationApprochingTitans(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int i = 0; i < names.length; i++) {

			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];
			ArrayList<Object> h = (ArrayList) currValue;
			int len = h.size() ;


			while (f == null) {

				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}

			}

			f.setAccessible(true);
			ArrayList<Object> h2 = (ArrayList) f.get(createdObject);
			int len2 = h2.size() ;


			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
					+ " class should initialize the instance variable \"" + currName + "\" correctly by the data read from the csv file.",
					len, len2);
		}

	}

	private void testConstructorInitializationLanes(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int i = 0; i < names.length; i++) {

			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];
			PriorityQueue<Object> h = (PriorityQueue) currValue;
			int len = h.size() ;


			while (f == null) {

				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}

			}

			f.setAccessible(true);
			PriorityQueue<Object> h2 = (PriorityQueue) f.get(createdObject);
			int len2 = h2.size() ;


			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
					+ " class should initialize the instance variable \"" + currName + "\" correctly by the data read from the csv file.",
					len, len2);
		}

	}

	private void testConstructorInitializationOriginalLanes(Object createdObject, String[] names, Object[] values)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException {

		for (int i = 0; i < names.length; i++) {

			Field f = null;
			Class curr = createdObject.getClass();
			String currName = names[i];
			Object currValue = values[i];
			ArrayList<Object> h = (ArrayList) currValue;
			int len = h.size() ;


			while (f == null) {

				if (curr == Object.class)
					fail("Class " + createdObject.getClass().getSimpleName() + " should have the instance variable \""
							+ currName + "\".");
				try {
					f = curr.getDeclaredField(currName);
				} catch (NoSuchFieldException e) {
					curr = curr.getSuperclass();
				}

			}

			f.setAccessible(true);
			ArrayList<Object> h2 = (ArrayList) f.get(createdObject);
			int len2 = h2.size() ;


			assertEquals(
					"The constructor of the " + createdObject.getClass().getSimpleName()
					+ " class should initialize the instance variable \"" + currName + "\" correctly by the data read from the csv file.",
					len, len2);
		}

	}



	private void testGetterLogic(Object createdObject, String name, Object value,String currentMethodName)
			throws Exception {

		Field f = null;
		Class curr = createdObject.getClass();

		while (f == null) {

			if (curr == Object.class)
				fail("Class " + createdObject.getClass().getSimpleName()
						+ " should have the instance variable \"" + name
						+ "\".");
			try {
				f = curr.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				curr = curr.getSuperclass();
			}

		}

		f.setAccessible(true);
		f.set(createdObject, value);

		Character c = name.charAt(0);

		String methodName = currentMethodName;

		if (value.getClass().equals(Boolean.class)
				&& !name.substring(0, 2).equals("is"))
			methodName = "is" + Character.toUpperCase(c)
			+ name.substring(1, name.length());
		else if (value.getClass().equals(Boolean.class)
				&& name.substring(0, 2).equals("is"))
			methodName = "is" + Character.toUpperCase(name.charAt(2))
			+ name.substring(3, name.length());

		Method m = createdObject.getClass().getMethod(methodName);
		assertEquals("The method \"" + methodName + "\" in class "
				+ createdObject.getClass().getSimpleName()
				+ " should return the correct value of variable \"" + name
				+ "\".", value, m.invoke(createdObject));

	}





	private void testAttributeExistence(String givenAttributeName,String classPath) throws ClassNotFoundException {
		Class givenClass = Class.forName(classPath);
		String attributeName = givenAttributeName;
		try {
			Field field = givenClass.getDeclaredField(givenAttributeName);

			assertTrue("Attribute " + attributeName + " should exist in class " + givenClass.getSimpleName(),
					field != null);
		} catch (Exception e) {
			fail("Exception occurred: " + e.getMessage());
		}
	}

}
