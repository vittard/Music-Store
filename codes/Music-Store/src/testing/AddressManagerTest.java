package testing;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import entities.Address;
import entities.User;
import manager.AddressManager;
import manager.DriverManagerConnectionPool;

public class AddressManagerTest {

	@Test
	public void test() throws SQLException {

		Address a = new Address(3, "Libera", "Trentino", "Via dei Fiorentini, 130","Quarto","NA", "80010","Italia","03496597850", 6);
		Address a2 = new Address(1, "Priscilla", "Marcelo", "Via Gaetano Donizetti, 130","Costalunga Brognoligo","VR", "37030","Italia","03631896164", 4);

		AddressManager am = new AddressManager(new DriverManagerConnectionPool());
		
		//doRetrieveByKey
		Address a1 = (Address)am.doRetrieveByKey("3");

		assertEquals(true,a.equals(a1));

		//doRetrieveAll
		ArrayList<Address> list = (ArrayList<Address>) am.doRetrieveAll("id");

		assertEquals(true, list.get(0).equals(a2));
		assertEquals(true,list.get(2).equals(a));

		//doSave
		Address add= new Address(214, "Filippo", "Galati", "Via Emanuela S.C., 6","Fisciano","SA", "84084","Italia","03631896164", 3);
		am.doSave(add);
		
		Address add1 = (Address)am.doRetrieveByKey("214");
		
		assertEquals(true, add.equals(add1));
		
		//doUpdate
		Address a3 = new Address(1, "Giovanni", "Mucciaccia", "Via Gaetano Donizetti, 130","Costalunga Brognoligo","VR", "37030","Italia","03631896164", 3);
		am.doUpdate(a3);

		Address a4 = (Address)am.doRetrieveByKey("1");

		assertEquals(true, a4.equals(a3));

		//doDelete
		Address a5 = (Address)am.doRetrieveByKey("1");
		am.doDelete(a5);
		Address aEl = (Address)am.doRetrieveByKey("1");
		assertNull(aEl);

		//doRetrieveByUser
		User user = new User(7, "Mirella", "Boni", "MirellaBoni@teleworm.us", 0);
		ArrayList<Address> ad = (ArrayList<Address>) am.doRetrieveByUser(user, "id");

		for(Address address : ad) {
			assertEquals(true,address.getUserId() == user.getId());
			System.out.println(address.toString());

		}
	}

}
