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
public final class Weapon extends Equipment {
	private int mAttackPoints;
	private WeaponType mWeaponType;
	
	/**
	 * @param iD
	 * @param worth
	 * @param rarity
	 * @param name
	 * @param description
	 * @param imageUri
	 */
	public Weapon(final int iD, final WeaponType weaponType, final String name, final String description, final int worth,
			final int rarity, final int attackPoints, final URI imageUri) {
		super(iD, worth, rarity, name, description, imageUri);
		this.mWeaponType = weaponType;
		this.mAttackPoints = attackPoints;
	}
	
	public Weapon(final int worth, final int rarity, final String name, final String description,
			final URI imageUri, final int attackPoints, final WeaponType weaponType) {
		this(-1, weaponType, name, description, worth, rarity, attackPoints, imageUri);
	}
	
	public Weapon(final Weapon other) {
		this(-1, other.mWeaponType, other.mName, other.mDescription, other.mWorth, other.mRarity, other.mAttackPoints,
				other.mImageUri);
	}

	/**
	 * Returns the attackPoints of the <code>Weapon</code>
	 * @return attackPoints The attackPoints
	 */
	public final int getAttackPoints() {
		return this.mAttackPoints;
	}

	/**
	 * Sets the current attackPoints to the parameter value
	 * @param attackPoints The attackPoints to set
	 */
	public final void setAttackPoints(final int attackPoints) {
		this.mAttackPoints = attackPoints;
	}

	/**
	 * Returns the weaponType of the <code>Weapon</code>
	 * @return weaponType The weaponType
	 */
	public final WeaponType getWeaponType() {
		return this.mWeaponType;
	}

	/**
	 * Sets the current weaponType to the parameter value
	 * @param weaponType The weaponType to set
	 */
	public final void setWeaponType(final WeaponType weaponType) {
		this.mWeaponType = weaponType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.mAttackPoints;
		result = prime * result + ((this.mWeaponType == null) ? 0 : this.mWeaponType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(java.lang.Object other) {
		if (this == other)
			return true;
		if (!super.equals(other))
			return false;
		if (this.getClass() != other.getClass())
			return false;
		Weapon o = (Weapon) other;
		if (this.mAttackPoints != o.mAttackPoints)
			return false;
		if (this.mWeaponType != o.mWeaponType)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Weapon [mAttackPoints=" + mAttackPoints + ", mWeaponType=" + mWeaponType + ", mID=" + mID + ", mWorth="
				+ mWorth + ", mRarity=" + mRarity + ", mName=" + mName + ", mDescription=" + mDescription
				+ ", mImageUri=" + mImageUri + ", getAttackPoints()=" + getAttackPoints() + ", getWeaponType()="
				+ getWeaponType() + ", hashCode()=" + hashCode() + ", getID()=" + getID() + ", getWorth()=" + getWorth()
				+ ", getRarity()=" + getRarity() + ", getName()=" + getName() + ", getDescription()=" + getDescription()
				+ ", getImageUri()=" + getImageUri() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ "]";
	}
}