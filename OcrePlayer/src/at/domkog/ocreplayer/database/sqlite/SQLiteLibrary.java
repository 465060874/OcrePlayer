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
				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Path TEXT NOT NULL UNIQUE"
				+ attributes.toString() + ");");
		
		executeStatement("CREATE TABLE IF NOT EXISTS folders (path TEXT PRIMARY KEY);");
	}
	
	@Override
	public ArrayList<LibraryEntry> loadLibrary() {
		ArrayList<LibraryEntry> result = new ArrayList<>();
		PreparedStatement pre = prepareStatement("SELECT * FROM library;");
		
		try {
			ResultSet rs = pre.executeQuery();
			
			while(rs.next()) {
				LibraryEntry entry = new LibraryEntry();
				for(Attribute a: Attribute.values()) {
					entry.setAttribute(a, rs.getString(a.toString()));
				}
				result.add(entry);
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
				LibraryEntry entry = new LibraryEntry();
				for(Attribute a: Attribute.values()) {
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
	public int getID(String path) {
		int result = -1;
		PreparedStatement pre = prepareStatement("SELECT * FROM library WHERE Path = ?;");

		try {
			pre.setString(1, path);
			
			ResultSet rs = pre.executeQuery();

			while(rs.next()) {
				result = rs.getInt("ID");
			}

			rs.close();
			pre.close();
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
		boolean first = true;
		for(int i = 0; i < attributes.length; i++) {
			if(attributes[i] == Attribute.ID) continue;
			if(first) {
				attr.append(attributes[i].toString());
				values.append("?");
				first = false;
			} else {
				attr.append(", " + attributes[i].toString());
				values.append(", ?");
			}
		}
		
		PreparedStatement pre = prepareStatement("INSERT INTO library(" + attr.toString() + ") VALUES(" + values.toString() + ");");

		try {
			for(int i = 0; i < attributes.length; i++) {
				Attribute a = attributes[i];
				if(a == Attribute.ID) continue;
				else if(a == Attribute.PATH) pre.setString((i), OcrePlayer.pathMode.createPath(new File(entry.getPath())));
				else pre.setString((i), entry.getAttribute(a));
			}
			
			execute(pre);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		
		entry.setAttribute(Attribute.ID, String.valueOf(this.getID(entry.getPath())));
	}

	@Override
	public void remove(LibraryEntry entry) {
		PreparedStatement pre = this.prepareStatement("DELETE FROM library WHERE ID = ?;");
		
		try {
			pre.setInt(1, entry.getID());
			pre.execute();
			
			pre.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	@Override
	public ArrayList<String> loadFolders() {
		ArrayList<String> result = new ArrayList<String>();
		
		PreparedStatement pre = prepareStatement("SELECT * FROM folders;");
		
		try {
			ResultSet rs = pre.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getString("path"));
			}
			
			rs.close();
			pre.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void addFolder(String path) {
		PreparedStatement pre = prepareStatement("INSERT INTO folders(path) VALUES(?);");
		
		try {
			pre.setString(1, path);
			pre.execute();
			
			pre.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeFolder(String path) {
		PreparedStatement pre = prepareStatement("DELETE FROM folders WHERE path = ?;");
		
		try {
			pre.setString(1, path);
			pre.execute();
			
			pre.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
