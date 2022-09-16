package dao;

import model.Talher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class TalherDAO extends DAO {	
	public TalherDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Talher talher) {
		boolean status = false;
		try {
			String sql = "INSERT INTO talher (descricao, preco, quantidade, datafabricacao, datavalidade) "
		               + "VALUES ('" + talher.getDescricao() + "', "
		               + talher.getPreco() + ", " + talher.getQuantidade() + ", ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(talher.getDataFabricacao()));
			st.setDate(2, Date.valueOf(talher.getDataValidade()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Talher get(int id) {
		Talher talher = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM talher WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 talher = new Talher(rs.getInt("id"), rs.getString("descricao"), (float)rs.getDouble("preco"), 
	                				   rs.getInt("quantidade"), 
	        			               rs.getTimestamp("datafabricacao").toLocalDateTime(),
	        			               rs.getDate("datavalidade").toLocalDate());
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return talher;
	}
	
	
	public List<Talher> get() {
		return get("");
	}

	
	public List<Talher> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Talher> getOrderByDescricao() {
		return get("descricao");		
	}
	
	
	public List<Talher> getOrderByPreco() {
		return get("preco");		
	}
	
	
	private List<Talher> get(String orderBy) {
		List<Talher> talhers = new ArrayList<Talher>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM talher" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Talher p = new Talher(rs.getInt("id"), rs.getString("descricao"), (float)rs.getDouble("preco"), 
	        			                rs.getInt("quantidade"),
	        			                rs.getTimestamp("datafabricacao").toLocalDateTime(),
	        			                rs.getDate("datavalidade").toLocalDate());
	            talhers.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return talhers;
	}
	
	
	public boolean update(Talher talher) {
		boolean status = false;
		try {  
			String sql = "UPDATE talher SET descricao = '" + talher.getDescricao() + "', "
					   + "preco = " + talher.getPreco() + ", " 
					   + "quantidade = " + talher.getQuantidade() + ","
					   + "datafabricacao = ?, " 
					   + "datavalidade = ? WHERE id = " + talher.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(talher.getDataFabricacao()));
			st.setDate(2, Date.valueOf(talher.getDataValidade()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM talher WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}