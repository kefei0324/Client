package com.ruixun.ui.dialog;

import com.ruixun.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class TipDialog extends DialogFragment implements Runnable {

	public interface DialogListener {
		void confirm(boolean confirm);

		void timeOut();
	}

	private Handler handler;

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_dialog, null);
		final TextView tv_dialog = (TextView) view.findViewById(R.id.tv_dialog);
		String tip = getArguments().getString("tip", getActivity().getString(R.string.confrim_base_tip));
		tv_dialog.setText(tip);
		builder.setView(view).setPositiveButton(R.string.confrim_base, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				DialogListener listener = (DialogListener) getActivity();
				handler.removeCallbacks(TipDialog.this);
				listener.confirm(true);
			}
		}).setNegativeButton(R.string.cancle_base, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				DialogListener listener = (DialogListener) getActivity();
				handler.removeCallbacks(TipDialog.this);
				listener.confirm(false);
			}
		});
		handler = new Handler();
		handler.postDelayed(this, 10000);
		return builder.create();
	}

	@Override
	public void run() {
		if (null != getActivity()) {
			((DialogListener) (TipDialog.this.getActivity())).timeOut();
			handler.removeCallbacks(this);
			TipDialog.this.dismiss();
		}else{
			System.out.println("error");
		}
	}

}
