package uk.ac.ucl.rits.inform.informdb.movement;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.ac.ucl.rits.inform.informdb.TemporalCore;
import uk.ac.ucl.rits.inform.informdb.annotation.AuditTable;
import uk.ac.ucl.rits.inform.informdb.identity.HospitalVisit;

/**
 * This represents a patient being in a location for an amount of time. Every
 * location visit is part of a hospital visit, as you have to be in the hospital
 * before you can go to a specific location within it. Location visits can
 * optionally have a parent location visit. This happens when the patient is
 * still considered to be at the parent location (e.g. going down to an MRI
 * scanner from a ward bed doesn't vacate the ward bed).
 *
 * @author UCL RITS
 */
@SuppressWarnings("serial")
@Entity
@Table(indexes = {@Index(name = "visit_discharge_time", columnList = "hospitalVisitId, dischargeTime DESC"),
        @Index(name = "location_visit_discharge_time", columnList = "locationId, hospitalVisitId, dischargeTime DESC")})
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AuditTable
public class LocationVisit extends TemporalCore<LocationVisit, LocationVisitAudit> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long              locationVisitId;

    @ManyToOne
    @JoinColumn(name = "hospitalVisitId", nullable = false)
    private HospitalVisit     hospitalVisitId;

    private Long              parentLocationVisitId;

    @Column(columnDefinition = "timestamp with time zone", nullable = false)
    private Instant           admissionTime;
    @Column(columnDefinition = "timestamp with time zone")
    private Instant           dischargeTime;

    /**
     * The source system from which we learnt about this hospital visit.
     */
    @Column(nullable = false)
    private String            sourceSystem;

    @OneToOne
    @JoinColumn(name = "locationId", nullable = false)
    private Location          locationId;

    public LocationVisit() {}

    /**
     * Create new location visit with all required information.
     *
     * @param validFrom     Time of the message event
     * @param storedFrom    Time that emap-core encountered the message
     * @param location      Location
     * @param hospitalVisit Hospital visit
     * @param sourceSystem  source system
     */
    public LocationVisit(Instant validFrom, Instant storedFrom, Location location, HospitalVisit hospitalVisit,
            String sourceSystem) {
        super();
        setAdmissionTime(validFrom);
        setLocationId(location);
        setSourceSystem(sourceSystem);
        setHospitalVisitId(hospitalVisit);
        setValidFrom(validFrom);
        setStoredFrom(storedFrom);
    }

    private LocationVisit(LocationVisit other) {
        super(other);
        this.locationVisitId = other.locationVisitId;
        this.hospitalVisitId = other.hospitalVisitId;
        this.parentLocationVisitId = other.parentLocationVisitId;
        this.sourceSystem = other.sourceSystem;
        this.admissionTime = other.admissionTime;
        this.dischargeTime = other.dischargeTime;
        this.locationId = other.locationId;
    }

    @Override
    public LocationVisit copy() {
        return new LocationVisit(this);
    }

    @Override
    public LocationVisitAudit createAuditEntity(Instant validUntil, Instant storedFrom) {
        return new LocationVisitAudit(this, validUntil, storedFrom);
    }

}