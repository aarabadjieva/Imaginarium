package project.imaginarium.data.models;

import lombok.Getter;

@Getter
public enum Sector {
    HOTEL("hotel"),
    VEHICLE("vehicle"),
    EVENT("event"),
    GUIDE("guide"),
    CLIENT("client");

    public final String name;

    Sector(String name) {
        this.name = name;
    }
}
