package com.yanghyeonjin.androidexamples.model;

import androidx.annotation.NonNull;

public class Contributor {

    String login;
    String html_url;

    int contributions;

    @NonNull
    @Override
    public String toString() {
        return login + " (" + contributions + ")";
    }
}
