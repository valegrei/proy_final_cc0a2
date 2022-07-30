package pe.edu.uni.valegrei.proyectofinal;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import pe.edu.uni.valegrei.proyectofinal.data.Resp;
import pe.edu.uni.valegrei.proyectofinal.data.RestApi;
import pe.edu.uni.valegrei.proyectofinal.databinding.ActivityNewPostBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostActivity extends AppCompatActivity {
    private static final String EMAIL_KEY = "email";
    private static final String TITLE_KEY = "title";
    private static final String BODY_KEY = "body";
    private static final String IMAGE_URL_KEY = "imageUrl";
    private ActivityNewPostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            cargarGuardado(savedInstanceState);
        }

        binding.btnPost.setOnClickListener(v -> nuevoPost());
    }

    private void cargarGuardado(Bundle bundle) {
        String email = bundle.getString(EMAIL_KEY);
        String title = bundle.getString(TITLE_KEY);
        String body = bundle.getString(BODY_KEY);
        String imageUrl = bundle.getString(IMAGE_URL_KEY);

        binding.edtEmail.append(email);
        binding.edtTitle.append(title);
        binding.edtBody.append(body);
        binding.edtImageUrl.append(imageUrl);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //Capturando de formulario
        String email = binding.edtEmail.getText().toString().trim();
        String title = binding.edtTitle.getText().toString().trim();
        String body = binding.edtBody.getText().toString().trim();
        String imageUrl = binding.edtImageUrl.getText().toString().trim();

        //Guardando en Bundle
        outState.putString(EMAIL_KEY, email);
        outState.putString(TITLE_KEY, title);
        outState.putString(BODY_KEY, body);
        outState.putString(IMAGE_URL_KEY, imageUrl);

        super.onSaveInstanceState(outState);
    }

    private void nuevoPost() {
        //Capturando de formulario
        String email = binding.edtEmail.getText().toString().trim();
        String title = binding.edtTitle.getText().toString().trim();
        String body = binding.edtBody.getText().toString().trim();
        String imageUrl = binding.edtImageUrl.getText().toString().trim();

        //Validar
        if (email.isEmpty() || title.isEmpty() || body.isEmpty()) {
            showSnackBar(R.string.text_completar);
            return;
        }

        //Nuevo post
        Post post = new Post(email, title, body, imageUrl);

        //Subiendo
        mostrarCarga();
        RestApi.getInstance(this).uploadPost(post, new Callback<Resp>() {
            @Override
            public void onResponse(@NonNull Call<Resp> call, @NonNull Response<Resp> response) {
                if (response.isSuccessful()) {
                    salir();
                } else {
                    showSnackBar(R.string.err_upload_post);
                }
                ocultarCarga();
            }

            @Override
            public void onFailure(@NonNull Call<Resp> call, @NonNull Throwable t) {
                showSnackBar(R.string.err_upload_post);
                ocultarCarga();
            }
        });
    }

    private void mostrarCarga() {
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.edtEmail.setEnabled(false);
        binding.edtTitle.setEnabled(false);
        binding.edtBody.setEnabled(false);
        binding.edtImageUrl.setEnabled(false);
        binding.btnPost.setEnabled(false);
    }

    private void ocultarCarga() {
        binding.progressbar.setVisibility(View.GONE);
        binding.edtEmail.setEnabled(true);
        binding.edtTitle.setEnabled(true);
        binding.edtBody.setEnabled(true);
        binding.edtImageUrl.setEnabled(true);
        binding.btnPost.setEnabled(true);
    }

    private void salir() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    private void showSnackBar(int resId) {
        Snackbar.make(binding.lyNewPost, resId, Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, v -> {
        }).show();
    }
}