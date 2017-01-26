package riksasuviana.apps.meetme;

/**
 * Created by riksasuviana on 18/01/17.
 */

public class FriendProfile {
    private String key;
    private String name;
    private String photo;

    public FriendProfile(String name, String photo, String key){
        this.setName(name);
        this.setPhoto(photo);
        this.setKey(key);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
