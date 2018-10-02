package com.rohit.flickerapp.Listner;

import android.view.View;

public interface RecyclerOnClickListner {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
