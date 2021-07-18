package ru.geekbrains.socialnetwork.data;

import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import ru.geekbrains.socialnetwork.R;

public class CardsSourceFirebaseImpl implements CardsSource {

    static String CARDS_COLLECTION = "cards";

    FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();

    CollectionReference collectionReference = firebaseFirestore.collection(CARDS_COLLECTION);

    private List<CardData> cardsData= new ArrayList<>();;


    @Override
    public CardsSource init(CardsSourceResponse cardsSourceResponse) {

        collectionReference.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                cardsData= new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document :task.getResult()){
                        Map<String,Object> documentMap = document.getData();
                        String id = document.getId();
                        CardData cardData = CardDataMapping.toCardDate(id,documentMap);
                        cardsData.add(cardData);
                    }
                }
                cardsSourceResponse.initialized(CardsSourceFirebaseImpl.this);
            }
        });
        return this;
    }

    public CardData getCardData(int position) {
        return cardsData.get(position);
    }

    public int size(){
        return cardsData.size();
    }

    @Override
    public void deleteCardData(int position) {
        collectionReference.document(cardsData.get(position).getId()).delete();
        cardsData.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        collectionReference.document(cardsData.get(position).getId()).set(CardDataMapping.toDocument(cardData)); //?
        cardsData.set(position, cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
        collectionReference.add(CardDataMapping.toDocument(cardData));
        cardsData.add(cardData);
    }

    @Override
    public void clearCardData() {
        for (CardData cardData:cardsData){
            collectionReference.document(cardData.getId()).delete();
        }
        cardsData.clear();
    }
}
