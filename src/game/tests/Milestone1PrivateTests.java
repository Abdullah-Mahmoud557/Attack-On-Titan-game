package game.tests;

import game.engine.titans.Titan;
import game.engine.weapons.Weapon;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class Milestone1PrivateTests {

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



	@Test
	public void testWallInstanceVariableBaseHealthIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(wallPath), "baseHealth");
	}

	
	@Test
	public void testWallInstanceVariableBaseHealthGetterExistance() throws ClassNotFoundException {
		testGetterMethodExistInClass(Class.forName(wallPath), "getBaseHealth", int.class);
	}

	
	@Test
	public void testWallInstanceVariableCurrentHealthGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int baseHealth = (int) (Math.random() * 10) + 1;
		int currentHealth = (int) (Math.random() * 10) + 1;
		Constructor<?> constructor = Class.forName(wallPath).getConstructor(int.class);
		Object createdObject = constructor.newInstance(baseHealth);
		testGetterMethodLogic(createdObject, "currentHealth", currentHealth);
	}

	
	@Test
	public void testLaneInstanceVariableLaneWallOfType() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(lanePath), "laneWall", Class.forName(wallPath));
	}

	@Test
	public void testLaneInstanceVariableDangerLevelIsPrivate() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		testInstanceVariableIsPrivate(Class.forName(lanePath), "dangerLevel");
	}
	
	
	
	@Test
	public void testLaneConstructorExists() throws ClassNotFoundException {
		Class[] parameters = {Class.forName(wallPath)};
		testConstructorExists(Class.forName(lanePath), parameters);
	}
	
	

	@Test
	public void testMobilIsAnInterface() throws ClassNotFoundException {
		testIsInterface(Class.forName(mobilPath));
	}

	
	
	
	@Test
	public void testInvalidLanExceptionMSGPresent() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(invalidLaneExceptionPath), "MSG");
	}

	
	@Test
	public void testInvalidLanExceptionMSGOfType() throws ClassNotFoundException {
		testInstanceVariableOfType(Class.forName(invalidLaneExceptionPath), "MSG", String.class);
	}
	
	
	@Test
	public void testInsufficientResourcesExceptionMSGValue() throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Class aClass = Class.forName(insufficientResourcesExceptionPath);
		Field f = aClass.getDeclaredField("MSG");
		f.setAccessible(true);
		String expected = "Not enough resources, resources provided = ";
		String actual = (String) f.get(null);
		assertEquals("wrong value of MSG", expected, actual);
	}

	
	@Test
	public void testInvalidCSVFormatMSGIsFinal() throws SecurityException, ClassNotFoundException {
		testInstanceVariableIsFinal(Class.forName(invalidCSVFormatExceptionPath), "MSG");
	}
	
	@Test
	public void testInvalidCSVFormatInputLineGetterLogic() throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String inputLine = "dummy text";
		Constructor<?> Constructor = Class.forName(invalidCSVFormatExceptionPath).getConstructor(String.class);
		Object createdObject = Constructor.newInstance(inputLine);

		testGetterMethodLogic(createdObject, "inputLine", inputLine);
	}

	

	@Test(timeout = 100)
	public void testClassPureTitanClassPathExists() {
		try {
			Class.forName(PureTitanClassPath);
		} catch (ClassNotFoundException e) {
			fail("missing class PureTitan");
		}
	}
	
	@Test(timeout = 100)
	public void testbaseHealthFinalAttribute() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field baseHealthField = Class.forName(titanClassPath).getDeclaredField("baseHealth");
		assertTrue("The baseHealth attribute should be final", Modifier.isFinal(baseHealthField.getModifiers()));
	}
	
	
	@Test(timeout = 100)
	public void testbaseHealthPrivateAttribute() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field baseHealthField = Class.forName(titanClassPath).getDeclaredField("baseHealth");
		assertTrue("The baseHealth attribute should be private", Modifier.isPrivate(baseHealthField.getModifiers()));
	}
	
	
	

	@Test(timeout = 100)
	public void testBaseHealthExistance() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("baseHealth",titanClassPath);
	}
	
	
	
	@Test(timeout = 100)
	public void testBaseHealthReadOnly() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanClassPath),"getBaseHealth",int.class,true);
		testSetterMethodExistsInClass(Class.forName(titanClassPath),"setBaseHealth",int.class,false);
	}
	
	@Test(timeout = 100)
	public void testCurrentHealthReadAndWrite() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanClassPath),"getCurrentHealth",int.class,true);
		testSetterMethodExistsInClass(Class.forName(titanClassPath),"setCurrentHealth",int.class,true);
	}
	
	
	
	
	
	
	
	@Test(timeout = 100)
	public void testTitanCodeExistanceInColossalClass() throws NoSuchFieldException, ClassNotFoundException {
		testAttributeExistence("TITAN_CODE",ColossalTitanClassPath);
	}
	
	
	
	@Test(timeout = 100)
	public void testTitanCodeFinalAttributeInArmoredClass() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(ArmoredTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be final", Modifier.isFinal(titanCodeField.getModifiers()));
	}
	
	
	
	@Test(timeout = 100)
	public void testTitanCodePublicAttributeInColossalClass() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(ColossalTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be public", Modifier.isPublic(titanCodeField.getModifiers()));
	}
	
	
	
	

	@Test(timeout = 100)
	public void testConstructorInitializationPureTitan() throws Exception {
		int baseHealth = 100;
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);


		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
		String[] names = { "baseHealth", "baseDamage", "heightInMeters", "distanceFromBase", "speed", "resourcesValue",
		"dangerLevel" };
		Object[] values = { baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel };

		Class superClass = Class.forName(PureTitanClassPath).getSuperclass();
		Method m = superClass.getDeclaredMethod("getCurrentHealth", null);
		assertEquals(baseHealth, m.invoke(pureTitan, null));

		testConstructorInitialization(pureTitan, names, values);
	}
	
	
	

	@Test(timeout = 100)
	public void testGetterLogicForInstanceVariableBaseHealthInClassPureTitan() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,dangerLevel,resourcesValue);
		testGetterLogic(pureTitan, "baseHealth", baseHealth,"getBaseHealth");
	}

	

	@Test(timeout = 100)
	public void testGetterLogicForInstanceVariableSpeedInClassPureTitan() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,dangerLevel,resourcesValue);
		testGetterLogic(pureTitan, "speed", speed,"getSpeed");
	}
	
	
	
	
	
	
	@Test(timeout = 100)
	public void testSetterForInstanceVariableSpeedDoesNotExistInTitanSubClasses() throws Exception {
		String[] subClasses = { PureTitanClassPath, AbnormalTitanClassPath , ArmoredTitanClassPath ,ColossalTitanClassPath};
		testSetterAbsentInSubclasses("setSpeed", subClasses);
	}

	@Test(timeout = 100)
	public void testDistanceSetterLogic() throws Exception {
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

		Method setterMethod = superClass.getDeclaredMethod("setDistance",  int.class);
		Method getterMethod = superClass.getDeclaredMethod("getDistance",  null);

		int new_distance = (int) (Math.random() * 5);
		setterMethod.invoke(pureTitan,new_distance);
		assertEquals(getterMethod.invoke(pureTitan,null), new_distance);

		int negative_distance = (int) (Math.random() * 5) * -1;
		setterMethod.invoke(pureTitan,negative_distance);
		assertEquals(getterMethod.invoke(pureTitan,null), 0);
	}
	
	
	@Test(timeout = 100)
	public void testCompareToGreaterThanLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase1 = 15;
		int distanceFromBase2 = 10;

		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);


		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan1 = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase1,speed,resourcesValue,dangerLevel);
		Object pureTitan2 = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase2,speed,resourcesValue,dangerLevel);

		Class superClass = Class.forName(PureTitanClassPath).getSuperclass();

		Method m = superClass.getDeclaredMethod("compareTo", superClass);
		assertTrue(((int)m.invoke(pureTitan1, pureTitan2)) > 0);
	}
	
	@Test(timeout = 1000)
	public void testIsWeaponAnAbstractClass() throws Exception {
		testClassIsAbstract(Class.forName(weaponPath));
	}
	
	
	
	@Test(timeout = 1000)
	public void testReadOnlyForInstanceVariableBaseDamageInClassWeapon() throws Exception {
		testGetterMethodExistsInClass(Class.forName(weaponPath), "getDamage", int.class, true);
		testSetterMethodExistsInClass(Class.forName(weaponPath), "setDamage", int.class , false);
	}

	
	@Test(timeout = 100)
	public void testPiercingCannonClassExists() {
		try {
			Class.forName(piercingCannonPath);
		} catch (ClassNotFoundException e) {
			fail("missing class PiercingCannon");
		}
	}

	@Test(timeout = 1000)
	public void testSniperCannonIsSubClassOfWeapon() throws Exception {
		testClassIsSubclass(Class.forName(sniperCannonPath), Class.forName(weaponPath));
	}
	
	
	
	@Test (timeout = 100)
	public void testAttributeWeaponCodeIsFinalInClassPiercingCannon() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field WeaponCodeField = Class.forName(piercingCannonPath).getDeclaredField("WEAPON_CODE");
		assertTrue("The WEAPON_CODE attribute should be final", Modifier.isFinal(WeaponCodeField.getModifiers()));
	}
	

	@Test (timeout = 100)
	public void testAttributeMinRangeIsFinalInClassVolleySpreadCannon() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field minRangeField = Class.forName(volleySpreadCannonPath).getDeclaredField("minRange");
		assertTrue("The minRange attribute should be final", Modifier.isFinal(minRangeField.getModifiers()));
	}

	@Test(timeout = 1000)
	public void testReadOnlyForInstanceVariableMaxRangeInClassVolleySpreadCannon() throws Exception {
		testGetterMethodExistsInClass(Class.forName(volleySpreadCannonPath), "getMaxRange", int.class, true);
		testSetterMethodExistsInClass(Class.forName(volleySpreadCannonPath), "setMaxRange", int.class , false);
	}

	@Test(timeout = 1000)
	public void testConstructorInitializationSniperCannon() throws Exception {
		int baseDamage = (int) (Math.random() * 1000);

		Constructor<?> constructor = Class.forName(sniperCannonPath).getConstructor(int.class);
		Object sniperCannon =  constructor.newInstance(baseDamage);

		String[] attributes = { "baseDamage" };
		Object[] values = { baseDamage  };

		testConstructorInitialization(sniperCannon, attributes, values);
	}
	
	
	@Test(timeout = 1000)
	public void testGetterLogicForInstanceVariableMinRangeInClassVolleySpreadCannon() throws Exception {
		int baseDamage = (int) (Math.random() * 1000);
		int minRange = (int) (Math.random() * 1000);
		int maxRange = (int) (Math.random() * 1000);

		Constructor<?> constructor = Class.forName(volleySpreadCannonPath).getConstructor(int.class, int.class, int.class);
		Object volley = constructor.newInstance(baseDamage, minRange, maxRange);

		testGetterLogic(volley, "minRange", minRange,"getMinRange" );
	}
	
	
	
	@Test(timeout = 100)
	public void testWallBaseValueBattle() throws Exception {
		Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1;
		int random6 = (int) (Math.random() * 10) + 1;
		Object b = constructor.newInstance(random1, random2, random3,random4,random5);
		String name = "WALL_BASE_HEALTH";
		int h =10000;
		testWallBaseValues(b, name, h);
	}
	
	

	@Test(timeout = 100)
	public void testFactoryResponseInstanceVariableRemainingResourcesType() throws Exception {
		testInstanceVariableOfType(Class.forName(factoryResponsePath), "remainingResources", int.class);

	}
	
	
	@Test(timeout = 100)
	public void testFactoryResponseInstanceVariableWeaponIsFinal() throws Exception {
		testInstanceVariableIsFinal(Class.forName(factoryResponsePath), "weapon");

	}

	
	
	@Test
	public void testFactoryResponseConstructorExists() throws Exception {
		Class[] parameters = { Class.forName(weaponPath) , int.class};
		testConstructorExists(Class.forName(factoryResponsePath), parameters);
	}

	
	
	
	

	@Test(timeout = 100) public void testInstanceVariableFactoryResponseRemainingResourcesGetterLogic() throws Exception { 
		Constructor<?> factoryResponseConstructor = Class.forName(factoryResponsePath).getConstructor(Class.forName(weaponPath),int.class);
		Constructor<?> weaponConstructor = Class.forName(weaponPiercingCannonPath).getConstructor(int.class);
		int randomRemainingResources = (int) (Math.random() * 10 )+1; 
		int baseDamage = (int) (Math.random() * 10 )+1; 
		Object weapon = weaponConstructor.newInstance(baseDamage);
		Object c = factoryResponseConstructor.newInstance(weapon,randomRemainingResources);
		testGetterLogic(c, "remainingResources", randomRemainingResources); }

	
	@Test
	public void testWeaponFactoryConstructorExists() throws Exception {
		Class[] parameters = {};
		testConstructorExists(Class.forName(weaponFactoryPath), parameters);
	}

	

	@Test(timeout = 800)
	public void testHealthInBattleisStatic() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field f = Class.forName(battlePath).getDeclaredField("WALL_BASE_HEALTH");
		int modifiers = f.getModifiers();
		assertTrue(f.getName() + " variable in calss Game should be static", (Modifier.isStatic(modifiers)));
	}

	@Test(timeout = 100)
	public void testBattlePhaseInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "battlePhase", true);

	}
	@Test(timeout = 100)
	public void testBattlePhaseInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "battlePhase");

	}
	
	

	@Test(timeout = 100)
	public void testTitanSpawnDistanceInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "titanSpawnDistance", int.class);

	}
	
	@Test(timeout = 100)
	public void testTitansArchivesInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "titansArchives", HashMap.class);

	}

	@Test(timeout = 100)
	public void testLanesInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "lanes");

	}
	
	
	@Test(timeout = 100)
	public void testOriginalLanesInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "originalLanes", ArrayList.class);

	}
	
	
	@Test(timeout = 100)
	public void testInstanceVariableBattlePhaseBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getBattlePhase",Class.forName(battlePhasePath) , true);
	}



	@Test(timeout = 100)
	public void testInstanceVariableTitansArchivesBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getTitansArchives",HashMap.class, true);
	}

	
	@Test(timeout = 100)
	public void testInstanceVariableNumberofTurnsBattleGetterLogic() throws Exception {

		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);
		testGetterLogic(battle, "numberOfTurns", nb);

	}
	
	@Test(timeout = 100)
	public void testInstanceVariableScoreBattleGetterLogic() throws Exception {
		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);
		testGetterLogic(battle, "score", score);
	}

	
	@Test(timeout = 100)
	public void testInstanceVariableApproachingTitansBattleGetterLogic() throws Exception {
		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);
		ArrayList<Object> t = new ArrayList();
		testGetterLogic(battle, "approachingTitans", t);
	}
	
	@Test(timeout = 100)
	public void testInstanceVariableResourcesGatheredBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setResourcesGathered", int.class, true);
	}
	
	@Test(timeout = 100)
	public void testInstanceVariableWeaponFactoryBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setWeaponFactory",Class.forName(weaponFactoryPath), false);
	}


	
	@Test(timeout = 100)
	public void testInstanceVariableOriginalLanesBattleSetter() throws ClassNotFoundException {
		testSetterMethodExistsInClass(Class.forName(battlePath), "setOriginalLanes",ArrayList.class, false);
	}



	@Test(timeout = 100)
	public void testBattleNumberOfTurnsSetterLogic() throws Exception {
		Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1;
		Object b = constructor.newInstance(random1, random2, random3,random4,random5);
		testSetterLogic(b, "numberOfTurns", random1, random1, int.class);
	}
	
	
	@Test(timeout = 100)
	public void testBattleScoreSetterLogic() throws Exception {
		Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1;
		int random6 = (int) (Math.random() * 10) + 1;
		Object b = constructor.newInstance(random1, random2, random3,random4,random5);
		testSetterLogic(b, "score", random6, random6, int.class);
	}

	
	@Test(timeout = 1000)
	public void testConstructorInitializationBattleInt() throws Exception {
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
		String[] names = { "numberOfTurns", "battlePhase","numberOfTitansPerTurn","score","titanSpawnDistance","resourcesGathered"};
		Object early= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "EARLY");
		Object[] values = {random1,early,1,random2,random3,random4*random5};
		testConstructorInitialization(battle, names, values);
	}


	
	@Test(timeout = 1000)
	public void testConstructorInitializationBattleOriginalLanes() throws Exception {
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1; 
		int random6 = (int) (Math.random() * 10) + 1; 
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

		Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
		Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
		for(int i=0;i<random4;i++)
		{
			Object wall = wallConstructor.newInstance(random6);
			Object lane = laneConstructor.newInstance(wall);
			Olanes.add( lane); 
		}

		String[] names = { "originalLanes"};
		Object[] values = {Olanes};
		testConstructorInitializationOriginalLanes(battle, names, values);
	}

	
	
	@Test(timeout = 1000)
	public void testConstructorInitializationClassTitanRegistry() throws Exception {

		Constructor<?> constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomBaseHealth = (int) (Math.random() * 20) + 1;
		int randomBaseDmg = (int) (Math.random() * 20) + 1;
		int randomHIM = (int) (Math.random() * 5) + 1;
		int randomSpeed = (int) (Math.random() * 20) + 1;
		int randomRV = (int) (Math.random() * 20) + 1;
		int randomDL = (int) (Math.random() * 20) + 1;

		Object tR = constructor.newInstance(randomCode,randomBaseHealth,randomBaseDmg,randomHIM,randomSpeed,randomRV,randomDL);
		String[] names = { "code","baseHealth", "baseDamage","heightInMeters","speed","resourcesValue","dangerLevel" };
		Object[] values = { randomCode,randomBaseHealth,randomBaseDmg,randomHIM,randomSpeed,randomRV,randomDL };

		testConstructorInitialization(tR, names, values);
	}

	
	@Test(timeout = 100)
	public void testInstanceVariableCodeTitanRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanRegistryPath), "getCode", int.class, true);
	}

	@Test(timeout = 100)
	public void testTitanRegistrySetterCodeDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { titanRegistryPath };
		testSetterAbsentInSubclasses("code", subClasses);
	}
	

	@Test(timeout = 100)
	public void testInstanceVariableBaseHealthTRGetterLogic() throws Exception {
		Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomBaseHealth = (int) (Math.random() * 20) + 1;
		int randomBaseDmg = (int) (Math.random() * 20) + 1;
		int randomHIM = (int) (Math.random() * 5) + 1;
		int randomSpeed = (int) (Math.random() * 20) + 1;
		int randomRV = (int) (Math.random() * 20) + 1;
		int randomDL = (int) (Math.random() * 20) + 1;

		Object c = Constructor.newInstance(randomCode,randomBaseHealth,randomBaseDmg,randomHIM,randomSpeed,randomRV,randomDL);
		testGetterLogic(c, "baseHealth", randomBaseHealth);
	}

	
	@Test(timeout = 100)
	public void testTitanRegistryInstanceVariableHIMPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(titanRegistryPath), "heightInMeters", true);
	}

	
	@Test(timeout = 100)
	public void testTitanRegistryInstanceVariableSpeedPrivate() throws Exception {

		Field f = Class.forName(titanRegistryPath).getDeclaredField("speed");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(titanRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}
	
	@Test(timeout = 100)
	public void testTitanRegistrySetterResourcesValueDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { titanRegistryPath };
		testSetterAbsentInSubclasses("resourcesValue", subClasses);
	}

	
	@Test(timeout = 100)
	public void testInstanceVariableDLGetterLogic() throws Exception {
		Constructor<?> Constructor = Class.forName(titanRegistryPath).getConstructor(int.class, int.class,int.class, int.class,int.class, int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomBaseHealth = (int) (Math.random() * 20) + 1;
		int randomBaseDmg = (int) (Math.random() * 20) + 1;
		int randomHIM = (int) (Math.random() * 5) + 1;
		int randomSpeed = (int) (Math.random() * 20) + 1;
		int randomRV = (int) (Math.random() * 20) + 1;
		int randomDL = (int) (Math.random() * 20) + 1;

		Object c = Constructor.newInstance(randomCode,randomBaseHealth,randomBaseDmg,randomHIM,randomSpeed,randomRV,randomDL);
		testGetterLogic(c, "dangerLevel", randomDL);
	}	
	
	
	@Test(timeout = 100)
	public void testWeaponRegistryInstanceVariableCodeisFinal() throws Exception {
		Field f = Class.forName(weaponRegistryPath).getDeclaredField("code");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in calss Weapon Registry should be final",(Modifier.isFinal(modifiers)));

	}
	
	
	@Test(timeout = 100)
	public void testInstanceVariableCodeWeaponRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(weaponRegistryPath), "getCode", int.class, true);
	}
	
	
	@Test(timeout = 100)
	public void testWeaponRegistrySetterPriceDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { weaponRegistryPath };
		testSetterAbsentInSubclasses("price", subClasses);
	}
	
	
	
	@Test(timeout = 100)
	public void testInstanceVariableDamageWRGetterLogic() throws Exception {

		Constructor<?> constructor = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomPrice = (int) (Math.random() * 20) + 1;
		int randomDmg = (int) (Math.random() * 20) + 1;
		int randomNameId = (int) (Math.random() * 5) + 1;
		int randomMax = (int) (Math.random() * 30) + 1;
		int randomMin = (int) (Math.random() * 5) + 1;


		Object wR = constructor.newInstance(randomCode,randomPrice,randomDmg,"Name_"+randomNameId,randomMax,randomMin);
		testGetterLogic(wR, "damage", randomDmg);
	}

	@Test(timeout = 100)
	public void testWeaponRegistryInstanceVariableNamePresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(weaponRegistryPath), "name", true);
	}

	
	
	@Test(timeout = 100)
	public void testWeaponRegistrySetterMaxRangeDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { weaponRegistryPath };
		testSetterAbsentInSubclasses("maxRange", subClasses);
	}
	
	
	@Test(timeout = 100)
	public void testDataLoaderTITANS_FILE_NAMEFinal() throws Exception {
		Field f = Class.forName(dataLoaderPath).getDeclaredField("TITANS_FILE_NAME");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(dataLoaderPath).getName()+" should be final",(Modifier.isFinal(modifiers)));	
	}

	
	@Test(timeout = 1000)
	public void testCorrectTitanFileName() {
		try {

			Field fd = Class.forName(dataLoaderPath).getDeclaredField("TITANS_FILE_NAME");
			fd.setAccessible(true);
			assertEquals("titans.csv", ((String)fd.get(null)));
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException  e) {
			e.printStackTrace();
			fail();

		}
	}
	
	
	
	@Test(timeout = 1000)
	public void testReadTitanRegistryisPublic() {

		Method m;
		try {
			m = Class.forName(dataLoaderPath).getMethod("readTitanRegistry",null);
			assertTrue("readTitanRegistry expected to be public",Modifier.isPublic(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}
	
	
	
	@Test(timeout = 1000)
	public void testLoadbaseHealthTitansCorrectly() {
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
				assertTrue("incorrect baseHealth in readTitanRegistry", checkTREqual(object, o, "baseHealth"));
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
			System.out.println("Please make sure to close the csv file for the test file to write on it");
			e.printStackTrace();
			fail();
		}
	}

	@Test(timeout = 1000)
	public void testLoaddangerLevelTitansCorrectly() {
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
				assertTrue("incorrect dangerLevel in readTitanRegistry", checkTREqual(object, o, "dangerLevel"));
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
			System.out.println("Please make sure to close the csv file for the test file to write on it");
			e.printStackTrace();
			fail();
		}
	}

	
	@Test(timeout = 1000)
	public void testLoadMaxWeaponRegistryCorrectly() {
		try {
			writeWeaponCSVForDataLoader();



			Method m = Class.forName(dataLoaderPath).getMethod("readWeaponRegistry",null);
			HashMap<Integer, Object> hashMap=((HashMap<Integer, Object>)(m.invoke(null, null)));

			Constructor<?> constructor1 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class);
			Constructor<?> constructor2 = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);


			Object o = hashMap.get(8);
			assertTrue("incorrect damage in readWeaponRegistry", checkWREqual(constructor2.newInstance(8,45,35,"Mjolnir",10,60), o, "maxRange"));

			o = hashMap.get(9);
			assertTrue("incorrect damage in readWeaponRegistry", checkWREqual(constructor2.newInstance(9,55,35,"verybigweapon",20,70), o, "maxRange"));


		} catch (FileNotFoundException  | SecurityException | ClassNotFoundException
				| IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException  e) {
			e.printStackTrace();
			fail();

		}
		try {
			rewriteOriginalWeaponCSVForDataLoader();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Please make sure to close the csv file for the test file to write on it");
			e.printStackTrace();
			fail();
		}
	}



	@Test(timeout = 1000)
	public void testReadTitanRegistryisStatic() {

		Method m;
		try {
			m = Class.forName(dataLoaderPath).getMethod("readTitanRegistry",null);
			assertTrue("readTitanRegistry expected to be static method",Modifier.isStatic(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}

	
	
	
	@Test(timeout = 100)
	public void testWeaponRegistryInstanceVariablePricePrivate() throws Exception {
		Field f = Class.forName(weaponRegistryPath).getDeclaredField("price");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(weaponRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));	
	}
	
	@Test(timeout = 100)
	public void testInstanceVariablePriceWRGetterLogic() throws Exception {

		Constructor<?> constructor = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomPrice = (int) (Math.random() * 20) + 1;
		int randomDmg = (int) (Math.random() * 20) + 1;
		int randomNameId = (int) (Math.random() * 5) + 1;
		int randomMax = (int) (Math.random() * 30) + 1;
		int randomMin = (int) (Math.random() * 5) + 1;


		Object wR = constructor.newInstance(randomCode,randomPrice,randomDmg,"Name_"+randomNameId,randomMax,randomMin);
		testGetterLogic(wR, "price", randomPrice);
	}

	@Test(timeout = 1000)
	public void testConstructorInitializationClassWeaponRegistry3() throws Exception {

		Constructor<?> constructor = Class.forName(weaponRegistryPath).getConstructor( int.class, int.class,int.class, String.class,int.class, int.class);
		int randomCode = (int) (Math.random() * 10) + 1;
		int randomPrice = (int) (Math.random() * 20) + 1;
		int randomDmg = (int) (Math.random() * 20) + 1;
		int randomNameId = (int) (Math.random() * 5) + 1;
		int randomMax = (int) (Math.random() * 30) + 1;
		int randomMin = (int) (Math.random() * 5) + 1;


		Object wR = constructor.newInstance(randomCode,randomPrice,randomDmg,"Name_"+randomNameId,randomMax,randomMin);
		String[] names = {"code", "price","damage","name","minRange","maxRange"};
		Object[] values = { randomCode,randomPrice,randomDmg,"Name_"+randomNameId,randomMax,randomMin};

		testConstructorInitialization(wR, names, values);
	}

	
	

	@Test(timeout = 100)
	public void testTitanRegistrySetterDLDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { titanRegistryPath };
		testSetterAbsentInSubclasses("dangerLevel", subClasses);
	}

	
	@Test(timeout = 100)
	public void testTitanRegistryInstanceVariableResourcesValuePrivate() throws Exception {

		Field f = Class.forName(titanRegistryPath).getDeclaredField("resourcesValue");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in "+Class.forName(titanRegistryPath).getName()+" should be private",(Modifier.isPrivate(modifiers)));

	}

	@Test(timeout = 100)
	public void testTitanRegistryInstanceVariableSpeedPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(titanRegistryPath), "speed", true);
	}



	@Test(timeout = 100)
	public void testTitanRegistryInstanceVariableHIMType() throws Exception {
		testInstanceVariableOfType(Class.forName(titanRegistryPath), "heightInMeters", int.class);
	}

	@Test(timeout = 100)
	public void testInstanceVariableBaseDmgTitanRegistryGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(titanRegistryPath), "getBaseDamage", int.class, true);
	}
	
	
	@Test(timeout = 100)
	public void testTitanRegistrySetterBaseHealthDoesNotExist()
			throws ClassNotFoundException {
		String[] subClasses = { titanRegistryPath };
		testSetterAbsentInSubclasses("baseHealth", subClasses);
	}


	@Test(timeout = 100)
	public void testTitanRegistryInstanceVariableBaseHealthPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(titanRegistryPath), "baseHealth", true);
	}

	
	@Test(timeout = 100)
	public void testTitanRegistryInstanceVariableCodeisFinal() throws Exception {
		Field f = Class.forName(titanRegistryPath).getDeclaredField("code");
		int modifiers = f.getModifiers();

		assertTrue(f.getName()+" variable in calss Titan Registry should be final",(Modifier.isFinal(modifiers)));

	}

	
	
	@Test(timeout = 1000)
	public void testConstructorInitializationBattleLanes() throws Exception {
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1; 
		int random6 = (int) (Math.random() * 10) + 1;
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
		Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
		Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
		for(int i=0;i<random4;i++)
		{
			Object wall = wallConstructor.newInstance(random6);
			Object lane = laneConstructor.newInstance(wall);
			lanes.add( lane); 
		}

		String[] names = {"lanes"};
		Object[] values = {lanes};
		testConstructorInitializationLanes(battle, names, values);
	}

	
	@Test
	public void testBattleConstructorExists() throws Exception {
		Class[] parameters = {int.class,int.class,int.class,int.class,int.class};
		testConstructorExists(Class.forName(battlePath), parameters);
	}



	@Test(timeout = 100)
	public void testBattleResourcesGatheredSetterLogic() throws Exception {
		Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
		int random1 = (int) (Math.random() * 10) + 1;
		int random2 = (int) (Math.random() * 10) + 1;
		int random3 = (int) (Math.random() * 10) + 1;
		int random4 = (int) (Math.random() * 10) + 1;
		int random5 = (int) (Math.random() * 10) + 1;
		int random = (int) (Math.random() * 10) + 1;
		Object b = constructor.newInstance(random1, random2, random3,random4,random5);
		testSetterLogic(b, "resourcesGathered", random, random, int.class);
	}

	
	
	@Test(timeout = 100)
	public void testInstanceVariableTitanSpawnDistanceBattleGetterLogic() throws Exception {
		int nb = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int distance = (int) (Math.random() * 10) + 1;
		int lanes = (int) (Math.random() * 10) + 1;
		int resources = (int) (Math.random() * 10) + 1;
		Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
		Object battle = battleConstructor.newInstance(nb,score,distance,lanes,resources);
		testGetterLogic(battle, "titanSpawnDistance", distance);
	}
	
	

	@Test(timeout = 100)
	public void testInstanceVariableOriginalLanesBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getOriginalLanes",ArrayList.class, true);
	}

	
	@Test(timeout = 100)
	public void testInstanceVariableNumberofTurnsBattleGetter() throws ClassNotFoundException {
		testGetterMethodExistsInClass(Class.forName(battlePath), "getNumberOfTurns", int.class, true);
	}

	@Test(timeout = 100)
	public void testOriginalLanesInBattleIsFinal() throws Exception {
		testInstanceVariableIsFinal(Class.forName(battlePath), "originalLanes");

	}
	
	@Test(timeout = 100)
	public void testApproachingTitansInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "approachingTitans", ArrayList.class);

	}

	@Test(timeout = 100)
	public void testTitansArchivesInBattleIsPrivate() throws Exception {
		testInstanceVariableIsPrivate(Class.forName(battlePath), "titansArchives");

	}
	

	@Test(timeout = 100)
	public void testWeaponFactoryInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "weaponFactory", Class.forName(weaponFactoryPath));

	}

	
	@Test(timeout = 100)
	public void testResourcesInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "resourcesGathered", int.class);

	}


	@Test(timeout = 100)
	public void testTurnsInBattleType() throws Exception {
		testInstanceVariableOfType(Class.forName(battlePath), "numberOfTurns", int.class);

	}
	
	@Test(timeout = 100)
	public void testPhasesInBattleIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(battlePath), "PHASES_APPROACHING_TITANS", true);

	}
	
	
	
	@Test
	public void testWeaponFactoryInstanceVariableWeaponShopGetterLogic() throws Exception {
		Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
		Object weaponFactory = weaponFactoryConstructor.newInstance();
		HashMap<Integer, Object> h = new HashMap();
		testGetterLogic(weaponFactory, "weaponShop", h);
	}


	
	@Test
	public void testWeaponFactoryInstanceVariableWeaponShopIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(weaponFactoryPath), "weaponShop", true);
	}
	
	@Test(timeout = 100)
	public void testFactoryResponseInstanceVariableWeaponType() throws Exception {
		testInstanceVariableOfType(Class.forName(factoryResponsePath), "weapon", Class.forName(weaponPath));

	}

	
	@Test(timeout = 100)
	public void testFactoryResponseInstanceVariableRemainingResourcesIsPresent() throws Exception {
		testInstanceVariableIsPresent(Class.forName(factoryResponsePath), "remainingResources", true);

	}

	@Test(timeout = 1000)
	public void testSetterForInstanceVariableBaseDamageDoesNotExistInWeaponSubClasses() throws Exception {
		String[] subClasses = { piercingCannonPath, sniperCannonPath, volleySpreadCannonPath };
		testSetterAbsentInSubclasses("setDamage", subClasses);
	}

	
	

	@Test(timeout = 1000)
	public void testGetterLogicForInstanceVariableBaseDamageInClassVolleySpreadCannon() throws Exception {
		int baseDamage = (int) (Math.random() * 1000);
		int minRange = (int) (Math.random() * 1000);
		int maxRange = (int) (Math.random() * 1000);

		Constructor<?> constructor = Class.forName(volleySpreadCannonPath).getConstructor(int.class, int.class, int.class);
		Object volley =  constructor.newInstance(baseDamage, minRange, maxRange);

		testGetterLogic(volley, "baseDamage", baseDamage,"getDamage" );
	}

	
	@Test(timeout = 1000)
	public void testInstanceVariableMaxRangeIsPresentInClassVolleySpreadCannon() throws Exception {
		testInstanceVariableIsPresent(Class.forName(volleySpreadCannonPath), "maxRange", true);
	}
	
	
	
	@Test (timeout = 100)
	public void testAttributeWeaponCodeIsFinalInClassVolleySpreadCannon() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field WeaponCodeField = Class.forName(volleySpreadCannonPath).getDeclaredField("WEAPON_CODE");
		assertTrue("The WEAPON_CODE attribute should be final", Modifier.isFinal(WeaponCodeField.getModifiers()));
	}

	@Test(timeout = 1000)
	public void testInstanceVariableWeaponCodeIsPresentInClassWallTrap() throws Exception {
		testInstanceVariableIsPresent(Class.forName(wallTrapPath), "WEAPON_CODE", true);
	}
	
	@Test(timeout = 1000)
	public void testWallTrapIsSubClassOfWeapon() throws Exception {
		testClassIsSubclass(Class.forName(wallTrapPath), Class.forName(weaponPath));
	}


	@Test(timeout = 100)
	public void testVolleySpreadCannonClassExists() {
		try {
			Class.forName(volleySpreadCannonPath);
		} catch (ClassNotFoundException e) {
			fail("missing class VolleySpreadCannon");
		}
	}
	
	
	
	@Test(timeout = 100)
	public void testConstructorWeapon() throws ClassNotFoundException {
		Class[] inputs = { int.class };
		testConstructorExists(Class.forName(weaponPath), inputs);
	}

	
	@Test(timeout = 100)
	public void testGetterLogicForInstanceVariableDistanceFromBaseInClassPureTitan() throws Exception {
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
		Object pureTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,dangerLevel,resourcesValue);
		testGetterLogic(pureTitan, "distanceFromBase", distanceFromBase,"getDistance");
	}

	@Test(timeout = 100)
	public void testConstructorColossalTitan() throws ClassNotFoundException {
		Class[] inputs = { int.class, int.class, int.class,int.class,int.class,int.class,int.class };
		testConstructorExists(Class.forName(ColossalTitanClassPath), inputs);
	}
	@Test(timeout = 100)
	public void testTitanCodePublicAttributeInArmoredClass() throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field titanCodeField = Class.forName(ArmoredTitanClassPath).getDeclaredField("TITAN_CODE");
		assertTrue("The TITAN_CODE attribute should be public", Modifier.isPublic(titanCodeField.getModifiers()));
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
				PriorityQueue<Titan> q = (PriorityQueue<Titan>) f.get(createdObject);
				//System.out.println(q.size());
				assertEquals("The constructor of the " + createdObject.getClass().getSimpleName()
						+ " class should initialize the instance variable \"" + currName
						+ "\" correctly. the size of titans should be equals to the 0 initially.", 
						currValue, q.size());
			}
			else if(currName.equals("weapons")) {
				ArrayList<Weapon> w = (ArrayList<Weapon>) f.get(createdObject);
				//System.out.println(q.size());
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

	private void testGetterMethodLogicForMSG(Object createdObject, String varName, String expectedValue) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException, NoSuchFieldException {		

		String methodName = "";
		if(expectedValue.getClass().equals(boolean.class))
			methodName = "is" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);
		else
			methodName = "get" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);

		
		Method m = createdObject.getClass().getDeclaredMethod(methodName);
		
		assertTrue("The method \"" + methodName
				+ "\" in class Character should return the correct value of variable \"" + varName + "\"."
				, expectedValue.contains(((String)m.invoke(createdObject))));
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
