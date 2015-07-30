package abassawo.c4q.nyc.dayminder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class GoalCardsFragment extends Fragment {
    private View rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      rv = inflater.inflate(R.layout.fragment_goal_list, container, false);
        RecyclerView recList = (RecyclerView) rv.findViewById(R.id.cardList);
        recList.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        CardAdapter cardAdapter = new CardAdapter(createList(5)); //Testing
        recList.setAdapter(cardAdapter);
       // init_standard_header();
        //CardExpand expand = new CardExpand(getContext());
        return rv;
    }



    private void init_standard_header() {

        //Create a Card
        Card card = new Card(getActivity());

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());

        //Set the header title
        //header.setTitle(getString(R.string.demo_header_basetitle));

        //Set visible the expand/collapse button
        header.setButtonExpandVisible(true);

        //Add Header to card
        card.addCardHeader(header);

        //This provides a simple (and useless) expand area
        CardExpand expand = new CardExpand(getActivity());
        //Set inner title in Expand Area
        expand.setTitle("This is the first test");
        card.addCardExpand(expand);

        //Set card in the cardView
      //  CardView cardView = (CardView) getActivity().findViewById(R.id.carddemo_example_card_expand1);
       // cardView.setCard(card);
    }


    private List<Note> createList(int size) {   //For test
        List<Note> NoteCardList = new ArrayList<>();
        for (int i=1; i <= size; i++) {
            Note ci = new Note();
            ci.setTitle("Urban Commune");
            NoteCardList.add(ci);
        }
        return NoteCardList;
    }
}
