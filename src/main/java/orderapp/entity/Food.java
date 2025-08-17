package orderapp.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank
	@NotNull
	private String name;
	@NotBlank
	@NotNull
	private String description;
	@NotBlank
	@NotNull
	private Float price;

	@JsonIgnore
	@ManyToMany(mappedBy = "food")
	List<Restaurant> restaurants;

}
