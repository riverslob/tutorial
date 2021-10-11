package architecture;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class LayeredArchitectureTest extends ArchitectureTest {

    @Test
    void layer_dependencies_must_be_respected_include_the_tests() {
        layeredArchitecture()

                .layer("Rest").definedBy("dev.demo.ddd.blogservice.adapters.inbound.rest..")
//                .layer("Client").definedBy("dev.demo.ddd.blogservice.adapters.outbound.client..")
                .layer("Persistence").definedBy("dev.demo.ddd.blogservice.adapters.outbound.persistence..")
                .layer("Application").definedBy("dev.demo.ddd.blogservice.application..")

                .whereLayer("Rest").mayNotBeAccessedByAnyLayer()
//                .whereLayer("Client").mayNotBeAccessedByAnyLayer()
                .whereLayer("Persistence").mayNotBeAccessedByAnyLayer()
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Rest")

                .as("The layer dependencies must be respected (include the tests)")
                .because("we must follow the DIP principle.")
                .check(classes);
    }
}
