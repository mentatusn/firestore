package ru.geekbrains.socialnetwork.data;

import java.util.Random;

import ru.geekbrains.socialnetwork.R;

public class PictureIndexConverter {
    static Random random = new Random();
    Object object = new Object();
    static int[] pictureIndex = {
            R.drawable.nature1,
            R.drawable.nature2,
            R.drawable.nature3,
            R.drawable.nature4,
            R.drawable.nature5,
            R.drawable.nature6,
            R.drawable.nature7,
    };
    public static int randomPictureIndex(){
        return random.nextInt(pictureIndex.length);
    }

    public static int getIndexByPicture(int picture) {
        for (int i=0;i<pictureIndex.length;i++){
            if(picture == pictureIndex[i]){
                return i;
            }
        }
        return 0;
    }

    public static int getPictureByIndex(int index) {
        return pictureIndex[index];
    }
}
