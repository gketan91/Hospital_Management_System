package ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ketan_studio.hospital.R;

public class SeeApoinmentViewHolder extends RecyclerView.ViewHolder {

    public TextView name;

    public SeeApoinmentViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name_textView_seeAppoinment);
    }
}
