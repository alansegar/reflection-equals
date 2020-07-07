package com.agilepeak.blog;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlogPostDTO {
    PersonDTO author;
}
