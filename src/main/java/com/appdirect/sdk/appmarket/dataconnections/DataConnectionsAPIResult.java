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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.appdirect.sdk.appmarket.events.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The payload that is sent back to the AppMarket in response to a data connection event.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class  DataConnectionsAPIResult {
	private boolean success;
	private ErrorCode errorCode;
	private String message;
	private String accountIdentifier;
	private String userIdentifier;
	@JsonIgnore
	private int statusCodeReturnedToAppmarket;
	// allows the overriding of authorization response page
	private DataConnectionsAuthorizationResponse authorizationResponse;

	public DataConnectionsAPIResult(ErrorCode errorCode, String message) {
		this(false, message);
		this.errorCode = errorCode;
	}

	@JsonCreator
	public DataConnectionsAPIResult(@JsonProperty("success") boolean success, @JsonProperty("message") String message) {
		this.success = success;
		this.message = message;
	}

	public static DataConnectionsAPIResult success(String message) {
		DataConnectionsAPIResult result = new DataConnectionsAPIResult(true, message);
		result.setStatusCodeReturnedToAppmarket(200);
		return result;
	}

	public static DataConnectionsAPIResult success(String message, DataConnectionsAuthorizationResponse authorizationResponse) {
		DataConnectionsAPIResult result = success(message);
		result.setAuthorizationResponse(authorizationResponse);
		return result;
	}

	public static DataConnectionsAPIResult asyncEventResult(String message) {
		DataConnectionsAPIResult result = new DataConnectionsAPIResult(true, message);
		result.setStatusCodeReturnedToAppmarket(202);
		return result;
	}

	/**
	 * Creates a failed result with the given error code and message.
	 * Note: HTTP OK (200) will be returned to the appmarket; all responses
	 * going to the appmarket need to have a 200 status, whether they are successful or not.
	 *
	 * @param errorCode the code of the error
	 * @param message   human-readable error message that explains the issue.
	 * @return the failed result object
	 */
	public static DataConnectionsAPIResult failure(ErrorCode errorCode, String message) {
		DataConnectionsAPIResult result = new DataConnectionsAPIResult(errorCode, message);
		result.setStatusCodeReturnedToAppmarket(200);
		return result;
	}

	public static DataConnectionsAPIResult failure(ErrorCode errorCode, String message, DataConnectionsAuthorizationResponse authorizationResponse) {
		DataConnectionsAPIResult result = failure(errorCode, message);
		result.setAuthorizationResponse(authorizationResponse);
		return result;
	}
}
