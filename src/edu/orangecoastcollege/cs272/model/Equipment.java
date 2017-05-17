/**
 * 
 */
package edu.orangecoastcollege.cs272.model;

import java.net.URI;

/** 
 * The <code>Equipment</code> class stores information in regards to a wearable
 * item such as swords, armor, etc.
 *
 * @author Vincent Nguyen
 * @version 1.0
*/
public abstract class Equipment {
	protected int mID;
	protected int mWorth;
	protected int mRarity;
	protected String mName;
	protected String mDescription;
	protected URI mImageUri;
	
	/**
	 * Parameterized constructor for the <code>Equipment</code> class, initializing all fields to
	 * the given specified values.
	 * @param other The other object to copy values from.
	 */
	protected Equipment(final int iD, final int worth, final int rarity, final String name, final String description,
			final URI imageUri) {
		super();
		this.mID = iD;
		this.mWorth = worth;
		this.mRarity = rarity;
		this.mName = name;
		this.mDescription = description;
		this.mImageUri = imageUri;
	}
	
	/**
	 * Copy constructor for the <code>Equipment</code> class, initializing all fields to
	 * the same (in value) as the parameter object.
	 * @param other The other object to copy values from.
	 */
	protected Equipment(final Equipment other) {
		this(-1, other.mWorth, other.mRarity, other.mName, other.mDescription, other.mImageUri);
	}
	
	protected Equipment() {}
	
	/**
	 * Returns the iD of the <code>Equipment</code>
	 * @return iD The iD
	 */
	public final int getID() {
		return this.mID;
	}

	/**
	 * Sets the current iD to the parameter value
	 * @param iD The iD to set
	 */
	public final void setID(final int iD) {
		this.mID = iD;
	}

	/**
	 * Returns the worth of the <code>Equipment</code>
	 * @return worth The worth
	 */
	public final int getWorth() {
		return this.mWorth;
	}

	/**
	 * Sets the current worth to the parameter value
	 * @param worth The worth to set
	 */
	public final void setWorth(final int worth) {
		this.mWorth = worth;
	}

	/**
	 * Returns the rarity of the <code>Equipment</code>
	 * @return rarity The rarity
	 */
	public final int getRarity() {
		return this.mRarity;
	}

	/**
	 * Sets the current rarity to the parameter value
	 * @param rarity The rarity to set
	 */
	public final void setRarity(final int rarity) {
		this.mRarity = rarity;
	}

	/**
	 * Returns the name of the <code>Equipment</code>
	 * @return name The name
	 */
	public final String getName() {
		return this.mName;
	}

	/**
	 * Sets the current name to the parameter value
	 * @param name The name to set
	 */
	public final void setName(String name) {
		this.mName = name;
	}

	/**
	 * Returns the description of the <code>Equipment</code>
	 * @return description The description
	 */
	public final String getDescription() {
		return this.mDescription;
	}

	/**
	 * Sets the current description to the parameter value
	 * @param description The description to set
	 */
	public final void setDescription(final String description) {
		this.mDescription = description;
	}

	/**
	 * Returns the imageUri of the <code>Equipment</code>
	 * @return imageUri The imageUri
	 */
	public final URI getImageUri() {
		return this.mImageUri;
	}

	/**
	 * Sets the current imageUri to the parameter value
	 * @param imageUri The imageUri to set
	 */
	public final void setImageUri(final URI imageUri) {
		this.mImageUri = imageUri;
	}

	/**
	 * Generates a unique integer primary key.
	 * @return The uniquely generated primary key.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.mDescription == null) ? 0 : this.mDescription.hashCode());
		result = prime * result + this.mID;
		result = prime * result + ((this.mImageUri == null) ? 0 : this.mImageUri.hashCode());
		result = prime * result + ((this.mName == null) ? 0 : this.mName.hashCode());
		result = prime * result + this.mRarity;
		result = prime * result + this.mWorth;
		return result;
	}


	/**
	 * Compares one <code>Equipment</code> from another, checking for eqaulity between the two.
	 * @param The other <code>Equipment
	 */
	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Equipment other = (Equipment) obj;
		if (this.mDescription == null) {
			if (other.mDescription != null)
				return false;
		} else if (!this.mDescription.equals(other.mDescription))
			return false;
		if (this.mID != other.mID)
			return false;
		if (this.mImageUri == null) {
			if (other.mImageUri != null)
				return false;
		} else if (!this.mImageUri.equals(other.mImageUri))
			return false;
		if (this.mName == null) {
			if (other.mName != null)
				return false;
		} else if (!this.mName.equals(other.mName))
			return false;
		if (this.mRarity != other.mRarity)
			return false;
		if (this.mWorth != other.mWorth)
			return false;
		return true;
	}

	/**
	 * Generates a String representation of an <code>Equipment</code>
	 * @return The String representation of an <code>Equipment</code>.
	 */
	@Override
	public String toString() {
		return "Equipment [mID=" + mID + ", mWorth=" + mWorth + ", mRarity=" + mRarity + ", mName=" + mName
				+ ", mDescription=" + mDescription + ", mImageUri=" + mImageUri + "]";
	}
}
