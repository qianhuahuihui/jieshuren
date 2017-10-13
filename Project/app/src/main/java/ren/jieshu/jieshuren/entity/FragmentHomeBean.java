package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laomaotao on 2017/6/30.
 */

public class FragmentHomeBean implements Serializable {

    private List<Images> imagesList;
    private List<BooksBean> booksList;
    private List<ButtonsBean> buttonsList;

    public List<Images> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    public List<BooksBean> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<BooksBean> booksList) {
        this.booksList = booksList;
    }

    public List<ButtonsBean> getButtonsList() {
        return buttonsList;
    }

    public void setButtonsList(List<ButtonsBean> buttonsList) {
        this.buttonsList = buttonsList;
    }
}
