package project.imaginarium.data.models;

public enum Sector {
    HOTELS("hotels"),
    VEHICLES("vehicles"),
    TIME_TRAVEL("timeTravel"),
    GUIDES("guides");

    public final String name;

    Sector(String name) {
        this.name = name;
    }
}
