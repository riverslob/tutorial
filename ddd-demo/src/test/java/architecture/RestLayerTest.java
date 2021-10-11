package architecture;

import dev.demo.ddd.blogservice.common.archrules.RequestDto;
import dev.demo.ddd.blogservice.common.archrules.ResponseDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class RestLayerTest extends ArchitectureTest {

    @Nested
    class request_dto {

        @Test
        void request_dtos_should_be_named_ending_with_Request() {
            classes()
                    .that().resideInAPackage("..inbound.rest..")
                    .and().areAnnotatedWith(RequestDto.class)
                    .should().haveSimpleNameEndingWith("Request")
                    .as("The request DTOs should be named ending with 'Request'.")
                    .check(classes);
        }

        @Test
        void request_dtos_should_annotate_RequestDto() {
            classes()
                    .that().resideInAPackage("..inbound.rest..")
                    .and().haveSimpleNameEndingWith("Request")
                    .and().areNotInterfaces()
                    .should().beAnnotatedWith(RequestDto.class)
                    .as("The request DTOs should annotated 'RequestDto' interface.")
                    .check(classes);
        }

    }

    @Nested
    class response_dto {

        @Test
        void response_dtos_should_be_named_ending_with_Dto() {
            classes()
                    .that().resideInAPackage("..inbound.rest..")
                    .and().areAnnotatedWith(ResponseDto.class)
                    .should().haveSimpleNameEndingWith("Response")
                    .as("The response DTOs should be named ending with 'Dto'.")
                    .check(classes);
        }

        @Test
        void response_dtos_should_annotate_ResponseDto() {
            classes()
                    .that().resideInAPackage("..inbound.rest..")
                    .and().haveSimpleNameEndingWith("Dto")
                    .and().areNotInterfaces()
                    .should().beAnnotatedWith(ResponseDto.class)
                    .as("The response DTOs should annotated 'ResponseDto' interface.")
                    .check(classes);
        }

    }
}
