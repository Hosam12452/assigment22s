package assaf.hosam.assiment22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;


public class LogIn extends AppCompatActivity {

    private Button signUp;
    private Button signIn;
    private EditText email_;
    private EditText password_;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
   private Gson gson =new Gson();
   CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_xml);
        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUpBtn);
        email_ = findViewById(R.id.email1);
        password_ = findViewById(R.id.password);
        checkBox=findViewById(R.id.chkRemember);
        prefs = getSharedPreferences("DATA", MODE_PRIVATE);
        editor = prefs.edit();
    }
    @Override
    protected void onStop() {
        if(checkBox.isChecked()){
            editor.putString("ch",email_.getText().toString());
            editor.putString("psr",password_.getText().toString());

            editor.commit();
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        if(prefs.getString("ch",null)!=null){
            email_.setText(prefs.getString("ch",null));
            password_.setText(prefs.getString("psr",null));
        }
        super.onStart();
    }

    public void signIn(View view) {
        String email = email_.getText().toString();
        String password = password_.getText().toString();
        // Perform validation checks
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }else{
           String s= prefs.getString(email,null);
            Email em=gson.fromJson(s,Email.class);
            if(s==null ){
                Toast.makeText(this, "Email Doe's not Exist", Toast.LENGTH_SHORT).show();
            }else{
                if(em.getPassword().equals(password)){
                    Intent intent=new Intent(LogIn.this,Population.class);
                    intent.putExtra("firstName",em.firstName);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, " Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }


        }

    }


    public void signU(View view) {
        Intent intent = new Intent(LogIn.this, RegisterClass.class);
        startActivity(intent);
        finish();
    }
}
