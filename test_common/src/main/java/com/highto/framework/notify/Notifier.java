package com.highto.framework.notify;

import java.io.IOException;

public interface Notifier {

	void notify(String msg);

	void notify(byte[] msg);

	boolean isConnected();

	void close() throws IOException;
}
