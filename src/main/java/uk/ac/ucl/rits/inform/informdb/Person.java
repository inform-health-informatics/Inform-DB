package uk.ac.ucl.rits.inform.informdb;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * A person represents the Inform-DB concept of a single real person (to whom
 * MRN events happen (e.g., merges).
 * <p>
 * Persons have a creation time that cannot be modified, and serves as an
 * indicator as to their age.
 *
 * @author UCL RITS
 *
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int       personId;

    @Column(nullable = false, columnDefinition = "timestamp with time zone")
    private Instant   createDatetime;

    @OneToMany(targetEntity = PersonMrn.class, mappedBy = "person", cascade = CascadeType.ALL)
    private List<PersonMrn> mrns;

    /**
     * @return the personId
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * @param personId the personId to set
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * @return the createDatetime
     */
    public Instant getCreateDatetime() {
        return this.createDatetime;
    }

    /**
     * @param createDatetime the createDatetime to set
     */
    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    /**
     * @return the mrns
     */
    public List<PersonMrn> getMrns() {
        return mrns;
    }

    /**
     * Add an MRN to this person.
     *
     * @param mrn The mrn to add
     * @param validFrom when the association became true
     * @param storedFrom when the association was stored
     */
    public void addMrn(Mrn mrn, Instant validFrom, Instant storedFrom) {
        if (this.mrns == null) {
            this.mrns = new ArrayList<>();
        }
        PersonMrn perMrn = new PersonMrn(this, mrn);
        perMrn.setValidFrom(validFrom);
        perMrn.setStoredFrom(storedFrom);
        this.mrns.add(perMrn);
        mrn.addPerson(perMrn);
    }

    @Override
    public String toString() {
        return "Person [person_id=" + personId + ", create_datetime=" + createDatetime + "]";
    }
}
