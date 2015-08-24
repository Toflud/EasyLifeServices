package com.easylife;

import com.easylife.model.DynamoDocumentBridge;
import com.easylife.model.PortFolioV2;
import com.easylife.model.UserList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;

/**
 * Created by cpe on 25/05/2015.
 */
public class DynamoDocumentBridgeTest {

    DynamoDocumentBridge bridge = DynamoDocumentBridge.getInstance() ;

    @BeforeMethod
    public void setUp() throws Exception {
    }

    @Test
    public void testCreateTable() throws Exception {
        bridge.deleteTable() ;
        bridge.createTable();
    }

    @Test
    public void testPutJson() throws Exception {
        bridge.putItemJSON();
        bridge.scan();
    }

    @Test
    public void testPutPOJO() throws Exception {
        bridge.putItemPOJO();
        bridge.scan();
    }

    @Test
    public void testGetJsonFromPOJO() throws Exception {
        bridge.putItemPOJO();
        bridge.getJsonFromPojo() ;
    }

    @Test
    public void testGetAllListFromUser() throws Exception {
        final Collection<UserList> userList = bridge.findUserList("Christophe");
        for(UserList ul : userList) {
            System.out.println(ul);
        }
    }

    @Test
    public void testPortFolioV2() throws Exception {
        PortFolioV2 portfolio = new PortFolioV2() ;
        portfolio.setUserId("Christophe") ;
        portfolio.printListe();
        portfolio.maybe("Maybe test V2");
        boolean moved = portfolio.move(PortFolioV2.PreDefinedListName.maybe.name(), 0, PortFolioV2.PreDefinedListName.todo.name()) ;
        if(!moved) System.err.println("Failed to moved");
        bridge.scan();
    }

    @Test
    public void testAddItem() throws Exception {
        PortFolioV2 portfolio = new PortFolioV2() ;
        portfolio.setUserId("Christophe") ;
        portfolio.printListe();
        portfolio.done("Already Done");
        portfolio.maybe("Could be") ;
    }
}
