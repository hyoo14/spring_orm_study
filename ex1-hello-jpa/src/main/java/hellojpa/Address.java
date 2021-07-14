package hellojpa;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String street;

    //@Column(name = "ZIPCODE_ID") // 이런 것도 됨.
    private String zipcode;

    //private Member member; //뭐 이런것도 가능하다

    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
