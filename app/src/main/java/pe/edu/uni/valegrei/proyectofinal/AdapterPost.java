package pe.edu.uni.valegrei.proyectofinal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import pe.edu.uni.valegrei.proyectofinal.databinding.PostItemLayoutBinding;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.ViewHolder> {
    private static final String TAG = AdapterPost.class.getSimpleName();
    private final Context context;
    private List<Post> posts;
    private final OnItemClickListener listener;

    public AdapterPost(Context context, List<Post> posts, OnItemClickListener listener) {
        this.context = context;
        this.posts = posts;
        this.listener = listener;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostItemLayoutBinding binding = PostItemLayoutBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (posts != null)
            holder.bindData(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PostItemLayoutBinding binding;

        public ViewHolder(@NonNull PostItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(Post post) {
            binding.tvPostTitle.setText(post.getPostTitle());
            binding.tvPostBody.setText(post.getShortBody());
            //Icono Usuario
            String urlIcon = context.getString(R.string.url_gravatar, Utils.md5(post.getPostEmail()).toLowerCase());
            Log.d(TAG,urlIcon);
            Glide.with(binding.imgPostUser)
                    .load(urlIcon)
                    .circleCrop()
                    .into(binding.imgPostUser);
            //Foto
            if (post.hasPostPhoto()) {
                binding.imgPostPhoto.setVisibility(View.VISIBLE);
                Glide.with(binding.imgPostPhoto)
                        .load(post.getPostPhoto())
                        .into(binding.imgPostPhoto);
            } else {
                binding.imgPostPhoto.setVisibility(View.GONE);
            }
            binding.lyPostItem.setOnClickListener(v->{
                if(listener!=null)
                    listener.onitemClick(post);
            });
        }
    }

    public interface OnItemClickListener{
        void onitemClick(Post post);
    }
}
