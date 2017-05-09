package com.timeset.codechallenge.interfaces;

/**
 * Created by rakeshh91 on 3/18/2017.
 * Interface to transfer the button click in a fragment to be handled at activity level for fragment transactions
 */


public interface HandleNavigationListener {
    public void navigateToItemSelected(int buttonId);
}
