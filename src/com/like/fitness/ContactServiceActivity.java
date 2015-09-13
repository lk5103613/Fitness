package com.like.fitness;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ContactServiceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_service);
	}
	
	public void back(View v) {
		this.finish();
	}
	
}
