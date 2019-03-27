package com.config;

import org.json.JSONObject;

import com.FILE.FILE_IO;

/**
 * @author Haojun E-mail: lovingcloud77@gmail.com
 * @version ����ʱ�䣺2018��6��7�� ����7:44:18 ��˵��
 */
public class AppConfig {
	// application version
	private String version = "1.7.4";

	private FILE_IO file_io;

	public AppConfig() {
		file_io = new FILE_IO(this);
		JSONObject jsonObject = file_io.getJsonObject();
		readJSONObject(jsonObject);
	}

	private void readJSONObject(JSONObject jsonObject) {
		setVersion(jsonObject.getString("version"));
	}

	public void reWriteFile() {
		file_io.file_rewirte(this);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
