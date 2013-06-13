package com.funnyplayer.cache;

public class ImageInfo {
	
	//Type of image to grab
		//ablum, artist, playlist, or genre
	public String type;
	
	//Where the image is to be pulled from
		//lastfm - pull from the web
		//file - pull from the audio file
		//gallery - path to image will be passed in
		//first_avail - attempt to pull from 'file' but fallback to 'lastfm'
	public String source;
	
	//Size of the image being requested
		//thumb or normal
	public String size;
	
	//Extra data needed to perform image fetching
		//lastFM -    needs artist for artist image
		//            album & artist for album image
		//file 	-     needs album ID
		//gallery -   needs file path to image
		//first_available - needs both file & lastFM data		
	public String[] data;

	@Override
	public String toString() {
		return escapeForFileSystem(type + data[0]);
	}
	
    /**
     * Replace the characters not allowed in file names with underscore
     * @param name
     * @return
     */
    private static String escapeForFileSystem(String name) {
        return name.replaceAll("[\\\\/:*?\"<>|]+", "_");
    }

	
}
