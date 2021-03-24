package dev.werber.beans;

import javax.persistence.*;

@Entity
@Table(name="supporting_docs")
public class SupportingDoc {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	String name;
	@Column(name="attachment_type")
	String attachmentType;
	
	public SupportingDoc(Integer id, String name, String attachmentType) {
		this.id = id;
		this.name = name;
		this.attachmentType = attachmentType;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachmentType == null) ? 0 : attachmentType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupportingDoc other = (SupportingDoc) obj;
		if (attachmentType == null) {
			if (other.attachmentType != null)
				return false;
		} else if (!attachmentType.equals(other.attachmentType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SupportingDoc " + name + "." + attachmentType;
	}
	
	
}
