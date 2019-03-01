package uk.ac.ucl.rits.inform.informdb;

import java.time.Instant;
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

    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "timestamp with time zone")
    private Instant   createDatetime;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Mrn> mrns;

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
        return createDatetime;
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
    public List<Mrn> getMrns() {
        return mrns;
    }

    /**
     * @param mrns the mrns to set
     */
    public void setMrns(List<Mrn> mrns) {
        this.mrns = mrns;
    }

    @Override
    public String toString() {
        return "Person [person_id=" + personId + ", create_datetime=" + createDatetime + "]";
    }
}
