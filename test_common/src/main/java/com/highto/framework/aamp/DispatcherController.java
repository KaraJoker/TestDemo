package com.highto.framework.aamp;

import java.util.ArrayList;
import java.util.List;

public class DispatcherController<T> {
	private List<Controller<T>> controllers;

	public DispatcherController() {
		controllers = new ArrayList<>();
	}

	public void dispatch(SocketMsgDecodeResult<T> result, Response response) {
		boolean dealed = false;
		for (Controller controller : controllers) {
			if (ProtocolType.request.value() == result.getType()) {
				if (controller instanceof RequestController) {
					dealed = ((RequestController) controller).dispatchRequest(result.getMsg(), response);
				}
			} else if (ProtocolType.notify.value() == result.getType()) {
				if (controller instanceof NotifyController) {
					dealed = ((NotifyController) controller).dispatchNotify(result.getMsg());
				}
			} else {

			}

			if (dealed) {
				break;
			}
		}

	}

	public void addController(Controller<T> controller) {
		controllers.add(controller);
	}

	public List<Controller<T>> getControllers() {
		return controllers;
	}
}
