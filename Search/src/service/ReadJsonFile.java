package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class ReadJsonFile {
	public Map<String,String> map = new HashMap<String, String>();
	
	public BufferedReader reader = null;
	
	public ReadJsonFile(String filename) {
		File file = new File(filename);
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void stringToJson(String value) {
		map.clear();
		map = JSON.parseObject(value, Map.class);
		return;
	}
	
	/*public static void main(String[] args) throws IOException {
		ReadJsonFile phase = new ReadJsonFile("D:\\eclipseworkspace\\Search\\src\\service\\ntcir14_test_doc.json");
		String value = null;
		String[] temp_title = null;
		String[] temp_content = null;
		while(true) {
			value = phase.reader.readLine();
			if(value == null) {
				break;
			}
			phase.stringToJson(value);
			temp_title = phase.map.get("title_seg").split(" ");
			temp_content = phase.map.get("content_seg").split(" ");
			System.out.println(temp_title.length);
			System.out.println(temp_content.length);
			break;
		}
		phase.reader.close();
	}*/
}
