package com.funnyplayer.util;

public class Consts {
    //Image Loading Constants
	public enum TYPE {
		ALBUM(0), ARTIST(1), PLAYLIST(2);
		
		private int mIndex;
		
		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
		
		private TYPE(int index) {
			this.mIndex = index;
		}
		
		public int getIndex() {
			return mIndex;
		}
	}
	
	public static class SdkVersion {
		//3.0
		public static int API_11 = 11;
	}
	
	public final static String PLAY_GRID_INDEX = "play_grid_index";
	public final static String PLAY_ITEM_INDEX = "play_item_index";
	
	
    public final static String SRC_FIRST_AVAILABLE = "first_avail";
    public final static String SIZE_THUMB = "thumb";
    public final static String SIZE_NORMAL = "normal";
    
    // Bundle & Intent type
    public final static String MIME_TYPE = "mimetype";
    public final static String INTENT_ACTION = "action";
    public final static String DATA_SCHEME = "file";
    
    public final static String ARTIST_KEY = "artist";
    public final static String ALBUM_KEY = "album";
    public final static String ALBUM_ID_KEY = "albumid";
    
    
}
