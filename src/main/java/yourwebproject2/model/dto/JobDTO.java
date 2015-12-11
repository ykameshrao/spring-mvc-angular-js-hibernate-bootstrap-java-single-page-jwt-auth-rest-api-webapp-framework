package yourwebproject2.model.dto;

/**
 * {"name":"job1", "metadataJsonString":"{}", "callbackUrl":"", "catId":1}
 *
 * @author: kameshr
 */
public class JobDTO {
    String name;
    String metadataJsonString;
    String callbackURL;
    Long catId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetadataJsonString() {
        return metadataJsonString;
    }

    public void setMetadataJsonString(String metadataJsonString) {
        this.metadataJsonString = metadataJsonString;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }

    @Override
    public String toString() {
        return "JobDTO{" +
                "name='" + name + '\'' +
                ", metadataJsonString='" + metadataJsonString + '\'' +
                ", catId=" + catId +
                ", callbackURL='" + callbackURL + '\'' +
                '}';
    }
}
