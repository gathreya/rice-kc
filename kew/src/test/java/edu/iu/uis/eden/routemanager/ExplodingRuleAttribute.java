/*
 * Copyright 2005-2007 The Kuali Foundation.
 * 
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
package edu.iu.uis.eden.routemanager;

import java.util.List;
import java.util.Map;

import org.kuali.rice.kew.plugin.attributes.WorkflowAttribute;
import org.kuali.rice.kew.routeheader.DocumentContent;


public class ExplodingRuleAttribute implements WorkflowAttribute {
    
    public static boolean dontExplode = false;

    public boolean isMatch(DocumentContent docContent, List ruleExtensions) {
        if (dontExplode) {
            return true;
        }
        throw new RuntimeException("I'm the exploder");
    }

    public List getRuleRows() {
        return null;
    }

    public List getRoutingDataRows() {
        return null;
    }

    public String getDocContent() {
        return null;
    }

    public List getRuleExtensionValues() {
        return null;
    }

    public List validateRoutingData(Map paramMap) {
        return null;
    }

    public List validateRuleData(Map paramMap) {
        return null;
    }

    public void setRequired(boolean required) {
    }

    public boolean isRequired() {
        return false;
    }
}