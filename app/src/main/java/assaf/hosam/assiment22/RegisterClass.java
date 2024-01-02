package assaf.hosam.assiment22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class RegisterClass extends AppCompatActivity {

    private Button logInButton;
    private Button signUp;
    private EditText first_name;
    private EditText last_name;
    private EditText email_;
    private EditText password_;
    private EditText phoneNumber_;
    private EditText confirm_password;
    private TextView error;
    SharedPreferences preferences ;

    SharedPreferences.Editor editor ;
    Gson gson=new Gson();
    private static final String firstName = "First Name";
    private static final String lastName = "Last Name";
    private static final String email = "Email";
    private static final String password = "Password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        preferences = getSharedPreferences("DATA", MODE_PRIVATE);
        editor = preferences.edit();
        signUp = findViewById(R.id.signIn);
        first_name = findViewById(R.id.email1);
        last_name = findViewById(R.id.lastName2);
        email_ = findViewById(R.id.email);
        password_ = findViewById(R.id.password);
        confirm_password = findViewById(R.id.password2);
        error = findViewById(R.id.errorM);

    }


    public void login(View view) {

        Intent intent = new Intent(RegisterClass.this, LogIn.class);
        startActivity(intent);
        finish();
    }



    public void signUp(View view) {
        String firstNameValue = first_name.getText().toString();
        String lastNameValue = last_name.getText().toString();
        String emailValue = email_.getText().toString();
        String passwordValue = password_.getText().toString();
        String confirmPasswordValue = confirm_password.getText().toString();

        if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || emailValue.isEmpty()
                || passwordValue.isEmpty() || confirmPasswordValue.isEmpty()) {
            error.setText("Fill in all fields");
        } else if (!passwordValue.equals(confirmPasswordValue)) {
            error.setText("Passwords do not match");
        } else {
            if(preferences.getString(emailValue,null)==null) {
               Email email=new Email( emailValue,  passwordValue,  firstNameValue,  lastNameValue);
                String emailobj= gson.toJson(email);
                  editor.putString(emailValue,emailobj);
                  editor.commit();
                Toast.makeText(this, "New Account Created!", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "This Account Already Exist", Toast.LENGTH_SHORT).show();
            }





        }
    }




}

