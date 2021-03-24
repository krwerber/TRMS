package dev.werber.beans;

import javax.persistence.*;

@Entity
@Table(name="request_for_info")
public class InfoRequest {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Employee employee;
	@Column(name="requested_info")
	private String requestedInfo;
	
	public InfoRequest() {
		this.id = null;
		this.employee = null;
		this.requestedInfo = null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getRequestedInfo() {
		return requestedInfo;
	}

	public void setRequestedInfo(String requestedInfo) {
		this.requestedInfo = requestedInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((requestedInfo == null) ? 0 : requestedInfo.hashCode());
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
		InfoRequest other = (InfoRequest) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (requestedInfo == null) {
			if (other.requestedInfo != null)
				return false;
		} else if (!requestedInfo.equals(other.requestedInfo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InfoRequest [id=" + id + ", employee=" + employee + ", requestedInfo=" + requestedInfo + "]";
	}
	
	
}
