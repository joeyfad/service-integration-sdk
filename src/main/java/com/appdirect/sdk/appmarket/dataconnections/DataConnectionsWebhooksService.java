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

import static com.appdirect.sdk.appmarket.events.ErrorCode.UNKNOWN_ERROR;
import static java.lang.String.format;

import lombok.extern.slf4j.Slf4j;

import com.appdirect.sdk.exception.DeveloperServiceException;

@Slf4j
public class DataConnectionsWebhooksService {

	private final DataConnectionsWebhooksDispatcher dispatcher;

	public DataConnectionsWebhooksService(DataConnectionsWebhooksDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	/**
	 * Process webhook connections data from the AppMarket or Application
	 * @param context
	 * @return
	 */
	DataConnectionsAPIResult processData(DataConnectionsHandlingContext context) {
		log.info("processing connections data for context={}", context);
		try {
			return dispatcher.dispatchAndHandle(context);
		} catch (DeveloperServiceException e) {
			log.error("Service returned an error for context={}, result={}", context, e.getResult());
			throw e;
		} catch (RuntimeException e) {
			log.error("Exception while attempting to process connections data. context={}", context, e);
			throw new DeveloperServiceException(UNKNOWN_ERROR, format("Failed to process connections data. context=%s | exception=%s", context, e.getMessage()));
		}
	}

}