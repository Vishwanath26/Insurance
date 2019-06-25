package my.pack.Controller;

import my.pack.dal.DAL;
import my.pack.model.Policy;
import my.pack.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DAL dal;

    @Autowired
    User user;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        logger.info("Getting all users");
        return dal.getAllUsers();
    }

    @RequestMapping(value = "/findUser/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Object getUserById(@PathVariable String userId) {
        logger.info("Getting User with Id {}.", userId);
        user = null;
        try {
            user = dal.getUserById(userId);
        } catch (Exception e) {
            if (e.getMessage().equals("No user found Exception"))
                return "Sorry user with userId: " + userId + " not found";
        }
        return user;
    }

    @RequestMapping(value = "/userDetails/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Object getUserDetails(String userId) {
        logger.info("Getting detials of user with userId: {}", userId);
        try {
            user = dal.getUserById(userId);
        } catch (Exception e) {
            e.getMessage().equals("No User found Exception");
            return "Sorry user with userId: " + userId + " not found";
        }
        return user.getUserDetails();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User addNewUser(@RequestBody User user) {
        logger.info("Creating new user");
        dal.addNewUser(user);
        return user;
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object deleteUserById(@PathVariable String userId) {
        logger.info("Deleting User with Id {}.", userId);
        try {
            dal.deleteUserById(userId);
        } catch (Exception e) {
            e.getMessage().equals("No User found exception");
            return "Sorry user with userId: " + userId + " not found";
        }
        return null;
    }

    @RequestMapping(value = "/getBalance/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Object getUserBalance(@PathVariable String userId) {
        logger.info("Getting balance of User with Id {}.", userId);
        try {
            user = dal.getUserById(userId);
        } catch (Exception e) {
            if (e.getMessage().equals("No User found Exception")) ;
            return "Sorry, user wih userId: " + userId + " not found.";
        }
        return user.getBalance();
    }

    @RequestMapping(value = "/updateBalance/{userId}/{amount}/{type}", method = RequestMethod.PUT)
    public @ResponseBody
    Object updateBalance(@PathVariable String userId, @PathVariable int amount, @PathVariable char type) throws Exception {
        logger.info("Updating balance of user with userId: {}.", userId);
        try {
            user = dal.getUserById(userId);
        } catch (Exception e) {
            if (e.getMessage().equals("No User found Exception")) ;
            return "Sorry, user wih userId: " + userId + " not found.";
        }
        return dal.updateBalance(userId, amount, type);
    }

    @RequestMapping(value = "/policies/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Object getPolicies(@PathVariable String userId) throws Exception {
        logger.info("Getting policies of user with userId: {}.", userId);
        try {
            user = dal.getUserById(userId);
        } catch (Exception e) {
            e.getMessage().equals("No User found Exception");
            return "Sorry, user wih userId: " + userId + " not found.";
        }
        return dal.getPolicies(userId);
    }

    @RequestMapping(value = "/addPolicy/{userId}", method = RequestMethod.PUT)
    public @ResponseBody
    Object addPolicy(@PathVariable String userId, @RequestBody Policy policy) throws Exception {
        logger.info("Updating policies of user with userId: {}", userId);
        try {
            user = dal.getUserById(userId);
        } catch (Exception e) {
            e.getMessage().equals("No User found Exception");
            return "Sorry user with userId: " + userId + " not found";
        }
        return dal.addPolicy(userId, policy.getInsuranceType(), policy.getInsurer(), policy.getAmount());
    }

    @RequestMapping(value = "/changeCrossSell/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Object changeCrossSell(@PathVariable String userId) throws Exception {
        logger.info("Changing crossSell of user with userId: {}", userId);
        try {
            user = dal.getUserById(userId);
        } catch (Exception e) {
            e.getMessage().equals("No User found Exception");
            return "Sorry user with userId: " + userId + "not found";
        }
        dal.changeCrossSell(userId);
        return null;
    }

    @RequestMapping(value = "/crossInsurer/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Object getCrossSellInsurer(@PathVariable String userId) throws Exception {
        logger.info("Getting CrossSell Insurer of user with userId: {}", userId);
        try {
            user = dal.getUserById(userId);
        } catch (Exception e) {
            e.getMessage().equals("No User found Exception");
            return "Sorry user with userId: " + userId + "not found";
        }
        return dal.getCrossSellInsurer(userId);
    }

    @RequestMapping(value = "/crossInsuranceType/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Object getCrossSellInsuranceType(@PathVariable String userId) throws Exception {
        logger.info("Getting CrossSell Insurer of user with userId: {}", userId);
        try {
            user = dal.getUserById(userId);
        } catch (Exception e) {
            e.getMessage().equals("No User found Exception");
            return "Sorry user with userId: " + userId + "not found";
        }
        return dal.getCrossSellInsuranceType(userId);
    }

    @RequestMapping(value = "/deletePolicy/{userId}/{insurer}/{insuranceType}", method = RequestMethod.DELETE)
    public void deletePolicy(@PathVariable String userId, @PathVariable String insurer, @PathVariable String insuranceType) throws Exception {
        logger.info("Deleting policy of user with userId: {}", userId);
        dal.deletePolicy(userId, insurer, insuranceType);
    }
}
