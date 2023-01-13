package id.kawahedukasi.service;

import id.kawahedukasi.models.Item;
import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ItemService {
  Logger logger = LoggerFactory.getLogger(ItemService.class);

  @Transactional
  @Scheduled(cron = "0 * * * *")
  public void delete() {

    List<Item> arrayList = Item.listAll();
    List<Item> filteredList = arrayList.stream()
            .filter(item -> item.count == 0)
            .collect(Collectors.toList());

    for (Item item : filteredList) {
      Item.deleteById(item.id);
      logger.info("Item success delete");
    }
  }
}
