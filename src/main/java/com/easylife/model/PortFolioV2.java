package com.easylife.model;

import java.util.Collection;

/**
 * Created by cpe on 28/05/2015.
 */
public class PortFolioV2 {

    public UserList getUserList(PreDefinedListName listName) {
        return bridge.findUserList(getUserId(), listName.toString());
    }

    public enum PreDefinedListName {
        maybe,todo,done ;
    }

    private final DynamoDocumentBridge bridge = DynamoDocumentBridge.getInstance() ;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void printListe() {
        final Collection<UserList> userListes = bridge.findUserList(userId);
        for(UserList userList : userListes) {
            System.out.println(userList) ;
        }
    }

    public void maybe(String content) {
        newItem(PreDefinedListName.maybe.name(), new UserIntent().withContent(content));
    }



    public void newItem(String listeName, UserIntent intent) {
        UserList list = bridge.findUserList(userId, listeName) ;
        if(list==null) list = new UserList().withListeName(listeName).withUserId(getUserId()) ;
        list.getListe().add(intent) ;
        bridge.save(list) ;
    }

    /**
     * // Batch ? Transaction ? TODO
     * @param listeName
     * @param index
     * @param listedest
     * @return
     */
    public boolean move(String listeName, int index, String listedest) {
        UserList list = bridge.findUserList(getUserId(), listeName) ;
        if(list==null) return false ;
        UserIntent content = list.getListe().remove(index) ;
        System.out.println(list) ;
        bridge.save(list) ;
        newItem(listedest, content);
        return true ;
    }

    public void setUserId(String userId) {
        this.userId = userId ;
    }
}
