package ren.jieshu.jieshuren.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by laomaotao on 2017/6/30.
 */

public class BooksBean implements Serializable {
    private Integer bookid;
    private Integer j_book_id;
    private String bookName;
    private String bookImage;
    private String bookAuthor;
    private String bookPress;
    private String bookPuttime;
    private String bookBind;
    private String isbn;
    private String average;
    private String numraters;
    private Integer bookPageCount;
    private BigDecimal bookPrice;
    private BigDecimal  bookPricing;
    private Integer publishNum;
    private Integer printingNum;
    private Integer freeDb;
    private String j_order_number;
    private String member_name;
    private String area_name;
    private String distance;
    private String j_headimgurl;
    private Date time;
    private String image;
    private Integer book_count;
    private List<BooksBean> books;
    private BooksBean book;

    private String error;
    private Integer status;
    private Integer total;
    private int j_id;
    private String j_book_name;
    private String j_book_subtitle;
    private String j_book_cover_image;
    private String j_book_author;
    private String j_book_press;
    private String j_book_oldname;
    private String j_book_puttime;
    private int j_book_pagecount;
    private int j_flag;
    private int j_book_status;
    private BigDecimal j_book_price;
    private String j_book_bind;
    private String j_book_introduction;
    private String j_book_isbn10;
    private String j_book_isbn13;
    private int j_book_create_id;
    private Date j_book_create_time;
    private String j_catalog;
    private String j_author_intro;
    private String j_average;
    private String j_numraters;
    private int is_serial;
    private BigDecimal book_price;
    private String printing_time;
    private int printing_num;
    private String edition_time;
    private int edition_num;
    private String detail;
    private String book_norms;
    private int volume_num;
    private int update_id;
    private Date update_time;
    private BigDecimal weight;
    private int is_del;

    private int book_db_id;
    private BigDecimal book_db_price;
    private int db_status;

    //借出数量
    private int borrow;

    //总库存
    private int count;

    //空闲
    private int free;

    //借出
    private int use;

    //借阅的次数
    private int readCount;

    //是否已读
    private int hasRead;

    //是否想读
    private int wantTORead;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getJ_headimgurl() {
        return j_headimgurl;
    }

    public void setJ_headimgurl(String j_headimgurl) {
        this.j_headimgurl = j_headimgurl;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setJ_id(int j_id) {
        this.j_id = j_id;
    }

    public String getJ_book_name() {
        return j_book_name;
    }

    public void setJ_book_name(String j_book_name) {
        this.j_book_name = j_book_name;
    }

    public String getJ_book_subtitle() {
        return j_book_subtitle;
    }

    public void setJ_book_subtitle(String j_book_subtitle) {
        this.j_book_subtitle = j_book_subtitle;
    }

    public String getJ_book_cover_image() {
        return j_book_cover_image;
    }

    public void setJ_book_cover_image(String j_book_cover_image) {
        this.j_book_cover_image = j_book_cover_image;
    }

    public String getJ_book_author() {
        return j_book_author;
    }

    public void setJ_book_author(String j_book_author) {
        this.j_book_author = j_book_author;
    }

    public String getJ_book_press() {
        return j_book_press;
    }

    public void setJ_book_press(String j_book_press) {
        this.j_book_press = j_book_press;
    }

    public String getJ_book_oldname() {
        return j_book_oldname;
    }

    public void setJ_book_oldname(String j_book_oldname) {
        this.j_book_oldname = j_book_oldname;
    }

    public String getJ_book_puttime() {
        return j_book_puttime;
    }

    public void setJ_book_puttime(String j_book_puttime) {
        this.j_book_puttime = j_book_puttime;
    }

    public int getJ_book_pagecount() {
        return j_book_pagecount;
    }

    public void setJ_book_pagecount(int j_book_pagecount) {
        this.j_book_pagecount = j_book_pagecount;
    }

    public int getJ_flag() {
        return j_flag;
    }

    public void setJ_flag(int j_flag) {
        this.j_flag = j_flag;
    }

    public int getJ_book_status() {
        return j_book_status;
    }

    public void setJ_book_status(int j_book_status) {
        this.j_book_status = j_book_status;
    }

    public BigDecimal getJ_book_price() {
        return j_book_price;
    }

    public void setJ_book_price(BigDecimal j_book_price) {
        this.j_book_price = j_book_price;
    }

    public String getJ_book_bind() {
        return j_book_bind;
    }

    public void setJ_book_bind(String j_book_bind) {
        this.j_book_bind = j_book_bind;
    }

    public String getJ_book_introduction() {
        return j_book_introduction;
    }

    public void setJ_book_introduction(String j_book_introduction) {
        this.j_book_introduction = j_book_introduction;
    }

    public String getJ_book_isbn10() {
        return j_book_isbn10;
    }

    public void setJ_book_isbn10(String j_book_isbn10) {
        this.j_book_isbn10 = j_book_isbn10;
    }

    public String getJ_book_isbn13() {
        return j_book_isbn13;
    }

    public void setJ_book_isbn13(String j_book_isbn13) {
        this.j_book_isbn13 = j_book_isbn13;
    }

    public int getJ_book_create_id() {
        return j_book_create_id;
    }

    public void setJ_book_create_id(int j_book_create_id) {
        this.j_book_create_id = j_book_create_id;
    }

    public Date getJ_book_create_time() {
        return j_book_create_time;
    }

    public void setJ_book_create_time(Date j_book_create_time) {
        this.j_book_create_time = j_book_create_time;
    }

    public String getJ_catalog() {
        return j_catalog;
    }

    public void setJ_catalog(String j_catalog) {
        this.j_catalog = j_catalog;
    }

    public String getJ_author_intro() {
        return j_author_intro;
    }

    public void setJ_author_intro(String j_author_intro) {
        this.j_author_intro = j_author_intro;
    }

    public String getJ_average() {
        return j_average;
    }

    public void setJ_average(String j_average) {
        this.j_average = j_average;
    }

    public String getJ_numraters() {
        return j_numraters;
    }

    public void setJ_numraters(String j_numraters) {
        this.j_numraters = j_numraters;
    }

    public int getIs_serial() {
        return is_serial;
    }

    public void setIs_serial(int is_serial) {
        this.is_serial = is_serial;
    }

    public BigDecimal getBook_price() {
        return book_price;
    }

    public void setBook_price(BigDecimal book_price) {
        this.book_price = book_price;
    }

    public String getPrinting_time() {
        return printing_time;
    }

    public void setPrinting_time(String printing_time) {
        this.printing_time = printing_time;
    }

    public int getPrinting_num() {
        return printing_num;
    }

    public void setPrinting_num(int printing_num) {
        this.printing_num = printing_num;
    }

    public String getEdition_time() {
        return edition_time;
    }

    public void setEdition_time(String edition_time) {
        this.edition_time = edition_time;
    }

    public int getEdition_num() {
        return edition_num;
    }

    public void setEdition_num(int edition_num) {
        this.edition_num = edition_num;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBook_norms() {
        return book_norms;
    }

    public void setBook_norms(String book_norms) {
        this.book_norms = book_norms;
    }

    public int getVolume_num() {
        return volume_num;
    }

    public void setVolume_num(int volume_num) {
        this.volume_num = volume_num;
    }

    public int getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(int update_id) {
        this.update_id = update_id;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getBook_db_id() {
        return book_db_id;
    }

    public void setBook_db_id(int book_db_id) {
        this.book_db_id = book_db_id;
    }

    public BigDecimal getBook_db_price() {
        return book_db_price;
    }

    public void setBook_db_price(BigDecimal book_db_price) {
        this.book_db_price = book_db_price;
    }

    public int getDb_status() {
        return db_status;
    }

    public void setDb_status(int db_status) {
        this.db_status = db_status;
    }

    public int getBorrow() {
        return borrow;
    }

    public void setBorrow(int borrow) {
        this.borrow = borrow;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public void setUse(int use) {
        this.use = use;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public void setWantTORead(int wantTORead) {
        this.wantTORead = wantTORead;
    }

    public BooksBean getBook() {
        return book;
    }

    public void setBook(BooksBean book) {
        this.book = book;
    }

    public Integer getJ_book_id() {
        return j_book_id;
    }

    public void setJ_book_id(Integer j_book_id) {
        this.j_book_id = j_book_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPress() {
        return bookPress;
    }

    public void setBookPress(String bookPress) {
        this.bookPress = bookPress;
    }

    public String getBookPuttime() {
        return bookPuttime;
    }

    public void setBookPuttime(String bookPuttime) {
        this.bookPuttime = bookPuttime;
    }

    public String getBookBind() {
        return bookBind;
    }

    public void setBookBind(String bookBind) {
        this.bookBind = bookBind;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getNumraters() {
        return numraters;
    }

    public void setNumraters(String numraters) {
        this.numraters = numraters;
    }

    public Integer getBookPageCount() {
        return bookPageCount;
    }

    public void setBookPageCount(Integer bookPageCount) {
        this.bookPageCount = bookPageCount;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public BigDecimal getBookPricing() {
        return bookPricing;
    }

    public void setBookPricing(BigDecimal bookPricing) {
        this.bookPricing = bookPricing;
    }

    public Integer getPublishNum() {
        return publishNum;
    }

    public void setPublishNum(Integer publishNum) {
        this.publishNum = publishNum;
    }

    public Integer getPrintingNum() {
        return printingNum;
    }

    public void setPrintingNum(Integer printingNum) {
        this.printingNum = printingNum;
    }

    public Integer getFreeDb() {
        return freeDb;
    }

    public void setFreeDb(Integer freeDb) {
        this.freeDb = freeDb;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }

    public Integer getHasRead() {
        return hasRead;
    }

    public void setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
    }

    public Integer getWantTORead() {
        return wantTORead;
    }

    public void setWantTORead(Integer wantTORead) {
        this.wantTORead = wantTORead;
    }

    public Integer getJ_id() {
        return j_id;
    }

    public void setJ_id(Integer j_id) {
        this.j_id = j_id;
    }

    public String getJ_order_number() {
        return j_order_number;
    }

    public void setJ_order_number(String j_order_number) {
        this.j_order_number = j_order_number;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public Integer getBook_count() {
        return book_count;
    }

    public void setBook_count(Integer book_count) {
        this.book_count = book_count;
    }

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BooksBean{" +
                "bookid=" + bookid +
                ", j_book_id=" + j_book_id +
                ", bookName='" + bookName + '\'' +
                ", bookImage='" + bookImage + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPress='" + bookPress + '\'' +
                ", bookPuttime='" + bookPuttime + '\'' +
                ", bookBind='" + bookBind + '\'' +
                ", isbn='" + isbn + '\'' +
                ", average='" + average + '\'' +
                ", numraters='" + numraters + '\'' +
                ", bookPageCount=" + bookPageCount +
                ", bookPrice=" + bookPrice +
                ", bookPricing=" + bookPricing +
                ", publishNum=" + publishNum +
                ", printingNum=" + printingNum +
                ", freeDb=" + freeDb +
                ", j_order_number='" + j_order_number + '\'' +
                ", member_name='" + member_name + '\'' +
                ", area_name='" + area_name + '\'' +
                ", image='" + image + '\'' +
                ", book_count=" + book_count +
                ", books=" + books +
                ", book=" + book +
                ", error='" + error + '\'' +
                ", status=" + status +
                ", total=" + total +
                ", j_id=" + j_id +
                ", j_book_name='" + j_book_name + '\'' +
                ", j_book_subtitle='" + j_book_subtitle + '\'' +
                ", j_book_cover_image='" + j_book_cover_image + '\'' +
                ", j_book_author='" + j_book_author + '\'' +
                ", j_book_press='" + j_book_press + '\'' +
                ", j_book_oldname='" + j_book_oldname + '\'' +
                ", j_book_puttime='" + j_book_puttime + '\'' +
                ", j_book_pagecount=" + j_book_pagecount +
                ", j_flag=" + j_flag +
                ", j_book_status=" + j_book_status +
                ", j_book_price=" + j_book_price +
                ", j_book_bind='" + j_book_bind + '\'' +
                ", j_book_introduction='" + j_book_introduction + '\'' +
                ", j_book_isbn10='" + j_book_isbn10 + '\'' +
                ", j_book_isbn13='" + j_book_isbn13 + '\'' +
                ", j_book_create_id=" + j_book_create_id +
                ", j_book_create_time=" + j_book_create_time +
                ", j_catalog='" + j_catalog + '\'' +
                ", j_author_intro='" + j_author_intro + '\'' +
                ", j_average='" + j_average + '\'' +
                ", j_numraters='" + j_numraters + '\'' +
                ", is_serial=" + is_serial +
                ", book_price=" + book_price +
                ", printing_time='" + printing_time + '\'' +
                ", printing_num=" + printing_num +
                ", edition_time='" + edition_time + '\'' +
                ", edition_num=" + edition_num +
                ", detail='" + detail + '\'' +
                ", book_norms='" + book_norms + '\'' +
                ", volume_num=" + volume_num +
                ", update_id=" + update_id +
                ", update_time=" + update_time +
                ", weight=" + weight +
                ", is_del=" + is_del +
                ", book_db_id=" + book_db_id +
                ", book_db_price=" + book_db_price +
                ", db_status=" + db_status +
                ", borrow=" + borrow +
                ", count=" + count +
                ", free=" + free +
                ", use=" + use +
                ", readCount=" + readCount +
                ", hasRead=" + hasRead +
                ", wantTORead=" + wantTORead +
                '}';
    }
}
