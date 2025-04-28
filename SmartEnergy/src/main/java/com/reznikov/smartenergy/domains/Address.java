package com.reznikov.smartenergy.domains;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @Column(nullable = false)
    @NonNull
    private double latitude;

    @Column(nullable = false)
    @NonNull
    private double longitude;

    @Column(nullable = false)
    @NonNull
    private String city;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private  LocalDateTime updated;


}
