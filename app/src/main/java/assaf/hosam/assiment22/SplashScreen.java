package assaf.hosam.assiment22;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class SplashScreen extends AppCompatActivity {


    private Animation top, bottom,spin;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        img = findViewById(R.id.img);

        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        spin = AnimationUtils.loadAnimation(this,R.anim.spin_animation);



        AnimationSet s = new AnimationSet(false);
        s.addAnimation(top);
        s.addAnimation(spin);
        img.setAnimation(s);







        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                    //  go to LogIN activity
                    Intent intent = new Intent(SplashScreen.this, LogIn.class);
                    startActivity(intent);


                finish();
            }
        }, 3000);



    }


}
