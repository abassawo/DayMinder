package abassawo.c4q.nyc.dayminder;

//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.List;
//

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by c4q-Abass on 7/29/15.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ContactViewHolder> {

    private List<Note> cardList;

    public CardAdapter(List<Note> cardList) {
        this.cardList = cardList;
    }


    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder cardTitleViewHolder, int i) {
       Note ci = cardList.get(i);
        cardTitleViewHolder.vName.setText(ci.getTitle());
//        contactViewHolder.vSurname.setText(ci.surname);
//        contactViewHolder.vEmail.setText(ci.email);
//        contactViewHolder.vTitle.setText(ci.name + " " + ci.surname);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardtitles_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;


        public ContactViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);

        }
    }
}
