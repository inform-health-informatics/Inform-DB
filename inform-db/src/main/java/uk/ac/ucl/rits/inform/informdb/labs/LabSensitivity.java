package uk.ac.ucl.rits.inform.informdb.labs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.ac.ucl.rits.inform.informdb.TemporalCore;
import uk.ac.ucl.rits.inform.informdb.annotation.AuditTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

/**
 * Sensitivities show the affect of specific agents on isolates from cultures.
 * @author Roma Klapaukh
 * @author Stef Piatek
 */
@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AuditTable
public class LabSensitivity extends TemporalCore<LabSensitivity, LabSensitivityAudit> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long labSensitivityId;

    @ManyToOne
    @JoinColumn(name = "labIsolateId", nullable = false)
    private LabIsolate labIsolateId;

    /**
     * The chemical (often antibiotic) used.
     */
    private String agent;
    /**
     * Sensitivity of the microbe to the agent.
     */
    private String sensitivity;

    @Column(columnDefinition = "timestamp with time zone")
    private Instant reportingDatetime;

    public LabSensitivity() {}

    public LabSensitivity(LabIsolate labIsolateId, String agent) {
        this.labIsolateId = labIsolateId;
        this.agent = agent;
    }


    public LabSensitivity(LabSensitivity other) {
        super(other);
        this.labSensitivityId = other.labSensitivityId;
        this.labIsolateId = other.labIsolateId;
        this.agent = other.agent;
        this.sensitivity = other.sensitivity;
        this.reportingDatetime = other.reportingDatetime;
    }


    @Override
    public LabSensitivity copy() {
        return new LabSensitivity(this);
    }

    @Override
    public LabSensitivityAudit createAuditEntity(Instant validUntil, Instant storedUntil) {
        return new LabSensitivityAudit(this, validUntil, storedUntil);
    }
}
