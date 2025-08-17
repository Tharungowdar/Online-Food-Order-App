package orderapp.entity;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull
	@NotBlank
	private String name;
	@NotNull
	@NotBlank
	private String address;
	@NotNull
	private Long contactNumber;
	@NotBlank
	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9.%/=-]+@[A-Za-z0-9.-]+\\.[A-za-z]{2,6}$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid wmail address")
	private String email;
	@CreationTimestamp
	private LocalDate createdAt;
	@UpdateTimestamp
	private LocalDate updateAt;
	@ManyToMany
	@JoinTable(name="restaurant_food",joinColumns = @JoinColumn(name ="id-restaurant"),inverseJoinColumns = @JoinColumn(name ="id_food"))
	private List<Food> food;
	
	@OneToMany(mappedBy = "restuarant")
	private List<Order> orders;
	
}
