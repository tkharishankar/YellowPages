package zoho.vinith.yellowpages.model;

public class ContactInfo {

    private String Id;
    private String Name;
    private String Phone_Number;
    private String Photo;

    public ContactInfo(String name, String phone_Number, String photo) {
        this.Name = name;
        this.Phone_Number = phone_Number;
        this.Photo = photo;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPhone_Number() {
        return this.Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        this.Phone_Number = phone_Number;
    }

    public String getPhoto() {
        return this.Photo;
    }

    public void setPhoto(String photo) {
        this.Photo = photo;
    }
}
