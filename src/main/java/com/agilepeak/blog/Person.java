package com.agilepeak.blog;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
    String name;
    String jobTitle;
}
