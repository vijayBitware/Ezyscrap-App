package com.ezyscrap.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ezyscrap.Model.Login.CategoryDatum;
import com.ezyscrap.R;

import java.util.List;

/**
 * Created by vincentpaing on 6/7/16.
 */
public class OverlapFragment extends Fragment {

  int resourceId;
  static final String ARG_RES_ID = "ARG_RES_ID";
  static List<CategoryDatum> arrayList1;
  private float mx,my;

  public static OverlapFragment newInstance(int resourceId,List<CategoryDatum> arrayList) {
    OverlapFragment overlapFragment = new OverlapFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(ARG_RES_ID, resourceId);
    overlapFragment.setArguments(bundle);
    arrayList1 = arrayList;
    return overlapFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    resourceId = getArguments().getInt(ARG_RES_ID);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.item_overlap_cover, container, false);
    final ImageView coverImageView = (ImageView) rootView.findViewById(R.id.image_cover);
    coverImageView.setImageResource(arrayList1.get(resourceId).getSampleImage());

    /*Glide.with(this)
            .load(arrayList1.get(resourceId).getCategorySmallImage())
            .centerCrop()
            .into(coverImageView);*/

    return rootView;
  }
}
