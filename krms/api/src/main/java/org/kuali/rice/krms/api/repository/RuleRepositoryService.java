package org.kuali.rice.krms.api.repository;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * The rule repository contains all of the information about context definitions,
 * agendas, and business rules.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
@WebService(name = "ruleRepositoryService", targetNamespace = RepositoryConstants.Namespaces.KRMS_NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface RuleRepositoryService {

	/**
	 * Locates a ContextDefinition based on the given map of context qualifiers.
	 * 
	 * @param contextQualifiers
	 * @return
	 */
	@WebMethod(operationName = "selectContext")
	@WebResult(name = "contextDefinition")
	public ContextDefinition selectContext(@WebParam(name = "contextSelectionCriteria") ContextSelectionCriteria contextSelectionCriteria);
	
	/**
	 * Loads the agenda tree for the given agendaId.  The agenda tree includes
	 * the entire agenda definition in the appropriate order and with the
	 * defined agenda branching.
	 * 
	 * @param agendaId the id of the agenda for which to load the agenda tree
	 * @return the agenda tree, or null if no agenda could be located for the given agendaId
	 * 
	 * @throws IllegalArgumentException if the given agendaId is null
	 */
	@WebMethod(operationName = "getAgendaTree")
	@WebResult(name = "agendaTree")
	public AgendaTreeDefinition getAgendaTree(@WebParam(name = "agendaId") String agendaId);
	
	/**
	 * Loads all of the agendas trees for the given list of agendaIds.  The agenda tree includes
	 * the entire agenda definition in the appropriate order and with the
	 * defined agenda branching.
	 * 
	 * <p>The list which is returned from this method may not be the same size as the list
	 * which is passed to this method.  If an agenda doesn't exist for a given agenda id then
	 * no result for that id will be returned in the list.  As a result of this, the returned
	 * list can be empty, but it will never be null.
	 * 
	 * @param agendaIds the list of agenda ids for which to load the agenda trees
	 * @return the list of agenda trees for the given ids, this list will only contain agenda trees for the ids
	 * that were resolved successfully, it will never return null but could return an empty list if no agenda
	 * trees could be loaded for the given set of ids
	 * 
	 * @throws IllegalArgumentException if the given list of agendaIds is null
	 */
	@WebMethod(operationName = "getAgendaTrees")
	@WebResult(name = "agendaTrees")
	public List<AgendaTreeDefinition> getAgendaTrees(@WebParam(name = "agendaIds") List<String> agendaIds);
	
	
	@WebMethod(operationName = "getRule")
	@WebResult(name = "rule")
	public RuleDefinition getRule(@WebParam(name = "ruleId") String ruleId);
	
	@WebMethod(operationName = "getRules")
	@WebResult(name = "rules")
	public List<RuleDefinition> getRules(@WebParam(name = "ruleIds") List<String> ruleIds);

}
