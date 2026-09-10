package com.ping.simpledirectoryapi;

public class User {

    public String id;

    public String username;

    // read-restricted and write-restricted for limited clients
    public String email;

    public String givenName;

    public String familyName;

    public String streetAddress;

    public String locality;

    public String populationId;

    // write-restricted for limited clients
    public Boolean enabled;

    public String type;

    public User() {
    }

    /**
     * Copy constructor. The store hands out copies so that callers holding a
     * fetched User cannot mutate the stored record, mirroring how a real
     * database only changes through its own operations.
     */
    public User(User other) {
        this.id = other.id;
        this.username = other.username;
        this.email = other.email;
        this.givenName = other.givenName;
        this.familyName = other.familyName;
        this.streetAddress = other.streetAddress;
        this.locality = other.locality;
        this.populationId = other.populationId;
        this.enabled = other.enabled;
        this.type = other.type;
    }
}
