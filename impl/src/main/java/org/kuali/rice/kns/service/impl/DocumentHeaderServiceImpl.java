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
package org.kuali.rice.kns.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.KNSServiceLocator;
import org.kuali.rice.kns.bo.DocumentHeader;
import org.kuali.rice.kns.dao.DocumentHeaderDao;
import org.kuali.rice.kns.service.DocumentHeaderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a description of what this class does - delyea don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Transactional
public class DocumentHeaderServiceImpl implements DocumentHeaderService {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DocumentHeaderServiceImpl.class);
    private DocumentHeaderDao documentHeaderDao;

    /**
     * @see org.kuali.rice.kns.service.DocumentHeaderService#getDocumentHeaderBaseClass()
     */
    public Class getDocumentHeaderBaseClass() {
        Class documentHeaderClass = documentHeaderDao.getDocumentHeaderBaseClass();
        if ( (documentHeaderClass == null) || (!DocumentHeader.class.isAssignableFrom(documentHeaderClass)) ) {
            throw new RuntimeException("invalid document header base class '" + documentHeaderClass + "' returned by dao '" + documentHeaderDao.getClass().getName() + "'");
        }
        return documentHeaderClass;
    }

    /**
     * @see org.kuali.rice.kns.service.DocumentHeaderService#getDocumentHeaderById(java.lang.String)
     */
    public DocumentHeader getDocumentHeaderById(String documentHeaderId) {
        if (StringUtils.isBlank(documentHeaderId)) {
            throw new IllegalArgumentException("document header id given is blank");
        }
        return documentHeaderDao.getByDocumentHeaderId(documentHeaderId);
    }

    /**
     * @see org.kuali.rice.kns.service.DocumentHeaderService#saveDocumentHeader(org.kuali.rice.kns.bo.DocumentHeader)
     */
    public void saveDocumentHeader(DocumentHeader documentHeader) {
        KNSServiceLocator.getBusinessObjectService().save(documentHeader);
    }
    
    /**
     * @see org.kuali.rice.kns.service.DocumentHeaderService#deleteDocumentHeader(org.kuali.rice.kns.bo.DocumentHeader)
     */
    public void deleteDocumentHeader(DocumentHeader documentHeader) {
        KNSServiceLocator.getBusinessObjectService().delete(documentHeader);
    }

    /**
     * dao injected by spring
     * 
     * @param documentHeaderDao
     */
    public void setDocumentHeaderDao(DocumentHeaderDao documentHeaderDao) {
        this.documentHeaderDao = documentHeaderDao;
    }

}
