package pe.edu.uni.valegrei.proyectofinal.api;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pe.edu.uni.valegrei.proyectofinal.Comment;
import pe.edu.uni.valegrei.proyectofinal.Post;
import pe.edu.uni.valegrei.proyectofinal.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {

    private static RestApi restApi;
    private final Api api;

    public static RestApi getInstance(Context context) {
        if (restApi == null) {
            restApi = new RestApi(context);
        }
        return restApi;
    }

    private RestApi(Context context) {

        String apiUrl = context.getString(R.string.url_api);

        Gson gson = new GsonBuilder()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(Api.class);
    }

    public void getPosts(Callback<RespPosts> callback) {
        Call<RespPosts> resp = api.getPosts();
        resp.enqueue(callback);
    }

    public void getComments(String postId, Callback<RespComments> callback) {
        Call<RespComments> resp = api.getComments(postId);
        resp.enqueue(callback);
    }

    public void uploadPost(Post post, Callback<Resp> callback) {
        Call<Resp> resp = api.uploadPost(post);
        resp.enqueue(callback);
    }

    public void uploadComment(Comment comment, Callback<Resp> callback) {
        Call<Resp> resp = api.uploadComment(comment);
        resp.enqueue(callback);
    }
}