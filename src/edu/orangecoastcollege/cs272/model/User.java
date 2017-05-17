package edu.orangecoastcollege.cs272.model;

public final class User {
	private int mId;
	private String mUserName;
	private Role mRole;
	
	public User(final int id, final String userName, final Role role) {
		this.mId = id;
		this.mUserName = userName;
		this.mRole = role;
	}

	public final String getUserName() {
		return this.mUserName;
	}

	public final void setUserName(final String name) {
		this.mUserName = name;
	}

	public final Role getRole() {
		return this.mRole;
	}

	public final void setRole(final Role role) {
		this.mRole = role;
	}

	public final int getId() {
		return this.mId;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.mId;
		result = prime * result + ((this.mUserName == null) ? 0 : this.mUserName.hashCode());
		result = prime * result + ((this.mRole == null) ? 0 : mRole.hashCode());
		return result;
	}

	@Override
	public final boolean equals(final java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (this.mId != other.mId)
			return false;
		if (this.mUserName == null) {
			if (other.mUserName != null)
				return false;
		} else if (!this.mUserName.equals(other.mUserName))
			return false;
		if (this.mRole == null) {
			if (other.mRole != null)
				return false;
		} else if (!this.mRole.equals(other.mRole))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return "User [Id=" + this.mId + ", Name=" + this.mUserName + ", Role=" + this.mRole + "]";
	}	
}