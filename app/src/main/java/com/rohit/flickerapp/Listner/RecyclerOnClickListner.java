package com.rohit.flickerapp.Listner;

import android.view.View;

// added in version 3.0

public interface RecyclerOnClickListner {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
