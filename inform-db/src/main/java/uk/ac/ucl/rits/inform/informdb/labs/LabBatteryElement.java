package uk.ac.ucl.rits.inform.informdb.labs;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import uk.ac.ucl.rits.inform.informdb.TemporalCore;

/**
 * This represents all the different batteries of test that can be ordered, and
 * what we know about which constituent tests make them up.
 *
 * @author Roma Klapaukh
 *
 */
public class LabBatteryElement extends TemporalCore<LabBatteryElement> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long   labBatteryTypeId;

    private long   labBatteryTypeDurableId;

    private String battery;

    private String labTestDefinitionDurableId;

    public LabBatteryElement() {}

    public LabBatteryElement(LabBatteryElement other) {
        super(other);
        this.labBatteryTypeDurableId = other.labBatteryTypeDurableId;
        this.battery = other.battery;
        this.labTestDefinitionDurableId = other.labTestDefinitionDurableId;
    }

    /**
     * @return the labBatteryTypeId
     */
    public long getLabBatteryTypeId() {
        return labBatteryTypeId;
    }

    /**
     * @param labBatteryTypeId the labBatteryTypeId to set
     */
    public void setLabBatteryTypeId(long labBatteryTypeId) {
        this.labBatteryTypeId = labBatteryTypeId;
    }

    /**
     * @return the labBatteryTypeDurableId
     */
    public long getLabBatteryTypeDurableId() {
        return labBatteryTypeDurableId;
    }

    /**
     * @param labBatteryTypeDurableId the labBatteryTypeDurableId to set
     */
    public void setLabBatteryTypeDurableId(long labBatteryTypeDurableId) {
        this.labBatteryTypeDurableId = labBatteryTypeDurableId;
    }

    /**
     * @return the battery
     */
    public String getBattery() {
        return battery;
    }

    /**
     * @param battery the battery to set
     */
    public void setBattery(String battery) {
        this.battery = battery;
    }

    /**
     * @return the labTestDefinitionDurableId
     */
    public String getLabTestDefinitionDurableId() {
        return labTestDefinitionDurableId;
    }

    /**
     * @param labTestDefinitionDurableId the labTestDefinitionDurableId to set
     */
    public void setLabTestDefinitionDurableId(String labTestDefinitionDurableId) {
        this.labTestDefinitionDurableId = labTestDefinitionDurableId;
    }

    @Override
    public LabBatteryElement copy() {
        return new LabBatteryElement(this);
    }

}