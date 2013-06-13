package com.funnyplayer.net.api;

public interface APIMethod {

	public enum Album implements APIMethod {
		GetInfo("album.getInfo");

		private String name;

		private Album(final String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	public enum Artist implements APIMethod {
		GetInfo("artist.getInfo");

		private String name;

		private Artist(final String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return  name;
		}

	}

}
