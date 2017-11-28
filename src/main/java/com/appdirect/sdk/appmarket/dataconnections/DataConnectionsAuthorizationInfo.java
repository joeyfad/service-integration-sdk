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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * Representation of the the params and values required below are per Dave's spec outlined in the
 * "User Authorization Configurator API Endpoint" section of this page:
 * https://appdirect.jira.com/wiki/spaces/EN/pages/220995076/Outbound+Connect+Flow
 *
 * SDK internal, a user of the SDK should never interact with those directly.
 */
@Getter
@ToString
@AllArgsConstructor
@Builder
public class DataConnectionsAuthorizationInfo {

	private String appId;
	private String userId;
	private String companyId;
	private String baseUrl;
	private String source; // Either "visualization" (for AppInsights) or "content" (for AppWise)
	// These names match the API URL paths the ISVs need to use for the First Parties.

}
