package riksasuviana.apps.meetme;

/**
 * Created by riksasuviana on 06/02/17.
 */

public class ChatList {
    String photo;
    String name;
    String chatkey;

    public String getChatkey() {
        return chatkey;
    }

    public void setChatkey(String chatkey) {
        this.chatkey = chatkey;
    }

    public ChatList(String photo, String name, String chatkey) {
        this.photo = photo;
        this.name = name;
        this.chatkey = chatkey;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
