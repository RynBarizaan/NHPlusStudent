package model;

/**
 * A Person is someone with a firstname and a surname
 */
public abstract class Person {
    private String firstName;
    private String surname;

    /**
     *
     * @param firstName
     * @param surname
     */
    public Person(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }

    /**
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName set new firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname set new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
