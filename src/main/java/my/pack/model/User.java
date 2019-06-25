package my.pack.model;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(value = "User")
public class User {

    @Id
    private String userId;
    private int balance;
    private HashMap<String, HashMap<String, Integer>> policies;
    private UserDetails userDetails;
    private String crossSellInsurer;
    private String crossSellInsuranceType;

    public String getCrossSellInsurer() {
        return crossSellInsurer;
    }

    public void setCrossSellInsurer(String crossSellInsurer) {
        this.crossSellInsurer = crossSellInsurer;
    }

    public String getCrossSellInsuranceType() {
        return crossSellInsuranceType;
    }

    public void setCrossSellInsuranceType(String crosseSellInsuranceType) {
        this.crossSellInsuranceType = crosseSellInsuranceType;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, HashMap<String, Integer>> getPolicies() {
        return policies;
    }

    public void addPolicy(String insuranceType, String insurer, int amount) {
        HashMap<String, Integer> newPolicy = new HashMap<>();
        if (this.policies.containsKey(insurer)) {
            newPolicy = this.policies.get(insurer);
            newPolicy.put(insuranceType, amount);
            this.policies.put(insurer, newPolicy);
        } else {
            newPolicy.put(insuranceType, amount);
            this.policies.put(insurer, newPolicy);
        }
    }

    public int getBalance() {
        return balance;
    }

    public int setBalance(int amount, int type) {
        if (type == '+') {
            this.balance = this.balance + amount;
        }
        if (type == '-') {
            this.balance = this.balance - amount;
        }
        return this.balance;
    }

    public void deletePolicy(String insurer, String insuranceType) {
        this.policies.get(insurer).remove(insuranceType);
    }

}
