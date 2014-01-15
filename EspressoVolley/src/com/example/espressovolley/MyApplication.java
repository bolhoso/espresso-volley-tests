package com.example.espressovolley;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {
	private static RequestQueue requestQueue;
	private static Application appInstance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		appInstance = this;
		requestQueue = Volley.newRequestQueue(this);
		Thread.currentThread().dumpStack();
		Log.d("TEST", this.toString());
	}
	
	public synchronized static RequestQueue getRequestQueue () {
		if (requestQueue == null) {
			requestQueue = Volley.newRequestQueue(appInstance);
		}
		Log.d("TEST", "entering getRequestQueue");
		Log.d("TEST", "Application instance: " + appInstance);
		Log.d("TEST", "requestQueue instance: " + requestQueue);
		
		Thread.currentThread().dumpStack();
		
		return requestQueue;
	}
}
