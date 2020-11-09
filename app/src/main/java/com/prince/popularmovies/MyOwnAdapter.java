package com.prince.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyOwnAdapter extends RecyclerView.Adapter<MyOwnAdapter.MyOwnViewHandler> {
    Context context;
    List<String> movieName;
    List<String> movieImage;
    List<Double> movieRating;
    final private ListItemClickListener mClickListener;
    @NonNull
    @Override
    public MyOwnViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.grid_element_layout,parent,false);


        return new MyOwnViewHandler(view);
    }


    public interface ListItemClickListener{
         void onListItemClick(int listItemIndex);

    }




    @Override
    public void onBindViewHolder(@NonNull MyOwnViewHandler holder, int position) {
        holder.tvName.setText(movieName.get(position));
        holder.tvDescription.setText(""+movieRating.get(position));
      //  holder.animalImage.setImageResource(animalImage[position]);
        Picasso.get().load("http://image.tmdb.org/t/p/w185//"+movieImage.get(position)).into(holder.animalImage);

    }

    @Override
    public int getItemCount() {
        return movieName.size();
    }

    public class MyOwnViewHandler extends RecyclerView.ViewHolder implements View.OnClickListener {
         public final TextView tvName;
         public final TextView tvDescription;
         public final ImageView animalImage;

        public MyOwnViewHandler(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textView);
            tvDescription = itemView.findViewById(R.id.textView2);
            animalImage = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedItemIndex = getAdapterPosition();
            mClickListener.onListItemClick(clickedItemIndex);

        }
    }
    MyOwnAdapter(Context context,Parameters parameters,ListItemClickListener listener ){
        this.context=context;
        this.movieName = parameters.getMovieName();
        this.movieRating = parameters.getMovieRating();
        this.movieImage = parameters.getMovieImage();
        mClickListener = listener;
        notifyDataSetChanged();
    }
}
