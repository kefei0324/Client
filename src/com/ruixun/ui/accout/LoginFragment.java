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
public class LoginFragment extends DialogFragment {

	String name;
	String pwd;

	boolean verifyName(EditText et_name) {
		name = et_name.getText().toString();
		// Pattern pattern =
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[01236789]))\\d{8}$");
		// Matcher matcher = pattern.matcher(name);
		return (!TextUtils
				.isEmpty(name)/* && matcher.matches() && name.length() == 11 */)
				|| (!TextUtils.isEmpty(name) && name.equals("admin"));
	}

	boolean verifyPwd(EditText et_pwd) {
		pwd = et_pwd.getText().toString();
		return !TextUtils.isEmpty(pwd) && pwd.length() >= 6;
	}

	public interface LoginDialogListener {
		void loginComplete(String name, String pwd);

		void showMsg(String string);

		void forgetPwd();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_home, null);
		final EditText et_name = (EditText) view.findViewById(R.id.et_name);
		final EditText et_pwd = (EditText) view.findViewById(R.id.et_pwd);
		checkLocal(et_name, et_pwd);
		builder.setView(view).setPositiveButton(R.string.login_button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				LoginDialogListener listener = (LoginDialogListener) getActivity();
				if (verifyName(et_name) && verifyPwd(et_pwd)) {
					listener.loginComplete(name, pwd);
				} else {
					listener.showMsg("请检查输入");
				}
			}
		}).setNegativeButton(R.string.cancle_base, null).setNeutralButton(R.string.forget_pwd,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						LoginDialogListener listener = (LoginDialogListener) getActivity();
						listener.forgetPwd();
					}
				});
		return builder.create();
	}

	private void checkLocal(EditText et_name, EditText et_pwd) {
		et_name.setText(getArguments().getString("name", ""));
		et_pwd.setText(getArguments().getString("pwd", ""));
	}

}
