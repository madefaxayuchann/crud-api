package id.kawahedukasi.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity()
@Table(name = "product")
public class Product extends PanacheEntityBase {
  @Id
  @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", initialValue = 1, allocationSize = 1)

  @GeneratedValue(generator = "product_sequence", strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  public Integer id;
  @Column(name = "name")
  public String name;
  @Column(name = "count")
  public Integer count;
  @Column(name = "price")
  public Integer price;
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
