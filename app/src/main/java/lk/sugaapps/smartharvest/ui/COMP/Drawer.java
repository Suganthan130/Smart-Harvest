package lk.sugaapps.smartharvest.ui.COMP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import lk.sugaapps.smartharvest.BuildConfig;
import lk.sugaapps.smartharvest.R;
import lk.sugaapps.smartharvest.databinding.ActivityMainDrawerBinding;
import lk.sugaapps.smartharvest.ui.activities.AccountActivity;
import lk.sugaapps.smartharvest.viewmodel.UserViewModel;

public class Drawer  {
	private static final String TAG = Drawer.class.getSimpleName();
	private Activity mActivity;
	private DrawerLayout drawerLayout;
	FragmentManager fragmentManager;
	ActivityMainDrawerBinding binding; // Note: This field is declared but not initialized in the provided code
	UserViewModel viewModel;
	private String userName;
	public Drawer(Activity activity, FragmentManager supportFragmentManager,String userName) {
		mActivity = activity;
		fragmentManager = supportFragmentManager;
		this.userName = userName;
	}
	@SuppressLint("SetTextI18n")
	public void setDrawer(DrawerLayout.DrawerListener listener) {
		drawerLayout = mActivity.findViewById(R.id.drawerLayout);

		TextView buildVersionText = mActivity.findViewById(R.id.buildVersionText);
		buildVersionText.setText(BuildConfig.VERSION_NAME);

		TextView NameText = mActivity.findViewById(R.id.user_name_txt);
		// CRITICAL: firebaseAuth will be null here if Drawer is instantiated with "new"
		// and not managed by Hilt for injection.
		NameText.setText("Hi "+userName);


		LinearLayout layout_logout = mActivity.findViewById(R.id.layout_logout);
		layout_logout.setOnClickListener(v -> {
			// TODO: Implement logout
		});

		LinearLayout layout_account = mActivity.findViewById(R.id.layout_account);
		layout_account.setOnClickListener(v -> {
			lunchActivity(mActivity, AccountActivity.class);
		});

		LinearLayout layout_setting = mActivity.findViewById(R.id.layout_setting); // Assuming this ID exists
		layout_setting.setOnClickListener(v -> {
			// TODO: Implement settings action
		});

		drawerLayout.addDrawerListener(listener);
	}

	private void lunchActivity(Activity mActivity, Class openClass) { // 'lunchActivity' likely a typo for 'launchActivity'
		mActivity.startActivity(new Intent(mActivity,openClass));
	}

	public DrawerLayout getDrawerLayout() {
		return drawerLayout;
	}
}
