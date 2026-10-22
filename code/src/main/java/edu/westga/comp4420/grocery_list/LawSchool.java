package edu.westga.comp4420.grocery_list;

/**
 * Stores information about a law school application listing.
 * 
 * @author Maria Shamasna
 * @version 2026-10-11 [Fall 2026]
 */
public class LawSchool {
    private String name;
    private String location;
    private String deadline;
    private String status;
    private String applicationLink;

    /**
     * Creates a law school listing.
     *
     * @param name the name of the law school
     * @param location the location of the law school
     * @param deadline the application deadline
     * @param status the current application status
     * @param applicationLink the official application link
     */
    public LawSchool(String name, String location, String deadline, String status, String applicationLink) {
        this.name = name;
        this.location = location;
        this.deadline = deadline;
        this.status = status;
        this.applicationLink = applicationLink;
    }

    /**
     * Gets the law school name.
     *
     * @return the law school name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the law school location.
     *
     * @return the law school location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Gets the application deadline.
     *
     * @return the application deadline
     */
    public String getDeadline() {
        return this.deadline;
    }

    /**
     * Gets the application status.
     *
     * @return the application status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Gets the official application link.
     *
     * @return the official application link
     */
    public String getApplicationLink() {
        return this.applicationLink;
    }

    @Override
    public String toString() {
        return this.name + " - " + this.status;
    }
}