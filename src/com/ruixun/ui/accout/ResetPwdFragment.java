package com.ruixun.ui.accout;

import com.ruixun.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ResetPwdFragment extends DialogFragment {
	String name;
	String pwd_old;
	String pwd_new;

	boolean verifyName(EditText et_name) {
		name = et_name.getText().toString();
		return (!TextUtils
				.isEmpty(name)/* && matcher.matches() && name.length() == 11 */)
				|| (!TextUtils.isEmpty(name) && name.equals("admin"));
	}

	boolean verifyPwdOld(EditText et_pwd) {
		pwd_old = et_pwd.getText().toString();
		return !TextUtils.isEmpty(pwd_old) && pwd_old.length() >= 6;
	}

	boolean verifyPwdNew(EditText et_pwd) {
		pwd_new = et_pwd.getText().toString();
		return !TextUtils.isEmpty(pwd_new) && pwd_new.length() >= 6;
	}

	public interface ResetDialogListener {
		void resetComplete(String name, String pwd_old, String pwd_new);

		void showMsg(String string);

		void callService();
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_resetpwd, null);
		// int deviceid = getArguments().getInt("deviceid");
		final EditText et_name = (EditText) view.findViewById(R.id.et_name);
		// if (deviceid == Constants.ADMIN_DEVICE_ID) {
		// et_name.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		// }
		final EditText et_pwd_old = (EditText) view.findViewById(R.id.et_pwd_old);
		final EditText et_pwd_new = (EditText) view.findViewById(R.id.et_pwd_new);
		// checkLocal(et_name, et_pwd);
		builder.setView(view).setPositiveButton(R.string.reset_pwd_button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				ResetDialogListener listener = (ResetDialogListener) getActivity();
				if (verifyName(et_name) && verifyPwdOld(et_pwd_old) && verifyPwdNew(et_pwd_new)) {
					// listener.loginComplete(name, pwd);
					listener.resetComplete(name, pwd_old, pwd_new);
				} else {
					listener.showMsg("请检查输入");
				}
			}
		}).setNegativeButton(R.string.reset_forget_button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				ResetDialogListener listener = (ResetDialogListener) getActivity();
				// if (verifyName(et_name) && verifyPwdOld(et_pwd_old) &&
				// verifyPwdNew(et_pwd_new)) {
				// // listener.loginComplete(name, pwd);
				// listener.resetComplete(name, pwd_old, pwd_new);
				// } else {
				// listener.showMsg("请检查输入");
				// }
				listener.callService();
			}
		})/*
			 * .setNeutralButton(R.string.forget_pwd, new
			 * DialogInterface.OnClickListener() {
			 * 
			 * @Override public void onClick(DialogInterface dialog, int which)
			 * { LoginDialogListener listener = (LoginDialogListener)
			 * getActivity(); // if (verifyName(et_name) && verifyPwd(et_pwd)) {
			 * listener.forgetPwd(); // } else { // listener.showMsg("请检查输入");
			 * // } } })
			 */;
		return builder.create();
	}
	// private void checkLocal(EditText et_name, EditText et_pwd) {
	// et_name.setText(getArguments().getString("name", ""));
	// et_pwd.setText(getArguments().getString("pwd", ""));
	// }
}
