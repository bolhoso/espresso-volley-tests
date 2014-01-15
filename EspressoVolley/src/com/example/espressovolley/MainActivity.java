package com.example.espressovolley;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.VolleyError;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		callWebservice();
	}
	
	private void callWebservice () {
		RequestQueue queue = MyApplication.getRequestQueue();
		queue.add(new MyRequest ("http://192.168.1.149/", errorListener));
	}
	
	// Show a label on screen
	private void showLabel () {
		findViewById(R.id.label).setVisibility (View.VISIBLE);
	}
	
	ErrorListener errorListener = new ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError arg0) {
			Toast.makeText(MainActivity.this, "ops...", Toast.LENGTH_LONG).show();
		}
	};
	
	class MyRequest extends Request<Object> {
	    public MyRequest(String url, ErrorListener e) {
	        super(Method.GET, url, e);
	    }
	 
		@Override
		protected Response<Object> parseNetworkResponse(NetworkResponse arg0) {
			return Response.success(new Object(), HttpHeaderParser.parseCacheHeaders(arg0));
		}

		@Override
		protected void deliverResponse(Object arg0) {
			showLabel();
		}
	}

}
