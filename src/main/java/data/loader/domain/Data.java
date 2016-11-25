package data.loader.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Data {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private Status status;

	private Date date = new Date();

	private String str;

	/**
	 *
	 */
	public Data() {
		super();
	}

	/**
	 * @param status
	 */
	public Data(Status status) {
		super();
		this.status = status;
	}

	/**
	 * @param status
	 * @param date
	 * @param str
	 */
	public Data(Status status, Date date, String str) {
		super();
		this.date = date;
		this.str = str;
		this.status = status;
	}

	/**
	 * @param str
	 */
	public Data(String str) {
		super();
		this.str = str;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Data other = (Data) obj;
		if (this.date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!this.date.equals(other.date)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.status != other.status) {
			return false;
		}
		if (this.str == null) {
			if (other.str != null) {
				return false;
			}
		} else if (!this.str.equals(other.str)) {
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @return
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return this.status;
	}

	/**
	 * @return
	 */
	public String getStr() {
		return this.str;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.date == null) ? 0 : this.date.hashCode());
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		result = (prime * result) + ((this.status == null) ? 0 : this.status.hashCode());
		result = (prime * result) + ((this.str == null) ? 0 : this.str.hashCode());
		return result;
	}

	/**
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @param str
	 */
	public void setStr(String str) {
		this.str = str;
	}

}
