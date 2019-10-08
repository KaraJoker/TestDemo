package com.highto.framework.web;

import java.util.ArrayList;
import java.util.List;

public class Dispatcher {
	private List<DispatchMessageController> controllers;

	public Dispatcher() {
		controllers = new ArrayList<>();
	}

	public void dispatchMessage(String message, Response response) {
		for (DispatchMessageController controller : controllers) {
			boolean dispatchOk = controller.doDispatch(message, response);
			if (dispatchOk) {
				break;
			}
		}
	}

	public void addController(DispatchMessageController controller) {
		controllers.add(controller);
	}
}
