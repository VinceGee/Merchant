package com.vhg.empire.merchant;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vhg.empire.merchant.Account.AccountFragment;
import com.vhg.empire.merchant.Cart.CardViewFragment;
import com.vhg.empire.merchant.Account.ExpandableFragment;
import com.vhg.empire.merchant.Cart.search.Search;
import com.vhg.empire.merchant.Cart.search.scanner.FullScannerFragment;
import com.vhg.empire.merchant.Cart.search.scanner.TextingFragment;

/**
 * Created by VinceGee on 9/11/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final class FirstPageListener implements
            FirstPageFragmentListener {
        public void onSwitchToNextFragment() {
            mFragmentManager.beginTransaction().remove(mFragmentAtPos0)
                    .commit();
            if (mFragmentAtPos0 instanceof Search){
                mFragmentAtPos0 = new FullScannerFragment(listener);
            }else{ // Instance of NextFragment
                mFragmentAtPos0 = new Search(listener);
            }
            notifyDataSetChanged();
        }
    }

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    /*private FragmentManager mFragmentManager;
    private Fragment mFragmentAtPos0;
    */
    private final FragmentManager mFragmentManager;
    public Fragment mFragmentAtPos0;
    private Context context;
    FirstPageListener listener = new FirstPageListener();

   /* public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }*/



    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        mFragmentManager = fm;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: // Fragment # 0
                if (mFragmentAtPos0 == null)
                {
                    mFragmentAtPos0 = new Search(listener);
                }
                return mFragmentAtPos0;

            case 1: // Fragment # 1
                return new CardViewFragment();
            case 2:// Fragment # 2
                return new AccountFragment();
        }
        return null;




    }

    @Override
    public int getItemPosition(Object object)
    {
        if (object instanceof Search &&
                mFragmentAtPos0 instanceof FullScannerFragment) {
            return POSITION_NONE;
        }
        if (object instanceof FullScannerFragment &&
                mFragmentAtPos0 instanceof Search) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {

        return NumbOfTabs;
    }


}




