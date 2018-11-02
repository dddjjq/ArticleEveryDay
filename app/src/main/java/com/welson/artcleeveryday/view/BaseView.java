package com.welson.artcleeveryday.view;

import com.welson.artcleeveryday.entity.MainData;

public interface BaseView {
    void showSuccess(MainData mainData);
    void showError();
}
