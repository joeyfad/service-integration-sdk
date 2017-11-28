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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DataConnectionsController {

	private final DataConnectionsAuthorizationService dataConnectionsAuthorizationService;
	private final DataConnectionsWebhooksService dataConnectionsWebhooksService;
	private String payload;

	public DataConnectionsController(DataConnectionsAuthorizationService dataConnectionsAuthorizationService,
																	 DataConnectionsWebhooksService dataConnectionsWebhooksService) {
		this.dataConnectionsAuthorizationService = dataConnectionsAuthorizationService;
		this.dataConnectionsWebhooksService = dataConnectionsWebhooksService;
	}

	/**
	 * Defines the connector endpoint to which AppDirect Integrations (AI / AW) connect authorization should be sent.
	 *
	 * @param request the http request
	 * @param source  the source from which the connect authorization request is coming from (i.e. AppWise or AppInsights)
	 *
	 * @return the HTTP response to return to the AppMarket.
	 */
	@RequestMapping(method = GET, value = "/authorization", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<DataConnectionsAPIResult> authorization(HttpServletRequest request, HttpServletResponse response, @RequestParam("source") String source) {
		// TODO: consider adding all required parameters i.e. appId, userId, companyId, baseUrl, along with source
		log.info("authorization: source={} ", source);

		DataConnectionsAPIResult result = dataConnectionsAuthorizationService.processData(executionContext(request, response));

		log.info("authorization: apiResult={}", result);
		return new ResponseEntity<>(result, httpStatusOf(result));
	}

	/**
	 * Defines the connector endpoint to which AppDirect Integrations webhook (AI / AW) events should be sent.
	 *
	 * @param request  the http request
	 * @param response the http response
	 *
	 * @return the HTTP response to return to the AppMarket.
	 */
	@RequestMapping(method = POST, value = "/webhooks", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<DataConnectionsAPIResult> webhooks(HttpServletRequest request, HttpServletResponse response, @RequestBody String payload) {
		this.payload = payload;

		log.info("webhook: payload={} ", payload);

		DataConnectionsAPIResult result = dataConnectionsWebhooksService.processData(executionContext(request, response, payload));

		log.info("webbhook: result={}", result);
		return new ResponseEntity<>(result, httpStatusOf(result));
	}

	private DataConnectionsHandlingContext executionContext(HttpServletRequest request, HttpServletResponse response) {
		return new DataConnectionsHandlingContext(request, response);
	}

	private DataConnectionsHandlingContext executionContext(HttpServletRequest request, HttpServletResponse response, String payload) {
		return new DataConnectionsHandlingContext(request, response, payload);
	}

	private HttpStatus httpStatusOf(DataConnectionsAPIResult result) {
		return HttpStatus.valueOf(result.getStatusCodeReturnedToAppmarket());
	}

}
