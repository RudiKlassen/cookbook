package de.david.cookbook.rest;

import de.david.cookbook.persistence.entities.Category;
import de.david.cookbook.persistence.entities.Recipe;

/**
 * Repräsentiert eine {@link Category}
 */
public class CategoryRessource {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
