package at.domkog.ocreplayer.database.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import at.domkog.ocreplayer.OcrePlayer;
import at.domkog.ocreplayer.database.LibraryDatabase;
import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.libary.LibraryEntry.Attribute;

public class SQLiteLibrary implements LibraryDatabase {

	private Connection con;
	
	private ExecutorService executor;
	
	public SQLiteLibrary() {
		try {
			Class.forName("org.sqlite.JDBC");
			File db = new File(OcrePlayer.workingDir.getAbsolutePath(), "/Library/");
			if(!db.exists()) db.mkdirs();
			db = new File(db, "library.db");
			if(!db.exists()) db.createNewFile();
			this.con = DriverManager.getConnection("jdbc:sqlite:" + db.getAbsolutePath());
		} catch ( Exception e ) {
			e.printStackTrace();
			return;
		}
		System.out.println("Opened database successfully");
		
		executor = Executors.newCachedThreadPool();
		
		this.createTable();
	}
	
	private void createTable() {
		StringBuilder attributes = new StringBuilder();
		for(Attribute a: Attribute.values()) {
			if(a == Attribute.ID || a == Attribute.PATH) continue;
			attributes.append("," + a.toString() + " TEXT");
		}
		
		executeStatement("CREATE TABLE IF NOT EXISTS library ("
				+ "ID INT NOT NULL PRIMARY KEY,"
				+ "Path TEXT"
				+ attributes.toString() + ");");
	}
	
	@Override
	public ArrayList<LibraryEntry> loadLibrary() {
		ArrayList<LibraryEntry> result = new ArrayList<>();
		PreparedStatement pre = prepareStatement("SELECT * FROM library;");
		
		try {
			ResultSet rs = pre.executeQuery();
			
			while(rs.next()) {
				LibraryEntry entry = new LibraryEntry(String.valueOf(rs.getInt("ID")), rs.getString("Path"));
				for(Attribute a: Attribute.values()) {
					if(a == Attribute.ID || a == Attribute.PATH) continue;
					entry.setAttribute(a, rs.getString(a.toString()));
				}
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public ArrayList<LibraryEntry> search(Attribute attribute, String key) {
		ArrayList<LibraryEntry> result = new ArrayList<>();
		PreparedStatement pre = prepareStatement("SELECT * FROM library WHERE " + attribute.toString() + " LIKE ?;");
		try {
			pre.setString(1, "%" + key + "%");
			
			ResultSet rs = pre.executeQuery();
			
			while(rs.next()) {
				LibraryEntry entry = new LibraryEntry(String.valueOf(rs.getInt("ID")), rs.getString("Path"));
				for(Attribute a: Attribute.values()) {
					if(a == Attribute.ID || a == Attribute.PATH) continue;
					entry.setAttribute(a, rs.getString(a.toString()));
				}
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void add(LibraryEntry entry) {
		StringBuilder attr = new StringBuilder();
		StringBuilder values = new StringBuilder();
		Attribute[] attributes = Attribute.values();
		int idIndex = 0;
		int pathIndex = 0;
		for(int i = 0; i < attributes.length; i++) {
			if(attributes[i] == Attribute.ID) idIndex = i;
			if(attributes[i] == Attribute.PATH) pathIndex = i;
			if(i == 0) {
				attr.append(attributes[i].toString());
				values.append("?");
			} else {
				attr.append(", " + attributes[i].toString());
				values.append(", ?");
			}
		}
		PreparedStatement pre = prepareStatement("INSERT INTO library(" + attr.toString() + ") VALUES(" + values.toString() + ");");
		

		try {
			for(int i = 1; i <= attributes.length; i++) {
				if(i == (idIndex + 1)) pre.setInt(i, Integer.valueOf(entry.getID()));
				else if(i == (pathIndex + 1)) pre.setString(i, OcrePlayer.pathMode.createPath(new File(entry.getPath())));
				else pre.setString(i, entry.getAttribute(attributes[i - 1]));
			}
			
			execute(pre);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(LibraryEntry entry) {
		
	}

	@Override
	public void update(LibraryEntry entry) {
		
	}

	@Override
	public boolean contains(String path) {
		PreparedStatement pre = prepareStatement("SELECT (count(*) > 0) as found FROM library WHERE Path = ?;");
		try {
			pre.setString(1, path);
			ResultSet rs = pre.executeQuery();
			
			boolean result;
			
            if (rs.next()) {
            	result = rs.getBoolean(1);
            } else result = false;
            
            rs.close();
            
            return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void executeStatement(String statement) {
		this.execute(this.prepareStatement(statement));
	}
	
	private PreparedStatement prepareStatement(String statement) {
		try {
			return this.con.prepareStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void execute(PreparedStatement pre) {
		executor.submit(() -> {
			try {
				pre.execute();
				pre.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void shutdown() {
		executor.shutdown();
	}

}
