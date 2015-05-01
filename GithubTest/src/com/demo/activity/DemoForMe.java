package com.demo.activity;
import com.demo.view.MyView;

import android.app.Activity;
import android.os.Bundle;


public class DemoForMe extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

}
