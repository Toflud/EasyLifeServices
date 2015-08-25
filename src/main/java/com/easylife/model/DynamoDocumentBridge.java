package com.easylife.model;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by cpe on 25/05/2015.
 */
public class DynamoDocumentBridge {

    private final static DynamoDocumentBridge dynamoBridge = new DynamoDocumentBridge() ;
    private final AmazonDynamoDBClient client ;
    private final DynamoDBMapper mapper ;
    private final String yourAccessKeyId = "key2" ;
    private final DynamoDB dynamoDB ;

    public final static DynamoDocumentBridge getInstance() {
        return dynamoBridge ;
    }

    public DynamoDocumentBridge() {
        AWSCredentials credentials = new BasicAWSCredentials(yourAccessKeyId, "bogus2"); ;
        client = new AmazonDynamoDBClient(credentials);
        client.setEndpoint("http://localhost:8000");
        mapper = new DynamoDBMapper(client);
        dynamoDB = new DynamoDB(client) ;
    }

    public void createTable() {
        List<KeySchemaElement> keySchema = new ArrayList<>() ;
        keySchema.add(new KeySchemaElement("UserId", KeyType.HASH)) ;
        keySchema.add(new KeySchemaElement("ListeName", KeyType.RANGE)) ;

        List<AttributeDefinition> attributeDefinitions = new ArrayList<>() ;
        attributeDefinitions.add(new AttributeDefinition("UserId", ScalarAttributeType.S)) ;
        attributeDefinitions.add(new AttributeDefinition("ListeName",ScalarAttributeType.S)) ;

        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1L,1L) ;

        CreateTableRequest createTableRequest = new CreateTableRequest(attributeDefinitions, "UserList", keySchema, provisionedThroughput) ;
        client.createTable(createTableRequest) ;
    }

    public void deleteTable() {
        client.deleteTable("UserList") ;
    }

    public void putItemPOJO() {
        UserList userList = new UserList() ;
        userList.setListeName("POJO");
        userList.setUserId("Christophe");
        List<UserIntent> list = new ArrayList<>() ;
        list.add(new UserIntent().withContent("Call Friends")) ;
        list.add(new UserIntent().withContent("To this after")) ;
        userList.setListe(list);
        mapper.save(userList);
    }

    public void putItemJSON() {

        try {
            JSONArray list = new JSONArray() ;

            JSONObject uijson = new JSONObject() ;
            uijson.put("Content","Call Friends") ;
            JSONObject uijson2 = new JSONObject() ;
            uijson2.put("Content","To this after") ;

            list.put(uijson) ;
            list.put(uijson2) ;


            JSONObject doc = new JSONObject() ;

            doc.put("Liste",list) ;
            doc.put("UserId","Christophe") ;
            doc.put("ListeName","JSON") ;

            String json = doc.toString() ;
            Item item = Item.fromJSON(json) ;
            Table table = dynamoDB.getTable("UserList");

            table.putItem(item) ;

            System.out.println(item.toJSONPretty());

        } catch (JSONException e) {
            e.printStackTrace();
            return ;
        }

        //System.out.println(json) ;


        /*Item item = new Item()
                .withPrimaryKey("UserId","Christophe","ListeName","JSON")
                .withJSON("Liste",json) ;*/



    }

    public void scan() {
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("UserList");
        System.out.println("Scan List") ;
        ScanResult scanResult = client.scan(scanRequest);
        System.out.printf("Fetch %s item(s)%n", scanResult.getCount());
        for (Map<String, AttributeValue> item : scanResult.getItems()) {
            System.out.println(item) ;
        }
    }

    public void getJsonFromPojo() {
        GetItemSpec spec = new GetItemSpec()
                .withPrimaryKey("UserId", "Christophe", "ListeName", "POJO") ;
               // .withProjectionExpression("Id, Title, RelatedItems[0], Reviews.FiveStar")
               // .withConsistentRead(true);

        Table table = dynamoDB.getTable("UserList");
        Item item = table.getItem(spec);

        System.out.println(item.toJSONPretty());
    }

    public Collection<UserList> findUserList(String userId) {
        UserList ul = new UserList().withUserId(userId) ;

        DynamoDBQueryExpression<UserList> queryExpression = new DynamoDBQueryExpression<UserList>()
                .withHashKeyValues(ul) ;
                //.withConsistentRead(false) ; // Querying GSI require non consistent read

        return mapper.query(UserList.class, queryExpression) ;
    }

    public UserList findUserList(String userId, String listeName) {
        UserList ul = new UserList().withUserId(userId).withListeName(listeName) ;

        Condition listeNameCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(listeName));


        DynamoDBQueryExpression<UserList> queryExpression = new DynamoDBQueryExpression<UserList>()
                .withHashKeyValues(ul)
                .withRangeKeyCondition("ListeName", listeNameCondition) ;

        Collection<UserList> result = mapper.query(UserList.class, queryExpression) ;
        if(result.size()==1) return result.iterator().next() ;
        if(result.size()>1) throw new RuntimeException() ;
        return null ;
    }

    public void save(UserList list) {
        mapper.save(list);
    }


}
