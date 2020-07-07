package com.agilepeak.blog;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RecursiveComparisonTest {

    Person devPerson = Person.builder().name("Example Name").jobTitle("SOFTWARE_DEVELOPER").build();
    BlogPost devBlogPost = BlogPost.builder().author(devPerson).build();
    PersonDTO devPersonDTO = PersonDTO.builder().name("Example Name").jobTitle(PersonDTO.JobTitle.SOFTWARE_DEVELOPER).build();
    BlogPostDTO devBlogPostDTO = BlogPostDTO.builder().author(devPersonDTO).build();

    Person qaPerson = Person.builder().name("Example Name").jobTitle("QA_ENGINEER").build();
    BlogPost qaBlogPost = BlogPost.builder().author(qaPerson).build();
    PersonDTO qaPersonDTO = PersonDTO.builder().name("Example Name").jobTitle(PersonDTO.JobTitle.QA_ENGINEER).build();
    BlogPostDTO qaBlogPostDTO = BlogPostDTO.builder().author(qaPersonDTO).build();

    @Test
    void blogPostToDTO_given_equal_field_values_using_custom_comparator() {
        assertThat(devBlogPost)
            .usingRecursiveComparison()
            .ignoringAllOverriddenEquals()
            .withComparatorForFields((stringJobTitle, enumJobTitle) ->
                    (stringJobTitle == null && enumJobTitle == null)
                        || (stringJobTitle != null
                        && enumJobTitle != null
                        && stringJobTitle.equals(((PersonDTO.JobTitle) enumJobTitle).name())) ? 0 : 1,
                "author.jobTitle"
            )
            .isEqualTo(devBlogPostDTO);
    }

    @Test
    void blogPostToDTO_given_equal_field_values_standard_comparator() {
        // Considers devBlogPost not equal to devBlogPostDTO but due to enum / String type mismatch.
        // This is good as it makes it obvious that a custom comparator is required for this scenario.
        // If .isNotEqualTo is changed to .isEqualTo here, it fails with a clear explanation of why.
        assertThat(devBlogPost)
            .usingRecursiveComparison()
            .ignoringAllOverriddenEquals()
            .isNotEqualTo(devBlogPostDTO);
    }

    @Test
    void blogPostToDTO_given_different_field_values_standard_comparator() {
        // Considers devBlogPost not equal to qaBlogPostDTO but due to enum / String type mismatch rather than value difference
        assertThat(devBlogPost)
            .usingRecursiveComparison()
            .ignoringAllOverriddenEquals()
            .isNotEqualTo(qaBlogPostDTO);
    }

    @Test
    void blogPostToDTO_given_different_field_values_using_custom_comparator() {
        assertThat(devBlogPost)
            .usingRecursiveComparison()
            .ignoringAllOverriddenEquals()
            .withComparatorForFields((stringJobTitle, enumJobTitle) ->
                    (stringJobTitle == null && enumJobTitle == null)
                        || (stringJobTitle != null
                        && enumJobTitle != null
                        && stringJobTitle.equals(((PersonDTO.JobTitle) enumJobTitle).name())) ? 0 : 1,
                "author.jobTitle"
            )
            .isNotEqualTo(qaBlogPostDTO);
    }

    @Test
    void dtoToBlogPost_given_different_field_values_using_custom_comparator() {
        assertThat(devBlogPostDTO)
            .usingRecursiveComparison()
            .ignoringAllOverriddenEquals()
            .withComparatorForFields((enumJobTitle, stringJobTitle) ->
                    (stringJobTitle == null && enumJobTitle == null)
                        || (stringJobTitle != null
                        && enumJobTitle != null
                        && stringJobTitle.equals(((PersonDTO.JobTitle) enumJobTitle).name())) ? 0 : 1,
                "author.jobTitle"
            )
            .isNotEqualTo(qaBlogPost);
    }

    @Test
    void dtoToBlogPost_given_different_field_values_using_standard_comparator() {
        // This is expected to fail but passes
        // qaBlogPost should not be equal to devBlogPostDTO but the jobTitle field appears to be ignored when there is no custom comparator
        // Expected behaviour (based on the reverse direction existing behaviour):
        // - failure due to not being able to compare an enum with a string for author.jobTitle
        assertThat(devBlogPostDTO)
            .usingRecursiveComparison()
            .ignoringAllOverriddenEquals()
            .isEqualTo(qaBlogPost);

        // This is expected to pass but it's odd that isEqualTo and isNotEqualTo both pass when comparing the same two objects
        assertThat(devBlogPostDTO)
            .usingRecursiveComparison()
            .ignoringAllOverriddenEquals()
            .isNotEqualTo(qaBlogPost);
    }
}
