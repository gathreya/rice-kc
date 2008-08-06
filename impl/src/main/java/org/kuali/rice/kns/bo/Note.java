/*
 * Copyright 2007 The Kuali Foundation.
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

package org.kuali.rice.kns.bo;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.kuali.rice.kns.KNSServiceLocator;
import org.kuali.rice.kns.bo.user.UniversalUser;
import org.kuali.rice.kns.util.KNSConstants;

/**
 * Represents a user note in the system.
 */
@Entity
@Table(name="SH_NTE_T")
public class Note extends PersistableBusinessObjectBase {
    private static final long serialVersionUID = -7647166354016356770L;

    @Id
	@Column(name="NTE_ID")
	private Long noteIdentifier;
    @Column(name="RMT_OBJ_ID")
	private String remoteObjectIdentifier;
    @Column(name="NTE_AUTH_ID")
	private String authorUniversalIdentifier;
    //@Temporal(TemporalType.TIMESTAMP)
	@Column(name="NTE_POST_TS")
	private Timestamp notePostedTimestamp;
    @Column(name="NTE_TYP_CD")
	private String noteTypeCode;
    @Column(name="NTE_TXT")
	private String noteText;
    @Column(name="NTE_TPC_TXT")
	private String noteTopicText;
    @Column(name="NTE_PRG_CD")
	private String notePurgeCode;
    @Transient
    private String attachmentIdentifier;

    @OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST})
	@JoinColumn(name="NTE_TYP_CD", insertable=false, updatable=false)
	private NoteType noteType;
    private UniversalUser authorUniversal;
    @OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.REMOVE, CascadeType.MERGE})
	@JoinColumn(name="NTE_ID")
	private Attachment attachment;
    @Transient
    private AdHocRouteRecipient adHocRouteRecipient;

    /**
     * Default constructor.
     */
    public Note() {
        super();

        Timestamp now = KNSServiceLocator.getDateTimeService().getCurrentTimestamp();
        this.setNotePostedTimestamp(now);
        this.setNoteText(KNSConstants.EMPTY_STRING);
        // for now just do this
        this.setNoteTypeCode("DH");

        this.authorUniversal = new UniversalUser();
        this.setAdHocRouteRecipient(new AdHocRoutePerson());
    }

    /**
     * Gets the noteIdentifier attribute.
     * 
     * @return Returns the noteIdentifier.
     */
    public Long getNoteIdentifier() {
        return noteIdentifier;
    }

    /**
     * Sets the noteIdentifier attribute value.
     * 
     * @param noteIdentifier The noteIdentifier to set.
     */
    public void setNoteIdentifier(Long noteIdentifier) {
        this.noteIdentifier = noteIdentifier;
    }

    /**
     * Gets the remoteObjectIdentifier attribute.
     * 
     * @return Returns the remoteObjectIdentifier
     */
    public String getRemoteObjectIdentifier() {
        return remoteObjectIdentifier;
    }

    /**
     * Sets the remoteObjectIdentifier attribute.
     * 
     * @param remoteObjectIdentifier The remoteObjectIdentifier to set.
     */
    public void setRemoteObjectIdentifier(String remoteObjectIdentifier) {
        this.remoteObjectIdentifier = remoteObjectIdentifier;
    }


    /**
     * Gets the authorUniversalIdentifier attribute.
     * 
     * @return Returns the authorUniversalIdentifier
     */
    public String getAuthorUniversalIdentifier() {
        return authorUniversalIdentifier;
    }

    /**
     * Sets the authorUniversalIdentifier attribute.
     * 
     * @param authorUniversalIdentifier The authorUniversalIdentifier to set.
     */
    public void setAuthorUniversalIdentifier(String noteAuthorIdentifier) {
        this.authorUniversalIdentifier = noteAuthorIdentifier;
    }


    /**
     * Gets the notePostedTimestamp attribute.
     * 
     * @return Returns the notePostedTimestamp
     */
    public Timestamp getNotePostedTimestamp() {
        return notePostedTimestamp;
    }

    /**
     * Sets the notePostedTimestamp attribute.
     * 
     * @param notePostedTimestamp The notePostedTimestamp to set.
     */
    public void setNotePostedTimestamp(Timestamp notePostedTimestamp) {
        this.notePostedTimestamp = notePostedTimestamp;
    }


    /**
     * Gets the noteTypeCode attribute.
     * 
     * @return Returns the noteTypeCode
     */
    public String getNoteTypeCode() {
        return noteTypeCode;
    }

    /**
     * Sets the noteTypeCode attribute.
     * 
     * @param noteTypeCode The noteTypeCode to set.
     */
    public void setNoteTypeCode(String noteTypeCode) {
        this.noteTypeCode = noteTypeCode;
    }


    /**
     * Gets the noteText attribute.
     * 
     * @return Returns the noteText
     */
    public String getNoteText() {
        return noteText;
    }

    /**
     * Sets the noteText attribute.
     * 
     * @param noteText The noteText to set.
     */
    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }


    /**
     * Gets the noteTopicText attribute.
     * 
     * @return Returns the noteTopicText.
     */
    public String getNoteTopicText() {
        return noteTopicText;
    }

    /**
     * Sets the noteTopicText attribute value.
     * 
     * @param noteTopicText The noteTopicText to set.
     */
    public void setNoteTopicText(String noteTopicText) {
        this.noteTopicText = noteTopicText;
    }

    /**
     * Gets the notePurgeCode attribute.
     * 
     * @return Returns the notePurgeCode
     */
    public String getNotePurgeCode() {
        return notePurgeCode;
    }

    /**
     * Sets the notePurgeCode attribute.
     * 
     * @param notePurgeCode The notePurgeCode to set.
     */
    public void setNotePurgeCode(String notePurgeCode) {
        this.notePurgeCode = notePurgeCode;
    }

    /**
     * Gets the noteType attribute.
     * 
     * @return Returns the noteType.
     */
    public NoteType getNoteType() {
        return noteType;
    }

    /**
     * Sets the noteType attribute value.
     * 
     * @param noteType The noteType to set.
     * @deprecated
     */
    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    /**
     * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
     */
    protected LinkedHashMap toStringMapper() {
        LinkedHashMap m = new LinkedHashMap();
        if (this.noteIdentifier != null) {
            m.put("noteIdentifier", this.noteIdentifier.toString());
        }
        return m;
    }

    /**
     * Gets the authorUniversal attribute.
     * 
     * @return Returns the authorUniversal.
     */
    public UniversalUser getAuthorUniversal() {
        authorUniversal = KNSServiceLocator.getUniversalUserService().updateUniversalUserIfNecessary(authorUniversalIdentifier, authorUniversal);
        return authorUniversal;
    }

    /**
     * Sets the authorUniversal attribute value.
     * 
     * @param authorUniversal The authorUniversal to set.
     * @deprecated
     */
    public void setAuthorUniversal(UniversalUser authorUniversal) {
        this.authorUniversal = authorUniversal;
    }

    /**
     * Gets the attachment attribute.
     * 
     * @return Returns the attachment.
     */
    public Attachment getAttachment() {
        return attachment;
    }

    /**
     * Sets the attachment attribute value.
     * 
     * @param attachment The attachment to set.
     */
    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    /**
     * Gets the attachmentIdentifier attribute.
     * 
     * @return Returns the attachmentIdentifier.
     */
    public String getAttachmentIdentifier() {
        return attachmentIdentifier;
    }

    /**
     * Sets the attachmentIdentifier attribute value.
     * 
     * @param attachmentIdentifier The attachmentIdentifier to set.
     */
    public void setAttachmentIdentifier(String attachmentIdentifier) {
        this.attachmentIdentifier = attachmentIdentifier;
    }

    /**
     * Adds the given attachment to this note. More specifically, sets both the attachmentIdentifier and the attachment reference,
     * since they both need to be done separately now that we aren't using anonymous keys.
     * 
     * @param attachment
     */
    public void addAttachment(Attachment attachment) {
        setAttachmentIdentifier(attachment.getAttachmentIdentifier());
        setAttachment(attachment);

        // copy foreign key and redundant values into attachment
        attachment.setNoteIdentifier(noteIdentifier);

    }

    /**
     * Removes the current attachment, if any. More specifically, clears both the attachmentIdentifier and the attachment reference,
     * since they both need to be done separately now that we aren't using anonymous keys.
     */
    public void removeAttachment() {
        setAttachment(null);
        setAttachmentIdentifier(null);
    }

    /**
     * @return the adHocRouteRecipient
     */
    public AdHocRouteRecipient getAdHocRouteRecipient() {
        return adHocRouteRecipient;
    }

    /**
     * @param adHocRouteRecipient the adHocRouteRecipient to set
     */
    public void setAdHocRouteRecipient(AdHocRouteRecipient adHocRouteRecipient) {
        this.adHocRouteRecipient = adHocRouteRecipient;
    }

}

