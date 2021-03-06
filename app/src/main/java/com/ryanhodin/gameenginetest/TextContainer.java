package com.ryanhodin.gameenginetest;

public class TextContainer {
	protected String mText; // The contained text
	protected Callable mCallback=null; // The callback for when the text is changed
	protected boolean mCallbackReady; // False if a callback is in progress
	protected boolean mCallbackWaiting; // True if a callback is waiting to run

	public TextContainer() {
		mText="";
		mCallbackReady=true;
		mCallbackWaiting=false;
	}

	public TextContainer(String text) {
		mText=text;
		mCallbackReady=true;
		mCallbackWaiting=false;
	}

	public TextContainer(Callable callback) {
		mCallback=callback;
		mCallbackReady=true;
		mCallbackWaiting=false;
	}

	public TextContainer(String text, Callable callback) {
		mText=text;
		mCallback=callback;
		mCallbackReady=true;
		mCallbackWaiting=false;
	}

	public TextContainer(TextContainer copy) {
		mText=copy.mText;
		mCallback=copy.mCallback;
		mCallbackReady=true; // This allows two calls in parallel
		mCallbackWaiting=false;
	}

	public String text() {
		return mText;
	}

	public String text (String replacement) {
		final String out=mText;
		mText=replacement;
		Thread th=new Thread(new Runnable() {
			@Override
			public void run() {
				if (mCallback==null || mCallbackWaiting)
					return;
				try {
					if (!mCallbackReady)
						mCallbackWaiting=true;
					while (!mCallbackReady)
						Thread.sleep(16);
					mCallbackReady=false;
					mCallback.call(out, mText);
					mCallbackReady=true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mCallbackWaiting=false;
			}
		});
		th.start();
		return out;
	}

	public String append(String addition) {
		return text(text()+addition);
	}

	public String prepend(String addition) {
		return text(addition+text());
	}
	// Suspends all callbacks in the near future
	public void forceCallbackSuspend() {
		mCallbackReady=false;
	}

	// Continues all waiting callbacks, even if others may be running. This is needed to undo the
	//  forced suspension of calling the above function.
	public void forceCallbackContinue() {
		mCallbackReady=true;
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

	public interface Callable { // Used as a callback for when text changes
		// Called with the text pre- and post- the change for which this is called
		void call(String old, String replacement);
	}
}
