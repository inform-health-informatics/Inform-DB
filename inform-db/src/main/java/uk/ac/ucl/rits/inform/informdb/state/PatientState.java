package uk.ac.ucl.rits.inform.informdb.state;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.ac.ucl.rits.inform.informdb.TemporalCore;
import uk.ac.ucl.rits.inform.informdb.annotation.AuditTable;
import uk.ac.ucl.rits.inform.informdb.identity.HospitalVisit;
import uk.ac.ucl.rits.inform.informdb.identity.Mrn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Represents patient states that start and can end.
 * Currently envisaged as storing infection control's patient infection information and problem lists.
 * @author Anika Cawthorn
 * @author Stef Piatek
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AuditTable
public class PatientState extends TemporalCore<PatientState, PatientStateAudit> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long patientStateId;

    @ManyToOne
    @JoinColumn(name = "patientStateTypeId", nullable = false)
    private PatientStateType patientStateTypeId;

    @ManyToOne
    @JoinColumn(name = "mnrId", nullable = false)
    private Mrn mrnId;

    @ManyToOne
    @JoinColumn(name = "hospitalVisitId")
    private HospitalVisit hospitalVisitId;

    @Column(nullable = false, columnDefinition = "timestamp with time zone")
    private Instant addedDateTime;

    @Column(columnDefinition = "timestamp with time zone")
    private Instant resolutionDateTime;

    private LocalDate onsetDate;

    /**
     * temporary infection?
     */
    private String classification;

    private String status;

    private String priority;

    @Column(columnDefinition = "text")
    private String comment;

    /**
     * Minimal information constructor.
     * @param patientStateTypeId    ID for patient state type
     * @param mrn                   patient ID
     * @param addedDateTime         when patient state has been added
     */
    public PatientState(PatientStateType patientStateTypeId, Mrn mrn,  Instant addedDateTime) {
        this.patientStateTypeId = patientStateTypeId;
        this.mrnId = mrn;
        this.addedDateTime = addedDateTime;
    }

    /**
     * Build a new PatientState from an existing one.
     * @param other existing PatientState
     */
    public PatientState(PatientState other) {
        super(other);
        this.patientStateTypeId = other.patientStateTypeId;
        this.mrnId = other.mrnId;
        if (other.hospitalVisitId != null) {
            this.hospitalVisitId = other.hospitalVisitId;
        }
        this.addedDateTime = other.addedDateTime;
        this.resolutionDateTime = other.resolutionDateTime;
        this.onsetDate = other.onsetDate;
        this.classification = other.classification;
        this.status = other.status;
        this.priority = other.priority;
        this.comment = other.comment;
    }

    @Override
    public PatientState copy() {
        return new PatientState(this);
    }

    @Override
    public PatientStateAudit createAuditEntity(Instant validUntil, Instant storedUntil) {
        return new PatientStateAudit(this, validUntil, storedUntil);
    }
}

