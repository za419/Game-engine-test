package com.ryanhodin.gameenginetest;

public class TextContainer {
	protected String mText;

	public TextContainer() {
		mText="";
	}

	public TextContainer(final String text) {
		mText=text;
	}

	public TextContainer(TextContainer copy) {
		mText=copy.mText;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof TextContainer && ((TextContainer) obj).mText.equals(mText);
	}

	@Override
	public String toString() {
		return mText;
	}

	public static String toString(TextContainer tc) {
		return tc.toString();
	}
}
