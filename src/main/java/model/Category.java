package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

// Lombok Data takes care of getter and setter methods
// ignoreUnknown allows you to create model only for a slice of attributes

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Category {

    private Integer id;
    private String name;




}
