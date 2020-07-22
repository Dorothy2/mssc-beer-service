package guru.sfg.common.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

//import com.drifai.domain.BeerStyleEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto implements Serializable {
	
	private static final long serialVersionUID = -7259099783383800602L;

	@Null
	@JsonProperty("beerId")
	private UUID id;
	
	@Null
	private Integer version;
	
	@Null
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
	private OffsetDateTime createdDate;
	
	@Null
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
	private OffsetDateTime lastModifiedDate;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String beerName;
	
	@NotNull
	//private BeerStyleEnum beerStyle;
	private String beerStyle;
	
	@NotNull
	private String upc;
	
	@NotNull
	@Positive
	@JsonFormat(shape=JsonFormat.Shape.STRING)
	private BigDecimal price;
	
	@Positive
	private Integer quantityOnHand;
	
	//@JsonSerialize(using = LocalDateSerializer.class)
	//@JsonDeserialize(using = LocalDateDeserializer.class)
	//private LocalDate myLocalDate;
	

}
