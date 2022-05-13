package at.fhooe.swe4.administration.controller;

import at.fhooe.swe4.administration.models.Article;
import at.fhooe.swe4.administration.models.DemandItem;
import at.fhooe.swe4.administration.models.ReceivingOffice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class DemandController {
  private static DemandController obj;

  private static final ObservableList<DemandItem> demand =
          FXCollections.observableArrayList();

  private static FilteredList<DemandItem> filteredDemand = new FilteredList<>(demand);

  private DemandController() {}

  public static DemandController getInstance() {
    if(obj == null) {
      obj = new DemandController();
      obj.initWithTestData();
    }
    return obj;
  }

  private void initWithTestData(){
    demand.add(new DemandItem(1, ArticleController.getInstance().getArrayList().get(0),
            OfficesController.getInstance().getArrayList().get(0), 3));
    demand.add(new DemandItem(2, ArticleController.getInstance().getArrayList().get(1),
            OfficesController.getInstance().getArrayList().get(0), 12));
    demand.add(new DemandItem(3, ArticleController.getInstance().getArrayList().get(2),
            OfficesController.getInstance().getArrayList().get(1), 4));
  }

  public ObservableList<DemandItem> getArrayList() {return demand;}
  public FilteredList<DemandItem> getFilteredDemand() {return filteredDemand;}

  public void addDemand(DemandItem d) {demand.add(d);}
  public void deleteDemand(DemandItem d) {demand.remove(d);}
  public void updateArticle(DemandItem d, Article article, ReceivingOffice relatedOffice, Integer amount) {
    d.setRelatedArticle(article);
    d.setRelatedOffice(relatedOffice);
    d.setAmount(amount);
  }
}
