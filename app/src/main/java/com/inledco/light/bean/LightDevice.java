package com.inledco.light.bean;


/**
 * 控制器支持灯具模型
 */
public class LightDevice extends ListItem {
    private String name;
    private String imageName;
    private String lightCode;
    private String lightType;

    public LightDevice(String name, String imageName, String lightCode, String lightType) {
        this.name = name;
        this.imageName = imageName;
        this.lightCode = lightCode;
        this.lightType = lightType;
    }

    @Override
    public int getType() {
        return TYPE_LIGHT;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getLightCode() {
        return lightCode;
    }

    public void setLightCode(String lightCode) {
        this.lightCode = lightCode;
    }

    public String getLightType() {
        return lightType;
    }

    public void setLightType(String lightType) {
        this.lightType = lightType;
    }
}
