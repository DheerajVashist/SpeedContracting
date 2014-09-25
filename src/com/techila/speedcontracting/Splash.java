package com.techila.speedcontracting;



import com.techila.speedcontracting.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;




public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Intent intent=new Intent(Splash.this,CreateContractActivity.class);
				    startActivity(intent);
					overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
				    Splash.this.finish();
			}
		}, 1500);
	}
}
