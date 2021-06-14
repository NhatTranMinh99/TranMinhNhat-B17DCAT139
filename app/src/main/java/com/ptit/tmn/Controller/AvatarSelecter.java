package com.ptit.tmn.Controller;

import com.ptit.tmn.R;

import java.util.ArrayList;
import java.util.Random;

public class AvatarSelecter {

    ArrayList<Integer> avatars;

    public AvatarSelecter() {
        avatars = new ArrayList<Integer>();
        avatars.add(R.drawable.ahri);
        avatars.add(R.drawable.akali);
        avatars.add(R.drawable.caitlin);
        avatars.add(R.drawable.ekko);
        avatars.add(R.drawable.ezreal);
        avatars.add(R.drawable.illaoi);
        avatars.add(R.drawable.jhin);
        avatars.add(R.drawable.jinx);
        avatars.add(R.drawable.kata);
        avatars.add(R.drawable.lux);
        avatars.add(R.drawable.neeko);
        avatars.add(R.drawable.pyke);
        avatars.add(R.drawable.qiyana);
        avatars.add(R.drawable.red);
        avatars.add(R.drawable.sett);
        avatars.add(R.drawable.shen);
        avatars.add(R.drawable.thresh);
        avatars.add(R.drawable.yasuo);
        avatars.add(R.drawable.yone);
        avatars.add(R.drawable.zed);
        avatars.add(R.drawable.zoe);
    }

    public Integer randomAvatar() {
        Random rand = new Random();
        int randomNum = rand.nextInt(avatars.size());
        return randomNum;
    }

    public boolean has(Integer imageSrc) {
        if (0 <= imageSrc && imageSrc <= 20)
            return true;
        return false;
    }

    public Integer getAvatar(Integer imageSrc) {
        return avatars.get(imageSrc);
    }
}
