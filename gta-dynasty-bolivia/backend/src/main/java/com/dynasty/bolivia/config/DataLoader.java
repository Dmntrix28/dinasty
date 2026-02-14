package com.dynasty.bolivia.config;

import com.dynasty.bolivia.model.*;
import com.dynasty.bolivia.repository.PropertyRepository;
import com.dynasty.bolivia.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PropertyRepository propertyRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        User owner = userRepository.save(User.builder().name("Owner Demo").email("owner@dynasty.bo").password(passwordEncoder.encode("123456")).build());
        User user = userRepository.save(User.builder().name("User Demo").email("user@dynasty.bo").password(passwordEncoder.encode("123456")).build());

        List<String> zonas = List.of("Sopocachi", "Calacoto", "San Miguel", "Miraflores", "Achumani", "Obrajes", "Seguencoma", "Villa Fátima", "Centro", "Irpavi", "Los Pinos", "Cota Cota");
        double baseLat = -16.5;
        double baseLng = -68.15;

        for (int i = 0; i < 12; i++) {
            propertyRepository.save(Property.builder()
                    .titulo("Propiedad Dynasty #" + (i + 1))
                    .descripcion("Inmueble premium estilo GTA en " + zonas.get(i) + ", vista panorámica de La Paz y acabados de lujo.")
                    .tipo(PropertyType.values()[i % PropertyType.values().length])
                    .modalidad(i % 3 == 0 ? PropertyMode.AMBOS : (i % 2 == 0 ? PropertyMode.VENTA : PropertyMode.ALQUILER))
                    .precioVenta(85000.0 + (i * 18000))
                    .precioNoche(35.0 + (i * 7))
                    .ciudad("La Paz")
                    .zona(zonas.get(i))
                    .lat(baseLat + (i * 0.012))
                    .lng(baseLng + (i * 0.009))
                    .dormitorios(1 + (i % 5))
                    .banos(1 + (i % 3))
                    .m2(45.0 + (i * 14))
                    .imagenes(List.of(
                            "https://picsum.photos/seed/dynasty" + i + "/900/500",
                            "https://picsum.photos/seed/lapaz" + i + "/900/500"
                    ))
                    .disponible(true)
                    .owner(i % 2 == 0 ? owner : user)
                    .createdAt(LocalDateTime.now().minusDays(i))
                    .build());
        }
    }
}
