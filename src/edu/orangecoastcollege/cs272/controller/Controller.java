/**
 * 
 */
package edu.orangecoastcollege.cs272.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import edu.orangecoastcollege.cs272.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * 
 * @author mengv
 * @version 1.0
 */
public final class Controller {
    private User mCurrentUser;
    private static final Role DEFAULT_ROLE = Role.valueOf("STANDARD");
    //private static final String DEFAULT_ROLE = "STANDARD";
    private ObservableList<User> mAllUsersList;
    
    private ObservableList<Weapon> mAllWeaponsList;
    private ObservableList<Armor> mAllArmorList;
    private ObservableList<Item> mAllItemsList;
    
	public static final double WINDOW_WIDTH = 1000;
	public static final double WINDOW_HEIGHT = WINDOW_WIDTH * 9.0 / 16.0;
	public static final int ENEMY_COUNT = 1;

	private static Controller theOne;
	private Player mPlayer;
	private ArrayList<Enemy> mEnemysList;
	private ArrayList<Projectile> mAllProjectiles;
	
    private static final String DB_NAME = "adventure_game.db";
    private DBModel mUserDB;
    private DBModel mWeaponsDB;
    private DBModel mArmorDB;
    private DBModel mItemsDB;
    
    private static final String WEAPONS_DATA_FILE = "weapons_lite.csv";
    private static final String ARMOR_DATA_FILE = "armor_lite.csv";
    private static final String ITEMS_DATA_FILE = "items_lite.csv";
	
    private static final String USER_TABLE_NAME = "user";
    private static final String[] USER_FIELD_NAMES = { "id", "username", "role", "password" };
    private static final String[] USER_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT", "TEXT" };
	
    private static final String SCORE_TABLE_NAME = "scores";
    private static final String[] SCORE_FIELD_NAMES = { "score_id", "name", "score_points", "character_class", "image_uri" };
    private static final String[] SCORE_FIELD_TYPES = { "INTEGER", "TEXT", "INTEGER", "TEXT", "TEXT" };
	
    private static final String LEVEL_TABLE_NAME = "levels";
    private static final String[] LEVEL_FIELD_NAMES = { "level_id", "name", "difficulty_rating", "number_enemies", "background_music_uri", "background_image" };
    private static final String[] LEVEL_FIELD_TYPES = { "INTEGER", "TEXT", "INTEGER", "INTEGER", "TEXT", "TEXT" };
	
    private static final String WEAPON_TABLE_NAME = "weapons";
    private static final String[] WEAPON_FIELD_NAMES = { "weapon_id", "weapon_type", "name", "description", "worth", "rarity_value", "attack_points", "image_uri" };
    private static final String[] WEAPON_FIELD_TYPES = { "INTEGER", "TEXT", "TEXT", "TEXT", "INTEGER", "INTEGER", "INTEGER", "TEXT" };
    
    private static final String ARMOR_TABLE_NAME = "armor";
    private static final String[] ARMOR_FIELD_NAMES = { "armor_id", "armor_type", "name", "description", "worth", "rarity_value", "armor_rating", "image_uri" };
    private static final String[] ARMOR_FIELD_TYPES = { "INTEGER", "TEXT", "TEXT", "TEXT", "INTEGER", "INTEGER", "INTEGER", "TEXT" };

    private static final String ITEM_TABLE_NAME = "items";
    private static final String[] ITEM_FIELD_NAMES = { "item_id", "item_type", "name", "description", "worth", "rarity_value", "points", "image_uri" };
    private static final String[] ITEM_FIELD_TYPES = { "INTEGER", "TEXT", "TEXT", "TEXT", "INTEGER", "INTEGER", "INTEGER", "TEXT" };

    public static final String SUCCESS = "SUCCESS";
    public static final String USER_EXISTS = "Entered username already exists. Please enter another one.";
    public static final String ACCOUNT_CREATION_FAIL = "Account creation failed. Please try again.";
    public static final String COMBINATION_INCORRECT = "Username and password combination is incorrect. Please try again.";
    public static final String PASSWORD_INCORRECT = "Entered password is incorrect. Please try again.";
    public static final String USERNAME_DOES_NOT_MEET_CRITERIA = "Entered username does not meet the following criteria. Please try again.";
    public static final String PASSWORD_DOES_NOT_MEET_CRITERIA = "Entered password does not meet the following criteria. Please try again.";
    public static final String PASSWORDS_DO_NOT_MATCH = "Entered passwords do not match. Please try again.";
    public static final String PASSWORD_SAME_AS_USER = "Entered password cannot be the same as the username. Please try again.";
    
	private Controller() {}
	
	public static Controller getInstance() {
		if (theOne == null) {
			theOne = new Controller();
			
            try {
            	System.out.println("Loading User Database...");
                theOne.mAllUsersList = FXCollections.observableArrayList();
                theOne.mUserDB = new DBModel(DB_NAME, USER_TABLE_NAME, USER_FIELD_NAMES, USER_FIELD_TYPES);
                theOne.mUserDB.deleteAllRecords();
                ResultSet userRS = theOne.mUserDB.getAllRecords();
                
                int id;
                String username;
                Role role;
                
                while (userRS.next()) {
                    id = userRS.getInt(USER_FIELD_NAMES[0]);
                    username = userRS.getString(USER_FIELD_NAMES[1]);
                    role = Role.valueOf(userRS.getString(USER_FIELD_NAMES[2]));
                    theOne.mAllUsersList.add(new User(id, username, role));
                }
                userRS.close();
                //theOne.mAllUsersList.forEach(System.out::println);

            	System.out.println("Loading Weapon Database...");
                theOne.mWeaponsDB = new DBModel(DB_NAME, WEAPON_TABLE_NAME, WEAPON_FIELD_NAMES,
                		WEAPON_FIELD_TYPES);
                theOne.mWeaponsDB.deleteAllRecords();  
                //theOne.initializeWeaponsDBFromFile(); 
                ResultSet weaponRS = theOne.mWeaponsDB.getAllRecords();
                
                int weaponID;
                WeaponType weaponType;
                String weaponName;
                String weaponDescription;
                int weaponWorth;
                int weaponRarity;
                int attackPoints;
                URI weaponImageURI;
                
                theOne.mAllWeaponsList = FXCollections.observableArrayList();     
                while (weaponRS.next()) {
                	weaponID = weaponRS.getInt(WEAPON_FIELD_NAMES[0]);
                    weaponType = WeaponType.valueOf(weaponRS.getString(WEAPON_FIELD_NAMES[1]));
                    weaponName = weaponRS.getString(WEAPON_FIELD_NAMES[2]);
                    weaponDescription = weaponRS.getString(WEAPON_FIELD_NAMES[3]);
                    weaponWorth = weaponRS.getInt(WEAPON_FIELD_NAMES[4]);
                    weaponRarity = weaponRS.getInt(WEAPON_FIELD_NAMES[5]);
                    attackPoints = weaponRS.getInt(WEAPON_FIELD_NAMES[6]);                
                    weaponImageURI = URI.create(weaponRS.getString(WEAPON_FIELD_NAMES[7]));    
                    theOne.mAllWeaponsList.add(new Weapon(weaponID, weaponType, weaponName, weaponDescription,
                    		weaponWorth, weaponRarity, attackPoints, weaponImageURI));
                }    
                weaponRS.close();
                //theOne.mAllWeaponsList.forEach(System.out::println);

            	System.out.println("Loading Armor Database...");
                theOne.mArmorDB = new DBModel(DB_NAME, ARMOR_TABLE_NAME, ARMOR_FIELD_NAMES,
                		ARMOR_FIELD_TYPES);
                theOne.mArmorDB.deleteAllRecords();   
                //theOne.initializeArmorDBFromFile();

                int armorID;
                ArmorType armorType;
                String armorName;
                String armorDescription;
                int armorWorth;
                int armorRarity;
                int armorRating;
                URI armorImageURI;
                
                theOne.mAllArmorList = FXCollections.observableArrayList();
                final ResultSet armorRS = theOne.mArmorDB.getAllRecords();  
                while (armorRS.next()) {
                	armorID = armorRS.getInt(ARMOR_FIELD_NAMES[0]);
                	armorType = ArmorType.valueOf(armorRS.getString(ARMOR_FIELD_NAMES[1]));
                	armorName = armorRS.getString(ARMOR_FIELD_NAMES[2]);
                	armorDescription = armorRS.getString(ARMOR_FIELD_NAMES[3]);
                	armorWorth = armorRS.getInt(ARMOR_FIELD_NAMES[4]);
                	armorRarity = armorRS.getInt(ARMOR_FIELD_NAMES[5]);
                	armorRating = armorRS.getInt(ARMOR_FIELD_NAMES[6]);
                	armorImageURI = URI.create(armorRS.getString(ARMOR_FIELD_NAMES[7]));
                    theOne.mAllArmorList.add(new Armor(armorID, armorType, armorName, armorDescription,
                    		armorWorth, armorRarity, armorRating, armorImageURI));
                } 
                armorRS.close();
                //theOne.mAllArmorList.forEach(System.out::println);
            } catch (SQLException e) {
                e.printStackTrace();
            }
			
            theOne.mAllUsersList = FXCollections.observableArrayList();
			theOne.mPlayer = new Player(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, "Vichet",
					new Image(new File("player-move.png").toURI().toString()));
			theOne.mEnemysList = new ArrayList<Enemy>();
			theOne.mAllProjectiles = new ArrayList<>();
			for (int i = 0; i < ENEMY_COUNT; i++) {
				Enemy e = new Enemy((Math.random() * (WINDOW_WIDTH - 32)), 100, "Trolls",
						new Image(new File("troll-move.png").toURI().toString())); 
				theOne.mEnemysList.add(e);
			}
		}
		return theOne;
	}
	
	public final boolean isValidUsername(final String username) {
		return username.matches("[a-zA-Z0-9 @._']{4,20}$*");
	}
	
    public final boolean isValidPassword(final String password) {
        return password.matches("^(?=.*[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9 @._']{8,20}$*");
    }
    
    public final String signIn(final String u, final String p) {
    	final String username = u.trim();
    	final String password = p.trim();
    	
    	if (isValidUsername(username) && isValidPassword(password)) { 	
        	final Iterator<User> userIT = mAllUsersList.iterator();
        	User user;
        	
            while (userIT.hasNext()) {
            	user = (User) userIT.next();
            	
                if (user.getUserName().equalsIgnoreCase(username)) {
                	ResultSet rs = null;
                    try {
                        rs = theOne.mUserDB.getRecord(String.valueOf(user.getId()));
                        final String storedPassword = rs.getString("password");
                        rs.close();
                        
                        if (password.equals(storedPassword)) {
                            theOne.mCurrentUser = user;
                            return SUCCESS;
                        } else
                            return PASSWORD_INCORRECT;
                    } catch (SQLException e) {
                    	e.printStackTrace();
                    }
                }
            }
    	}    
        return COMBINATION_INCORRECT;
    }
    
    public final String signUp(final String u, final String p, final String cp)
    {
    	final String username = u.trim();
    	final String password = p.trim();
    	final String confirmPassword = cp.trim();
    	
    	if (!isValidUsername(username))
    		return USERNAME_DOES_NOT_MEET_CRITERIA;
    	if (!isValidPassword(password))
    		return PASSWORD_DOES_NOT_MEET_CRITERIA;
    	if (!confirmPassword.equals(password))
    		return PASSWORDS_DO_NOT_MATCH;
    	if (password.equalsIgnoreCase(username))
        	return PASSWORD_SAME_AS_USER;
    	
		final Iterator<User> userIT = mAllUsersList.iterator();
		User user;

		while (userIT.hasNext()) {
			user = (User) userIT.next();
			if (user.getUserName().equalsIgnoreCase(username))
				return USER_EXISTS;
		}

		try {
			final int id = theOne.mUserDB.createRecord(
					Arrays.copyOfRange(USER_FIELD_NAMES, 1, USER_FIELD_NAMES.length),
					new String[] { username, DEFAULT_ROLE.toString(), password });
			final User currentUser = new User(id, username, DEFAULT_ROLE);
			theOne.mAllUsersList.add(currentUser);
			theOne.mCurrentUser = currentUser;
		} catch (SQLException e) {
			return ACCOUNT_CREATION_FAIL;
		}
		return SUCCESS;
    }
	
	public Player getPlayer() {
		return theOne.mPlayer;
	}
	
	public void movePlayer(double dx, double dy, double cx, double cy) {
		if (dx == 0 && dy == 0)
			return;
		double x = dx + theOne.mPlayer.getX();
		double y = dy + theOne.mPlayer.getY();

		moveTo(x, y, cx, cy);
	}
	
	public void moveTo(double x, double y, double cx, double cy) {
		if (x - cx >= 0 && x + cx <= WINDOW_WIDTH && y - cy >= 0 && y + cy <= WINDOW_HEIGHT) {
			theOne.mPlayer.setPos(x, y);
		}
	}
	
	public void playerGotHit() {
		theOne.mPlayer.wasHit(10);
	}
	
	public ArrayList<Enemy> getAllEnemyList() {
		return new ArrayList<Enemy>(theOne.mEnemysList);
	}
	public ArrayList<Projectile> getAllProjectiles() {
		return new ArrayList<Projectile>(mAllProjectiles);
	}
	public void playerShot(double mouseX, double mouseY) {
		String projectileFileStr = null;
		switch(theOne.mPlayer.getSkill()) {
		case NoSkill:
			projectileFileStr = "projectile.png";
			break;
		case FireBall:
			projectileFileStr = "fireball.png";
			break;
		}
		Projectile shot = new Projectile(theOne.mPlayer.getX(), theOne.mPlayer.getY(), mouseX, mouseY, theOne.mPlayer.getAttackPower(), "Player Shot", new Image(new File(projectileFileStr).toURI().toString()));
		theOne.mAllProjectiles.add(shot);
	}
	
	public void playerUsedSkill(Skill skill) {
		theOne.mPlayer.setSkill(skill);
	}
	
    private final int initializeWeaponsDBFromFile() throws SQLException {
        if (theOne.mWeaponsDB.getRecordCount() > 0) return 0;

        int recordsCreated = 0;
        Scanner cin = null;
        try {
            cin = new Scanner(new File(WEAPONS_DATA_FILE));
            cin.nextLine();

            String[] data;
            String[] values;
            
            while (cin.hasNextLine()) {
                data = cin.nextLine().split(",");
                
                if (data.length != WEAPON_FIELD_NAMES.length) {
                	System.err.println("Skippng bad CSV row: " + data[0]);
                	continue;
                }
                
                values = new String[WEAPON_FIELD_NAMES.length-1];
                //values[0] = data[0];
                values[0] = data[1];
                values[1] = data[2].replaceAll("'", "''");
                values[2] = data[3].replaceAll("'", "''");
                values[3] = data[4];
                values[4] = data[5];
                values[5] = data[6];
                values[6] = (data[7]);
                theOne.mWeaponsDB.createRecord(Arrays.copyOfRange(WEAPON_FIELD_NAMES, 1,
                		WEAPON_FIELD_NAMES.length), values);
                
                //System.out.println("Controller Line 352: " + Arrays.toString(values));
                recordsCreated++;
            }

            cin.close();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
            return 0;
        } finally {
        	if (cin != null)
        		cin.close();
        }
        return recordsCreated;
    }
    
    private final int initializeArmorDBFromFile() throws SQLException {
        if (theOne.mArmorDB.getRecordCount() > 0) return 0;

        int recordsCreated = 0;
        Scanner cin = null;
        try {
            cin = new Scanner(new File(ARMOR_DATA_FILE));
            cin.nextLine();

            String[] data;
            String[] values;
            
            while (cin.hasNextLine()) {
                data = cin.nextLine().split(",");
                
                if (data.length != ARMOR_FIELD_NAMES.length) {
                	System.err.println("Skippng bad CSV row: " + data[0]);
                	continue;
                }
                
                values = new String[ARMOR_FIELD_NAMES.length-1];
                //values[0] = data[0];
                values[0] = data[1];
                values[1] = data[2].replaceAll("'", "''");
                values[2] = data[3].replaceAll("'", "''");
                values[3] = data[4];
                values[4] = data[5];
                values[5] = data[6];
                values[6] = (data[7]);
                theOne.mArmorDB.createRecord(Arrays.copyOfRange(ARMOR_FIELD_NAMES, 1,
                		ARMOR_FIELD_NAMES.length), values);
                
                //System.out.println("Controller Line 391: " + Arrays.toString(values));
                recordsCreated++;
            }
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
            return 0;
        } finally {
        	if (cin != null)
        		cin.close();
        }
        return recordsCreated;
    }
    
    private final int initializeItemsDBFromFile() throws SQLException {
        if (theOne.mItemsDB.getRecordCount() > 0) return 0;

        int recordsCreated = 0;
        Scanner cin = null;
        try {
            cin = new Scanner(new File(ITEMS_DATA_FILE));
            cin.nextLine();

            String[] data;
            String[] values;
            
            while (cin.hasNextLine()) {
                data = cin.nextLine().split(",");
                
                if (data.length != ITEM_FIELD_NAMES.length) {
                	System.err.println("Skippng bad CSV row: " + data[0]);
                	continue;
                }
                
                values = new String[ITEM_FIELD_NAMES.length-1];
                //values[0] = data[0];
                values[0] = data[1];
                values[1] = data[2].replaceAll("'", "''");
                values[2] = data[3].replaceAll("'", "''");
                values[3] = data[4];
                theOne.mItemsDB.createRecord(
                        Arrays.copyOfRange(ITEM_FIELD_NAMES, 1, ITEM_FIELD_NAMES.length), values);
                
                //System.out.println("Controller Line : " + Arrays.toString(values));
                recordsCreated++;
            }

            cin.close();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
            return 0;
        } finally {
        	if (cin != null)
        		cin.close();
        }
        return recordsCreated;
    }
}