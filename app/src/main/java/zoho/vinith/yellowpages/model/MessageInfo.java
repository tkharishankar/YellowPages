package zoho.vinith.yellowpages.model;

public class MessageInfo {

    private String Name;
    private String Phone_Number;
    private String Time;
    private String Text;
    private Integer ID;

    public MessageInfo(){

    }

    public MessageInfo(Integer id, String name, String phone_Number, String text,String time) {
        this.ID = id;
        this.Name = name;
        this.Phone_Number = phone_Number;
        this.Text = text;
        this.Time = time;
    }

    public Integer getID() {
        return this.ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

    public String getTime() {
        return this.Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getText() {
        return this.Text;
    }

    public void setText(String text) {
        this.Text = text;
    }
}
