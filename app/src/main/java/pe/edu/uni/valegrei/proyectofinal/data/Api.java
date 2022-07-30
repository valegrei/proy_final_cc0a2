package pe.edu.uni.valegrei.proyectofinal.data;

import pe.edu.uni.valegrei.proyectofinal.Comment;
import pe.edu.uni.valegrei.proyectofinal.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("posts")
    Call<RespPosts> getPosts();

    @GET("comments")
    Call<RespComments> getComments(@Query("postId") String postId);

    @POST("posts")
    Call<Resp> uploadPost(@Body Post post);

    @POST("comments")
    Call<Resp> uploadComment(@Body Comment comment);
}
