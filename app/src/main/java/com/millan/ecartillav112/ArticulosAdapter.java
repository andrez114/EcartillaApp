package com.millan.ecartillav112;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class ArticulosAdapter extends FirestoreRecyclerAdapter<Articulo,ArticulosAdapter.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private FirestoreRecyclerOptions<Articulo> mData;
    private Context context;
    final ArticulosAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Articulo item);
    }



    public ArticulosAdapter(@NonNull FirestoreRecyclerOptions<Articulo> options,Context context, ArticulosAdapter.OnItemClickListener listener) {
        super(options);
        this.context=context;
        this.listener=listener;
        this.mData = options;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Articulo articulo) {
        holder.card.setAnimation(AnimationUtils.loadAnimation(context,R.anim.slide));
    holder.textViewTitulo.setText(articulo.getTitulo());
    holder.textViewFecha.setText(articulo.getFecha());
    holder.textViewContenido.setText(articulo.getContenido());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));
        Glide.with(context).load(getItem(position).getImageUrl()).apply(requestOptions).into(holder.imageView);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(getItem(position));
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_articulos,viewGroup, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitulo;
        TextView textViewContenido;
        TextView textViewFecha;
        CardView card;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewContenido = itemView.findViewById(R.id.textViewContenido);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            card = itemView.findViewById(R.id.card);
            imageView = itemView.findViewById(R.id.m_image);


            }



        void bindData(final Articulo item){
            textViewTitulo.setText(item.getTitulo());
            textViewContenido.setText(item.getContenido());
            textViewFecha.setText(item.getFecha());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }

    }
}
