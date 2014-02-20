package com.multilanguage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.multilanguage.util.FileParser;
import com.multilanguage.util.LanguageFolder;

public class ConvertLanguage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		convert();
	}
	
	private static void convert() {
		String targetDir = "C:\\Users\\alu\\Desktop\\language\\target\\";
		String sourceFile = "C:\\Users\\alu\\Desktop\\language\\language.txt";
		try {
			FileParser parser = new FileParser(sourceFile);
			Map<String, String> namesMap = parser.parse();
			Map<String, String> folderMap = LanguageFolder.getMap();
			Set<Entry<String, String>> namesSet =  namesMap.entrySet();
			int count = 0;
			for (Entry<String, String> entry : namesSet) {
				String folder = folderMap.get(entry.getKey());
				count++;
				System.out.println("count=" + count + "  lan=" + entry.getKey() + "  folder=" + folder + "  [" + entry.getValue() + "]");
				File valueDir = new File(targetDir + folder + "");
				if (!valueDir.exists()) {
					valueDir.mkdir();
				}
				File targetFile = new File(valueDir, "strings.xml");
				if (targetFile.exists()) {
					targetFile.delete();
				}
				writeFile(targetFile, entry.getValue());
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void writeFile(File f, String content) throws UnsupportedEncodingException {
		try {
			PrintWriter writer = new PrintWriter(f, "UTF-8");
			writer.append(content);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
