package pe.edu.uni.valegrei.proyectofinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import pe.edu.uni.valegrei.proyectofinal.data.RespPosts;
import pe.edu.uni.valegrei.proyectofinal.data.RestApi;
import pe.edu.uni.valegrei.proyectofinal.databinding.ActivityFeedBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedActivity extends AppCompatActivity implements AdapterPost.OnItemClickListener {
    private static final String TAG = FeedActivity.class.getSimpleName();
    private ActivityFeedBinding binding;
    private AdapterPost adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new AdapterPost(this,null, this);
        binding.rvPosts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPosts.setAdapter(adapter);

        loadPosts();
    }

    private void loadPosts() {
        RestApi.getInstance(this).getPosts(new Callback<RespPosts>() {
            @Override
            public void onResponse(@NonNull Call<RespPosts> call, @NonNull Response<RespPosts> response) {
                if (response.isSuccessful()) {
                    RespPosts respPosts = response.body();
                    if (respPosts != null) {
                        actualizarLista(respPosts.getPosts());
                    }
                }else{
                    Log.e(TAG, getString(R.string.err_download));
                    showSnackBar(R.string.err_download);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RespPosts> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                showSnackBar(R.string.err_download);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void actualizarLista(List<Post> posts) {
        adapter.setPosts(posts);
        adapter.notifyDataSetChanged();
    }

    private void showSnackBar(int resId){
        Snackbar.make(binding.lyFeed, resId, Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, v -> {}).show();
    }

    @Override
    public void onitemClick(Post post) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(PostActivity.POST, post);
        startActivity(intent);
    }
}