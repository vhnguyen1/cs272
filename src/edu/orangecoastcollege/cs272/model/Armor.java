/**
 * 
 */
package edu.orangecoastcollege.cs272.model;

import java.net.URI;

/** 
 * The <code></code> class 
 *
 * @author Vincent Nguyen
 * @version 1.0
*/
public final class Armor extends Equipment {
	
	private int mArmorRating;
	private ArmorType mArmorType;

	/**
	 * @param iD
	 * @param worth
	 * @param rarity
	 * @param name
	 * @param description
	 * @param imageUri
	 */
	public Armor(final int iD, final ArmorType armorType, final String name, final String description, final int worth,
			final int rarity, final int armorRating, final URI imageUri) {
		super(iD, worth, rarity, name, description, imageUri);
		this.mArmorRating = armorRating;
		this.mArmorType = armorType;
	}
	
	public Armor(final int worth, final int rarity, final String name, final String description,
			final URI imageUri, final int armorRating, final ArmorType armorType) {
		this(-1, armorType, name, description, worth, rarity, armorRating, imageUri);
	}
	
	public Armor(final Armor other) {
		this(-1, other.mArmorType, other.mName, other.mDescription, other.mWorth, other.mRarity, other.mArmorRating,
				other.mImageUri);
	}

	/**
	 * Returns the armorRating of the <code>Armor</code>
	 * @return armorRating The armorRating
	 */
	public final int getArmorRating() {
		return this.mArmorRating;
	}

	/**
	 * Sets the current armorRating to the parameter value
	 * @param armorRating The armorRating to set
	 */
	public final void setArmorRating(final int armorRating) {
		this.mArmorRating = armorRating;
	}

	/**
	 * Returns the armorType of the <code>Armor</code>
	 * @return armorType The armorType
	 */
	public final ArmorType getArmorType() {
		return this.mArmorType;
	}

	/**
	 * Sets the current armorType to the parameter value
	 * @param armorType The armorType to set
	 */
	public final void setArmorType(final ArmorType armorType) {
		this.mArmorType = armorType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.mArmorRating;
		result = prime * result + ((this.mArmorType == null) ? 0 : this.mArmorType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Armor other = (Armor) obj;
		if (this.mArmorRating != other.mArmorRating)
			return false;
		if (this.mArmorType != other.mArmorType)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Armor [ID=" + this.mID + ", Armor Type=" + this.mArmorType + ", Name=" + this.mName
				+ ", Description=" + this.mDescription + ", Worth=" + this.mWorth + " gold, "
				+ "Rarity=" + this.mRarity + ", Armor Rating=" + this.mArmorRating
				+ ",\n\tImage URI=" + this.mImageUri + "]";
	}
}