package soft.dot.com.campingandrandoneespot.com.dot.soft.entities;

public class Item {
    int id;
    String name;
    String description;
    Species species;

    public Item(int id, String name, String description, Species species) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.species = species;
    }

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
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
}
