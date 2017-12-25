//리뷰정보 관리
package ClassObj;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author User
 */
public class ReviewInfo {
// **오류 조심 menu_name같이 언더바 붙인 이름은 인식을 못함! &너무 긴 변수명은 인식 못함 phonenumber정도 길면 인식 못함
    //db랑은 상관 없음 즉 이클래스 안에서 언더바 사용하지 말 것
    //textfeild는 setString으로 값을 받을 수 있다
    //tableview는 SimpleStringProperty를 해줘야 함

    private int reviewid;
    private String consumerid;
    private String cafename;
    private String menuname;
    private String star;
    private String title;
    private String reviewtext;
    private String reviewdate;

    public ReviewInfo(int reviewid, String consumerid, String cafename, String menuname, String star, String title, String reviewtext, String reviewdate) {
        this.reviewid = reviewid;
        this.consumerid = consumerid;
        this.cafename = cafename;
        this.menuname = menuname;
        this.star = star;
        this.title = title;
        this.reviewtext = reviewtext;
        this.reviewdate = reviewdate;
    }

//  final SimpleStringProperty reviewimg; 이미지는 뺀다.
    //final SimpleStringProperty reviewdate
    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public String getConsumerid() {
        return consumerid;
    }

    public void setConsumerid(String consumerid) {
        this.consumerid = consumerid;
    }

    public String getCafename() {
        return cafename;
    }

    public void setCafename(String cafename) {
        this.cafename = cafename;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewtext() {
        return reviewtext;
    }

    public void setReviewtext(String reviewtext) {
        this.reviewtext = reviewtext;
    }

    public String getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(String reviewdate) {
        this.reviewdate = reviewdate;
    }

}
