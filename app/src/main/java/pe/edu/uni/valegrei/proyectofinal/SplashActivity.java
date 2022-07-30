package pe.edu.uni.valegrei.proyectofinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import pe.edu.uni.valegrei.proyectofinal.databinding.ActivitySplashBinding;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Carga animacion antihorario y escalando
        Animation animationImage = AnimationUtils.loadAnimation(this, R.anim.image_animation);
        binding.imgLogo.setAnimation(animationImage);

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