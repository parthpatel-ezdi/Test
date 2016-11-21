/**
 * 
 */
package com.ezdi.mt.worklist.pojo;

import com.ezdi.mt.core.util.ProcessData;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author EZDI\atul.r
 *
 */
@Table("ez_document_master_mt")
public class TranscriptionDocument implements Comparable<TranscriptionDocument> {

	@Column(value = "updated_by")
	private String updatedBy;

	@Column(value = "updated_date")
	private Date updatedDate;

	@PrimaryKeyColumn(name = "document_id", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
	private String documentId;

	@PrimaryKeyColumn(name = "audio_id", ordinal = 3, type = PrimaryKeyType.PARTITIONED)
	private String audioId;

	@PrimaryKeyColumn(name = "user_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private Integer userId;
	
	@Column(value = "patient_first_name")
	private String patientFirstName;

	@Column(value = "patient_last_name")
	private String patientLastName;

	@Column(value = "patient_mrn")
	private String mrn;

	@Column(value = "patient_accountno")
	private String accountNo;

	@Column(value = "audio_file_play_time_in_sec")
	private Integer audioFilePlayTimeInSec;

	@Column(value = "tracking_number")
	private String trackingNumber;

	@Column(value = "service_datetime")
	private Date serviceDatetime;

	@Column(value = "document_current_status")
	private String fileStatus;

	

	@Column(value = "audio_file_dictation_date")
	private Date dateOfDictation;

	/*@Column(value = "transcription_datetime")
	private Date transcriptionDatetime;*/

	@Column(value = "worktype_desc")
	private String worktypeDesc;

	@Column(value = "dictating_physician_name")
	private String dictatingPhysicianName;

	@Column(value = "attending_physician_name")
	private String attendingPhysicianName;

	@Column(value = "turn_around_time")
	private Integer turnAroundTime;

	/*@Column(value = "tat_change_request_flag")
	private String tatChangeRequestFlag;*/

	/*@Column(value = "tat_change_log")
	private String tatChangeLog;*/

	@Column(value = "document_current_status_id")
	private Integer documentCurrentStatusId;

	@Column(value = "document_file_lock")
	private String documentFileLock;

	@Column(value = "worktype_value")
	private String worktypeValue;

	@Column(value = "hospital_name")
	private String hospitalName;
	
	private Integer remainingTat;

    @Column(value = "hospital_shortname")
    private String hospitalShortName;

    @Column(value = "hospital_id")
    private Integer hospitalId;

    @Column(value = "document_display_id")
    private String uniqueId;

    @Column(value = "worktype")
    private Integer worktypeId;

    @Column(value = "tat_expiry_datetime")
    private Date tatExpiry;

    @Column(value = "document_name")
    private String documentName;

    @Column(value = "document_path")
    private String documentPath;

    @Column(value = "patient_accession_no")
    private String accessionNumber;

    @Column(value = "template_file_name")
    private String templateName;

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    /*this field to hold the solr id while getting the work list from solr
       * it is user_id+_+document_id in solr*/
    private String solrId;

    public String getSolrId() {
        return solrId;
    }

    public void setSolrId(String solrId) {
        this.solrId = solrId;
    }

    public String getHospitalName() {
		return ProcessData.isValid(this.hospitalName)?this.hospitalName:"";
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getAudioId() {
		return audioId;
	}

	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getMrn() {
		return mrn;
	}

	public void setMrn(String mrn) {
		this.mrn = mrn;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Integer getAudioFilePlayTimeInSec() {
        if(!ProcessData.isValid(audioFilePlayTimeInSec)){
            return 0;
        }
		return audioFilePlayTimeInSec;
	}

	public void setAudioFilePlayTimeInSec(Integer audioFilePlayTimeInSec) {
		this.audioFilePlayTimeInSec = audioFilePlayTimeInSec;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Date getServiceDatetime() {
		return serviceDatetime;
	}

	public void setServiceDatetime(Date serviceDatetime) {
		this.serviceDatetime = serviceDatetime;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Date getDateOfDictation() {
		return dateOfDictation;
	}

	public void setDateOfDictation(Date dateOfDictation) {
		this.dateOfDictation = dateOfDictation;
	}

	public String getWorktypeDesc() {
		return worktypeDesc;
	}

	public void setWorktypeDesc(String worktypeDesc) {
		this.worktypeDesc = worktypeDesc;
	}

	public String getDictatingPhysicianName() {
		return ProcessData.isValid(dictatingPhysicianName)?this.dictatingPhysicianName:"";
	}

	public void setDictatingPhysicianName(String dictatingPhysicianName) {
		this.dictatingPhysicianName = dictatingPhysicianName;
	}

	public String getAttendingPhysicianName() {
		return ProcessData.isValid(attendingPhysicianName)?this.attendingPhysicianName:"";
	}

	public void setAttendingPhysicianName(String attendingPhysicianName) {
		this.attendingPhysicianName = attendingPhysicianName;
	}

	public Integer getTurnAroundTime() {
		return turnAroundTime;
	}

	public void setTurnAroundTime(Integer turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}

	public Integer getDocumentCurrentStatusId() {
		return documentCurrentStatusId;
	}

	public void setDocumentCurrentStatusId(Integer documentCurrentStatusId) {
		this.documentCurrentStatusId = documentCurrentStatusId;
	}

	public String getDocumentFileLock() {
		return documentFileLock;
	}

	public void setDocumentFileLock(String documentFileLock) {
		this.documentFileLock = documentFileLock;
	}

	public String getWorktypeValue() {
        return ProcessData.isValid(worktypeValue)?this.worktypeValue:"";
	}

	public void setWorktypeValue(String worktypeValue) {
		this.worktypeValue = worktypeValue;
	}


    public String getHospitalShortName() {
        return ProcessData.isValid(this.hospitalShortName)?this.hospitalShortName:"";
    }

    public void setHospitalShortName(String hospitalShortName) {
        this.hospitalShortName = hospitalShortName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getWorktypeId() {
        return worktypeId;
    }

    public void setWorktypeId(Integer worktypeId) {
        this.worktypeId = worktypeId;
    }

    public Date getTatExpiry() {
        return tatExpiry;
    }

    public void setTatExpiry(Date tatExpiry) {
        this.tatExpiry = tatExpiry;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getRemainingTat() {
        Calendar c = Calendar.getInstance();

        c.setTime(ProcessData.isValid(getTatExpiry())?getTatExpiry():new Date());
        c.setTimeZone(TimeZone.getTimeZone("IST"));
        /*System.out.println("date of dict -->  "+getDateOfDictation());
        System.out.println("tat -->   "+getTurnAroundTime());
        System.out.println(" d1 -->  "+c.getTime());*/
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        c1.setTimeZone(TimeZone.getTimeZone("IST"));
       /* System.out.println("d2 --->   "+c1.getTime());
        System.out.println(c.getTime().getTime() - c1.getTime().getTime());*/
        int timeInMin = (int) (c.getTime().getTime()-c1.getTime().getTime());
        /*System.out.println("time in minute --->     "+timeInMin/(60 * 1000));*/
        return timeInMin/(60 * 1000);
    }

    public void setRemainingTat(Integer remainingTat) {
        this.remainingTat = remainingTat;
    }

    @Override
    public int compareTo(TranscriptionDocument transcriptionDocument) {
        return (this.getRemainingTat() - transcriptionDocument.getRemainingTat());
    }

}
