package zoho.vinith.yellowpages;

public class CustomContactClass {

    private String Id;
    private String Name;
    private String Phone_Number;
    private String Photo;

    public CustomContactClass(String id, String name, String phone_Number, String photo) {
        this.Id = id;
        this.Name = name;
        this.Phone_Number = phone_Number;
        this.Photo = photo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        this.Phone_Number = phone_Number;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        this.Photo = photo;
    }
}
