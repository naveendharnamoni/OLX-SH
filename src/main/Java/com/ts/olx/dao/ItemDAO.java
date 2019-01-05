package com.ts.olx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mysql.jdbc.Statement;
import com.ts.olx.dto.Interest;
import com.ts.olx.dto.Item;
import com.ts.olx.dto.User;
@Component
public class ItemDAO {

	public Item insert(Item item) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement(
					"insert into item(description,min_price,max_price,subcategory_id,locality,city,state,country,postal_code,posted_date,sold,posted_by) values(?,?,?,?,?,?,?,?,?,?,0,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, item.getDescription());
			preparedStatement.setDouble(2, item.getMinPrice());
			preparedStatement.setDouble(3, item.getMaxPrice());
			preparedStatement.setInt(4, item.getSubCategory().getId());
			preparedStatement.setString(5, item.getLocality());
			preparedStatement.setString(6, item.getCity());
			preparedStatement.setString(7, item.getState());
			preparedStatement.setString(8, item.getCountry());
			preparedStatement.setInt(9, item.getPostalCode());
			preparedStatement.setDate(10, new java.sql.Date(item.getPostedDate().getTime()));
			preparedStatement.setInt(11, item.getPostedBy().getId());
			if (preparedStatement.executeUpdate() != 0) {
				
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					item.setId(rs.getInt(1));
				}
				return item;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public boolean updateApproverId(int approverId, int itemId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("update item set approved_by = ? where id = ?");
			preparedStatement.setInt(1, approverId);
			preparedStatement.setInt(2, itemId);
			if (preparedStatement.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updatePrice(int itemId, double minPrice, double maxPrice) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection
					.prepareStatement("update item set min_price = ?  and max_price = ? where id = ?");
			preparedStatement.setDouble(1, minPrice);
			preparedStatement.setDouble(2, maxPrice);
			preparedStatement.setInt(3, itemId);
			if (preparedStatement.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int itemId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("update item set sold = 1  where id = ?");
			preparedStatement.setInt(1, itemId);
			if (preparedStatement.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Item get(int itemId) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Item item = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select * from item where id = ?");
			preparedStatement.setInt(1, itemId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				item = new Item();
				item.setId(resultSet.getInt(1));
				item.setDescription(resultSet.getString(2));
				item.setMinPrice(resultSet.getDouble(3));
				item.setMaxPrice(resultSet.getDouble(4));
				item.setLocality(resultSet.getString(6));
				item.setCity(resultSet.getString(7));
				item.setState(resultSet.getString(8));
				item.setCountry(resultSet.getString(9));
				item.setPostalCode(resultSet.getInt(10));
				item.setPostedDate(resultSet.getDate(11));
				User user= new User();
				user.setId(resultSet.getInt(13));
				item.setPostedBy(user);
				if (resultSet.getInt(12) != 0) {
					item.setSold(true);
				} else {
					item.setSold(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public List<Item> getItems() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Item> itemsList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select * from item ");
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				itemsList = new ArrayList<Item>();
				do {
					Item item = new Item();
					item.setId(resultSet.getInt(1));
					item.setDescription(resultSet.getString(2));
					item.setMinPrice(resultSet.getDouble(3));
					item.setMaxPrice(resultSet.getDouble(4));
					item.setLocality(resultSet.getString(6));
					item.setCity(resultSet.getString(7));
					item.setState(resultSet.getString(8));
					item.setCountry(resultSet.getString(9));
					item.setPostalCode(resultSet.getInt(10));
					item.setPostedDate(resultSet.getDate(11));
					User user= new User();
					user.setId(resultSet.getInt(13));
					item.setPostedBy(user);
					if (resultSet.getInt(12) != 0) {
						item.setSold(true);
					} else {
						item.setSold(false);
					}
					itemsList.add(item);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemsList;
	}

	public List<Interest> getItemInterests(int itemId) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Interest> interestsList = null;

		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select * from interest where item_id = ?");
			preparedStatement.setInt(1, itemId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				interestsList = new ArrayList<Interest>();
				do {
					Interest interest = new Interest();
					interest.setExpressedDate(resultSet.getDate(4));
					interest.setBidAmount(resultSet.getDouble(3));
					interest.setExpressedBy(new UserDAO().get(resultSet.getInt(2)));
					if (resultSet.getInt(5) != 0) {
						interest.setBidStatus(true);
					} else {
						interest.setBidStatus(false);
					}
					interestsList.add(interest);
				} while (resultSet.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return interestsList;
	}

	public List<Item> getItemsOfCategory(int categoryId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Item> itemsList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement(
					"select item.* from item, category, subcategory where item.subcategory_id = subcategory.id and category.id = subcategory.category_id and category.id = ?");
			preparedStatement.setInt(1, categoryId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				itemsList = new ArrayList<Item>();
				do {
					Item item = new Item();
					item.setId(resultSet.getInt(1));
					item.setDescription(resultSet.getString(2));
					item.setMinPrice(resultSet.getDouble(3));
					item.setMaxPrice(resultSet.getDouble(4));
					item.setLocality(resultSet.getString(6));
					item.setCity(resultSet.getString(7));
					item.setState(resultSet.getString(8));
					item.setCountry(resultSet.getString(9));
					item.setPostalCode(resultSet.getInt(10));
					item.setPostedDate(resultSet.getDate(11));
					User user= new User();
					user.setId(resultSet.getInt(13));
					item.setPostedBy(user);
					if (resultSet.getInt(12) != 0) {
						item.setSold(true);
					} else {
						item.setSold(false);
					}
					itemsList.add(item);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemsList;

	}

	public List<Item> getItemsOfSubCategory(int subCategoryId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Item> itemsList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement(
					"select item.* from item, subcategory where item.subcategory_id = subcategory.id and subcategory.id = ? ");
			preparedStatement.setInt(1, subCategoryId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				itemsList = new ArrayList<Item>();
				do {
					Item item = new Item();
					item.setId(resultSet.getInt(1));
					item.setDescription(resultSet.getString(2));
					item.setMinPrice(resultSet.getDouble(3));
					item.setMaxPrice(resultSet.getDouble(4));
					item.setLocality(resultSet.getString(6));
					item.setCity(resultSet.getString(7));
					item.setState(resultSet.getString(8));
					item.setCountry(resultSet.getString(9));
					item.setPostalCode(resultSet.getInt(10));
					item.setPostedDate(resultSet.getDate(11));
					User user= new User();
					user.setId(resultSet.getInt(13));
					item.setPostedBy(user);
					if (resultSet.getInt(12) != 0) {
						item.setSold(true);
					} else {
						item.setSold(false);
					}
					itemsList.add(item);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemsList;
	}

	public List<Item> getItemsPostedBy(int userId) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Item> itemsList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select item.* from item where posted_by = ? ");
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				itemsList = new ArrayList<Item>();
				do {
					Item item = new Item();
					item.setId(resultSet.getInt(1));
					item.setDescription(resultSet.getString(2));
					item.setMinPrice(resultSet.getDouble(3));
					item.setMaxPrice(resultSet.getDouble(4));
					item.setLocality(resultSet.getString(6));
					item.setCity(resultSet.getString(7));
					item.setState(resultSet.getString(8));
					item.setCountry(resultSet.getString(9));
					item.setPostalCode(resultSet.getInt(10));
					item.setPostedDate(resultSet.getDate(11));
					User user= new User();
					user.setId(resultSet.getInt(13));
					item.setPostedBy(user);
					if (resultSet.getInt(12) != 0) {
						item.setSold(true);
					} else {
						item.setSold(false);
					}
					itemsList.add(item);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemsList;
	}

	public List<String> getImages(int itemId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> imagesList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select image from item_image where item_id = ?");
			preparedStatement.setInt(1, itemId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				imagesList = new ArrayList<String>();
				do {
					imagesList.add(resultSet.getString(1));
				} while (resultSet.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtility.close(resultSet, preparedStatement, connection);
		}
		return imagesList;
	}

	
	public boolean saveImage(int id, String image) {
		System.out.println("SaveImage in DAO");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		connection = DAOUtility.getConnection();
		try {
			preparedStatement = connection.prepareStatement("insert into item_image(item_id,image) values(?,?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2,image);
			if (preparedStatement.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DAOUtility.close(preparedStatement, connection);
		}

		return false;

	}
	public boolean updateBidAmount(int itemId,double bidAmount,int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("update interest set bid_amount=? where item_id=? and user_id=?");
			preparedStatement.setDouble(1, bidAmount);
			preparedStatement.setInt(2, itemId);
			preparedStatement.setInt(3, userId);
			if (preparedStatement.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public Interest insertBidAmount(Interest interest ) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement(
					"insert into interest(item_id,user_id,bid_amount,expressed_date) values(?,?,?,?)");
			preparedStatement.setInt(1, interest.getItem().getId());
			preparedStatement.setInt(2, interest.getExpressedBy().getId());
			preparedStatement.setDouble(3, interest.getBidAmount());
			preparedStatement.setDate(4, new java.sql.Date(interest.getExpressedDate().getTime()));
			System.out.println(interest.getExpressedBy().getId());
			System.out.println(interest.getItem().getId());
			
			if (preparedStatement.executeUpdate() != 0) {
				System.out.println("inside execute");
				return interest;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DAOUtility.close(preparedStatement, connection);
		}
		return interest;
	}
	public List<Item> getItemsInterestedByUser(int userId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Item> itemsList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement(
					" select * from item where id in(select item_id from interest where user_id = ?) ");
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				itemsList = new ArrayList<Item>();
				do {
					Item item = new Item();
					item.setId(resultSet.getInt(1));
					item.setDescription(resultSet.getString(2));
					item.setMinPrice(resultSet.getDouble(3));
					item.setMaxPrice(resultSet.getDouble(4));
					item.setLocality(resultSet.getString(6));
					item.setCity(resultSet.getString(7));
					item.setState(resultSet.getString(8));
					item.setCountry(resultSet.getString(9));
					item.setPostalCode(resultSet.getInt(10));
					item.setPostedDate(resultSet.getDate(11));
					item.setPostedBy(new UserDAO().get(resultSet.getInt(13)));
					if (resultSet.getInt(12) != 0) {
						item.setSold(true);
					} else {
						item.setSold(false);
					}
					itemsList.add(item);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemsList;
	}
	public Interest getInterestDetailsOfItem(int userId,int itemId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Interest interest = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select * from interest where item_id=? and user_id=?");
			preparedStatement.setInt(1, itemId);
			preparedStatement.setInt(2, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				interest = new Interest();
				/*Item item= new Item();
				item.setId(resultSet.getInt(1));
				interest.setItem(item);*/
			/*	User user=new User();
				user.setId(resultSet.getInt(2));
				interest.setExpressedBy(user);*/
				interest.setBidAmount(resultSet.getInt(3));
				interest.setExpressedDate(resultSet.getDate(4));
				
				if (resultSet.getInt(5) != 0) {
					interest.setBidStatus(true);
				} else {
					interest.setBidStatus(false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return interest;
	}
	public boolean updateBidStatus(int itemId,int userId,int status) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("update interest set bid_status = ? where item_id=? and user_id=?");
			preparedStatement.setInt(1, status);
			preparedStatement.setInt(2, itemId);
			preparedStatement.setInt(3, userId);
			
			if (preparedStatement.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public List<Item> getItemsWithNoApprovals() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Item> itemsList = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("select * from item where approved_by is null");
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				itemsList = new ArrayList<Item>();
				do {
					Item item = new Item();
					item.setId(resultSet.getInt(1));
					item.setDescription(resultSet.getString(2));
					item.setMinPrice(resultSet.getDouble(3));
					item.setMaxPrice(resultSet.getDouble(4));
					item.setLocality(resultSet.getString(6));
					item.setCity(resultSet.getString(7));
					item.setState(resultSet.getString(8));
					item.setCountry(resultSet.getString(9));
					item.setPostalCode(resultSet.getInt(10));
					item.setPostedDate(resultSet.getDate(11));
					item.setPostedBy(new UserDAO().get(resultSet.getInt(13)));
					if (resultSet.getInt(12) != 0) {
						item.setSold(true);
					} else {
						item.setSold(false);
					}
					itemsList.add(item);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemsList;
	}

	public boolean approveItemStatus(int itemId, int moderatorId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("update item set approved_by = ? where id= ? ");
			preparedStatement.setInt(1, moderatorId);
			preparedStatement.setInt(2, itemId);
			
			if (preparedStatement.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean rejectItemStatus(int itemId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DAOUtility.getConnection();
			preparedStatement = connection.prepareStatement("delete from item where id= ? ");
			preparedStatement.setInt(1, itemId);
			
			if (preparedStatement.executeUpdate() != 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	

}