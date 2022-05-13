package at.fhooe.swe4.administration.controller;

import at.fhooe.swe4.administration.enums.Category;
import at.fhooe.swe4.administration.enums.Condition;
import at.fhooe.swe4.administration.models.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArticleController {
  private static ArticleController obj;

  private static final ObservableList<Article> articles =
          FXCollections.observableArrayList();

  private ArticleController()  {}

  public static ArticleController getInstance() {
    if (obj == null) {
      obj = new ArticleController();

      obj.initWithTestData();
    }
    return obj;
  }

  private void initWithTestData() {
    Article temp = new Article(1, "Babynahrung", "in Gläsern, alle Sorten", Condition.NEW, Category.FOOD);
    Article temp2 = new Article(2, "Damenhygieneartikel", "alles mögliche", Condition.NEW, Category.HYGIENE);
    Article temp3 = new Article(3, "Spielzeug", "für alle Altersstufen", Condition.NEW, Category.OTHER);
    Article temp4 = new Article(4, "Handys", "frei, müssen keine Smartphones sein", Condition.NEW, Category.ELECTRONICS);

    articles.addAll(temp, temp2, temp3, temp4);
  }

  public ObservableList<Article> getArrayList() {
    return articles;
  }

  public void addArticle(Article a) {
    articles.add(a);
  }

  public void deleteArticle(Article a) {
    articles.remove(a);
  }

  public void updateArticle(Article a, String name, String description, Condition condition, Category category) {
    a.setName(name);
    a.setDescription(description);
    a.setCondition(condition);
    a.setCategory(category);
  }

}
