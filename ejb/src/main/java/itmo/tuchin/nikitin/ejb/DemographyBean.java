package itmo.tuchin.nikitin.ejb;

import itmo.tuchin.nikitin.ejb.client.PeopleClient;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.LinkedList;
import java.util.List;

@Stateless
public class DemographyBean implements DemographyBeanRemoteTest {

    private List<String> colors;
    private List<String> countries;

    @Inject
    PeopleClient peopleClient;

    private List<String> getColors() {
        if (colors == null) {
            colors = peopleClient.getColors();
        }
        return colors;
    }

    private List<String> getCountries() {
        if (countries == null) {
            countries = peopleClient.getCountries();
        }
        return countries;
    }

    @Override
    public double getPercentageByHairColor(String color) {
        System.out.println(this);
        if (color == null || color.isEmpty() || !getColors().contains(color.toUpperCase())) {
            throw new IllegalArgumentException("hair-color");
        }
        int total = peopleClient.getTotal();
        return total == 0 ? 0.0 : (double) peopleClient.getCountByHairColor(color) / total * 100;
    }

    @Override
    public int getCountByNationalityAndHairColor(String nationality, String color) {
        System.out.println(this);
        List<String> errors = new LinkedList<>();
        if (color == null || color.isEmpty() || !getColors().contains(color.toUpperCase())) {
            errors.add("hair-color");
        }
        if (nationality == null || nationality.isEmpty() || !getCountries().contains(nationality.toUpperCase())) {
            errors.add("nationality");
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(",", errors));
        }
        return peopleClient.getCountByNationalityAndHairColor(nationality, color);
    }
}
