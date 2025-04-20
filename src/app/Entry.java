package app;

public class Entry {

    private  static int identificationNumber = 1;
    private String title;
    private String body;

    public Entry(String title, String body){
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (body == null || body.isEmpty()) {
            throw new IllegalArgumentException("Body cannot be empty");
        }
      //  this.identificationNumber = identificationNumber++;
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getIdentificationNumber() {
        return identificationNumber++;
    }

    public void setTitle(String title) {
    }

    public void setBody(String content) {
    }
}
