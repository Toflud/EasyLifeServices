package com.easylife.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

/**
 * Created by cpe on 28/05/2015.
 */
@DynamoDBDocument
public class UserIntent {

    private String content ;

    @DynamoDBAttribute(attributeName = "Content")
    public String getContent() {
        return content ;
    }

    public UserIntent withContent(String s) {
        this.setContent(s);
        return this;
    }


    public void setContent(String content) {
        this.content = content;
    }
}
