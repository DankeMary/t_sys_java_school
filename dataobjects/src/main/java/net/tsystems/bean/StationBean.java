package net.tsystems.bean;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


public class StationBean implements Serializable {
    private int id;

    @NotEmpty(message = "Station name required")
    @Size(max = 65, message = "Max length - 65")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z \\-0-9]+$", message = "Station name has to have at least one letter and can have only latin letters, digits, spaces and hyphens")
    private String name;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name.trim();
    }
}
