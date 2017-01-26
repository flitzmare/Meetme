package riksasuviana.apps.meetme;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by riksasuviana on 24/01/17.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    int tabcount;

    public HomePagerAdapter(FragmentManager fm, int tabcount){
        super(fm);
        this.tabcount = tabcount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Friendlist sf = new Friendlist();
                return sf;
            default:
                ChatlistActivity sft = new ChatlistActivity();
                return sft;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
