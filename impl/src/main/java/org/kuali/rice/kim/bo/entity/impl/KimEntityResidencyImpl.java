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
package org.kuali.rice.kim.bo.entity.impl;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.kim.bo.entity.KimEntityResidency;

/**
 * This class is used to store residency information about an entity. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Entity
@Table(name = "KRIM_ENTITY_RESIDENCY_T")
public class KimEntityResidencyImpl extends KimEntityDataBase implements KimEntityResidency {

	private static final long serialVersionUID = 6577601907062646925L;

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "ENTITY_ID")
	private String entityId;
	
	@Column(name = "DETERMINATION_METHOD")
	private String determinationMethod;
	
	@Column(name = "IN_STATE")
	private String inStateFlag;
	
	/**
	 * @param inStateFlag the inStateFlag to set
	 */
	public void setInStateFlag(String inStateFlag) {
		this.inStateFlag = inStateFlag;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * @param determinationMethod the determinationMethod to set
	 */
	public void setDeterminationMethod(String determinationMethod) {
		this.determinationMethod = determinationMethod;
	}

	/**
	 * @see org.kuali.rice.kim.bo.entity.KimEntityResidency#getDeterminationMethod()
	 */
	public String getDeterminationMethod() {
		return determinationMethod;
	}

	/**
	 * @see org.kuali.rice.kim.bo.entity.KimEntityResidency#getEntityId()
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * @see org.kuali.rice.kim.bo.entity.KimEntityResidency#getId()
	 */
	public String getId() {
		return id;
	}

	/**
	 * @see org.kuali.rice.kim.bo.entity.KimEntityResidency#getInState()
	 */
	public String getInState() {
		return inStateFlag;
	}

	/**
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, String> m = new LinkedHashMap<String, String>();
		m.put("id", id);
		m.put("entityId", entityId);
		m.put("determinationMethod", determinationMethod);
		m.put("inStateFlag", inStateFlag);
		return m;
	}
}
