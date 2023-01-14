package id.kawahedukasi.service;

import id.kawahedukasi.models.Item;
import id.kawahedukasi.service.scheduler.SchedularItem;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.*;

@ApplicationScoped
public class ItemService {

  @Inject
  SchedularItem schedularItem;

  //  this is service getAll item
  public List<Map<String, Object>> getAll() {
    List<Item> item = Item.listAll();
    List<Map<String, Object>> result = new ArrayList<>();
    for (Item item1 : item) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", item1.id);
      map.put("name", item1.name);
      map.put("price", item1.price);
      map.put("count", item1.count);
      map.put("type", item1.type);
      map.put("description", item1.description);

      result.add(map);
    }
    return result;
  }

  //  this is service for getItem by id
  public static Map<String, Object> getById(String id) {
    Item item = Item.findById(id);
    if (item == null) {
      throw new ValidationException("Item with id " + id + " not found");
    }
    return Map.of("data", item);
  }

//  this is service for create item

  public Map<String, Object> create(JsonObject request) {
    String name = request.getString("name");
    Double price = request.getDouble("price");
    String type = request.getString("type");
    Long count = request.getLong("count");
    String description = request.getString("description");

    if (name == null || price == null || type == null || count == null) {
      throw new ValidationException("BAD_REQUEST");
    }
    Item item = persistItem(name, type, description, count, price);

    return Map.of("id", item.id);
  }


  //this is service for persistItem;
  @Transactional
  public Item persistItem(String name, String type, String description, Long count, Double price) {
    Item item = new Item();
    item.name = name;
    item.type = type;
    item.description = description;
    item.count = count;
    item.price = price;
    item.persist();

    return item;
  }

  // this is service for put data
  @Transactional
  public Map<String, Object> update(String id, JsonObject request) {
    String name = request.getString("name");
    Long count = request.getLong("count");
    String type = request.getString("type");
    Double price = request.getDouble("price");
    String description = request.getString("description");

    if (name == null || price == null || type == null || count == null) {
      throw new ValidationException("BAD_REQUEST");
    }

    Item item = Item.findById(id);
    if (item == null) {
      throw new ValidationException("ITEM_NOT_FOUND");
    }

    item.persist();
    return Map.of("id", item.id);
  }

  // this is service for delete item
  @Transactional
  public static Map<String, Object> delete(String id) {
    Item item = Item.findById(id);
    if (item == null) {
      throw new ValidationException("ITEM_ALREADY_DELETED");
    }
    item.delete();

    return Map.of("id", item.id);
  }

}
