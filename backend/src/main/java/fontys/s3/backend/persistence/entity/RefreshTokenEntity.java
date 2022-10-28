package fontys.s3.backend.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "refreshtoken")
@Table(name = "tokens")
public class RefreshTokenEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private UserEntity user;

        @Column(nullable = false, unique = true)
        private String token;

        @Column(nullable = false)
        private Instant expiryDate;


        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public UserEntity getUser() {
            return user;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Instant getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
        }
}
