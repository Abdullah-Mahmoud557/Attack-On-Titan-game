package game.tests;


import org.junit.Test;

import java.lang.reflect.*;
import java.util.*;

import static org.junit.Assert.*;



public class Milestone2PublicTests {

	private String weaponPath = "game.engine.weapons.Weapon";
	private String weaponPiercingCannonPath = "game.engine.weapons.PiercingCannon";
	private String factoryResponsePath = "game.engine.weapons.factory.FactoryResponse";
	private String weaponFactoryPath = "game.engine.weapons.factory.WeaponFactory";
	private String battlePath = "game.engine.Battle";

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



	private String sniperCannonPath = "game.engine.weapons.SniperCannon";
	private String volleySpreadCannonPath = "game.engine.weapons.VolleySpreadCannon";
	private String wallTrapPath = "game.engine.weapons.WallTrap";

	private String titanClassPath = "game.engine.titans.Titan";
	private String PureTitanClassPath = "game.engine.titans.PureTitan";
	private String AbnormalTitanClassPath = "game.engine.titans.AbnormalTitan";
	private String ArmoredTitanClassPath = "game.engine.titans.ArmoredTitan";
	private String ColossalTitanClassPath = "game.engine.titans.ColossalTitan";

	private String titanPath = "game.engine.titans.Titan";
	private String pureTitanPath = "game.engine.titans.PureTitan";
	private String abnormalTitan = "game.engine.titans.AbnormalTitan";
	private String armoredTitan = "game.engine.titans.ArmoredTitan";
	private String colossalTitan = "game.engine.titans.ColossalTitan";


	@Test(timeout=1000)
	public void testUpdatelaneDangerLevelInLaneBattle() {

		try {
			Constructor<?> battleConstructor;
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();

			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Object wall=createWall();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(wall);
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createPureTitan();
			Object titan2= createAbnormalTitan();
			Object titan3= createAbnormalTitan();
			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			lanesPQ.add(laneObject);

			originalLanes.add(laneObject);
			lanesField.set(battle,lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Field dangerLevelFieldTitan= Class.forName(titanClassPath).getDeclaredField("dangerLevel");
			dangerLevelFieldTitan.setAccessible(true);
			int dangerLevel= dangerLevelFieldTitan.getInt(titan)+dangerLevelFieldTitan.getInt(titan2)+dangerLevelFieldTitan.getInt(titan3);



			Method updatelaneDangerLevelMethod= Class.forName(battlePath).getDeclaredMethod("updateLanesDangerLevels",  null);
			updatelaneDangerLevelMethod.setAccessible(true);
			updatelaneDangerLevelMethod.invoke(battle);
			laneObject= ((PriorityQueue<Object>) lanesField.get(battle)).peek();

			Field dangerLevelField= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);

			assertEquals("All active lane danger level should be updated correctly inside a game battle.", dangerLevel,dangerLevelField.get(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}
	@Test(timeout=1000)
	public void testOrderAddTurnTitansAndFinalizeTurnBattlePerformTurn() {
		Constructor<?> battleConstructor;

		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field battlePhase= Class.forName(battlePath).getDeclaredField("battlePhase");
			battlePhase.setAccessible(true);
			battlePhase.set(battle, returnEnumValue(battlePhasePath, "GRUMBLING"));

			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, 34);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			lanesPQ= new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			Object laneObject4 =  constructor.newInstance(createWall());

			int dangerLevel1= returnLaneDangerLevel(laneObject);
			int dangerLevel2= returnLaneDangerLevel(laneObject2);
			int dangerLevel3= returnLaneDangerLevel(laneObject3);

			Field dangerLevelFieldLane= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelFieldLane.setAccessible(true);
			dangerLevelFieldLane.set(laneObject4, 0);
			dangerLevelFieldLane.set(laneObject3, dangerLevel3);
			dangerLevelFieldLane.set(laneObject2, dangerLevel2);
			dangerLevelFieldLane.set(laneObject, dangerLevel1);


			lanesPQ.add(laneObject4);
			lanesPQ.add(laneObject3);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);

			originalLanes.addAll(lanesPQ);

			originalLanesField.set(battle, originalLanes);

			lanesField.set(battle, lanesPQ);
			Object titan= createPureTitan();
			int value=0;
			Field dangerLevelField= Class.forName(titanClassPath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);

			int random6 = (int) (Math.random() * 2) + 100;
			value+=random6;
			dangerLevelField.set(titan,random6 );

			PriorityQueue<Integer> dangerLevel= new PriorityQueue<>();
			dangerLevel.add(dangerLevel1);
			dangerLevel.add(dangerLevel2);
			dangerLevel.add(dangerLevel3);
			dangerLevel.add(random6);

			Field approachingTitans=  Class.forName(battlePath).getDeclaredField("approachingTitans");
			approachingTitans.setAccessible(true);
			approachingTitans.set(battle, new ArrayList<>());
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);

			Method m= Class.forName(battlePath).getDeclaredMethod("performTurn", null);
			m.setAccessible(true);
			m.invoke(battle);
			if(((PriorityQueue<Object>) (lanesField.get(battle))).isEmpty())
				fail("Lanes should not be emptied");
			while(!dangerLevel.isEmpty()) {
				int dl= dangerLevel.poll();
				Object lane=((PriorityQueue<Object>) (lanesField.get(battle))).poll();
				if(lane== null)
					fail("Add turn titans should not remove any lane from the battle lanes");
				int laneDL= dangerLevelFieldLane.getInt(lane);

				assertTrue("PerformTurn should excute the main functionalities with the correct order as mentioned in the game description, "
						+ "to finalize the turn when all the main functionalities have been excuted",laneDL==dl) ;
			}


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException |
				InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout = 1000)
	public void testAttackMethodLogic2(){
		try {
			Class weaponClass = Class.forName(sniperCannonPath);
			Constructor<?> constructor1 = weaponClass.getConstructor(int.class);
			int baseDamage = (int) (Math.random() * 100) + 60; 
			Object weapon = constructor1.newInstance(baseDamage);
			Class armoredTitanClass = Class.forName(armoredTitan);
			Constructor<?> constructor2 = armoredTitanClass.getConstructor(int.class, int.class, int.class, int.class,
					int.class, int.class, int.class);
			int health = (int) (Math.random() * 10) + 1;
			int damage = (int) (Math.random() * 20) + 10; 
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int distanceFromBase = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;
			Object armoredTitan = constructor2.newInstance(health, damage, heightInMeters, distanceFromBase, speed,
					resourcesValue, dangerLevel);

			Method attack = Class.forName(attackerPath).getDeclaredMethod("attack", Class.forName(attackeePath));
			Method getResourcesValue = Class.forName(titanPath).getDeclaredMethod("getResourcesValue");
			Method getCurrentHealth = Class.forName(titanPath).getDeclaredMethod("getCurrentHealth");
			//if the titan is defeated
			int result = (int) attack.invoke(weapon, armoredTitan);
			int expectedHealth = Math.max(0,health - (baseDamage/4));
			int expectedResult = (int) getResourcesValue.invoke(armoredTitan);
			assertEquals("The current health of the titan is wrong",expectedHealth , (int)getCurrentHealth.invoke(armoredTitan));
			assertEquals("The method should return the resources value if the titan is defeated",expectedResult , result);

			// if the titan is not defeated
			baseDamage = (int) (Math.random() * 10) + 1;
			weapon = constructor1.newInstance(baseDamage);
			health = (int) (Math.random() * 20) + 10;
			armoredTitan = constructor2.newInstance(health, damage, heightInMeters, distanceFromBase, speed, resourcesValue,
					dangerLevel);

			result = (int) attack.invoke(weapon, armoredTitan);
			expectedHealth = Math.max(0,health - (baseDamage/4));
			expectedResult = 0;
			assertEquals("The current health of the titan is wrong",expectedHealth , (int)getCurrentHealth.invoke(armoredTitan));
			assertEquals("The method should return 0 if the titan is not defeated",expectedResult , result);

		} catch (ClassNotFoundException| NoSuchMethodException| SecurityException|
				InstantiationException| IllegalAccessException| IllegalArgumentException|
				InvocationTargetException e) {

			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}


	}

	@Test(timeout=1000)
	public void testUpdatelaneDangerLevelLostLanesBattle() {

		try {
			Constructor<?> battleConstructor;
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			originalLanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();





			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());


			int lane1DL= returnLaneDangerLevel(laneObject);
			int lane2DL= returnLaneDangerLevel(laneObject2);
			int lane3DL= returnLaneDangerLevel(laneObject3);



			Field dangerLevelField= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);
			dangerLevelField.set(laneObject, 2);
			dangerLevelField.set(laneObject2, 2);
			dangerLevelField.set(laneObject3, 2);

			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);

			lanesField.set(battle, lanesPQ);

			ArrayList<Object> originalLanes= new ArrayList<>();
			originalLanes.add(laneObject3);
			originalLanes.add(laneObject2);
			originalLanes.add(laneObject);
			originalLanesField.set(battle, originalLanes);
			Method updatelaneDangerLevelMethod= Class.forName(battlePath).getDeclaredMethod("updateLanesDangerLevels",  null);
			updatelaneDangerLevelMethod.setAccessible(true);
			updatelaneDangerLevelMethod.invoke(battle);

			assertEquals("ONLY active lane danger level should be updated correctly inside a game battle.", 2,dangerLevelField.get(laneObject3));
			assertEquals("All active lane danger level should be updated correctly inside a game battle.", lane1DL,dangerLevelField.get(laneObject));
			assertEquals("All active lane danger level should be updated correctly inside a game battle.", lane2DL,dangerLevelField.get(laneObject2));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout=1000)
	public void testUpdatelaneDangerLevelInLaneBattleOrderLanes() {

		try {
			Constructor<?> battleConstructor;
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			Object laneObject4 =  constructor.newInstance(createWall());



			int dl1=returnLaneDangerLevel(laneObject);
			int dl2=returnLaneDangerLevel(laneObject2);
			int dl3=returnLaneDangerLevel(laneObject3);
			int dl4=returnLaneDangerLevel(laneObject4);


			lanesPQ.add(laneObject3);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject4);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			PriorityQueue<Integer> array= new PriorityQueue<>();
			array.add(dl1);
			array.add(dl2);
			array.add(dl3);
			array.add(dl4);

			Field dangerLevelField= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);
			dangerLevelField.set(laneObject, 2);
			dangerLevelField.set(laneObject2, 2);
			dangerLevelField.set(laneObject3, 2);
			dangerLevelField.set(laneObject4, 2);
			lanesField.set(battle, new PriorityQueue<>());

			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject4);
			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject3);
			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject2);
			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject);


			Method updatelaneDangerLevelMethod= Class.forName(battlePath).getDeclaredMethod("updateLanesDangerLevels",  null);
			updatelaneDangerLevelMethod.setAccessible(true);
			updatelaneDangerLevelMethod.invoke(battle);
			lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);
			
			if(lanesPQ.isEmpty()) {
				fail("lanes PriorityQueue should NOT be be emptied when updating danger level");

			}
			while(!lanesPQ.isEmpty())  {
				Object object = lanesPQ.poll();
				int x=array.poll();
				assertTrue("Lanes should be sorted correctly after updating their danger level in the lanes PriorityQueue inside the battle",
						dangerLevelField.get(object).equals(x));
			}

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout=1000)
	public void testMoveTitansBattleAll() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());


			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);

			PriorityQueue<Object> titansPQ2= (PriorityQueue<Object>) titansField.get(laneObject2);
			Object titan2= createAbnormalTitanFixed();
			titansPQ2.add(titan2);
			titansField.set(laneObject2, titansPQ2);

			lanesPQ.add(laneObject2);

			PriorityQueue<Object> titansPQ3= (PriorityQueue<Object>) titansField.get(laneObject3);
			Object titan3= createAbnormalTitanFixed();
			titansPQ.add(titan3);
			titansField.set(laneObject3, titansPQ3);

			lanesPQ.add(laneObject3);


			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);
			lanesField.set(battle, lanesPQ);

			Method moveLaneTitans= Class.forName(battlePath).getDeclaredMethod("moveTitans",  null);
			moveLaneTitans.setAccessible(true);
			moveLaneTitans.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);


			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			assertEquals("ALL active lanes should move their titans in battle",25,titanDistanceField.get(titan2) );
			assertEquals("ALL active lanes should move their titans in battle",25,titanDistanceField.get(titan3) );
			assertEquals("ALL active lanes should move their titans in battle",25,titanDistanceField.get(titan) );


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testMoveTitansBattleOnlyActiveLanes() {
		
		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);
			
			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();
			
			
			
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();
			
			
			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			
			
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			
			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);
			
			lanesPQ.add(laneObject);
			
			PriorityQueue<Object> titansPQ2= (PriorityQueue<Object>) titansField.get(laneObject2);
			Object titan2= createAbnormalTitanFixed();
			titansPQ2.add(titan2);
			titansField.set(laneObject2, titansPQ2);
			
			lanesPQ.add(laneObject2);
			
			PriorityQueue<Object> titansPQ3= (PriorityQueue<Object>) titansField.get(laneObject3);
			Object titan3= createAbnormalTitanFixed();
			titansPQ3.add(titan3);
			titansField.set(laneObject3, titansPQ3);
			
			
			
			originalLanes.add(laneObject3);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);
			
			
			lanesField.set(battle, lanesPQ);
			
			
			Method moveLaneTitans= Class.forName(battlePath).getDeclaredMethod("moveTitans",  null);
			moveLaneTitans.setAccessible(true);
			moveLaneTitans.invoke(battle);
			
			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			
			
			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			
			assertEquals("ALL active lanes should move their titans in battle",25,titanDistanceField.get(titan2) );
			assertEquals("ALL active lanes should move their titans in battle",25,titanDistanceField.get(titan) );
			assertEquals("ONLY active lanes should move their titans in battle",30,titanDistanceField.get(titan3) );
			
			
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testMoveTitansBattleAllExist() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());


			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);

			PriorityQueue<Object> titansPQ2= (PriorityQueue<Object>) titansField.get(laneObject2);
			Object titan2= createAbnormalTitanFixed();
			titansPQ2.add(titan2);
			titansField.set(laneObject2, titansPQ2);

			lanesPQ.add(laneObject2);

			PriorityQueue<Object> titansPQ3= (PriorityQueue<Object>) titansField.get(laneObject3);
			Object titan3= createAbnormalTitanFixed();
			titansPQ.add(titan3);
			titansField.set(laneObject3, titansPQ3);

			lanesPQ.add(laneObject3);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);
			lanesField.set(battle, lanesPQ);

			Method moveLaneTitans= Class.forName(battlePath).getDeclaredMethod("moveTitans",  null);
			moveLaneTitans.setAccessible(true);
			moveLaneTitans.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			assertTrue("ALL active lanes should remain in the game when moving their titans in battle",((PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject));
			assertTrue("ALL active lanes should remain in the game when moving their titans in battle",((PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject2));
			assertTrue("ALL active lanes should remain in the game when moving their titans in battle",((PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject3));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testperformWeaponsAttacksBattle() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);


			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			int value=0;

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			value+=returnResourceGathered(laneObject);
			value+=returnResourceGathered(laneObject2);
			value+=returnResourceGathered(laneObject3);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			lanesField.set(battle, lanesPQ);

			Method performWeaponsAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performWeaponsAttacks",  null);
			performWeaponsAttacksmethod.setAccessible(true);
			assertEquals("Incorrect return value for perform weapon attacks in battle", value, performWeaponsAttacksmethod.invoke(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	@Test(timeout=1000)
	public void testperformWeaponsAttacksBattleResourcesGathered() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			int value=0;

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			value+=returnResourceGathered(laneObject);
			value+=returnResourceGathered(laneObject2);
			value+=returnResourceGathered(laneObject3);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);


			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Field resourcesGatheredField= Class.forName(battlePath).getDeclaredField("resourcesGathered");
			resourcesGatheredField.setAccessible(true);
			int random6 = (int) (Math.random() * 10) + 1; 
			resourcesGatheredField.set(battle, random6);

			Method performWeaponsAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performWeaponsAttacks",  null);
			performWeaponsAttacksmethod.setAccessible(true);
			performWeaponsAttacksmethod.invoke(battle);

			resourcesGatheredField= Class.forName(battlePath).getDeclaredField("resourcesGathered");
			resourcesGatheredField.setAccessible(true);
			assertEquals(" When performing the weapons attacks in battle you should update the resouces gathered correctly", value+random6, resourcesGatheredField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	@Test(timeout=1000)
	public void testperformWeaponsAttacksBattleScore() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			int value=0;

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			value+=returnResourceGathered(laneObject);
			value+=returnResourceGathered(laneObject2);
			value+=returnResourceGathered(laneObject3);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Field scoreField= Class.forName(battlePath).getDeclaredField("score");
			scoreField.setAccessible(true);
			int random6 = (int) (Math.random() * 10) + 1; 
			scoreField.set(battle, random6);

			Method performWeaponsAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performWeaponsAttacks",  null);
			performWeaponsAttacksmethod.setAccessible(true);
			performWeaponsAttacksmethod.invoke(battle);

			scoreField= Class.forName(battlePath).getDeclaredField("score");
			scoreField.setAccessible(true);
			assertEquals(" When performing the weapons attacks in battle you should update the score correctly", value+random6, scoreField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testperformWeaponsAttacksBattleScore2() {
		
		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);
			
			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);
			
			
			
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();
			
			int value=0;
			
			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			
			value+=returnResourceGathered(laneObject);
			value+=returnResourceGathered(laneObject2);
			returnResourceGathered(laneObject3);
			
			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			
			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanes.add(laneObject3);
			originalLanesField.set(battle, originalLanes);
			
			Field scoreField= Class.forName(battlePath).getDeclaredField("score");
			scoreField.setAccessible(true);
			int random6 = (int) (Math.random() * 10) + 1; 
			scoreField.set(battle, random6);
			
			Method performWeaponsAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performWeaponsAttacks",  null);
			performWeaponsAttacksmethod.setAccessible(true);
			performWeaponsAttacksmethod.invoke(battle);
			
			scoreField= Class.forName(battlePath).getDeclaredField("score");
			scoreField.setAccessible(true);
			assertEquals("Only active lanes should preform weapons attack", value+random6, scoreField.get(battle));
			
			
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	



	@Test(timeout=1000)
	public void testperformTitansAttacksBattleResourcesGathered() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

			Field resourcesGatheredField= Class.forName(battlePath).getDeclaredField("resourcesGathered");
			resourcesGatheredField.setAccessible(true);
			int random6 = (int) (Math.random() * 10) + 1; 
			resourcesGatheredField.set(battle, random6);

			Field scoreField= Class.forName(battlePath).getDeclaredField("score");
			scoreField.setAccessible(true);
			int random7 = (int) (Math.random() * 10) + 1; 
			scoreField.set(battle, random7);


			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			int value=0;

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			value+=returnResourceGatheredTitans(laneObject);
			value+=returnResourceGatheredTitans(laneObject2);
			value+=returnResourceGatheredTitans(laneObject3);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			resourcesGatheredField= Class.forName(battlePath).getDeclaredField("resourcesGathered");
			resourcesGatheredField.setAccessible(true);

			Method performTitansAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performTitansAttacks",  null);
			performTitansAttacksmethod.setAccessible(true);
			performTitansAttacksmethod.invoke(battle);
			assertEquals("resources gathered value should not be changed from calling performTitansAttacks In battle.",
					random6, resourcesGatheredField.get(battle));
			assertEquals("Battle score  value should not be changed from calling performTitansAttacks In battle.",
					random7, scoreField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}



	@Test(timeout=1000)
	public void testperformTitansAttacksBattleLaneLost1() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			returnResourceGatheredTitans(laneObject3);
			returnResourceGatheredTitans(laneObject2);
			returnResourceGatheredTitans2(laneObject);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);


			Method performTitansAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performTitansAttacks",  null);
			performTitansAttacksmethod.setAccessible(true);
			performTitansAttacksmethod.invoke(battle);
			assertTrue("All lost lanes should be removed from active lanes in battle",
					!( (PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject3));
			assertTrue("All lost lanes should be removed from active lanes in battle",
					!( (PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject2));
			assertTrue("ONLY lost lanes should be removed from active lanes in battle",
					( (PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testperformTitansAttacksBattleLaneLostOriginalLanes() {
		
		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);
			
			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);
			
			
			
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();
			
			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			
			returnResourceGatheredTitans(laneObject3);
			returnResourceGatheredTitans(laneObject2);
			returnResourceGatheredTitans2(laneObject);
			
			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);
			
			lanesField.set(battle, lanesPQ);
			
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);
			
			
			Method performTitansAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performTitansAttacks",  null);
			performTitansAttacksmethod.setAccessible(true);
			performTitansAttacksmethod.invoke(battle);
			assertTrue("lost lanes should NOT be removed from original lanes in battle",
					( (ArrayList<Object>) originalLanesField.get(battle)).contains(laneObject3));
			assertTrue("lost lanes should NOT be removed from original lanes in battle",
					( (ArrayList<Object>) originalLanesField.get(battle)).contains(laneObject2));
			
			
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testperformTitansAttacksBattle2() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);


			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			int value=0;

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			value+=returnResourceGatheredTitans2(laneObject);
			value+=returnResourceGatheredTitans2(laneObject2);
			value+=returnResourceGatheredTitans2(laneObject3);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);


			Method performTitansAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performTitansAttacks",  null);
			performTitansAttacksmethod.setAccessible(true);
			assertEquals("Incorrect resources gathered value from calling performTitansAttacks.In battle all active lane titans, that has reached the wall, should perfom their attack on their lane wall.", 
					value, performTitansAttacksmethod.invoke(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testfinalizeTurnssetNumberOfTurns() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			random5 = (int) (Math.random() * 10) + 5;
			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, random5);

			Method finalizeTurnsmethod=Class.forName(battlePath).getDeclaredMethod("finalizeTurns",  null);
			finalizeTurnsmethod.setAccessible(true);
			finalizeTurnsmethod.invoke(battle);

			assertEquals("Number of turns should be incremented each time the turn is finalized", random5+1,numberOfTurnsField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testfinalizeTurnssetPhaseINTENSE() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			random5 = 14;
			Field battlePhase= Class.forName(battlePath).getDeclaredField("battlePhase");
			battlePhase.setAccessible(true);
			battlePhase.set(battle, returnEnumValue(battlePhasePath, "EARLY"));

			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, random5);

			Method finalizeTurnsmethod=Class.forName(battlePath).getDeclaredMethod("finalizeTurns",  null);
			finalizeTurnsmethod.setAccessible(true);
			finalizeTurnsmethod.invoke(battle);

			Field phasesField= Class.forName(battlePath).getDeclaredField("battlePhase");
			phasesField.setAccessible(true);

			assertTrue("The battle phase should be updated correctly depending on the number of turns",
					returnEnumValue(battlePhasePath,"INTENSE").equals(phasesField.get(battle)));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testfinalizeTurnssetPhaseGRUMBLING() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);
			Field battlePhase= Class.forName(battlePath).getDeclaredField("battlePhase");
			battlePhase.setAccessible(true);
			battlePhase.set(battle, returnEnumValue(battlePhasePath, "INTENSE"));

			random5 = 29;
			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, random5);

			Field numberOfTitansPerTurnField= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			numberOfTitansPerTurnField.setAccessible(true);
			numberOfTitansPerTurnField.set(battle, random4);

			Method finalizeTurnsmethod=Class.forName(battlePath).getDeclaredMethod("finalizeTurns",  null);
			finalizeTurnsmethod.setAccessible(true);
			finalizeTurnsmethod.invoke(battle);

			Field phasesField= Class.forName(battlePath).getDeclaredField("battlePhase");
			phasesField.setAccessible(true);

			assertTrue("The battle phase should be updated correctly depending on the number of turns",
					returnEnumValue(battlePhasePath,"GRUMBLING").equals(phasesField.get(battle)));


			assertEquals("The number of titans should ONLY be updated correctly when entering defined levels", 
					random4,numberOfTitansPerTurnField.get(battle));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testfinalizeTurnssetPhaseGRUMBLING2() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			random5 = 34;
			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, random5);

			Field numberOfTitansPerTurnField= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			numberOfTitansPerTurnField.setAccessible(true);
			numberOfTitansPerTurnField.set(battle, random4);

			Field phasesField= Class.forName(battlePath).getDeclaredField("battlePhase");
			phasesField.setAccessible(true);
			phasesField.set(battle,returnEnumValue(battlePhasePath, "GRUMBLING"));

			Method finalizeTurnsmethod=Class.forName(battlePath).getDeclaredMethod("finalizeTurns",  null);
			finalizeTurnsmethod.setAccessible(true);
			finalizeTurnsmethod.invoke(battle);

			assertEquals("The number of titans should be updated correctly when entering defined levels", 
					random4*2,numberOfTitansPerTurnField.get(battle));

			assertTrue("The battle phase should be updated correctly depending on the number of turns",
					returnEnumValue(battlePhasePath,"GRUMBLING").equals(phasesField.get(battle)));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testfinalizeTurnsEarly() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			random5 = (int) (Math.random() * 13) + 1; ;
			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, random5);

			Field numberOfTitansPerTurnField= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			numberOfTitansPerTurnField.setAccessible(true);
			numberOfTitansPerTurnField.set(battle, random4);

			Field phasesField= Class.forName(battlePath).getDeclaredField("battlePhase");
			phasesField.setAccessible(true);
			phasesField.set(battle,returnEnumValue(battlePhasePath, "EARLY"));

			Method finalizeTurnsmethod=Class.forName(battlePath).getDeclaredMethod("finalizeTurns",  null);
			finalizeTurnsmethod.setAccessible(true);
			finalizeTurnsmethod.invoke(battle);

			assertEquals("The number of titans should ONLY be updated when entering levels that are divisible by 5 and >30", 
					random4,numberOfTitansPerTurnField.get(battle));

			assertTrue("The battle phase should be updated correctly depending on the number of turns",
					returnEnumValue(battlePhasePath,"EARLY").equals(phasesField.get(battle)));

			assertEquals("Number of turns should be incremented each time the turn is finalized",
					random5+1,numberOfTurnsField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}





	@Test(timeout=1000)
	public void testPassTurnMoveTitans2() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());


			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);

			PriorityQueue<Object> titansPQ2= (PriorityQueue<Object>) titansField.get(laneObject2);
			Object titan2= createAbnormalTitanFixed();
			titansPQ2.add(titan2);
			titansField.set(laneObject2, titansPQ2);

			lanesPQ.add(laneObject2);

			PriorityQueue<Object> titansPQ3= (PriorityQueue<Object>) titansField.get(laneObject3);
			Object titan3= createAbnormalTitanFixed();
			titansPQ.add(titan3);
			titansField.set(laneObject3, titansPQ3);

			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method moveLaneTitans= Class.forName(battlePath).getDeclaredMethod("passTurn",  null);
			moveLaneTitans.setAccessible(true);
			moveLaneTitans.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);


			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			assertEquals("pass turn should perform skipping the turn including  moving the titans in ALL active lanes",25,titanDistanceField.get(titan2) );
			assertEquals("pass turn should perform skipping the turn including  moving the titans in ALL active lanes",25,titanDistanceField.get(titan3) );
			assertEquals("pass turn should perform skipping the turn including  moving the titans in ALL active lanes",25,titanDistanceField.get(titan) );

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testPassTurnperformWeaponsAttacksBattleResourcesGathered() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			int value=0;

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			value+=returnResourceGathered(laneObject);
			value+=returnResourceGathered(laneObject2);
			value+=returnResourceGathered(laneObject3);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Field resourcesGatheredField= Class.forName(battlePath).getDeclaredField("resourcesGathered");
			resourcesGatheredField.setAccessible(true);
			int random6 = (int) (Math.random() * 10) + 1; 
			resourcesGatheredField.set(battle, random6);

			Method performTurnPerformWeaponsAttacksmethod=Class.forName(battlePath).getDeclaredMethod("passTurn",  null);
			performTurnPerformWeaponsAttacksmethod.setAccessible(true);
			performTurnPerformWeaponsAttacksmethod.invoke(battle);

			resourcesGatheredField= Class.forName(battlePath).getDeclaredField("resourcesGathered");
			resourcesGatheredField.setAccessible(true);
			assertEquals("pass turn should perform skipping the turn including  weapons in all lanes carrying out their attacks, ResourcesGathered in battle", value+random6, resourcesGatheredField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}



	@Test(timeout=1000)
	public void testPassTurnPerformTitansAttacksBattleLaneLost() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);


			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			returnResourceGatheredTitans(laneObject3);
			returnResourceGatheredTitans(laneObject2);
			returnResourceGatheredTitans2(laneObject);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method performTurnPerformTitansAttacksmethod=Class.forName(battlePath).getDeclaredMethod("passTurn",  null);
			performTurnPerformTitansAttacksmethod.setAccessible(true);
			performTurnPerformTitansAttacksmethod.invoke(battle);
			assertTrue("pass turn should perform skipping the turn including titans in all lanes carrying out their attacks, only titans that have reached their lane wall",
					!( (PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject3));
			assertTrue("pass turn should perform skipping the turn including titans in all lanes carrying out their attacks, only titans that have reached their lane wall",
					!( (PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject2));
			assertTrue("pass turn should perform skipping the turn including titans in all lanes carrying out their attacks, only titans that have reached their lane wall",
					( (PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	@Test(timeout=1000)
	public void testPassTurnUpdatelaneDangerLevelInLaneBattleOrderLanes() {

		try {
			Constructor<?> battleConstructor;
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);


			Field numberOfTitansPerTurn= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			numberOfTitansPerTurn.setAccessible(true);
			numberOfTitansPerTurn.set(battle, 0);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Object wall=createWall();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			Object laneObject4 =  constructor.newInstance(createWall());



			int dl1=returnLaneDangerLevel(laneObject);
			int dl2=returnLaneDangerLevel(laneObject2);
			int dl3=returnLaneDangerLevel(laneObject3);
			int dl4=returnLaneDangerLevel(laneObject4);


			lanesPQ.add(laneObject3);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject4);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			PriorityQueue<Integer> array= new PriorityQueue<>();
			array.add(dl1);
			array.add(dl2);
			array.add(dl3);
			array.add(dl4);


			Field dangerLevelField= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);
			dangerLevelField.set(laneObject, 2);
			dangerLevelField.set(laneObject2, 2);
			dangerLevelField.set(laneObject3, 2);
			dangerLevelField.set(laneObject4, 2);
			lanesField.set(battle, new PriorityQueue<>());

			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject4);
			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject3);
			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject2);
			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject);


			Method updatelaneDangerLevelMethod= Class.forName(battlePath).getDeclaredMethod("passTurn",  null);
			updatelaneDangerLevelMethod.setAccessible(true);
			updatelaneDangerLevelMethod.invoke(battle);
			lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

			if(lanesPQ.isEmpty()) {
				fail("lanes PriorityQueue should NOT be be empty");

			}
			while(!lanesPQ.isEmpty())  {
				Object object = lanesPQ.poll();
				int x=array.poll();
				assertTrue("pass turn should perform skipping the turn including updating the dangerLevel for each active lane and reorder the priority queue accordingly",
						dangerLevelField.get(object).equals(x));
			}
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout=1000)
	public void testPassTurnFinalizeTurnssetPhaseGRUMBLING2() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			random5 = 34;
			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, random5);

			Field numberOfTitansPerTurnField= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			numberOfTitansPerTurnField.setAccessible(true);
			numberOfTitansPerTurnField.set(battle, random4);

			Field phasesField= Class.forName(battlePath).getDeclaredField("battlePhase");
			phasesField.setAccessible(true);
			phasesField.set(battle,returnEnumValue(battlePhasePath, "GRUMBLING"));

			Method finalizeTurnsmethod=Class.forName(battlePath).getDeclaredMethod("passTurn",  null);
			finalizeTurnsmethod.setAccessible(true);
			finalizeTurnsmethod.invoke(battle);

			assertEquals("pass turn should perform skipping the turn including finalizing the turns with the correct number of titans", 
					random4*2,numberOfTitansPerTurnField.get(battle));

			assertTrue("pass turn should perform skipping the turn including finalizing the turns with the battle phase",
					returnEnumValue(battlePhasePath,"GRUMBLING").equals(phasesField.get(battle)));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testPassTurnFinalizeTurnssetNumberOfTurns() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			random5 = (int) (Math.random() * 10) + 5;
			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, random5);

			Method finalizeTurnsmethod=Class.forName(battlePath).getDeclaredMethod("passTurn",  null);
			finalizeTurnsmethod.setAccessible(true);
			finalizeTurnsmethod.invoke(battle);

			assertEquals("pass turn should perform skipping the turn including finalizing the turns with the correct number of turns", random5+1,numberOfTurnsField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	@Test(timeout=1000)
	public void testPerformTurnMoveTitans() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());


			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);

			PriorityQueue<Object> titansPQ2= (PriorityQueue<Object>) titansField.get(laneObject2);
			Object titan2= createAbnormalTitanFixed();
			titansPQ2.add(titan2);
			titansField.set(laneObject2, titansPQ2);

			lanesPQ.add(laneObject2);

			PriorityQueue<Object> titansPQ3= (PriorityQueue<Object>) titansField.get(laneObject3);
			Object titan3= createAbnormalTitanFixed();
			titansPQ.add(titan3);
			titansField.set(laneObject3, titansPQ3);

			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method moveLaneTitans= Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			moveLaneTitans.setAccessible(true);
			moveLaneTitans.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);


			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			assertEquals("performTurn should perform the main functionalities throughout each turn including moving the titans in ALL active lanes",25,titanDistanceField.get(titan2) );
			assertEquals("performTurn should perform the main functionalities throughout each turn including moving the titans in ALL active lanes",25,titanDistanceField.get(titan3) );
			assertEquals("performTurn should perform the main functionalities throughout each turn including moving the titans in ALL active lanes",25,titanDistanceField.get(titan) );

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testPerformTurnMoveTitans2() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());


			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);

			PriorityQueue<Object> titansPQ2= (PriorityQueue<Object>) titansField.get(laneObject2);
			Object titan2= createAbnormalTitanFixed();
			titansPQ2.add(titan2);
			titansField.set(laneObject2, titansPQ2);

			lanesPQ.add(laneObject2);

			PriorityQueue<Object> titansPQ3= (PriorityQueue<Object>) titansField.get(laneObject3);
			Object titan3= createAbnormalTitanFixed();
			titansPQ.add(titan3);
			titansField.set(laneObject3, titansPQ3);

			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method moveLaneTitans= Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			moveLaneTitans.setAccessible(true);
			moveLaneTitans.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);


			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			assertEquals("performTurn should perform the main functionalities throughout each turn including moving the titans in ALL active lanes",25,titanDistanceField.get(titan2) );
			assertEquals("performTurn should perform the main functionalities throughout each turn including moving the titans in ALL active lanes",25,titanDistanceField.get(titan3) );
			assertEquals("performTurn should perform the main functionalities throughout each turn including moving the titans in ALL active lanes",25,titanDistanceField.get(titan) );

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}



	@Test(timeout=1000)
	public void testPerformTurnPerformWeaponsAttacksBattleScore() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			int value=0;

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			value+=returnResourceGathered(laneObject);
			value+=returnResourceGathered(laneObject2);
			value+=returnResourceGathered(laneObject3);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Field scoreField= Class.forName(battlePath).getDeclaredField("score");
			scoreField.setAccessible(true);
			int random6 = (int) (Math.random() * 10) + 1; 
			scoreField.set(battle, random6);

			Method performTurnPerformWeaponsAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			performTurnPerformWeaponsAttacksmethod.setAccessible(true);
			performTurnPerformWeaponsAttacksmethod.invoke(battle);

			scoreField= Class.forName(battlePath).getDeclaredField("score");
			scoreField.setAccessible(true);
			assertEquals("performTurn should perform the main functionalities throughout each turn including  weapons in all lanes carrying out their attacks, Battle Score", value+random6, scoreField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testperformTitansAttacksBattle() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();

			int value=0;

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			value+=returnResourceGatheredTitans(laneObject);
			value+=returnResourceGatheredTitans(laneObject2);
			returnResourceGatheredTitans(laneObject3);
			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);

			lanesField.set(battle, lanesPQ);
			
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			originalLanes.addAll(lanesPQ);
			originalLanes.add(laneObject3);
			originalLanesField.set(battle, originalLanes);
			
			Method performTitansAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performTitansAttacks",  null);
			performTitansAttacksmethod.setAccessible(true);
			
			assertEquals("Incorrect resources gathered value from calling performTitansAttacks.In battle all active lane titans, that has reached the wall, should perfom their attack on their lane wall.", 
					value, performTitansAttacksmethod.invoke(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout=1000)
	public void testPerformTurnPerformTitansAttacksBattleLaneLost() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);


			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			returnResourceGatheredTitans(laneObject3);
			returnResourceGatheredTitans(laneObject2);
			returnResourceGatheredTitans2(laneObject);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method performTurnPerformTitansAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			performTurnPerformTitansAttacksmethod.setAccessible(true);
			performTurnPerformTitansAttacksmethod.invoke(battle);
			assertTrue("performTurn should perform the main functionalities throughout each turn including titans in all lanes carrying out their attacks, only titans that have reached their lane wall",
					!( (PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject3));
			assertTrue("performTurn should perform the main functionalities throughout each turn including titans in all lanes carrying out their attacks, only titans that have reached their lane wall",
					!( (PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject2));
			assertTrue("performTurn should perform the main functionalities throughout each turn including titans in all lanes carrying out their attacks, only titans that have reached their lane wall",
					( (PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testPerformTurnUpdatelaneDangerLevelInLaneBattleAll() {

		try {
			Constructor<?> battleConstructor;
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			Field turnTitans= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			turnTitans.setAccessible(true);
			turnTitans.set(battle, 0);

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());

			int lane1DL= returnLaneDangerLevel(laneObject);
			int lane2DL= returnLaneDangerLevel(laneObject2);
			int lane3DL= returnLaneDangerLevel(laneObject3);



			Field dangerLevelField= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);
			dangerLevelField.set(laneObject, 2);
			dangerLevelField.set(laneObject2, 2);
			dangerLevelField.set(laneObject3, 2);

			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject3);

			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method performTurnMethod= Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			performTurnMethod.setAccessible(true);
			performTurnMethod.invoke(battle);

			assertEquals("performTurn should perform the main functionalities throughout each turn including updating the dangerLevel for each active lane", lane1DL,dangerLevelField.get(laneObject));
			assertEquals("performTurn should perform the main functionalities throughout each turn including updating the dangerLevel for each active lane", lane2DL,dangerLevelField.get(laneObject2));
			assertEquals("performTurn should perform the main functionalities throughout each turn including updating the dangerLevel for each active lane", lane3DL,dangerLevelField.get(laneObject3));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}
	@Test(timeout=1000)
	public void testPerformTurnUpdatelaneDangerLevelInLaneBattleOrderLanes() {

		try {
			Constructor<?> battleConstructor;
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Field numberOfTitansPerTurn= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			numberOfTitansPerTurn.setAccessible(true);
			numberOfTitansPerTurn.set(battle, 0);

			Object wall=createWall();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			Object laneObject4 =  constructor.newInstance(createWall());


			int dl1=returnLaneDangerLevel(laneObject);
			int dl2=returnLaneDangerLevel(laneObject2);
			int dl3=returnLaneDangerLevel(laneObject3);
			int dl4=returnLaneDangerLevel(laneObject4);


			lanesPQ.add(laneObject3);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject4);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			PriorityQueue<Integer> array= new PriorityQueue<>();
			array.add(dl1);
			array.add(dl2);
			array.add(dl3);
			array.add(dl4);

			Field dangerLevelField= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);
			dangerLevelField.set(laneObject, 2);
			dangerLevelField.set(laneObject2, 2);
			dangerLevelField.set(laneObject3, 2);
			dangerLevelField.set(laneObject4, 2);
			lanesField.set(battle, new PriorityQueue<>());

			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject4);
			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject3);
			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject2);
			((PriorityQueue<Object>)lanesField.get(battle)).add(laneObject);



			Method updatelaneDangerLevelMethod= Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			updatelaneDangerLevelMethod.setAccessible(true);
			updatelaneDangerLevelMethod.invoke(battle);
			lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

			if(lanesPQ.isEmpty()) {
				fail("lanes PriorityQueue should NOT be be empty");

			}
			while(!lanesPQ.isEmpty())  {
				Object object = lanesPQ.poll();
				int x=array.poll();
				assertTrue("performTurn should perform the main functionalities throughout each turn including updating the dangerLevel for each active lane and reorder the priority queue accordingly",
						dangerLevelField.get(object).equals(x));
			}

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout=1000)
	public void testPerformTurnFinalizeTurnssetPhaseGRUMBLING2() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			random5 = 34;
			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, random5);

			Field numberOfTitansPerTurnField= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			numberOfTitansPerTurnField.setAccessible(true);
			numberOfTitansPerTurnField.set(battle, random4);

			Field phasesField= Class.forName(battlePath).getDeclaredField("battlePhase");
			phasesField.setAccessible(true);
			phasesField.set(battle,returnEnumValue(battlePhasePath, "GRUMBLING"));

			Method finalizeTurnsmethod=Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			finalizeTurnsmethod.setAccessible(true);
			finalizeTurnsmethod.invoke(battle);

			assertEquals("performTurn should perform the main functionalities throughout each turn including finalizing the turns with the correct number of titans", 
					random4*2,numberOfTitansPerTurnField.get(battle));

			assertTrue("performTurn should perform the main functionalities throughout each turn including finalizing the turns with the battle phase",
					returnEnumValue(battlePhasePath,"GRUMBLING").equals(phasesField.get(battle)));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testPerformTurnFinalizeTurnssetNumberOfTurns() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			random5 = (int) (Math.random() * 10) + 5;
			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, random5);

			Method finalizeTurnsmethod=Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			finalizeTurnsmethod.setAccessible(true);
			finalizeTurnsmethod.invoke(battle);

			assertEquals("performTurn should perform the main functionalities throughout each turn including finalizing the turns with the correct number of turns", random5+1,numberOfTurnsField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testPerformTurnMoveTitansBeforeAttack() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall2());
			Object laneObject2 =  constructor.newInstance(createWall());


			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= new PriorityQueue<>();
			Object titan= createAbnormalTitanFixed();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 5);
			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);
			titanSpeedField.set(titan, 5);

			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);


			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method performTurn= Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			performTurn.setAccessible(true);
			performTurn.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			Method isLaneLost= Class.forName(lanePath).getDeclaredMethod("isLaneLost", null);
			isLaneLost.setAccessible(true);

			assertTrue("performTurn should perform the main functionalities with the correct order as mentioned in the game description", (boolean)isLaneLost.invoke(laneObject));;

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testPerformTurnMoveTitansBeforeWeaponAttack() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());

			Field weaponField= Class.forName(lanePath).getDeclaredField("weapons");
			weaponField.setAccessible(true);
			ArrayList<Object> weaponArray= (ArrayList<Object>) weaponField.get(laneObject);
			weaponArray.add(createWeaponVolleySpreadCannon());

			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= new PriorityQueue<>();
			Object titan= createAbnormalTitanFixed();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 25);
			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);
			titanSpeedField.set(titan, 5);

			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);


			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method performTurn= Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			performTurn.setAccessible(true);
			performTurn.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			Field health= Class.forName(titanClassPath).getDeclaredField("currentHealth");
			health.setAccessible(true);

			assertTrue("performTurn should perform the main functionalities with the correct order as mentioned in the game description",
					((int)health.get(titan))==80);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testPassTurnMoveTitansBeforeWeaponAttack() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();


			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());

			Field weaponField= Class.forName(lanePath).getDeclaredField("weapons");
			weaponField.setAccessible(true);
			ArrayList<Object> weaponArray= (ArrayList<Object>) weaponField.get(laneObject);
			weaponArray.add(createWeaponVolleySpreadCannon());

			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= new PriorityQueue<>();
			Object titan= createAbnormalTitanFixed();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 25);
			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);
			titanSpeedField.set(titan, 5);

			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);


			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method performTurn= Class.forName(battlePath).getDeclaredMethod("passTurn",  null);
			performTurn.setAccessible(true);
			performTurn.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			Field health= Class.forName(titanClassPath).getDeclaredField("currentHealth");
			health.setAccessible(true);

			assertTrue("passTurn should skip the turn by performing the main functionalities with the correct order as mentioned in the game description",
					((int)health.get(titan))==80);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testPerformTurnWeaponAttackBeforeTitanAttack() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object wall=createWall();
			Object laneObject =  constructor.newInstance(wall);

			Field weaponField= Class.forName(lanePath).getDeclaredField("weapons");
			weaponField.setAccessible(true);
			ArrayList<Object> weaponArray= (ArrayList<Object>) weaponField.get(laneObject);
			weaponArray.add(createWeaponVolleySpreadCannon());

			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= new PriorityQueue<>();
			Object titan= createAbnormalTitanFixed();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 0);

			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);
			titanSpeedField.set(titan, 5);

			Field health= Class.forName(titanClassPath).getDeclaredField("currentHealth");
			health.setAccessible(true);
			health.set(titan, 10);

			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);


			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method performTurn= Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			performTurn.setAccessible(true);
			performTurn.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			Field wallHealth= Class.forName(wallPath).getDeclaredField("currentHealth");
			wallHealth.setAccessible(true);

			assertTrue("performTurn should perform the main functionalities with the correct order as mentioned in the game description",
					((int)wallHealth.get(wall))==50);;

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testPerformTurnWeaponAttackBeforeTitanAttack3() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object wall=createWall();
			Object laneObject =  constructor.newInstance(wall);

			Field weaponField= Class.forName(lanePath).getDeclaredField("weapons");
			weaponField.setAccessible(true);
			ArrayList<Object> weaponArray= (ArrayList<Object>) weaponField.get(laneObject);
			weaponArray.add(createWeaponVolleySpreadCannon());

			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= new PriorityQueue<>();
			Object titan= createAbnormalTitanFixed();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 5);

			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);
			titanSpeedField.set(titan, 5);

			Field health= Class.forName(titanClassPath).getDeclaredField("currentHealth");
			health.setAccessible(true);
			health.set(titan, 10);

			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);


			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Method performTurn= Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			performTurn.setAccessible(true);
			performTurn.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			Field wallHealth= Class.forName(wallPath).getDeclaredField("currentHealth");
			wallHealth.setAccessible(true);

			assertTrue("performTurn should perform the main functionalities with the correct order as mentioned in the game description", titanDistanceField.get(titan).equals(0));

			assertTrue("performTurn should perform the main functionalities with the correct order as mentioned in the game description",
					((int)health.get(titan))==0);
			assertTrue("performTurn should perform the main functionalities with the correct order as mentioned in the game description",
					((int)wallHealth.get(wall))==50);;

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=100)
	public void testAddTurnTitansToLanePrivate() {
		Method m;
		try {
			m = Class.forName(battlePath).getDeclaredMethod("addTurnTitansToLane",null);
			assertTrue("addTurnTitansToLane should be private",Modifier.isPrivate(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=100)
	public void testMoveTitansinBattlePrivate() {
		Method m;
		try {
			m = Class.forName(battlePath).getDeclaredMethod("moveTitans",null);
			assertTrue("moveTitans should be private",Modifier.isPrivate(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=100)
	public void testPerformWeaponsAttacksinBattlePrivate() {
		Method m;
		try {
			m = Class.forName(battlePath).getDeclaredMethod("performWeaponsAttacks",null);
			assertTrue("performWeaponsAttacks should be private",Modifier.isPrivate(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=100)
	public void testPerformTitansAttacksinBattlePrivate() {
		Method m;
		try {
			m = Class.forName(battlePath).getDeclaredMethod("performTitansAttacks",null);
			assertTrue("performTitansAttacks should be private",Modifier.isPrivate(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=100)
	public void testPerformTurninBattlePrivate() {
		Method m;
		try {
			m = Class.forName(battlePath).getDeclaredMethod("performTurn",null);
			assertTrue("performTurn should be private",Modifier.isPrivate(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}




	@Test(timeout=1000)
	public void testOrderAddTurnTitansToLaneBattle() {
		Constructor<?> battleConstructor;

		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

			lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject4 =  constructor.newInstance(createWall());

			int dangerLevel1= returnLaneDangerLevel(laneObject);
			int dangerLevel2= returnLaneDangerLevel(laneObject2);
			while(dangerLevel1==dangerLevel2) {
				dangerLevel2= returnLaneDangerLevel(laneObject2);
			}

			Field dangerLevelFieldLane= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelFieldLane.setAccessible(true);
			dangerLevelFieldLane.set(laneObject4, 0);
			dangerLevelFieldLane.set(laneObject2, dangerLevel2);
			dangerLevelFieldLane.set(laneObject, dangerLevel1);

			PriorityQueue<Object> dangerLevel= new PriorityQueue<>();
			dangerLevel.add(laneObject4);
			dangerLevel.add(laneObject2);
			dangerLevel.add(laneObject);

			lanesPQ.add(laneObject4);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);
			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Object titan= createPureTitan();
			Field dangerLevelField= Class.forName(titanClassPath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);

			int random6 = (int) (Math.random() * 2) + 5;
			dangerLevelField.set(titan,random6 );

			Field approachingTitans=  Class.forName(battlePath).getDeclaredField("approachingTitans");
			approachingTitans.setAccessible(true);
			approachingTitans.set(battle, new ArrayList<>());
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);


			Method m= Class.forName(battlePath).getDeclaredMethod("addTurnTitansToLane", null);
			m.setAccessible(true);
			m.invoke(battle);
			while(!dangerLevel.isEmpty()) {
				Object lane= dangerLevel.poll();
				Object l=((PriorityQueue<Object>) (lanesField.get(battle))).poll();
				if(l== null)
					fail("Add turn titans should not remove any lane from the battle lanes");

				assertTrue("Lanes should be sorted correctly after calling addTurnTitansToLane",lane.equals(l) );
			}


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException |
				InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}
	@Test(timeout=1000)
	public void testOrderAddTurnTitansAndUpdateDLBattlePerformTurn() {
		Constructor<?> battleConstructor;

		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

			lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			Object laneObject4 =  constructor.newInstance(createWall());

			int dangerLevel1= returnLaneDangerLevel(laneObject);
			int dangerLevel2= returnLaneDangerLevel(laneObject2);
			int dangerLevel3= returnLaneDangerLevel(laneObject3);

			Field dangerLevelFieldLane= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelFieldLane.setAccessible(true);
			dangerLevelFieldLane.set(laneObject4, 0);
			dangerLevelFieldLane.set(laneObject3, dangerLevel3);
			dangerLevelFieldLane.set(laneObject2, dangerLevel2);
			dangerLevelFieldLane.set(laneObject, dangerLevel1);


			lanesPQ.add(laneObject4);
			lanesPQ.add(laneObject3);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);
			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Object titan= createPureTitan();
			int value=0;
			Field dangerLevelField= Class.forName(titanClassPath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);

			int random6 = (int) (Math.random() * 2) + 100;
			value+=random6;
			dangerLevelField.set(titan,random6 );

			PriorityQueue<Integer> dangerLevel= new PriorityQueue<>();
			dangerLevel.add(dangerLevel1);
			dangerLevel.add(dangerLevel2);
			dangerLevel.add(dangerLevel3);
			dangerLevel.add(random6);

			Field approachingTitans=  Class.forName(battlePath).getDeclaredField("approachingTitans");
			approachingTitans.setAccessible(true);
			approachingTitans.set(battle, new ArrayList<>());
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);

			Method m= Class.forName(battlePath).getDeclaredMethod("performTurn", null);
			m.setAccessible(true);
			m.invoke(battle);
			while(!dangerLevel.isEmpty()) {
				int dl= dangerLevel.poll();
				Object lane=((PriorityQueue<Object>) (lanesField.get(battle))).poll();
				if(lane== null)
					fail("Add turn titans should not remove any lane from the battle lanes");
				int laneDL= dangerLevelFieldLane.getInt(lane);

				assertTrue("performTurn should perform the main functionalities with the correct order as mentioned in the game description, "
						+ "to add the turn titans first then update the lanes dangerLevel",laneDL==dl) ;
			}


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException |
				InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}
	@Test(timeout=1000)
	public void testOrderAddTurnTitansAndUpdateDLBattlePassTurn() {
		Constructor<?> battleConstructor;

		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

			lanesPQ= new PriorityQueue<>();


			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			Object laneObject4 =  constructor.newInstance(createWall());

			int dangerLevel1= returnLaneDangerLevel(laneObject);
			int dangerLevel2= returnLaneDangerLevel(laneObject2);
			int dangerLevel3= returnLaneDangerLevel(laneObject3);

			Field dangerLevelFieldLane= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelFieldLane.setAccessible(true);
			dangerLevelFieldLane.set(laneObject4, 0);
			dangerLevelFieldLane.set(laneObject3, dangerLevel3);
			dangerLevelFieldLane.set(laneObject2, dangerLevel2);
			dangerLevelFieldLane.set(laneObject, dangerLevel1);


			lanesPQ.add(laneObject4);
			lanesPQ.add(laneObject3);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);
			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Object titan= createPureTitan();
			int value=0;
			Field dangerLevelField= Class.forName(titanClassPath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);

			int random6 = (int) (Math.random() * 2) + 100;
			value+=random6;
			dangerLevelField.set(titan,random6 );

			PriorityQueue<Integer> dangerLevel= new PriorityQueue<>();
			dangerLevel.add(dangerLevel1);
			dangerLevel.add(dangerLevel2);
			dangerLevel.add(dangerLevel3);
			dangerLevel.add(random6);

			Field approachingTitans=  Class.forName(battlePath).getDeclaredField("approachingTitans");
			approachingTitans.setAccessible(true);
			approachingTitans.set(battle, new ArrayList<>());
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);

			Method m= Class.forName(battlePath).getDeclaredMethod("passTurn", null);
			m.setAccessible(true);
			m.invoke(battle);
			while(!dangerLevel.isEmpty()) {
				int dl= dangerLevel.poll();
				Object lane=((PriorityQueue<Object>) (lanesField.get(battle))).poll();
				if(lane== null)
					fail("Add turn titans should not remove any lane from the battle lanes");
				int laneDL= dangerLevelFieldLane.getInt(lane);

				assertTrue("pass turn should perform skipping the turn including performing the main functionalities with the correct order as mentioned in the game description, "
						+ "to add the turn titans first then update the lanes dangerLevel",laneDL==dl) ;
			}


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException |
				InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout=1000)
	public void testOrderAddTurnTitansAndFinalizeTurnBattlePassTurn() {
		Constructor<?> battleConstructor;

		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field battlePhase= Class.forName(battlePath).getDeclaredField("battlePhase");
			battlePhase.setAccessible(true);
			battlePhase.set(battle, returnEnumValue(battlePhasePath, "GRUMBLING"));

			Field numberOfTurnsField= Class.forName(battlePath).getDeclaredField("numberOfTurns");
			numberOfTurnsField.setAccessible(true);
			numberOfTurnsField.set(battle, 34);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

			lanesPQ= new PriorityQueue<>();


			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			Object laneObject4 =  constructor.newInstance(createWall());

			int dangerLevel1= returnLaneDangerLevel(laneObject);
			int dangerLevel2= returnLaneDangerLevel(laneObject2);
			int dangerLevel3= returnLaneDangerLevel(laneObject3);

			Field dangerLevelFieldLane= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelFieldLane.setAccessible(true);
			dangerLevelFieldLane.set(laneObject4, 0);
			dangerLevelFieldLane.set(laneObject3, dangerLevel3);
			dangerLevelFieldLane.set(laneObject2, dangerLevel2);
			dangerLevelFieldLane.set(laneObject, dangerLevel1);


			lanesPQ.add(laneObject4);
			lanesPQ.add(laneObject3);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);
			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Object titan= createPureTitan();
			int value=0;
			Field dangerLevelField= Class.forName(titanClassPath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);

			int random6 = (int) (Math.random() * 2) + 100;
			value+=random6;
			dangerLevelField.set(titan,random6 );

			PriorityQueue<Integer> dangerLevel= new PriorityQueue<>();
			dangerLevel.add(dangerLevel1);
			dangerLevel.add(dangerLevel2);
			dangerLevel.add(dangerLevel3);
			dangerLevel.add(random6);

			Field approachingTitans=  Class.forName(battlePath).getDeclaredField("approachingTitans");
			approachingTitans.setAccessible(true);
			approachingTitans.set(battle, new ArrayList<>());
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);

			Method m= Class.forName(battlePath).getDeclaredMethod("passTurn", null);
			m.setAccessible(true);
			m.invoke(battle);
			while(!dangerLevel.isEmpty()) {
				int dl= dangerLevel.poll();
				Object lane=((PriorityQueue<Object>) (lanesField.get(battle))).poll();
				if(lane== null)
					fail("Add turn titans should not remove any lane from the battle lanes");
				int laneDL= dangerLevelFieldLane.getInt(lane);

				assertTrue("pass turn should perform skipping the turn including performing the main functionalities with the correct order as mentioned in the game description, "
						+ "to finalize the turn when all the main functionalities have been excuted",laneDL==dl) ;
			}


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException |
				InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}
	
	@Test(timeout=1000)
	public void testPerformTurnTitanAttackBeforeAddTitan() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);
			Field turnTitansField= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			turnTitansField.setAccessible(true);
			turnTitansField.set(battle, 1);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object wall=createWall();
			Object laneObject =  constructor.newInstance(wall);

			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			int dl2=returnLaneDangerLevel(laneObject2);
			int dl3= returnLaneDangerLevel(laneObject3);

			Field dangerLevelField= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);
			dangerLevelField.set(laneObject3, dl3);
			dangerLevelField.set(laneObject2, dl2);
			dangerLevelField.set(laneObject, 0);

			Field weaponsField= Class.forName(lanePath).getDeclaredField("weapons");
			weaponsField.setAccessible(true);
			weaponsField.set(laneObject, new ArrayList<>());

			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);


			PriorityQueue<Object> titansPQ= new PriorityQueue<>();
			Object titan= createAbnormalTitanFixed();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 0);

			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);


			lanesField.set(battle, lanesPQ);

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Object titan2= createPureTitan();

			Object titan3= createPureTitan();
			Field baseDamage= Class.forName(titanClassPath).getDeclaredField("baseDamage");
			baseDamage.setAccessible(true);
			baseDamage.set(titan2, 40);
			baseDamage.set(titan3, 40);
			titanDistanceField.set(titan2, 0);
			titanDistanceField.set(titan3, 0);

			Field approachingTitans=  Class.forName(battlePath).getDeclaredField("approachingTitans");
			approachingTitans.setAccessible(true);
			approachingTitans.set(battle, new ArrayList<>());
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan2);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan3);


			Method performTurn= Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			performTurn.setAccessible(true);
			performTurn.invoke(battle);



			assertTrue("performTurn should perform the main functionalities with the correct order as mentioned in the game description."
					+ "Adding the turn titans should be done after performing the titans attacks on their lane.",
					((PriorityQueue<Object>)lanesField.get(battle)).contains(laneObject)	);


			Field wallHealth= Class.forName(wallPath).getDeclaredField("currentHealth");
			wallHealth.setAccessible(true);

			assertTrue("performTurn should perform the main functionalities with the correct order as mentioned in the game description."
					+ "Adding the turn titans should be done after performing the titans attacks on their lane.",
					wallHealth.getInt(wall)>0);



		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testPassTurnTitanAttackBeforeAddTitan() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);
			Field turnTitansField= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			turnTitansField.setAccessible(true);
			turnTitansField.set(battle, 1);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object wall=createWall();
			Object laneObject =  constructor.newInstance(wall);

			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			int dl2=returnLaneDangerLevel(laneObject2);
			int dl3= returnLaneDangerLevel(laneObject3);

			Field dangerLevelField= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);
			dangerLevelField.set(laneObject3, dl3);
			dangerLevelField.set(laneObject2, dl2);
			dangerLevelField.set(laneObject, 0);

			Field weaponsField= Class.forName(lanePath).getDeclaredField("weapons");
			weaponsField.setAccessible(true);
			weaponsField.set(laneObject, new ArrayList<>());

			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);


			PriorityQueue<Object> titansPQ= new PriorityQueue<>();
			Object titan= createAbnormalTitanFixed();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 0);

			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject3);


			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Object titan2= createPureTitan();

			Object titan3= createPureTitan();
			Field baseDamage= Class.forName(titanClassPath).getDeclaredField("baseDamage");
			baseDamage.setAccessible(true);
			baseDamage.set(titan2, 40);
			baseDamage.set(titan3, 40);
			titanDistanceField.set(titan2, 0);
			titanDistanceField.set(titan3, 0);

			Field approachingTitans=  Class.forName(battlePath).getDeclaredField("approachingTitans");
			approachingTitans.setAccessible(true);
			approachingTitans.set(battle, new ArrayList<>());
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan2);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan3);


			Method performTurn= Class.forName(battlePath).getDeclaredMethod("passTurn",  null);
			performTurn.setAccessible(true);
			performTurn.invoke(battle);



			assertTrue("PassTurn should perform the main functionalities with the correct order as mentioned in the game description."
					+ "Adding the turn titans should be done after performing the titans attacks on their lane.",
					((PriorityQueue<Object>)lanesField.get(battle)).contains(laneObject)	);


			Field wallHealth= Class.forName(wallPath).getDeclaredField("currentHealth");
			wallHealth.setAccessible(true);

			assertTrue("PassTurn should perform the main functionalities with the correct order as mentioned in the game description."
					+ "Adding the turn titans should be done after performing the titans attacks on their lane.",
					wallHealth.getInt(wall)>0);



		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}




	@Test(timeout=1000)
	public void testAddTurnTitansOriginalLanes() {
		
		Constructor<?> battleConstructor;

		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

			lanesPQ= new PriorityQueue<>();


			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject3 =  constructor.newInstance(createWall());
			Object laneObject4 =  constructor.newInstance(createWall());

			int dangerLevel1= returnLaneDangerLevel(laneObject);
			int dangerLevel2= returnLaneDangerLevel(laneObject2);
			while(dangerLevel1==dangerLevel2) {
				dangerLevel2= returnLaneDangerLevel(laneObject2);
			}
			PriorityQueue<Object> titansPQ= new PriorityQueue<>();
			titansPQ.add(createAbnormalTitanFixed());

			Field titansLaneField= Class.forName(lanePath).getDeclaredField("titans");
			titansLaneField.setAccessible(true);
			titansLaneField.set(laneObject4, titansPQ);

			Field dangerLevelFieldLane= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelFieldLane.setAccessible(true);
			dangerLevelFieldLane.set(laneObject4, 0);
			dangerLevelFieldLane.set(laneObject3, 0);
			dangerLevelFieldLane.set(laneObject2, dangerLevel2);
			dangerLevelFieldLane.set(laneObject, dangerLevel1);

			PriorityQueue<Object> dangerLevel= new PriorityQueue<>();
			dangerLevel.add(laneObject4);
			dangerLevel.add(laneObject2);
			dangerLevel.add(laneObject);

			lanesPQ.add(laneObject4);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);
			lanesField.set(battle, lanesPQ);
			
			originalLanes.add(laneObject3);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Object titan= createPureTitan();
			Object titan2= createPureTitan();
			Object titan3= createPureTitan();
			Object titan4= createPureTitan();
			Field dangerLevelField= Class.forName(titanClassPath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);

			int random6 = (int) (Math.random() * 2) + 5;
			dangerLevelField.set(titan,random6 );

			Field approachingTitans=  Class.forName(battlePath).getDeclaredField("approachingTitans");
			approachingTitans.setAccessible(true);
			approachingTitans.set(battle, new ArrayList<>());
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan2);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan3);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan4);

			Field numberOfTitansPerTurnField= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			numberOfTitansPerTurnField.setAccessible(true);
			numberOfTitansPerTurnField.set(battle, 2);

			Method m= Class.forName(battlePath).getDeclaredMethod("addTurnTitansToLane", null);
			m.setAccessible(true);
			m.invoke(battle);

			assertFalse("addTurnTitansToLane should add the titans to the least danger ACTIVE lane",
					((PriorityQueue<Object>)titansLaneField.get(laneObject3)).contains(titan));
			assertFalse("addTurnTitansToLane should add the titans to the least danger ACTIVE lane",
					((PriorityQueue<Object>)titansLaneField.get(laneObject3)).contains(titan2));
			assertFalse("addTurnTitansToLane should add the titans to the least danger ACTIVE lane",
					((PriorityQueue<Object>)titansLaneField.get(laneObject3)).contains(titan3));
			assertFalse("addTurnTitansToLane should add the titans to the least danger ACTIVE lane",
					((PriorityQueue<Object>)titansLaneField.get(laneObject3)).contains(titan4));
			assertFalse("addTurnTitansToLane should add the titans to the least danger ACTIVE lane",
					((PriorityQueue<Object>)titansLaneField.get(laneObject3)).contains(titan));
			assertFalse("addTurnTitansToLane should add the titans to the least danger ACTIVE lane",
					((PriorityQueue<Object>)titansLaneField.get(laneObject3)).contains(titan2));




		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException |
				InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testAddTurnTitansToLaneBattlePassTurnOnce() {
		Constructor<?> battleConstructor;

		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 1;
			int random2 = (int) (Math.random() * 10) + 1;
			int random3 = (int) (Math.random() * 10) + 1;
			int random4 = (int) (Math.random() * 10) + 1;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

			lanesPQ= new PriorityQueue<>();


			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Object laneObject2 =  constructor.newInstance(createWall());
			Object laneObject4 =  constructor.newInstance(createWall());

			int dangerLevel1= returnLaneDangerLevel(laneObject);
			int dangerLevel2= returnLaneDangerLevel(laneObject2);
			while(dangerLevel1==dangerLevel2) {
				dangerLevel2= returnLaneDangerLevel(laneObject2);
			}
			PriorityQueue<Object> titansPQ= new PriorityQueue<>();
			titansPQ.add(createAbnormalTitanFixed());

			Field titansLaneField= Class.forName(lanePath).getDeclaredField("titans");
			titansLaneField.setAccessible(true);
			titansLaneField.set(laneObject4, titansPQ);

			Field dangerLevelFieldLane= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelFieldLane.setAccessible(true);
			dangerLevelFieldLane.set(laneObject4, 0);
			dangerLevelFieldLane.set(laneObject2, dangerLevel2);
			dangerLevelFieldLane.set(laneObject, dangerLevel1);

			PriorityQueue<Object> dangerLevel= new PriorityQueue<>();
			dangerLevel.add(laneObject4);
			dangerLevel.add(laneObject2);
			dangerLevel.add(laneObject);

			lanesPQ.add(laneObject4);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);
			lanesField.set(battle, lanesPQ);
			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);

			Object titan= createPureTitan();
			Object titan2= createPureTitan();
			Object titan3= createPureTitan();
			Object titan4= createPureTitan();
			Field dangerLevelField= Class.forName(titanClassPath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);

			int random6 = (int) (Math.random() * 2) + 5;
			dangerLevelField.set(titan,random6 );

			Field approachingTitans=  Class.forName(battlePath).getDeclaredField("approachingTitans");
			approachingTitans.setAccessible(true);
			approachingTitans.set(battle, new ArrayList<>());
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan2);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan3);
			((ArrayList<Object>)approachingTitans.get(battle)).add(titan4);

			Field numberOfTitansPerTurnField= Class.forName(battlePath).getDeclaredField("numberOfTitansPerTurn");
			numberOfTitansPerTurnField.setAccessible(true);
			numberOfTitansPerTurnField.set(battle, 2);

			Method m= Class.forName(battlePath).getDeclaredMethod("passTurn", null);
			m.setAccessible(true);
			m.invoke(battle);

			assertTrue("passTurn should only skip the turn by calling perform turn to excute the main functionalities only once", 
					((ArrayList<Object>)approachingTitans.get(battle)).size()==2);
			assertTrue("passTurn should only skip the turn by calling perform turn to excute the main functionalities only once",
					((PriorityQueue<Object>)titansLaneField.get(laneObject4)).contains(titan));
			assertTrue("passTurn should only skip the turn by calling perform turn to excute the main functionalities only once",
					((PriorityQueue<Object>)titansLaneField.get(laneObject4)).contains(titan2));
			assertFalse("passTurn should only skip the turn by calling perform turn to excute the main functionalities only once", 
					((PriorityQueue<Object>)titansLaneField.get(laneObject4)).contains(titan3));
			assertFalse("passTurn should only skip the turn by calling perform turn to excute the main functionalities only once", 
					((PriorityQueue<Object>)titansLaneField.get(laneObject4)).contains(titan4));
			assertFalse("passTurn should only skip the turn by calling perform turn to excute the main functionalities only once", 
					((ArrayList<Object>)approachingTitans.get(battle)).contains(titan));
			assertFalse("passTurn should only skip the turn by calling perform turn to excute the main functionalities only once", 
					((ArrayList<Object>)approachingTitans.get(battle)).contains(titan2));




		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException |
				InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}





	@Test(timeout = 1000)
	public void testAddTitanLane2() {
		int random = (int) (Math.random() * 1000);
		int baseHealth = (int) (Math.random() * 100);
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		try {
			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall = wallConstructor.newInstance(random);
			Object lane = laneConstructor.newInstance(wall);
			Class<?> laneClass = Class.forName(lanePath);
			Method addTitan = laneClass.getDeclaredMethod("addTitan",Class.forName(titanClassPath));
			addTitan.setAccessible(true);
			addTitan.invoke(lane, abnormalTitan);
			Field f = Class.forName(lanePath).getDeclaredField("titans");
			f.setAccessible(true);

			Class curr = lane.getClass();
			PriorityQueue<Object> t = (PriorityQueue<Object>) f.get(lane);
			assertEquals(
					"The method addTitan(Titan titan) should add the input titan to the priorityQueue of titans."+"The titan",abnormalTitan, t.peek());

		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}


	@Test(timeout = 1000)
	public void testRefillApproachingTitansBattleEARLY() {

		int numberOfTurns = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int titanSpawnDistance = (int) (Math.random() * 10) + 1;
		int initialNumOfLanes = (int) (Math.random() * 10) + 1;
		int initialResourcesPerLane = (int) (Math.random() * 10) + 1;
		try {
			Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
			Object b = constructor.newInstance(numberOfTurns, score, titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			Object early= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "EARLY");
			Field f = null;
			Class<? extends Object> curr = b.getClass();
			f = curr.getDeclaredField("battlePhase");
			f.setAccessible(true);
			f.set(b, early);
			Method m = null;
			m = b.getClass().getDeclaredMethod("refillApproachingTitans");
			m.invoke(b);
			Field f2 = null;
			Class<? extends Object> curr2 = b.getClass();
			f2 = curr.getDeclaredField("approachingTitans");
			f2.setAccessible(true);
			ArrayList<Object> t =(ArrayList<Object>) f2.get(b);
			boolean correct = true;
			if(t.size()!=7) {
				correct=false;}
			else {
				for(int i=0;i<3;i++) {

					if(t.get(i).getClass() != Class.forName(PureTitanClassPath))
						correct=false;

				}
				if(t.get(3).getClass() != Class.forName(AbnormalTitanClassPath))
					correct=false;

				if(t.get(4).getClass() != Class.forName(PureTitanClassPath))
					correct=false;

				if(t.get(5).getClass() != Class.forName(ArmoredTitanClassPath))
					correct=false;

				if(t.get(6).getClass() != Class.forName(ColossalTitanClassPath))
					correct=false;

			}

			assertEquals(
					"The method refillApproachingTitans() should refill the approaching titans based on the titans codes present in the current phase"+"",true, correct);

		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}


	@Test(timeout = 1000)
	public void testRefillApproachingTitansBattleINTENSE(){
		int numberOfTurns = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int titanSpawnDistance = (int) (Math.random() * 10) + 1;
		int initialNumOfLanes = (int) (Math.random() * 10) + 1;
		int initialResourcesPerLane = (int) (Math.random() * 10) + 1;
		try {
			Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);	
			Object b = constructor.newInstance(numberOfTurns, score, titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			Object intense= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "INTENSE");
			Field f = null;
			Class<? extends Object> curr = b.getClass();
			f = curr.getDeclaredField("battlePhase");
			f.setAccessible(true);
			f.set(b, intense);
			Method m = null;
			m = b.getClass().getDeclaredMethod("refillApproachingTitans");
			m.invoke(b);
			Field f2 = null;
			Class<? extends Object> curr2 = b.getClass();
			f2 = curr.getDeclaredField("approachingTitans");
			f2.setAccessible(true);
			ArrayList<Object> t =(ArrayList<Object>) f2.get(b);
			boolean correct = true;
			if(t.size()!=7) {
				correct=false;}
			else {
				for(int i=0;i<3;i++) {

					if(t.get(i).getClass() != Class.forName(AbnormalTitanClassPath))
						correct=false;

				}
				if(t.get(3).getClass() != Class.forName(PureTitanClassPath))
					correct=false;

				if(t.get(4).getClass() != Class.forName(ArmoredTitanClassPath))
					correct=false;

				if(t.get(5).getClass() != Class.forName(ArmoredTitanClassPath))
					correct=false;

				if(t.get(6).getClass() != Class.forName(ColossalTitanClassPath))
					correct=false;

			}

			assertEquals(
					"The method refillApproachingTitans() should refill the approaching titans based on the titans codes present in the current phase"+"",true, correct);

		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}


	@Test(timeout = 1000)
	public void testRefillApproachingTitansBattleEARLY2(){
		int numberOfTurns = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int titanSpawnDistance = (int) (Math.random() * 10) + 1;
		int initialNumOfLanes = (int) (Math.random() * 10) + 1;
		int initialResourcesPerLane = (int) (Math.random() * 10) + 1;
		try {
			Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
			Object b = constructor.newInstance(numberOfTurns, score, titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			Object early= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "EARLY");
			Field f = null;
			Class<? extends Object> curr = b.getClass();
			f = curr.getDeclaredField("battlePhase");
			f.setAccessible(true);
			f.set(b, early);
			Method m = null;
			m = b.getClass().getDeclaredMethod("refillApproachingTitans");
			m.invoke(b);
			Field f2 = null;
			Class<? extends Object> curr2 = b.getClass();
			f2 = curr.getDeclaredField("approachingTitans");
			f2.setAccessible(true);
			ArrayList<Object> t =(ArrayList<Object>) f2.get(b);
			Field f3=null;
			boolean correct =true;
			for(int i=0;i<t.size();i++) {
				f3=t.get(i).getClass().getSuperclass().getDeclaredField("distanceFromBase");
				f3.setAccessible(true);
				if((int)f3.get(t.get(i))!=titanSpawnDistance)
					correct = false;

			}

			assertEquals(
					"The method refillApproachingTitans() should refill the approaching titans with the correct distance."+"",true, correct);

		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}
	@Test(timeout = 1000)
	public void testRefillApproachingTitansBattleINTENSE2(){

		int numberOfTurns = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int titanSpawnDistance = (int) (Math.random() * 10) + 1;
		int initialNumOfLanes = (int) (Math.random() * 10) + 1;
		int initialResourcesPerLane = (int) (Math.random() * 10) + 1;
		try {
			Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
			Object b = constructor.newInstance(numberOfTurns, score, titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			Object intense= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "INTENSE");
			Field f = null;
			Class<? extends Object> curr = b.getClass();
			f = curr.getDeclaredField("battlePhase");
			f.setAccessible(true);
			f.set(b, intense);
			Method m = null;
			m = b.getClass().getDeclaredMethod("refillApproachingTitans");
			m.invoke(b);
			Field f2 = null;
			Class<? extends Object> curr2 = b.getClass();
			f2 = curr.getDeclaredField("approachingTitans");
			f2.setAccessible(true);
			ArrayList<Object> t =(ArrayList<Object>) f2.get(b);
			boolean correct = true;
			Field f3=null;

			for(int i=0;i<t.size();i++) {
				f3=t.get(i).getClass().getSuperclass().getDeclaredField("distanceFromBase");
				f3.setAccessible(true);
				if((int)f3.get(t.get(i))!=titanSpawnDistance)
					correct = false;

			}

			assertEquals(
					"The method refillApproachingTitans() should refill the approaching titans with the correct distance."+"",true, correct);

		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}





	@Test(timeout = 1000)
	public void testAddTurnTitansToLaneBattle(){
		int numberOfTurns = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int titanSpawnDistance = (int) (Math.random() * 10) + 1;
		int initialNumOfLanes = (int) (Math.random() * 10) + 1;
		int initialResourcesPerLane = (int) (Math.random() * 10) + 1;
		int baseHealth = 100;
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		try {
			Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
			Object b = constructor.newInstance(numberOfTurns, score, titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			Class<? extends Object> curr = b.getClass();
			PriorityQueue<Object> l = new PriorityQueue<>();

			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Constructor<?> pureTitanConstructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan = pureTitanConstructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);		
			Constructor<?> abnormalTitanconstructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan =  abnormalTitanconstructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
			Object pureTitan2 = pureTitanConstructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);		
			Object abnormalTitan2 =  abnormalTitanconstructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);

			Object wall1 = wallConstructor.newInstance(1);
			Object lane1 = laneConstructor.newInstance(wall1);
			Object wall2 = wallConstructor.newInstance(2);
			Object lane2 = laneConstructor.newInstance(wall2);
			Object wall3 = wallConstructor.newInstance(3);
			Object lane3 = laneConstructor.newInstance(wall3);
			l.add(lane1);
			l.add(lane2);
			l.add(lane3);
			Object early= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "EARLY");
			ArrayList<Object> appTitans = new ArrayList<>();
			appTitans.add(pureTitan);
			appTitans.add(abnormalTitan);
			appTitans.add(pureTitan2);
			appTitans.add(abnormalTitan2);

			Field f = null;
			f = curr.getDeclaredField("battlePhase");
			f.setAccessible(true);
			f.set(b, early);

			Field f2 = null;
			f2 = curr.getDeclaredField("lanes");
			f2.setAccessible(true);
			f2.set(b, l);



			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			originalLanes.addAll(l);
			originalLanesField.set(b, originalLanes);
			Field f3 = null;
			f3 = curr.getDeclaredField("approachingTitans");
			f3.setAccessible(true);
			f3.set(b, appTitans);


			Field f4= null;
			f4= curr.getDeclaredField("numberOfTitansPerTurn");
			f4.setAccessible(true);
			f4.set(b, 3);

			Method m = null;
			m = b.getClass().getDeclaredMethod("addTurnTitansToLane");
			m.setAccessible(true);
			m.invoke(b);


			PriorityQueue<Object> t =(PriorityQueue<Object>) f2.get(b);
			boolean correct = true;
			Object leastDangerLane = t.poll();

			Class<? extends Object> laneClass = lane1.getClass();
			Field f5= null;
			f5= laneClass.getDeclaredField("titans");
			f5.setAccessible(true);
			PriorityQueue<Object> titans = (PriorityQueue<Object>) f5.get(lane1);

			if(titans.size()!=3)
				correct=false;

			if(titans.poll()!=pureTitan)
				correct=false;

			if(titans.poll()!=pureTitan2)
				correct=false;

			if(titans.poll()!=abnormalTitan)
				correct=false;

			assertEquals(
					"The method addTurnTitansToLane() should add titans from approachingTitans to the least dangerous active lane based on the numberOfTitansPerTurn."+"",true, correct);

		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}}

	@Test(timeout = 1000)
	public void testAddTurnTitansToLaneBattleEmptyApproachingTitans(){

		int numberOfTurns = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int titanSpawnDistance = (int) (Math.random() * 10) + 1;
		int initialNumOfLanes = (int) (Math.random() * 10) + 1;
		int initialResourcesPerLane = (int) (Math.random() * 10) + 1;
		int baseHealth = 100;
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		try {
			Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
			Object b = constructor.newInstance(numberOfTurns, score, titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			Class<? extends Object> curr = b.getClass();
			PriorityQueue<Object> l = new PriorityQueue<>();
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Constructor<?> pureTitanConstructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan = pureTitanConstructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);		
			Object wall1 = wallConstructor.newInstance(1);
			Object lane1 = laneConstructor.newInstance(wall1);
			Object wall2 = wallConstructor.newInstance(2);
			Object lane2 = laneConstructor.newInstance(wall2);
			Object wall3 = wallConstructor.newInstance(3);
			Object lane3 = laneConstructor.newInstance(wall3);
			l.add(lane1);
			l.add(lane2);
			l.add(lane3);
			Object early= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "EARLY");
			ArrayList<Object> appTitans = new ArrayList<>();


			Field f = null;
			f = curr.getDeclaredField("battlePhase");
			f.setAccessible(true);
			f.set(b, early);

			Field f2 = null;
			f2 = curr.getDeclaredField("lanes");
			f2.setAccessible(true);
			f2.set(b, l);

			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			originalLanes.addAll(l);
			originalLanesField.set(b, originalLanes);
			Field f3 = null;
			f3 = curr.getDeclaredField("approachingTitans");
			f3.setAccessible(true);
			f3.set(b, appTitans);

			ArrayList<Object> t =(ArrayList<Object>) f3.get(b);
			Method m = null;
			m = b.getClass().getDeclaredMethod("addTurnTitansToLane");
			m.setAccessible(true);
			try {
				m.invoke(b);}
			catch(Exception e) {
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}
			t =(ArrayList<Object>) f3.get(b);
			boolean correct = true;

			if(t.size()==0)
				correct=false;

			if(t.size()!=6)
				correct=false;

			assertEquals(
					"The method addTurnTitansToLane() should refill approachingTitans."+"",true, correct);
		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}
	@Test(timeout = 1000)
	public void testAddTurnTitansToLaneBattleNotEmptyApproachingTitans(){
		int numberOfTurns = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int titanSpawnDistance = (int) (Math.random() * 10) + 1;
		int initialNumOfLanes = (int) (Math.random() * 10) + 1;
		int initialResourcesPerLane = (int) (Math.random() * 10) + 1;
		int baseHealth = 100;
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		try {
			Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
			Object b = constructor.newInstance(numberOfTurns, score, titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			Class<? extends Object> curr = b.getClass();
			PriorityQueue<Object> l = new PriorityQueue<>();
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Constructor<?> pureTitanConstructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan = pureTitanConstructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);		
			Constructor<?> abnormalTitanconstructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan =  abnormalTitanconstructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
			Object wall1 = wallConstructor.newInstance(1);
			Object lane1 = laneConstructor.newInstance(wall1);
			Object wall2 = wallConstructor.newInstance(2);
			Object lane2 = laneConstructor.newInstance(wall2);
			Object wall3 = wallConstructor.newInstance(3);
			Object lane3 = laneConstructor.newInstance(wall3);
			l.add(lane1);
			l.add(lane2);
			l.add(lane3);
			Object early= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "EARLY");
			ArrayList<Object> appTitans = new ArrayList<>();
			appTitans.add(pureTitan);
			appTitans.add(abnormalTitan);


			Field f = null;
			f = curr.getDeclaredField("battlePhase");
			f.setAccessible(true);
			f.set(b, early);

			Field f2 = null;
			f2 = curr.getDeclaredField("lanes");
			f2.setAccessible(true);
			f2.set(b, l);
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			originalLanes.addAll(l);
			originalLanesField.set(b, originalLanes);

			Field f3 = null;
			f3 = curr.getDeclaredField("approachingTitans");
			f3.setAccessible(true);
			f3.set(b, appTitans);

			ArrayList<Object> t =(ArrayList<Object>) f3.get(b);
			Method m = null;
			m = b.getClass().getDeclaredMethod("addTurnTitansToLane");
			m.setAccessible(true);
			try {
				m.invoke(b);
			}
			catch(Exception e) {
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}

			t =(ArrayList<Object>) f3.get(b);
			boolean correct = true;
			if(t.size()>2)
				correct=false;
			assertEquals(
					"The method addTurnTitansToLane() should not refill approachingTitans if it was not empty."+"",true, correct);
		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}



	}

	@Test(timeout = 1000)
	public void testAddTurnTitansToLaneBattleNotEmptyApproachingTitans2(){
		int numberOfTurns = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int titanSpawnDistance = (int) (Math.random() * 10) + 1;
		int initialNumOfLanes = (int) (Math.random() * 10) + 1;
		int initialResourcesPerLane = (int) (Math.random() * 10) + 1;
		int baseHealth = 100;
		int baseDamage = (int) (Math.random() * 100);
		int heightInMeters = (int) (Math.random() * 5);
		int distanceFromBase = (int) (Math.random() * 5);
		int speed = (int) (Math.random() * 5);
		int dangerLevel = (int) (Math.random() * 5);
		int resourcesValue = (int) (Math.random() * 5);
		try {
			Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
			Object b = constructor.newInstance(numberOfTurns, score, titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			Class<? extends Object> curr = b.getClass();
			PriorityQueue<Object> l = new PriorityQueue<>();
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Constructor<?> pureTitanConstructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan = pureTitanConstructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);		
			Constructor<?> abnormalTitanconstructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan =  abnormalTitanconstructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
			Object pureTitan2 = pureTitanConstructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);		
			Object wall1 = wallConstructor.newInstance(1);
			Object lane1 = laneConstructor.newInstance(wall1);
			Object wall2 = wallConstructor.newInstance(2);
			Object lane2 = laneConstructor.newInstance(wall2);
			Object wall3 = wallConstructor.newInstance(3);
			Object lane3 = laneConstructor.newInstance(wall3);
			l.add(lane1);
			l.add(lane2);
			l.add(lane3);
			Object early= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "EARLY");
			ArrayList<Object> appTitans = new ArrayList<>();
			appTitans.add(pureTitan);
			appTitans.add(abnormalTitan);


			Field f = null;
			f = curr.getDeclaredField("battlePhase");
			f.setAccessible(true);
			f.set(b, early);

			Field numberOfTitansPerTurnField= curr.getDeclaredField("numberOfTitansPerTurn");
			numberOfTitansPerTurnField.setAccessible(true);
			numberOfTitansPerTurnField.set(b, 3);

			Field f2 = null;
			f2 = curr.getDeclaredField("lanes");
			f2.setAccessible(true);
			f2.set(b, l);

			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			originalLanes.addAll(l);
			originalLanesField.set(b, originalLanes);
			Field f3 = null;
			f3 = curr.getDeclaredField("approachingTitans");
			f3.setAccessible(true);
			f3.set(b, appTitans);

			ArrayList<Object> t =(ArrayList<Object>) f3.get(b);
			Method m = null;
			m = b.getClass().getDeclaredMethod("addTurnTitansToLane");
			m.setAccessible(true);
			try {
				m.invoke(b);
			}
			catch(Exception e) {
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}

			t =(ArrayList<Object>) f3.get(b);
			boolean correct = true;
			if(t.size()!=6)
				correct=false;
			assertEquals(
					"The method addTurnTitansToLane() should refill approachingTitans if it became empty."+"",true, correct);
		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}



	}







	@Test(timeout=10000)
	public void testMoveLaneTitansSize() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitan();
			Object titan2= createAbnormalTitan();
			Object titan3= createAbnormalTitan();
			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			titansField.set(laneObject, titansPQ);

			Method moveLaneTitans= Class.forName(lanePath).getDeclaredMethod("moveLaneTitans",  null);
			moveLaneTitans.invoke(laneObject);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			assertTrue("Removing titan while moving lane failure", titansPQ.size()==3);
			assertTrue("Removing titan while moving lane failure", titansPQ.contains(titan3));
			assertTrue("Removing titan while moving lane failure", titansPQ.contains(titan2));
			assertTrue("Removing titan while moving lane failure", titansPQ.contains(titan));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}

	@Test(timeout=1000)
	public void testMoveLaneTitansMovedAll() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);

			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);



			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createPureTitan();
			Object titan2= createAbnormalTitan();
			Object titan3= createAbnormalTitan();
			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			titansField.set(laneObject, titansPQ);
			ArrayList<Integer> distances = new ArrayList<>();
			ArrayList<Integer> speeds = new ArrayList<>();
			ArrayList<Object> titans= new ArrayList<>();

			titans.add(titan);
			titans.add(titan2);
			titans.add(titan3);

			distances.add(titanDistanceField.getInt(titan));
			distances.add(titanDistanceField.getInt(titan2));
			distances.add(titanDistanceField.getInt(titan3));

			speeds.add(titanSpeedField.getInt(titan));
			speeds.add(titanSpeedField.getInt(titan2));
			speeds.add(titanSpeedField.getInt(titan3));

			Method moveLaneTitans= Class.forName(lanePath).getDeclaredMethod("moveLaneTitans",  null);
			moveLaneTitans.invoke(laneObject);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			for (int i = 0; i < speeds.size(); i++) {

				int expected= distances.get(i)-speeds.get(i);

				int actual= ((int)titanDistanceField.get(titans.get(i)));
				assertEquals("ALL titans inside a lane should move when calling moveLaneTitans",expected ,actual);
			}

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}

	@Test(timeout=1000)
	public void testMoveLaneTitansMovedOneStep2() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			Object titan= createAbnormalTitanFixed();
			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 0);

			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);
			titanSpeedField.set(titan, 10);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			Method moveLaneTitans= Class.forName(lanePath).getDeclaredMethod("moveLaneTitans",  null);
			moveLaneTitans.invoke(laneObject);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);


			assertTrue("moveLaneTitans should NOT remove any titan that has already reached their destination",titansPQ.contains(titan));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}

	@Test(timeout=1000)
	public void testMoveLaneTitansMovedOneStep3() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			Object titan= createPureTitan();
			Object titan2= createAbnormalTitan();
			Object titan3= createAbnormalTitan();
			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 5);

			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);
			titanSpeedField.set(titan, 10);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			titansField.set(laneObject, titansPQ);

			Method moveLaneTitans= Class.forName(lanePath).getDeclaredMethod("moveLaneTitans",  null);
			moveLaneTitans.invoke(laneObject);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);


			assertTrue("No Titans should be removed from the queue when moving the titans in a lane",titansPQ.contains(titan));
			assertTrue("No Titans should be removed from the queue when moving the titans in a lane",titansPQ.contains(titan2));
			assertTrue("No Titans should be removed from the queue when moving the titans in a lane",titansPQ.contains(titan3));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}

	@Test(timeout=1000)
	public void testPriorityQueueOrderinLaneTitanMove() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			Object titan= createPureTitan();
			Object titan2= createAbnormalTitan();
			Object titan3= createAbnormalTitan();
			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 30);
			titanDistanceField.set(titan2, 30);
			titanDistanceField.set(titan3, 30);

			ArrayList<Object> array= new ArrayList<>();
			array.add(titan2);
			array.add(titan3);
			array.add(titan);

			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);
			titanSpeedField.set(titan, 5);
			titanSpeedField.set(titan2, 15);
			titanSpeedField.set(titan3, 10);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			titansField.set(laneObject, titansPQ);

			Method moveLaneTitans= Class.forName(lanePath).getDeclaredMethod("moveLaneTitans",  null);
			moveLaneTitans.invoke(laneObject);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			int i =0;
			if(titansPQ.isEmpty()) {
				fail("Titans should be sorted correctly after moving, and no titans should be removed");

			}
			while (!titansPQ.isEmpty()) {
				Object object = titansPQ.poll();
				if(object==null)
					fail("Titans should be sorted correctly after moving, and no titans should be removed");
				assertTrue("Titans should be sorted correctly after moving",object.equals(array.get(i)));
				i++;

			}


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}


	}



	@Test(timeout=1000)
	public void testPerformLaneTitansAttacksHasReachedWall() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 0);


			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			Method performLaneTitansAttacks= Class.forName(lanePath).getDeclaredMethod("performLaneTitansAttacks",  null);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			assertEquals("performLaneTitansAttacks should return the correct gained values from the titans attacks in the lane on the wall", 0,performLaneTitansAttacks.invoke(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}


	@Test(timeout=1000)
	public void testPerformLaneTitansAttacksDefeated() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			Object titan2= createAbnormalTitanFixed();
			Object titan3= createAbnormalTitanFixed();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 0);
			titanDistanceField.set(titan2, 0);
			titanDistanceField.set(titan3, 0);


			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			titansField.set(laneObject, titansPQ);

			Method moveLaneTitans= Class.forName(lanePath).getDeclaredMethod("performLaneTitansAttacks",  null);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			assertEquals("performLaneTitansAttacks should return the correct gained values from the titans attacks in the lane on the wall", -2,moveLaneTitans.invoke(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}

	@Test(timeout=1000)
	public void testPerformLaneTitansAttacksNotReached() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			Object titan2= createAbnormalTitanFixed();
			Object titan3= createAbnormalTitanFixed();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 10);
			titanDistanceField.set(titan2, 20);
			titanDistanceField.set(titan3, 30);


			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			titansField.set(laneObject, titansPQ);

			Method moveLaneTitans= Class.forName(lanePath).getDeclaredMethod("performLaneTitansAttacks",  null);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			assertEquals("performLaneTitansAttacks should return the correct gained values from the titans that reached the wall in the lane",0 ,moveLaneTitans.invoke(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}

	@Test(timeout=1000)
	public void testPerformLaneWeaponAttacksNoWeapons() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			Object titan2= createAbnormalTitanFixed();
			Object titan3= createAbnormalTitanFixed();

			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			titansField.set(laneObject, titansPQ);

			Method weaponAttack= Class.forName(lanePath).getDeclaredMethod("performLaneWeaponsAttacks",  null);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			assertEquals("performLaneWeaponsAttacks should return the correct gained values from the weapons attack",0 ,weaponAttack.invoke(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}
	@Test(timeout=1000)
	public void testPerformLaneWeaponAttacks() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());

			Field weaponField= Class.forName(lanePath).getDeclaredField("weapons");
			weaponField.setAccessible(true);
			ArrayList<Object> weaponArray= (ArrayList<Object>) weaponField.get(laneObject);
			weaponArray.add(createWeaponPiercingCannon());
			weaponArray.add(createWeaponPiercingCannon());
			weaponArray.add(createWeaponPiercingCannon());
			weaponField.set(laneObject, weaponArray);


			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			Object titan2= createAbnormalTitanFixed();
			Object titan3= createAbnormalTitanFixed();

			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			titansField.set(laneObject, titansPQ);

			Method weaponAttack= Class.forName(lanePath).getDeclaredMethod("performLaneWeaponsAttacks",  null);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			assertEquals("performLaneWeaponsAttacks should return the correct gained values from the weapons attack",30 ,weaponAttack.invoke(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}

	@Test(timeout=1000)
	public void testPerformLaneWeaponAttacks2() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());

			Field weaponField= Class.forName(lanePath).getDeclaredField("weapons");
			weaponField.setAccessible(true);
			ArrayList<Object> weaponArray= (ArrayList<Object>) weaponField.get(laneObject);
			weaponArray.add(createWeaponPiercingCannon());
			weaponArray.add(createWeaponPiercingCannon());
			weaponArray.add(createWeaponPiercingCannon());
			weaponField.set(laneObject, weaponArray);


			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitan();
			Object titan2= createAbnormalTitan();
			Object titan3= createAbnormalTitan();

			Field resources= Class.forName(titanClassPath).getDeclaredField("resourcesValue");
			resources.setAccessible(true);
			int returnedR=resources.getInt(titan)+resources.getInt(titan2)+resources.getInt(titan3);
			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			titansField.set(laneObject, titansPQ);

			Method weaponAttack= Class.forName(lanePath).getDeclaredMethod("performLaneWeaponsAttacks",  null);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			assertEquals("performLaneWeaponsAttacks should return the correct gained values from the weapons attack",returnedR ,weaponAttack.invoke(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}


	@Test(timeout=1000)
	public void testIsLaneLostLaneClass1() {

		try {

			Object wall=createWall();

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(wall);

			Field laneWallField= Class.forName(lanePath).getDeclaredField("laneWall");
			laneWallField.setAccessible(true);


			Method isLaneLostMethod= Class.forName(lanePath).getDeclaredMethod("isLaneLost",  null);

			assertTrue("The lane is not lost unless its wall is destroyed ", !(boolean)(isLaneLostMethod.invoke(laneObject)));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}


	@Test(timeout=1000)
	public void testIsLaneLostLaneClass2() {

		try {

			Object wall=createWall();

			Field currentHealthField= Class.forName(wallPath).getDeclaredField("currentHealth");
			currentHealthField.setAccessible(true);
			currentHealthField.set(wall, 0);

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(wall);

			Field laneWallField= Class.forName(lanePath).getDeclaredField("laneWall");
			laneWallField.setAccessible(true);


			Method isLaneLostMethod= Class.forName(lanePath).getDeclaredMethod("isLaneLost",  null);

			assertTrue("The lane is lost only when its wall is destroied ", (boolean)(isLaneLostMethod.invoke(laneObject)));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");

		}

	}



	@Test(timeout=1000)
	public void testUpdatelaneDangerLevelInLaneAllTitans() {

		try {

			Object wall=createWall();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(wall);
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createPureTitan();
			Object titan2= createAbnormalTitan();
			Object titan3= createAbnormalTitan();
			titansPQ.add(titan);
			titansPQ.add(titan2);
			titansPQ.add(titan3);
			Field dangerLevelFieldTitan= Class.forName(titanClassPath).getDeclaredField("dangerLevel");
			dangerLevelFieldTitan.setAccessible(true);
			int dangerLevel= dangerLevelFieldTitan.getInt(titan)+dangerLevelFieldTitan.getInt(titan2)+dangerLevelFieldTitan.getInt(titan3);



			Method updatelaneDangerLevelMethod= Class.forName(lanePath).getDeclaredMethod("updateLaneDangerLevel",  null);
			updatelaneDangerLevelMethod.invoke(laneObject);
			Field dangerLevelField= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);

			assertEquals("The lane danger level is not updated correctly for ALL the titans inside the lane.", dangerLevel,dangerLevelField.get(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}


	//


	@Test(timeout = 1000)
	public void testspawnTitanPureTitanCaseDistance(){
		try{
			int code = 1;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> pureTitanClass = Class.forName(PureTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);


			Class superClass = Class.forName(PureTitanClassPath).getSuperclass();
			Method getDistance = superClass.getDeclaredMethod("getDistance", null);
			assertEquals(input_distance_from_based, getDistance.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanPureTitanCaseSpeed(){
		try{
			int code = 1;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> pureTitanClass = Class.forName(PureTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);


			Class superClass = Class.forName(PureTitanClassPath).getSuperclass();
			Method getSpeed = superClass.getDeclaredMethod("getSpeed", null);
			assertEquals(speed, getSpeed.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanPureTitanCaseResources() {
		try{
			int code = 1;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> pureTitanClass = Class.forName(PureTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);


			Class superClass = Class.forName(PureTitanClassPath).getSuperclass();
			Method getResourcesValue = superClass.getDeclaredMethod("getResourcesValue", null);
			assertEquals(resourcesValue, getResourcesValue.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanPureTitanCaseDangerLevel() {
		try{
			int code = 1;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> pureTitanClass = Class.forName(PureTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);


			Class superClass = Class.forName(PureTitanClassPath).getSuperclass();
			Method getDangerLevel = superClass.getDeclaredMethod("getDangerLevel", null);
			assertEquals(dangerLevel, getDangerLevel.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanAbnormalTitanCaseBaseDamage() {
		try{
			int code = 2;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> abnormalTitanClass = Class.forName(AbnormalTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(AbnormalTitanClassPath).getSuperclass();
			Method getDamage = superClass.getDeclaredMethod("getDamage", null);
			assertEquals(baseDamage, getDamage.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanAbnormalTitanCaseHeightInMeters() {
		try{
			int code = 2;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> abnormalTitanClass = Class.forName(AbnormalTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(AbnormalTitanClassPath).getSuperclass();
			Method getHeightInMeters = superClass.getDeclaredMethod("getHeightInMeters", null);
			assertEquals(heightInMeters, getHeightInMeters.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanAbnormalTitanCaseDistance() {
		try{
			int code = 2;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> abnormalTitanClass = Class.forName(AbnormalTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(AbnormalTitanClassPath).getSuperclass();
			Method getDistance = superClass.getDeclaredMethod("getDistance", null);
			assertEquals(input_distance_from_based, getDistance.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout = 1000)
	public void testspawnTitanAbnormalTitanCaseSpeed() {
		try{
			int code = 2;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> abnormalTitanClass = Class.forName(AbnormalTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(AbnormalTitanClassPath).getSuperclass();

			Method getSpeed = superClass.getDeclaredMethod("getSpeed", null);
			assertEquals(speed, getSpeed.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanArmoredTitanCaseBaseHealth() {
		try{
			int code = 3;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> armoredTitanClass = Class.forName(ArmoredTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(ArmoredTitanClassPath).getSuperclass();
			Method getBaseHealth = superClass.getDeclaredMethod("getBaseHealth", null);
			assertEquals(baseHealth, getBaseHealth.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanArmoredTitanCaseBaseDamage() {
		try{
			int code = 3;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> armoredTitanClass = Class.forName(ArmoredTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(ArmoredTitanClassPath).getSuperclass();
			Method getDamage = superClass.getDeclaredMethod("getDamage", null);
			assertEquals(baseDamage, getDamage.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanArmoredTitanCaseHeightInMeters() {
		try{
			int code = 3;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> armoredTitanClass = Class.forName(ArmoredTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(ArmoredTitanClassPath).getSuperclass();

			Method getHeightInMeters = superClass.getDeclaredMethod("getHeightInMeters", null);
			assertEquals(heightInMeters, getHeightInMeters.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanArmoredTitanCaseDistance() {
		try{
			int code = 3;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> armoredTitanClass = Class.forName(ArmoredTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(ArmoredTitanClassPath).getSuperclass();

			Method getDistance = superClass.getDeclaredMethod("getDistance", null);
			assertEquals(input_distance_from_based, getDistance.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout = 1000)
	public void testspawnTitanColossalTitanCaseDistance() {
		try{
			int code = 4;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> colossalTitanClass = Class.forName(ColossalTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(ColossalTitanClassPath).getSuperclass();
			Method getDistance = superClass.getDeclaredMethod("getDistance", null);
			assertEquals(input_distance_from_based, getDistance.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout = 1000)
	public void testspawnTitanColossalTitanCaseSpeed() {
		try{
			int code = 4;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> colossalTitanClass = Class.forName(ColossalTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(ColossalTitanClassPath).getSuperclass();
			Method getSpeed = superClass.getDeclaredMethod("getSpeed", null);
			assertEquals(speed, getSpeed.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanColossalTitanCaseResources() {
		try{
			int code = 4;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> colossalTitanClass = Class.forName(ColossalTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(ColossalTitanClassPath).getSuperclass();
			Method getResourcesValue = superClass.getDeclaredMethod("getResourcesValue", null);
			assertEquals(resourcesValue, getResourcesValue.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testspawnTitanColossalTitanCaseDangerLevel() {
		try{
			int code = 4;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);
			Class<?> colossalTitanClass = Class.forName(ColossalTitanClassPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);

			Class superClass = Class.forName(ColossalTitanClassPath).getSuperclass();
			Method getDangerLevel = superClass.getDeclaredMethod("getDangerLevel", null);
			assertEquals(dangerLevel, getDangerLevel.invoke(titan, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect Titan code, Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout = 1000)
	public void testspawnTitanDefaultCase()  {
		try{
			int code = 5;
			int baseHealth = (int) (Math.random() * 10) + 1;
			int baseDamage = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;

			int input_distance_from_based = (int) (Math.random() * 10) + 1;

			Class<?> titanRegistryClass = Class.forName(titanRegistryPath);

			Constructor<?> titanRegistryConstructor = titanRegistryClass.getConstructor(int.class, int.class, int.class, int.class, int.class, int.class, int.class);
			Object titanRegistry = titanRegistryConstructor.newInstance(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
			Method spawnTitanMethod = titanRegistryClass.getDeclaredMethod("spawnTitan", int.class);
			Object titan = spawnTitanMethod.invoke(titanRegistry, input_distance_from_based);
			assertNull(titan);
		}catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	@Test(timeout = 1000)
	public void testbuildWeaponWallTrapCaseDamage() {
		try{
			int code = 4;
			int price = (int) (Math.random() * 20) + 1;
			int damage = (int) (Math.random() * 20) + 1;
			int randomNameId = (int) (Math.random() * 5) + 1;
			int max = (int) (Math.random() * 30) + 1;
			int min = (int) (Math.random() * 5) + 1;


			Class<?> weaponRegistryClass = Class.forName(weaponRegistryPath);
			Class<?> wallTrapClass = Class.forName(wallTrapPath);

			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class, int.class, int.class, String.class, int.class, int.class);

			Object weaponRegistry = weaponRegistryConstructor.newInstance(code, price, damage, "Name_" + randomNameId, min, max);
			Method buildWeaponMethod = weaponRegistryClass.getDeclaredMethod("buildWeapon", null);

			Object weapon = buildWeaponMethod.invoke(weaponRegistry, null);

			Class superClass = Class.forName(wallTrapPath).getSuperclass();

			Method getDamage = superClass.getDeclaredMethod("getDamage", null);
			assertEquals(damage, getDamage.invoke(weapon, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon code,Please check the console for the error");

		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout = 1000)
	public void testbuildWeaponVollySpreadCaseMin() {
		try{
			int code = 3;
			int price = (int) (Math.random() * 20) + 1;
			int damage = (int) (Math.random() * 20) + 1;
			int randomNameId = (int) (Math.random() * 5) + 1;
			int max = (int) (Math.random() * 30) + 1;
			int min = (int) (Math.random() * 5) + 1;


			Class<?> weaponRegistryClass = Class.forName(weaponRegistryPath);
			Class<?> volleySpreadClass = Class.forName(volleySpreadCannonPath);

			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class, int.class, int.class, String.class, int.class, int.class);

			Object weaponRegistry = weaponRegistryConstructor.newInstance(code, price, damage, "Name_" + randomNameId, min, max);
			Method buildWeaponMethod = weaponRegistryClass.getDeclaredMethod("buildWeapon", null);

			Object weapon = buildWeaponMethod.invoke(weaponRegistry, null);

			Method getMinRange = volleySpreadClass.getDeclaredMethod("getMinRange", null);
			assertEquals(min, getMinRange.invoke(weapon, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon code,Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testbuildWeaponVollySpreadCaseMax() {
		try{
			int code = 3;
			int price = (int) (Math.random() * 20) + 1;
			int damage = (int) (Math.random() * 20) + 1;
			int randomNameId = (int) (Math.random() * 5) + 1;
			int max = (int) (Math.random() * 30) + 1;
			int min = (int) (Math.random() * 5) + 1;


			Class<?> weaponRegistryClass = Class.forName(weaponRegistryPath);
			Class<?> volleySpreadClass = Class.forName(volleySpreadCannonPath);

			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class, int.class, int.class, String.class, int.class, int.class);

			Object weaponRegistry = weaponRegistryConstructor.newInstance(code, price, damage, "Name_" + randomNameId, min, max);
			Method buildWeaponMethod = weaponRegistryClass.getDeclaredMethod("buildWeapon", null);

			Object weapon = buildWeaponMethod.invoke(weaponRegistry, null);

			Class superClass = Class.forName(volleySpreadCannonPath).getSuperclass();

			Method getMaxRange = volleySpreadClass.getDeclaredMethod("getMaxRange", null);
			assertEquals(max, getMaxRange.invoke(weapon, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon code,Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testbuildWeaponSniperCannonCaseDamage()  {
		try{
			int code = 2;
			int price = (int) (Math.random() * 20) + 1;
			int damage = (int) (Math.random() * 20) + 1;
			int randomNameId = (int) (Math.random() * 5) + 1;
			int max = (int) (Math.random() * 30) + 1;
			int min = (int) (Math.random() * 5) + 1;


			Class<?> weaponRegistryClass = Class.forName(weaponRegistryPath);
			Class<?> sniperCannonClass = Class.forName(sniperCannonPath);

			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class, int.class, int.class, String.class, int.class, int.class);

			Object weaponRegistry = weaponRegistryConstructor.newInstance(code, price, damage, "Name_" + randomNameId, min, max);
			Method buildWeaponMethod = weaponRegistryClass.getDeclaredMethod("buildWeapon", null);

			Object weapon = buildWeaponMethod.invoke(weaponRegistry, null);

			Class superClass = Class.forName(sniperCannonPath).getSuperclass();
			Method getDamage = superClass.getDeclaredMethod("getDamage", null);
			assertEquals(damage, getDamage.invoke(weapon, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon code,Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testbuildWeaponPiercingCannonCaseDamage()  {
		try{
			int code = 1;
			int price = (int) (Math.random() * 20) + 1;
			int damage = (int) (Math.random() * 20) + 1;
			int randomNameId = (int) (Math.random() * 5) + 1;
			int max = (int) (Math.random() * 30) + 1;
			int min = (int) (Math.random() * 5) + 1;


			Class<?> weaponRegistryClass = Class.forName(weaponRegistryPath);
			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class, int.class, int.class, String.class, int.class, int.class);

			Object weaponRegistry = weaponRegistryConstructor.newInstance(code, price, damage, "Name_" + randomNameId, min, max);
			Method buildWeaponMethod = weaponRegistryClass.getDeclaredMethod("buildWeapon", null);

			Object weapon = buildWeaponMethod.invoke(weaponRegistry, null);

			Class superClass = Class.forName(weaponPiercingCannonPath).getSuperclass();
			Method getDamage = superClass.getDeclaredMethod("getDamage", null);
			assertEquals(damage, getDamage.invoke(weapon, null));
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon code,Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testAddWeaponToShopWith2Attributes() {
		try{
			int code = (int) (Math.random() * 20) + 1;
			int price = (int) (Math.random() * 20) + 1;


			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);

			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();

			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class, int.class);
			Object weaponRegistry = weaponRegistryConstructor.newInstance(code, price);


			Method getWeaponShop = weaponFactoryClass.getDeclaredMethod("getWeaponShop");

			HashMap<Integer, Object> weaponShop_expected_shallow = (HashMap<Integer, Object>) getWeaponShop.invoke(weaponFactory);
			HashMap<Integer, Object> weaponShop_expected = new HashMap<Integer, Object>();
			for (Map.Entry<Integer, Object> entry : weaponShop_expected_shallow.entrySet()) {
				weaponShop_expected.put(entry.getKey(), entry.getValue());
			}
			weaponShop_expected.put(code, weaponRegistry);


			Class[] attributes = {int.class, int.class};
			Method addWeaponToShopMethod = weaponFactoryClass.getDeclaredMethod("addWeaponToShop", attributes);
			addWeaponToShopMethod.invoke(weaponFactory, code, price);


			HashMap<Integer, Object> weaponShop_actual_shallow = (HashMap<Integer, Object>) getWeaponShop.invoke(weaponFactory);
			HashMap<Integer, Object> weaponShop_actual = new HashMap<Integer, Object>();
			for (Map.Entry<Integer, Object> entry : weaponShop_actual_shallow.entrySet()) {
				weaponShop_actual.put(entry.getKey(), entry.getValue());
			}

			assertEquals("The 2 hashmaps should have equal size", weaponShop_expected.size(), weaponShop_actual.size());


			for (int key : weaponShop_actual.keySet()) {
				Object curr_actual = weaponShop_actual.get(key);
				Object curr_expected = weaponShop_expected.get(key);
				assertNotNull("The actual weaponShop has an additional key", curr_expected);
				assertTrue("The 2 hashmaps should be equal", compare2WeaponRegistryWith2(curr_actual, curr_expected));
			}
		}catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testAddWeaponToShopWith4Attributes() {
		try{
			int code = (int) (Math.random() * 20) + 1;
			int price = (int) (Math.random() * 20) + 1;
			int damage = (int) (Math.random() * 20) + 1;
			int randomNameId = (int) (Math.random() * 5) + 1;


			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);

			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();

			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class, int.class, int.class, String.class);
			Object weaponRegistry = weaponRegistryConstructor.newInstance(code, price, damage, ("Name_" + randomNameId));


			Method getWeaponShop = weaponFactoryClass.getDeclaredMethod("getWeaponShop");

			HashMap<Integer, Object> weaponShop_expected_shallow = (HashMap<Integer, Object>) getWeaponShop.invoke(weaponFactory);
			HashMap<Integer, Object> weaponShop_expected = new HashMap<Integer, Object>();
			for (Map.Entry<Integer, Object> entry : weaponShop_expected_shallow.entrySet()) {
				weaponShop_expected.put(entry.getKey(), entry.getValue());
			}
			weaponShop_expected.put(code, weaponRegistry);


			Class[] attributes = {int.class, int.class, int.class, String.class};
			Method addWeaponToShopMethod = weaponFactoryClass.getDeclaredMethod("addWeaponToShop", attributes);
			addWeaponToShopMethod.invoke(weaponFactory, code, price, damage, ("Name_" + randomNameId));


			HashMap<Integer, Object> weaponShop_actual_shallow = (HashMap<Integer, Object>) getWeaponShop.invoke(weaponFactory);
			HashMap<Integer, Object> weaponShop_actual = new HashMap<Integer, Object>();
			for (Map.Entry<Integer, Object> entry : weaponShop_actual_shallow.entrySet()) {
				weaponShop_actual.put(entry.getKey(), entry.getValue());
			}

			assertEquals("The 2 hashmaps should have equal size", weaponShop_expected.size(), weaponShop_actual.size());


			for (int key : weaponShop_actual.keySet()) {
				Object curr_actual = weaponShop_actual.get(key);
				Object curr_expected = weaponShop_expected.get(key);
				assertNotNull("The actual weaponShop has an additional key", curr_expected);
				assertTrue("The 2 hashmaps should be equal", compare2WeaponRegistryWith4(curr_actual, curr_expected));
			}
		}catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testBuyWeaponFailedCase()  {
		Class<?> InsufficientResources = null;
		try {
			int code = (int) (Math.random() * 20) + 1;
			int price = (int) (Math.random() * 20) + 1;

			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);
			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();
			InsufficientResources = Class.forName(insufficientResourcesExceptionPath);


			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class , int.class);
			Object weaponRegistry = weaponRegistryConstructor.newInstance(code, price);

			Method getWeaponShop = weaponFactoryClass.getDeclaredMethod("getWeaponShop");
			HashMap<Integer, Object> weaponShop = (HashMap<Integer, Object>) getWeaponShop.invoke(weaponFactory);
			weaponShop.put(code, weaponRegistry);

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			buyWeapon.invoke(weaponFactory, 0, code); // Passing 0 resources intentionally to trigger InsufficientResourcesException
			fail("Expected InsufficientResourcesException was not thrown");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InsufficientResourcesException was not thrown",
					InsufficientResources.isInstance(thrownException));
		} catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testBuyWeaponFailedCaseWithCorrectCoditionAndResourcesNumber(){
		Class<?> InsufficientResources = null;
		int resources = 0;
		try {
			int code = (int) (Math.random() * 4) + 1;
			int price = (int) (Math.random() * 50) + 30;
			resources= (int) (Math.random() * 20) + 5;


			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);
			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();
			InsufficientResources = Class.forName(insufficientResourcesExceptionPath);


			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class , int.class);
			Object weaponRegistry = weaponRegistryConstructor.newInstance(code, price);
			Method getWeaponShop = weaponFactoryClass.getDeclaredMethod("getWeaponShop");
			HashMap<Integer, Object> weaponShop = (HashMap<Integer, Object>) getWeaponShop.invoke(weaponFactory);
			weaponShop.put(code, weaponRegistry);
			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			buyWeapon.invoke(weaponFactory, resources, code); // Passing 0 resources intentionally to trigger InsufficientResourcesException
			fail("Expected InsufficientResourcesException was not thrown");
		} catch (InvocationTargetException e) {
			// Check if the thrown exception is InsufficientResourcesException
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected InsufficientResourcesException was not thrown",
					InsufficientResources.isInstance(thrownException));
			try{
				Field field = thrownException.getClass().getDeclaredField("resourcesProvided");
				field.setAccessible(true);
				Object actualResourcesValue = field.get(thrownException);
				assertEquals("The resources should equal to ", resources,actualResourcesValue);
			}catch(SecurityException| IllegalArgumentException| IllegalAccessException|NoSuchFieldException e2) {
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			}

		} catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout = 1000)
	public void testBuyWeaponPassCaseOtherCodesResources() {
		try{
			int randomNum = (int) (Math.random() * 4) + 1;
			/*should be 1,2,4*/
			int code = (randomNum == 3) ? randomNum + 1 : randomNum;

			int resources = (int) (Math.random() * 200) + 300;


			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);
			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();


			Class<?> weaponRegistryClass = Class.forName(weaponRegistryPath);
			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class, int.class);

			Method getWeaponShop = weaponFactoryClass.getDeclaredMethod("getWeaponShop", null);
			Method getRegistryPrice = weaponRegistryClass.getDeclaredMethod("getPrice", null);
			HashMap<Integer, Object> weaponShop_expected_shallow = (HashMap<Integer, Object>) getWeaponShop.invoke(weaponFactory);
			Object weaponRegistry = weaponShop_expected_shallow.get(code);
			int price = (int) getRegistryPrice.invoke(weaponRegistry, null);

			while (price > resources)
				resources = (int) (Math.random() * 200) + 300;

			Method buildWeaponMethod = weaponRegistryClass.getDeclaredMethod("buildWeapon", null);
			Object weapon = buildWeaponMethod.invoke(weaponRegistry, null);


			int expected_remainig_resources = resources - price;

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			Object factoryResponse = buyWeapon.invoke(weaponFactory, resources, code);

			Class<?> factory_response = Class.forName(factoryResponsePath);
			Method getResources = factory_response.getDeclaredMethod("getRemainingResources", null);

			int actual_remaining_resources = (int) getResources.invoke(factoryResponse, null);

			assertEquals("The number of resources isn't correct", expected_remainig_resources, actual_remaining_resources);
		}catch(InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect Weapon,Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException  e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testBuyWeaponPassCaseCode3Min() {
		try{
			int code = 3;

			int resources = (int) (Math.random() * 200) + 300;


			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);
			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();


			Class<?> weaponRegistryClass = Class.forName(weaponRegistryPath);

			Method getWeaponShop = weaponFactoryClass.getDeclaredMethod("getWeaponShop", null);
			Method getRegistryPrice = weaponRegistryClass.getDeclaredMethod("getPrice", null);
			HashMap<Integer, Object> weaponShop_expected_shallow = (HashMap<Integer, Object>) getWeaponShop.invoke(weaponFactory);
			Object weaponRegistry = weaponShop_expected_shallow.get(code);
			int price = (int) getRegistryPrice.invoke(weaponRegistry, null);

			while (price > resources)
				resources = (int) (Math.random() * 200) + 300;

			Method buildWeaponMethod = weaponRegistryClass.getDeclaredMethod("buildWeapon", null);
			Object weapon = buildWeaponMethod.invoke(weaponRegistry, null);


			Class<?> VolleyweaponClass = Class.forName(volleySpreadCannonPath);
			Method getMinRange = VolleyweaponClass.getDeclaredMethod("getMinRange", null);


			int expected_min_range = (int) getMinRange.invoke(weapon, null);

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			Object factoryResponse = buyWeapon.invoke(weaponFactory, resources, code);

			Class<?> factory_response = Class.forName(factoryResponsePath);
			Method getWeapon = factory_response.getDeclaredMethod("getWeapon", null);


			Object actual_weapon = getWeapon.invoke(factoryResponse, null);
			int actual_min_range = (int) getMinRange.invoke(actual_weapon, null);

			assertEquals("The Weapon's minimum range isn't correct", expected_min_range, actual_min_range);
		}catch(NullPointerException|InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon,Please check the console for the error");
		}
		catch( ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testBuyWeaponPassCaseCode3Max() {
		try{
			int code = 3;

			int resources = (int) (Math.random() * 200) + 300;


			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);
			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();


			Class<?> weaponRegistryClass = Class.forName(weaponRegistryPath);

			Method getWeaponShop = weaponFactoryClass.getDeclaredMethod("getWeaponShop", null);

			Method getRegistryPrice = weaponRegistryClass.getDeclaredMethod("getPrice", null);
			HashMap<Integer, Object> weaponShop_expected_shallow = (HashMap<Integer, Object>) getWeaponShop.invoke(weaponFactory);
			Object weaponRegistry = weaponShop_expected_shallow.get(code);
			int price = (int) getRegistryPrice.invoke(weaponRegistry, null);

			while (price > resources)
				resources = (int) (Math.random() * 200) + 300;

			Method buildWeaponMethod = weaponRegistryClass.getDeclaredMethod("buildWeapon", null);
			Object weapon = buildWeaponMethod.invoke(weaponRegistry, null);


			Class<?> VolleyweaponClass = Class.forName(volleySpreadCannonPath);
			Method getMaxRange = VolleyweaponClass.getDeclaredMethod("getMaxRange", null);


			int expected_max_range = (int) getMaxRange.invoke(weapon, null);

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			Object factoryResponse = buyWeapon.invoke(weaponFactory, resources, code);

			Class<?> factory_response = Class.forName(factoryResponsePath);
			Method getWeapon = factory_response.getDeclaredMethod("getWeapon", null);


			Object actual_weapon = getWeapon.invoke(factoryResponse, null);
			int actual_max_range = (int) getMaxRange.invoke(actual_weapon, null);

			assertEquals("The Weapon's maximum range isn't correct", expected_max_range, actual_max_range);
		}
		catch(NullPointerException|InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon, Please check the console for the error");
		}
		catch( ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout = 1000)
	public void testPurchaseWeaponFailCase() {

		Class<?> InvalidLaneException = null;
		try {
			int numberOfTurns = (int) (Math.random() * 20) + 1;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 20) + 1;
			int initialResourcesPerLane = (int) (Math.random() * 20) + 1;

			int wallHealth = (int)(Math.random()*20)+10001;
			int weaponCode = (int)(Math.random()*4)+1;

			InvalidLaneException = Class.forName(invalidLaneExceptionPath);
			Class laneClass = Class.forName(lanePath);

			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns,score,titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);

			Class [] attributes = {int.class,laneClass};
			Method purchase_weapon = battleClass.getDeclaredMethod("purchaseWeapon", attributes);

			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall = wallConstructor.newInstance(wallHealth);

			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);
			Object lane = laneConstructor.newInstance(wall);
			purchase_weapon.invoke(battle,weaponCode,lane); 
			fail("Expected InvalidLaneException was not thrown");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected invalidLaneException was not thrown",
					InvalidLaneException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}	
	@Test(timeout = 1000)
	public void testPurchaseWeaponFailCase2() {
		
		Class<?> InvalidLaneException = null;
		try {
			int numberOfTurns = (int) (Math.random() * 20) + 1;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 20) + 1;
			int initialResourcesPerLane = (int) (Math.random() * 20) + 1;
			
			int wallHealth = (int)(Math.random()*20)+10001;
			int weaponCode = (int)(Math.random()*4)+1;
			
			InvalidLaneException = Class.forName(invalidLaneExceptionPath);
			Class laneClass = Class.forName(lanePath);
			
			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns,score,titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			
			Class [] attributes = {int.class,laneClass};
			Method purchase_weapon = battleClass.getDeclaredMethod("purchaseWeapon", attributes);
			
			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall = wallConstructor.newInstance(wallHealth);
			
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);
			Object lane = laneConstructor.newInstance(wall);
			
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();
			originalLanes.add(lane);
			originalLanesField.set(battle, originalLanes);
			
			purchase_weapon.invoke(battle,weaponCode,lane); 
			fail("Expected InvalidLaneException was not thrown");
		} catch (InvocationTargetException e) {
			Throwable thrownException = e.getTargetException();
			assertNotNull("Expected exception was thrown", thrownException);
			assertTrue("Expected invalidLaneException was not thrown",
					InvalidLaneException.isInstance(thrownException));
		}
		catch(ClassNotFoundException| NoSuchMethodException| NoSuchFieldException|SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}	


	@Test(timeout = 1000)
	public void testPurchaseWeaponSameArrayListContent() {

		try{
			int numberOfTurns = (int) (Math.random() * 20) + 1;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 200) + 1000;
			int initialResourcesPerLane = (int) (Math.random() * 200) + 1000;

			int randomNum = (int) (Math.random() * 4) + 1;

			int weaponCode = (randomNum == 3) ? randomNum + 1 : randomNum;

			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall = wallConstructor.newInstance(10000);

			Class laneClass = Class.forName(lanePath);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);
			Object lane = laneConstructor.newInstance(wall);

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 0, 15, 15, 2);
			Method addT = laneClass.getDeclaredMethod("addTitan", Class.forName(titanClassPath));
			addT.invoke(lane, abnormalTitan1);


			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class, int.class, int.class, int.class, int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns, score, titanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);



			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);
			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();

			Class<?> factoryResponseClass = Class.forName(factoryResponsePath);

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			Method getWeapon = factoryResponseClass.getDeclaredMethod("getWeapon", null);
			Object factoryResponse = buyWeapon.invoke(weaponFactory, (initialResourcesPerLane * initialNumOfLanes), weaponCode);
			Object purchasedWeapon = getWeapon.invoke(factoryResponse, null);
			Method getWeapons = laneClass.getDeclaredMethod("getWeapons", null);
			ArrayList<Object> weaponsShallowExpected = (ArrayList<Object>) getWeapons.invoke(lane, null);
			ArrayList<Object> weaponsDeepColExpected = new ArrayList<>();
			for (Object weapon : weaponsShallowExpected)
				weaponsDeepColExpected.add(weapon);
			weaponsDeepColExpected.add(purchasedWeapon);


			Method getLanes = battleClass.getDeclaredMethod("getLanes", null);
			PriorityQueue<Object> lanes = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			lanes.add(lane);
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			originalLanes.addAll(lanes);
			originalLanesField.set(battle, originalLanes);
//			
			Class[] attributes = {int.class, laneClass};
			Method purchase_weapon = battleClass.getDeclaredMethod("purchaseWeapon", attributes);
			purchase_weapon.invoke(battle, weaponCode, lane);


			ArrayList<Object> weaponsShallowActual = (ArrayList<Object>) getWeapons.invoke(lane, null);
			ArrayList<Object> weaponsDeepColActual = new ArrayList<>();
			for (Object weapon : weaponsShallowActual)
				weaponsDeepColActual.add(weapon);

			boolean equalContent = false;
			for (int i = 0; i < weaponsDeepColActual.size(); i++) {
				equalContent = compare2Weapons(weaponsDeepColActual.get(i), weaponsDeepColExpected.get(i));
			}

			assertTrue("The 2 arrayLists should have the same weapons ", equalContent);
		}catch(InvocationTargetException|NoSuchFieldException e1) {
			e1.printStackTrace();
			fail("Incorrect Weapon,Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testPurchaseWeaponSameArrayListContentVC() {

		try{
			int numberOfTurns = (int) (Math.random() * 20) + 1;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 200) + 1000;
			int initialResourcesPerLane = (int) (Math.random() * 200) + 1000;

			int weaponCode = 3;

			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall = wallConstructor.newInstance(10000);

			Class laneClass = Class.forName(lanePath);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);
			Object lane = laneConstructor.newInstance(wall);


			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class, int.class, int.class, int.class, int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns, score, titanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);



			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);
			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();

			Class<?> factoryResponseClass = Class.forName(factoryResponsePath);

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			Method getWeapon = factoryResponseClass.getDeclaredMethod("getWeapon", null);
			Object factoryResponse = buyWeapon.invoke(weaponFactory, (initialResourcesPerLane * initialNumOfLanes), weaponCode);
			Object purchasedWeapon = getWeapon.invoke(factoryResponse, null);
			Method getWeapons = laneClass.getDeclaredMethod("getWeapons", null);
			ArrayList<Object> weaponsShallowExpected = (ArrayList<Object>) getWeapons.invoke(lane, null);
			ArrayList<Object> weaponsDeepColExpected = new ArrayList<>();
			for (Object weapon : weaponsShallowExpected)
				weaponsDeepColExpected.add(weapon);
			weaponsDeepColExpected.add(purchasedWeapon);


			Method getLanes = battleClass.getDeclaredMethod("getLanes", null);
			PriorityQueue<Object> lanes = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			lanes.add(lane);
			
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			originalLanes.addAll(lanes);
			originalLanesField.set(battle, originalLanes);
//			

			Class[] attributes = {int.class, laneClass};
			Method purchase_weapon = battleClass.getDeclaredMethod("purchaseWeapon", attributes);
			purchase_weapon.invoke(battle, weaponCode, lane);


			ArrayList<Object> weaponsShallowActual = (ArrayList<Object>) getWeapons.invoke(lane, null);
			ArrayList<Object> weaponsDeepColActual = new ArrayList<>();
			for (Object weapon : weaponsShallowActual)
				weaponsDeepColActual.add(weapon);

			boolean equalContent = false;
			for (int i = 0; i < weaponsDeepColActual.size(); i++) {
				equalContent = compare2WeaponsVC(weaponsDeepColActual.get(i), weaponsDeepColExpected.get(i));
			}

			assertTrue("The 2 arrayLists should have the same weapons ", equalContent);
		}catch(InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon,Please check the console for the error");

		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testPurchaseWeaponRemainResources() {

		try{
			int numberOfTurns = (int) (Math.random() * 20) + 1;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 200) + 1000;
			int initialResourcesPerLane = (int) (Math.random() * 200) + 1000;

			int weaponCode = (int) (Math.random() * 4) + 1;

			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall = wallConstructor.newInstance(10000);

			Class laneClass = Class.forName(lanePath);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);
			Object lane = laneConstructor.newInstance(wall);

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 30, 15, 15, 2);
			Method addT = laneClass.getDeclaredMethod("addTitan", Class.forName(titanClassPath));
			addT.invoke(lane, abnormalTitan1);

			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class, int.class, int.class, int.class, int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns, score, titanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);



			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);
			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();

			Class<?> factoryResponseClass = Class.forName(factoryResponsePath);

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			Method getRemainingResources = factoryResponseClass.getDeclaredMethod("getRemainingResources", null);
			Object factoryResponse = buyWeapon.invoke(weaponFactory, (initialResourcesPerLane * initialNumOfLanes), weaponCode);
			int remainingResourcesExpected = (int) getRemainingResources.invoke(factoryResponse, null);


			Method getLanes = battleClass.getDeclaredMethod("getLanes", null);
			PriorityQueue<Object> lanes = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			lanes.add(lane);
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			originalLanes.addAll(lanes);
			originalLanesField.set(battle, originalLanes);
//			
			Class[] attributes = {int.class, laneClass};
			Method purchase_weapon = battleClass.getDeclaredMethod("purchaseWeapon", attributes);
			purchase_weapon.invoke(battle, weaponCode, lane);
			Method getResourcesGathered = battleClass.getDeclaredMethod("getResourcesGathered", null);
			int remainingResourcesActual = (int) getResourcesGathered.invoke(battle, null);
			assertEquals("The resources gathered should be equal to" + remainingResourcesExpected, remainingResourcesExpected, remainingResourcesActual);
		}catch(InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect Weapon,Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException|NoSuchFieldException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	@Test(timeout = 1000)
	public void testPurchaseWeaponPerformedTurnCalled() {

		try{
			int numberOfTurns = 14;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 200) + 1000;
			int initialResourcesPerLane = (int) (Math.random() * 200) + 1000;



			int weaponCode = (int) (Math.random() * 4) + 1;

			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall = wallConstructor.newInstance(10000);

			Class laneClass = Class.forName(lanePath);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);
			Object lane = laneConstructor.newInstance(wall);

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 0, 15, 15, 2);
			Method addT = laneClass.getDeclaredMethod("addTitan", Class.forName(titanClassPath));
			addT.invoke(lane, abnormalTitan1);


			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class, int.class, int.class, int.class, int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns, score, titanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);

			Method getLanes = battleClass.getDeclaredMethod("getLanes", null);
			PriorityQueue<Object> lanes = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			lanes.add(lane);
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			originalLanes.addAll(lanes);
			originalLanesField.set(battle, originalLanes);
//			
			Class[] attributes = {int.class, laneClass};
			Method purchase_weapon = battleClass.getDeclaredMethod("purchaseWeapon", attributes);

			Method getBattlePhase = battleClass.getDeclaredMethod("getBattlePhase", null);

			purchase_weapon.invoke(battle, weaponCode, lane);

			Object getBattlePhaseActual = getBattlePhase.invoke(battle, null);

			assertEquals("The battle phase should be " + getBattlePhaseActual, getBattlePhaseActual, (Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "INTENSE")));
		}catch(InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon,Please check the console for the error");

		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout=1000)
	public void testPurchaseWeaponPerformedTurnCalledInCorrectSequence() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int numberOfTurns = (int) (Math.random() * 20) + 1;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 200) + 1000;
			int initialResourcesPerLane = (int) (Math.random() * 200) + 1000;

			Object battle = battleConstructor.newInstance(numberOfTurns,score,titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);


			int baseHealth = 100;
			int baseDamage = 20;
			int heightInMeters =5;
			int distanceFromBase = 30;
			int speed = 5;
			int dangerLevel = 5;
			int resourcesValue = 10;

			Constructor<?> constructor_titan = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan =  constructor_titan.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);

			Field lanesField= Class.forName(battlePath).getDeclaredField("lanes");
			lanesField.setAccessible(true);
			PriorityQueue<Object> lanesPQ= new PriorityQueue<>();
			
			

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());


			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			PriorityQueue<Object> titansPQ= new PriorityQueue<>();

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(abnormalTitan, 25);
			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);
			titanSpeedField.set(abnormalTitan, 5);

			titansPQ.add(abnormalTitan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);

			lanesField.set(battle, lanesPQ);
			
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);
//			

			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);
			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();

			Class<?> factoryResponseClass = Class.forName(factoryResponsePath);

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			Method getWeapon = factoryResponseClass.getDeclaredMethod("getWeapon",null);

			Object factoryResponse = buyWeapon.invoke(weaponFactory, (initialResourcesPerLane * initialNumOfLanes), 3);
			Object purchasedWeapon  = getWeapon.invoke(factoryResponse,null);


			Class<?> weaponClass= Class.forName(weaponPath);
			Method getDamage = weaponClass.getDeclaredMethod("getDamage", null);
			int damage = (int) getDamage.invoke(purchasedWeapon,null);



			Class laneClass = Class.forName(lanePath);

			Field originalHealth= Class.forName(titanClassPath).getDeclaredField("currentHealth");
			originalHealth.setAccessible(true);
			int originalHealthValue = (int)originalHealth.get(abnormalTitan);

			Class [] attributes = {int.class,laneClass};
			Method purchase_weapon = Class.forName(battlePath).getDeclaredMethod("purchaseWeapon", attributes);
			purchase_weapon.invoke(battle,3,laneObject);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			Field health= Class.forName(titanClassPath).getDeclaredField("currentHealth");
			health.setAccessible(true);

			if((originalHealthValue - damage)<0)
				assertTrue("purchase weapon should perform the main functionalities with the correct order",
						((int)health.get(abnormalTitan))==0);
			else
				assertTrue("purchase weapon should perform the main functionalities with the correct order",
						((int)health.get(abnormalTitan))==(originalHealthValue - damage));

		}catch(NullPointerException| InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon, Please check the console for the error");

		} 
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException |
				InstantiationException | IllegalAccessException | IllegalArgumentException  | NoSuchFieldException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testGameOverLogicTrueCase() {

		try{
			int numberOfTurns = (int) (Math.random() * 20) + 1;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 200) + 1000;
			int initialResourcesPerLane = (int) (Math.random() * 200) + 1000;

			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class, int.class, int.class, int.class, int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns, score, titanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);

			Method isGameOver = battleClass.getDeclaredMethod("isGameOver", null);
			Method getLanes = battleClass.getDeclaredMethod("getLanes", null);
			PriorityQueue<Object> lanes = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			lanes.clear();
			Field originalLanesField= Class.forName(battlePath).getDeclaredField("originalLanes");
			originalLanesField.setAccessible(true);
			ArrayList<Object> originalLanes= new ArrayList<>();

//			originalLanes.addAll(lanesPQ);
			originalLanesField.set(battle, originalLanes);
//			
			boolean isGameOverOutput = (boolean) isGameOver.invoke(battle);
			assertTrue("Expected True but was " + isGameOverOutput, isGameOverOutput);
		}
		catch( ClassNotFoundException| NoSuchMethodException| NoSuchFieldException|IllegalAccessException|InvocationTargetException| InstantiationException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testGameOverLogicNoChangeInLanesContent() {

		try{
			Class LaneClass = Class.forName(lanePath);

			int numberOfTurns = (int) (Math.random() * 20) + 1;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 200) + 1000;
			int initialResourcesPerLane = (int) (Math.random() * 200) + 1000;

			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class, int.class, int.class, int.class, int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns, score, titanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);

			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 0, 15, 15, 2);
			Object abnormalTitan2 =  constructor.newInstance(100, 20, 10, 0, 15, 15, 2);


			Method isGameOver = battleClass.getDeclaredMethod("isGameOver", null);
			Method getLanes = battleClass.getDeclaredMethod("getLanes", null);
			PriorityQueue<Object> lanesBeforeInvokation = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			PriorityQueue<Object> lanesBeforeInvokationDeep = new PriorityQueue<>();
			Method addT = LaneClass.getDeclaredMethod("addTitan", Class.forName(titanClassPath));
			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Class laneClass = Class.forName(lanePath);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);
			Method getCurH = wallClass.getDeclaredMethod("getCurrentHealth", null);
			Method getWall = LaneClass.getDeclaredMethod("getLaneWall", null);

			for(Object l: lanesBeforeInvokation) {
				Object l_wall = getWall.invoke(l, null);
				int currH= (int) getCurH.invoke(l_wall, null);
				Object wall = wallConstructor.newInstance(currH);
				Object lane = laneConstructor.newInstance(wall);
				lanesBeforeInvokationDeep.add(lane);
			}
			for(Object l: lanesBeforeInvokation) {
				addT.invoke(l, abnormalTitan1);
				addT.invoke(l, abnormalTitan2);
			}
			int sizeOfLanesBeforeInvokation = lanesBeforeInvokationDeep.size();
			isGameOver.invoke(battle);
			PriorityQueue<Object> lanesAfterInvokation = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			PriorityQueue<Object> lanesAfterInvokationDeep = new PriorityQueue<>();
			for(Object l: lanesAfterInvokation) 
				lanesAfterInvokationDeep.add(l);
			int sizeOfLanesAfterInvokation = lanesAfterInvokationDeep.size();

			boolean wallsEquality = true;
			if(sizeOfLanesBeforeInvokation == sizeOfLanesAfterInvokation) {
				while(!lanesBeforeInvokationDeep.isEmpty() && wallsEquality == true) {
					Object l1 = lanesBeforeInvokationDeep.poll();
					Object l2 = lanesAfterInvokationDeep.poll();
					Object w1 = getWall.invoke(l1, null);
					Object w2 = getWall.invoke(l2, null);
					wallsEquality = compare2Walls(w1,w2);
				}
			}
			assertTrue("GameOver method shouldn't affect lanes",wallsEquality);
		}catch( ClassNotFoundException| NoSuchMethodException| IllegalAccessException| InvocationTargetException| InstantiationException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testGameOverLogicNoChangeInTitanDistance() {

		try{
			int numberOfTurns = (int) (Math.random() * 20) + 1;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 200) + 1000;
			int initialResourcesPerLane = (int) (Math.random() * 200) + 1000;

			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class, int.class, int.class, int.class, int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns, score, titanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);

			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Class laneClass = Class.forName(lanePath);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 30, 15, 15, 2);

			Method getLanes = battleClass.getDeclaredMethod("getLanes", null);
			PriorityQueue<Object> lanesBeforeInvokation = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			Method addT = laneClass.getDeclaredMethod("addTitan", Class.forName(titanClassPath));
			for(Object l: lanesBeforeInvokation) {
				addT.invoke(l, abnormalTitan1);
			}			
			Method isGameOver = battleClass.getDeclaredMethod("isGameOver", null);
			isGameOver.invoke(battle);
			Method getTitans = laneClass.getDeclaredMethod("getTitans", null);
			PriorityQueue<Object> lanesAfterInvokation = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			Class titanClass = Class.forName(titanClassPath);
			Method getDistance = titanClass.getDeclaredMethod("getDistance", null);
			boolean equality = true;
			for(Object l: lanesAfterInvokation) {
				PriorityQueue<Object> titansInsideEachLane= (PriorityQueue<Object>) getTitans.invoke(l, null);
				Object titan = titansInsideEachLane.poll();
				int distanceFromBase = (int) getDistance.invoke(titan, null);
				equality = distanceFromBase == 30;
			}

			assertTrue("GameOver method shouldn't move titans",equality);
		}catch( ClassNotFoundException| NoSuchMethodException| IllegalAccessException| InvocationTargetException| InstantiationException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testGameOverLogicNoChangeInDangerLevel()  {

		try{
			int numberOfTurns = (int) (Math.random() * 20) + 1;
			int score = (int) (Math.random() * 20) + 1;
			int titanSpawnDistance = (int) (Math.random() * 20) + 1;
			int initialNumOfLanes = (int) (Math.random() * 200) + 1000;
			int initialResourcesPerLane = (int) (Math.random() * 200) + 1000;

			Class battleClass = Class.forName(battlePath);
			Constructor<?> battleConstructor = Class.forName(battlePath).getConstructor(int.class, int.class, int.class, int.class, int.class);
			Object battle = battleConstructor.newInstance(numberOfTurns, score, titanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);

			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Class laneClass = Class.forName(lanePath);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);

			Object laneObject =  laneConstructor.newInstance(createWall());
			Method getDangerLevel = laneClass.getDeclaredMethod("getDangerLevel", null);
			int dangerLevelBeforeInvocation = (int) getDangerLevel.invoke(laneObject, null);

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(250,  20, 10, 30, 15, 15, 2);

			Method getLanes = battleClass.getDeclaredMethod("getLanes", null);

			PriorityQueue<Object> lanesBeforeInvokation = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			Method addT = laneClass.getDeclaredMethod("addTitan", Class.forName(titanClassPath));
			for(Object l: lanesBeforeInvokation) {
				addT.invoke(l, abnormalTitan1);
			}			
			Method isGameOver = battleClass.getDeclaredMethod("isGameOver", null);
			isGameOver.invoke(battle);

			PriorityQueue<Object> lanesAfterInvokation = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			boolean equality = true;
			for(Object l: lanesAfterInvokation) {
				int dangerLevelAfterInvocation = (int) getDangerLevel.invoke(l, null);
				equality = dangerLevelAfterInvocation == dangerLevelBeforeInvocation;
			}
			assertTrue("isGameOver shouldn't affect the danger level of lanes",equality);
		}catch( ClassNotFoundException| NoSuchMethodException| IllegalAccessException| InvocationTargetException| InstantiationException| SecurityException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testAddWeaponLogicArraySameContentPC() {

		try{
			Class WeaponClass = Class.forName(weaponPath);
			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall = wallConstructor.newInstance(10000);

			Class laneClass = Class.forName(lanePath);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);
			Object lane = laneConstructor.newInstance(wall);

			Constructor<?> weaponConstructor = Class.forName(weaponPiercingCannonPath).getConstructor(int.class);
			int baseDamage = (int) (Math.random() * 10) + 1;
			Object weapon = weaponConstructor.newInstance(baseDamage);

			Method getWeapons = laneClass.getDeclaredMethod("getWeapons", null);

			ArrayList<Object> weaponsShallowExpected = (ArrayList<Object>) getWeapons.invoke(lane, null);
			ArrayList<Object> weaponsDeepColExpected = new ArrayList<>();
			for (Object w : weaponsShallowExpected)
				weaponsDeepColExpected.add(w);
			weaponsDeepColExpected.add(weapon);

			Method method = laneClass.getDeclaredMethod("addWeapon", WeaponClass);
			method.invoke(lane, weapon);

			ArrayList<Object> weaponsShallowActual = (ArrayList<Object>) getWeapons.invoke(lane, null);
			ArrayList<Object> weaponsDeepColActual = new ArrayList<>();
			for (Object w1 : weaponsShallowActual)
				weaponsDeepColActual.add(w1);

			boolean equalContent = false;
			for (int i = 0; i < weaponsDeepColActual.size(); i++) {
				equalContent = compare2Weapons(weaponsDeepColActual.get(i), weaponsDeepColExpected.get(i));
			}

			assertTrue("The 2 arrayLists should have the same weapons ", equalContent);
		}catch(ClassNotFoundException| NoSuchMethodException| SecurityException|IllegalAccessException| InvocationTargetException| InstantiationException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout = 1000)
	public void testAddWeaponLogicArraySameContentVSC()  {
		try{
			Class WeaponClass = Class.forName(weaponPath);
			Class wallClass = Class.forName(wallPath);
			Constructor<?> wallConstructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall = wallConstructor.newInstance(10000);

			Class laneClass = Class.forName(lanePath);
			Constructor<?> laneConstructor = Class.forName(lanePath).getConstructor(wallClass);
			Object lane = laneConstructor.newInstance(wall);

			Constructor<?> weaponConstructor = Class.forName(volleySpreadCannonPath).getConstructor(int.class, int.class, int.class);
			int baseDamage = (int) (Math.random() * 10) + 1;
			int min = (int) (Math.random() * 10) + 1;
			int max = (int) (Math.random() * 10) + 10;
			Object weapon = weaponConstructor.newInstance(baseDamage, min, max);

			Method getWeapons = laneClass.getDeclaredMethod("getWeapons", null);

			ArrayList<Object> weaponsShallowExpected = (ArrayList<Object>) getWeapons.invoke(lane, null);
			ArrayList<Object> weaponsDeepColExpected = new ArrayList<>();
			for (Object w : weaponsShallowExpected)
				weaponsDeepColExpected.add(w);
			weaponsDeepColExpected.add(weapon);

			Method method = laneClass.getDeclaredMethod("addWeapon", WeaponClass);
			method.invoke(lane, weapon);

			ArrayList<Object> weaponsShallowActual = (ArrayList<Object>) getWeapons.invoke(lane, null);
			ArrayList<Object> weaponsDeepColActual = new ArrayList<>();
			for (Object w1 : weaponsShallowActual)
				weaponsDeepColActual.add(w1);

			boolean equalContent = false;
			for (int i = 0; i < weaponsDeepColActual.size(); i++) {
				equalContent = compare2WeaponsVC(weaponsDeepColActual.get(i), weaponsDeepColExpected.get(i));
			}

			assertTrue("The 2 arrayLists should have the same weapons ", equalContent);
		}catch(ClassNotFoundException| NoSuchMethodException| SecurityException|IllegalAccessException| InvocationTargetException| InstantiationException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	//

	@Test(timeout=1000)
	public void testIsDefeatedLogic(){
		try {
			Class aClass = Class.forName(wallPath);
			Constructor<?> constructor = aClass.getConstructor(int.class);
			int baseHealth = (int) (Math.random() * 10) + 1;
			Object wall = constructor.newInstance(baseHealth);
			Method isDefeated = Class.forName(attackeePath).getDeclaredMethod("isDefeated");
			// case 1 : the wall is not defeated yet
			assertEquals("isDefeated Method should return false if the currentHealth > 0", false, isDefeated.invoke(wall));
			// case 2 : the wall is defeated
			Method setCurrentHealth = Class.forName(wallPath).getDeclaredMethod("setCurrentHealth", int.class);
			setCurrentHealth.invoke(wall, -3);
			assertEquals("isDefeated Method should return false if the currentHealth = 0", true, isDefeated.invoke(wall));
			setCurrentHealth.invoke(wall, 0);
			assertEquals("isDefeated Method should return false if the currentHealth = 0", true, isDefeated.invoke(wall));

		} catch (ClassNotFoundException| NoSuchMethodException| SecurityException|
				InstantiationException| IllegalAccessException| IllegalArgumentException| 
				InvocationTargetException e) {
			// TODO: handle exception
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout=1000)
	public void testTakeDamageLogic() {
		try {		
			Class aClass = Class.forName(wallPath);
			Constructor<?> constructor = aClass.getConstructor(int.class);
			int baseHealth = (int) (Math.random() * 20) + 10;
			Object wall = constructor.newInstance(baseHealth);
			Method takeDamage = Class.forName(attackeePath).getDeclaredMethod("takeDamage", int.class);
			Method getCurrentHealth = aClass.getDeclaredMethod("getCurrentHealth");
			Method getResourcesValue = aClass.getDeclaredMethod("getResourcesValue");
			int damage;
			int expectedHealth;

			// case 1 : the damage is greater than the current health of the wall
			damage = baseHealth + (int) (Math.random() * 10) + 1;
			int result = (int) takeDamage.invoke(wall, damage); // invoke the method
			expectedHealth = 0;
			assertEquals("You have to set the currentHealth correctly", expectedHealth, getCurrentHealth.invoke(wall));
			assertEquals("The method should return -1 if the wall is defeated", getResourcesValue.invoke(wall), result);

			// case 2 : the damage is equal to the current health of the wall
			wall = constructor.newInstance(baseHealth);
			damage = (int) getCurrentHealth.invoke(wall);
			result = (int) takeDamage.invoke(wall, damage); // invoke the method
			expectedHealth = 0;
			assertEquals("You have to set the currentHealth correctly", expectedHealth, getCurrentHealth.invoke(wall));
			assertEquals("The method should return -1 if the wall is defeated", getResourcesValue.invoke(wall), result);

			// case 3 : the damage is less than the current health of the wall
			wall = constructor.newInstance(baseHealth);
			damage = (int) (Math.random() * 5) + 1; 
			expectedHealth = (int) getCurrentHealth.invoke(wall) - damage;
			result = (int) takeDamage.invoke(wall, damage); // invoke the method
			assertEquals("You have to set the currentHealth correctly", expectedHealth, getCurrentHealth.invoke(wall));
			assertEquals("The method should return 0 if the wall is not defeated", 0, result);
		} 
		catch (ClassNotFoundException| NoSuchMethodException| SecurityException|
				InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e) {
			// TODO: handle exception
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}



	@Test(timeout=1000)
	public void testAttackMethodLogic(){
		try {
			Class wallClass = Class.forName(wallPath);
			Constructor<?> constructor1 = wallClass.getConstructor(int.class);
			int baseHealth = (int) (Math.random() * 10) + 1; 
			Object wall = constructor1.newInstance(baseHealth);
			Class pureTitanClass = Class.forName(pureTitanPath);
			Constructor<?> constructor2 = pureTitanClass.getConstructor(int.class, int.class, int.class, int.class,
					int.class, int.class, int.class);
			int health = (int) (Math.random() * 10) + 1;
			int damage = (int) (Math.random() * 20) + 10 + baseHealth; 
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int distanceFromBase = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;
			Object pureTitan = constructor2.newInstance(health, damage, heightInMeters, distanceFromBase, speed,
					resourcesValue, dangerLevel);

			Method attack = Class.forName(attackerPath).getDeclaredMethod("attack", Class.forName(attackeePath));
			// case 1 : if the damage is greater than the health of the wall
			int result = (int) attack.invoke(pureTitan, wall);
			assertEquals("The method should return -1 if the wall is defeated", -1, result);

			// case 2 : if the damage is equal to the health of the wall
			baseHealth = (int) (Math.random() * 10) + 1;
			wall = constructor1.newInstance(baseHealth);
			damage = baseHealth;
			pureTitan = constructor2.newInstance(health, damage, heightInMeters, distanceFromBase, speed, resourcesValue,
					dangerLevel);
			result = (int) attack.invoke(pureTitan, wall);
			assertEquals("The method should return -1 if the wall is defeated", -1, result);

			// case 3 : if the damage is less than the health of the wall
			baseHealth = (int) (Math.random() * 20) + 10;
			wall = constructor1.newInstance(baseHealth);
			damage = (int) (Math.random() * 5) + 1;
			pureTitan = constructor2.newInstance(health, damage, heightInMeters, distanceFromBase, speed, resourcesValue,
					dangerLevel);
			result = (int) attack.invoke(pureTitan, wall);
			assertEquals("The method should return 0 if the wall is not defeated", 0, result);
		} catch (ClassNotFoundException| NoSuchMethodException| SecurityException|
				InstantiationException| IllegalAccessException| IllegalArgumentException|
				InvocationTargetException e) {

			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}


	}


	@Test(timeout=1000)
	public void testhasReachedTargetMethodInMobil() {
		try {
			testInterfaceMethod(Class.forName(mobilPath), "hasReachedTarget", boolean.class, null);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testhasReachedTargetIsDefault(){
		try {
			testInterfaceMethodIsDefault(Class.forName(mobilPath), "hasReachedTarget", null);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testhasReachedTargetMethodLogic() {

		try {
			Class pureTitanClass = Class.forName(pureTitanPath);
			Constructor<?> constructor2 = pureTitanClass.getConstructor(int.class, int.class, int.class, int.class,
					int.class, int.class, int.class);
			int damage = (int) (Math.random() * 10) + 1;
			int health = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int distanceFromBase = (int) (Math.random() * 20) + 10;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;
			Object pureTitan = constructor2.newInstance(health, damage, heightInMeters, distanceFromBase, speed,
					resourcesValue, dangerLevel);
			Method hasReachedTarget = Class.forName(mobilPath).getDeclaredMethod("hasReachedTarget");
			Method getDistance = Class.forName(mobilPath).getDeclaredMethod("getDistance");

			// case 1 : distance is greater than zero
			boolean result = (boolean) hasReachedTarget.invoke(pureTitan);
			assertEquals("The method should return false if the titan did not reach", false, result);

			// case 2 : distance is less than zero, which is not logic :)
			distanceFromBase = (int) (Math.random() * 10) + 1;
			pureTitan = constructor2.newInstance(health, damage, heightInMeters, distanceFromBase * -1, speed,
					resourcesValue, dangerLevel);
			result = (boolean) hasReachedTarget.invoke(pureTitan);
			assertEquals("The method should return false if the titan did not reach", true, result);

			// case 3 : distance is equal to zero
			pureTitan = constructor2.newInstance(health, damage, heightInMeters, 0, speed, resourcesValue, dangerLevel);
			result = (boolean) hasReachedTarget.invoke(pureTitan);
			assertEquals("The method should return false if the titan did not reach", true, result);
		} 
		catch (ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException|
				IllegalAccessException| IllegalArgumentException| InvocationTargetException e) {
			// TODO: handle exception
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}


	}

	@Test(timeout=1000)
	public void testMoveMethodInMobil() {
		try {
			testInterfaceMethod(Class.forName(mobilPath), "move", boolean.class, null);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testMoveMethodIsDefault() {
		try {
			testInterfaceMethodIsDefault(Class.forName(mobilPath), "move", null);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testMoveMethodLogic(){

		try {
			Class pureTitanClass = Class.forName(pureTitanPath);
			Constructor<?> constructor2 = pureTitanClass.getConstructor(int.class, int.class, int.class, int.class,
					int.class, int.class, int.class);
			int damage = (int) (Math.random() * 10) + 1;
			int health = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int distanceFromBase = (int) (Math.random() * 20) + 11; 
			int speed = (int) (Math.random() * 10) + 1; 
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;
			Object pureTitan = constructor2.newInstance(health, damage, heightInMeters, distanceFromBase, speed,
					resourcesValue, dangerLevel);
			Method move = Class.forName(mobilPath).getDeclaredMethod("move");
			Method getDistance = Class.forName(titanPath).getDeclaredMethod("getDistance");
			Method getSpeed = Class.forName(titanPath).getDeclaredMethod("getSpeed");

			int expectedDistance;
			boolean result;
			// case 1 : the titan did not reach
			expectedDistance = (int) getDistance.invoke(pureTitan) - (int) getSpeed.invoke(pureTitan);
			expectedDistance = expectedDistance < 0 ? 0 : expectedDistance;
			result = (boolean) move.invoke(pureTitan);
			assertEquals("The distance of the titan from the base is calculated wrongly", expectedDistance,
					getDistance.invoke(pureTitan));
			assertEquals("The method should return false because the titan did not reach", false, result);

			// case 2 : the titan reached
			distanceFromBase = (int) (Math.random() * 10) + 1; 
			speed = (int) (Math.random() * 20) + 10; 
			pureTitan = constructor2.newInstance(health, damage, heightInMeters, distanceFromBase, speed, resourcesValue,
					dangerLevel);
			expectedDistance = (int) getDistance.invoke(pureTitan) - (int) getSpeed.invoke(pureTitan);
			expectedDistance = expectedDistance < 0 ? 0 : expectedDistance;
			result = (boolean) move.invoke(pureTitan);
			assertEquals("The distance of the titan from the base is calculated wrongly", expectedDistance,
					getDistance.invoke(pureTitan));
			assertEquals("The method should return true because the titan reaches the wall", true, result);
		} 
		catch (ClassNotFoundException| NoSuchMethodException| SecurityException|
				InstantiationException| IllegalAccessException| IllegalArgumentException| 
				InvocationTargetException  e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}


	@Test(timeout=1000)
	public void testTakeDamageAbsenceInPure() {
		try {
			testMethodAbsence(Class.forName(pureTitanPath), "takeDamage");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testAttackAbsenceInPure() {
		try {
			testMethodAbsence(Class.forName(pureTitanPath), "attack");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testMoveAbsenceInPure() {
		try {
			testMethodAbsence(Class.forName(pureTitanPath), "move");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}



	@Test(timeout=1000)
	public void testAttackMethodExistenceInAbnormalClass() {
		try {
			testMethodExistence(Class.forName(abnormalTitan), "attack", int.class,
					new Class[] { Class.forName(attackeePath) });
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testAttackMethodLogicInAbnormalClass(){
		try {
			Class abnormalTitanClass = Class.forName(abnormalTitan);
			Constructor<?> constructor1 = abnormalTitanClass.getConstructor(int.class, int.class, int.class, int.class,
					int.class, int.class, int.class);
			int damage = (int) (Math.random() * 20) + 10; 
			int health = (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int distanceFromBase = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;
			Object abnormalTitan = constructor1.newInstance(health, damage, heightInMeters, distanceFromBase, speed,
					resourcesValue, dangerLevel);

			Class wallClass = Class.forName(wallPath);
			Constructor<?> constructor2 = wallClass.getConstructor(int.class);
			int baseHealth = (int) (Math.random() * 5) + 1; 
			Object wall = constructor2.newInstance(baseHealth);
			Method attack = abnormalTitanClass.getDeclaredMethod("attack", Class.forName(attackeePath));
			Method getCurrentHealth = wallClass.getDeclaredMethod("getCurrentHealth");

			int result;
			int expectedHealth;
			// case 1 : if the titan's damage > wall's health, then it will be defeated
			// after the method invocation
			expectedHealth = 0;
			result = (int) attack.invoke(abnormalTitan, wall);
			assertEquals("The current health of the wall should be zero after it is defeated", expectedHealth,
					getCurrentHealth.invoke(wall));
			assertEquals("The attack method should return -1 because the wall is defeated", -1, result);

			// case 2 : if the titan's damage <= wall's health, however wall will be
			// defeated after the method invocation
			damage = (int) (Math.random() * 10) + 1; 
			baseHealth = (int) (Math.random() * damage) + damage; 
			abnormalTitan = constructor1.newInstance(health, damage, heightInMeters, distanceFromBase, speed,
					resourcesValue, dangerLevel);
			wall = constructor2.newInstance(baseHealth);
			expectedHealth = 0;
			result = (int) attack.invoke(abnormalTitan, wall);
			assertEquals("The current health of the wall should be zero after it is defeated", expectedHealth,
					getCurrentHealth.invoke(wall));
			assertEquals("The attack method should return -1 because the wall is defeated", -1, result);

			// case 3 : if the titan's damage < wall's health, and the wall will not be
			// defeated after the method invocation
			damage = (int) (Math.random() * 10) + 1; 
			baseHealth = (damage * 2) + (int) (Math.random() * 10) + 1;
			abnormalTitan = constructor1.newInstance(health, damage, heightInMeters, distanceFromBase, speed,
					resourcesValue, dangerLevel);
			wall = constructor2.newInstance(baseHealth);
			expectedHealth = baseHealth - 2 * damage;
			result = (int) attack.invoke(abnormalTitan, wall);
			assertEquals("The current health of the wall should be " + expectedHealth, expectedHealth,
					getCurrentHealth.invoke(wall));
			assertEquals("The attack method should return 0 because the wall is not defeated", 0, result);
		} 
		catch (ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException|
				IllegalAccessException| IllegalArgumentException| InvocationTargetException  e) {
			// TODO: handle exception
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}

	@Test(timeout=1000)
	public void testTakeDamageAbsenceInAbnormal(){
		try {
			testMethodAbsence(Class.forName(abnormalTitan), "takeDamage");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testMoveAbsenceInAbnormal() {
		try {
			testMethodAbsence(Class.forName(abnormalTitan), "move");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}



	@Test(timeout=1000)
	public void testTakeDamageMethodExistanceInArmoredClass() {
		try {
			testMethodExistence(Class.forName(armoredTitan), "takeDamage", int.class, new Class[] { int.class });
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testTakeDamageMethodLogic() {
		try {
			Class armoredTitanClass = Class.forName(armoredTitan);
			Constructor<?> constructor1 = armoredTitanClass.getConstructor(int.class, int.class, int.class, int.class,
					int.class, int.class, int.class);
			int damage = (int) (Math.random() * 20) + 10; 
			int health = (damage * 4) + (int) (Math.random() * 10) + 1;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int distanceFromBase = (int) (Math.random() * 10) + 1;
			int speed = (int) (Math.random() * 10) + 1;
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;
			Object armoredTitan = constructor1.newInstance(health, damage, heightInMeters, distanceFromBase, speed,
					resourcesValue, dangerLevel);
			Method takeDamage = armoredTitanClass.getDeclaredMethod("takeDamage", int.class);
			Method getCurrentHealth = Class.forName(attackeePath).getDeclaredMethod("getCurrentHealth");
			Method getResourcesValue = Class.forName(attackeePath).getDeclaredMethod("getResourcesValue");

			int input;
			int expectedHealth;
			int result;
			// case 1 : the titan will not be defeated
			input = damage;
			expectedHealth = (int) getCurrentHealth.invoke(armoredTitan) - input / 4;
			result = (int) takeDamage.invoke(armoredTitan, input);
			assertEquals("the current health of the titan should be " + expectedHealth, expectedHealth,
					getCurrentHealth.invoke(armoredTitan));
			assertEquals("the method should return 0 as the titan is not defeated", 0, result);

			// case 2 : the titan will be defeated
			input = damage;
			health = damage / 4;
			armoredTitan = constructor1.newInstance(health, damage, heightInMeters, distanceFromBase, speed, resourcesValue,
					dangerLevel);
			expectedHealth = Math.max((int) getCurrentHealth.invoke(armoredTitan) - input / 4, 0);
			result = (int) takeDamage.invoke(armoredTitan, input);
			assertEquals("the current health of the titan should be " + expectedHealth, expectedHealth,
					getCurrentHealth.invoke(armoredTitan));
			assertEquals("the method should return the resources value as the titan is defeated",
					getResourcesValue.invoke(armoredTitan), result);
		} 
		catch (ClassNotFoundException| NoSuchMethodException| SecurityException|
				InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e) {
			// TODO: handle exception
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}	
	}

	@Test(timeout=1000)
	public void testAttackAbsenceInArmored() {
		try {
			testMethodAbsence(Class.forName(armoredTitan), "attack");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testMoveAbsenceInArmored() {
		try {
			testMethodAbsence(Class.forName(armoredTitan), "move");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}



	@Test(timeout=1000)
	public void testMoveMethodInColossalClass() {
		try {
			testMethodExistence(Class.forName(colossalTitan), "move", boolean.class, null);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testMoveMethodLogicInColossalClass() {
		try {
			Class colossalTitanClass = Class.forName(colossalTitan);
			Constructor<?> constructor1 = colossalTitanClass.getConstructor(int.class, int.class, int.class, int.class,
					int.class, int.class, int.class);
			int damage = (int) (Math.random() * 20) + 10;
			int health = (int) (Math.random() * 20) + 10;
			int heightInMeters = (int) (Math.random() * 10) + 1;
			int distanceFromBase = (int) (Math.random() * 10) + 1; 
			int speed = (int) (Math.random() * 20) + 10; 
			int resourcesValue = (int) (Math.random() * 10) + 1;
			int dangerLevel = (int) (Math.random() * 10) + 1;
			Object colossalTitanObject = constructor1.newInstance(health, damage, heightInMeters, distanceFromBase, speed,
					resourcesValue, dangerLevel);

			Method move = Class.forName(colossalTitan).getDeclaredMethod("move");
			Method getDistance = Class.forName(mobilPath).getDeclaredMethod("getDistance");
			Method getSpeed = Class.forName(mobilPath).getDeclaredMethod("getSpeed");

			int expectedDistance;
			int expectedSpeed;
			boolean result;
			// case 1 : if the speed >= distance
			expectedDistance = Math.max(0, distanceFromBase - speed);
			expectedSpeed = speed + 1;
			result = (boolean) move.invoke(colossalTitanObject);

			assertEquals("the ditsance after the move should be " + expectedDistance, expectedDistance,
					getDistance.invoke(colossalTitanObject));
			assertEquals("the speed after the move should be increased by 1", expectedSpeed,
					getSpeed.invoke(colossalTitanObject));
			assertEquals("the method should return true if the titan has reached", true, result);

			// case 2 : if the speed < distance
			distanceFromBase = (int) (Math.random() * 20) + 11;
			speed = (int) (Math.random() * 10) + 1;
			colossalTitanObject = constructor1.newInstance(health, damage, heightInMeters, distanceFromBase, speed,
					resourcesValue, dangerLevel);
			expectedDistance = Math.max(0, distanceFromBase - speed);
			expectedSpeed = speed + 1;
			result = (boolean) move.invoke(colossalTitanObject);
			assertEquals("the ditsance after the move should be " + expectedDistance, expectedDistance,
					getDistance.invoke(colossalTitanObject));
			assertEquals("the speed after the move should be increased by 1", expectedSpeed,
					getSpeed.invoke(colossalTitanObject));
			assertEquals("the method should return false if the titan has not reached", false, result);

		} 
		catch (ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException|
				IllegalAccessException| IllegalArgumentException| InvocationTargetException e) {
			// TODO: handle exception
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}


	@Test(timeout=1000)
	public void testTakeDamageAbsenceInColossal() {
		try {
			testMethodAbsence(Class.forName(colossalTitan), "takeDamage");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testAttackAbsenceInColossal() {
		try {
			testMethodAbsence(Class.forName(colossalTitan), "attack");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	//	


	@Test(timeout=1000)
	public void testMethodTurnAttackExistsInClassWeapon()  {
		Method m;
		try {
			m = Class.forName(weaponPath).getDeclaredMethod("turnAttack", PriorityQueue.class);
			assertNotNull(m);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");

		}

	}

	@Test(timeout=1000)
	public void testMethodTurnAttackIsAbstractInClassWeapon(){

		Method method;
		try {
			method = Class.forName(weaponPath).getDeclaredMethod("turnAttack", PriorityQueue.class);
			assertTrue("Method is not abstract", Modifier.isAbstract(method.getModifiers()));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");

		}

	}


	@Test(timeout=1000)
	public void testSniperWeaponTurnAttack1() {
		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			int distanceAbnormalTitan1 =  (int) (Math.random() * 10) + 30;
			int distanceAbnormalTitan2 =  (int) (Math.random() * 10) + 20;
			int distanceArmoredTitan1 =  (int) (Math.random() * 5) + 1;
			int distanceArmoredTitan2 =  (int) (Math.random() * 10) + 50;

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, distanceAbnormalTitan1, 15, 15, 2);
			Object abnormalTitan2 =  constructor.newInstance(100, 20, 10, distanceAbnormalTitan2, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10, distanceArmoredTitan1, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, distanceArmoredTitan2, 15, 15, 2);

			laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);

			Class sniperWeaponClass = Class.forName(sniperCannonPath);
			Constructor<?> sniperWeaponConstructor = sniperWeaponClass.getConstructor(int.class);
			Object sniperWeapon = sniperWeaponConstructor.newInstance(20);

			Method turnAttack = sniperWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(sniperWeapon, laneTitans);

			assertTrue("turnAttack Method of SniperWeapon fails to attack closest Titan",checkAttributesEqualValue(titanClassPath, armoredTitan1, 65, "currentHealth"));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testSniperWeaponTurnAttack2(){
		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			int distanceAbnormalTitan1 =  (int) (Math.random() * 10) + 30;
			int distanceAbnormalTitan2 =  (int) (Math.random() * 10) + 20;
			int distanceArmoredTitan1 =  (int) (Math.random() * 5) + 1;
			int distanceArmoredTitan2 =  (int) (Math.random() * 10) + 50;

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, distanceAbnormalTitan1, 15, 15, 2);
			Object abnormalTitan2 =  constructor.newInstance(100, 20, 10, distanceAbnormalTitan2, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10, distanceArmoredTitan1, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, distanceArmoredTitan2, 15, 15, 2);

			laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);

			Class sniperWeaponClass = Class.forName(sniperCannonPath);
			Constructor<?> sniperWeaponConstructor = sniperWeaponClass.getConstructor(int.class);
			Object sniperWeapon = sniperWeaponConstructor.newInstance(20);

			Method turnAttack = sniperWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(sniperWeapon, laneTitans);


			assertTrue("turnAttack Method shouldn't affect far titans",checkAttributesEqualValue(titanClassPath, abnormalTitan1, 50, "currentHealth"));
			assertTrue("turnAttack Method shouldn't affect far titans",checkAttributesEqualValue(titanClassPath, abnormalTitan2, 100, "currentHealth"));
			assertTrue("turnAttack Method shouldn't affect far titans", checkAttributesEqualValue(titanClassPath, armoredTitan2, 100, "currentHealth"));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}


	@Test(timeout=1000)
	public void testSniperWeaponTurnAttackOnDeathOfClosestTitan4(){
		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor.newInstance(80, 20, 10, 50, 15, 15, 2);
			Object colossalTitan2 =  constructor.newInstance(20, 20, 10,  2, 15, 50, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor.newInstance(20,  20, 10, 10, 15, 15, 2);
			Object armoredTitan2 =  constructor.newInstance(100, 20, 10, 50, 15, 15, 2);

			laneTitans.add(colossalTitan1); laneTitans.add(colossalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);

			Class sniperWeaponClass = Class.forName(sniperCannonPath);
			Constructor<?> sniperWeaponConstructor = sniperWeaponClass.getConstructor(int.class);
			Object sniperWeapon = sniperWeaponConstructor.newInstance(20);

			Method turnAttack = sniperWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(sniperWeapon, laneTitans);

			// check dead titan was removed from queue

			assertFalse("Dead titans should be removed from lane on attack",laneTitans.contains(colossalTitan2));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testSniperWeaponTurnAttack5() {
		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Class sniperWeaponClass = Class.forName(sniperCannonPath);
			Constructor<?> sniperWeaponConstructor = sniperWeaponClass.getConstructor(int.class);
			Object sniperWeapon = sniperWeaponConstructor.newInstance(20);

			Method turnAttack = sniperWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(sniperWeapon, laneTitans);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Sniper weapon should attack closest titan only if it exists ");
		}
	}

	@Test(timeout=1000)
	public void testSniperWeaponTurnAttackOnDeathOfClosestTitan3() {

		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();
			Constructor<?> constructor = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor.newInstance(80, 20, 10, 50, 15, 15, 2);
			Object colossalTitan2 =  constructor.newInstance(20, 20, 10,  2, 15, 50, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(20,  20, 10, 10, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, 50, 15, 15, 2);

			laneTitans.add(colossalTitan1); laneTitans.add(colossalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);

			Class sniperWeaponClass = Class.forName(sniperCannonPath);
			Constructor<?> sniperWeaponConstructor = sniperWeaponClass.getConstructor(int.class);
			Object sniperWeapon = sniperWeaponConstructor.newInstance(20);

			Method turnAttack = sniperWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(sniperWeapon, laneTitans);

			assertEquals(3, laneTitans.size());

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException  e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testWallTrapWeaponTurnAttackOnDeathOfClosestTitan6() {
		try {
			Class Titan = Class.forName(titanClassPath);
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			int distanceColossalTitan1 =  (int) (Math.random() * 10) + 30;
			int distanceColossalTitan2 =  (int) (Math.random() * 10) + 2;
			int distanceArmoredTitan1 =  (int) (Math.random() * 10) + 20;
			int distanceArmoredTitan2 =  (int) (Math.random() * 10) + 50;

			int resourcesColossalTitan2 =  (int) (Math.random() * 10) + 20;

			Constructor<?> constructor = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor.newInstance(80, 20, 10, distanceColossalTitan1, 15, 15, 2);
			Object colossalTitan2 =  constructor.newInstance(20, 20, 10,  0, 15, resourcesColossalTitan2, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(20,  20, 10, distanceArmoredTitan1, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, distanceArmoredTitan2, 15, 15, 2);

			laneTitans.add(colossalTitan1); laneTitans.add(colossalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);

			Class wallTrapWeaponClass = Class.forName(wallTrapPath);
			Constructor<?> wallTrapWeaponConstructor = wallTrapWeaponClass.getConstructor(int.class);
			Object wallTrapWeapon = wallTrapWeaponConstructor.newInstance(20);

			Method turnAttack = wallTrapWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(wallTrapWeapon, laneTitans);

			assertEquals(resourcesColossalTitan2, returnedResources); 

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}

	@Test(timeout=1000)
	public void testPiercingWeaponTurnAttack1a() {

		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);
			Object abnormalTitan2 =  constructor.newInstance(15, 20, 10, 30, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10, 10, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, 50, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(60,  20, 10,  1, 13, 15, 2);
			Object pureTitan2 =  constructor3.newInstance(100, 20, 10, 50, 70, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  6, 15, 25, 2);
			Object colossalTitan2 =  constructor4.newInstance(100, 20, 10, 80, 15, 15, 2);

			laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
			laneTitans.add(pureTitan1);  laneTitans.add(pureTitan2);
			laneTitans.add(colossalTitan1);  laneTitans.add(colossalTitan2);


			Class piercingWeaponClass = Class.forName(weaponPiercingCannonPath);
			Constructor<?> piercingWeaponConstructor = piercingWeaponClass.getConstructor(int.class);
			Object piercingWeapon = piercingWeaponConstructor.newInstance(20);

			Method turnAttack = piercingWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(piercingWeapon, laneTitans);

			assertTrue("turnAttack Method of PiercingCannon fails to attack closest 5 Titans",checkAttributesEqualValue(titanClassPath, abnormalTitan1, 30, "currentHealth"));
			assertTrue("turnAttack Method of PiercingCannon fails to attack closest 5 Titans",checkAttributesEqualValue(titanClassPath, abnormalTitan2, 0, "currentHealth"));
			assertTrue("turnAttack Method of PiercingCannon fails to attack closest 5 Titans",checkAttributesEqualValue(titanClassPath, armoredTitan1, 65, "currentHealth"));
			assertTrue("turnAttack Method of PiercingCannon fails to attack closest 5 Titans",checkAttributesEqualValue(titanClassPath, pureTitan1, 40, "currentHealth"));
			assertTrue("turnAttack Method of PiercingCannon fails to attack closest 5 Titans",checkAttributesEqualValue(titanClassPath, colossalTitan1, 0, "currentHealth"));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testPiercingWeaponTurnAttack2() {
		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);
			Object abnormalTitan2 =  constructor.newInstance(15, 20, 10, 30, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10, 10, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, 50, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(60,  20, 10,  1, 13, 15, 2);
			Object pureTitan2 =  constructor3.newInstance(100, 20, 10, 50, 70, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  6, 15, 25, 2);
			Object colossalTitan2 =  constructor4.newInstance(100, 20, 10, 80, 15, 15, 2);

			laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
			laneTitans.add(pureTitan1);  laneTitans.add(pureTitan2);
			laneTitans.add(colossalTitan1);  laneTitans.add(colossalTitan2);


			Class piercingWeaponClass = Class.forName(weaponPiercingCannonPath);
			Constructor<?> piercingWeaponConstructor = piercingWeaponClass.getConstructor(int.class);
			Object piercingWeapon = piercingWeaponConstructor.newInstance(20);

			Method turnAttack = piercingWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(piercingWeapon, laneTitans);

			assertTrue("turnAttack Method shouldn't affect far titans",checkAttributesEqualValue(titanClassPath, colossalTitan2, 100, "currentHealth"));
			assertTrue("turnAttack Method shouldn't affect far titans",checkAttributesEqualValue(titanClassPath, pureTitan2, 100, "currentHealth"));
			assertTrue("turnAttack Method shouldn't affect far titans", checkAttributesEqualValue(titanClassPath, armoredTitan2, 100, "currentHealth"));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testPiercingWeaponTurnAttack4(){

		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);
			Object abnormalTitan2 =  constructor.newInstance(15, 20, 10, 30, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10, 10, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, 50, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(60,  20, 10,  1, 13, 15, 2);
			Object pureTitan2 =  constructor3.newInstance(100, 20, 10, 50, 70, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  6, 15, 25, 2);
			Object colossalTitan2 =  constructor4.newInstance(100, 20, 10, 80, 15, 15, 2);

			laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
			laneTitans.add(pureTitan1);  laneTitans.add(pureTitan2);
			laneTitans.add(colossalTitan1);  laneTitans.add(colossalTitan2);


			Class piercingWeaponClass = Class.forName(weaponPiercingCannonPath);
			Constructor<?> piercingWeaponConstructor = piercingWeaponClass.getConstructor(int.class);
			Object piercingWeapon = piercingWeaponConstructor.newInstance(20);

			Method turnAttack = piercingWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(piercingWeapon, laneTitans);

			assertFalse("Dead titans should be removed from lane on attack",laneTitans.contains(abnormalTitan2));
			assertFalse("Dead titans should be removed from lane on attack",laneTitans.contains(colossalTitan1));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}
	@Test(timeout=1000)
	public void testPiercingWeaponTurnAttack6() {
		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);
			Object abnormalTitan2 =  constructor.newInstance(15, 20, 10, 30, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10, 10, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, 50, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(60,  20, 10,  1, 13, 15, 2);
			Object pureTitan2 =  constructor3.newInstance(100, 20, 10, 50, 70, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  6, 15, 25, 2);
			Object colossalTitan2 =  constructor4.newInstance(100, 20, 10, 80, 15, 15, 2);

			laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
			laneTitans.add(pureTitan1);  laneTitans.add(pureTitan2);
			laneTitans.add(colossalTitan1);  laneTitans.add(colossalTitan2);


			Class piercingWeaponClass = Class.forName(weaponPiercingCannonPath);
			Constructor<?> piercingWeaponConstructor = piercingWeaponClass.getConstructor(int.class);
			Object piercingWeapon = piercingWeaponConstructor.newInstance(20);

			Method turnAttack = piercingWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(piercingWeapon, laneTitans);


			assertEquals("Method turnAttack should return the total retrieved resources of dead titans",40, returnedResources); 
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}
	@Test(timeout=1000)
	public void testPiercingWeaponTurnAttack7() {
		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);


			Class piercingWeaponClass = Class.forName(weaponPiercingCannonPath);
			Constructor<?> piercingWeaponConstructor = piercingWeaponClass.getConstructor(int.class);
			Object piercingWeapon = piercingWeaponConstructor.newInstance(20);

			Method turnAttack = piercingWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(piercingWeapon, laneTitans);


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Piercing Weapon should attack the closest 5 titans only if they exist");
		}
	}


	@Test(timeout=1000)
	public void testVolleySpreadTurnAttack3() {
		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);
			Object abnormalTitan2 =  constructor.newInstance(15, 20, 10, 30, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10,  9, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, 26, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(10,  20, 10,  15, 13, 15, 2);
			Object pureTitan2 =  constructor3.newInstance(100, 20, 10, 50, 70, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  6, 15, 25, 2);
			Object colossalTitan2 =  constructor4.newInstance(100, 20, 10, 10, 15, 15, 2);

			laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
			laneTitans.add(pureTitan1);  laneTitans.add(pureTitan2);
			laneTitans.add(colossalTitan1);  laneTitans.add(colossalTitan2);


			Class volleyWeaponClass = Class.forName(volleySpreadCannonPath);
			Constructor<?> volleyWeaponConstructor = volleyWeaponClass.getConstructor(int.class,int.class,int.class);
			Object volleyWeapon = volleyWeaponConstructor.newInstance(20, 10, 30);

			Method turnAttack = volleyWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(volleyWeapon, laneTitans);

			assertTrue("Living titans shouldn't be removed from lane on attack",laneTitans.contains(abnormalTitan1));
			assertTrue("Living titans shouldn't be removed from lane on attack",laneTitans.contains(armoredTitan2));
			assertTrue("Living titans shouldn't be removed from lane on attack",laneTitans.contains(colossalTitan2));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testVolleySpreadTurnAttack4() {
		try {
			Class Titan = Class.forName(titanClassPath);
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();


			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);
			Object abnormalTitan2 =  constructor.newInstance(15, 20, 10, 30, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10,  9, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, 26, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(10,  20, 10,  15, 13, 15, 2);
			Object pureTitan2 =  constructor3.newInstance(100, 20, 10, 50, 70, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  6, 15, 25, 2);
			Object colossalTitan2 =  constructor4.newInstance(100, 20, 10, 10, 15, 15, 2);

			laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
			laneTitans.add(pureTitan1);  laneTitans.add(pureTitan2);
			laneTitans.add(colossalTitan1);  laneTitans.add(colossalTitan2);


			Class volleyWeaponClass = Class.forName(volleySpreadCannonPath);
			Constructor<?> volleyWeaponConstructor = volleyWeaponClass.getConstructor(int.class,int.class,int.class);
			Object volleyWeapon = volleyWeaponConstructor.newInstance(20, 10, 30);

			Method turnAttack = volleyWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(volleyWeapon, laneTitans);

			assertFalse("Dead titans should be removed from lane on attack",laneTitans.contains(abnormalTitan2));
			assertFalse("Dead titans should be removed from lane on attack",laneTitans.contains(pureTitan1));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testVolleySpreadTurnAttack5() {

		try {
			Class Titan = Class.forName(titanClassPath);
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();


			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);
			Object abnormalTitan2 =  constructor.newInstance(15, 20, 10, 30, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10,  9, 15, 15, 2);
			Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, 26, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(10,  20, 10,  15, 13, 15, 2);
			Object pureTitan2 =  constructor3.newInstance(100, 20, 10, 50, 70, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  6, 15, 25, 2);
			Object colossalTitan2 =  constructor4.newInstance(100, 20, 10, 10, 15, 15, 2);

			laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
			laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
			laneTitans.add(pureTitan1);  laneTitans.add(pureTitan2);
			laneTitans.add(colossalTitan1);  laneTitans.add(colossalTitan2);


			Class volleyWeaponClass = Class.forName(volleySpreadCannonPath);
			Constructor<?> volleyWeaponConstructor = volleyWeaponClass.getConstructor(int.class,int.class,int.class);
			Object volleyWeapon = volleyWeaponConstructor.newInstance(20, 10, 30);

			Method turnAttack = volleyWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(volleyWeapon, laneTitans);

			assertTrue("Unattacked titans shouldn't be removed from lane on attack",laneTitans.contains(colossalTitan1));
			assertTrue("Unattacked titans shouldn't be removed from lane on attack",laneTitans.contains(pureTitan2));
			assertTrue("Unattacked titans shouldn't be removed from lane on attack",laneTitans.contains(armoredTitan1));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testWallTrapTurnAttack1() {
		try {
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10,  10, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(50,  20, 10,  0, 15, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  50, 15, 25, 2);

			laneTitans.add(abnormalTitan1);
			laneTitans.add(armoredTitan1); 
			laneTitans.add(pureTitan1); 
			laneTitans.add(colossalTitan1);  

			Class wallTrapWeaponClass = Class.forName(wallTrapPath);
			Constructor<?> wallTrapWeaponConstructor = wallTrapWeaponClass.getConstructor(int.class);
			Object wallTrapWeapon = wallTrapWeaponConstructor.newInstance(20);

			Method turnAttack = wallTrapWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(wallTrapWeapon, laneTitans);

			assertTrue("turnAttack Method of WallTrap fails to attack closest Titan which reached the wall",checkAttributesEqualValue(titanClassPath, pureTitan1, 30, "currentHealth"));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}


	@Test(timeout=1000)
	public void testWallTrapTurnAttack3(){
		try {
			Class Titan = Class.forName(titanClassPath);
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();


			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10,  10, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(50,  20, 10,  0, 15, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  50, 15, 25, 2);

			laneTitans.add(abnormalTitan1);
			laneTitans.add(armoredTitan1); 
			laneTitans.add(pureTitan1); 
			laneTitans.add(colossalTitan1);  


			Class wallTrapWeaponClass = Class.forName(wallTrapPath);
			Constructor<?> wallTrapWeaponConstructor = wallTrapWeaponClass.getConstructor(int.class);
			Object wallTrapWeapon = wallTrapWeaponConstructor.newInstance(20);

			Method turnAttack = wallTrapWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(wallTrapWeapon, laneTitans);

			assertTrue("Living titans shouldn't be removed from lane on attack",laneTitans.contains(colossalTitan1));
			assertTrue("Living titans shouldn't be removed from lane on attack",laneTitans.contains(abnormalTitan1));
			assertTrue("Living titans shouldn't be removed from lane on attack",laneTitans.contains(armoredTitan1));
			assertTrue("Living titans shouldn't be removed from lane on attack",laneTitans.contains(pureTitan1));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testWallTrapTurnAttack4(){
		try {
			Class Titan = Class.forName(titanClassPath);
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10,  10, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(50,  20, 10,  0, 15, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  50, 15, 25, 2);

			laneTitans.add(abnormalTitan1);
			laneTitans.add(armoredTitan1); 
			laneTitans.add(pureTitan1); 
			laneTitans.add(colossalTitan1);  


			Class wallTrapWeaponClass = Class.forName(wallTrapPath);
			Constructor<?> wallTrapWeaponConstructor = wallTrapWeaponClass.getConstructor(int.class);
			Object wallTrapWeapon = wallTrapWeaponConstructor.newInstance(20);

			Method turnAttack = wallTrapWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(wallTrapWeapon, laneTitans);

			assertEquals("Method turnAttack should return the total retrieved resources of dead titans",0, returnedResources); 
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testWallTrapTurnAttack5a() {
		try {
			Class Titan = Class.forName(titanClassPath);
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			int distanceAbnormalTitan1 =  (int) (Math.random() * 10) + 30;
			int distanceArmoredTitan1 =  (int) (Math.random() * 10) + 20;
			int distancePureTitan1 =  (int) (Math.random() * 10) + 50;
			int distanceColossalTitan1 =  (int) (Math.random() * 5) + 1;

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, distanceAbnormalTitan1, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10,  distanceArmoredTitan1, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(50,  20, 10,  distancePureTitan1, 15, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  distanceColossalTitan1, 15, 25, 2);

			laneTitans.add(abnormalTitan1);
			laneTitans.add(armoredTitan1); 
			laneTitans.add(pureTitan1); 
			laneTitans.add(colossalTitan1);  


			Class wallTrapWeaponClass = Class.forName(wallTrapPath);
			Constructor<?> wallTrapWeaponConstructor = wallTrapWeaponClass.getConstructor(int.class);
			Object wallTrapWeapon = wallTrapWeaponConstructor.newInstance(20);

			Method turnAttack = wallTrapWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(wallTrapWeapon, laneTitans);

			// check health of closest titan
			assertTrue("turnAttack Method of WallTrap shouldn't attack closest Titan if he didn't reach the wall",checkAttributesEqualValue(titanClassPath, colossalTitan1, 20, "currentHealth"));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}

	@Test(timeout=1000)
	public void testWallTrapTurnAttack5b() {
		try {
			Class Titan = Class.forName(titanClassPath);
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			int distanceAbnormalTitan1 =  (int) (Math.random() * 10) + 1;
			int distanceArmoredTitan1 =  (int) (Math.random() * 10) + 20;
			int distancePureTitan1 =  (int) (Math.random() * 5) + 40;
			int distanceColossalTitan1 =  (int) (Math.random() * 10) + 50;

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, distanceAbnormalTitan1, 15, 15, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10,  distanceArmoredTitan1, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(50,  20, 10,  distancePureTitan1, 15, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  distanceColossalTitan1, 15, 25, 2);

			laneTitans.add(abnormalTitan1);
			laneTitans.add(armoredTitan1); 
			laneTitans.add(pureTitan1); 
			laneTitans.add(colossalTitan1);  


			Class wallTrapWeaponClass = Class.forName(wallTrapPath);
			Constructor<?> wallTrapWeaponConstructor = wallTrapWeaponClass.getConstructor(int.class);
			Object wallTrapWeapon = wallTrapWeaponConstructor.newInstance(20);

			Method turnAttack = wallTrapWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(wallTrapWeapon, laneTitans);

			assertTrue("turnAttack Method of WallTrap shouldn't attack any other titan if they are all far from the all",checkAttributesEqualValue(titanClassPath, colossalTitan1, 20, "currentHealth"));
			assertTrue("turnAttack Method of WallTrap shouldn't attack any other titan if they are all far from the all",checkAttributesEqualValue(titanClassPath, pureTitan1, 50, "currentHealth"));
			assertTrue("turnAttack Method of WallTrap shouldn't attack any other titan if they are all far from the all",checkAttributesEqualValue(titanClassPath, armoredTitan1, 70, "currentHealth"));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}

	@Test(timeout=1000)
	public void testWallTrapTurnAttack6()  {

		try {
			Class Titan = Class.forName(titanClassPath);
			PriorityQueue<Object> laneTitans = new PriorityQueue<>();

			int resourcesAbnormalTitan1 =  (int) (Math.random() * 5) + 1;

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan1 =  constructor.newInstance(10,  20, 10, 0, 15, resourcesAbnormalTitan1, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object armoredTitan1 =  constructor2.newInstance(70,  20, 10,  10, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan1 =  constructor3.newInstance(50,  20, 10,  35, 15, 15, 2);

			Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  5, 15, 25, 2);

			laneTitans.add(abnormalTitan1);
			laneTitans.add(armoredTitan1); 
			laneTitans.add(pureTitan1); 
			laneTitans.add(colossalTitan1);  


			Class wallTrapWeaponClass = Class.forName(wallTrapPath);
			Constructor<?> wallTrapWeaponConstructor = wallTrapWeaponClass.getConstructor(int.class);
			Object wallTrapWeapon = wallTrapWeaponConstructor.newInstance(20);

			Method turnAttack = wallTrapWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(wallTrapWeapon, laneTitans);

			assertEquals("turnAttack Method of WallTrap should return resources of attacked titan if he dies",resourcesAbnormalTitan1,returnedResources);

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}


	@Test(timeout=1000)
	public void testPriorityQueueOrderinPiercingCannonTurnAttack() {

		try {

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object titan1 =  constructor.newInstance(10,  20, 10, 20, 15, 5, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object titan2 =  constructor2.newInstance(70,  20, 10,  10, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object titan3 =  constructor3.newInstance(50,  20, 10,  35, 15, 15, 2);

			ArrayList<Object> array= new ArrayList<>();
			array.add(titan2);
			array.add(titan1);
			array.add(titan3);


			PriorityQueue<Object> titansPQ= new PriorityQueue<Object>();
			titansPQ.add(titan2);
			titansPQ.add(titan1);
			titansPQ.add(titan3);

			Class piercingWeaponClass = Class.forName(weaponPiercingCannonPath);
			Constructor<?> piercingWeaponConstructor = piercingWeaponClass.getConstructor(int.class);
			Object piercingWeapon = piercingWeaponConstructor.newInstance(5);

			Method turnAttack = piercingWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			int returnedResources = (int) turnAttack.invoke(piercingWeapon, titansPQ);


			int i =0;
			if(titansPQ.isEmpty())
				fail("Non-defeated titans should NOT be removed from the pripority queue during thre attack");
			while(!titansPQ.isEmpty()) {
				Object object= titansPQ.poll();
				if(object==null)
					fail("Non-defeated titans should NOT be removed from the pripority queue during thre attack");
				assertTrue("Titans should be sorted correctly",object.equals(array.get(i)));
				i++;

			}


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}
	@Test(timeout=1000)
	public void testPriorityQueueOrderinSniperCannonTurnAttack() {

		try {

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object titan1 =  constructor.newInstance(10,  20, 10, 10, 15, 5, 2);

			Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object titan2 =  constructor2.newInstance(70,  20, 10,  35, 15, 15, 2);

			Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object titan3 =  constructor3.newInstance(50,  20, 10,  20, 15, 15, 2);

			ArrayList<Object> array= new ArrayList<>();
			array.add(titan1);
			array.add(titan3);
			array.add(titan2);


			PriorityQueue<Object> titansPQ= new PriorityQueue<Object>();
			titansPQ.add(titan1);
			titansPQ.add(titan3);
			titansPQ.add(titan2);

			Class sniperCannonWeaponClass = Class.forName(sniperCannonPath);
			Constructor<?> sniperWeaponConstructor = sniperCannonWeaponClass.getConstructor(int.class);
			Object sniperWeapon = sniperWeaponConstructor.newInstance(5);

			Method turnAttack = sniperCannonWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
			turnAttack.invoke(sniperWeapon, titansPQ);

			int i =0;
			if(titansPQ.isEmpty())
				fail("Non-defeated titans should NOT be removed from the pripority queue during thre attack");
			while(!titansPQ.isEmpty()) {
				Object object= titansPQ.poll();
				if(object==null)
					fail("Non-defeated titans should NOT be removed from the pripority queue during thre attack");
				assertTrue("Titans should be sorted correctly",object.equals(array.get(i)));
				i++;	
			}


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}





	//	////////////////////////////////     HELPER METHODS    ///////////////////////

	private boolean compare2Walls(Object wall1, Object wall2) {
		try{
			Class wallClass = Class.forName(wallPath);
			Method getBaseHealth = wallClass.getDeclaredMethod("getBaseHealth", null);
			Method getCurrentHealth = wallClass.getDeclaredMethod("getCurrentHealth", null);

			int wall1CurrentHealth = (int) getCurrentHealth.invoke(wall1, null);
			int wall2CurrentHealth = (int) getCurrentHealth.invoke(wall2, null);
			return wall1CurrentHealth == wall2CurrentHealth ;
		}catch(ClassNotFoundException|NoSuchMethodException| SecurityException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			return false;
		}

	}

	private boolean compare2WeaponRegistryWith2(Object actual_obj, Object expected_obj) {
		try{
			Class<?> weaponFactoryClass = Class.forName(weaponRegistryPath);
			Method getCode = weaponFactoryClass.getDeclaredMethod("getCode", null);
			Method getPrice = weaponFactoryClass.getDeclaredMethod("getPrice", null);

			Method getDamage = weaponFactoryClass.getDeclaredMethod("getDamage", null);
			Method getName = weaponFactoryClass.getDeclaredMethod("getName", null);

			Method getMinRange = weaponFactoryClass.getDeclaredMethod("getMinRange", null);
			Method getMaxRange = weaponFactoryClass.getDeclaredMethod("getMaxRange", null);

			int actualCode = (int) getCode.invoke(actual_obj, null);
			int actualPrice = (int) getPrice.invoke(actual_obj, null);

			int expectedCode = (int) getCode.invoke(expected_obj, null);
			int expectedPrice = (int) getPrice.invoke(expected_obj, null);

			int actualDamage = (int) getDamage.invoke(actual_obj, null);

			String actualName = (String) getName.invoke(actual_obj, null);

			int actualMin = (int) getMinRange.invoke(actual_obj, null);

			int actualMax = (int) getMaxRange.invoke(actual_obj, null);

			if (expectedCode > 4)
				return actualCode == expectedCode && actualPrice == expectedPrice && actualDamage == 0 && actualName == null && actualMin == 0 && actualMax == 0;

			return actualCode == expectedCode && actualPrice == expectedPrice;
		}
		catch(ClassNotFoundException| NoSuchMethodException| InvocationTargetException| IllegalAccessException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			return false;
		}
	}

	private boolean compare2WeaponRegistryWith4(Object actual_obj, Object expected_obj) {
		try{
			Class<?> weaponFactoryClass = Class.forName(weaponRegistryPath);
			Method getCode = weaponFactoryClass.getDeclaredMethod("getCode", null);
			Method getPrice = weaponFactoryClass.getDeclaredMethod("getPrice", null);

			Method getDamage = weaponFactoryClass.getDeclaredMethod("getDamage", null);
			Method getName = weaponFactoryClass.getDeclaredMethod("getName", null);

			Method getMinRange = weaponFactoryClass.getDeclaredMethod("getMinRange", null);
			Method getMaxRange = weaponFactoryClass.getDeclaredMethod("getMaxRange", null);

			int actualCode = (int) getCode.invoke(actual_obj, null);
			int actualPrice = (int) getPrice.invoke(actual_obj, null);

			int expectedCode = (int) getCode.invoke(expected_obj, null);
			int expectedPrice = (int) getPrice.invoke(expected_obj, null);

			int actualDamage = (int) getDamage.invoke(actual_obj, null);
			int expectedDamage = (int) getDamage.invoke(expected_obj, null);


			String actualName = (String) getName.invoke(actual_obj, null);
			String expectedName = (String) getName.invoke(expected_obj, null);


			int actualMin = (int) getMinRange.invoke(actual_obj, null);

			int actualMax = (int) getMaxRange.invoke(actual_obj, null);

			if (expectedCode > 4)
				return actualCode == expectedCode && actualPrice == expectedPrice && actualDamage == expectedDamage && actualName.equals(expectedName) && actualMin == 0 && actualMax == 0;

			return actualCode == expectedCode && actualPrice == expectedPrice && actualDamage == expectedDamage && actualName.equals(expectedName);
		}
		catch(ClassNotFoundException| NoSuchMethodException| InvocationTargetException| IllegalAccessException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			return false;
		}
	}

	private boolean compare2WeaponRegistryWith6(Object actual_obj, Object expected_obj) {
		try{
			Class<?> weaponFactoryClass = Class.forName(weaponRegistryPath);
			Method getCode = weaponFactoryClass.getDeclaredMethod("getCode", null);
			Method getPrice = weaponFactoryClass.getDeclaredMethod("getPrice", null);

			Method getDamage = weaponFactoryClass.getDeclaredMethod("getDamage", null);
			Method getName = weaponFactoryClass.getDeclaredMethod("getName", null);

			Method getMinRange = weaponFactoryClass.getDeclaredMethod("getMinRange", null);
			Method getMaxRange = weaponFactoryClass.getDeclaredMethod("getMaxRange", null);

			int actualCode = (int) getCode.invoke(actual_obj, null);
			int actualPrice = (int) getPrice.invoke(actual_obj, null);

			int expectedCode = (int) getCode.invoke(expected_obj, null);
			int expectedPrice = (int) getPrice.invoke(expected_obj, null);

			int actualDamage = (int) getDamage.invoke(actual_obj, null);
			int expectedDamage = (int) getDamage.invoke(expected_obj, null);


			String actualName = (String) getName.invoke(actual_obj, null);
			String expectedName = (String) getName.invoke(expected_obj, null);


			int actualMin = (int) getMinRange.invoke(actual_obj, null);
			int expectedMin = (int) getMinRange.invoke(expected_obj, null);


			int actualMax = (int) getMaxRange.invoke(actual_obj, null);
			int expectedMax = (int) getMaxRange.invoke(expected_obj, null);


			return actualCode == expectedCode && actualPrice == expectedPrice && actualDamage == expectedDamage && actualName.equals(expectedName) && actualMin == expectedMin && actualMax == expectedMax;
		}
		catch(ClassNotFoundException| NoSuchMethodException| InvocationTargetException| IllegalAccessException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			return false;
		}

	}

	private boolean compare2Weapons(Object actual_obj,Object expected_obj) {
		try{
			Class<?> weaponClass = Class.forName(weaponPath);
			Method getDamage = weaponClass.getDeclaredMethod("getDamage", null);
			int actualDamage = (int) getDamage.invoke(actual_obj, null);
			int expectedDamage = (int) getDamage.invoke(expected_obj, null);

			return actualDamage == expectedDamage;
		}
		catch(ClassNotFoundException| NoSuchMethodException| InvocationTargetException| IllegalAccessException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			return false;
		}

	}

	private boolean compare2WeaponsVC(Object actual_obj,Object expected_obj) {
		try{
			Class<?> weaponClass = Class.forName(weaponPath);
			Class<?> vcWeaponClass = Class.forName(volleySpreadCannonPath);
			Method getDamage = weaponClass.getDeclaredMethod("getDamage", null);
			Method getMinRange = vcWeaponClass.getDeclaredMethod("getMinRange", null);
			Method getMaxRange = vcWeaponClass.getDeclaredMethod("getMaxRange", null);

			int actualDamage = (int) getDamage.invoke(actual_obj, null);
			int expectedDamage = (int) getDamage.invoke(expected_obj, null);

			int actualMinRange = (int) getMinRange.invoke(actual_obj, null);
			int expectedMinRange = (int) getMinRange.invoke(expected_obj, null);

			int actualMaxRange = (int) getMaxRange.invoke(actual_obj, null);
			int expectedMaxRange = (int) getMaxRange.invoke(expected_obj, null);


			return actualDamage == expectedDamage && actualMinRange == expectedMinRange && actualMaxRange == expectedMaxRange;
		}
		catch(ClassNotFoundException| NoSuchMethodException| InvocationTargetException| IllegalAccessException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			return false;
		}
	}

	private int returnResourceGatheredTitans(Object laneObject ) throws NoSuchMethodException, SecurityException, 
	ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException, 
	InvocationTargetException, NoSuchFieldException {

		int rV=0;
		Field titansField= Class.forName(lanePath).getDeclaredField("titans");
		titansField.setAccessible(true);
		PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
		Object titan= createAbnormalTitanFixed();
		Object titan2= createAbnormalTitanFixed();
		Object titan3= createAbnormalTitanFixed();

		Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
		titanDistanceField.setAccessible(true);
		titanDistanceField.set(titan, 0);
		titanDistanceField.set(titan2, 0);
		titanDistanceField.set(titan3, 0);


		titansPQ.add(titan);
		titansPQ.add(titan2);
		titansPQ.add(titan3);
		titansField.set(laneObject, titansPQ);

		return -2;
	}
	private int returnResourceGatheredTitans2(Object laneObject ) throws NoSuchMethodException, SecurityException, 
	ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException, 
	InvocationTargetException, NoSuchFieldException {

		int rV=0;
		Field titansField= Class.forName(lanePath).getDeclaredField("titans");
		titansField.setAccessible(true);
		PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
		Object titan= createPureTitan();
		Object titan2= createPureTitan();
		Object titan3= createPureTitan();

		Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
		titanDistanceField.setAccessible(true);
		titanDistanceField.set(titan, 0);
		titanDistanceField.set(titan2, 0);
		titanDistanceField.set(titan3, 0);


		titansPQ.add(titan);
		titansPQ.add(titan2);
		titansPQ.add(titan3);
		titansField.set(laneObject, titansPQ);

		return 0;
	}
	private int returnLaneDangerLevel(Object laneObject ) throws NoSuchMethodException, SecurityException, 
	ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException, 
	InvocationTargetException, NoSuchFieldException {

		Field titansField= Class.forName(lanePath).getDeclaredField("titans");
		titansField.setAccessible(true);
		PriorityQueue<Object> titansPQ= new PriorityQueue<>();
		Object titan= createPureTitan();
		Object titan2= createPureTitan();
		Object titan3= createPureTitan();
		int value=0;
		Field dangerLevelField= Class.forName(titanClassPath).getDeclaredField("dangerLevel");
		dangerLevelField.setAccessible(true);

		int random1 = (int) (Math.random() * 10) + 10;
		value+=random1;
		dangerLevelField.set(titan,random1 );

		random1 = (int) (Math.random() * 10) + 10;
		value+=random1;
		dangerLevelField.set(titan2, random1);

		random1 = (int) (Math.random() * 10) + 10;
		value+=random1;
		dangerLevelField.set(titan3, random1);


		titansPQ.add(titan);
		titansPQ.add(titan2);
		titansPQ.add(titan3);
		titansField.set(laneObject, titansPQ);
		return value;
	}


	private int returnResourceGathered(Object laneObject ) throws NoSuchMethodException, SecurityException, 
	ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException, 
	InvocationTargetException, NoSuchFieldException {


		Field weaponField= Class.forName(lanePath).getDeclaredField("weapons");
		weaponField.setAccessible(true);
		ArrayList<Object> weaponArray=new ArrayList<>();
		weaponArray.add(createWeaponPiercingCannon());
		weaponArray.add(createWeaponPiercingCannon());
		weaponArray.add(createWeaponPiercingCannon());
		weaponField.set(laneObject, weaponArray);


		Field titansField= Class.forName(lanePath).getDeclaredField("titans");
		titansField.setAccessible(true);

		PriorityQueue<Object> titansPQ=new PriorityQueue<>();
		Object titan= createAbnormalTitan();
		Object titan2= createAbnormalTitan();
		Object titan3= createAbnormalTitan();

		Field resources= Class.forName(titanClassPath).getDeclaredField("resourcesValue");
		resources.setAccessible(true);
		int returnedR=resources.getInt(titan)+resources.getInt(titan2)+resources.getInt(titan3);
		titansPQ.add(titan);
		titansPQ.add(titan2);
		titansPQ.add(titan3);
		titansField.set(laneObject, titansPQ);
		return returnedR;

	}

	private Object createPureTitan() {
		try {
			int baseHealth = 100;
			int baseDamage = (int) (Math.random() * 5)+1;
			int heightInMeters = (int) (Math.random() * 5)+1;
			int distanceFromBase = (int) (Math.random() * 5)+30;
			int speed = (int) (Math.random() * 5)+1;
			int dangerLevel = (int) (Math.random() * 5)+1;
			int resourcesValue = (int) (Math.random() * 5)+1;
			Constructor<?> constructor = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object pureTitan = constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,dangerLevel,resourcesValue);
			return pureTitan;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


	private Object createAbnormalTitan() {
		try {
			int baseHealth = 250;
			int baseDamage = (int) (Math.random() * 100)+5;
			int heightInMeters = (int) (Math.random() * 5)+1;
			int distanceFromBase = (int) (Math.random() * 5)+30;
			int speed = (int) (Math.random() * 5)+1;
			int dangerLevel = (int) (Math.random() * 5)+1;
			int resourcesValue = (int) (Math.random() * 5)+1;

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
			return abnormalTitan;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private Object createAbnormalTitanFixed() {
		try {
			int baseHealth = 100;
			int baseDamage = 20;
			int heightInMeters =5;
			int distanceFromBase = 30;
			int speed = 5;
			int dangerLevel = 5;
			int resourcesValue = 10;

			Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
			Object abnormalTitan =  constructor.newInstance(baseHealth, baseDamage, heightInMeters, distanceFromBase,speed,resourcesValue,dangerLevel);
			return abnormalTitan;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


	private Object createWeaponPiercingCannon() {
		try {
			int baseDamage = 200;

			Constructor<?> constructor = Class.forName(weaponPiercingCannonPath).getConstructor(int.class);
			Object piercingCannon =  constructor.newInstance(baseDamage);

			return piercingCannon;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private Object createWeaponVolleySpreadCannon() {
		try {
			int baseDamage = 20;
			int minDistance=0;
			int maxDistance=21;
			Constructor<?> constructor = Class.forName(volleySpreadCannonPath).getConstructor(int.class,int.class,int.class);
			Object piercingCannon =  constructor.newInstance(baseDamage,minDistance,maxDistance);

			return piercingCannon;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private Object createWall() {
		try {
			int baseHealth = 50;

			Constructor<?> constructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall =  constructor.newInstance(baseHealth);

			return wall;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	private Object createWall2() {
		try {
			int baseHealth = 1;

			Constructor<?> constructor = Class.forName(wallPath).getConstructor(int.class);
			Object wall =  constructor.newInstance(baseHealth);

			return wall;
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}




	private boolean checkTwoAttributesEqualValue(String className,Object object1, Object object2, String fieldName)
			throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		Class curr1 = Class.forName(className);

		Field f = null;

		try {
			f = curr1.getDeclaredField(fieldName);
			f.setAccessible(true);

			Object mh1 = f.get(object1);
			Object mh2 = f.get(object2);
			return mh1.equals(mh2);

		} catch (NoSuchFieldException e) {
			curr1 = curr1.getSuperclass();
			fail("Attributes name error");
			return false;
		}

	}

	private boolean checkAttributesEqualValue(String className,Object object1, Object value, String fieldName)
			throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		Class curr1 = Class.forName(className);

		Field f = null;

		try {
			f = curr1.getDeclaredField(fieldName);
			f.setAccessible(true);

			Object mh1 = f.get(object1);

			return mh1.equals(value);

		} catch (NoSuchFieldException e) {
			curr1 = curr1.getSuperclass();
			fail("Attributes name error");
			return false;
		}

	}

	private Object returnEnumValue(String enumClassPath, String enumValue) {
		try {
			return Enum.valueOf((Class<Enum>) Class.forName(enumClassPath), enumValue);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	private void testMethodExistence(Class aClass, String methodName, Class returnType, Class[] parameters) {
		Method[] methods = aClass.getDeclaredMethods();
		assertTrue(aClass.getSimpleName() + " class should have " + methodName + "method",
				containsMethodName(methods, methodName));

		Method m = null;
		boolean thrown = false;
		try {
			m = aClass.getDeclaredMethod(methodName, parameters);
		} catch (NoSuchMethodException e) {
			thrown = true;
		}

		assertTrue("Method " + methodName + " should have the following set of parameters : "
				+ Arrays.toString(parameters), !thrown);
		assertTrue("wrong return type", m.getReturnType().equals(returnType));
	}

	private void testMethodAbsence(Class aClass, String methodName) {
		Method[] methods = aClass.getDeclaredMethods();
		assertFalse(aClass.getSimpleName() + " class should not override " + methodName + " method",
				containsMethodName(methods, methodName));
	}


	private void testInterfaceMethodIsDefault(Class iClass, String methodName, Class[] parameters){
		Method m = null;
		boolean thrown = false;
		try {
			m = iClass.getDeclaredMethod(methodName, parameters);
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertTrue("Method " + methodName + " with the following parameters :" + Arrays.toString(parameters)
		+ " should exist in interface" + iClass.getName(), !thrown);
		assertTrue("Method " + methodName + " should be a default method", m.isDefault());
	}

	private void testInterfaceMethod(Class iClass, String methodName, Class returnType, Class[] parameters) {
		Method[] methods = iClass.getDeclaredMethods();
		assertTrue(iClass.getSimpleName() + " interface should have " + methodName + "method",
				containsMethodName(methods, methodName));

		Method m = null;
		boolean thrown = false;
		try {
			m = iClass.getDeclaredMethod(methodName, parameters);
		} catch (NoSuchMethodException e) {
			thrown = true;
		}

		assertTrue("Method " + methodName + " should have the following set of parameters : "
				+ Arrays.toString(parameters), !thrown);
		assertTrue("wrong return type", m.getReturnType().equals(returnType));

	}

	private static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}




}