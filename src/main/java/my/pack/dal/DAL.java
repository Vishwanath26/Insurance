package my.pack.dal;

import my.pack.model.User;
import my.pack.model.UserDetails;

import java.util.HashMap;
import java.util.List;

public interface DAL {
    List<User> getAllUsers();

    User getUserById(String userId) throws Exception;

    User addNewUser(User user);

    void deleteUserById(String userId) throws Exception;

    UserDetails getUserDetails(String userId) throws Exception;

    int getUserBalance(String userId) throws Exception;

    int updateBalance(String userId, int amount, int type) throws Exception;

    HashMap<String, HashMap<String, Integer>> getPolicies(String userId) throws Exception;

    HashMap<String, HashMap<String, Integer>> addPolicy(String userId, String insuranceType, String insurer, int amount) throws Exception;

    void changeCrossSell(String userId) throws Exception;

    String getCrossSellInsurer(String userId) throws Exception;

    String getCrossSellInsuranceType(String userId) throws Exception;

    void deletePolicy(String UserId, String insurer, String insuranceType) throws Exception;
}
