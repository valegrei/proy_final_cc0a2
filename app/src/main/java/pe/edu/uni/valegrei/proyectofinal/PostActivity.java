package pe.edu.uni.valegrei.proyectofinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import pe.edu.uni.valegrei.proyectofinal.adapters.AdapterComment;
import pe.edu.uni.valegrei.proyectofinal.api.RespComments;
import pe.edu.uni.valegrei.proyectofinal.api.RestApi;
import pe.edu.uni.valegrei.proyectofinal.databinding.ActivityPostBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    private static final String TAG = PostActivity.class.getSimpleName();
    public static final String POST = "post";
    private ActivityPostBinding binding;
    private AdapterComment adapter;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            post = intent.getParcelableExtra(POST);
        }
        if (savedInstanceState != null) {
            post = savedInstanceState.getParcelable(POST);
        }

        adapter = new AdapterComment(this, null);
        binding.rvComments.setLayoutManager(new LinearLayoutManager(this));
        binding.rvComments.setAdapter(adapter);
        binding.btnNewComment.setOnClickListener(v -> nuevoComentario());

        cargarPost();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(POST, post);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarComentarios();
    }

    private void nuevoComentario() {
        Intent intent = new Intent(this, NewCommentActivity.class);
        intent.putExtra(NewCommentActivity.POST_ID, post.getPostId());
        startActivity(intent);
    }

    private void cargarComentarios() {
        mostrarCarga();
        RestApi.getInstance(this).getComments(post.getPostId(), new Callback<RespComments>() {
            @Override
            public void onResponse(@NonNull Call<RespComments> call, @NonNull Response<RespComments> response) {
                if (response.isSuccessful()) {
                    RespComments respComments = response.body();
                    if (respComments != null) {
                        List<Comment> comments = respComments.getComments();
                        Collections.sort(comments);
                        actualizarLista(comments);
                    }
                } else {
                    Log.e(TAG, getString(R.string.err_download));
                    showSnackBar(R.string.err_download);
                }
                ocultarCarga();
            }

            @Override
            public void onFailure(@NonNull Call<RespComments> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                showSnackBar(R.string.err_download);
                ocultarCarga();
            }
        });
    }

    private void mostrarCarga() {
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.tvPostCommentsCount.setVisibility(View.GONE);
        binding.rvComments.setVisibility(View.GONE);
    }

    private void ocultarCarga() {
        binding.progressbar.setVisibility(View.GONE);
        binding.tvPostCommentsCount.setVisibility(View.VISIBLE);
        binding.rvComments.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void actualizarLista(List<Comment> comments) {
        binding.tvPostCommentsCount.setText(getString(R.string.text_comentarios, comments != null ? comments.size() : 0));
        adapter.setComments(comments);
        adapter.notifyDataSetChanged();
    }

    private void showSnackBar(int resId) {
        Snackbar.make(binding.lyPostMain, resId, Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, v -> {
        }).show();
    }

    private void cargarPost() {
        binding.lyPost.tvPostEmail.setText(post.getPostEmail());
        binding.lyPost.tvPostDate.setText(Utils.fechaFormato(post.getPostDate()));
        binding.lyPost.tvPostTitle.setText(post.getPostTitle());
        binding.lyPost.tvPostBody.setText(post.getPostBody());
        //Icono Usuario
        String urlIcon = getString(R.string.url_gravatar, Utils.md5(post.getPostEmail()).toLowerCase());
        Glide.with(binding.lyPost.imgPostUser)
                .load(urlIcon)
                .circleCrop()
                .into(binding.lyPost.imgPostUser);
        //Foto
        if (post.hasPostPhoto()) {
            binding.lyPost.imgPostPhoto.setVisibility(View.VISIBLE);
            Glide.with(binding.lyPost.imgPostPhoto)
                    .load(post.getPostPhoto())
                    .into(binding.lyPost.imgPostPhoto);
        } else {
            binding.lyPost.imgPostPhoto.setVisibility(View.GONE);
        }
    }
}