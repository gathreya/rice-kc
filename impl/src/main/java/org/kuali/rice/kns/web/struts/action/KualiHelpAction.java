/*
 * Copyright 2005-2007 The Kuali Foundation.
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
package org.kuali.rice.kns.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kns.KNSServiceLocator;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.BusinessObjectEntry;
import org.kuali.rice.kns.datadictionary.DataDictionary;
import org.kuali.rice.kns.datadictionary.DataDictionaryEntry;
import org.kuali.rice.kns.datadictionary.DocumentEntry;
import org.kuali.rice.kns.datadictionary.HeaderNavigation;
import org.kuali.rice.kns.datadictionary.HelpDefinition;
import org.kuali.rice.kns.datadictionary.MaintainableFieldDefinition;
import org.kuali.rice.kns.service.DataDictionaryService;
import org.kuali.rice.kns.service.KualiConfigurationService;
import org.kuali.rice.kns.service.MaintenanceDocumentDictionaryService;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.RiceKeyConstants;
import org.kuali.rice.kns.web.struts.form.KualiHelpForm;

/**
 * This class handles requests for help text.
 * 
 * 
 */
public class KualiHelpAction extends KualiAction {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KualiHelpAction.class);

    private static final String VALIDATION_PATTERN_STRING = "ValidationPattern";
    private static final String NO = "No";
    private static final String YES = "Yes";
    
    private static DataDictionaryService dataDictionaryService;
    private static KualiConfigurationService kualiConfigurationService;
    private static MaintenanceDocumentDictionaryService maintenanceDocumentDictionaryService;

    private DataDictionaryService getDataDictionaryService() {
        if ( dataDictionaryService == null ) {
            dataDictionaryService = KNSServiceLocator.getDataDictionaryService();
        }
        return dataDictionaryService;
    }
    private KualiConfigurationService getConfigurationService() {
        if ( kualiConfigurationService == null ) {
            kualiConfigurationService = KNSServiceLocator.getKualiConfigurationService();
        }
        return kualiConfigurationService;
    }

    private MaintenanceDocumentDictionaryService getMaintenanceDocumentDictionaryService() {
        if ( maintenanceDocumentDictionaryService == null ) {
            maintenanceDocumentDictionaryService = KNSServiceLocator.getMaintenanceDocumentDictionaryService();
        }
        return maintenanceDocumentDictionaryService;
    }
    
    /**
     * Convenience method for accessing <code>{@link DataDictionaryEntry}</code> for the given business object
     * 
     * @param businessObjectClassName
     * @return DataDictionaryEntry
     */
    private DataDictionaryEntry getDataDictionaryEntry(String businessObjectClassName) {
        return getDataDictionaryService().getDataDictionary().getDictionaryObjectEntry(businessObjectClassName);
    }

    /**
     * Convenience method for accessing the <code>{@link AttributeDefinition}</code> for a specific business object attribute
     * defined in the DataDictionary.
     * 
     * @param businessObjectClassName
     * @param attributeName
     * @return AttributeDefinition
     */
    private AttributeDefinition getAttributeDefinition(String businessObjectClassName, String attributeName) throws ClassNotFoundException {
        AttributeDefinition retval = null;

        if (getDataDictionaryEntry(businessObjectClassName) != null) {
            retval = getDataDictionaryEntry(businessObjectClassName).getAttributeDefinition(attributeName);
        }
        return retval;
    }

    /**
     * @param a <code>{@link AttributeDefinition}</code>
     * @return String
     */
    private String getAttributeMaxLength(AttributeDefinition attribute) throws Exception {
        return attribute.getMaxLength().toString();
    }

    /**
     * @param a <code>{@link AttributeDefinition}</code>
     * @return String
     */
    private String getAttributeValidationPatternName(AttributeDefinition attribute) throws Exception {
        String retval = new String();
        if (attribute.getValidationPattern() != null) {
            retval = attribute.getValidationPattern().getClass().getName();
        }

        if (retval.indexOf(".") > 0) {
            retval = retval.substring(retval.lastIndexOf(".") + 1);
        }
        if (retval.endsWith(VALIDATION_PATTERN_STRING)) {
            retval = retval.substring(0, retval.lastIndexOf(VALIDATION_PATTERN_STRING));
        }

        return retval;
    }

    /**
     * Retrieves help information from the data dictionary for the business object attribute.
     * 
     * @return ActionForward
     */
    public ActionForward getAttributeHelpText(ActionMapping mapping, KualiHelpForm helpForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

        AttributeDefinition attribute;

        if (StringUtils.isBlank(helpForm.getBusinessObjectClassName()) || StringUtils.isBlank(helpForm.getAttributeName())) {
            throw new RuntimeException("Business object and attribute name not specified.");
        }
        attribute = getAttributeDefinition(helpForm.getBusinessObjectClassName(), helpForm.getAttributeName());

        if ( LOG.isDebugEnabled() ) {
            LOG.debug( "Request for help on: " + helpForm.getBusinessObjectClassName() + " -- " + helpForm.getAttributeName() );
            LOG.debug( "  attribute: " + attribute );
        }
                
        if (attribute == null || StringUtils.isBlank(attribute.getSummary())) {
            helpForm.setResourceKey(RiceKeyConstants.MESSAGE_NO_HELP_TEXT);
            return getResourceHelpText(mapping, helpForm, request, response);
        }

        boolean required = attribute.isRequired().booleanValue();
        // KULRNE-4392 - pull the required attribute on BO maintenance documents from the document def rather than the BO
        try {
            Class boClass = Class.forName( helpForm.getBusinessObjectClassName() );
            String docTypeName = getMaintenanceDocumentDictionaryService().getDocumentTypeName( boClass );
            if (StringUtils.isNotBlank(docTypeName)) {
                // maybe it's not a maint doc
                MaintainableFieldDefinition field = getMaintenanceDocumentDictionaryService().getMaintainableField( docTypeName, helpForm.getAttributeName() );
                if ( field != null ) {
                    required = field.isRequired();
                }
            }
            else {
                if (log.isInfoEnabled()) {
                    log.info("BO class " + boClass.getName() + " does not have a maint doc definition.  Defaulting to using DD for definition");
                }
            }
        } catch ( ClassNotFoundException ex ) {
            // do nothing
            LOG.warn( "Unable to obtain maintainable field for BO property.", ex );
        }
        
        helpForm.setHelpLabel(attribute.getLabel());
        helpForm.setHelpSummary(attribute.getSummary());
        helpForm.setHelpDescription(attribute.getDescription());
        helpForm.setHelpRequired(required?YES:NO);
        helpForm.setHelpMaxLength(getAttributeMaxLength(attribute));
        helpForm.setValidationPatternName(getAttributeValidationPatternName(attribute));

        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    /**
     * Retrieves help information from the data dictionary for the business object attribute.
     * 
     * @return ActionForward
     */
    public ActionForward getAttributeHelpText(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getAttributeHelpText(mapping, (KualiHelpForm) form, request, response);
    }

    /**
     * Retrieves help information from the data dictionary for the document type.
     */
    public ActionForward getDocumentHelpText(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KualiHelpForm helpForm = (KualiHelpForm) form;

        String documentTypeName = helpForm.getDocumentTypeName();

        if (StringUtils.isBlank(documentTypeName)) {
            throw new RuntimeException("Document type name not specified.");
        }

        DataDictionary dataDictionary = getDataDictionaryService().getDataDictionary();
        DocumentEntry entry = dataDictionary.getDocumentEntry(documentTypeName);

        String label = "";
        String summary = "";
        String description = "";
        HelpDefinition helpDefinition = null;
        String apcHelpUrl = null;
        if (entry != null) {
            label = entry.getLabel();
            summary = entry.getSummary();
            description = entry.getDescription();
            helpDefinition = entry.getHelpDefinition();

            if (null != helpDefinition && null != helpDefinition.getParameterNamespace() && null != helpDefinition.getParameterDetailType() && null != helpDefinition.getParameterName()) {
                apcHelpUrl = getHelpUrl(helpDefinition.getParameterNamespace(), helpDefinition.getParameterDetailType(), helpDefinition.getParameterName());
                }
                }
        

        if ( !StringUtils.isBlank(apcHelpUrl) ) {
            response.sendRedirect(apcHelpUrl);
            return null;
        }
        
        helpForm.setHelpLabel(label);
        helpForm.setHelpSummary(summary);
        helpForm.setHelpDescription(description);
        helpForm.setHelpDefinition(helpDefinition);

        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    /**
     * Retrieves help information from the data dictionary for the document type.
     */
    public ActionForward getBusinessObjectHelpText(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KualiHelpForm helpForm = (KualiHelpForm) form;

        String objectClassName = helpForm.getBusinessObjectClassName();

        if (StringUtils.isBlank(objectClassName)) {
            throw new RuntimeException("Document type name not specified.");
        }

        DataDictionary dataDictionary = getDataDictionaryService().getDataDictionary();
        BusinessObjectEntry entry = dataDictionary.getBusinessObjectEntry(objectClassName);

        HelpDefinition helpDefinition = null;
        String apcHelpUrl = null;
        String label = "";
        String objectDescription = "";
        if (entry != null) {
            helpDefinition = entry.getHelpDefinition();
            label = entry.getObjectLabel();
            objectDescription = entry.getObjectDescription();
            if (null != helpDefinition && null != helpDefinition.getParameterNamespace() && null != helpDefinition.getParameterDetailType() && null != helpDefinition.getParameterName()) {
                apcHelpUrl = getHelpUrl(helpDefinition.getParameterNamespace(), helpDefinition.getParameterDetailType(), helpDefinition.getParameterName());
                }
                }

        if ( !StringUtils.isBlank(apcHelpUrl) ) {
            response.sendRedirect(apcHelpUrl);
            return null;
        }
        helpForm.setHelpLabel(label);
        helpForm.setHelpDescription(objectDescription);

        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }
    
    /**
     * Retrieves help information from the data dictionary for the document type.
     */
    public ActionForward getPageHelpText(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KualiHelpForm helpForm = (KualiHelpForm) form;

        String documentTypeName = helpForm.getDocumentTypeName();
        String pageName = helpForm.getPageName();

        if (StringUtils.isBlank(documentTypeName)) {
            throw new RuntimeException("Document type name not specified.");
        }
        
        if (StringUtils.isBlank(pageName)) {
            throw new RuntimeException("Page name not specified.");
        }

        DataDictionary dataDictionary = getDataDictionaryService().getDataDictionary();
        DocumentEntry entry = dataDictionary.getDocumentEntry(documentTypeName);

        String apcHelpUrl = null;
        String label = "";
        String objectDescription = "";
        if (entry != null) {
            for ( HeaderNavigation headerNavigation : entry.getHeaderNavigationList() ) {
                if (headerNavigation.getHeaderTabDisplayName().equals(pageName)) {
                    HelpDefinition helpDefinition = headerNavigation.getHelpDefinition();
                    if (null != helpDefinition && null != helpDefinition.getParameterNamespace() && null != helpDefinition.getParameterDetailType() && null != helpDefinition.getParameterName()) {
                        apcHelpUrl = getHelpUrl(helpDefinition.getParameterNamespace(), helpDefinition.getParameterDetailType(), helpDefinition.getParameterName());
                    }
                }
            }
        }

        if ( !StringUtils.isBlank(apcHelpUrl) ) {
            response.sendRedirect(apcHelpUrl);
            return null;
        }
        helpForm.setHelpLabel(pageName);
        helpForm.setHelpDescription("No help content available.");

        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }
    
    /**
     * Retrieves help content to link to based on security group/parameter
     */
    public ActionForward getStoredHelpUrl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KualiHelpForm helpForm = (KualiHelpForm) form;
        
        String helpParameterNamespace = helpForm.getHelpParameterNamespace();
        String helpParameterDetailType = helpForm.getHelpParameterDetailType();
        String helpParameterName = helpForm.getHelpParameterName();
        
        if (StringUtils.isBlank(helpParameterNamespace)) {
            throw new RuntimeException("Parameter Namespace not specified.");
        }
        
        if (StringUtils.isBlank(helpParameterDetailType)) {
            throw new RuntimeException("Detail Type not specified.");
        }

        if (StringUtils.isBlank(helpParameterName)) {
            throw new RuntimeException("Parameter Name not specified.");
        }
        
        String apcHelpUrl = getHelpUrl(helpParameterNamespace, helpParameterDetailType, helpParameterName);
        
        if ( !StringUtils.isBlank(apcHelpUrl) ) {
            response.sendRedirect(apcHelpUrl);
            return null;
        }
        
        helpForm.setHelpDescription("No help content available.");
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    /**
     * Retrieves help information from resources by key.
     */
    public ActionForward getResourceHelpText(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KualiHelpForm helpForm = (KualiHelpForm) form;

        String resourceKey = helpForm.getResourceKey();
        if (StringUtils.isBlank(resourceKey)) {
            throw new RuntimeException("Help resource key not specified.");
        }

        helpForm.setHelpLabel("");
        helpForm.setHelpSummary("");
        helpForm.setHelpDescription(getConfigurationService().getPropertyString(resourceKey));

        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    private String getHelpUrl(String parameterNamespace, String parameterDetailTypeCode, String parameterName) {
        return getConfigurationService().getPropertyString(KNSConstants.EXTERNALIZABLE_HELP_URL_KEY) + getConfigurationService().getParameterValue(parameterNamespace, parameterDetailTypeCode, parameterName);
    }
    }
