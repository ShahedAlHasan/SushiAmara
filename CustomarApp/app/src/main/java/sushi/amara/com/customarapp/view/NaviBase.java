package sushi.amara.com.customarapp.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import sushi.amara.com.customarapp.Common.Common;
import sushi.amara.com.customarapp.R;

public class NaviBase extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {


    private NavigationView navigationView;
    private DrawerLayout fullLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedNavItemId;
    TextView mtollbarText;
    ImageView openDrawer;

    private static final int REQUEST_CALL = 1;

    int i = 0,j=0;


    @SuppressLint("InflateParams")
    @Override
    public void setContentView(@LayoutRes int layoutResID) {





        /**
         * This is going to be our actual root layout.
         */



        fullLayout = (DrawerLayout) findViewById(R.id.naviBuisnessDraerLayout);
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navi_base, null);
        /**
         * {@link FrameLayout} to inflate the child's view. We could also use a {@link ViewStub}
         */
        FrameLayout activityContainer = (FrameLayout) fullLayout.findViewById(R.id.Activity_Contant);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        /**
         * Note that we don't pass the child's layoutId to the parent,
         * instead we pass it our inflated layout.
         */
        super.setContentView(fullLayout);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            toolbar = (Toolbar) findViewById(R.id.toolbar_base);
//        }
////        mtollbarText = (TextView) findViewById(R.id.toolbar_text_base);
//        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        View headerView = navigationView.getHeaderView(0);



        setUpNavView();

        openDrawer = findViewById(R.id.open_drawer_image);

        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullLayout.openDrawer(Gravity.LEFT);
            }
        });

    }

    /**
     * Helper method that can be used by child classes to
     * specify that they don't want a {@link Toolbar}
     *
     * @return true
     */
    protected boolean useToolbar() {
        return true;
    }

    protected void setUpNavView() {
        navigationView.setNavigationItemSelectedListener(this);

        if (useDrawerToggle()) { // use the hamburger menu

            drawerToggle = new ActionBarDrawerToggle(this, fullLayout, toolbar,
                    R.string.nav_drawer_opened,
                    R.string.nav_drawer_closed);

            fullLayout.setDrawerListener(drawerToggle);
            drawerToggle.syncState();
        } else if (useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }


    /**
     * Helper method to allow child classes to opt-out of having the
     * hamburger menu.
     *
     * @return
     */
    protected boolean useDrawerToggle() {
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        fullLayout.closeDrawer(GravityCompat.START);
        selectedNavItemId = menuItem.getItemId();
        return onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*if (id == R.id.nav_menu) {
            // Handle the camera action
            Intent intent = new Intent(this, FoodActivity.class);
            intent.putExtra("store", Common.store);
            intent.putExtra("get", Common.system);
            intent.putExtra("address", Common.location);
            startActivity(intent);

        } else*/ if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_search) {
            String deviceId = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Intent intent = new Intent(this, AllFoodSearch.class);
            intent.putExtra("store", Common.store);
            intent.putExtra("get", Common.system);
            intent.putExtra("address", Common.location);
            startActivity(intent);

        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this, ContactUs.class);
            startActivity(intent);

        }else if (id == R.id.nav_Impressum) {
            Intent intent = new Intent(this, Impressum.class);
            startActivity(intent);

        }else if (id == R.id.nav_Datenschutzerklarung) {
            Intent intent = new Intent(this, Datenschutzerklarung.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_onlineA) {

            Intent intent = new Intent(this, LocationActivity.class);
            intent.putExtra("store","Langenfeld" );
            startActivity(intent);

        }
        else if (id == R.id.nav_onlineB) {

            Intent intent = new Intent(this, LocationActivity.class);
            intent.putExtra("store", "Leichlingen");
            startActivity(intent);


        }
        else if (id == R.id.nav_space_booking) {

            Intent intent = new Intent(this, SpaceBooking.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_call1) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + "+49021751869932";
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }
        else if (id == R.id.nav_call2) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + "+49021731625015";
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}