package fontys.s3.backend.business;

import fontys.s3.backend.business.exception.InvalidAirportException;

public interface AirportValidator {
    void validAirport(String initials) throws InvalidAirportException;
}
