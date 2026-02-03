package pe.com.birdcare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("Admin"),
    CUSTOMER("Customer"),
    GUEST("Guest");

    final String value;
}
