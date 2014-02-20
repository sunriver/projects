package com.multilanguage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class FileParser {
	private String mFileName;
	
	private BufferedReader mReader;
	private Map<String, String> mValueMap;
	
	public FileParser(final String fileName) {
		this.mFileName = fileName;
	}
	

	
	private void open() throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(new File(mFileName));
		mReader = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
	}
	
	private void close() throws IOException {
		if (mReader != null) {
			mReader.close();
		}
	}
	
	//Use channel to read file
	public Map<String, String> parseEx() throws Exception {
		try (
				FileChannel fcin = FileChannel.open(Paths.get(mFileName), StandardOpenOption.READ); //try里面的资源必须实现AutoCloseable,否者编译错误
		) {
			ByteBuffer buffer = ByteBuffer.allocate(512);
			HashMap<String, String> map = new HashMap<String, String>();
			Charset utf8 = Charset.forName("utf-8");
			while ((fcin.read(buffer)) != -1) {
				buffer.flip();
				CharBuffer charBuffer = utf8.decode(buffer);
			}
			return map;
		}
	}
	
	public Map<String, String> parse() throws Exception {
		open();
		mValueMap = new HashMap<String, String>();
		String nameLine = mReader.readLine();
		String valueLine = mReader.readLine();
		if (nameLine != null && valueLine != null) {
			String[] names = nameLine.split("\t");
			String[] values = valueLine.split("\t");
			if (names.length == values.length) {
				for (int i = 0, len = names.length; i < len; i++) {
					mValueMap.put(names[i], values[i]);
				}
			}
		}
		close();
		return mValueMap;
	}

	@Override
	protected void finalize() throws Throwable {
		if (mReader != null) {
			mReader.close();
		}
		super.finalize();
	}
	
	
	

}
