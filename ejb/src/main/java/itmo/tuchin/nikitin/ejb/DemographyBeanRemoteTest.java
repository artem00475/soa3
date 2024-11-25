package itmo.tuchin.nikitin.ejb;

import jakarta.ejb.Remote;

@Remote
public interface DemographyBeanRemoteTest {
    double getPercentageByHairColor(String hairColor);
    int getCountByNationalityAndHairColor(String nationality, String hairColor);
}
