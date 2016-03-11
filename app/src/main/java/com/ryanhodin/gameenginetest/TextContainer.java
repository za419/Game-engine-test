package com.ryanhodin.gameenginetest;

public class TextContainer {
	protected String mText;
	protected Runnable mCallback;
	protected boolean mCallbackReady;

	public TextContainer() {
		mText="";
		mCallbackReady=true;
	}

	public TextContainer(String text) {
		mText=text;
		mCallbackReady=true;
	}

	public TextContainer(Runnable callback) {
		mCallback=callback;
		mCallbackReady=true;
	}

	public TextContainer(String text, Runnable callback) {
		mText=text;
		mCallback=callback;
		mCallbackReady=true;
	}

	public TextContainer(TextContainer copy) {
		mText=copy.mText;
		mCallbackReady=true;
	}

	public String text() {
		return mText;
	}

	public String text (String replacement) {
		String out=mText;
		mText=replacement;
		return out;
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
