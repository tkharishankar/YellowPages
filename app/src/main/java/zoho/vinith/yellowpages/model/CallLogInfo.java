package zoho.vinith.yellowpages.model;

public class CallLogInfo {

    private String Id;
    private String Name;
    private String Phone_Number;
    private String Photo;
    private String Call_Type;
    private String Call_Duration;

    public CallLogInfo(String name, String phone_Number, String photo,String call_Type,String call_Duration) {
        this.Name = name;
        this.Phone_Number = phone_Number;
        this.Photo = photo;
        this.Call_Type = call_Type;
        this.Call_Duration = call_Duration;
    }

    public String getCall_Type() {
        return this.Call_Type;
    }

    public void setCall_Type(String call_Type) {
        this.Call_Type = call_Type;
    }

    public String getCall_Duration() {
        return this.Call_Duration;
    }

    public void setCall_Duration(String call_Duration) {
        this.Call_Duration = call_Duration;
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
