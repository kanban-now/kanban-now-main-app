package main.archivedcardserviceclient;

import java.util.List;

public class PagedArchivedCardList {

    private List<Card> data;
    private PagingData pagingData;

    public List<Card> getData() {
        return data;
    }

    public void setData(List<Card> data) {
        this.data = data;
    }

    public PagingData getPagingData() {
        return pagingData;
    }

    public void setPagingData(PagingData pagingData) {
        this.pagingData = pagingData;
    }
}
