package id.kawahedukasi.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity()
@Table(name = "item")
public class Item extends PanacheEntityBase {
  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "uuid")
  @Column(name = "id")
  public String id;
  @Column(name = "name")
  public String name;
  @Column(name = "count")
  public Long count;
  @Column(name = "price")
  public Double price;
  @Column(name = "type")
  public String type;
  @Column(name = "description")
  public String description;

  @CreationTimestamp
  @Column(name = "created_at")
  public LocalDateTime createdAt;
  @UpdateTimestamp
  @Column(name = "updated_at")
  public LocalDateTime updatedAt;


}
