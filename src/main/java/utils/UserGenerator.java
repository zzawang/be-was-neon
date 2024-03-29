package utils;

import java.io.UnsupportedEncodingException;
import manager.RequestManager;
import model.LoginUser;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserGenerator {
    private static final int ID_INDEX = 0;
    private static final int PW_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int EMAIL_INDEX = 3;
    private static final Logger logger = LoggerFactory.getLogger(UserGenerator.class);
    private final RequestManager requestManager;

    public UserGenerator(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public User createUser() {
        String[] userEncodedInfo = requestManager.extractUser();
        String[] userInfo = decodeUserInfo(userEncodedInfo);
        User user = generateUserObj(userInfo);
        logger.info(user.toString());

        return user;
    }

    private String[] decodeUserInfo(String[] userEncodedInfo) {
        try {
            return Decoder.decodeUser(userEncodedInfo);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return new String[0];
    }

    private User generateUserObj(String[] userInfo) {
        String id = userInfo[ID_INDEX];
        String pw = userInfo[PW_INDEX];
        String name = userInfo[NAME_INDEX];
        String email = userInfo[EMAIL_INDEX];
        return new User(id, pw, name, email);
    }

    public LoginUser createLoginUser() {
        String[] userEncodedInfo = requestManager.extractUser();
        String[] userInfo = decodeUserInfo(userEncodedInfo);
        return new LoginUser(userInfo[ID_INDEX], userInfo[PW_INDEX]);
    }
}
