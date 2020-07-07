package com.agilepeak.blog;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDTO {
    String name;
    JobTitle jobTitle;

    public enum JobTitle {
        SOFTWARE_DEVELOPER,
        QA_ENGINEER
    }
}
