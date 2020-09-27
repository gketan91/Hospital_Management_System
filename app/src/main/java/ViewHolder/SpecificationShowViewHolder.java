package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ketan_studio.hospital.R;

public class SpecificationShowViewHolder extends RecyclerView.ViewHolder{
    public ImageView pic;
    public TextView spec;
    public CardView card;


    public SpecificationShowViewHolder(@NonNull View itemView) {
        super(itemView);
        pic = (ImageView)itemView.findViewById(R.id.image_recycle_choose_specification_id);
        spec = (TextView)itemView.findViewById(R.id.textview_recycle_choose_specification_id);
        card = (CardView)itemView.findViewById(R.id.card);
    }
}
