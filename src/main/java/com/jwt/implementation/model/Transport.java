package com.jwt.implementation.model;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transport")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transportId;

    @Column(name = "bus_number", unique = true, nullable = false)
    private String busNumber;

    @Column(name = "route")
    private String route;

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "contact_number")
    private String contactNumber;
}