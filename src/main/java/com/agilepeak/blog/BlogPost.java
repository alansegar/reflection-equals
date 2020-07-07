package com.agilepeak.blog;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlogPost {
    Person author;
}
