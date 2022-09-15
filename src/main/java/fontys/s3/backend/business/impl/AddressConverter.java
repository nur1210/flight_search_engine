package fontys.s3.backend.business.impl;

import fontys.s3.backend.domain.Address;
import fontys.s3.backend.persistence.entity.AddressEntity;

public class AddressConverter {
    public static Address convert(AddressEntity address){
        return Address.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }
}
