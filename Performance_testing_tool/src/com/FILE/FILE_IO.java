package com.FILE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.json.JSONObject;
import org.json.JSONTokener;

public class FILE_IO {
	private JSONObject readJsonObject;

	private String address;

	private BufferedWriter bw;
	private OutputStream os;
	private File file;

	public FILE_IO(Object bean) {
		// creat json files in path /usr/home/MIRLAB-Agent-Config if files do
		// not exist
		address = System.getProperty("user.home");

		address = address + "/MIRLAB-Agent-Config/" + bean.getClass().getSimpleName() + ".json";

		file = new File(address);

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (file.exists()) {
			file_I();
		} else {
			file_O(new JSONObject(bean));
		}
	}

	public JSONObject getJsonObject() {
		return readJsonObject;
	}

	private void file_I() {
		try {
			JSONTokener jsonTokener = new JSONTokener(new FileReader(file));

			readJsonObject = new JSONObject(jsonTokener);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void file_O(JSONObject writeJsonObject) {

		try {
			os = new FileOutputStream(address, true);
			bw = new BufferedWriter(new OutputStreamWriter(os));

			bw.write(writeJsonObject.toString());

			bw.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void file_rewirte(Object bean) {
		file.delete();
		file_O(new JSONObject(bean));
	}
}
