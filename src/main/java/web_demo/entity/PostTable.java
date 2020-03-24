package web_demo.entity;

import java.time.LocalDate;

public class PostTable {
    String postTitle;
    String Author;
    LocalDate postDate;
    int likeCount;
    int CommentCount;

    public PostTable(String postTitle, String author, LocalDate postDate, int likeCount, int commentCount) {
        this.postTitle = postTitle;
        Author = author;
        this.postDate = postDate;
        this.likeCount = likeCount;
        CommentCount = commentCount;
    }

    public String getPostTitle() {return postTitle;}
    public String getAuthor() {return Author;}
    public LocalDate getPostDate() {return postDate;}
    public int getLikeCount() {return likeCount;}
    public int getCommentCount() {return CommentCount;}

    public void setPostTitle(String postTitle) {this.postTitle = postTitle;}
    public void setAuthor(String author) {Author = author;}
    public void setPostDate(LocalDate postDate) {this.postDate = postDate;}
    public void setLikeCount(int likeCount) {this.likeCount = likeCount;}
    public void setCommentCount(int commentCount) {CommentCount = commentCount;}
}
