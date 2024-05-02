package com.intuit.playerpractice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private String playerId; // This is the string ID from the CSV

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    //Change fields from primitive to Integer objects, because of null default values
    private int age;
    private String teamName;
    private String position;
    private int gamesPlayed;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
    private int shotsOnTarget;
    private double passAccuracy;
    private double runningDistance;
    private double speed;
    private int height;
    private int weight;
    private String nationality;
    private String contractUntil;
    private double marketValue;
}
