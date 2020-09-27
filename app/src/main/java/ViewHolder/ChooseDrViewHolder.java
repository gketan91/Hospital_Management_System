package ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ketan_studio.hospital.R;

public class ChooseDrViewHolder extends RecyclerView.ViewHolder {
    public TextView drname;
    public TextView drQuali;
    public Button add;

    public ChooseDrViewHolder(@NonNull View itemView) {
        super(itemView);
        drname = (TextView)itemView.findViewById(R.id.drname_recler_chooseDr);
        drQuali = (TextView)itemView.findViewById(R.id.drQuali_recler_chooseDr);
        add = (Button)itemView.findViewById(R.id.button_recler_chooseDr);
    }
}
