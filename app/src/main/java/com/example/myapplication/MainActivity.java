package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    ImageView profileImage;
    ImageView smallProfileImage;

    Bundle extras ;
    private static final int PICK_PHOTO_FOR_AVATAR = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            Log.i("0","0");
            Uri u = data.getData();
            String filePath = getPath(u);
            Log.i("DATA",u.toString());
            Log.i("DATA2",filePath);
            profileImage = findViewById(R.id.profileImageSource);
            profileImage.setImageURI(data.getData());
            File file = new File(filePath);
            Log.i("file name",file.getName());
            Log.i("1","1");
            Log.i("3","3");
//            SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
//            String token = shP.getString("token", "");
//            String firstName = shP.getString("firstname","");
//            String lastName = shP.getString("lastname","");
//            String email = shP.getString("email","");
//            String phoneNumber = shP.getString("phonenumber","");
//            Log.i("4","4");

//            RequestBody requestFile =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//// MultipartBody.Part is used to send also the actual file name
//            MultipartBody.Part body =
//                    MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
//            RequestBody emailR =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), email);
//            RequestBody firstNameR =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), firstName);
//            RequestBody lastNameR =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), lastName);
//            RequestBody phoneNumberR =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), phoneNumber);
//            Call<User> userSessionCall = userAPI.editProfile("token "+ token,emailR,firstNameR,lastNameR,phoneNumberR,body);
//            Log.i("5","5");
//            userSessionCall.enqueue(new Callback<User>() {
//                @Override
//                public void onResponse(Call<User> call, Response<User> response) {
//                    if(!response.isSuccessful())
//                    {
//                        Toast.makeText(MainActivity.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
//                        Log.i("MOSHKEL",response.message());
//                    }
//                    else{
//                        String code = Integer.toString(response.code());
//                        Toast.makeText(MainActivity.this, "Photo Edited!", Toast.LENGTH_SHORT).show();
//                        final String[] userAvatar = new String[1];
//                        Call<User> userSessionCall1 = userAPI.showProfile("token "+ token);
//                        userSessionCall1.enqueue(new Callback<User>() {
//                            @Override
//                            public void onResponse(Call<User> call, Response<User> response) {
//                                if(!response.isSuccessful())
//                                {
//                                    Toast.makeText(MainActivity.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    String code = Integer.toString(response.code());
//                                    User user = response.body();
//                                    if(!(user.getAvatar() == null)){
//                                        userAvatar[0] = user.getAvatar();
//                                    }
//                                    else{
//                                        userAvatar[0] = "";
//                                    }
//                                    SharedPreferences UI = getSharedPreferences("userInformation",MODE_PRIVATE);
//                                    SharedPreferences.Editor myEdit = UI.edit();
//                                    myEdit.putString("avatar",userAvatar[0]);
//                                    myEdit.apply();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<User> call, Throwable t) {
//                                //Toast.makeText(MainActivity.this, "error is :"+t.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<User> call, Throwable t) {
//                    //Toast.makeText(MainActivity.this, "error is :"+t.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
//
//            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
       }
        if (requestCode == 1) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
        }
    }
    public  void GoToEditProfile(View view){
        Intent editProfile = new Intent(MainActivity.this, EditProfile.class);
        startActivity(editProfile);
    }
}