package riksasuviana.apps.meetme;

/**
 * Created by riksasuviana on 18/01/17.
 */

public class Registration {
    private String name;
    private String photo;
    private String email;

    public Registration(String name, String photo, String email){
        this.setName(name);
        this.setPhoto(photo);
        this.setEmail(email);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
