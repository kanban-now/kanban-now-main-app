package main.archivedcardserviceclient;

//import lombok.Data;

//@Data
public class Card {

    private String text;
    private long boardId;
    private long id;
    private String date;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
