package com.easylife.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cpe on 28/05/2015.
 */
@DynamoDBTable(tableName = "UserList")
public class UserList {

    private String userId;
    private String listeName;
    private List<UserIntent> liste;

    @DynamoDBHashKey(attributeName="UserId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBRangeKey(attributeName="ListeName")
    public String getListeName() {
        return listeName ;
    }

    @DynamoDBAttribute(attributeName = "Liste")
    public List<UserIntent> getListe() {
        if(liste==null) liste = new ArrayList<>() ;
        return liste ;
    }

    public void setListe(List<UserIntent> liste) {
        this.liste = liste;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setListeName(String listeName) {
        this.listeName = listeName;
    }

    public UserList withUserId(String userId) {
        this.setUserId(userId);
        return this ;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer() ;
        sb.append("UserList("+userId+","+listeName+"):");
        sb.append(liste.toString()) ;
        return sb.toString() ;
    }

    public UserList withListeName(String listeName) {
        this.setListeName(listeName);
        return this ;
    }
}
