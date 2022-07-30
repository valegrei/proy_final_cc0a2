package pe.edu.uni.valegrei.proyectofinal.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import pe.edu.uni.valegrei.proyectofinal.Comment;
import pe.edu.uni.valegrei.proyectofinal.R;
import pe.edu.uni.valegrei.proyectofinal.Utils;
import pe.edu.uni.valegrei.proyectofinal.databinding.CommentLayoutBinding;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {
    private static final String TAG = AdapterComment.class.getSimpleName();
    private final Context context;
    private List<Comment> comments;

    public AdapterComment(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentLayoutBinding binding = CommentLayoutBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (comments != null)
            holder.bindData(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CommentLayoutBinding binding;

        public ViewHolder(@NonNull CommentLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(Comment comment) {
            binding.tvPostEmail.setText(comment.getCommentEmail());
            binding.tvPostDate.setText(Utils.fechaFormato(comment.getCommentDate()));
            binding.tvPostBody.setText(comment.getCommentBody());
            //Icono Usuario
            String urlIcon = context.getString(R.string.url_gravatar, Utils.md5(comment.getCommentEmail()).toLowerCase());
            Log.d(TAG, urlIcon);
            Glide.with(binding.imgPostUser)
                    .load(urlIcon)
                    .circleCrop()
                    .into(binding.imgPostUser);
        }
    }

}
