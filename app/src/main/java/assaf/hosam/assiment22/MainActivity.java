package assaf.hosam.assiment22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.show);


    }
    public void log(View view){
      //  Intent intent = new Intent(MainActivity.this, LogIn.class);
   //     startActivity(intent);
      //  finish();
    }
}