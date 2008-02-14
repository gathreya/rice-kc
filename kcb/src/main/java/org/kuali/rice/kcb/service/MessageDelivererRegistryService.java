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

import org.kuali.rice.kcb.bo.MessageDelivery;
import org.kuali.rice.kcb.deliverer.MessageDeliverer;

/**
 * This class is responsible for providing services for Message Deliverers (delivery types)
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public interface MessageDelivererRegistryService {
    /**
     * This service method is responsible for retrieving all MessageDeliverer Types.  
     * This service is to be run periodically in a separate thread, as a daemon process.
     * @return ArrayList of Deliverer Classes
     */
    public Collection<MessageDeliverer> getAllDelivererTypes();

    /**
     * This method returns the associated deliverer class instance for the given MessageDelivery instance.
     * @param messageDelivery
     * @return MessageDeliverer or null if not found
     */
    public MessageDeliverer getDeliverer(MessageDelivery messageDelivery);
    
    /**
     * This method returns the associated deliverer class instance for the given deliverer name.
     * @param messageDelivererName
     * @return MessageDeliverer or null if not found
     */
    public MessageDeliverer getDelivererByName(String messageDelivererName);
}
