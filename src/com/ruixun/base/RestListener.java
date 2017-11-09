package com.ruixun.base;

public interface RestListener {

	void onRest(BaseProtocol protocol);

	void restFail(String string);

}
