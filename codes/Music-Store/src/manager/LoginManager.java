package manager;

import entities.User;





public class LoginManager {

	User user;

	public LoginManager (User user) {
		if (user.isNull()) {
			this.user = null;
		} else 
			this.user = user;
	}

	public boolean checkClient() {

		if (user != null) {
			if (user.getType() == 0)
				return true;
		}
		return false;

	}


	public boolean checkOrderAdmin() {
		if (user != null) {
			if (user.getType() == 1)
				return true;
		}
		return false;
	}


	public boolean checkCatalogAdmin() {
		if (user != null) {
			if (user.getType() == 2)
				return true;
		}
		return false;
	}

	public boolean checkUserAdmin() {
		if (user != null) {
			if (user.getType() == 3)
				return true;
		}
		return false;
	}

	public boolean checkAdmin() {
		if (user != null) {
			if ( checkUserAdmin() || checkCatalogAdmin() || checkOrderAdmin() )
				return true;
		}
		return false;
	}











}
