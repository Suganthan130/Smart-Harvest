package lk.sugaapps.smartharvest.ui.view.drawer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;

import lk.sugaapps.smartharvest.BuildConfig;
import lk.sugaapps.smartharvest.R;
import lk.sugaapps.smartharvest.ui.activities.LoginActivity;

public class Drawer  {
	private Activity mActivity;
	private DrawerLayout drawerLayout;
	FragmentManager fragmentManager;
	FirebaseAuth firebaseAuth;
	public Drawer(Activity activity, FragmentManager supportFragmentManager, FirebaseAuth firebaseAuth) {
		mActivity = activity;
		fragmentManager = supportFragmentManager;
	}
	@SuppressLint("SetTextI18n")
	public void setDrawer(DrawerLayout.DrawerListener listener) {
		drawerLayout = mActivity.findViewById(R.id.drawerLayout);

		TextView buildVersionText = mActivity.findViewById(R.id.buildVersionText);
		buildVersionText.setText(BuildConfig.VERSION_NAME);


		LinearLayout layout_logout = mActivity.findViewById(R.id.layout_logout);
		layout_logout.setOnClickListener(v -> {
			firebaseAuth.getInstance().signOut();
			mActivity.finish();
			mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
		});


		drawerLayout.addDrawerListener(listener);
	}

	public DrawerLayout getDrawerLayout() {
		return drawerLayout;
	}
}
