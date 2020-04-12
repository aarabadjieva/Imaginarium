package project.imaginarium.data.models;

import lombok.Getter;

@Getter
public enum Sector {
    HOTELS("hotels"),
    VEHICLES("vehicles"),
    TIME_TRAVEL("timeTravel"),
    GUIDES("guides"),
    CLIENTS("clients");

    public final String name;

    Sector(String name) {
        this.name = name;
    }
}
