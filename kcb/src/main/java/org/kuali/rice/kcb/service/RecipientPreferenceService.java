/*
 * Copyright 2007 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kcb.service;

import java.util.Collection;
import java.util.HashMap;

import org.kuali.rice.kcb.bo.RecipientPreference;
import org.kuali.rice.kcb.deliverer.MessageDeliverer;
import org.kuali.rice.kcb.exception.ErrorList;

/**
 * Service for accessing user preferences in the KEN system.{@link UserPreference}
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public interface RecipientPreferenceService {
    /**
     * This method will get all  user recipient preference from the system.
     * @param recipientId
     */
    public HashMap<String, String> getRecipientPreferences(String recipientId);

    /**
     * This method will save a user recipient preference in the system.
     * @param userid
     * @param prefs a hashmap of key/values
     * @param deliveryTypeName name of deliverer
     */
    public void saveRecipientPreferences(String userid, HashMap<String, String> prefs, MessageDeliverer deliverer) throws ErrorList;
    
    /**
     * This method will get a specific user recipient preference from the system.
     * @param recipientId
     * @param key
     */
    public RecipientPreference getRecipientPreference(String recipientId, String key);

    /**
     * This method will save a specific user recipient preference in the system.
     * @param pref the preference
     */
    public void saveRecipientPreference(RecipientPreference pref);

    /**
     * This method will retrieve all of the message deliverer configurations for a given user, associated with a 
     * particular channel.
     * @param recipientId
     * @param channel
     */
    public Collection<String> getDeliverersForRecipientAndChannel(String recipientId, String channel);
    
    /**
     * This method will retrieve all of the message deliverer configurations for a given user 
     * @param recipientId
     */
    public Collection<String> getDeliverersForRecipient(String recipientId);
}