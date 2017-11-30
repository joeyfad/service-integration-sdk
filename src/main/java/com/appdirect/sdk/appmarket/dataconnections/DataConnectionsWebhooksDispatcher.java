/*
 * Copyright 2017 AppDirect, Inc. and/or its affiliates
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appdirect.sdk.appmarket.dataconnections;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Dispatches incoming data to the appropriate handler.
 */
public class DataConnectionsWebhooksDispatcher {
	private final SDKDataConnectionsWebhooksHandler webhooksHandler;
	private final SDKDataConnectionsWebhooksHandler unknownHandler;

	DataConnectionsWebhooksDispatcher(
			//AsyncEventHandler asyncHandler
			SDKDataConnectionsWebhooksHandler webhooksHandler,
			SDKDataConnectionsWebhooksHandler unknownHandler) {
		//this.asyncHandler = asyncHandler;
		this.webhooksHandler = webhooksHandler;
		this.unknownHandler = unknownHandler;
	}

	/**
	 * Resolves the appropriate SDK-internal handler that corresponds to the incoming data and
	 * forwards the data to it
	 *
	 * @param context
	 *
	 * @return
	 */
	DataConnectionsAPIResult dispatchAndHandle(DataConnectionsHandlingContext context) {

		//SDKDataConnectionsAuthorizationHandler handler = getHandlerFor(context);

//		if (events.eventShouldBeHandledAsync(rawEvent)) {
//			return asyncHandler.handle(eventHandler, rawEvent, eventContext);
//		} else {
//			return eventHandler.handle(rawEvent, eventContext);
//		}
		// no support for asyn, so just handle it one way
		return getHandlerFor(context).handleWebhooks(context);

	}

	private SDKDataConnectionsWebhooksHandler getHandlerFor(final DataConnectionsHandlingContext context) {

		DataConnectionType currentType = DataConnectionType.WEBHOOK;
		// TODO: better way of idenfiying webhook? webhooks are POST, authorize are GET
		if (!context.getRequest().getMethod().equalsIgnoreCase("POST"))
		{
			currentType = DataConnectionType.UNKNOWN;
		}

		Map<DataConnectionType, Supplier<SDKDataConnectionsWebhooksHandler>> contextToHandlers = new EnumMap<>(DataConnectionType.class);
		contextToHandlers.put(DataConnectionType.WEBHOOK, () -> webhooksHandler);
		contextToHandlers.put(DataConnectionType.UNKNOWN, () -> unknownHandler);

		return contextToHandlers.getOrDefault(currentType, () -> unknownHandler).get();
	}

}
