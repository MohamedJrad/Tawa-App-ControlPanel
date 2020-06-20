package com.tawa.tawa_app_controlpanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.tawa.tawa_app_controlpanel.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    AppBarConfiguration appBarConfiguration;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        // Set up Action Bar
        NavController navController = host.getNavController();

        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();

        setupActionBar(navController, appBarConfiguration);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }



    private void setupActionBar(NavController navController,
                                AppBarConfiguration appBarConfig) {
        // TODO STEP 9.6 - Have NavigationUI handle what your ActionBar displays
//        // This allows NavigationUI to decide what label to show in the action bar
//        // By using appBarConfig, it will also determine whether to
//        // show the up arrow or drawer menu icon
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
        // TODO END STEP 9.6
    }



    // TODO STEP 9.7 - Have NavigationUI handle up behavior in the ActionBar
    @Override
    public boolean onSupportNavigateUp() {
//        // Allows NavigationUI to support proper up navigation or the drawer layout
//        // drawer menu, depending on the situation
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), appBarConfiguration);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case  R.id.action_logout:
//                firebaseAuth.signOut();
//                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            case R.id.action_settings:
//                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_regionFragment_to_aboutUsFragment);
//
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}