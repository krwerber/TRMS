package dev.werber.beans;

import javax.persistence.*;

@Entity
@Table(name="format_type")
public class Format {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	String name;
	@Column(name="presentation_needed")
	Boolean presentationNeeded;
	
	public Format() {
		this.id = null;
		this.name = null;
		this.presentationNeeded = null;
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

	public Boolean getPresentationNeeded() {
		return presentationNeeded;
	}

	public void setPresentationNeeded(Boolean presentationNeeded) {
		this.presentationNeeded = presentationNeeded;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((presentationNeeded == null) ? 0 : presentationNeeded.hashCode());
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
		Format other = (Format) obj;
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
		if (presentationNeeded == null) {
			if (other.presentationNeeded != null)
				return false;
		} else if (!presentationNeeded.equals(other.presentationNeeded))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Format [id=" + id + ", name=" + name + ", presentationNeeded=" + presentationNeeded + "]";
	}
	
	
}
