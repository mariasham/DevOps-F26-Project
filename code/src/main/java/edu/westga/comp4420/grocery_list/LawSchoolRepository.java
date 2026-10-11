package edu.westga.comp4420.grocery_list;

import java.util.ArrayList;

/**
 * Provides law school listing data for the application.
 *
 * @author Maria Shamasna
 * @version 2026-10-11 [Fall 2026]
 */
public class LawSchoolRepository {
    /**
     * Creates the starting law school listings for the app.
     *
     * @return the sample law school listings
     */
    public ArrayList<LawSchool> getSampleLawSchools() {
        ArrayList<LawSchool> schools = new ArrayList<LawSchool>();

        schools.add(new LawSchool("Georgia State University College of Law", "Atlanta, GA",
                "March 15", "Open", "https://law.gsu.edu/admissions/"));
        schools.add(new LawSchool("University of Georgia School of Law", "Athens, GA",
                "June 1", "Open", "https://www.law.uga.edu/admissions"));
        schools.add(new LawSchool("Emory University School of Law", "Atlanta, GA",
                "April 1", "Open", "https://law.emory.edu/admission/"));
        schools.add(new LawSchool("Mercer University School of Law", "Macon, GA",
                "July 1", "Open", "https://law.mercer.edu/admissions/"));
        schools.add(new LawSchool("Atlanta's John Marshall Law School", "Atlanta, GA",
                "Priority deadline varies", "Open", "https://www.johnmarshall.edu/admissions/"));

        return schools;
    }
}