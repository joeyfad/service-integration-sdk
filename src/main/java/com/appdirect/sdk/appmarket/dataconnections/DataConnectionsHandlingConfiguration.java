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

import static com.appdirect.sdk.appmarket.events.ErrorCode.CONFIGURATION_ERROR;
import static java.lang.String.format;
import static java.util.concurrent.Executors.newWorkStealingPool;

import java.util.concurrent.ExecutorService;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.appmarket.AppmarketDataConnectionAuthorizationHandler;
import com.appdirect.sdk.appmarket.AppmarketDataConnectionWebhooksHandler;
import com.appdirect.sdk.executor.MdcExecutor;

@Configuration
@RequiredArgsConstructor
public class DataConnectionsHandlingConfiguration {

	private final AppmarketDataConnectionAuthorizationHandler authorizationHandler;
	private final AppmarketDataConnectionWebhooksHandler webhooksHandler;

	@Bean
	SDKDataConnectionsAuthorizationHandler unknownHandler() {
		return (context) -> new DataConnectionsAPIResult(CONFIGURATION_ERROR, format("Unsupported data connections authorization context %s", context));
	}

	@Bean(destroyMethod = "shutdown")
	public ExecutorService defaultExecutorService() {
		return new MdcExecutor(newWorkStealingPool());
	}

	@Bean
	public DataConnectionsAuthorizationDispatcher dataConnectionDispatcher() {
		return new DataConnectionsAuthorizationDispatcher(
				new AuthorizationHandleWrapper(authorizationHandler),
				unknownHandler());
	}

	@Bean
	SDKDataConnectionsWebhooksHandler unknownWebhooksHandler() {
		return (context) -> new DataConnectionsAPIResult(CONFIGURATION_ERROR, format("Unsupported data connections webhooks context %s", context));
	}

	@Bean
	public DataConnectionsWebhooksDispatcher dataConnectionWebhooksDispatcher() {
		return new DataConnectionsWebhooksDispatcher(
				new WebhooksHandleWrapper(webhooksHandler),
				unknownWebhooksHandler());
	}

}
