package com.ruixun.engine;
import android.net.wifi.WifiConfiguration.Protocol;

/**
 * 
 * @author qinchi
 *
 */
public interface OnRestListener {

	boolean prepareRest();

	void onRest(Protocol protocol);

	void onRestFailed();

}
