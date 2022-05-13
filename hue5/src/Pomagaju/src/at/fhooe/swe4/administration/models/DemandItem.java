package at.fhooe.swe4.administration.models;

public class DemandItem {
  private Integer id;
  private Article relatedArticle;
  private ReceivingOffice relatedOffice;
  private Integer amount;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public DemandItem(Integer id, Article relatedGood, ReceivingOffice relatedOffice, Integer amount) {
    this.id = id;
    this.relatedArticle = relatedGood;
    this.relatedOffice = relatedOffice;
    this.amount = amount;
  }

  public Article getRelatedArticle() {
    return relatedArticle;
  }

  public void setRelatedArticle(Article relatedArticle) {
    this.relatedArticle = relatedArticle;
  }

  public ReceivingOffice getRelatedOffice() {
    return relatedOffice;
  }

  public void setRelatedOffice(ReceivingOffice relatedOffice) {
    this.relatedOffice = relatedOffice;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
}
