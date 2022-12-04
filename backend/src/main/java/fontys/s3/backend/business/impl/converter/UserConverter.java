package fontys.s3.backend.business.impl.converter;

import fontys.s3.backend.domain.model.User;
import fontys.s3.backend.persistence.entity.UserEntity;

public final class UserConverter {
    private UserConverter() {
    }

    public static User convert(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .build();
    }

}
