package com.example.tolearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tolearn.AlertDialogs.CustomeConfirmAlertDialog;
import com.example.tolearn.Entity.User;
import com.example.tolearn.webService.UserAPI;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    UserAPI userAPI;
    TextView userNameNavigationHeader;
    private static final int PICK_PHOTO_FOR_AVATAR = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MessageFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_message);
        }

        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        String userName = shP.getString("username", "");
        String avatarUrl = shP.getString("avatar","");
        if(!avatarUrl.equals("")){
            ImageView profileNav = ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageProfileSmall));
            Picasso.get().load(avatarUrl).placeholder(R.drawable.acount_circle).error(R.drawable.acount_circle).into(profileNav);
        }
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.NavHeaderUserNameTextView)).setText(userName);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit LoginRetrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI =LoginRetrofit.create(UserAPI.class);
        initRetro();
        verifyStoragePermissions(this);

    }

    private void initRetro() {
        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        String token = shP.getString("token", "");
        final String[] userEmail = new String[1];
        final String[] userFirstName = new String[1];
        final String[] userLastName = new String[1];
        final String[] userPhoneNumber = new String[1];
        final String[] userAvatar = new String[1];
        Call<User> userSessionCall = userAPI.showProfile("token "+ token);
        userSessionCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                }
                else{
                    String code = Integer.toString(response.code());
                    User user = response.body();
                    if(!(user.getEmail()==null)){
                        userEmail[0] = user.getEmail();
                    }
                    else{
                        userEmail[0] = "";
                    }
                    if(!(user.getFirst_name()==null)){
                        userFirstName[0] = user.getFirst_name();                                            }
                    else{
                        userFirstName[0] = "";
                    }
                    if(!(user.getLast_name()==null)){
                        userLastName[0] = user.getLast_name();
                    }
                    else{
                        userLastName[0] = "";
                    }
                    if(!(user.getPhone_number() == null)){
                        userPhoneNumber[0] = user.getPhone_number();
                    }
                    else{
                        userPhoneNumber[0] = "";
                    }
                    if(!(user.getAvatar() == null)){
                        userAvatar[0] = user.getAvatar();
                    }
                    else{
                        userAvatar[0] = "";
                    }
                    SharedPreferences UI = getSharedPreferences("userInformation",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = UI.edit();
                    myEdit.putString("token", token);
                    myEdit.putString("firstname",userFirstName[0]);
                    myEdit.putString("lastname",userLastName[0]);
                    myEdit.putString("email",userEmail[0]);
                    myEdit.putString("avatar",userAvatar[0]);
                    myEdit.putString("phonenumber",userPhoneNumber[0]);
                    myEdit.apply();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error is :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MessageFragment()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.classCreation:
                Intent goToClassCreation = new Intent(this , Class_creation.class);
                startActivity(goToClassCreation);
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logoutBtn:
                CustomeConfirmAlertDialog exit = new CustomeConfirmAlertDialog(this,"Confirmation","Do you want to logout ?");
                exit.No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        exit.alertDialog.dismiss();
                    }
                });
                exit.Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences shp = getSharedPreferences("userInformation",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = shp.edit();
                        myEdit.putString("token","");
                        myEdit.apply();
                        exit.alertDialog.dismiss();
                        Intent goToLogin = new Intent(MainActivity.this,Login.class);
                        startActivity(goToLogin);
                        finish();
                    }
                });
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onDrawerOpened (View drawerView){

    }
    public  void GoToEditProfile(View view){
        Intent editProfile = new Intent(MainActivity.this, EditProfile.class);
        startActivity(editProfile);
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
            try {
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
                SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
                String token = shP.getString("token", "");
                String firstName = shP.getString("firstname","");
                String lastName = shP.getString("lastname","");
                String email = shP.getString("email","");
                String phoneNumber = shP.getString("phonenumber","");
                Log.i("4","4");

                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
                RequestBody emailR =
                        RequestBody.create(MediaType.parse("multipart/form-data"), email);
                RequestBody firstNameR =
                        RequestBody.create(MediaType.parse("multipart/form-data"), firstName);
                RequestBody lastNameR =
                        RequestBody.create(MediaType.parse("multipart/form-data"), lastName);
                RequestBody phoneNumberR =
                        RequestBody.create(MediaType.parse("multipart/form-data"), phoneNumber);
                Call<User> userSessionCall = userAPI.editProfile("token "+ token,emailR,firstNameR,lastNameR,phoneNumberR,body);
                Log.i("5","5");
                userSessionCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(!response.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                            Log.i("MOSHKEL",response.message());
                        }
                        else{
                            String code = Integer.toString(response.code());
                            User user = response.body();
                            Log.i("PHOTO","SUCCED");
 //                           Log.i("IMAGE URL",user.getAvatar().toString());
                            Toast.makeText(MainActivity.this, "Profile Edited!", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "error is :"+t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }
}