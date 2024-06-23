package models;

import lombok.Data;

@Data
public class CreateUserBodyModel {
    private String name, job;
}