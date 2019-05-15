package com.cs.nks.easycouriers.activity;

import android.speech.tts.UtteranceProgressListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.fragment.Center_List_Fragment;
import com.cs.nks.easycouriers.fragment.Center_search_Fragment;
import com.cs.nks.easycouriers.fragment.CourseMaterial_List_Fragment;
import com.cs.nks.easycouriers.fragment.Course_List_Fragment;
import com.cs.nks.easycouriers.fragment.HomeFragment;
import com.cs.nks.easycouriers.fragment.LoginFragment;
import com.cs.nks.easycouriers.fragment.ProfileFragment;
import com.cs.nks.easycouriers.fragment.RegisterFragment;
import com.cs.nks.easycouriers.fragment.Subject_List_Fragment;
import com.cs.nks.easycouriers.util.UTIL;
import com.squareup.picasso.Picasso;


public class ActivityWithNavigationMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context myContext;
    // UTIL utill;
    AlertDialog alertDialog;
    NavigationView navigationView;
    // ArrayList<Item> Item_list = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_menu);
        myContext = ActivityWithNavigationMenu.this;
        // utill = new UTIL(myContext);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        manupulateDrawerItems();
        addDeviceListFragment();

    }


    public void addDeviceListFragment() {
        HomeFragment fragment = new HomeFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frgmnt_placehodler, fragment);
        fragmentTransaction.commit();

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        /*
        Menu menu = navigationView.getMenu();
        menu.add(R.id.group_1, 123, Menu.NONE, "Title1");
        menu.add(R.id.group_1, 124, Menu.NONE, "Title2");
       */

        closeDrawer();
        int id = item.getItemId();
        // to not highlight when selected
        //  navigationView.getMenu().getItem(item).setChecked(false);


        if (id == R.id.nav_Home) {
            replaceFragmnt(new HomeFragment());
        } else if (id == R.id.nav_Regsiteration) {
            // replaceFragmnt(new RegisterFragment());
            startActivity(new Intent(ActivityWithNavigationMenu.this, Tab_Login_Register_Activity.class));
            finish();
       /* } else if (id == R.id.nav_login) {
            replaceFragmnt(new LoginFragment());
     */
        } else if (id == R.id.nav_Course_List) {
            replaceFragmnt(new Course_List_Fragment());
        } else if (id == R.id.nav_Center_List) {
            replaceFragmnt(new Center_List_Fragment());
        } else if (id == R.id.nav_Center_Search) {
            replaceFragmnt(new Center_search_Fragment());
        } else if (id == R.id.nav_subject_lst) {
           // replaceFragmnt(new Subject_List_Fragment());
            replaceFragmnt(new CourseMaterial_List_Fragment());

        } else if (id == R.id.nav_logout) {
            dialog_LOGOUT();
        }

        return false;
    }

    public void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void replaceFragmnt(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // fragmentTransaction.setCustomAnimations( R.anim.left_in, R.anim.left_out);
        fragmentTransaction.replace(R.id.frgmnt_placehodler, fragment);
        //fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
        //
        ActivityWithNavigationMenu.this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        //HomeActivity.this.overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

    }


    public void manupulateDrawerItems() {

        /*set drawer menu programmatically*/

       ImageView imageView;
        TextView textviewUsr, textCourse;
        String Uid = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_UserId);
        boolean isLogin = true;

        if (UTIL.isLogin(ActivityWithNavigationMenu.this)) {
            navigationView.inflateMenu(R.menu.menu_drawer_with_login);

            View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main2, null);
            navigationView.addHeaderView(nav_header);

            View headerLayout = navigationView.getHeaderView(0);
           imageView =  headerLayout.findViewById(R.id.img_usr);
            textviewUsr =  headerLayout.findViewById(R.id.textUserName);
            textCourse =  headerLayout.findViewById(R.id.textCourse);

//            imageView.setVisibility(View.GONE);
            String usr = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_USERNAME);
            String COURSE = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_COURSE);
            String IMG_URL = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_IMG_URL);

            // String user = "Guest";

            textviewUsr.setText(usr);
            textCourse.setText(COURSE);
          //  imageView.setBackgroundResource(R.drawable.mannnnn);

            try {
                Picasso.get()
                        .load(IMG_URL)
                        .placeholder(R.drawable.mannnnn)
                        .error(R.drawable.mannnnn)
                        .into(imageView);

            }catch (Exception e){
                e.getCause();
            }

            headerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeDrawer();
                    replaceFragmnt(new ProfileFragment());
                }
            });


        } else {
            navigationView.inflateMenu(R.menu.menu_drawer_without_login);
            View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main1, null);
            navigationView.addHeaderView(nav_header);

        }


    }


    private void dialog_LOGOUT() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fancyalertdialog, null);
        dialogBuilder.setView(dialogView);


        final TextView title = dialogView.findViewById(R.id.title);
        final TextView message = dialogView.findViewById(R.id.message);
        final Button positiveBtn = dialogView.findViewById(R.id.positiveBtn);
        final Button negativeBtn = dialogView.findViewById(R.id.negativeBtn);


        // dialogBuilder.setTitle("Device Details");
        title.setText("Do you want to logout?");
        message.setText("Are you sure?");
        positiveBtn.setText("Yes");
        negativeBtn.setText("No");
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                UTIL.clearPref(ActivityWithNavigationMenu.this);
                Intent abc = new Intent(ActivityWithNavigationMenu.this, ActivityWithNavigationMenu.class);
                startActivity(abc);
                Toast.makeText(ActivityWithNavigationMenu.this, "Logout successfully!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                dialog_Exit();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
           /* Toast.makeText(this, "Please click BACK again to exit!",
                    Toast.LENGTH_SHORT).show();*/
            addDeviceListFragment();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);


        }
    }

    private void dialog_Exit() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fancyalertdialog, null);
        dialogBuilder.setView(dialogView);


        final TextView title = dialogView.findViewById(R.id.title);
        final TextView message = dialogView.findViewById(R.id.message);
        final Button positiveBtn = dialogView.findViewById(R.id.positiveBtn);
        final Button negativeBtn = dialogView.findViewById(R.id.negativeBtn);


        // dialogBuilder.setTitle("Device Details");
        title.setText("Do you want to exit?");
        message.setText("Are you sure?");
        positiveBtn.setText("Yes");
        negativeBtn.setText("No");
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                finish();


            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }
}
