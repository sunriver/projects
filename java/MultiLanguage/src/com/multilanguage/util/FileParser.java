package com.multilanguage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class FileParser {
	private String mFileName;

	public FileParser(final String fileName) {
		this.mFileName = fileName;
	}

	// Use channel to read file with utility class Channels
	public Map<String, String> parseEx() throws Exception {
		try (FileChannel fcin = FileChannel.open(Paths.get(mFileName),
				StandardOpenOption.READ); // try里面的资源必须实现AutoCloseable,否者编译错误
		) {
			BufferedReader reader = new BufferedReader(Channels.newReader(fcin,
					"UTF-8"));
			Map<String, String> map = parse(reader);
			return map;
		}
	}

	// Use channel to read file with utility class "Files"
	public Map<String, String> parseEx2() throws Exception {
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("path"),
				Charset.forName("utf-8"))) {
			Map<String, String> map = parse(reader);
			return map;
		}
	}

	Map<String, String> parse(BufferedReader reader) throws Exception {
		String nameLine = reader.readLine();
		String valueLine = reader.readLine();
		HashMap<String, String> map = new HashMap<String, String>();
		if (nameLine != null && valueLine != null) {
			String[] names = nameLine.split("\t");
			String[] values = valueLine.split("\t");
			if (names.length == values.length) {
				for (int i = 0, len = names.length; i < len; i++) {
					map.put(names[i], values[i]);
				}
			}
		}
		return map;
	}

	public Map<String, String> parse() throws Exception {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(mFileName));
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis, Charset.forName("UTF-8")));
			Map<String, String> map = parse(reader);
			return map;
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

}
