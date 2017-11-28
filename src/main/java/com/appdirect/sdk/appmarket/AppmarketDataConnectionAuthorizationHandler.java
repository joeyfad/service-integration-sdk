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

package com.appdirect.sdk.appmarket;

import com.appdirect.sdk.appmarket.dataconnections.DataConnectionsAPIResult;
import com.appdirect.sdk.appmarket.dataconnections.DataConnectionsHandlingContext;
import com.appdirect.sdk.appmarket.events.AppmarketEventClient;

/**
 * This is the interface you need to implement to support handling appmarket data connections
 * authorizations in your connector.
 *
 */
@FunctionalInterface
public interface AppmarketDataConnectionAuthorizationHandler {

	/**
	 *  TODO: is async needed for dataConnections?
	 * For async events (everything but <code>SUBSCRIPTION_NOTICE</code> events),
	 * returning a <code>null</code> result will not resolve the event on the appmarket's side.
	 * You will have to manually resolve the event using {@link AppmarketEventClient#resolve} at a later point in time.
	 * @param context the context
	 * @return the result that will be sent to the appmarket to resolve the event, or <code>null</code> if you the event is to be resolved later.
	 */
	DataConnectionsAPIResult handleAuthorization(DataConnectionsHandlingContext context);
}
