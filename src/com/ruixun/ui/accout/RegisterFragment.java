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

@SuppressLint("InflateParams")
public class RegisterFragment extends DialogFragment {

	String name;
	String pwd;

	boolean verifyName(EditText et_name) {
		name = et_name.getText().toString();
//		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[01236789]))\\d{8}$");
//		Matcher matcher = pattern.matcher(name);
		return !TextUtils.isEmpty(name) /*&& matcher.matches() && name.length() == 11*/;
	}

	boolean verifyPwd(EditText et_pwd) {
		pwd = et_pwd.getText().toString();
		return !TextUtils.isEmpty(pwd) && pwd.length() >= 6;
	}

	public interface RegisterDialogListener {
		void registerComplete(String name, String pwd);

		void showMsg(String string);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_home, null);
		final EditText et_name = (EditText) view.findViewById(R.id.et_name);
		final EditText et_pwd = (EditText) view.findViewById(R.id.et_pwd);
		builder.setView(view).setPositiveButton(R.string.register_button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				RegisterDialogListener listener = (RegisterDialogListener) getActivity();
				if (verifyName(et_name) && verifyPwd(et_pwd)) {
					listener.registerComplete(name, pwd);
				} else {
					listener.showMsg("请检查输入");
				}
			}
		}).setNegativeButton(R.string.cancle_base, null);
		return builder.create();
	}

}
