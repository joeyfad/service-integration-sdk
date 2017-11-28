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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class DataConnectionsHandlingContext {

	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final String payload;

	DataConnectionsHandlingContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.payload = null;
	}

	DataConnectionsHandlingContext(HttpServletRequest request, HttpServletResponse response, String payload) {
		this.request = request;
		this.response = response;
		this.payload = payload;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getPayload() { return payload; }

}
