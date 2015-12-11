package yourwebproject2.framework.data;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;

/**
 * JPAEntity to declare fields to be in each entity table like id
 * and creational timestamps
 *
 * @author : Y Kamesh Rao
 */
@MappedSuperclass
public abstract class JPAEntity<T extends Serializable> implements Entity {
    protected  T id;
    protected Date createdAt;
    protected Date updatedAt;


    public JPAEntity() {
        createdAt = new Date();
        updatedAt = new Date();
    }


    /**
     * To make XStream deserialization assign values to
     * base class fields of createdAt and updatedAt
     *
     * @return
     */
    public Object readResolve() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
            this.updatedAt = createdAt;
        }

        return this;
    }


    @XmlElement(type = Object.class) @Id @GeneratedValue
    public T getId() {
        return id;
    }


    public void setId(T id) {
        this.id = id;
    }


    @JsonIgnore @Temporal(TemporalType.DATE) @Column
    public Date getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    @JsonIgnore @Temporal(TemporalType.TIMESTAMP) @Column
    public Date getUpdatedAt() {
        return updatedAt;
    }


    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
