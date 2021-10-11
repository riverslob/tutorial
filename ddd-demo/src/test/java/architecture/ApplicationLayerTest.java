package architecture;

import dev.demo.ddd.blogservice.common.archrules.UseCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class ApplicationLayerTest extends ArchitectureTest {

    @Nested
    class use_case {

        @Test
        void use_cases_should_be_named_ending_with_UseCase() {
            classes().that()
                    .areAnnotatedWith(UseCase.class)
                    .should().haveSimpleNameEndingWith("UseCase")
                    .as("The use cases should be named ending with 'UseCase'.")
                    .check(classes);
        }

        @Test
        void use_cases_should_annotate_useCase() {
            classes()
                    .that().haveSimpleNameEndingWith("UseCase")
                    .and().areNotInterfaces()
                    .should().beAnnotatedWith(UseCase.class)
                    .as("The use cases should annotated 'UseCase' interface.")
                    .check(classes);
        }

        @Test
        void use_cases_should_place_under_usecase_package() {
            classes()
                    .that().areAnnotatedWith(UseCase.class)
                    .should().resideInAPackage("..application.usecase..")
                    .as("The use cases should place under usecases package.")
                    .check(classes);
        }
    }
}
