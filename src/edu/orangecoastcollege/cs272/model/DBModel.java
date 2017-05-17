package edu.orangecoastcollege.cs272.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <code>DBModel</code> represents the database model for a table containing one primary key 
 * and one or more fields.  It provides mechanisms by which new records can be created and
 * existing ones can be updated or deleted.  <code>DBModel</code> also provides functionality
 * to query the database table for a single record, all records or the total count of records.
 *  
 * @author 
 * @version
 */
public final class DBModel {
	private String mDBName;
	private String mTableName;
	private String[] mFieldNames;
	private String[] mFieldTypes;
	private Connection mConnection;
	private Statement mStmt;

	/**
	 * Instantiates a new <code>DBModel</code> given the required parameters, such as the database name,
	 * table name, field names and field types.
	 * 
	 * @param dbName The database name
	 * @param tableName The table name
	 * @param fieldNames The field names
	 * @param fieldTypes The field types
	 * @throws SQLException If the field names are not the same length as the field types, or the names/types are empty, 
	 * or there is an error connecting to the database.
	 */
	public DBModel(final String dbName, final String tableName, final String[] fieldNames, final String[] fieldTypes)
			throws SQLException {
		super();
		mDBName = dbName;
		mTableName = tableName;
		mFieldNames = fieldNames;
		mFieldTypes = fieldTypes;
		if (mFieldNames == null || mFieldTypes == null || mFieldNames.length == 0 || mFieldNames.length != mFieldTypes.length)
			throw new SQLException("Database field names and types must exist and have the same number of elements.");
		mConnection = connectToDB();
		mStmt = mConnection.createStatement();
		mStmt.setQueryTimeout(30);
		createTable();
	}
	
	/**
	 * Creates the database table, only if it does not already exist.
	 * 
	 * @throws SQLException If a database access error occurs, this method is called on a closed Statement, 
	 * or the given SQL statement produces anything other than a single ResultSet object.
	 */
	private final void createTable() throws SQLException {
		final StringBuilder createSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(
				mTableName).append("(");
		
		for (int idx = 0; idx < mFieldNames.length; idx++)
			createSQL.append(mFieldNames[idx]).append(" ").append(mFieldTypes[idx]).append(
					(idx < mFieldNames.length -1) ? "," : ")");
		
		mStmt.executeUpdate(createSQL.toString());
	}
	
	/**
	 * Gets all records from the database.
	 * 
	 * @return A <code>ResultSet</code> containing all records from the database table.
	 * @throws SQLException If a database access error occurs, this method is called on a closed Statement, 
	 * or the given SQL statement produces anything other than a single ResultSet object.
	 */
	public final ResultSet getAllRecords() throws SQLException {		
		return mStmt.executeQuery("SELECT * FROM " + mTableName);
	}
		
	/**
	 * Gets a single record from the database matching a specific primary key. 
	 * 
	 * @param key The primary key value for the record to return.
	 * @return A <code>ResultSet</code> containing a single record matching the key.
	 * @throws SQLException If a database access error occurs, this method is called on a closed Statement, 
	 * or the given SQL statement produces anything other than a single ResultSet object.
	 */
	public final ResultSet getRecord(final String key) throws SQLException {
		return mStmt.executeQuery("SELECT * FROM " + mTableName + " WHERE " + mFieldNames[0] + " = " + key);
	}
	
	/**
	 * Gets the count of all records in the database.
	 * 
	 * @return The count of all records in the database. 
	 * @throws SQLException If a database access error occurs, this method is called on a closed Statement, 
	 * or the given SQL statement produces anything other than a single ResultSet object.
	 */
	public final int getRecordCount() throws SQLException {
		int count = 0;		
		
		final ResultSet rs = getAllRecords();
		while (rs.next())
			count++;
		rs.close();
		
		return count;
	}

	/**
	 * Creates (inserts) a new record into the database with the fields and values provided.
	 * Usage: Do not provide a primary key in the fields or values, as the database will assign one automatically.
	 * 
	 * @param fields The field names, e.g. "name", "age", "gender", "worth", "citizenship", "sector", "political"
	 * @param values The values, e.g. "Mike Paul", "40", "male", "2.2", "United States", "technology", "0"
	 * @return The newly generated primary key, or -1 if there was an error.
	 * @throws SQLException If a database access error occurs, this method is called on a closed Statement, 
	 * or the given SQL statement produces anything other than a single ResultSet object.
	 */
	public final int createRecord(String[] fields, String[] values) throws SQLException {	
		if(fields == null || values == null || fields.length == 0 || fields.length != values.length)
			return -1;
		
		final StringBuilder insertSQL = new StringBuilder("INSERT INTO ").append(mTableName).append("(");
		
		for(int idx = 0; idx < fields.length; idx++)
			insertSQL.append(fields[idx]).append((idx < fields.length-1) ? "," : ") VALUES(");
		for(int idx = 0; idx < values.length; idx++)
			insertSQL.append(convertToSQLText(fields[idx], values[idx])).append((
					idx < values.length-1) ? "," : ")");
		
		mStmt.executeUpdate(insertSQL.toString());
		return mStmt.getGeneratedKeys().getInt(1);
	}
	
	/**
	 * Updates a single record from the database matching the given primary key value.
	 * Usage: Do not provide primary key in the fields or values, only provide it as the key parameter.
	 * 
	 * @param key The primary key value to update.
	 * @param fields The field names, e.g. "name", "age", "gender", "worth", "citizenship", "sector", "political"
	 * @param values The values, e.g. "Mike Paul", "40", "male", "2.2", "United States", "technology", "0"
	 * @return True if the record was updated successfully, false if the fields length does not match the values length (or if fields/values are empty).
	 * @throws SQLException
	 */
	public final boolean updateRecord(final String key, final String[] fields, final String[] values) throws SQLException {
		if (fields == null || values == null || fields.length == 0 || fields.length != values.length)
			return false;
		final StringBuilder updateSQL = new StringBuilder("UPDATE ").append(mTableName).append(" SET ");

		for (int idx = 0; idx < fields.length; idx++)
			updateSQL.append(fields[idx]).append("=").append(convertToSQLText(fields[idx], values[idx])).append((
					idx < fields.length - 1) ? ", " : " ");
		
		updateSQL.append("WHERE ").append(mFieldNames[0]).append("=").append(key);
		mStmt.executeUpdate(updateSQL.toString());
		return true;
	}

	/**
	 * Deletes all records from the database.  
	 * 
	 * @throws SQLException If a database access error occurs, this method is called on a closed Statement, 
	 * or the given SQL statement produces anything other than a single ResultSet object.
	 */
	public final void deleteAllRecords() throws SQLException {
		mStmt.executeUpdate("DELETE FROM " + mTableName);		
	}
	
	/**
	 * Deletes a single record from the database matching the given primary key value.
	 * 
	 * @param key The primary key value to delete.
	 * @throws SQLException If a database access error occurs, this method is called on a closed Statement, 
	 * or the given SQL statement produces anything other than a single ResultSet object.
	 */
	public final void deleteRecord(final String key) throws SQLException {
		mStmt.executeUpdate("DELETE FROM " + mTableName + " WHERE " + mFieldNames[0] + " = " + key);
	}

	/**
	 * Converts a field value into SQL text by surrounding value with single quotes (e.g. technology becomes 'technology')
	 * This only happens when the field provided has the SQL data type TEXT.
	 * 
	 * @param field The field name (e.g. sector)
	 * @param value The value (e.g. technology)
	 * @return The value surrounded with single quotes if it's field type is TEXT, otherwise returns the original value.
	 */
	private final String convertToSQLText(final String field, final String value) {
		for (int idx = 0; idx < mFieldNames.length; idx++) {
			if (mFieldNames[idx].equals(field)) {
				if (mFieldTypes[idx].toUpperCase().startsWith("TEXT"))
					return "'" + value + "'";
				break;
			}
		}
		return value;
	}
		
	/**
	 * Establishes a connection to the database.
	 * 
	 * @return The connection to the database.
	 * @throws SQLException If a database access error occurs, this method is called on a closed Statement, 
	 * or the given SQL statement produces anything other than a single ResultSet object.
	 */
	private final Connection connectToDB() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		final Connection connection = DriverManager.getConnection("jdbc:sqlite:" + mDBName);	
		return connection;
	}
	
	private final void close() {
		try {
			if (mStmt != null)
				mStmt.close();
			if (mConnection != null)
				mConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}