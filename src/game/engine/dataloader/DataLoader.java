package game.engine.dataloader;

import game.engine.exceptions.InvalidCSVFormat;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataLoader
{
	private static final String TITANS_FILE_NAME = "titans.csv";
	private static final String WEAPONS_FILE_NAME = "weapons.csv";

	public static HashMap<Integer, TitanRegistry> readTitanRegistry() throws IOException
	{
		HashMap<Integer, TitanRegistry> titanRegistryMap = new HashMap<>();

		BufferedReader br = new BufferedReader(new FileReader(TITANS_FILE_NAME));

		while (br.ready())
		{
			String nextLine = br.readLine();
			String[] data = nextLine.split(",");

			if (data.length != 7)
			{
				throw new InvalidCSVFormat(nextLine);
			}

			TitanRegistry reg = new TitanRegistry(Integer.parseInt(data[0]), Integer.parseInt(data[1]),
					Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]),
					Integer.parseInt(data[5]), Integer.parseInt(data[6]));

			titanRegistryMap.put(reg.getCode(), reg);
		}

		br.close();

		return titanRegistryMap;
	}

	public static HashMap<Integer, WeaponRegistry> readWeaponRegistry() throws IOException
	{
		HashMap<Integer, WeaponRegistry> weaponRegistryMap = new HashMap<>();

		BufferedReader br = new BufferedReader(new FileReader(WEAPONS_FILE_NAME));

		while (br.ready())
		{
			String nextLine = br.readLine();
			String[] data = nextLine.split(",");

			WeaponRegistry reg;

			if (data.length != 6 && data.length != 4)
			{
				throw new InvalidCSVFormat(nextLine);
			}

			if (data.length == 6)
			{
				reg = new WeaponRegistry(Integer.parseInt(data[0]), Integer.parseInt(data[1]),
						Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]), Integer.parseInt(data[5]));
			} else
			{
				reg = new WeaponRegistry(Integer.parseInt(data[0]), Integer.parseInt(data[1]),
						Integer.parseInt(data[2]), data[3]);
			}

			weaponRegistryMap.put(reg.getCode(), reg);
		}

		br.close();

		return weaponRegistryMap;
	}

}
