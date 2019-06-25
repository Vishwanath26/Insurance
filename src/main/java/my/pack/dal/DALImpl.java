package my.pack.dal;

import io.swagger.models.auth.In;
import my.pack.model.User;
import my.pack.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Repository
@Service
public class DALImpl implements DAL {

    private MongoTemplate mongoTemplate;

    @Autowired
    public DALImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired
    User user;

    @Override
    public List<User> getAllUsers() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public User getUserById(String userId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new Exception("No user found Exception");
        }
        return user;
    }

    @Override
    public UserDetails getUserDetails(String userId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new Exception("No User found Exception");
        }
        return user.getUserDetails();
    }

    @Override
    public User addNewUser(User user) {
        mongoTemplate.save(user);
        return user;
    }

    @Override
    public void deleteUserById(String userId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new Exception("No User found Exception");
        }
        mongoTemplate.remove(query, User.class);
    }

    @Override
    public int getUserBalance(String userId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new Exception("No user found Exception");
        }
        return user.getBalance();

    }

    @Override
    public int updateBalance(String userId, int amount, int type) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new Exception("No User found Exception");
        }
        user.setBalance(amount, type);
        mongoTemplate.save(user);
        return user.getBalance();
    }

    @Override
    public HashMap<String, HashMap<String, Integer>> getPolicies(String userId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new Exception("No User found Exception");
        }
        return user.getPolicies();
    }

    @Override
    public HashMap<String, HashMap<String, Integer>> addPolicy(String userId, String insuranceType, String insurer, int amount) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new Exception("No User found Exception");
        }
        user.setBalance(amount, '-');
        user.addPolicy(insuranceType, insurer, amount);
        mongoTemplate.save(user);
        return user.getPolicies();
    }

    @Override
    public void changeCrossSell(String userId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new Exception("No user found Exception");
        }
        HashMap<String, HashMap<String, Integer>> allPolicies = new HashMap<>();
        HashMap<String, Integer> insType = new HashMap<>();
        insType.put("LIFE", 20);
        insType.put("HEALTH", 40);
        insType.put("CYBER", 60);
        allPolicies.put("ICICI", insType);
        allPolicies.put("BAJAJ", insType);
        allPolicies.put("AEGON", insType);
        HashMap<String, HashMap<String, Integer>> userList = user.getPolicies();
        if (CollectionUtils.isEmpty(userList)) {
            user.setCrossSellInsurer("ICICI");
            user.setCrossSellInsuranceType("LIFE");
        }
        for (String insurer : allPolicies.keySet()) {
            if (userList.containsKey(insurer)) {
                for (String insuranceType : allPolicies.get(insurer).keySet()) {
                    if (!(userList.values().contains(insuranceType))) {
                        user.setCrossSellInsurer(insurer);
                        user.setCrossSellInsuranceType(insuranceType);
                        mongoTemplate.save(user);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public String getCrossSellInsurer(String userId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new Exception("No User found Exception");
        }
        return user.getCrossSellInsurer();
    }

    @Override
    public String getCrossSellInsuranceType(String userId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        if (user == null) {
            throw new Exception("No User found Exception");
        }
        return user.getCrossSellInsuranceType();
    }

    @Override
    public void deletePolicy(String userId, String insurer, String insuranceType) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        user = mongoTemplate.findOne(query, User.class);
        user.deletePolicy(insurer, insuranceType);
        mongoTemplate.save(user);
    }

}
