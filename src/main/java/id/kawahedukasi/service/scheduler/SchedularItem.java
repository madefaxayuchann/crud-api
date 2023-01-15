package id.kawahedukasi.service.scheduler;

import id.kawahedukasi.models.Item;
import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;


@ApplicationScoped
public class SchedularItem {

  Logger logger = LoggerFactory.getLogger(SchedularItem.class);

  @Transactional
  @Scheduled(every = "1h")
  public void delete() {
    List<Item> arrayList = Item.listAll();
    arrayList.removeIf(item -> item.count != 0);
    if (arrayList.isEmpty()) {
      logger.info("No items found with count 0");
      return;
    }
    for (Item item : arrayList) {
      logger.info("Deleted items with count 0");
      item.delete();
    }
  }


}
