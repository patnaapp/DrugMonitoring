package bih.in.drugmonitor.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class FilterOptionEntity implements KvmSerializable, Serializable {

    public static Class<FilterOptionEntity> FilterOptionEntity_CLASS = FilterOptionEntity.class;

    boolean isAuthenticated = true;

    String optionText;
    String optionValue;
    String optionType;
    String id;

    public FilterOptionEntity(SoapObject res1) {
        this.isAuthenticated = Boolean.parseBoolean(res1.getProperty("IS_authenticate").toString());
        this.optionText=res1.getProperty("Option_Text").toString();
        this.optionValue=res1.getProperty("Option_Values").toString();
    }

    public FilterOptionEntity() {
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
