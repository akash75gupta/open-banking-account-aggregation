package io.akash.accountaggregator.fipsimulator.model.dtos;

public class MetaInfoDTO {
    private String type;
    private String value;
    private DeviceDTO deviceInfo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DeviceDTO getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceDTO deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
