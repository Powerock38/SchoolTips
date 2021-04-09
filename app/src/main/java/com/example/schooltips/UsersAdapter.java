package com.example.schooltips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<User> {

    public UsersAdapter(Context mCtx, List<User> userList) {
        super(mCtx, R.layout.template_user, userList);
    }

    /**
     * Remplit une ligne de la userView avec les informations de les infos associés
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Récupération de la multiplication
        final User user = getItem(position);

        // Charge le template XML
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.template_user, parent, false);

        // Récupération des objets graphiques dans le template
        TextView textViewName = (TextView) rowView.findViewById(R.id.textViewName);
        TextView textViewFirstName = (TextView) rowView.findViewById(R.id.textViewFirstName);
        TextView textViewHighScore = (TextView) rowView.findViewById(R.id.textViewHighScore);

        //
        textViewName.setText(user.getNom());
        textViewFirstName.setText(user.getPrenom());
        textViewHighScore.setText(getContext().getString(R.string.highScore, user.getHighScore()));

        //
        return rowView;
    }

}