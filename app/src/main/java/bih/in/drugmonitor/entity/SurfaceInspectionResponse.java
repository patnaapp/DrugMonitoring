package bih.in.drugmonitor.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class SurfaceInspectionResponse implements KvmSerializable, Serializable {

    public static Class<SurfaceInspectionResponse> SurfaceInspectionResponse_CLASS = SurfaceInspectionResponse.class;

    boolean isAuthenticated = true;

    private String INSPECTION_ID;
    private String Message;

    public SurfaceInspectionResponse(SoapObject res1) {
        this.isAuthenticated = Boolean.parseBoolean(res1.getProperty("IS_authenticate").toString());
        this.Message=res1.getProperty("Message").toString();
        this.INSPECTION_ID=res1.getProperty("Inspection_ID").toString();
    }

    public static Class<SurfaceInspectionResponse> getSurfaceInspectionResponse_CLASS() {

        return SurfaceInspectionResponse_CLASS;
    }

    public static void setSurfaceInspectionResponse_CLASS(Class<SurfaceInspectionResponse> surfaceInspectionResponse_CLASS) {
        SurfaceInspectionResponse_CLASS = surfaceInspectionResponse_CLASS;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getINSPECTION_ID() {
        return INSPECTION_ID;
    }

    public void setINSPECTION_ID(String INSPECTION_ID) {
        this.INSPECTION_ID = INSPECTION_ID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public SurfaceInspectionResponse() {
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
