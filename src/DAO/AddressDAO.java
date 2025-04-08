package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.Ward;
import DTO.District;
import DTO.City;
import Database.connection;

public class AddressDAO {
	public static ArrayList<City> getList() {
		Connection conn = connection.getConnection();
		ArrayList<City> list = new ArrayList<>();
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from TINHTHANHPHO order by tenTinhThanhPho");
			while(rs.next()) {
				
				City x = new City();
				x.setCityName(rs.getString("tenTinhThanhPho"));
				Statement st1 = conn.createStatement();
				ResultSet rs1 = st1.executeQuery("select * from QUANHUYEN where maTinhThanhPho = '"+rs.getString(1)+"' order by tenQuanHuyen");
				while(rs1.next()) {
					District qh = new District();
					qh.setDistrictName(rs1.getString("tenQuanHuyen"));
					
					Statement st2 = conn.createStatement();
					ResultSet rs2 = st2.executeQuery("select * from PHUONGXA where maQuanHuyen = '"+rs1.getString(1)+"' order by tenPhuongXa");
					while(rs2.next()) {
						Ward px =new Ward();
						px.setWardName(rs2.getString("tenPhuongXa"));
						Statement st3 = conn.createStatement();
						ResultSet rs3 = st3.executeQuery("select * from DUONG where maPhuongXa = '"+rs2.getString(1)+"' order by tenDuong");
						while(rs3.next()) {
							px.getStreetList().add(rs3.getString("tenDuong"));
						}
						qh.getWardList().add(px);
					}
					x.getDistrictList().add(qh);
					
				}
				list.add(x);
				
			}
			connection.closeConnection(conn);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}