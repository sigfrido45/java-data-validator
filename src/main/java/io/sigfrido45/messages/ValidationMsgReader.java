package io.sigfrido45.messages;


import java.io.InputStream;
import java.util.Properties;


public class ValidationMsgReader {

    private Properties msgProperties;
    private Properties attrProperties;

    public ValidationMsgReader(Properties msgProperties, Properties attrProperties) {
        this.msgProperties = msgProperties;
        this.attrProperties = attrProperties;
    }

    public ValidationMsgReader(String msgResourcesFile, String attrResourcesFile) {
        setMsgProperties(msgResourcesFile);
        setAttrProperties(attrResourcesFile);
    }

    public String getMsg(String key) {
        return msgProperties.getProperty(key, "");
    }

    public String getProperty(String key, String def) {
        return attrProperties.getProperty(key, def);
    }

    private void setMsgProperties(String resourceFile) {
        var inputStream = loadResource(resourceFile);
        msgProperties = new Properties();
        loadProperties(msgProperties, inputStream);
    }

    private void setAttrProperties(String resourceFile) {
        var inputStream = loadResource(resourceFile);
        attrProperties = new Properties();
        loadProperties(attrProperties, inputStream);
    }

    private void loadProperties(Properties props, InputStream inputStream) {
        try {
            if (inputStream != null)
                props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InputStream loadResource(String resource) {
        try {
            return this.getClass().getClassLoader().getResourceAsStream(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
