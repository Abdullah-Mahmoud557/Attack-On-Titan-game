package game.tests;


import org.junit.Test;

import java.lang.reflect.*;
import java.util.*;

import static org.junit.Assert.*;



public class Milestone2PrivateTests {

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
	public void testUpdatelaneDangerLevelInLaneBattleAll() {

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

			lanesPQ.add(laneObject3);
			lanesPQ.add(laneObject2);
			lanesPQ.add(laneObject);

			lanesField.set(battle, lanesPQ);

			Method updatelaneDangerLevelMethod= Class.forName(battlePath).getDeclaredMethod("updateLanesDangerLevels",  null);
			updatelaneDangerLevelMethod.setAccessible(true);
			updatelaneDangerLevelMethod.invoke(battle);

			assertEquals("All active lane danger level should be updated correctly inside a game battle.", lane1DL,dangerLevelField.get(laneObject));
			assertEquals("All active lane danger level should be updated correctly inside a game battle.", lane2DL,dangerLevelField.get(laneObject2));
			assertEquals("All active lane danger level should be updated correctly inside a game battle.", lane3DL,dangerLevelField.get(laneObject3));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}
	

	@Test(timeout=1000)
	public void testMoveTitansBattle() {

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
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);



			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			lanesPQ.add(laneObject);

			Method moveLaneTitans= Class.forName(battlePath).getDeclaredMethod("moveTitans",  null);
			moveLaneTitans.setAccessible(true);
			moveLaneTitans.invoke(battle);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);


			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			assertEquals("Calling moveTitans should move all titans inside the game",25,titanDistanceField.get(titan) );


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout=1000)
	public void testperformWeaponsAttacksBattleExists() {

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
			Method performWeaponsAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performWeaponsAttacks",  null);
			performWeaponsAttacksmethod.setAccessible(true);
			performWeaponsAttacksmethod.invoke(battle);

			assertTrue("ALL active lanes should not be removed from the active lane list in battle when performing an attack",
					((PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject));
			assertTrue("ALL active lanes should not be removed from the active lane list in battle when performing an attack",
					((PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject2));
			assertTrue("ALL active lanes should not be removed from the active lane list in battle when performing an attack",
					((PriorityQueue<Object>) lanesField.get(battle)).contains(laneObject3));

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
			PriorityQueue<Object> lanesPQ= (PriorityQueue<Object>) lanesField.get(battle);

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
	public void testfinalizeTurnssetPhaseGRUMBLING3() {

		Constructor<?> battleConstructor;
		try {
			battleConstructor = Class.forName(battlePath).getConstructor(int.class,int.class,int.class,int.class,int.class);
			int random1 = (int) (Math.random() * 10) + 5;
			int random2 = (int) (Math.random() * 10) + 5;
			int random3 = (int) (Math.random() * 10) + 5;
			int random4 = (int) (Math.random() * 10) + 5;
			int random5 = (int) (Math.random() * 10) + 1; 
			Object battle = battleConstructor.newInstance(random1,random2,random3,random4,random5);

			random5 = 37;
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

			assertEquals("The number of titans should ONLY be updated when entering levels that are divisible by 5 and >30", 
					random4,numberOfTitansPerTurnField.get(battle));

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
	public void testfinalizeTurnssetPhaseINTENSETitans() {

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
			battlePhase.set(battle, returnEnumValue(battlePhasePath, "EARLY"));

			random5 = 14;
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
	public void testPassTurnMoveTitans() {

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
	public void testPassTurnPerformWeaponsAttacksBattleScore() {

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
			Field scoreField= Class.forName(battlePath).getDeclaredField("score");
			scoreField.setAccessible(true);
			int random6 = (int) (Math.random() * 10) + 1; 
			scoreField.set(battle, random6);

			Method performTurnPerformWeaponsAttacksmethod=Class.forName(battlePath).getDeclaredMethod("passTurn",  null);
			performTurnPerformWeaponsAttacksmethod.setAccessible(true);
			performTurnPerformWeaponsAttacksmethod.invoke(battle);

			scoreField= Class.forName(battlePath).getDeclaredField("score");
			scoreField.setAccessible(true);
			assertEquals("pass turn should perform skipping the turn including  weapons in all lanes carrying out their attacks, Battle Score", value+random6, scoreField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=1000)
	public void testPassTurnUpdatelaneDangerLevelInLaneBattleAll() {

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

			Method performTurnMethod= Class.forName(battlePath).getDeclaredMethod("passTurn",  null);
			performTurnMethod.setAccessible(true);
			performTurnMethod.invoke(battle);

			assertEquals("pass turn should perform skipping the turn including updating the dangerLevel for each active lane", lane1DL,dangerLevelField.get(laneObject));
			assertEquals("pass turn should perform skipping the turn including updating the dangerLevel for each active lane", lane2DL,dangerLevelField.get(laneObject2));
			assertEquals("pass turn should perform skipping the turn including updating the dangerLevel for each active lane", lane3DL,dangerLevelField.get(laneObject3));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}

	}
	@Test(timeout=1000)
	public void testPerformTurnperformWeaponsAttacksBattleResourcesGathered() {

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
			Field resourcesGatheredField= Class.forName(battlePath).getDeclaredField("resourcesGathered");
			resourcesGatheredField.setAccessible(true);
			int random6 = (int) (Math.random() * 10) + 1; 
			resourcesGatheredField.set(battle, random6);

			Method performTurnPerformWeaponsAttacksmethod=Class.forName(battlePath).getDeclaredMethod("performTurn",  null);
			performTurnPerformWeaponsAttacksmethod.setAccessible(true);
			performTurnPerformWeaponsAttacksmethod.invoke(battle);

			resourcesGatheredField= Class.forName(battlePath).getDeclaredField("resourcesGathered");
			resourcesGatheredField.setAccessible(true);
			assertEquals("performTurn should perform the main functionalities throughout each turn including  weapons in all lanes carrying out their attacks, ResourcesGathered in battle", value+random6, resourcesGatheredField.get(battle));


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout=100)
	public void testUpdateLanesDangerLevelsinBattlePrivate() {
		Method m;
		try {
			m = Class.forName(battlePath).getDeclaredMethod("updateLanesDangerLevels",null);
			assertTrue("updateLanesDangerLevels should be private",Modifier.isPrivate(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout=100)
	public void testFinalizeTurnsinBattlePrivate() {
		Method m;
		try {
			m = Class.forName(battlePath).getDeclaredMethod("finalizeTurns",null);
			assertTrue("finalizeTurns should be private",Modifier.isPrivate(m.getModifiers()));
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout=1000)
	public void testAddTurnTitansToLaneBattleOnce() {
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
			
			Method m= Class.forName(battlePath).getDeclaredMethod("performTurn", null);
			m.setAccessible(true);
			m.invoke(battle);
			
			assertTrue("performTurn should only call addTurnTitansToLane once", 
					((ArrayList<Object>)approachingTitans.get(battle)).size()==2);
			assertTrue("performTurn should only call addTurnTitansToLane once",
					((PriorityQueue<Object>)titansLaneField.get(laneObject4)).contains(titan));
			assertTrue("performTurn should only call addTurnTitansToLane once",
					((PriorityQueue<Object>)titansLaneField.get(laneObject4)).contains(titan2));
			assertFalse("performTurn should only call addTurnTitansToLane once", 
					((PriorityQueue<Object>)titansLaneField.get(laneObject4)).contains(titan3));
			assertFalse("performTurn should only call addTurnTitansToLane once", 
					((PriorityQueue<Object>)titansLaneField.get(laneObject4)).contains(titan4));
			assertFalse("performTurn should only call addTurnTitansToLane once", 
					((ArrayList<Object>)approachingTitans.get(battle)).contains(titan));
			assertFalse("performTurn should only call addTurnTitansToLane once", 
					((ArrayList<Object>)approachingTitans.get(battle)).contains(titan2));
			
			


		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException |
				InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchFieldException e) {
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
	public void testAddTitanLane() {
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
			int len = t.size() ;
			assertEquals(
					"The method addTitan(Titan titan) should add the input titan to the priorityQueue of titans."+"The size",1, len);


		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}
	}	

	@Test(timeout = 1000)
	public void testRefillApproachingTitansBattleGRUMBLING() {

		int numberOfTurns = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int titanSpawnDistance = (int) (Math.random() * 10) + 1;
		int initialNumOfLanes = (int) (Math.random() * 10) + 1;
		int initialResourcesPerLane = (int) (Math.random() * 10) + 1;
		try {
			Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
			Object b = constructor.newInstance(numberOfTurns, score, titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			Object grumbling= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "GRUMBLING");
			Field f = null;
			Class<? extends Object> curr = b.getClass();
			f = curr.getDeclaredField("battlePhase");
			f.setAccessible(true);
			f.set(b, grumbling);
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
				System.out.println("here");
				correct=false;}
			else {
				for(int i=0;i<t.size();i++) {

					if(t.get(i).getClass() != Class.forName(ColossalTitanClassPath))
						correct=false;

				}


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
	public void testRefillApproachingTitansBattleGRUMBLING2(){

		int numberOfTurns = (int) (Math.random() * 10) + 1;
		int score = (int) (Math.random() * 10) + 1;
		int titanSpawnDistance = (int) (Math.random() * 10) + 1;
		int initialNumOfLanes = (int) (Math.random() * 10) + 1;
		int initialResourcesPerLane = (int) (Math.random() * 10) + 1;
		try {
			Constructor<?> constructor = Class.forName(battlePath).getConstructor( int.class, int.class, int.class,int.class, int.class);
			Object b = constructor.newInstance(numberOfTurns, score, titanSpawnDistance,initialNumOfLanes,initialResourcesPerLane);
			Object grumbling= Enum.valueOf((Class<Enum>) Class.forName(battlePhasePath), "GRUMBLING");
			Field f = null;
			Class<? extends Object> curr = b.getClass();
			f = curr.getDeclaredField("battlePhase");
			f.setAccessible(true);
			f.set(b, grumbling);
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

	@Test(timeout=1000)
	public void testMoveLaneTitansExistance() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitan();
			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			Method moveLaneTitans= Class.forName(lanePath).getDeclaredMethod("moveLaneTitans",  null);
			moveLaneTitans.invoke(laneObject);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			assertTrue("Removing titan while moving lane failure", titansPQ.contains(titan));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}
	@Test(timeout=1000)
	public void testMoveLaneTitansMovedOneStep() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			Object titan= createAbnormalTitanFixed();
			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);
			titanDistanceField.set(titan, 5);

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

			Method hasReachedTargetMethod=  Class.forName(mobilPath).getDeclaredMethod("hasReachedTarget",  null);

			assertEquals("moveLaneTitans should update all relative attributes for the titans once they move",0,titanDistanceField.get(titan) );
			assertTrue("moveLaneTitans should update all relative attributes and methods for the titans once they move",(boolean)hasReachedTargetMethod.invoke(titan));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}
	@Test(timeout=1000)
	public void testMoveLaneTitansMoved() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			PriorityQueue<Object> titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);
			Object titan= createAbnormalTitanFixed();
			titansPQ.add(titan);
			titansField.set(laneObject, titansPQ);

			Method moveLaneTitans= Class.forName(lanePath).getDeclaredMethod("moveLaneTitans",  null);
			moveLaneTitans.invoke(laneObject);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);
			titansPQ= (PriorityQueue<Object>) titansField.get(laneObject);

			Field titanDistanceField= Class.forName(titanClassPath).getDeclaredField("distanceFromBase");
			titanDistanceField.setAccessible(true);

			assertEquals("titans inside a lane should move when calling moevLaneTitans",25,titanDistanceField.get(titan) );

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}
	@Test(timeout=1000)
	public void testPriorityQueueOrderinLaneTitanMove2() {

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
			titanDistanceField.set(titan, 20);
			titanDistanceField.set(titan2, 10);
			titanDistanceField.set(titan3, 5);

			ArrayList<Object> array= new ArrayList<>();
			array.add(titan3);
			array.add(titan2);
			array.add(titan);

			Field titanSpeedField= Class.forName(titanClassPath).getDeclaredField("speed");
			titanSpeedField.setAccessible(true);
			titanSpeedField.set(titan, 5);
			titanSpeedField.set(titan2, 5);
			titanSpeedField.set(titan3, 5);

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
			while(!titansPQ.isEmpty()) {
				Object object= titansPQ.poll();
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
	public void testPerformLaneTitansAttacksNoTitans() {

		try {

			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(createWall());
			Field titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);


			Method moveLaneTitans= Class.forName(lanePath).getDeclaredMethod("performLaneTitansAttacks",  null);

			titansField= Class.forName(lanePath).getDeclaredField("titans");
			titansField.setAccessible(true);

			assertEquals("performLaneTitansAttacks should return the correct gained values from the titans attacks in the lane on the wall", 0,moveLaneTitans.invoke(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}
	
	@Test(timeout=1000)
	public void testPerformLaneWeaponAttacksNoTitans() {

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

			Method weaponAttack= Class.forName(lanePath).getDeclaredMethod("performLaneWeaponsAttacks",  null);


			assertEquals("performLaneWeaponsAttacks should return the correct gained values from the weapons attack",0 ,weaponAttack.invoke(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}
	@Test(timeout=1000)
	public void testUpdatelaneDangerLevelInLane1() {

		try {

			Object wall=createWall();


			Constructor<?> constructor = Class.forName(lanePath).getConstructor(Class.forName(wallPath));
			Object laneObject =  constructor.newInstance(wall);


			Method updatelaneDangerLevelMethod= Class.forName(lanePath).getDeclaredMethod("updateLaneDangerLevel",  null);
			updatelaneDangerLevelMethod.invoke(laneObject);
			Field dangerLevelField= Class.forName(lanePath).getDeclaredField("dangerLevel");
			dangerLevelField.setAccessible(true);

			assertEquals("The lane danger level is not updated correctly.", 0,dangerLevelField.get(laneObject));

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
		}

	}
	
	
	

	@Test(timeout = 1000)
	public void testspawnTitanPureTitanCaseBaseHealth() {
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
	public void testspawnTitanPureTitanCaseBaseDamage(){
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
	public void testspawnTitanPureTitanCaseHeightInMeters() {
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
	public void testspawnTitanAbnormalTitanCaseBaseHealth() {
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
	public void testspawnTitanAbnormalTitanCaseResources()  {
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
	public void testspawnTitanAbnormalTitanCaseDangerLevel() {
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
	public void testspawnTitanArmoredTitanCaseSpeed() {
		try {
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
	public void testspawnTitanArmoredTitanCaseResources() {
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
	public void testspawnTitanArmoredTitanCaseDangerLevel() {
		try {
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
	public void testspawnTitanColossalTitanCaseBaseHealth() {
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
	public void testspawnTitanColossalTitanCaseBaseDamage() {
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
	public void testspawnTitanColossalTitanCaseHeights() {
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
	public void testbuildWeaponDefaultCase()  {
		try{
			int code = 5;
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

			assertNull(weapon);
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
		}
	@Test(timeout = 1000)
	public void testbuildWeaponVollySpreadCaseDamage() {
		try{
			int code = 3;
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

			Class superClass = Class.forName(volleySpreadCannonPath).getSuperclass();

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
	public void testAddWeaponToShopWith6Attributes() {
		try{
			int code = (int) (Math.random() * 20) + 1;
			int price = (int) (Math.random() * 20) + 1;
			int damage = (int) (Math.random() * 20) + 1;
			int randomNameId = (int) (Math.random() * 5) + 1;
			int max = (int) (Math.random() * 30) + 1;
			int min = (int) (Math.random() * 5) + 1;


			Class<?> weaponFactoryClass = Class.forName(weaponFactoryPath);

			Constructor<?> weaponFactoryConstructor = Class.forName(weaponFactoryPath).getConstructor();
			Object weaponFactory = weaponFactoryConstructor.newInstance();

			Constructor<?> weaponRegistryConstructor = Class.forName(weaponRegistryPath).getConstructor(int.class, int.class, int.class, String.class, int.class, int.class);
			Object weaponRegistry = weaponRegistryConstructor.newInstance(code, price, damage, ("Name_" + randomNameId), min, max);




			Method getWeaponShop = weaponFactoryClass.getDeclaredMethod("getWeaponShop");
			HashMap<Integer, Object> weaponShop_expected_shallow = (HashMap<Integer, Object>) getWeaponShop.invoke(weaponFactory);
			HashMap<Integer, Object> weaponShop_expected = new HashMap<Integer, Object>();
			for (Map.Entry<Integer, Object> entry : weaponShop_expected_shallow.entrySet()) {
				weaponShop_expected.put(entry.getKey(), entry.getValue());
			}
			weaponShop_expected.put(code, weaponRegistry);


			Class[] attributes = {int.class, int.class, int.class, String.class, int.class, int.class};
			Method addWeaponToShopMethod = weaponFactoryClass.getDeclaredMethod("addWeaponToShop", attributes);
			addWeaponToShopMethod.invoke(weaponFactory, code, price, damage, ("Name_" + randomNameId), min, max);


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
				assertTrue("The 2 hashmaps should be equal", compare2WeaponRegistryWith6(curr_actual, curr_expected));
			}
		}catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException| InvocationTargetException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout = 1000)
	public void testBuyWeaponPassCaseOtherCodesDamage() {
		try{
			int randomNum = (int) (Math.random() * 4) + 1;
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

			Class<?> weaponClass = Class.forName(weaponPath);
			Method getBaseDamage = weaponClass.getDeclaredMethod("getDamage", null);

			int expected_baseDamage = (int) getBaseDamage.invoke(weapon, null);

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			Object factoryResponse = buyWeapon.invoke(weaponFactory, resources, code);

			Class<?> factory_response = Class.forName(factoryResponsePath);
			Constructor<?> factory_response_Constructor = Class.forName(factoryResponsePath).getConstructor(Class.forName(weaponPath), int.class);

			Method getWeapon = factory_response.getDeclaredMethod("getWeapon", null);

			Object actual_weapon = getWeapon.invoke(factoryResponse, null);
			int actual_base_damage = (int) getBaseDamage.invoke(actual_weapon, null);

			assertEquals("The Weapon/s base damage isn't correct ", expected_baseDamage, actual_base_damage);
		}catch(NullPointerException| InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon,Please check the console for the error");

		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	@Test(timeout = 1000)
	public void testBuyWeaponPassCaseCode3Resources() {
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

			int expected_remainig_resources = resources - price;

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			Object factoryResponse = buyWeapon.invoke(weaponFactory, resources, code);

			Class<?> factory_response = Class.forName(factoryResponsePath);
			Method getResources = factory_response.getDeclaredMethod("getRemainingResources", null);

			int actual_remaining_resources = (int) getResources.invoke(factoryResponse, null);

			assertEquals("The number of resources isn't correct", expected_remainig_resources, actual_remaining_resources);
		}catch(NullPointerException| InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon,Please check the console for the error");

		}
		catch( ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout = 1000)
	public void testBuyWeaponPassCaseCode3Damage() {
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

			Class<?> weaponClass = Class.forName(weaponPath);
			Method getBaseDamage = weaponClass.getDeclaredMethod("getDamage", null);


			int expected_baseDamage = (int) getBaseDamage.invoke(weapon, null);

			Method buyWeapon = weaponFactoryClass.getDeclaredMethod("buyWeapon", int.class, int.class);
			Object factoryResponse = buyWeapon.invoke(weaponFactory, resources, code);

			Class<?> factory_response = Class.forName(factoryResponsePath);
			Method getWeapon = factory_response.getDeclaredMethod("getWeapon", null);


			Object actual_weapon = getWeapon.invoke(factoryResponse, null);
			int actual_base_damage = (int) getBaseDamage.invoke(actual_weapon, null);

			assertEquals("The Weapon's base damage isn't correct", expected_baseDamage, actual_base_damage);
		}catch(NullPointerException|InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect weapon code,Please check the console for the error");

		}
		catch( ClassNotFoundException| NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout = 1000)
	public void testPurchaseWeaponSameArrayListSize() {

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

			Class[] attributes = {int.class, laneClass};
			Method purchase_weapon = battleClass.getDeclaredMethod("purchaseWeapon", attributes);
			purchase_weapon.invoke(battle, weaponCode, lane);


			ArrayList<Object> weaponsShallowActual = (ArrayList<Object>) getWeapons.invoke(lane, null);
			ArrayList<Object> weaponsDeepColActual = new ArrayList<>();
			for (Object weapon : weaponsShallowActual)
				weaponsDeepColActual.add(weapon);

			assertEquals("The 2 arrayList should have equal size", weaponsDeepColExpected.size(), weaponsDeepColActual.size());
		}catch(InvocationTargetException e1) {
			e1.printStackTrace();
			fail("Incorrect Weapon,Please check the console for the error");
		}
		catch(ClassNotFoundException|NoSuchMethodException| SecurityException| InstantiationException| IllegalAccessException| IllegalArgumentException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout = 1000)
	public void testGameOverLogicFalseCase() {

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
			boolean isGameOverOutput = (boolean) isGameOver.invoke(battle);
			assertFalse("Expected is false but was " + isGameOverOutput, isGameOverOutput);
		}catch(ClassNotFoundException| NoSuchMethodException| IllegalAccessException| InvocationTargetException| InstantiationException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout = 1000)
	public void testGameOverLogicNoChangeInTitanHealth() {

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
			Object abnormalTitan1 =  constructor.newInstance(250,  20, 10, 30, 15, 15, 2);
			Object weapon = createWeaponPiercingCannon();
			Method getLanes = battleClass.getDeclaredMethod("getLanes", null);
			
			PriorityQueue<Object> lanesBeforeInvokation = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			Method addT = laneClass.getDeclaredMethod("addTitan", Class.forName(titanClassPath));
			Method addWeapon = laneClass.getDeclaredMethod("addWeapon", Class.forName(weaponPath));
			for(Object l: lanesBeforeInvokation) {
				addT.invoke(l, abnormalTitan1);
				addWeapon.invoke(l, weapon);
			}			
			Method isGameOver = battleClass.getDeclaredMethod("isGameOver", null);
			isGameOver.invoke(battle);
			Method getTitans = laneClass.getDeclaredMethod("getTitans", null);
			PriorityQueue<Object> lanesAfterInvokation = (PriorityQueue<Object>) getLanes.invoke(battle, null);
			Class titanClass = Class.forName(titanClassPath);
			Method getCurrentHealth = titanClass.getDeclaredMethod("getCurrentHealth", null);
			boolean equality = true;
			for(Object l: lanesAfterInvokation) {
				PriorityQueue<Object> titansInsideEachLane= (PriorityQueue<Object>) getTitans.invoke(l, null);
				Object titan = titansInsideEachLane.poll();
				int currentH = (int) getCurrentHealth.invoke(titan, null);
				equality = currentH == 250;
			}
			assertTrue("GameOver method shouldn't attack titans",equality);
		}catch( ClassNotFoundException| NoSuchMethodException| IllegalAccessException| InvocationTargetException| InstantiationException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	@Test(timeout = 1000)
	public void testGameOverLogicNoChangeInNumberOfTurns()  {

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
				
			Method isGameOver = battleClass.getDeclaredMethod("isGameOver", null);
			isGameOver.invoke(battle);
			Method getNumberOfTurns = battleClass.getDeclaredMethod("getNumberOfTurns", null);
			int NumberOfTurns = (int) getNumberOfTurns.invoke(battle, null);
			assertEquals("isGameOver shouldn't affect number of turns",numberOfTurns,NumberOfTurns);
			
		}catch( ClassNotFoundException| NoSuchMethodException| IllegalAccessException| InvocationTargetException| InstantiationException| SecurityException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	
	
	@Test(timeout = 1000)
	public void testAddWeaponLogicArraySameSize()  {

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

			assertEquals("The 2 arrayLists should have equal length", weaponsDeepColExpected.size(), weaponsDeepColActual.size());
		}catch(ClassNotFoundException| NoSuchMethodException| SecurityException|IllegalAccessException| InvocationTargetException| InstantiationException e){
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
//
	
	
	@Test(timeout=1000)
	public void testIsDefeatedMethodIsInAttackee() {
		try {
			testInterfaceMethod(Class.forName(attackeePath), "isDefeated", boolean.class, null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}

	@Test(timeout=1000)
	public void testIsDefeatedMethodIsDefault(){
		try {
			testInterfaceMethodIsDefault(Class.forName(attackeePath), "isDefeated", null);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
		}
	}
	
	// takeDamage
		@Test(timeout=1000)
		public void testTakeDamageMethodInAttackee() {
			try {
				testInterfaceMethod(Class.forName(attackeePath), "takeDamage", int.class, new Class[] { int.class });
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			}
		}

		@Test(timeout=1000)
		public void testTakeDamageIsDefault() {
			try {
				testInterfaceMethodIsDefault(Class.forName(attackeePath), "takeDamage", new Class[] { int.class });
			} catch (SecurityException| ClassNotFoundException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			}
		}
	
		@Test(timeout=1000)
		public void testAttackMethodInAttacker() {
			try {
				testInterfaceMethod(Class.forName(attackerPath), "attack", int.class,
						new Class[] { Class.forName(attackeePath) });
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			}
		}

		@Test(timeout=1000)
		public void testAttackMethodIsDefault() {
			try {
				testInterfaceMethodIsDefault(Class.forName(attackerPath), "attack",
												new Class[] { Class.forName(attackeePath) });
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statement."+e.getClass()+" occurred");
			}
		}
	
		
//		

		@Test(timeout=1000)
		public void testSniperWeaponTurnAttack3(){
			try {
				Class Titan = Class.forName(titanClassPath);
		        PriorityQueue<Object> laneTitans = new PriorityQueue<>();
		        
		
				Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);
				Object abnormalTitan2 =  constructor.newInstance(100, 20, 10, 30, 15, 15, 2);
				
				Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object armoredTitan1 =  constructor2.newInstance(70,  20, 10, 1, 15, 15, 2);
				Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, 50, 15, 15, 2);
				
				laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
				laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
				
				Class sniperWeaponClass = Class.forName(sniperCannonPath);
				Constructor<?> sniperWeaponConstructor = sniperWeaponClass.getConstructor(int.class);
				Object sniperWeapon = sniperWeaponConstructor.newInstance(20);
				
				Method turnAttack = sniperWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
				int returnedResources = (int) turnAttack.invoke(sniperWeapon, laneTitans);
				
				assertEquals("turnAttack Method shouldn't remove any titans in the lane if none of them died", 4, laneTitans.size());
			
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
					InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}
		}

		@Test(timeout=1000)
		public void testSniperTurnAttack4() {
			try {
		        PriorityQueue<Object> laneTitans = new PriorityQueue<>();
		        
				Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 15, 15, 15, 2);
				Object abnormalTitan2 =  constructor.newInstance(100, 20, 10, 30, 15, 15, 2);
				
				Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object armoredTitan1 =  constructor2.newInstance(70,  20, 10, 1, 15, 15, 2);
				Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, 50, 15, 15, 2);
				
				laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
				laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
				
				Class sniperWeaponClass = Class.forName(sniperCannonPath);
				Constructor<?> sniperWeaponConstructor = sniperWeaponClass.getConstructor(int.class);
				Object sniperWeapon = sniperWeaponConstructor.newInstance(20);
				
				Method turnAttack = sniperWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
				int returnedResources = (int) turnAttack.invoke(sniperWeapon, laneTitans);
				
				assertEquals(0, returnedResources); 
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}

	    }
		
		@Test(timeout=1000)
		public void testSniperWeaponTurnAttackOnDeathOfClosestTitan1() {
			try {
		        PriorityQueue<Object> laneTitans = new PriorityQueue<>();
		        
		        int distanceColossalTitan1 =  (int) (Math.random() * 10) + 30;
		        int distanceColossalTitan2 =  (int) (Math.random() * 5) + 2;
		        int distanceArmoredTitan1 =  (int) (Math.random() * 10) + 20;
		        int distanceArmoredTitan2 =  (int) (Math.random() * 10) + 50;
		       
				Constructor<?> constructor = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object colossalTitan1 =  constructor.newInstance(80, 20, 10, distanceColossalTitan1, 15, 15, 2);
				Object colossalTitan2 =  constructor.newInstance(20, 20, 10,  distanceColossalTitan2 , 15, 50, 2);
				
				Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object armoredTitan1 =  constructor2.newInstance(20,  20, 10, distanceArmoredTitan1, 15, 15, 2);
				Object armoredTitan2 =  constructor2.newInstance(100, 20, 10, distanceArmoredTitan2, 15, 15, 2);
				
				laneTitans.add(colossalTitan1); laneTitans.add(colossalTitan2);
				laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
				
				Class sniperWeaponClass = Class.forName(sniperCannonPath);
				Constructor<?> sniperWeaponConstructor = sniperWeaponClass.getConstructor(int.class);
				Object sniperWeapon = sniperWeaponConstructor.newInstance(20);
				
				Method turnAttack = sniperWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
				turnAttack.invoke(sniperWeapon, laneTitans);
				
				assertTrue(checkAttributesEqualValue(titanClassPath, colossalTitan2, 0, "currentHealth"));

			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}
	    }
		
		@Test(timeout=1000)
		public void testSniperWeaponTurnAttackOnDeathOfClosestTitan2() {
			try {
		        PriorityQueue<Object> laneTitans = new PriorityQueue<>();
		        int distanceColossalTitan1 =  (int) (Math.random() * 10) + 30;
		        int distanceColossalTitan2 =  (int) (Math.random() * 5) + 2;
		        int distanceArmoredTitan1 =  (int) (Math.random() * 10) + 20;
		        int distanceArmoredTitan2 =  (int) (Math.random() * 10) + 50;
		       
		        int healthColossalTitan1 =  (int) (Math.random() * 10) + 80;
		        int healthColossalTitan2 =  (int) (Math.random() * 10) + 20;
		        int healthArmoredTitan1 =  (int) (Math.random() * 10) + 20;
		        int healthArmoredTitan2 =  (int) (Math.random() * 10) + 100;
		        
				Constructor<?> constructor = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object colossalTitan1 =  constructor.newInstance(healthColossalTitan1, 20, 10, distanceColossalTitan1, 15, 15, 2);
				Object colossalTitan2 =  constructor.newInstance(healthColossalTitan2, 20, 10,  distanceColossalTitan2 , 15, 50, 2);
				
				Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object armoredTitan1 =  constructor2.newInstance(healthArmoredTitan1,  20, 10, distanceArmoredTitan1, 15, 15, 2);
				Object armoredTitan2 =  constructor2.newInstance(healthArmoredTitan2, 20, 10, distanceArmoredTitan2, 15, 15, 2);
				
				laneTitans.add(colossalTitan1); laneTitans.add(colossalTitan2);
				laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
				
				Class sniperWeaponClass = Class.forName(sniperCannonPath);
				Constructor<?> sniperWeaponConstructor = sniperWeaponClass.getConstructor(int.class);
				Object sniperWeapon = sniperWeaponConstructor.newInstance(20);
				
				Method turnAttack = sniperWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
				turnAttack.invoke(sniperWeapon, laneTitans);
			
				assertTrue(checkAttributesEqualValue(titanClassPath, colossalTitan1, healthColossalTitan1, "currentHealth"));
				assertTrue(checkAttributesEqualValue(titanClassPath, armoredTitan1, healthArmoredTitan1, "currentHealth"));
				assertTrue(checkAttributesEqualValue(titanClassPath, armoredTitan2, healthArmoredTitan2, "currentHealth"));

			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
					InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
				}
	    }

		@Test(timeout=1000)
		public void testPiercingWeaponTurnAttack1b() {
			try {
				PriorityQueue<Object> laneTitans = new PriorityQueue<>();
		        
				Constructor<?> constructor = Class.forName(AbnormalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object abnormalTitan1 =  constructor.newInstance(50,  20, 10, 7, 15, 15, 2);
				Object abnormalTitan2 =  constructor.newInstance(5, 20, 10, 2, 15, 15, 2);
				
				Constructor<?> constructor2 = Class.forName(ArmoredTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object armoredTitan1 =  constructor2.newInstance(2,  20, 10, 65, 15, 15, 2);
				Object armoredTitan2 =  constructor2.newInstance(1, 20, 10, 5, 15, 15, 2);
				
				Constructor<?> constructor3 = Class.forName(PureTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object pureTitan1 =  constructor3.newInstance(60,  20, 10,  1, 50, 15, 2);
				Object pureTitan2 =  constructor3.newInstance(100, 20, 10, 50, 5, 15, 2);
				
				Constructor<?> constructor4 = Class.forName(ColossalTitanClassPath).getConstructor(int.class,int.class,int.class,int.class,int.class,int.class,int.class);
				Object colossalTitan1 =  constructor4.newInstance(20,  20, 10,  25, 15, 25, 2);
				Object colossalTitan2 =  constructor4.newInstance(100, 20, 10, 8, 15, 15, 2);
				
				laneTitans.add(abnormalTitan1); laneTitans.add(abnormalTitan2);
				laneTitans.add(armoredTitan1);  laneTitans.add(armoredTitan2);
				laneTitans.add(pureTitan1);  laneTitans.add(pureTitan2);
				laneTitans.add(colossalTitan1);  laneTitans.add(colossalTitan2);
				
				
				Class piercingWeaponClass = Class.forName(weaponPiercingCannonPath);
				Constructor<?> piercingWeaponConstructor = piercingWeaponClass.getConstructor(int.class);
				Object piercingWeapon = piercingWeaponConstructor.newInstance(10);
				
				Method turnAttack = piercingWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
				int returnedResources = (int) turnAttack.invoke(piercingWeapon, laneTitans);
				
				assertTrue("turnAttack Method of PiercingCannon fails to attack closest 5 Titans",checkAttributesEqualValue(titanClassPath, abnormalTitan1, 40, "currentHealth"));
				assertTrue("turnAttack Method of PiercingCannon fails to attack closest 5 Titans",checkAttributesEqualValue(titanClassPath, abnormalTitan2, 0, "currentHealth"));
				assertTrue("turnAttack Method of PiercingCannon fails to attack closest 5 Titans",checkAttributesEqualValue(titanClassPath, armoredTitan2, 0, "currentHealth"));
				assertTrue("turnAttack Method of PiercingCannon fails to attack closest 5 Titans",checkAttributesEqualValue(titanClassPath, pureTitan1, 50, "currentHealth"));
				assertTrue("turnAttack Method of PiercingCannon fails to attack closest 5 Titans",checkAttributesEqualValue(titanClassPath, colossalTitan2, 90, "currentHealth"));
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
					InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}
	    }
		
		@Test(timeout=1000)
		public void testPiercingWeaponTurnAttack3(){
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
				

				assertTrue("Living titans shouldn't be removed from lane on attack",laneTitans.contains(abnormalTitan1));
				assertTrue("Living titans shouldn't be removed from lane on attack",laneTitans.contains(armoredTitan1));
				assertTrue("Living titans shouldn't be removed from lane on attack",laneTitans.contains(pureTitan1));
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
					InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}

	    }

		@Test(timeout=1000)
		public void testPiercingWeaponTurnAttack5() {
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
				
				assertTrue("Unattacked titans shouldn't be removed from lane on attack",laneTitans.contains(armoredTitan1));
				assertTrue("Unattacked titans shouldn't be removed from lane on attack",laneTitans.contains(pureTitan1));
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
					InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}
	    }
		
		@Test(timeout=1000)
		public void testVolleySpreadTurnAttack1() {
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
				
				assertTrue("turnAttack Method of PiercingCannon fails to attack Titans within min/max range",checkAttributesEqualValue(titanClassPath, abnormalTitan1, 30, "currentHealth"));
				assertTrue("turnAttack Method of PiercingCannon fails to attack Titans within min/max range",checkAttributesEqualValue(titanClassPath, abnormalTitan2, 0, "currentHealth"));
				assertTrue("turnAttack Method of PiercingCannon fails to attack Titans within min/max range",checkAttributesEqualValue(titanClassPath, armoredTitan2, 95, "currentHealth"));
				assertTrue("turnAttack Method of PiercingCannon fails to attack Titans within min/max range",checkAttributesEqualValue(titanClassPath, pureTitan1, 0, "currentHealth"));
				assertTrue("turnAttack Method of PiercingCannon fails to attack Titans within min/max range",checkAttributesEqualValue(titanClassPath, colossalTitan2, 80, "currentHealth"));
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
					InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}
	    }
		@Test(timeout=1000)
		public void testVolleySpreadTurnAttack2() {
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
				
				assertTrue("turnAttack Method shouldn't affect titans outside min/max range",checkAttributesEqualValue(titanClassPath, colossalTitan1, 20, "currentHealth"));
				assertTrue("turnAttack Method shouldn't affect titans outside min/max range",checkAttributesEqualValue(titanClassPath, pureTitan2, 100, "currentHealth"));
				assertTrue("turnAttack Method shouldn't affect titans outside min/max range", checkAttributesEqualValue(titanClassPath, armoredTitan1, 70, "currentHealth"));
			
	       } catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
					InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}
		}
		
		@Test(timeout=1000)
		public void testVolleySpreadTurnAttack6(){
			
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
				
				assertEquals("Method turnAttack should return the total retrieved resources of dead titans",30, returnedResources); 
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
					InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}
		}

		@Test(timeout=1000)
		public void testWallTrapTurnAttack2() {
			try {
				Class Titan = Class.forName(titanClassPath);
		        PriorityQueue<Object> laneTitans = new PriorityQueue<>();
		        
		        int distanceAbnormalTitan1 =  (int) (Math.random() * 10) + 30;
		        int distanceArmoredTitan2 =  (int) (Math.random() * 10) + 20;
		        int distancePureTitan1 =  (int) (Math.random() * 5) + 1;
		        int distanceColossalTitan2 =  (int) (Math.random() * 10) + 50;
		
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
				
			
				assertTrue("turnAttack Method shouldn't affect far titans",checkAttributesEqualValue(titanClassPath, colossalTitan1, 20, "currentHealth"));
				assertTrue("turnAttack Method shouldn't affect far titans",checkAttributesEqualValue(titanClassPath, abnormalTitan1, 50, "currentHealth"));
				assertTrue("turnAttack Method shouldn't affect far titans", checkAttributesEqualValue(titanClassPath, armoredTitan1, 70, "currentHealth"));
			
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
					InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
			}
		}
		

		@Test(timeout=1000)
		public void testWallTrapTurnAttack7()  {
	     
			try {
				Class Titan = Class.forName(titanClassPath);
		        PriorityQueue<Object> laneTitans = new PriorityQueue<>();
		      
				
				Class wallTrapWeaponClass = Class.forName(wallTrapPath);
				Constructor<?> wallTrapWeaponConstructor = wallTrapWeaponClass.getConstructor(int.class);
				Object wallTrapWeapon = wallTrapWeaponConstructor.newInstance(20);
				
				Method turnAttack = wallTrapWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
				int returnedResources = (int) turnAttack.invoke(wallTrapWeapon, laneTitans);
				
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | 
					InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					fail("Wall Trap should attack if a titans exists");
			}
		}
		

		
		@Test(timeout=1000)
		public void testPriorityQueueOrderinWallTrapPathTurnAttack() {
		
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

				Class wallTrapWeaponClass = Class.forName(wallTrapPath);
				Constructor<?> wallTrapWeaponConstructor = wallTrapWeaponClass.getConstructor(int.class);
				Object wallTrapWeapon = wallTrapWeaponConstructor.newInstance(5);
				
				Method turnAttack = wallTrapWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
				turnAttack.invoke(wallTrapWeapon, titansPQ);

				int i =0;
				while(!titansPQ.isEmpty()) {
					Object object= titansPQ.poll();
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
		public void testPriorityQueueOrderinVolleySpreadTurnAttack() {
		
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

				Class volleySpreadCannonWeaponClass = Class.forName(volleySpreadCannonPath);
				Constructor<?> volleyWeaponConstructor = volleySpreadCannonWeaponClass.getConstructor(int.class, int.class,int.class);
				Object volleyWeapon = volleyWeaponConstructor.newInstance(5, 20, 50);
				
				Method turnAttack = volleySpreadCannonWeaponClass.getDeclaredMethod("turnAttack", PriorityQueue.class);
				turnAttack.invoke(volleyWeapon, titansPQ);

				int i =0;
				while(!titansPQ.isEmpty()) {
					Object object= titansPQ.poll();
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
			fail("Please check the console for the error, its an error from this catch statment."+e.getClass()+" occured");
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
