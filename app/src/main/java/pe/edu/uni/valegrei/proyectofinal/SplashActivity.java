package pe.edu.uni.valegrei.proyectofinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    ImageView imgLogo;
    Animation animationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgLogo = findViewById(R.id.img_logo);

        //Carga animacion antihorario y escalando
        animationImage = AnimationUtils.loadAnimation(this, R.anim.image_animation);
        imgLogo.setAnimation(animationImage);

        //Lanza Main despues de 5 segundos
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, FeedActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}