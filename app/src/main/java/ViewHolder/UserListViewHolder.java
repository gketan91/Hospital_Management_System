package ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ketan_studio.hospital.R;

public class UserListViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView email;
    public Button makeaddmin;
    public UserListViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name);
        email = (TextView)itemView.findViewById(R.id.email);
        makeaddmin = (Button)itemView.findViewById(R.id.makeAdminButton);
    }
}
