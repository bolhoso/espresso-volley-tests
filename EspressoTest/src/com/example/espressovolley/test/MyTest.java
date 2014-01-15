package com.example.espressovolley.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.registerIdlingResources;
import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Field;
import java.util.Set;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.example.espressovolley.MainActivity;
import com.example.espressovolley.MyApplication;
import com.example.espressovolley.R;
import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.IdlingResource;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

public class MyTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Activity mCurrActivity;

	public MyTest() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		mCurrActivity = getActivity();


		super.setUp();
	}

	public void testClickOnReviews() throws InterruptedException{
		VolleyIdlingResource volleyResources;
		try {
			volleyResources = new VolleyIdlingResource ("VolleyCalls");
			registerIdlingResources(volleyResources);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// should wait for espresso to be idle...
		Espresso
			.onView(ViewMatchers.withId(R.id.label))
			.check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
	}


	public final class VolleyIdlingResource implements IdlingResource {
		private static final String TAG = "VolleyIdlingResource";
		private final String resourceName;

		// written from main thread, read from any thread.
		private volatile ResourceCallback resourceCallback;
		
		private Field mCurrentRequests;
		private RequestQueue mVolleyRequestQueue; 

		public VolleyIdlingResource(String resourceName) throws SecurityException, NoSuchFieldException {
			this.resourceName = checkNotNull(resourceName);
			
			mVolleyRequestQueue = MyApplication.getRequestQueue();
			
			mCurrentRequests = RequestQueue.class.getDeclaredField("mCurrentRequests");
			mCurrentRequests.setAccessible(true);
		}

		@Override
		public String getName() {
			return resourceName;
		}

		@Override
		public boolean isIdleNow() {
			try {
				Set<Request> set = (Set<Request>) mCurrentRequests.get(mVolleyRequestQueue);
				int count = set.size();
				if (set != null) {
					
					if (count == 0) {
						Log.d(TAG, "Volley is idle now! with: " + count);
						resourceCallback.onTransitionToIdle();
					} else {
						Log.d(TAG, "Not idle... " +count);
					}
					return count == 0;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d(TAG, "Eita porra.. ");
			return true;
		}

		@Override
		public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
			this.resourceCallback = resourceCallback;
		}

	}




}
