package com.ezyscrap.AppUtils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.ezyscrap.R;


/**
 * This class for display error alert.
 * @author (Arun Chougule)
 */

public class AlertClass{

	/**
	 *  This method for display pop up for error or success 
	 *  @param Activity - activity
	 *  @param String - title
	 *  @param String - message
	 */
	
	
	public void customDialogforAlertMessage(Activity activity, String title, String message) {
		// TODO Auto-generated method stub

		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
		dialog.setContentView(R.layout.popup_alert);
		

		// set the custom dialog components - text, image and button

		TextView txtOk = (TextView) dialog.findViewById(R.id.txtOk);
		TextView txtTitle = (TextView) dialog.findViewById(R.id.txtAlertTile);
		TextView txtMsg= (TextView) dialog.findViewById(R.id.txtAlertMesssage);

		txtTitle.setText(title);
		txtMsg.setText(message);

		if(title.equalsIgnoreCase(""))
			txtTitle.setVisibility(View.GONE);

		// if button is clicked, close the custom dialog
		txtOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});


		dialog.show();

	}



	public void customDialog(Context activity, String title, String message) {
		// TODO Auto-generated method stub

		final Dialog dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
		dialog.setContentView(R.layout.popup_alert);


		// set the custom dialog components - text, image and button

		TextView txtOk = (TextView) dialog.findViewById(R.id.txtOk);
		TextView txtTitle = (TextView) dialog.findViewById(R.id.txtAlertTile);
		TextView txtMsg= (TextView) dialog.findViewById(R.id.txtAlertMesssage);

		txtTitle.setText(title);
		txtMsg.setText(message);

		if(title.equalsIgnoreCase(""))
			txtTitle.setVisibility(View.GONE);

		// if button is clicked, close the custom dialog
		txtOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});


		dialog.show();

	}
	

}
