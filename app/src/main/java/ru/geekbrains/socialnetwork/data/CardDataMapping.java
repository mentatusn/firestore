package ru.geekbrains.socialnetwork.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class CardDataMapping {
    public static class Fields {
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String PICTURE = "picture";
        public static final String DATE = "date";
        public static final String LIKE = "like";
    }

    public static CardData toCardDate(String id, Map<String, Object> document) {
        long indexPicture = (long) document.get(Fields.PICTURE);
        Timestamp timestamp = (Timestamp) document.get(Fields.DATE);
        CardData cardData = new CardData((String) document.get(Fields.TITLE),
                (String) document.get(Fields.DESCRIPTION),
                (int)indexPicture,
                (boolean) document.get(Fields.LIKE), timestamp.toDate());
        cardData.setId(id);
        return cardData;
    }

    public static Map<String, Object> toDocument(CardData cardData) {
        Map<String, Object> document = new HashMap<>();
        document.put(Fields.TITLE,cardData.getTitle());
        document.put(Fields.DESCRIPTION,cardData.getDescription());
        document.put(Fields.PICTURE,PictureIndexConverter.getIndexByPicture(cardData.getPicture()));
        document.put(Fields.DATE,cardData.getDate());
        document.put(Fields.LIKE,cardData.isLike());
        return document;
    }
}
