package kr.or.dgit.jdbc_cafe_project_setting.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import kr.or.dgit.jdbc_cafe_project_setting.dao.DatabaseDao;

public class Trigger_InitService implements DbService {
	private static final Trigger_InitService instance=new Trigger_InitService();
	
	public static Trigger_InitService getInstance() {
		return instance;
	}

	private Trigger_InitService() {

	}
	
	@Override
	public void service() {
		File f = new File(System.getProperty("user.dir") + "\\resources\\create_trigger.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(f));) {
			StringBuilder statement = new StringBuilder();
			for (String line; (line = br.readLine()) != null;) {
				if (!line.isEmpty() && !line.startsWith("--")) {
					statement.append(line.trim() + " ");
				}
				if (line.endsWith("$$")) {
					statement.replace(statement.lastIndexOf("$$"), statement.length(), "");
					DatabaseDao.getInstance().executeUpdateSQL(statement.toString());
					statement.setLength(0);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
