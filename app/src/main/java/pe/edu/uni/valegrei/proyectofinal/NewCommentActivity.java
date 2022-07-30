package pe.edu.uni.valegrei.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import pe.edu.uni.valegrei.proyectofinal.api.Resp;
import pe.edu.uni.valegrei.proyectofinal.api.RestApi;
import pe.edu.uni.valegrei.proyectofinal.databinding.ActivityNewCommentBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewCommentActivity extends AppCompatActivity {
    private static final String EMAIL_KEY = "email";
    private static final String BODY_KEY = "body";

    public static final String POST_ID = "post_id";

    private ActivityNewCommentBinding binding;

    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            postId = intent.getStringExtra(POST_ID);
        }

        if (savedInstanceState != null) {
            cargarGuardado(savedInstanceState);
        }

        binding.btnComment.setOnClickListener(v -> nuevoComentario());
    }


    private void cargarGuardado(Bundle bundle) {
        String email = bundle.getString(EMAIL_KEY);
        String body = bundle.getString(BODY_KEY);

        binding.edtEmail.append(email);
        binding.edtComment.append(body);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //Capturando de formulario
        String email = binding.edtEmail.getText().toString().trim();
        String body = binding.edtComment.getText().toString().trim();

        //Guardando en Bundle
        outState.putString(EMAIL_KEY, email);
        outState.putString(BODY_KEY, body);

        super.onSaveInstanceState(outState);
    }


    private void nuevoComentario() {
        //Capturando de formulario
        String email = binding.edtEmail.getText().toString().trim();
        String body = binding.edtComment.getText().toString().trim();

        //Validar
        if (email.isEmpty() || body.isEmpty()) {
            showSnackBar(R.string.text_completar);
            return;
        }

        //Nuevo post
        Comment comment = new Comment(postId, email, body);

        //Subiendo
        mostrarCarga();
        RestApi.getInstance(this).uploadComment(comment, new Callback<Resp>() {
            @Override
            public void onResponse(@NonNull Call<Resp> call, @NonNull Response<Resp> response) {
                if (response.isSuccessful()) {
                    salir();
                } else {
                    showSnackBar(R.string.err_upload_comment);
                }
                ocultarCarga();
            }

            @Override
            public void onFailure(@NonNull Call<Resp> call, @NonNull Throwable t) {
                showSnackBar(R.string.err_upload_comment);
                ocultarCarga();
            }
        });
    }

    private void mostrarCarga() {
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.edtEmail.setEnabled(false);
        binding.edtComment.setEnabled(false);
        binding.btnComment.setEnabled(false);
    }

    private void ocultarCarga() {
        binding.progressbar.setVisibility(View.GONE);
        binding.edtEmail.setEnabled(true);
        binding.edtComment.setEnabled(true);
        binding.btnComment.setEnabled(true);
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
        Snackbar.make(binding.lyNewComment, resId, Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, v -> {
        }).show();
    }
}