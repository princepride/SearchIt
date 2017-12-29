import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;


public class JDBC {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/vocabulary?user=root&password=wzp971117");
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void outPutTable() {
		try {
			String string="";
			String sql="select * from vocabulary";
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				string+=rs.getString("Vocabulary")+"\t"+rs.getString("Interpretation");
				string+="\r\n";
			}
//			File file=new File("C:\\Users\\PrincePride\\Desktop\\vocabulary table.txt");
			File file2=new File("C:\\Users\\PrincePride\\Documents\\vocabulary table.txt");
//				FileWriter fileWriter=new FileWriter(file);
				FileWriter fileWriter2=new FileWriter(file2);
//				fileWriter.write(string);
				fileWriter2.write(string);
//				fileWriter.close();
				fileWriter2.close();
//				if(!file.exists()) {
//					file.createNewFile();
//				}
				if(!file2.exists()) {
					file2.createNewFile();
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void insert(Vocabulary vocabulary) {
		String sql="insert into vocabulary(Vocabulary,Interpretation,DetailExplanation) values (?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt=(PreparedStatement)conn.prepareStatement(sql);
			pstmt.setString(1, vocabulary.vocabulary);
			pstmt.setString(2, vocabulary.interpretation);
			pstmt.setString(3, vocabulary.detailExplanation);
			pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Vocabulary select(String string) {
		String sql="select * from vocabulary where vocabulary like '"+string+"'";
		Vocabulary vocabulary=new Vocabulary("","","");
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rSet=stmt.executeQuery(sql);
			if(rSet.next()) {
			vocabulary.vocabulary=string;
			vocabulary.interpretation=rSet.getString("Interpretation");
			vocabulary.detailExplanation=rSet.getString("DetailExplanation");
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vocabulary;
	}
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
