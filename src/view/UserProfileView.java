package view;

import model.User;
import util.constants.WindowConstants;

public class UserProfileView extends BaseFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserProfileView(User user) {
		super(WindowConstants.USER_PROFILE_WINDOW_TITLE_PREFIX + user.getUsername());
		// TODO Auto-generated constructor stub
	}

	@Override
	void initializeFrame() {
		// TODO Auto-generated method stub
		
	}

}
