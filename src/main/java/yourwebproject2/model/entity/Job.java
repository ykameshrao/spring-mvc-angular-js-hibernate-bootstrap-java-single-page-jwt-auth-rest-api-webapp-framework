package yourwebproject2.model.entity;

import yourwebproject2.framework.data.JPAEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * The core Job Entity
 *
 * Created by Y.Kamesh on 8/2/2015.
 */
@Entity
@Table(indexes = {  @Index(name="name_idx", columnList = "name", unique = true),
        @Index(name="status_idx", columnList = "status"),
        @Index(name="category_idx", columnList = "category")})
public class Job extends JPAEntity<Long> {
    public enum Status {
        NEW, EXECUTING, PRIORITIZED, FAILED, RETRYING, SUCCESSFUL
    }

    private String name;
    private String metadataJson;
    private String callbackUrl;
    private Date submitTime;
    private Status status;
    private Date scheduledTime;
    private Date completionTime;
    private Integer retryCount;
    private Category category;

    @NotNull @NotBlank
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Column
    public String getMetadataJson() {
        return metadataJson;
    }

    public void setMetadataJson(String metadataJson) {
        this.metadataJson = metadataJson;
    }

    @NotNull
    @Column
    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @NotNull
    @Column
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column
    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Column
    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    @Column
    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public String toString() {
        return "Job{" +
                "name='" + name + '\'' +
                ", metadataJson='" + metadataJson + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", submitTime=" + submitTime +
                ", status=" + status +
                ", scheduledTime=" + scheduledTime +
                ", completionTime=" + completionTime +
                ", retryCount=" + retryCount +
                ", category=" + category +
                '}';
    }
}
