package com.niit.prefs;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class SharedPrefsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener
{
	private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
	CheckBox cb;
	Context ctx;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		ctx = getApplicationContext();
		setContentView(R.layout.main);
		cb=(CheckBox)findViewById(R.id.check);
		cb.setOnCheckedChangeListener(this);
		Button btn=(Button)findViewById(R.id.close);
		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

			if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_CONTACTS)
					!= PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale(this,
						Manifest.permission.READ_CONTACTS)) {
					Toast.makeText(ctx,"I still need it!",Toast.LENGTH_LONG).show();
				} else {
					// No explanation needed, we can request the permission.
					ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
							MY_PERMISSIONS_REQUEST_READ_CONTACTS);
				}
			}
			else {
				if (isChecked) {
					cb.setText("This checkbox is: checked");
				}
				else {
					cb.setText("This checkbox is: unchecked");
				}
			}

	}

	public void onResume() {
		super.onResume();
		SharedPreferences settings=getPreferences(0);
		cb.setChecked(settings.getBoolean("cb_checked", false));
	}

	public void onPause() {
		super.onPause();
		SharedPreferences settings=getPreferences(0);
		SharedPreferences.Editor editor=settings.edit();
		editor.putBoolean("cb_checked", cb.isChecked());
		editor.commit();
	}
}