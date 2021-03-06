/**
 * Copyright 2005-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.ksb.messaging.remotedservices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used for services deployed in the test harness and client applications to call.
 * This is useful when services are deployed in the test harness and therefore do not 
 * get called remotely and aren't recorded in the bam.
 * 
 * Holds a single hashmap that can hold whatever is needed for confirmation of call for 
 * testing. This class is special in that it is excluded from the shadowing so that the KSB test clients and the
 * main test harness classloader can pass values back and forth. Therefore, this class only references classes that are
 * part of the JVM and won't get shadowed by the {@link org.kuali.rice.core.api.util.ShadowingInstrumentableClassLoader}.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
public class ServiceCallInformationHolder {

	public static Map<String, String> values = new HashMap<String, String>();
    public static Map<String, List<String>> multiValues = null;
    public static Map<String, Boolean> flags = new HashMap<String, Boolean>();

}
